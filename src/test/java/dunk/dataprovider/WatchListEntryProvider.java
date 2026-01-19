package dunk.dataprovider;

import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dunk.models.WatchListEntry;

public class WatchListEntryProvider {
    private List<WatchListEntry> entries;

    public WatchListEntryProvider() {
        loadData();
    }

    private void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/testdata/watch-list-test-data.json");
            
            if (is == null) {
                throw new RuntimeException("Could not find watch-list-test-data.json at /testdata/");
            }
            
            this.entries = mapper.readValue(is, new TypeReference<List<WatchListEntry>>() {});
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("Failed to load Watch List data", e);
        }
    }

    public WatchListEntry getEntryById(String id) {
        return entries.stream()
                .filter(e -> e.id != null && e.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No watch list entry found with ID: " + id));
    }

    // New helper to find by Manufacturer/Model since Symbol is gone
    public WatchListEntry getEntry(String manufacturer, String model) {
        return entries.stream()
                .filter(e -> e.manufacturer.equalsIgnoreCase(manufacturer) 
                          && e.model.equalsIgnoreCase(model))
                .findFirst()
                .orElse(null);
    }
}