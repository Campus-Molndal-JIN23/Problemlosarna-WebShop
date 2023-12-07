package com.example.shopfrontend.http;

import com.example.shopfrontend.models.Product;
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


    public List<Product> getAllProducts() throws IOException, ParseException {

        HttpGet request = new HttpGet("http://localhost:8080/webshop/products");

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }
        HttpEntity entity = response.getEntity();

        ObjectMapper mapper = new ObjectMapper();
        List<Product> products = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Product>>() {});
        log.info("getAllProducts: ", products);
        return products;
    }

    public Product getProductById(Long id) throws IOException, ParseException {

        HttpGet request = new HttpGet("http://localhost:8080/webshop/products/"+ id);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));

        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return null;
        }

        HttpEntity entity = response.getEntity();

        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Product>() {});
        log.info("getProductById: ", product);
        return product;
    }

    public Product createProduct(Product product, String token) throws IOException, ParseException {

        HttpPost request = new HttpPost("http://localhost:8080/webshop/products");

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

        Product productRespons = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Product>() {});
        log.info("createProduct: ", productRespons);
        return productRespons;
    }

    public void updateProduct(Product product, String token) throws IOException {

        HttpPut request = new HttpPut("http://localhost:8080/webshop/products/"+ product.getId());

        ObjectMapper mapper = new ObjectMapper();
        StringEntity payload = new StringEntity(mapper.writeValueAsString(product), ContentType.APPLICATION_JSON);

        request.setEntity(payload);
        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 200) {
            log.error("Error uppstod");
            return;
        }
        log.info("Product updated");
    }

    public void deleteProductById(long id, String token) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8080/webshop/products"+ id);

        request.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse response = httpClient.execute(request);
        log.info(String.valueOf(response.getCode()));
        if (response.getCode() != 404) {
            log.error("Error uppstod");
        }
        log.info("Product deleted");

    }
}
