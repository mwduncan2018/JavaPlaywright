package dunk.hooks;

import java.nio.file.Paths;

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

	public Hooks(ScenarioContext context) {

		Before((scenario) -> {
			System.out.println(">>> Starting Scenario: " + scenario.getName());

			int speed = RECORD_VIDEO ? 250 : 0;
			context.playwright = Playwright.create();
			context.browser = context.playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(speed));
			if (RECORD_VIDEO) {
				String videoPath = "target/videos/" + scenario.getName().replaceAll(" ", "_");
				context.browserContext = context.browser.newContext(new Browser.NewContextOptions()
						.setRecordVideoDir(Paths.get(videoPath)).setRecordVideoSize(1280, 720));
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

			// 1. Capture the video path before closing
			java.nio.file.Path videoPath = context.page.video() != null ? context.page.video().path() : null;

			// 2. Must close context to finish writing the file
			if (context.browserContext != null) {
				context.browserContext.close();
			}
			if (context.browser != null) {
				context.browser.close();
			}
			if (context.playwright != null) {
				context.playwright.close();
			}

			// 3. Rename the file after everything is closed
			if (videoPath != null && RECORD_VIDEO) {
				String friendlyName = scenario.getName().replaceAll(" ", "_") + ".webm";
				java.nio.file.Path finalPath = videoPath.resolveSibling(friendlyName);

				try {
					java.nio.file.Files.move(videoPath, finalPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					System.out.println(">>> Video saved as: " + friendlyName);
				} catch (java.io.IOException e) {
					System.err.println("Could not rename video: " + e.getMessage());
				}
			}
		});
	}
}