package dunk.pages;

import com.microsoft.playwright.Page;

public class WatchListDetailsPage {
    private final Page page;

    // Selectors
    private final String x = "[data-cy='x']";

    public WatchListDetailsPage(Page page) {
        this.page = page;
    }

}
