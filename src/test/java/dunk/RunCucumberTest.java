package dunk;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		// Feature File location
		features = "src/test/resources/features",

		// Step Definition and Hook location
		glue = { "dunk.steps", "dunk.hooks" },

		// Reporting
		plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" },

		// Run specific tags
		tags = "not @ignore")
public class RunCucumberTest extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	// TestNG Cucumber parallel execution is enabled with DataProvider
	// Number of threads is configured in the pom.xml with dataproviderthreadcount
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeClass
	// Global setup runs once at the beginning of the test run
	public static void globalSetup() {
		System.out.println("*******************************************");
		System.out.println("GLOBAL SETUP");
		System.out.println("*******************************************");
	}

	@AfterClass
	// Global tear down runs once at the end of the test run
	public static void globalTeardown() {
		System.out.println("*******************************************");
		System.out.println("GLOBAL TEAR DOWN");
		System.out.println("*******************************************");
	}
}