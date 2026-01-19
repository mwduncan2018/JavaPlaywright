package dunk.pages;

import com.microsoft.playwright.Page;

public class NavbarPage {
    private final Page page;

    // Locators
    private final String productListLink = "text=Product List";
    private final String watchListLink = "text=Watch List";
    private final String contactLink = "text=Contact";

    public NavbarPage(Page page) {
        this.page = page;
    }

    // Actions
    public void goToProductList() {
        page.click(productListLink);
    }

    public void goToWatchList() {
        page.click(watchListLink);
    }

    public void goToContact() {
        page.click(contactLink);
    }
}