# CatStore
CatStore is a simple book store based on React and Spring boot. It's an assignment from lesson SE2321 and SE3353.
## To-do list
- [x] Implement transcation.
- [x] Implement websocket.
- [x] Write multithread notes.
- [ ] Try kafka.
## Update
+ 2021/9/19: Optimize controller with @Scope annotaion, and implement jms.

+ 2021/9/20: Implement websocket to create an online chatroom (with heart-beat check).
  
  The effect of two browsers.
  
  ![chatroom](https://raw.githubusercontent.com/Okabe-Rintarou-0/web-images/master/books/chatroom.3qfwysfvm7g0.png)

+ 2021/9/24: Implement transaction.

  [Notes](https://github.com/Okabe-Rintarou-0/CatStore/blob/master/notes/Transaction.md)

+ 2021/9/28

  Implement multithread to count daily visit of home page. Has passed jmeter tests.
  
  [Notes](https://github.com/Okabe-Rintarou-0/CatStore/blob/master/notes/Multithread.md)

+ 2021/10/1
  
  Implement redis. (Using jedis pool, for jedis is thought to be faster than redisTemplate.)
  
  [Notes](https://github.com/Okabe-Rintarou-0/CatStore/blob/master/notes/Caching.md)
