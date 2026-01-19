package dunk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("Id")
    public String id;
    
    @JsonProperty("Manufacturer")
    public String manufacturer;
    
    @JsonProperty("Model")
    public String model;
    
    @JsonProperty("Price")
    public String price;

    @JsonProperty("NumberInStock")
    public String numberInStock;
    
    public Product() {} 
}