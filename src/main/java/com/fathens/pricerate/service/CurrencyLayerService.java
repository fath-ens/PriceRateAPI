package com.fathens.pricerate.service;

import com.fathens.pricerate.config.HttpClientConfig;
import org.json.JSONObject;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyLayerService {

    private final RestTemplate restTemplate;

    private final HttpClientConfig httpClientConfig;
    private final String apiUrl = "https://api.currencylayer.com/live";
    private final String accessKey = "68e9c8951b4527a77ef14d598802a1ba";

    public CurrencyLayerService(HttpClientConfig httpClientConfig) {
        this.httpClientConfig = httpClientConfig;
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Double> getPrice(){
        String url = apiUrl + "?access_key=" + accessKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonResponse = response.getBody();

        Map<String, Double> rateMap = new HashMap<>();
        if (jsonResponse != null){
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject rates = jsonObject.getJSONObject("quotes");

            double eurRate = rates.getDouble("USDEUR");
            double usdRate = rates.getDouble("USDTRY");
            double eurRateFinal = (1/eurRate)*usdRate;

            rateMap.put("EUR", eurRateFinal);
            rateMap.put("USD", usdRate);

        }
        return rateMap;

    }
    //@Scheduled(fixedRate = 60000)
    public void fetchCurrenyRates() throws IOException, InterruptedException {
        httpClientConfig.getRates("http://localhost:8080/api/currencylayer/rates");
    }


}
