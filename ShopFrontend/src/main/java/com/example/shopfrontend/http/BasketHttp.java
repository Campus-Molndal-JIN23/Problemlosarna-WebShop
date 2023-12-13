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

    private ObjectMapper mapper = new ObjectMapper();

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

        Basket basket = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Basket>() {});
        log.info("getBasket: ", basket);
        return basket;
    }


    public Basket addProductToBasket(OrderQty product, String token) throws IOException, ParseException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/basket");

        request.setEntity(createPayload(product));

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

    public void updateProductQuantityInBasket(OrderQty update, String token) throws IOException {
        HttpPut request = new HttpPut("http://localhost:8080/webshop/basket");

        request.setEntity(createPayload(update));
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return;
        }
        log.info("Product updated");
    }

    public void removeProductFromBasket(OrderQty product, String token) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/basket");

        request.setEntity(createPayload(product));
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 404) {
            log.error("Error uppstod");
        }
        log.info("Product deleted");
    }

    public StringEntity createPayload(Object object) throws JsonProcessingException {
        StringEntity payload = new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);

        return payload;
    }
}
