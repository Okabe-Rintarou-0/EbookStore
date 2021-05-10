package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class Book {
    @Id
    private Integer bookId;
    private String bookTitle;
    private String bookTag;
    private String bookType;
    private String bookCover;
    private String bookAuthor;
    private Float bookPrice;
    private String bookDescription;
    private String bookDetails;
}

