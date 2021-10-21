# MySQL Optimization

+ MySQL存储表默认是一行一行顺序存储，所以为了加快检索速度（让更多的行被加载到内存中），可以分成多个表；
+ 对于TEXT/BLOB, 可以考虑存文件路径；
+ 用前缀建树？
+ 根据搜索需求建立索引；
+ 建立复合索引，增删需要更新索引。
+ OLAP 一列一列存；OLTP 一行一行存。 两者结合：HTAP（Hybrid Transaction and Analytical Process,混合事务和分析处理）；

