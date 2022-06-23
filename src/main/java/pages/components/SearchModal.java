package pages.components;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageObjectException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchModal {
    public static final String NO_RESULT_MESSAGE_FORMAT = "No results for \"%s\"";
    private static final By MODAL_LOCATOR = By.className("DocSearch-Modal");
    private static final By SEARCH_FIELD_LOCATOR = By.id("docsearch-input");
    private static final By SEARCH_RESULT_LOCATOR = By.cssSelector("#docsearch-list *[id^='docsearch-item-']");
    private static final By NO_RESULTS_MESSAGE_LOCATOR = By.className("DocSearch-Title");
    private static final By VIEW_ALL_RESULTS_LOCATOR = By.partialLinkText("See all");
    private final WebDriverWait wait;
    private final WebElement modal;

    public SearchModal(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.modal = wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_LOCATOR));
    }

    @Step("Perform search operation")
    public SearchModal search(String criteria) {
        WebElement searchField = modal.findElement(SEARCH_FIELD_LOCATOR);
        searchField.clear();
        searchField.sendKeys(criteria);
        return this;
    }

    private List<WebElement> getResultElements() {
        return wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(modal, SEARCH_RESULT_LOCATOR));
    }

    @Step("Get search results")
    public List<String> getResults() {
        List<String> values = new ArrayList<>();
        List<WebElement> items = getResultElements();
        items.forEach(item -> values.add(item.getText()));

        return values;
    }

    @Step("Click on search result")
    public void clickOnResult(String criteria) {
        getResultElements().stream().filter(result -> StringUtils.endsWithIgnoreCase(result.getText(), criteria)).findFirst()
                .orElseThrow(() -> new PageObjectException(String.format("There is not result that matches criteria:[%s]", criteria))).click();
    }

    @Step("Verify whether results are presented")
    public boolean areResultsPresented() {
        try {
            return !getResultElements().isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Get No Results message")
    public String getNoResultsMessage() {
        return modal.findElement(NO_RESULTS_MESSAGE_LOCATOR).getText();
    }

    @Step("Click on View all results")
    public void clickOnViewAllResults() {
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, VIEW_ALL_RESULTS_LOCATOR)).click();
    }
}
