# 说明文档

### Multithread

有关多线程的部分位于com.catstore.statistic.GlobalStatistic中，具体调用位于VisitController中。前端访问主页会给后端发送getvisit请求，获取当前访问量的同时增加访问量。使用violate + synchronized关键字保证long变量相关操作的原子性。经过Jmeter测试，在500并发量下访问量被正常计数。

### Caching

使用了更适合java的Jedis，与此同时配置jedis pool控制jedis连接的数量上限。用池的方式可以保证连接数在可控范围内，不至于在高并发量下产生大量连接导致内存不够。

代码相关内容：

+ com.catstore.serviceimpl.BookServiceImplement

  ```java
  @Override
  public Book getBookById(Integer bookId) {
      String redisKey = RedisKeys.BookKey + ":" + bookId;
      Book book = redisUtil.get(redisKey, Book.class);
      if (book == null) {
          System.out.println("Fetch book " + bookId + " from database.");
          book = bookDao.getBookById(bookId);
          if (book != null) {
              redisUtil.set(redisKey, book);
              redisUtil.expire(redisKey, 60);
          }
      } else
          System.out.println("Directly fetch book " + bookId + " from redis.");
      return book;
  }
  ```

  该方法使用经典的redis + DB读数据的方式，如果redis中有缓存数据那就直接返回缓存数据，否则从数据库中拿数据并更新缓存。

  默认60s过期。（过期时间本身是个tradeoff，短的话可以处理脏读但是会造成大量读miss，长的话读miss低，效率高，但是如果有脏值那就会保存很久）

  ```java
  @Override
  public Message getBooks(int page) {
      // try fetch from redis:
      String pageRedisKey = RedisKeys.BookKey + ":page:" + page;
      String totalRedisKey = RedisKeys.BookKey + ":total:";
      List<Book> books = redisUtil.getWholeList(pageRedisKey, Book.class);
      Long total = redisUtil.get(totalRedisKey, Long.class);
      if (books.size() == 0 || total == null) {
          PageRequest pageRequest = PageRequest.of(page, Constant.BOOK_PAGE_SIZE);
          Page<Book> bookPage = bookDao.getBooks(pageRequest);
          books = bookPage.getContent();
          total = bookPage.getTotalElements();
          System.out.println("Fetch books of page = " + page + " from db.");
          // cache
          redisUtil.set(totalRedisKey, total);
          redisUtil.rpushObj(pageRedisKey, books);
          redisUtil.expire(totalRedisKey, 300);
          redisUtil.expire(pageRedisKey, 300);
      } else {
          System.out.println("Fetch books of page = " + page + " from redis.");
      }
      JSONObject messageContent = new JSONObject();
      messageContent.put("total", total);
      messageContent.put("books", books);
      return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.GENERAL_SUCCESS_MSG, messageContent);
  }
  ```

  分页的caching。由于本系统删除书籍几乎不会发生，而且主页只是显示书籍名称和缩略图，这些极少更改的，所以对于一致性而言不是非常看重；但是翻页查看又是用户一直会做的事情，所以该场景为读多写少的场景，很适用于cache，可以设置一个较长的expire time。

+ com.catstore.daoimpl.BookDaoImplement

  这一个类中的部分函数涉及到Redis+DB的写入问题，使用了两种常用策略，在注释中给出了策略具体内容。

+ com.catstore.daoimpl.UserOrderDaoImplement

  ```java
  @Override
  public ArrayList<UserOrder> getAllOrders(Integer userId) {
      if (userId == null) return null;
      String redisKey = RedisKeys.OrderKey + ":" + userId;
      List<UserOrder> userOrders = redisUtil.getWholeList(redisKey, UserOrder.class);
      if (userOrders.size() > 0) {
          System.out.println("Fetch orders from redis.");
      } else {
          System.out.println("Fetch orders from db.");
          userOrders = userOrderRepository.getAllOrders(userId);
          redisUtil.rpushObj(redisKey, userOrders);
          redisUtil.expire(redisKey, 300);
      }
      return (ArrayList<UserOrder>) userOrders;
  }
  ```
  
  orders以list的形式存入。
  
  orders的写入处理：
  
```java
  orderId = userOrderRepository.saveAndFlush(userOrder).getOrderId();
redisUtil.del(RedisKeys.OrderKey + ":" + userId);
  ```

