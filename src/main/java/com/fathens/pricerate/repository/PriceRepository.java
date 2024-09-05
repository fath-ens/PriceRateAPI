package com.fathens.pricerate.repository;

import com.fathens.pricerate.entity.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface PriceRepository extends JpaRepository<Price, Integer>, JpaSpecificationExecutor<Price> {

    List<Price> findTopByCurrencyOrderByRateDateDesc(@Param("currency") String currency);
}
