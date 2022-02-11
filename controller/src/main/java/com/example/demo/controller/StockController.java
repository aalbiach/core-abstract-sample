package com.example.demo.controller;

import com.example.demo.dto.out.stock.Stock;
import com.example.demo.facade.StockFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockFacade stockFacade;

    @GetMapping
    public ResponseEntity<Stock> retrieveStock(@RequestHeader Integer version) {
        return ResponseEntity.ok(stockFacade.get(version).search());
    }

}
