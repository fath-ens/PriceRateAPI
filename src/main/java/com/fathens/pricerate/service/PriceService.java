package com.fathens.pricerate.service;

import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PriceService {

    private final RestTemplate restTemplate;
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.restTemplate = new RestTemplate();
        this.priceRepository = priceRepository;
    }

    public List<Price> getAllPrice() {
        return priceRepository.findAll();
    }

    public List<Price> getCurrencyPrice(String currency) {
        return priceRepository.findTopByCurrencyOrderByRateDateDesc(currency);
    }
}
