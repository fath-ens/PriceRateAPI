package com.fathens.pricerate.repository;

import com.fathens.pricerate.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PriceRepository extends JpaRepository<Price, Integer> {

    List<Price> findTopByCurrencyOrderByRateDateDesc(@Param("currency") String currency);
}
