package com.meetup.ms.stock.api;

import com.meetup.ms.contract.stock.StockDetails;
import com.meetup.ms.stock.service.StockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stock")
@RestController
public class StockApi {

    private final StockService stockService;

    public StockApi(StockService stockService){
        this.stockService = stockService;
    }

    @PostMapping
    public StockDetails modifyStock(@RequestBody StockDetails stockDetails){
        return stockService.modifyStock(stockDetails);
    }

    @PostMapping
    @RequestMapping("/revert")
    public StockDetails revertStock(@RequestBody StockDetails stockDetails){
        return stockService.revertStock(stockDetails);
    }
}
