package com.catstore.searching;

import com.alibaba.fastjson.JSONObject;
import com.catstore.constants.SearchingFields;
import com.catstore.utils.searchingUtils.SolrUtil;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SolrIndexer {
    public static final String INDEX_DIR = "./src/main/resources/index/";
    public static final String DATA_DIR = "./src/main/resources/book_info/";

    private static CharArraySet readStopWords() {
        return null;
    }

    private static void index(String indexDirPath, String filePath) throws IOException, SolrServerException {
        SolrClient client = SolrUtil.getSolrClient();


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("Read line: " + line);
            JSONObject jsonObj = JSONObject.parseObject(line);
            SolrInputDocument document = new SolrInputDocument();
            document.addField(SearchingFields.BookId, jsonObj.getString(SearchingFields.BookId));
            document.addField(SearchingFields.Details, jsonObj.getString(SearchingFields.Details));


            UpdateResponse updateResponse = client.add(document);
            client.commit();
        }
        br.close();
    }

    public static void main(String[] args) {
        try {
            index(INDEX_DIR, DATA_DIR + "book_infos.txt");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }
}
