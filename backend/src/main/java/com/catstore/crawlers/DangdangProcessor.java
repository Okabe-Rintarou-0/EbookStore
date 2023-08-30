package com.catstore.crawlers;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DangdangProcessor implements PageProcessor {

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    private final String urlFormat = "http://search.dangdang.com/?key=%S&act=input";

    private String url = "";

    private final List<Map<String, String>> crawledBooks;

    void setBookName(String bookName) {
        url = String.format(urlFormat, bookName);
    }

    String getTargetUrl() {
        return url;
    }

    DangdangProcessor() {
        crawledBooks = new ArrayList<>();
    }

    List<Map<String, String>> getCrawledBooks() {
        return crawledBooks;
    }

    @Override
    public void process(Page page) {
        List<String> bookCoverList = page.getHtml().xpath("//ul[@class='bigimg']/li/a/img").regex("\".*jpg\"").all();
        List<String> bookPriceList = page.getHtml().xpath("//ul[@class='bigimg']/li/p[@class='price']/span[@class='search_now_price']").regex("[0-9]+.[0-9]+").all();
        List<String> bookLinkList = page.getHtml().xpath("//ul[@class='bigimg']/li/a").links().all();
        page.putField("bookCover", bookCoverList);
        page.putField("bookPrice", bookPriceList);
        page.putField("bookLink", bookLinkList);
        crawledBooks.clear();
        for (int i = 0; i < 3; ++i) {
            Map<String, String> bookInfo = new HashMap<>();
            String bookCover = bookCoverList.get(i);
            bookInfo.put("bookCover", bookCover.substring(1, bookCover.length() - 1));
            bookInfo.put("bookPrice", bookPriceList.get(i));
            bookInfo.put("bookLink", bookLinkList.get(i));
            crawledBooks.add(bookInfo);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
