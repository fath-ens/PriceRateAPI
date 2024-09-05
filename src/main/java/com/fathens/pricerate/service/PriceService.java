package com.fathens.pricerate.service;

import com.fathens.pricerate.config.FilterSpecification;
import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PriceService {

    private final RestTemplate restTemplate;
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.restTemplate = new RestTemplate();
        this.priceRepository = priceRepository;
    }

    public Page<Price> getAllPrice(int page, int size, String rateSource, String rateDate, String currency){
        Pageable pageable = PageRequest.of(page, size);
        Specification<Price> spec = Specification.where(FilterSpecification.hasCurrancy(currency))
                .and(FilterSpecification.hasRateSource(rateSource))
                .and(FilterSpecification.isRateDateBetween(rateDate));

        return priceRepository.findAll(spec, pageable);
    }
    /*
    public Page<Price> getAllPrice(int page, int size, String rateSource, String rateDate, String currency) {
        Pageable pageable = PageRequest.of(page, size);
        List<Price> rateDateList = new ArrayList<>();
        List<Price> rateSourceList = new ArrayList<>();
        List<Price> currencyList = new ArrayList<>();
        Boolean filterStatus = false;
        if(rateDate!= null){
            LocalDate localDate = LocalDate.parse(rateDate);  // Parse the date without time
            LocalDateTime startOfDay = localDate.atStartOfDay();  // Start of the day
            LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999999999);  // End of the day
            Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
            Timestamp endTimestamp = Timestamp.valueOf(endOfDay);
            rateDateList = priceRepository.findByRateDateBetween(startTimestamp, endTimestamp);
            filterStatus = true;
        }
        if(rateSource!=null){
            rateSourceList = priceRepository.findByRateSource(rateSource);
            filterStatus = true;
        }
        if(currency != null){
           //currencyList = priceRepository.findByCurrency(currency, pageable);
            filterStatus = true;
        }
        if(!filterStatus){
            return priceRepository.findAll(pageable);
        }else{
            return priceRepository.findByCurrency(rateSource, pageable);
        }


    }
*/


    public List<Price> getCurrencyPrice(String currency) {
        return priceRepository.findTopByCurrencyOrderByRateDateDesc(currency);
    }
}
