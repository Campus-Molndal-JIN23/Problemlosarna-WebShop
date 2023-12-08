package com.example.shopfrontend.http;

import com.example.shopfrontend.models.*;
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
public class UserHttp {


    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public LoginResponse loginUser(LoginForm form) throws IOException, ParseException, IOException {

            HttpGet request = new HttpGet("http://localhost:8080/webshop/login");

            request.setEntity(createPayload(form));

            CloseableHttpResponse response = httpClient.execute(request);
            log.info(String.valueOf(response.getCode()));
            if (response.getCode() != 200) {
                log.error("Error uppstod");
                return null;
            }
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            LoginResponse loginResponse = mapper.readValue(EntityUtils.toString(entity), new TypeReference<LoginResponse>() {});
            log.info("loginResponse: " + loginResponse);
            return loginResponse;
    }

    public int registerUser(RegistrationForm form) throws IOException, ParseException, IOException {

        HttpGet request = new HttpGet("http://localhost:8080/webshop/register");

        request.setEntity(createPayload(form));

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return response.getCode();
        }
        HttpEntity entity = response.getEntity();
        log.info("entity: " + entity);
        return response.getCode();
    }

    public StringEntity createPayload(Object object) throws JsonProcessingException {
        //Skapa och inkludera en Payload till request
        ObjectMapper mapper = new ObjectMapper();
        StringEntity payload = new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);

        return payload;
    }


    //---------------- Basket http methods ----------------
    public Basket getBasket(String userId) throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/webshop/basket?userId=" + userId);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error occurred while fetching the basket");
            return null;
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(EntityUtils.toString(entity), Basket.class);
    }

    public int addProductToBasket(String userId, Product product) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/basket?userId=" + userId);

        request.setEntity(createPayload(product));

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        return response.getCode();
    }

    public void updateProductQuantityInBasket(String userId, Product product) throws IOException {
        HttpPut request = new HttpPut("http://localhost:8080/webshop/basket?userId=" + userId);

        request.setEntity(createPayload(product));

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
    }

    public void removeProductFromBasket(String userId, Long productId) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/basket?userId=" + userId + "&productId=" + productId);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
    }
}