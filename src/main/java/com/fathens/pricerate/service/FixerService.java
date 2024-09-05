package com.fathens.pricerate.service;

import com.fathens.pricerate.config.HttpClientConfig;
import com.fathens.pricerate.entity.Price;
import com.fathens.pricerate.repository.PriceRepository;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FixerService {

    private final RestTemplate restTemplate;
    private final PriceRepository priceRepository;
    private final HttpClientConfig httpClientConfig;
    private final String apiUrl = "http://data.fixer.io/api/latest";
    private final String accessKey = "0a16d537f3cac054df3036e0681ef8db";


    public FixerService(PriceRepository priceRepository, HttpClientConfig httpClientConfig) {
        this.priceRepository = priceRepository;
        this.httpClientConfig = httpClientConfig;
        this.restTemplate = new RestTemplate();
    }

    public String getPrice() {
        String url = apiUrl + "?access_key=" + accessKey+"&symbols=TRY,USD";    //API URL

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String jsonResponse = response.getBody();

        if (jsonResponse != null){
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String rateDate =  jsonObject.getString("date");    //String dateformat convert to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(rateDate, formatter);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime localDateTime = date.atTime(now.toLocalTime());

            JSONObject rates = jsonObject.getJSONObject("rates");
            double eurRate = rates.getDouble("TRY");
            double usdRate = rates.getDouble("USD");
            double usdRateFinal = (1/usdRate)*eurRate;  //USDEUR convert to USDTRY

            Price usdPrice = new Price("fixer.io", localDateTime, "USD", usdRateFinal, usdRateFinal);
            Price eurPrice = new Price("fixer.io", localDateTime, "EUR", eurRate, eurRate);

            priceRepository.save(usdPrice); //save price
            priceRepository.save(eurPrice);

            return "Save to Price";
        }
        return "Price not be saved";
    }

    @Scheduled(fixedRate = 7200000) //2 hours scheduled
    public void fetchCurrenyRates() throws IOException, InterruptedException {
        httpClientConfig.getRates("http://localhost:8080/api/fixer/rates");
    }
}
