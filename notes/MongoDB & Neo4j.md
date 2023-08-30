# MongoDB & Neo4j

### RDBMS

+ 关系型数据库的分布式存储很困难；
+ 对于半结构化和非结构化数据不友好；而非结构化数据在现实世界中非常多（纯文本，图片base64字符串）

### NoSQL

##### MongoDB

+ collection中的document格式可以不同；

+ blog.posts 和 blog.authors只是在语义上表明两个collection的联系，物理上两个东西是完全不同的collection，没有什么关联；

+ Auto-sharding：

  collection被分为很多block，Shard存储block。block会有备份。