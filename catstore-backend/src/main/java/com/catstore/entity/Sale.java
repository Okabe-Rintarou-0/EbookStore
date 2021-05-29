package com.catstore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "sales")
public class Sale {
    @Id
    Integer bookId;
    Integer sale;
}
