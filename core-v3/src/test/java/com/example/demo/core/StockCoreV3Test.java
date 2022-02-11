package com.example.demo.core;

import static org.assertj.core.api.BDDAssertions.then;

import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.stock.ShoeStockItem;
import com.example.demo.dto.out.stock.Stock;
import com.example.demo.dto.out.stock.StockState;
import java.util.List;
import org.junit.jupiter.api.Test;

class StockCoreV3Test {

    private final StockCore subjectUnderTest = new StockCoreV3();

    @Test
    void should_return_a_stock_with_state_SOME_and_containing_expected_stock() {
        // Given
        var expectedState = StockState.SOME;
        var expectedShoeStock = List.of(
            ShoeStockItem.builder().color(Color.BLACK).size(40).quantity(10).build(),
            ShoeStockItem.builder().color(Color.BLACK).size(41).quantity(0).build(),
            ShoeStockItem.builder().color(Color.BLUE).size(39).quantity(10).build()
        );
        var expectedStock = Stock.builder().state(expectedState).shoes(expectedShoeStock).build();

        // When
        final var response = subjectUnderTest.search();

        // Then
        then(response).isNotNull();
        then(response)
            .usingRecursiveComparison()
            .isEqualTo(expectedStock);
    }
}
