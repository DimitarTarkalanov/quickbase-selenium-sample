package pages.apidocs;

import org.openqa.selenium.WebDriver;
import pages.PageObject;

public class DragAndDropPage extends PageObject {
    public static final String PAGE_HEADING = "dragAndDrop";
    private static final String PAGE_URL = "docs/api/element/dragAndDrop/";
    private static final String PAGE_TITLE = "dragAndDrop | WebdriverIO";

    public DragAndDropPage(WebDriver driver) {
        super(driver, PAGE_URL, PAGE_TITLE);
    }
}
