package com.example.shopfrontend.http;

import com.example.shopfrontend.models.Basket;
import com.example.shopfrontend.models.Order;
import com.example.shopfrontend.models.OrderQty;
import com.example.shopfrontend.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
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
public class BasketHttp {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();


    public Basket getBasket(String token) throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/webshop/basket");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }
        HttpEntity entity = response.getEntity();

        ObjectMapper mapper = new ObjectMapper();
        Basket basket = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Basket>() {});
        log.info("getBasket: ", basket);
        return basket;
    }

    //TODO i think we only send the product id, rest gets taken from the token?
    public Basket addProductToBasket(OrderQty product, String token) throws IOException, ParseException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/basket");

        ObjectMapper mapper = new ObjectMapper();
        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);

        request.setEntity(payload);

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }

        HttpEntity entity = response.getEntity();

        Basket basketRespons = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Basket>() {});
        log.info("createProduct: ", basketRespons);
        return basketRespons;
    }

    //TODO await how kristians DTO looks
    public void updateProductQuantityInBasket(String userId, Product product) throws IOException {
        HttpPut request = new HttpPut("http://localhost:8080/webshop/basket?userId=" + userId);

        request.setEntity(createPayload(product));

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
    }

    //TODO i think this only needs the product id, rest gets taken from the token?
    public void removeProductFromBasket(String userId, Long productId) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/basket?userId=" + userId + "&productId=" + productId);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
    }

    public StringEntity createPayload(Object object) throws JsonProcessingException {
        //Skapa och inkludera en Payload till request
        ObjectMapper mapper = new ObjectMapper();
        StringEntity payload = new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);

        return payload;
    }
}
