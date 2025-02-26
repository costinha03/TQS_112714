package TQS.Lab02;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class ProductFinderService {
    private final String API_PRODUCTS = "https://fakestoreapi.com/products";
    private final ISimpleHttpClient httpClient;



    public ProductFinderService(TqsBasicHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetail(int id) throws Exception {
        String response = httpClient.doHttpGet(API_PRODUCTS + "/" + id);
        if (response == null) {
            return Optional.empty();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Product product = objectMapper.readValue(response, Product.class);
            return Optional.of(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
