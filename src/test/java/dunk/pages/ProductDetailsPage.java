package dunk.pages;

import com.microsoft.playwright.Page;

public class ProductDetailsPage {
    private final Page page;

    // Selectors
    private final String x = "[data-cy='x']";

    public ProductDetailsPage(Page page) {
        this.page = page;
    }

}
