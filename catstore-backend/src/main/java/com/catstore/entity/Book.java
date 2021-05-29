package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "bookId")
public class Book {
    @Id
    private Integer bookId;
    private String bookTitle;
    private String bookTag;
    private String bookType;
    private String bookCover;
    private String bookAuthor;
    private Integer bookStock;
    private BigDecimal bookPrice;
    private String bookDescription;
    private String bookDetails;
}

