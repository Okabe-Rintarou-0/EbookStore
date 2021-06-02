package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
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
}

