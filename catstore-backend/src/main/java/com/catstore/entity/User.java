package com.catstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
    private Integer userId;
    private String username;
    private String userSignature;
    private String userIcon;
    private String userTel;
    private Integer userIdentity;
    private String userAddress;
    private BigDecimal userProperty;
}
