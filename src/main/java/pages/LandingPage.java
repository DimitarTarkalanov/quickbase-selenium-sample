package pages;

import org.openqa.selenium.WebDriver;

public class LandingPage extends PageObject {
    public static final String LANDING_PAGE_URL = "";
    private static final String LANDING_PAGE_TITLE = "WebdriverIO · Next-gen browser and mobile automation test framework for Node.js | WebdriverIO";

    public LandingPage(WebDriver driver) {
        super(driver, LANDING_PAGE_URL, LANDING_PAGE_TITLE);
    }
}
