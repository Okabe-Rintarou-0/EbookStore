package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class OrderItem {
    @EmbeddedId
    OrderItemId orderItemId;

    @MapsId("bookId")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    Book book;
    Integer purchaseNumber;


    @Embeddable
    @Data
    public static class OrderItemId implements Serializable {
        Integer orderId;
        Integer bookId;
    }
}

