package com.catstore.searching;

import com.catstore.constants.Constant;
import com.catstore.constants.SearchingFields;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LuceneSearcher {
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

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            List<Integer> bookIdList = searchBooksBy(line);
            System.out.println("Get book id list: "+ bookIdList);
        }
    }
}

