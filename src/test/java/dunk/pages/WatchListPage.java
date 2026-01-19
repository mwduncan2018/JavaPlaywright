package dunk.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import dunk.models.WatchListEntry;

public class WatchListPage {
    private final Page page;

    // Locators
    private final String btnAddNewEntry = "text=Add To Watch List";
    private final String tableRows = "tbody tr";

    // Column Selectors (relative to a row)
    private final String colManufacturer = "td:nth-child(1)";
    private final String colModel = "td:nth-child(2)";
    private final String btnEdit = "td:nth-child(3) a:nth-child(1)";
    private final String btnDetails = "td:nth-child(3) a:nth-child(2)";
    private final String btnDelete = "td:nth-child(3) a:nth-child(3)";

    public WatchListPage(Page page) {
        this.page = page;
    }

    // Actions
    public void addNewEntry() {
        page.click(btnAddNewEntry);
    }

    public void edit(WatchListEntry entry) {
        getEntryRow(entry).locator(btnEdit).click();
    }

    public void details(WatchListEntry entry) {
        getEntryRow(entry).locator(btnDetails).click();
    }

    public void delete(WatchListEntry entry) {
        getEntryRow(entry).locator(btnDelete).click();
    }

    // Logic Helpers
    /**
     * Finds the specific row in the Watch List table using filters correctly
     */
    public Locator getEntryRow(WatchListEntry entry) {
        return page.locator(tableRows)
            .filter(new Locator.FilterOptions().setHasText(entry.manufacturer))
            .filter(new Locator.FilterOptions().setHasText(entry.model));
    }

    public boolean isEntryDisplayed(WatchListEntry entry) {
        return getEntryRow(entry).isVisible();
    }
}