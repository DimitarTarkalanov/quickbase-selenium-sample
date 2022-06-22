package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchModal {
    private static final By MODAL_LOCATOR = By.className("DocSearch-Modal");
    private static final By SEARCH_FIELD_LOCATOR = By.id("docsearch-input");
    private static final By SEARCH_RESULT_LOCATOR = By.cssSelector("#docsearch-list *[id^='docsearch-item-']");
    private final WebDriverWait wait;
    private final WebElement modal;

    public SearchModal(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.modal = wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_LOCATOR));
    }

    public SearchModal search(String criteria) {
        WebElement searchField = modal.findElement(SEARCH_FIELD_LOCATOR);
        searchField.clear();
        searchField.sendKeys(criteria);
        return this;
    }

    public List<String> getResults() {
        List<String> values = new ArrayList<>();
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(modal, SEARCH_RESULT_LOCATOR));
        items.forEach(item -> values.add(item.getText()));

        return values;
    }
}
