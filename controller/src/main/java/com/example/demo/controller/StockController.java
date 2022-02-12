package com.example.demo.controller;

import com.example.demo.core.StockCore;
import com.example.demo.dto.out.stock.Stock;
import com.example.demo.exception.ApiVersionNotFoundException;
import com.example.demo.facade.StockFacade;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockFacade stockFacade;

    @GetMapping
    public ResponseEntity<Stock> retrieveStock(@RequestHeader Integer version) {
        return Optional.ofNullable(stockFacade.get(version))
            .map(StockCore::search)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ApiVersionNotFoundException(version));
    }

}
