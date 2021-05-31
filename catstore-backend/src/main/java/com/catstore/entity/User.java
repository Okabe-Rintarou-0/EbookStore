package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class User {
    @Id
    private Integer userId;
    private String username;
    private String userSignature;
    private String userIcon;
    private String userTel;
    private Integer userIdentity;
    private String userAddress;
    private BigDecimal userProperty;
}
