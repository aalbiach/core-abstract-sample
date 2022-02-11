package com.example.demo.dto.out.stock;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class StockItem {

    private final Integer quantity;

}
