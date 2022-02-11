package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.stock.ShoeStockItem;
import com.example.demo.dto.out.stock.Stock;
import com.example.demo.dto.out.stock.StockState;
import java.util.List;

@Implementation(version = 3)
public class StockCoreV3 extends AbstractStockCore implements StockCore {

    @Override
    public Stock search() {
        return Stock.builder()
            .state(StockState.SOME)
            .shoes(List.of(
                ShoeStockItem.builder()
                    .color(Color.BLACK)
                    .size(40)
                    .quantity(10)
                    .build(),

                ShoeStockItem.builder()
                    .color(Color.BLACK)
                    .size(41)
                    .quantity(0)
                    .build(),

                ShoeStockItem.builder()
                    .color(Color.BLUE)
                    .size(39)
                    .quantity(10)
                    .build())
            )
            .build();
    }
}
