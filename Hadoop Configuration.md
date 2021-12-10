# Hadoop Configuration in Windows

+ 下载Hadoop，[镜像源](http://mirror.bit.edu.cn/apache/hadoop/common/) 

  建议下载[稳定版](http://mirror.bit.edu.cn/apache/hadoop/common/stable/)（hadoop-3.3.1.tar.gz）

+ 修改hadoop/bin下的配置文件core-site.xml:

  ```xml
  <configuration>
  	<property>
  		<name>fs.defaultFS</name>
  		<value>hdfs://localhost:9000</value>
  	</property>
  </configuration>
  ```

+ 修改hdfs-site.xml

  ```xml
  <configuration>
  	<property>
  		<name>dfs.replication</name>
  		<value>1</value>
      </property>	
  </configuration>
  ```

+ 进入hadoop/bin, 输入命令：

  ```bash
  hdfs namenode -format
  ```

+ 进入hadoop/sbin，输入命令：

  ```bash
  start-dfs.cmd
  ```

+ 如果出现错误，必须下载与版本对应的**hadoop.dll**和**winutils.exe**。

  前者放到C:/windows/System32和hadoop/bin下，后者放在hadoop/bin下。

  [下载地址](https://github.com/kontext-tech/winutils)

+ 在/sbin下使用jps指令，若出现以下结果

  ![image-20211210153519858](./imgs/jps.png)

  且http://localhost:9870/ 显示如下则配置成功。

<img src="./imgs/hadoop_http.png" alt="image-20211210153611473" style="zoom:50%;" />