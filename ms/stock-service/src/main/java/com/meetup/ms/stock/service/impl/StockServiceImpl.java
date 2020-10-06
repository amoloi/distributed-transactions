package com.meetup.ms.stock.service.impl;

import com.meetup.ms.contract.stock.StockDetails;
import com.meetup.ms.stock.repository.StockRepository;
import com.meetup.ms.stock.service.StockService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public StockDetails modifyStock(StockDetails stockDetails) {
        var item = stockRepository.findById(stockDetails.getStockId()).get();
        item.setAmount(item.getAmount() - stockDetails.getAmount());

        var modifiedItem = stockRepository.save(item);

        var modifiedDetails = new StockDetails();

        modifiedDetails.setAmount(modifiedItem.getAmount());
        modifiedDetails.setStockId(modifiedItem.getId());

        return modifiedDetails;
    }
}
