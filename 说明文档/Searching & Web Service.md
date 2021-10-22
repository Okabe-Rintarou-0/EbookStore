# 说明文档（Searching & Web Service & RESTFUL）

### Searching

全文搜索使用的是Lucene，用于做一些轻量级的搜索工作。

#### 索引流程

+ 把书籍解析成必要的json格式，存入/resources/book_info/book_infos.txt文本中，其主要内容如下所示：

  ```
  {"details":"\"The Top-Down Method of Computer Networks\" is a book published by Machinery Industry Press in 2009. The author is (United States) Cours.  This book is one of the most popular computer network textbooks in the world.","bookId":1}
  {"details":"\"Graphic TCP/IP: 5th Edition\" is a book published by People's Posts and Telecommunications Publishing House in 2013. The author is Takashi Nichitakeshita and others.\r\n ","bookId":2}
  ```

  主要用于存储书籍的详细介绍和书籍的id。

+ 将文本文件解析成文档，并对文档建立索引：

  相关实现位于com.catstore.searching.LuceneIndexer中

  读取并加载stop words

  ```java
  private static CharArraySet readStopWords() {
      CharArraySet stopWordsSet = null;
      try {
          File stopWordsFile = new File(INDEX_DIR + "stop_words.txt");
          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(stopWordsFile), StandardCharsets.UTF_8));
          String line;
          List<String> stopWords = new ArrayList<>();
          while ((line = br.readLine()) != null) {
              stopWords.add(line);
          }
  
          stopWordsSet = new CharArraySet(stopWords, true);
      } catch (IOException e) {
          e.printStackTrace();
      }
  
      System.out.println("Stop words are: " + stopWordsSet.toString());
  
      return stopWordsSet;
  }
  ```

  完成索引工作：

  ```java
  private static void index(String indexDirPath, String filePath) throws IOException {
      Directory indexDir = FSDirectory.open(Paths.get(indexDirPath));
      // 可以自定义stop words
      Analyzer analyzer = new StandardAnalyzer(readStopWords());
  
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
      // 是否生成混合文件（把多个文件集合到一起 会影响效率）
      indexWriterConfig.setUseCompoundFile(false);
  
      IndexWriter writer = new IndexWriter(indexDir, indexWriterConfig);
  
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
      String line;
      while ((line = br.readLine()) != null) {
          System.out.println("Read line: " + line);
          JSONObject jsonObj = JSONObject.parseObject(line);
          Document document = new Document();
          document.add(new Field(SearchingFields.Details, jsonObj.getString(SearchingFields.Details), TextField.TYPE_STORED));
          document.add(new Field(SearchingFields.BookId, jsonObj.getString(SearchingFields.BookId), StringField.TYPE_STORED));
          writer.addDocument(document);
      }
      br.close();
      writer.close();
  }
  ```

+ 使用索引完成搜索工作：

  相关代码位于com.catstore.searching.LuceneSearcher

  ```java
  public static List<Integer> searchBooksBy(String keyword) {
      List<Integer> bookIdList = new ArrayList<>();
      try {
          FSDirectory indexDir = FSDirectory.open(Paths.get(LuceneIndexer.INDEX_DIR));
          IndexReader reader = DirectoryReader.open(indexDir);
          IndexSearcher indexSearcher = new IndexSearcher(reader);
  
          Term term = new Term(SearchingFields.Details, keyword);
          Query query = new TermQuery(term);
          TopDocs topDocs = indexSearcher.search(query, Constant.BOOK_PAGE_SIZE);
          ScoreDoc[] scoreDocs = topDocs.scoreDocs;
          for (ScoreDoc scoreDoc : scoreDocs) {
              Document document = reader.document(scoreDoc.doc);
              bookIdList.add(Integer.valueOf(document.get(SearchingFields.BookId)));
              //                System.out.println(reader.document(scoreDoc.doc));
              //                System.out.println("Explanation:" + (indexSearcher.explain(query, scoreDoc.doc)));
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return bookIdList;
  }
  ```

  和com.catstore.serviceimpl.BookServiceImplement中：

  ```java
  public List<Book> getBooksByKeyword(String keyword) {
      List<Integer> bookIdList = LuceneSearcher.searchBooksBy(keyword);
      List<Book> books = new ArrayList<>();
      System.out.println("Get book id list from lucene: "+ bookIdList);
      for (Integer bookId : bookIdList) {
          books.add(bookDao.getBookById(bookId));
      }
      return books;
  }
  ```

### Web Service

+ Server

  本系统使用基于SOAP的web service

  相关代码位于包com.catstore.webService中，由maven插件xjc自动生成

  xsd文件为/resources/xsd/bookSearch.xsd

+ Client

  client项目文件位于Wsclient文件中（位于catstore-backend文件夹中）

  通过/search路径搜索书籍

  ```java
  @GetMapping
  public List<Book> searchBooksBy(@RequestParam(name = "keyword") String keyword) {
      System.out.println("Read keyword: " + keyword);
      return client.searchBooksBy(keyword);
  }
  ```

