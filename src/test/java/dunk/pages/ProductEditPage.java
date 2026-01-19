package dunk.pages;

import com.microsoft.playwright.Page;

public class ProductEditPage {
    private final Page page;

    // Selectors
    private final String x = "[data-cy='x']";

    public ProductEditPage(Page page) {
        this.page = page;
    }

}
