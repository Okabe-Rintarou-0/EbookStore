package com.catstore.serverless.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPurchaseInfo implements Serializable {
    public int purchaseNumber;
    public BigDecimal price;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
