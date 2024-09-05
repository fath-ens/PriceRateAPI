package com.fathens.pricerate.controller;

import com.fathens.pricerate.service.FixerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/fixer")
public class FixerController {

    private final FixerService fixerService;
    @Autowired
    public FixerController(FixerService fixerService){
        this.fixerService = fixerService;
    }
    @GetMapping("/rates")   //fixer.io api request
    public String fixerGetPrice(){
        return fixerService.getPrice();
    }
}
