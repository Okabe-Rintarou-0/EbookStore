package com.catstore.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class User {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer userId;
    private String username;
    private String userSignature;
    private String userIcon;
    private String userTel;
    private Integer userIdentity;
    private String userAddress;
    private BigDecimal userProperty;
    private String email;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
