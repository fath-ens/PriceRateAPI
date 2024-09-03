package com.fathens.pricerate.repository;

import com.fathens.pricerate.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PriceRepository extends JpaRepository<Price, Integer> {
}
