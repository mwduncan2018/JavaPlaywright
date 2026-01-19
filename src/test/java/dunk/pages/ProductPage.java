package dunk.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Page;
import dunk.models.Product;

public class ProductPage {
	private final Page page;

	// Locators
	private final String btnAddNewProduct = "text=Add New Product";
	private final String btnFuzzyMatching = "#fuzzFuzz";
	private final String tableRows = "tbody tr";

	// Column Selectors (relative to a row)
	private final String colManufacturer = "td:nth-child(2)";
	private final String colModel = "td:nth-child(3)";
	private final String colPrice = "td:nth-child(4)";
	private final String colStock = "td:nth-child(5)";
	private final String btnEdit = "td:nth-child(6) a:nth-child(1)";
	private final String btnDetails = "td:nth-child(6) a:nth-child(2)";
	private final String btnDelete = "td:nth-child(6) a:nth-child(3)";
	private final String chkMatch = "td:nth-child(1) > input";
	private final String chkFuzzyMatch = "td:nth-child(7) > input";

	public ProductPage(Page page) {
		this.page = page;
	}

	// Actions
	public void addNewProduct() {
		page.click(btnAddNewProduct);
	}

	public void enableStandardMatching() {
		if (isFuzzyMatchingEnabled()) {
			page.click(btnFuzzyMatching);
		}
	}

	public void enableFuzzyMatching() {
		if (!isFuzzyMatchingEnabled()) {
			page.click(btnFuzzyMatching);
		}
	}

	public void edit(Product product) {
		getProductRow(product).locator(btnEdit).click();
	}

	public void details(Product product) {
		getProductRow(product).locator(btnDetails).click();
	}

	public void delete(Product product) {
		getProductRow(product).locator(btnDelete).click();
	}

	// Logic Helpers
	public boolean isFuzzyMatchingEnabled() {
		return page.textContent(btnFuzzyMatching).equals("Disable Fuzzy Matching!");
	}

	/**
	 * Locates a specific row based on Manufacturer AND Model
	 */
	public Locator getProductRow(Product product) {
		return page.locator(tableRows)
				.filter(new Locator.FilterOptions().setHas(page.locator(colManufacturer)
						.filter(new Locator.FilterOptions().setHasText(product.manufacturer))))
				.filter(new Locator.FilterOptions()
						.setHas(page.locator(colModel).filter(new Locator.FilterOptions().setHasText(product.model))));
	}

	// Methods for Step Definition Assertions
	public boolean isProductDisplayed(Product product) {
		Locator row = getProductRow(product);
		return row.isVisible() && row.locator(colPrice).textContent().equals(String.valueOf(product.price))
				&& row.locator(colStock).textContent().equals(String.valueOf(product.numberInStock));
	}

	public boolean isProductAMatch(Product product) {
		return getProductRow(product).locator(chkMatch).isChecked();
	}

	public boolean isProductAFuzzyMatch(Product product) {
		return getProductRow(product).locator(chkFuzzyMatch).isChecked();
	}
}