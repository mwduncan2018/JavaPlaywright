package dunk.pages;

import com.microsoft.playwright.Page;

public class ProductDeletePage {
    private final Page page;

    // Selectors
    private final String x = "[data-cy='x']";

    public ProductDeletePage(Page page) {
        this.page = page;
    }
}
