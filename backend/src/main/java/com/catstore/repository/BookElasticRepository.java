//package com.catstore.repository;
//
//import com.catstore.entity.BookInfo;
//import org.springframework.data.domain.Page;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//public interface BookElasticRepository extends ElasticsearchRepository<BookInfo, String> {
//    Page<BookInfo> findBookInfoByDetailsContains(String keyword);
//}
