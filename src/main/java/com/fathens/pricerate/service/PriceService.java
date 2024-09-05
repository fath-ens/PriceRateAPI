package com.fathens.pricerate.service;

import com.fathens.pricerate.config.FilterSpecification;
import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    public Page<Price> getAllPrice(int page, int size, String rateSource, String startDate, String endDate, String currency){
        Pageable pageable = PageRequest.of(page, size);
        Specification<Price> spec = Specification.where(FilterSpecification.hasCurrancy(currency))
                .and(FilterSpecification.hasRateSource(rateSource))
                .and(FilterSpecification.isRateDateBetween(startDate, endDate));
        //Review and creation of criteria in the specification
        return priceRepository.findAll(spec, pageable);
    }

    public List<Price> getCurrencyPrice(String currency) {  //Last currency data
        return priceRepository.findTopByCurrencyOrderByRateDateDesc(currency);
    }

}
