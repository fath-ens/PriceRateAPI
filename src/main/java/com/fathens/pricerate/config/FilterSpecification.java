package com.fathens.pricerate.config;

import com.fathens.pricerate.entity.Price;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilterSpecification {
    public static Specification<Price> hasRateSource(String rateSource){
        return ((root, query, criteriaBuilder) -> {
           if (rateSource == null){
               return criteriaBuilder.conjunction();    //Null Criteria
           }
           return criteriaBuilder.equal(root.get("rateSource"), rateSource);    //Add Criteria
        });
    }

    public static Specification<Price> hasCurrancy(String currency){
        return ((root, query, criteriaBuilder) -> {
            if (currency == null){
                return criteriaBuilder.conjunction();   //Null Criteria
            }
            return criteriaBuilder.equal(root.get("currency"), currency);   //Add Criteria
        });
    }

    public static Specification<Price> isRateDateBetween(String startDate, String endDate){
        return ((root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null){
                return criteriaBuilder.conjunction(); //Null Criteria
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDateT = LocalDateTime.parse(startDate, formatter);
            LocalDateTime endDateT = LocalDateTime.parse(endDate, formatter);
            return criteriaBuilder.between(root.get("rateDate"), startDateT, endDateT); //Add criteria
        });
    }
}
