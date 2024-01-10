package com.example.shopfrontend.http;

import com.example.shopfrontend.controller.IndexController;
import com.example.shopfrontend.form.LoginForm;
import com.example.shopfrontend.form.LoginResponse;
import com.example.shopfrontend.form.RegistrationForm;
import com.example.shopfrontend.http.utils.HttpUtils;
import com.example.shopfrontend.models.userDetails.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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


/**
 * this class is used to make requests concerning the users to the backend api.
 * it uses the HttpUtils class to create payloads for the requests.
 * in many cases the response code is returned to the controller to be used in the frontend.
 */
@Slf4j
@Service
public class UserHttp {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper mapper = new ObjectMapper();

    public int loginUser(LoginForm form) throws ParseException, IOException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/auth/login");

        request.setEntity(HttpUtils.createPayload(form));

        CloseableHttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
         if (response.getCode() == 200) {
             LoginResponse loginResponse = mapper.readValue(EntityUtils.toString(entity), new TypeReference<LoginResponse>() {
             });
             log.info("loginResponse: " + loginResponse);
             IndexController.currentUser = loginResponse;
             return response.getCode();
         } else{
             String responsContent = EntityUtils.toString(entity);
             log.info("responsContent: " + responsContent);
         }
        return response.getCode();
    }

    public int registerUser(RegistrationForm form) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/auth/register");

        request.setEntity(HttpUtils.createPayload(form));

        CloseableHttpResponse response = httpClient.execute(request);

        return response.getCode();
    }

    public UserDTO getUserDetails(String token) throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/webshop/user");

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        HttpEntity entity = response.getEntity();
        return mapper.readValue(EntityUtils.toString(entity), new TypeReference<UserDTO>() {
        });
    }
}