package com.catstore.bookservice.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"}, ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "bookId")
public class Book {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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
    private Boolean forSale;
    private Integer sales;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

