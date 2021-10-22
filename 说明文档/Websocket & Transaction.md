# 说明文档

### Websocket

点击在线讨论按键即可进入聊天室。配备了心跳包检测。

相关代码在后端的decoder，encoder中。

ws配合http session使用，非匿名聊天，目前还没教spring security，还没做安全认证。

### 基本流程

下订单的服务流程如下所示：

+ 添加订单：userOrderDao.addOrder
+ 从购物车中删除购物车物品：cartDao.deleteCartItem
+ 添加订单物品：userOrderDao.addOrderItem
+ 减少书的存货：bookDao.adjustStock
+ 更新用户消费：consumptionDao.addUserConsumption
+ 扣除用户余额：userDao.updateUserProperty

### 不同事务属性下的表现

所有的isolation属性都采用默认的READ_COMMITED属性，避免脏读。

#### 1. REQUIRED

由于上述流程都应该在一个流程中完成，所以将所有的服务方法全都加@Transactional(propagation = Propagation.REQUIRED)是没有问题的。

具体流程如下：首先进入最外层的UserOrderDao.placeOrder方法，该方法使用**REQUIRED**属性，此时开启一个新事务，然后由于该方法中调用的每个服务方法都采用了**REQUIRED**属性，所以会在该方法开启的事务中执行对应服务。直到所有服务方法调用完毕，且没有出错，则提交事务，完成下订单的所有动作。一旦期间出现错误，所有操作都会回滚，保证了下订单动作的原子性。

当我们对一个存货量为0，即售罄的书进行下订单操作的时候，出现了如下报错：

Data truncation: BIGINT UNSIGNED value is out of range in '(`catstore`.`book`.`book_stock` - 1)'

因为我们试图将存货更新为负数，引发了错误，整个transaction将不会提交。不难发现数据库中的数据没有被修改，书也还在用户的购物车中。

相关代码：

```java
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public void placeOrder(OrderInfo orderInfo)
......
//Transaction Required
Integer orderId = userOrderDao.addOrder(userId, orderReceiver, orderAddress, orderTel);
System.out.println("orderId: " + orderId);
for (OrderItemInfo item : items) {
    Integer bookId = item.bookId;
    Integer purchaseNumber = item.purchaseNumber;
    Integer cartId = item.cartId;
    //Transaction Required
    cartDao.deleteCartItem(cartId);
    //Transaction Required
    userOrderDao.addOrderItem(orderId, bookId, purchaseNumber);
    //Transaction Required
    bookDao.adjustStock(bookId, purchaseNumber);
}
//Transaction Required
consumptionDao.addUserConsumption(totalPrice);
//Transaction Required
userDao.updateUserProperty(userId, totalPrice.negate()); //-totalPrice
}
```

#### 2. MANDATORY

MANDATORY要求使用当前的事务，如果没有事务那就会抛出异常。通过将最外层的placeOrder中的事务属性修改成MANDATORY，即：@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)

在测试下订单动作的时候抛出了异常：

org.springframework.transaction.IllegalTransactionStateException: No existing transaction found for transaction marked with propagation 'mandatory'

这是因为对于placeOrder而言，调用它的时候并不存在已开启的事务，所以会导致上述的异常被抛出。

#### 3. REQUIRES_NEW

REQUIRES_NEW属性要求无论当前是否存在事务都会开启新事务进行处理。将最外层的placeOrder方法中的事务属性修改成REQUIRES_NEW：@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)

运行后其效果和使用REQUIRED是相同的，因为在调用placeOrder之前没有事务被创建，所以两者都会开启新事务处理业务。

一般该属性可以用于处理下面的情况：比如执行下单业务的时候，我希望进行log。log这一业务和下单流程关系不大，我不希望log时出错导致整体事务全部回滚，这时候就适合使用REQUIRES_NEW事务属性。对于log这一业务，开启新事务进行处理，一旦过程中出现错误，回滚的是log业务的事务，而不会影响到下单流程。也就是说，只要下单的流程都正确无误地完成了，那么log出不出错都对下单没有任何影响。因为两者处在不同的事务中。

将bookDao.adjustStock设置为REQUIRED_NEW，这样进入该方法的时候会开启一个新transaction。同样尝试购买一个存货量为0的书：

这时发现主方法也回滚了，这是因为异常被上抛到了主方法中，主方法还是被认定为出现问题，产生了回滚。

当我把代码改成如下所示时（用try-catch捕获runtime exception），主方法没有出现回滚，即用户余额被扣除，书被移出购物车，但是书的余量没有被修改为负数，该子方法被回滚，而主方法没有被回滚。

```java
  for (OrderItemInfo item : items) {
      Integer bookId = item.bookId;
      Integer purchaseNumber = item.purchaseNumber;
      Integer cartId = item.cartId;
      //Transaction Required
      cartDao.deleteCartItem(cartId);
      //Transaction Required
      userOrderDao.addOrderItem(orderId, bookId, purchaseNumber);
      //Transaction Required_NEW
      try {
      	bookDao.adjustStock(bookId, purchaseNumber);
      } catch (Exception e) {
      	System.err.println(e.getMessage());
      }
  }
  //Transaction Required
  consumptionDao.addUserConsumption(totalPrice);
  //Transaction Required
  userDao.updateUserProperty(userId, totalPrice.negate()); //-totalPrice
```
