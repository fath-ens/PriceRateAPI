package com.fathens.pricerate.controller;

import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import com.fathens.pricerate.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final PriceService priceService;
    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/allPrice")
    public Page<Price> getAllPrice(@RequestParam("page") int page,
                                   @RequestParam("size") int size,
                                   @RequestParam(required = false) String rateSource,
                                   @RequestParam(required = false) String rateDate,
                                   @RequestParam(required = false) String currency){
        return priceService.getAllPrice(page, size, rateSource, rateDate, currency);
    }

    @GetMapping("/rates/{currency}")
    public List<Price> getCurrencyPrice(@PathVariable("currency") String currency){
        return priceService.getCurrencyPrice(currency);
    }
}
