package com.fathens.pricerate.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rate-source")
    private String rateSource;

    @Column(name = "rate-date")
    @CreationTimestamp
    private Date rateDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "purchase-price")
    private Double purchasePrice;

    @Column(name = "sale-price")
    private Double salePrice;

    public Price(String rateSource, String currency, Double purchasePrice, Double salePrice) {
        this.rateSource = rateSource;
        this.currency = currency;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
    }
}
