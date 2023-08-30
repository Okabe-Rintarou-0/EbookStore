package com.catstore.repository;

import com.catstore.entity.Book4Neo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookRepository4Neo extends Neo4jRepository<Book4Neo, Long> {
    @Query(value = "match (t1:tag)--()-->(t2:tag) match (n:book)-[:BELONGS_TO]->(t:tag) where t1.tag in $tags and (t=t1 or t=t2) return n")
    List<Book4Neo> searchByTags(@Param(value = "tags") List<String> tags);

    List<Book4Neo> findAll();
}
