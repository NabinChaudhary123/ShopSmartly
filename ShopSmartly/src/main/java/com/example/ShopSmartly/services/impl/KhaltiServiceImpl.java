package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.services.KhaltiService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KhaltiServiceImpl implements KhaltiService {

    private final String khaltiApiURL = "https://a.khalti.com/api/v2/epayment/initiate/";
    private final String khaltiSecretKey = "test_secret_key_6dbe8b98a7504b3ab75be6fbdc8135ed";

    private final RestTemplate restTemplate;

    public KhaltiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String initiatePayment(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Key "+ khaltiSecretKey);

        String requestBody = "{"
                + "\"return_url\": \"https://localhost:4200/order\", "
//                + "\"website_url\": \"https://localhost:4200\", "
                + "\"amount\":  \"1200\", "
                + "\"purchase_order_id\": \"3\", "
               + "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


        System.out.println("Request URL: "+khaltiApiURL);
        System.out.println("Request Headers: "+headers);
        System.out.println("Request Body: "+requestBody);

        try{
            String responseEntity = restTemplate.postForObject(
                    khaltiApiURL,
                    entity,
                    String.class);
            System.out.println("Response Body: "+responseEntity);
            return responseEntity;
        }
        catch (HttpClientErrorException e){
            System.err.println("Http error: "+e.getStatusCode()+" , "+ e.getStatusText());
            return null;

        }

    }
}
