package com.example.shopfrontend.http;


import com.example.shopfrontend.models.dto.OrderDTO;
import com.example.shopfrontend.models.dto.OrderDetailsDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * this class is used to make requests concerning the orders to the backend api.

 */

@Slf4j
@Service
public class OrderHttp {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper mapper = new ObjectMapper();


    public OrderDetailsDTO getAllOrdersForAll(String token) throws IOException, ParseException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        HttpGet request = new HttpGet("http://localhost:8080/webshop/orders");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            return null;
        }
        HttpEntity entity = response.getEntity();

        return mapper.readValue(EntityUtils.toString(entity), new TypeReference<OrderDetailsDTO>() {});
    }

    public int placeOrder(String token) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/order");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        return response.getCode();
    }

    public OrderDTO getAllOrdersForOne(String token) throws IOException, ParseException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        HttpGet request = new HttpGet("http://localhost:8080/webshop/order");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getCode() != 200) {
            return null;
        }
        HttpEntity entity = response.getEntity();

        return mapper.readValue(EntityUtils.toString(entity), new TypeReference<OrderDTO>() {});
    }

}
