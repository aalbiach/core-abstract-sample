package com.example.demo.core;

import com.example.demo.dto.out.stock.ShoeStockItem;
import com.example.demo.dto.out.stock.Stock;
import com.example.demo.dto.out.stock.StockState;
import com.example.demo.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Implementation(version = 3)
@RequiredArgsConstructor
public class StockCoreV3 extends AbstractStockCore implements StockCore {

    private final StockRepository stockRepository;

    @Override
    public Stock search() {
        final var allStock = stockRepository.findAll();

        return Stock.builder()
            .state(calculateStockState(allStock))
            .shoes(stockDomainToDto(allStock))
            .build();
    }

    // TODO: How should stock be calculated?
    private StockState calculateStockState(List<com.example.demo.entity.Stock> stockList) {
        return StockState.SOME;
    }

    private List<ShoeStockItem> stockDomainToDto(List<com.example.demo.entity.Stock> allStock) {
        return allStock.stream()
            .map(item ->
                ShoeStockItem.builder()
                    .size(item.getShoe().getSize())
                    .color(item.getShoe().getColor())
                    .quantity(item.getQuantity())
                    .build())
            .map(ShoeStockItem.class::cast)
            .toList();
    }
}
