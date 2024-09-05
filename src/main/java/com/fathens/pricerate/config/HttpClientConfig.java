package com.fathens.pricerate.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Configuration
public class HttpClientConfig {
    private HttpClient httpClient;

    public HttpClientConfig(){
        this.httpClient = HttpClient.newHttpClient();
    }

    public void getRates(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))   //URL
                .GET()  //Request Type
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
