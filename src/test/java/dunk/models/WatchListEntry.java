package dunk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchListEntry {
    @JsonProperty("Id")
    public String id;
    
    @JsonProperty("Manufacturer")
    public String manufacturer;
    
    @JsonProperty("Model")
    public String model;
    
    public WatchListEntry() {}
}