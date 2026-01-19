package dunk.pages;

import com.microsoft.playwright.Page;

public class WatchListDeletePage {
    private final Page page;

    // Selectors
    private final String x = "[data-cy='x']";

    public WatchListDeletePage(Page page) {
        this.page = page;
    }

}
