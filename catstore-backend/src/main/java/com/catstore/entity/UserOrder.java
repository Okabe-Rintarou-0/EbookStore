package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class UserOrder {
    @Id
    Integer orderId;
    Timestamp orderTime;
    String orderState;
    String orderAddress;
    String orderTel;
    String orderReceiver;
    Integer purchaseNumber;
    Integer bookId;
}
