package com.example.shopfrontend.http;


import com.example.shopfrontend.models.dto.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * this class is used to make requests concerning the products to the backend api.
 * in many cases the response code is returned to the controller to be used in the frontend.
 */

@Slf4j
@Service
public class ProductHttp {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper mapper = new ObjectMapper();


    public List<ProductDTO> getAllProducts() throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/webshop/products");

        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getCode() != 200) {
            return null;
        }
        HttpEntity entity = response.getEntity();

        List<ProductDTO> products = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<ProductDTO>>() {});
        return products;
    }

    public ProductDTO getProductById(long id) throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/webshop/products/" + id);

        CloseableHttpResponse response = httpClient.execute(request);

        if (response.getCode() != 200) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        return mapper.readValue(EntityUtils.toString(entity), new TypeReference<ProductDTO>() {});
    }

    public int createProduct(ProductDTO product, String token) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080/webshop/products");

        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);

        request.setEntity(payload);

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        return response.getCode();
    }

    public int updateProduct(ProductDTO product, String token) throws IOException {
        HttpPut request = new HttpPut("http://localhost:8080/webshop/products");

        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);

        request.setEntity(payload);
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        return response.getCode();
    }

    public int deleteProductById(ProductDTO product, String token) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/products");

        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);
        request.setEntity(payload);

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);

        return response.getCode();
    }
}
