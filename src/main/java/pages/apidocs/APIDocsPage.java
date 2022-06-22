package pages.apidocs;

import org.openqa.selenium.WebDriver;
import pages.PageObject;

public class APIDocsPage extends PageObject {
    private static final String PAGE_URL = "docs/api";
    private static final String PAGE_TITLE = "Introduction | WebdriverIO";

    public APIDocsPage(WebDriver driver) {
        super(driver, PAGE_URL, PAGE_TITLE);
    }

    public APIDocsNavMenu getSideNavMenu() {
        return new APIDocsNavMenu(getDriver());
    }
}
