package com.catstore.searching;

import com.alibaba.fastjson.JSONObject;
import com.catstore.constants.LuceneFields;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LuceneIndexer {
    public static final String INDEX_DIR = "./src/main/resources/index/";
    public static final String DATA_DIR = "./src/main/resources/book_info/";

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

    private static void index(String indexDirPath, String filePath) throws IOException {
        String line;
        Directory indexDir = FSDirectory.open(Paths.get(indexDirPath));
        // 可以自定义stop words
        Analyzer analyzer = new StandardAnalyzer(readStopWords());

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        // 是否生成混合文件（把多个文件集合到一起 会影响效率）
        indexWriterConfig.setUseCompoundFile(false);

        IndexWriter writer = new IndexWriter(indexDir, indexWriterConfig);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));//构造一个BufferedReader类来读取文件
        while ((line = br.readLine()) != null) {//使用readLine方法，一次读一行
            System.out.println("Read line: " + line);
            JSONObject jsonObj = JSONObject.parseObject(line);
            Document document = new Document();
            document.add(new Field(LuceneFields.Details, jsonObj.getString(LuceneFields.Details), TextField.TYPE_STORED));
            document.add(new Field(LuceneFields.BookId, jsonObj.getString(LuceneFields.BookId), StringField.TYPE_STORED));
            writer.addDocument(document);
        }
        writer.close();
    }

    public static void main(String[] args) {
        try {
            index(INDEX_DIR, DATA_DIR + "book_infos.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
