package com.example.shopfrontend.http;

import com.example.shopfrontend.models.Basket;
import com.example.shopfrontend.models.Order;

import com.example.shopfrontend.models.OrderDTO;
import com.example.shopfrontend.models.OrderQty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
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
    private ObjectMapper mapper = new ObjectMapper();


    //gets all orders for all users
    public List<OrderDTO> getAllOrdersForAll(String token) throws IOException, ParseException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        HttpGet request = new HttpGet("http://localhost:8080/webshop/orders");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }
        HttpEntity entity = response.getEntity();

        List<OrderDTO> orders = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<OrderDTO>>() {});
        log.info("getAllOrders: ", orders);
        return orders;
    }

    public void placeOrder(String token) throws IOException, ParseException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/order");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
        }

        HttpEntity entity = response.getEntity();

        OrderDTO orderRespons = mapper.readValue(EntityUtils.toString(entity), new TypeReference<OrderDTO>() {});
        log.info("createProduct: ", orderRespons);
    }

    public OrderDTO getAllOrdersForOne(String token) throws IOException, ParseException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        HttpGet request = new HttpGet("http://localhost:8080/webshop/order");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }
        HttpEntity entity = response.getEntity();

        OrderDTO orders = mapper.readValue(EntityUtils.toString(entity), new TypeReference<OrderDTO>() {});
        log.info("getAllOrders: ", orders);
        return orders;
    }

}
