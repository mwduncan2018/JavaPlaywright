package dunk.pages;

import com.microsoft.playwright.Page;
import dunk.models.WatchListEntry;

public class WatchListAddPage {
    private final Page page;

    // Locators
    private final String inputManufacturer = "[data-cy='manufacturerInput']";
    private final String inputModel = "[data-cy='modelInput']";
    private final String btnAdd = "[data-cy='submitButton']";

    public WatchListAddPage(Page page) {
        this.page = page;
    }

    public void addEntry(WatchListEntry entry) {
        page.fill(inputManufacturer, entry.manufacturer);
        page.fill(inputModel, entry.model);
        page.click(btnAdd);
    }
}