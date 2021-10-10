package com.catstore;

import com.alibaba.fastjson.JSONObject;
import com.catstore.entity.Book;
import com.catstore.searching.LuceneIndexer;
import com.catstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@EnableAsync
@EnableScheduling //开启定时任务
@SpringBootApplication
@ServletComponentScan
public class CatStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatStoreApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    void formBookInfoFiles() throws IOException {
        System.out.println("called");
        List<Book> books = bookRepository.findAll();

        File dataDir = new File(LuceneIndexer.DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        } else if (!dataDir.isDirectory()) {
            dataDir.delete();
            dataDir.mkdir();
        }

        File bookInfoFile = new File(LuceneIndexer.DATA_DIR + "book_infos.txt");
        if (!bookInfoFile.exists())
            bookInfoFile.createNewFile();

        FileWriter fileWriter = new FileWriter(bookInfoFile);
        for (Book book : books) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("details", book.getBookDetails());
            jsonObj.put("bookId", book.getBookId());
            fileWriter.write(jsonObj.toString() + "\n");
        }

        fileWriter.close();
    }
}
