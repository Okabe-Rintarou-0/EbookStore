package com.catstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title:
 * @Package
 * @Description:
 * @date 2021/9/18 14:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemInfo implements Serializable {
    public Integer cartId;
    public Integer bookId;
    public Integer purchaseNumber;
    public BigDecimal bookPrice;
}
