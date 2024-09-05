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

    public static Specification<Price> isRateDateBetween(String rateDate){
        return ((root, query, criteriaBuilder) -> {
            if (rateDate == null){
                return criteriaBuilder.conjunction(); //Null Criteria
            }
            LocalDate localDate = LocalDate.parse(rateDate);
            LocalDateTime startOfDay = localDate.atStartOfDay();  //Start of the day
            LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999999999);  // End of the day
            Timestamp startTimestamp = Timestamp.valueOf(startOfDay);   //Localtime to timestamp
            Timestamp endTimestamp = Timestamp.valueOf(endOfDay);
            return criteriaBuilder.between(root.get("rateDate"), startTimestamp, endTimestamp); //Add criteria
        });
    }
}
