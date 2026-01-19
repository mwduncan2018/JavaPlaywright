package dunk.steps;

import org.testng.Assert;

import com.microsoft.playwright.Locator;

import dunk.context.ScenarioContext;
import io.cucumber.java8.En;

public class SandboxSteps implements En {

	public SandboxSteps(ScenarioContext context) {

		Given("navigation to Duncan Safe App", () -> {
			context.page.navigate("http://localhost:3000");
		});

		When("a product is added", () -> {
			context.page.locator("[data-cy='addNewProductButton']").click();
			context.page.locator("[data-cy='manufacturerInput']").fill("Tesla");
			context.page.locator("[data-cy='modelInput']").fill("Cyber Truck");
			context.page.locator("[data-cy='priceInput']").fill("99000");
			context.page.locator("[data-cy='numberInStockInput']").fill("4321");
			context.page.locator("[data-cy='submitButton']").click();
		});

		Then("the product is displayed in the Product Table", () -> {
			Locator targetRow = context.page.locator("tr").filter(new Locator.FilterOptions().setHasText("Tesla"));
			Assert.assertTrue(targetRow.isVisible(), "Could not find a table row containing 'Tesla'");

			String rowData = targetRow.innerText();
			Assert.assertTrue(rowData.contains("Cyber Truck"), "Missing 'Cyber Truck'");
			Assert.assertTrue(rowData.contains("99000.00"), "Missing '99000.00'");
			Assert.assertTrue(rowData.contains("4321"), "Missing '4321'");
		});
	}

}