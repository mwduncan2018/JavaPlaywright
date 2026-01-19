package dunk.steps;

import java.util.List;
import io.cucumber.java8.En;
import org.testng.Assert;

import dunk.config.ConfigReader;
import dunk.context.ScenarioContext;

public class ContactSteps implements En {

    public ContactSteps(ScenarioContext context) {

        When("the Contact page is viewed", () -> {
            context.page.navigate(ConfigReader.getLocalUrl() + "/contact");
        });

        Then("a GitHub link should be provided", (String expectedUrl) -> {
            String actualUrl = context.contactPage.getGitHubHref();
            Assert.assertEquals(actualUrl, expectedUrl.trim(), "GitHub URL does not match!");
        });

        Then("the following text should display in the footer", (String expectedText) -> {
            String actualText = context.contactPage.getFooterText();
            Assert.assertTrue(actualText.contains(expectedText.trim()), 
                "Footer text did not contain: " + expectedText);
        });

        Then("the following skills should be listed", (io.cucumber.datatable.DataTable table) -> {
            List<String> expectedSkills = table.column(0);
            List<String> actualSkills = context.contactPage.getAllSkills();
            
            for (String expected : expectedSkills) {
                if (expected.equalsIgnoreCase("skills")) continue; 
                
                Assert.assertTrue(actualSkills.contains(expected), 
                    String.format("Expected skill '%s' was not found in the UI list: %s", expected, actualSkills));
            }
        });
    }
}