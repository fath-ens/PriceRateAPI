package com.fathens.pricerate.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "price")
@NoArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rate_source")
    private String rateSource;

    @Column(name = "rate_date")
    private LocalDateTime rateDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "sale_price")
    private Double salePrice;

    public Price(String rateSource, LocalDateTime rateDate, String currency, Double purchasePrice, Double salePrice) {
        this.rateSource = rateSource;
        this.rateDate = rateDate;
        this.currency = currency;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
    }
}
