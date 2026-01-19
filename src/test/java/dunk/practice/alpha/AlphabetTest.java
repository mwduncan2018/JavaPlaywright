package dunk.practice.alpha;

import java.nio.file.Paths;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.microsoft.playwright.*;

public class AlphabetTest {

	@Test
	public void test_01_basicNav() {
		// Try-with-resources ensures playwright and browser close automatically
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			// BrowserContext contains info for cookies
			BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1280, 720));
			Page page = context.newPage();
			page.setDefaultTimeout(5000);
			page.navigate("http://localhost:3000");
			Locator title = page.locator("[data-cy='pageTitle']");
			title.waitFor(); // Wait for element to appear in the DOM (currently set to 5 seconds, default 30
								// seconds)
			Assert.assertEquals(title.innerText(), "Product List");
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/test_01_basicNav.png")));
		}
	}

	@Test
	public void test_02_toggleFuzzyMatching() {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();
			page.setDefaultTimeout(5000);
			page.navigate("http://localhost:3000");
			Locator fuzzyBtn = page.locator("[data-cy='fuzzyfuzzy']");
			fuzzyBtn.click();
			Assert.assertEquals(fuzzyBtn.innerText(), "Disable Fuzzy Matching!");
			page.screenshot(
					new Page.ScreenshotOptions().setPath(Paths.get("screenshots/test_02_toggleFuzzyMatching.png")));
		}
	}

	@Test
	public void test_03_addNewProduct() {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
			BrowserContext context = browser
					.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));
			Page page = context.newPage();
			page.setDefaultTimeout(5000);
			page.navigate("http://localhost:3000");

			Locator addNewProductBtn = page.locator("[data-cy='addNewProductButton']");
			addNewProductBtn.click();

			Locator manufacturerInput = page.locator("[data-cy='manufacturerInput']");
			Locator modelInput = page.locator("[data-cy='modelInput']");
			Locator priceInput = page.locator("[data-cy='priceInput']");
			Locator numberInStockInput = page.locator("[data-cy='numberInStockInput']");
			Locator addBtn = page.locator("[data-cy='submitButton']");

			manufacturerInput.fill("Tesla");
			modelInput.fill("Cyber Truck");
			priceInput.fill("99000");
			numberInStockInput.fill("4321");
			addBtn.click();

			// Find the row
			Locator targetRow = page.locator("tr").filter(new Locator.FilterOptions().setHasText("Tesla"));
			Assert.assertTrue(targetRow.isVisible(), "Could not find a table row containing 'Tesla'");

			// Get the text of that row
			String rowData = targetRow.innerText();

			// Assert the text of that row contains the other data
			Assert.assertTrue(rowData.contains("Cyber Truck"), "Missing 'Cyber Truck'");
			Assert.assertTrue(rowData.contains("99000.00"), "Missing '99000.00'");
			Assert.assertTrue(rowData.contains("4321"), "Missing '4321'");

			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/test_03_addNewProduct.png")));
			context.close(); // Manually close to ensure no video corruption
			page.video().saveAs(Paths.get("videos/test_03_addNewProduct.webm")); // Save with a specific name
			page.video().delete(); // Delete the random name
		}
	}
}
