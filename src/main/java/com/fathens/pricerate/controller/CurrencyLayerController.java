package com.fathens.pricerate.controller;

import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import com.fathens.pricerate.service.CurrencyLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/currencylayer")
public class CurrencyLayerController {

    private final CurrencyLayerService currencyLayerService;
    @Autowired
    public CurrencyLayerController(CurrencyLayerService currencyLayerService) {
        this.currencyLayerService = currencyLayerService;
    }
    @GetMapping("/rates")   //currenclayer.com api request
    public String currencyGetPrice(){
        return currencyLayerService.getPrice();
    }
}
