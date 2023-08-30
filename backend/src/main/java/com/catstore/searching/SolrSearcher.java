package com.catstore.searching;

import com.alibaba.fastjson.JSON;
import com.catstore.constants.SearchingFields;
import com.catstore.utils.searchingUtils.SolrUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SolrSearcher {
    public static List<Integer> searchBooksBy(SolrClient client, String keyword) {
        List<Integer> bookIdList = new ArrayList<>();
        final SolrQuery query = new SolrQuery(SearchingFields.Details + ":" + keyword);
        query.addField(SearchingFields.BookId);
        query.setSort("id", SolrQuery.ORDER.desc);
        try {
            QueryResponse response = client.query(query);
            List<SolrDocument> documents = response.getResults();
            for (SolrDocument document : documents) {
                Integer bookId = Integer.valueOf(document.getFirstValue(SearchingFields.BookId).toString());
                bookIdList.add(bookId);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

        return bookIdList;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            List<Integer> bookIdList = searchBooksBy(SolrUtil.getSolrClient(), line);
            System.out.println("Get book id list: " + bookIdList);
        }
    }
}
