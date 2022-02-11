package com.example.demo.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.demo.core.StockCore;
import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.stock.ShoeStockItem;
import com.example.demo.dto.out.stock.Stock;
import com.example.demo.dto.out.stock.StockState;
import com.example.demo.facade.StockFacade;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private StockCore stockCore;

    @Mock
    private StockFacade facade;

    @InjectMocks
    private StockController subjectUnderTest;

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

        given(facade.get(anyInt())).willReturn(stockCore);
        given(stockCore.search()).willReturn(expectedStock);

        // When
        final var response = subjectUnderTest.retrieveStock(3);

        // Then
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isNotNull();
        then(response.getBody())
            .usingRecursiveComparison()
            .isEqualTo(expectedStock);

        verify(facade).get(eq(3));
        verify(stockCore).search();
    }
}
