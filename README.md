# CatStore
CatStore is a simple book store based on React and Spring boot. It's an assignment from lesson SE2321 and SE3353.

## Notes

| Topic                              | Link                                                         | Keywords                               |
| ---------------------------------- | ------------------------------------------------------------ | -------------------------------------- |
| Transaction                        | [Transaction](./notes/Transaction.md)                        | Transaction/Isolation                  |
| Multithread                        | [Multithread](./notes/Multithread.md)                        | Multithread                            |
| Caching                            | [Caching](./notes/Caching.md)                                | Redis/Memcached                        |
| Searching & Web Service            | [Searching & Web Service](./notes/Searching.md)              | Lucene/Solr/Elasticsearch    SOPA&WSDL |
| Micro Service & Serverless         | [Micro Service & Serverless](./notes/Micro%20Service%20%26%20Serverless.md) | Eureka                                 |
| Security                           | [Security](./notes/Security.md)                              | TSL/SSL/HTTPS/etc                      |
| MySQL Optimization                 | [MySQL Optimization](./notes/MySQL%20Optimization.md)        | Index                                  |
| MySQL Backup, Recovery & Partition | [MySQL Backup, Recovery & Partition]()                       | Backup/Partition                       |
| MongoDB & Neo4j                    | [MongoDB & Neo4j]()                                          | MongoDB                                |

## Update Log

+ 2021/9/19

  Optimize controller with @Scope annotaion, and implement jms.

+ 2021/9/20 
  
  Implement websocket to create an online chatroom (with heart-beat check).
  
  Effects: [chatroom](./notes/imgs/chatroom.png)

+ 2021/9/24

   Implement transaction.

+ 2021/9/28

  Implement multithread to count daily visit of home page. Has passed jmeter tests.
  
+ 2021/10/1
  
  Implement redis. (Using jedis pool, for jedis is thought to be faster than redisTemplate.)
  
+ 2021/10/10

  Implement Lucene & Solr to index the book details, making it more flexible to search books. 
  
+ 2021/10/12

  Try elasticsearch & kibana; Implement Web Service by SOAP. (Client codes are in ./catstore-backend/Wsclient)

+ 2021/10/15

  Deploy Eureka, Spring cloud gateway and Feign. Also try to implement serverless functional programming.

+ 2021/11/9

  Implement book comments by MongoDB.

