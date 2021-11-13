package com.catstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "tag")
public class BookTag {
    @Id
    @GeneratedValue
    private Long id;
    private String tag;

    public BookTag(String tag) {
        this.tag = tag;
    }
}
