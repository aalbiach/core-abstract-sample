package com.example.demo.dto.out.stock;

import com.example.demo.dto.in.ShoeFilter.Color;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
public class ShoeStockItem extends StockItem {

    Color   color;
    Integer size;

}
