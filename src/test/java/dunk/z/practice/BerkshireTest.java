package dunk.z.practice;

import java.lang.reflect.Method;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BerkshireTest {
	
	/*
	 * ThreadLocal allows each private variable to exist for each thread.
	 * TestNG requires an XML file to configure parallel execution.
	 * Parallel execution is not fully setup here.
	 * When using Cucumber, use picocontainer instead of ThreadLocal.
	 */

	private static ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
	private static ThreadLocal<Browser> browserThread = new ThreadLocal<>();
	private ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
	private ThreadLocal<Page> pageThread = new ThreadLocal<>();

	@BeforeMethod
	public void setup(Method method) {
		Playwright playwright = Playwright.create();
		playwrightThread.set(playwright);

		Browser browser = playwright.chromium()
				.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(250));
		browserThread.set(browser);

		BrowserContext context = browser.newContext(
				new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/temp/" + method.getName())));
		contextThread.set(context);

		Page page = context.newPage();
		page.setDefaultTimeout(5000);
		pageThread.set(page);

		page.navigate("http://localhost:3000");
	}

	@AfterMethod
	public void tearDown(Method method) {
		BrowserContext context = contextThread.get();

		context.close();

		browserThread.get().close();
		playwrightThread.get().close();

		pageThread.remove();
		contextThread.remove();
		browserThread.remove();
		playwrightThread.remove();
	}

	@Test
	public void test_04_basicNav() {
		Page page = pageThread.get();
		Locator title = page.locator("[data-cy='pageTitle']");
		Assert.assertEquals(title.innerText(), "Product List");
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/test_04_basicNav.png")));
	}

	@Test
	public void test_05_toggleFuzzyMatching() {
		Page page = pageThread.get();
		Locator fuzzyBtn = page.locator("[data-cy='fuzzyfuzzy']");
		fuzzyBtn.click();
		Assert.assertEquals(fuzzyBtn.innerText(), "Disable Fuzzy Matching!");
	}

	@Test
	public void test_06_addNewProduct() {
		Page page = pageThread.get();
		page.locator("[data-cy='addNewProductButton']").click();
		page.locator("[data-cy='manufacturerInput']").fill("Tesla");
		page.locator("[data-cy='modelInput']").fill("Cyber Truck");
		page.locator("[data-cy='priceInput']").fill("99000");
		page.locator("[data-cy='numberInStockInput']").fill("4321");
		page.locator("[data-cy='submitButton']").click();

		Locator targetRow = page.locator("tr").filter(new Locator.FilterOptions().setHasText("Tesla"));
		Assert.assertTrue(targetRow.isVisible(), "Could not find a table row containing 'Tesla'");

		String rowData = targetRow.innerText();
		Assert.assertTrue(rowData.contains("Cyber Truck"), "Missing 'Cyber Truck'");
		Assert.assertTrue(rowData.contains("99000.00"), "Missing '99000.00'");
		Assert.assertTrue(rowData.contains("4321"), "Missing '4321'");
	}
}
