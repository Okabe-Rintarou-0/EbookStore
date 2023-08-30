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
public class TmallPageProcessor implements PageProcessor {

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    private final String urlFormat = "https://list.tmall.com/search_product.htm?q=%S 书&type=p&spm=a220m.1000858.a2227oh.d100&from=.list.pc_1_searchbutton";

    private String url = "";

    private final List<Map<String, String>> crawledBooks;

    void setBookName(String bookName) {
        url = String.format(urlFormat, bookName);
    }

    String getTargetUrl() {
        return url;
    }

    TmallPageProcessor() {
        crawledBooks = new ArrayList<>();
    }

    List<Map<String, String>> getCrawledBooks() {
        return crawledBooks;
    }

    @Override
    public void process(Page page) {
        List<String> bookCoverList = page.getHtml().xpath("//div[@class='product-iWrap']/div[@class='productImg-wrap']/a[@class='productImg']/img").regex("\".*\"").all();
        List<String> bookPriceList = page.getHtml().xpath("//div[@class='product-iWrap']/p[@class='productPrice']/em[@title]").regex("[0-9]+.[0-9]+").all();
        List<String> bookLinkList = page.getHtml().xpath("//div[@class='product-iWrap']/div[@class='productImg-wrap']/a").links().all();
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