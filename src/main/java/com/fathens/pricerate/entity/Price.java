package com.fathens.pricerate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "price-rate")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rate-source")
    private String rateSource;

    @Column(name = "rate-date")
    private Date rateDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "purchase-price")
    private BigDecimal purchasePrice;

    @Column(name = "sale-price")
    private BigDecimal salePrice;
}
