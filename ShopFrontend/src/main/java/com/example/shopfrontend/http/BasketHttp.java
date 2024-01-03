package com.example.shopfrontend.http;

import com.example.shopfrontend.http.utils.HttpUtils;
import com.example.shopfrontend.models.dto.BasketDTO;
import com.example.shopfrontend.models.dto.UpdateBasketDTO;
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

/**
 * this class is used to make requests concerning the basket to the backend api.
 * it uses the HttpUtils class to create payloads for the requests.
 */

@Slf4j
@Service
public class BasketHttp {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final ObjectMapper mapper = new ObjectMapper();

    public BasketDTO getBasket(String token) throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/webshop/basket");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getCode() != 200) {
            return null;
        }
        HttpEntity entity = response.getEntity();

        BasketDTO basket = mapper.readValue(EntityUtils.toString(entity), new TypeReference<BasketDTO>() {});
        return basket;
    }

    public int addProductToBasket(UpdateBasketDTO product, String token) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/basket");

        request.setEntity(HttpUtils.createPayload(product));

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getCode() != 200) {
            return response.getCode();
        }

        return response.getCode();
    }

    public int updateProductQuantityInBasket(UpdateBasketDTO update, String token) throws IOException {
        HttpPut request = new HttpPut("http://localhost:8080/webshop/basket");

        request.setEntity(HttpUtils.createPayload(update));
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        return response.getCode();
    }

    public int removeProductFromBasket(UpdateBasketDTO product, String token) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/basket");

        request.setEntity(HttpUtils.createPayload(product));
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        return response.getCode();
    }

}
