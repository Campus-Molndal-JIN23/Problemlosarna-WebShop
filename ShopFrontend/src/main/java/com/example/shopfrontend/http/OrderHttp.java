package com.example.shopfrontend.http;

import com.example.shopfrontend.models.Order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class OrderHttp {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public List<Order> getAllOrders(String token) throws IOException, ParseException {

        HttpGet request = new HttpGet("http://localhost:8080/webshop/order");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }
        HttpEntity entity = response.getEntity();

        ObjectMapper mapper = new ObjectMapper();
        List<Order> orders = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Order>>() {});
        log.info("getAllOrders: ", orders);
        return orders;
    }

}
