package com.catstore.crawlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Map;

@Component
public class BookCrawler {
    TmallPageProcessor tmallPageProcessor;
    DangdangProcessor dangdangProcessor;
    JingdongProcessor jingdongProcessor;

    @Autowired
    void setTmallPageProcessor(TmallPageProcessor tmallPageProcessor) {
        this.tmallPageProcessor = tmallPageProcessor;
    }

    @Autowired
    void setDangdangProcessor(DangdangProcessor dangdangProcessor) {
        this.dangdangProcessor = dangdangProcessor;
    }

    @Autowired
    void setJingdongProcessor(JingdongProcessor jingdongProcessor) {
        this.jingdongProcessor = jingdongProcessor;
    }

    public List<Map<String, String>> crawlBooks(String bookName, String websiteSrc) {
        switch (websiteSrc) {
            case "Tmall":
                System.out.println(bookName);
                tmallPageProcessor.setBookName(bookName);
                System.out.println(tmallPageProcessor.getTargetUrl());
                Spider.create(tmallPageProcessor).addUrl(tmallPageProcessor.getTargetUrl()).thread(5).run();
                return tmallPageProcessor.getCrawledBooks();
            case "Dangdang":
                System.out.println(bookName);
                dangdangProcessor.setBookName(bookName);
                System.out.println(dangdangProcessor.getTargetUrl());
                Spider.create(dangdangProcessor).addUrl(dangdangProcessor.getTargetUrl()).thread(5).run();
                return dangdangProcessor.getCrawledBooks();
            case "Jingdong":
                System.out.println(bookName);
                jingdongProcessor.setBookName(bookName);
                System.out.println(jingdongProcessor.getTargetUrl());
                Spider.create(jingdongProcessor).addUrl(jingdongProcessor.getTargetUrl()).thread(5).run();
                return jingdongProcessor.getCrawledBooks();
        }
        return null;
    }
}
