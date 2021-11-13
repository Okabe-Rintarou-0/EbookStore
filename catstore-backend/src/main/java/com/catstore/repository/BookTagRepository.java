package com.catstore.repository;

import com.catstore.entity.BookTag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface BookTagRepository extends Neo4jRepository<BookTag, Long> {
    List<BookTag> findAllByTagIn(List<String> tags);
}
