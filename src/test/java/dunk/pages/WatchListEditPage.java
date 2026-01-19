package dunk.pages;

import com.microsoft.playwright.Page;

public class WatchListEditPage {
    private final Page page;

    // Selectors
    private final String x = "[data-cy='x']";

    public WatchListEditPage(Page page) {
        this.page = page;
    }

}
