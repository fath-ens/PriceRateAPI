package com.fathens.pricerate.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FixerService {

    private final RestTemplate restTemplate;

    private final String apiUrl = "http://data.fixer.io/api/latest";
    private final String accessKey = "0a16d537f3cac054df3036e0681ef8db";

    public FixerService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Double> getPrice(){
        String url = apiUrl + "?access_key=" + accessKey+"&symbols=TRY,USD";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String jsonResponse = response.getBody();

        Map<String, Double> rateMap = new HashMap<>();
        if (jsonResponse != null){
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject rates = jsonObject.getJSONObject("rates");

            double eurRate = rates.getDouble("TRY");
            double usdRate = rates.getDouble("USD");
            double usdRateFinal = (1/usdRate)*eurRate;
            rateMap.put("EUR", eurRate);
            rateMap.put("USD", usdRateFinal);

        }
        return rateMap;
    }
}
