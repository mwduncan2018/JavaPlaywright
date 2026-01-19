package dunk.context;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import dunk.dataprovider.ProductProvider;
import dunk.dataprovider.WatchListEntryProvider;
import dunk.models.Product;
import dunk.models.WatchListEntry;
import dunk.pages.ContactPage;
import dunk.pages.NavbarPage;
import dunk.pages.ProductAddPage;
import dunk.pages.ProductDeletePage;
import dunk.pages.ProductDetailsPage;
import dunk.pages.ProductEditPage;
import dunk.pages.ProductPage;
import dunk.pages.WatchListAddPage;
import dunk.pages.WatchListDeletePage;
import dunk.pages.WatchListDetailsPage;
import dunk.pages.WatchListEditPage;
import dunk.pages.WatchListPage;

public class ScenarioContext {
	
	// Playwright objects
	public Playwright playwright;
	public Browser browser;
	public BrowserContext browserContext;
	public Page page;
	
	// Page objects
	public ContactPage contactPage;
	public NavbarPage navbarPage;
	public ProductPage productPage;
	public ProductAddPage productAddPage;
	public ProductEditPage productEditPage;
	public ProductDeletePage productDeletePage;
	public ProductDetailsPage productDetailsPage;	
	public WatchListPage watchListPage;
	public WatchListAddPage watchListAddPage;
	public WatchListEditPage watchListEditPage;
	public WatchListDeletePage watchListDeletePage;
	public WatchListDetailsPage watchListDetailsPage;
	
	// Data providers
	public ProductProvider productProvider = new ProductProvider();
	public WatchListEntryProvider watchListEntryProvider = new WatchListEntryProvider();
	
	// State storage
	public Product activeProduct;
	public WatchListEntry activeWatchListEntry;
	
    // Map to store scenario data
    private final Map<String, Object> storage = new HashMap<>();

    public void set(String key, Object value) {
        storage.put(key, value);
    }

    public Object get(String key) {
        return storage.get(key);
    }

    public boolean contains(String key) {
        return storage.containsKey(key);
    }
}