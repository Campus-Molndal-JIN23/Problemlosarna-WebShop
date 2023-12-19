package com.example.shopfrontend.http;


import com.example.shopfrontend.models.ProductDTO;
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

@Slf4j
@Service
public class ProductHttp {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper mapper = new ObjectMapper();


    public List<ProductDTO> getAllProducts() throws IOException, ParseException {

        HttpGet request = new HttpGet("http://localhost:8080/webshop/products");

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }
        HttpEntity entity = response.getEntity();

        List<ProductDTO> products = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<ProductDTO>>() {});
        log.info("getAllProducts: ", products);
        return products;
    }

    public ProductDTO getProductById(long id) throws IOException, ParseException {

        HttpGet request = new HttpGet("http://localhost:8080/webshop/products/" + id);


        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }

        HttpEntity entity = response.getEntity();

        ProductDTO productRespons = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ProductDTO>() {});
        log.info("getProductById: ", productRespons);
        return productRespons;
    }

    public int createProduct(ProductDTO product, String token) throws IOException {

        HttpPost request = new HttpPost("http://localhost:8080/webshop/products");

        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);

        request.setEntity(payload);

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return response.getCode();
        }

        HttpEntity entity = response.getEntity();

        log.info("createProduct ");
        return response.getCode();
    }

    public int updateProduct(ProductDTO product, String token) throws IOException {

        HttpPut request = new HttpPut("http://localhost:8080/webshop/products");

        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);

        request.setEntity(payload);
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return response.getCode();
        }

        log.info("Product updated");
        return response.getCode();
    }

    public int deleteProductById(ProductDTO product, String token) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/products");

        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);
        request.setEntity(payload);

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 204) {
            log.error("Error uppstod");
            return response.getCode();
        }
        log.info("Product deleted");
        return response.getCode();

    }
}
