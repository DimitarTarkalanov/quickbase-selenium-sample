package pages.apidocs;

import org.openqa.selenium.WebDriver;
import pages.PageObject;

public class ClickPage extends PageObject {
    public static final String PAGE_HEADING = "click";
    private static final String PAGE_URL = "docs/api/element/click/";
    private static final String PAGE_TITLE = "click | WebdriverIO";

    public ClickPage(WebDriver driver) {
        super(driver, PAGE_URL, PAGE_TITLE);
    }
}
