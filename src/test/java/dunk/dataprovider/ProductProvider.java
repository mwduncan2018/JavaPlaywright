package dunk.dataprovider;

import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dunk.models.Product;

public class ProductProvider {
    private List<Product> products;

    public ProductProvider() {
        loadData();
    }

    private void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/testdata/product-test-data.json");
            
            if (is == null) {
                throw new RuntimeException("Could not find product-test-data.json at src/test/resources/testdata/");
            }
            
            this.products = mapper.readValue(is, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Product test data from JSON", e);
        }
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product ID " + id + " not found in test data"));
    }

    public Product getProduct(String manufacturer, String model) {
        return products.stream()
                .filter(p -> p.manufacturer.equalsIgnoreCase(manufacturer) 
                          && p.model.equalsIgnoreCase(model))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getAllProducts() {
        return products;
    }
}