package com.fathens.pricerate.controller;

import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final PriceService priceService;
    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/allPrice")
    public Page<Price> getAllPrice(@RequestParam("page") int page,  //pagination
                                   @RequestParam("size") int size,
                                   @RequestParam(required = false) String rateSource,   //optional filter
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate,
                                   @RequestParam(required = false) String currency){
        return priceService.getAllPrice(page, size, rateSource, startDate, endDate, currency);
    }

    @GetMapping("/rates/{currency}")    //Last currency data
    public List<Price> getCurrencyPrice(@PathVariable("currency") String currency){
        return priceService.getCurrencyPrice(currency);
    }
}
