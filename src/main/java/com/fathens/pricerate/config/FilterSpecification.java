package com.fathens.pricerate.config;

import com.fathens.pricerate.entity.Price;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FilterSpecification {
    public static Specification<Price> hasRateSource(String rateSource){
        return ((root, query, criteriaBuilder) -> {
           if (rateSource == null){
               return criteriaBuilder.conjunction();
           }
           return criteriaBuilder.equal(root.get("rateSource"), rateSource);
        });
    }

    public static Specification<Price> hasCurrancy(String currency){
        return ((root, query, criteriaBuilder) -> {
            if (currency == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("currency"), currency);
        });
    }

    public static Specification<Price> isRateDateBetween(String rateDate){
        return ((root, query, criteriaBuilder) -> {
            if (rateDate == null){
                return criteriaBuilder.conjunction();
            }
            LocalDate localDate = LocalDate.parse(rateDate);  // Parse the date without time
            LocalDateTime startOfDay = localDate.atStartOfDay();  // Start of the day
            LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999999999);  // End of the day
            Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
            Timestamp endTimestamp = Timestamp.valueOf(endOfDay);
            return criteriaBuilder.between(root.get("rateDate"), startTimestamp, endTimestamp);
        });
    }
}
