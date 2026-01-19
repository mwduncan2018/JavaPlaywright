package dunk.pages;

import com.microsoft.playwright.Page;
import dunk.models.Product;

public class ProductAddPage {
    private final Page page;

    // Locators
    private final String inputManufacturer = "[data-cy='manufacturerInput']";
    private final String inputModel = "[data-cy='modelInput']";
    private final String inputPrice = "[data-cy='priceInput']";
    private final String inputNumberInStock = "[data-cy='numberInStockInput']";
    private final String btnAdd = "[data-cy='submitButton']";

    public ProductAddPage(Page page) {
        this.page = page;
    }

    public void addProduct(Product product) {
        page.fill(inputManufacturer, product.manufacturer);
        page.fill(inputModel, product.model);
        page.fill(inputPrice, String.valueOf(product.price));
        page.fill(inputNumberInStock, String.valueOf(product.numberInStock));
        page.click(btnAdd);
    }
}