package dunk.pages;

import com.microsoft.playwright.Page;
import java.util.List;
import java.util.stream.Collectors;

public class ContactPage {
    private final Page page;

    private final String secretMessage = "#secretMessage";
    private final String githubLink = "#github a";
    private final String skillList = "#skillList li";

    public ContactPage(Page page) {
        this.page = page;
    }

    public String getGitHubHref() {
        return page.getAttribute(githubLink, "href");
    }

    public String getFooterText() {
        return page.locator(secretMessage).innerText();
    }

    public List<String> getAllSkills() {
        return page.locator(skillList).allTextContents()
                   .stream()
                   .map(String::trim)
                   .collect(Collectors.toList());
    }
}