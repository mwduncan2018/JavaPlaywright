package dunk.hooks;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.InputStream;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.cucumber.java8.En;
import dunk.config.ConfigReader;
import dunk.context.ScenarioContext;
import dunk.pages.ContactPage;
import dunk.pages.NavbarPage;
import dunk.pages.ProductAddPage;
import dunk.pages.ProductDeletePage;
import dunk.pages.ProductDetailsPage;
import dunk.pages.ProductEditPage;
import dunk.pages.ProductPage;
import dunk.pages.WatchListAddPage;
import dunk.pages.WatchListDeletePage;
import dunk.pages.WatchListDetailsPage;
import dunk.pages.WatchListEditPage;
import dunk.pages.WatchListPage;

public class Hooks implements En {

	public static boolean RECORD_VIDEO = true;
	public static boolean HEADLESS = true;
	public static int EXECUTION_SPEED = 0;

	public Hooks(ScenarioContext context) {

		Before((scenario) -> {
			System.out.println(">>> Starting Scenario: " + scenario.getName());
		
			context.playwright = Playwright.create();
			context.browser = context.playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(HEADLESS).setSlowMo(EXECUTION_SPEED));
			if (RECORD_VIDEO) {
				context.browserContext = context.browser.newContext(new Browser.NewContextOptions()
						.setRecordVideoDir(Paths.get(System.getProperty("user.dir"), "target", "videos")).setRecordVideoSize(1280, 720));
			} else {
				context.browserContext = context.browser.newContext();
			}
			context.page = context.browserContext.newPage();
			context.page.setDefaultTimeout(7000);

			// Page Object Model initialization
			context.productPage = new ProductPage(context.page);
			context.productAddPage = new ProductAddPage(context.page);
			context.productEditPage = new ProductEditPage(context.page);
			context.productDetailsPage = new ProductDetailsPage(context.page);
			context.productDeletePage = new ProductDeletePage(context.page);
			context.watchListPage = new WatchListPage(context.page);
			context.watchListAddPage = new WatchListAddPage(context.page);
			context.watchListEditPage = new WatchListEditPage(context.page);
			context.watchListDetailsPage = new WatchListDetailsPage(context.page);
			context.watchListDeletePage = new WatchListDeletePage(context.page);
			context.contactPage = new ContactPage(context.page);
			context.navbarPage = new NavbarPage(context.page);

			context.page.navigate(ConfigReader.getLocalUrl());
		});

		After((scenario) -> {
			System.out.println(">>> Closing Scenario: " + scenario.getName());
			
			// Screenshot on Failure
			if (scenario.isFailed()) {
				byte[] screenshot = context.page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
				Allure.addAttachment("Failed Scenario Screenshot", new ByteArrayInputStream(screenshot));
			}

			// Capture the video path while the page is still open
			java.nio.file.Path videoPath = (context.page != null && context.page.video() != null) 
											? context.page.video().path() : null;

			// Must close everything in this order to save the video file
			if (context.browserContext != null) {
				context.browserContext.close();
			}
			if (context.browser != null) {
				context.browser.close();
			}
			if (context.playwright != null) {
				context.playwright.close();
			}

			// Attach to Allure from the default location
			if (videoPath != null && RECORD_VIDEO && scenario.isFailed()) {
				try (InputStream is = Files.newInputStream(videoPath)) {
					Allure.addAttachment("Execution Video", "video/webm", is, ".webm");
					System.out.println(">>> Video attached to Allure from source: " + videoPath);
				} catch (java.io.IOException e) {
					System.err.println("Allure video attachment failed: " + e.getMessage());
				}
			}
		});
	}
}