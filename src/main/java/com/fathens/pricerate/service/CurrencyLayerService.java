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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CurrencyLayerService {

    private final RestTemplate restTemplate;
    private final PriceRepository priceRepository;
    private final HttpClientConfig httpClientConfig;
    private final String apiUrl = "https://api.currencylayer.com/live";
    private final String accessKey = "68e9c8951b4527a77ef14d598802a1ba";

    public CurrencyLayerService(PriceRepository priceRepository, HttpClientConfig httpClientConfig) {
        this.priceRepository = priceRepository;
        this.httpClientConfig = httpClientConfig;
        this.restTemplate = new RestTemplate();
    }

    public String getPrice(){
        String url = apiUrl + "?access_key=" + accessKey;   //API url

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonResponse = response.getBody();

        if (jsonResponse != null){
            JSONObject jsonObject = new JSONObject(jsonResponse);
            long timestamp = jsonObject.getLong("timestamp");   //timestamp convert to localDateTime
            Instant instant = Instant.ofEpochSecond(timestamp);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            JSONObject rates = jsonObject.getJSONObject("quotes");

            double eurRate = rates.getDouble("USDEUR");
            double usdRate = rates.getDouble("USDTRY");
            double eurRateFinal = (1/eurRate)*usdRate;  //USDEUR convert to EURTRY

            Price usdPrice = new Price("currencylayer.com", localDateTime, "USD", usdRate, usdRate);
            Price eurPrice = new Price("currencylayer.com", localDateTime, "EUR", eurRateFinal, eurRateFinal);

            priceRepository.save(usdPrice); //save price
            priceRepository.save(eurPrice);

            return "Save to Price";
        }
        return "Price not be saved";

    }
    @Scheduled(fixedRate = 7200000) //2 hours scheduled
    public void fetchCurrenyRates() throws IOException, InterruptedException {
        httpClientConfig.getRates("http://localhost:8080/api/currencylayer/rates");
    }


}
