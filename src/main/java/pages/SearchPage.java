package pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage extends PageObject {
    private static final String PAGE_URL = "search?q=%s";
    private static final String PAGE_TITLE = "Search results for \"%s\" | WebdriverIO";
    private static final By SEARCH_RESULT = By.className("search-result-match");

    public SearchPage(WebDriver driver, String searchCriteria) {
        super(driver, String.format(PAGE_URL, searchCriteria), String.format(PAGE_TITLE, searchCriteria));
    }

    @Step("Verify whether results are valid")
    public boolean areResultsValid(String criteria) {
        List<WebElement> results = super.getDriver().findElements(SEARCH_RESULT);
        for (WebElement result : results) {
            if (!StringUtils.equalsIgnoreCase(result.getText(), criteria)) return false;
        }
        return true;
    }

}
