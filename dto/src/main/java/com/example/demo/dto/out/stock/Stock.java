package com.example.demo.dto.out.stock;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Stock {

    StockState state;

    List<ShoeStockItem> shoes;

}
