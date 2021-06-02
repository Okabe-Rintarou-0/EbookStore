package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user_order")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
public class UserOrder {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    Integer orderId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    Set<OrderItem> orders;
    Integer userId;
    String orderAddress;
    String orderTel;
    String orderReceiver;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date orderTime;
}
