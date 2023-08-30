package com.catstore.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo implements Serializable {
    public String receiver;
    public String tel;
    public String address;
    public List<OrderItemInfo> items;
    public BigDecimal userProperty;
    public Integer userId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
