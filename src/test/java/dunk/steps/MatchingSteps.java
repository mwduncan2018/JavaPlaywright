package dunk.steps;

import org.testng.Assert;

import dunk.config.ConfigReader;
import dunk.context.ScenarioContext;
import dunk.models.Product;
import dunk.models.WatchListEntry;
import io.cucumber.java8.En;

public class MatchingSteps implements En {

	public MatchingSteps(ScenarioContext context) {

		Given("a product is added with manufacturer {string} and model {string}",
				(String manufacturer, String model) -> {
					Product product = new Product();
					product.manufacturer = manufacturer;
					product.model = model;
					product.numberInStock = "1";
					product.price = "1";

					// Page interactions via ScenarioContext
					context.navbarPage.goToProductList();
					context.productPage.addNewProduct();
					context.productAddPage.addProduct(product);

					context.set("product", product);
				});

		And("an entry is added with manufacturer {string} and model {string}", (String manufacturer, String model) -> {
			WatchListEntry entry = new WatchListEntry();
			entry.manufacturer = manufacturer;
			entry.model = model;

			context.navbarPage.goToWatchList();
			context.watchListPage.addNewEntry();
			context.watchListAddPage.addEntry(entry);

			context.set("entry", entry);
		});

		When("fuzzy matching is enabled", () -> {
			context.navbarPage.goToProductList();
			context.productPage.enableFuzzyMatching();
		});

		When("standard matching is enabled", () -> {
			context.navbarPage.goToProductList();
			context.productPage.enableStandardMatching();
		});

		Then("the product with manufacturer {string} and model {string} is a standard match",
				(String manufacturer, String model) -> {
					Product product = (Product) context.get("product");

					boolean isMatch = context.productPage.isProductAMatch(product);
					Assert.assertTrue(isMatch,
							"Expected standard match (Column 1) to be checked for: " + manufacturer + " " + model);
				});

		Then("the product with manufacturer {string} and model {string} is a fuzzy match",
				(String manufacturer, String model) -> {
					Product product = (Product) context.get("product");

					boolean isFuzzy = context.productPage.isProductAFuzzyMatch(product);
					Assert.assertTrue(isFuzzy,
							"Expected fuzzy match (Column 7) to be checked for: " + manufacturer + " " + model);
				});

		Then("the product with manufacturer {string} and model {string} is not a match",
				(String manufacturer, String model) -> {
					Product product = (Product) context.get("product");

					boolean isMatch = context.productPage.isProductAMatch(product);
					boolean isFuzzy = context.productPage.isProductAFuzzyMatch(product);

					Assert.assertFalse(isMatch, "Expected standard match to be UNCHECKED");
					Assert.assertFalse(isFuzzy, "Expected fuzzy match to be UNCHECKED");
				});
	}
}