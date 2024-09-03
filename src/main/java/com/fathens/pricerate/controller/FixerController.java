package com.fathens.pricerate.controller;

import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import com.fathens.pricerate.service.FixerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/fixer")
public class FixerController {

    private final FixerService fixerService;
    private final PriceRepository priceRepository;
    @Autowired
    public FixerController(FixerService fixerService, PriceRepository priceRepository){
        this.fixerService = fixerService;
        this.priceRepository = priceRepository;
    }
    @GetMapping("/rates")
    public void fixerGetPrice(){
        Map<String, Double> rateMap = fixerService.getPrice();

        Price usdPrice = new Price("fixer.io","USD", rateMap.get("USD"), rateMap.get("USD"));
        Price eurPrice = new Price("fixer.io","EUR", rateMap.get("EUR"), rateMap.get("EUR"));

        priceRepository.save(usdPrice);
        priceRepository.save(eurPrice);
    }
}
