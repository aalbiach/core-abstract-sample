package com.example.demo.core


import com.example.demo.dto.out.stock.ShoeStockItem
import com.example.demo.dto.out.stock.Stock
import com.example.demo.dto.out.stock.StockState
import com.example.demo.entity.Color
import com.example.demo.entity.Shoe
import com.example.demo.repository.StockRepository
import spock.lang.Specification

import static org.assertj.core.api.BDDAssertions.then

class StockCoreV3Test extends Specification {

    private repository       = Mock(StockRepository)
    private subjectUnderTest = new StockCoreV3(repository)

    def "should return a stock with state SOME and containing expected stock"() {
        def expectedState = StockState.SOME
        def expectedShoeStock = List.of(
            ShoeStockItem.builder().color(Color.BLACK).size(40).quantity(10).build(),
            ShoeStockItem.builder().color(Color.BLACK).size(41).quantity(0).build(),
            ShoeStockItem.builder().color(Color.BLUE).size(39).quantity(10).build()
        )
        def expectedStock = Stock.builder().state(expectedState).shoes(expectedShoeStock).build()

        when:
        def response = subjectUnderTest.search()

        then:
        with(response) {
            then(it).isNotNull()
            then(it).usingRecursiveComparison().isEqualTo(expectedStock)
        }

        interaction {
            1 * repository.findAll() >> {
                [
                    com.example.demo.entity.Stock.builder()
                        .id(UUID.randomUUID())
                        .quantity(10)
                        .shoe(Shoe.builder()
                            .id(UUID.randomUUID())
                            .color(Color.BLACK)
                            .size(40)
                            .build())
                        .build(),

                    com.example.demo.entity.Stock.builder()
                        .id(UUID.randomUUID())
                        .quantity(0)
                        .shoe(Shoe.builder()
                            .id(UUID.randomUUID())
                            .color(Color.BLACK)
                            .size(41)
                            .build())
                        .build(),

                    com.example.demo.entity.Stock.builder()
                        .id(UUID.randomUUID())
                        .quantity(10)
                        .shoe(Shoe.builder()
                            .id(UUID.randomUUID())
                            .color(Color.BLUE)
                            .size(39)
                            .build())
                        .build(),
                ]
            }
        }

    }
}
