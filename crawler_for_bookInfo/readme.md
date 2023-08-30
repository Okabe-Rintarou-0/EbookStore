# 使用说明

### 1.基本使用方式（人工给定查询关键字）

请准备好src.txt,里面存放书名，一行一个

用pip下载好依赖：

pip install requests

pip install bs4

其余两个txt文件可以不用指定，将会默认生成dst.txt和sql.txt

dst.txt存放爬取的百度百科书籍概述,sql.txt存放生成的sql语句

只需要修改函数crawBookInfo中raw_sql = "..."里面的格式，使其与你的目标sql语句格式一致即可

直接运行run.py即可(python run.py)

### 2.爬取豆瓣图书内容作为查询关键字

把run.py下的USE_CRAWLER设置为TRUE，修改number大小即可。