package com.catstore.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "user_consumption")
public class Consumption {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    Integer id;
    Integer userId;
    Date consumptionTime;
    BigDecimal consumptionNumber;
}
