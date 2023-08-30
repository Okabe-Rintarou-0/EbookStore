package com.catstore.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "book")
public class Book4Neo {
    @Id
    @GeneratedValue
    private Long id;

    private Integer bookId;

    @Relationship(type = "BELONGS_TO")
    private Set<BookTag> tags;

    public void addTags(List<BookTag> tags) {
        if (this.tags == null)
            this.tags = new HashSet<>();
        this.tags.addAll(tags);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
