package com.catstore.crawlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Map;

@Component
public class BookCrawler {
    TmallPageProcessor tmallPageProcessor;

    @Autowired
    void setTmallPageProcessor(TmallPageProcessor tmallPageProcessor) {
        this.tmallPageProcessor = tmallPageProcessor;
    }

    public List<Map<String, String>> crawlBooks(String bookName) {
        System.out.println(bookName);
        tmallPageProcessor.setBookName(bookName);
        System.out.println(tmallPageProcessor.getTargetUrl());
        Spider.create(tmallPageProcessor).addUrl(tmallPageProcessor.getTargetUrl()).thread(5).run();
        return tmallPageProcessor.getCrawledBooks();
    }
}
