package com.example.demo.dto.out.stock;

import com.example.demo.entity.Color;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShoeStockItem extends StockItem {

    Color   color;
    Integer size;

}
