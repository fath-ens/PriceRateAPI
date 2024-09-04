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
    private final PriceRepository priceRepository;

    @Autowired
    public CurrencyLayerController(CurrencyLayerService currencyLayerService, PriceRepository priceRepository) {
        this.currencyLayerService = currencyLayerService;
        this.priceRepository = priceRepository;
    }

    @GetMapping("/rates")
    public void currencyGetPrice(){
        Map<String, Double> rateMap = currencyLayerService.getPrice();
        Price usdPrice = new Price("currencylayer.com","USD", rateMap.get("USD"), rateMap.get("USD"));
        Price eurPrice = new Price("currencylayer.com","EUR", rateMap.get("EUR"), rateMap.get("EUR"));

        priceRepository.save(usdPrice);
        priceRepository.save(eurPrice);
    }


}
