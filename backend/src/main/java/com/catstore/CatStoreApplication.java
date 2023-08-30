package com.catstore;

import com.catstore.entity.Book4Neo;
import com.catstore.entity.BookTag;
import com.catstore.repository.BookRepository4Neo;
import com.catstore.repository.BookTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@EnableAsync
@EnableScheduling //开启定时任务
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
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

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Autowired
//    private BookRepository bookRepository;

    @Autowired
    private BookTagRepository bookTagRepository;

    @Autowired
    private BookRepository4Neo bookRepository4Neo;

//    @PostConstruct
//    void formBookInfoFiles() throws IOException {
//        System.out.println("called");
//        List<Book> books = bookRepository.findAll();
//
//        File dataDir = new File(LuceneIndexer.DATA_DIR);
//        if (!dataDir.exists()) {
//            dataDir.mkdir();
//        } else if (!dataDir.isDirectory()) {
//            dataDir.delete();
//            dataDir.mkdir();
//        }
//
//        File bookInfoFile = new File(LuceneIndexer.DATA_DIR + "book_infos.txt");
//        if (!bookInfoFile.exists())
//            bookInfoFile.createNewFile();
//
//        FileWriter fileWriter = new FileWriter(bookInfoFile);
//        for (Book book : books) {
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("details", book.getBookDetails());
//            jsonObj.put("bookId", book.getBookId());
//            fileWriter.write(jsonObj.toString() + "\n");
//        }
//
//        fileWriter.close();
//    }

    @PostConstruct
    void prepareForNeo4j() throws IOException {
        String url = "./src/main/resources/neo4j/book.csv";
        BufferedReader br = new BufferedReader(new FileReader(url));
        String line;
        Map<String, BookTag> tagMap = new HashMap<>();
        List<Book4Neo> books = new ArrayList<>();
        while (!(line = br.readLine()).isEmpty()) {
            List<String> tuple = Arrays.asList(line.split(",").clone());
            System.out.println("Read line: " + tuple.toString());
            int bookId = Integer.parseInt(tuple.get(0));
            Book4Neo book = new Book4Neo();
            book.setBookId(bookId);
            List<BookTag> tags = new ArrayList<>();
            for (int i = 1; i < tuple.size(); ++i) {
                String tag = tuple.get(i);
                if (!tagMap.containsKey(tag))
                    tagMap.put(tag, new BookTag(tag));
                tags.add(tagMap.get(tag));
            }
            book.addTags(tags);
            books.add(book);
        }
        for (Map.Entry<String, BookTag> entry : tagMap.entrySet()) {
            bookTagRepository.save(entry.getValue());
        }
        for (Book4Neo book : books) {
            bookRepository4Neo.save(book);
        }
    }
}
