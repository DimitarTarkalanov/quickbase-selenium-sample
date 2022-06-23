package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.components.NavBar;

import java.time.Duration;

public abstract class PageObject {
    private static final String BASE_URL = "https://webdriver.io/";
    private static final String URL_ATTRIBUTE = "URL";
    private static final String TITLE_ATTRIBUTE = "Title";
    private static final String PAGE_VALIDATION_MESSAGE = "The page %s is incorrect!%nExpected:[%s]%nBut found:[%s]";
    private static final By PAGE_HEADING_LOCATOR = By.tagName("h1");
    private final WebDriver driver;
    private final String pageUrl;
    private final String pageTitle;

    protected PageObject(WebDriver driver, String pageUrl, String pageTitle) {
        this.driver = driver;
        this.pageUrl = BASE_URL.concat(pageUrl);
        this.pageTitle = pageTitle;
    }

    @Step("Validate page attributes")
    public void onPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        validatePageAttribute(URL_ATTRIBUTE, wait.until(ExpectedConditions.urlContains(pageUrl)), pageUrl, driver.getCurrentUrl());
        validatePageAttribute(TITLE_ATTRIBUTE, wait.until(ExpectedConditions.titleIs(pageTitle)), pageTitle, driver.getTitle());
    }

    private void validatePageAttribute(String attribute, boolean isAttributeValid, String expectedValue, String currentValue) {
        if (!isAttributeValid) {
            throw new PageObjectException(String.format(PAGE_VALIDATION_MESSAGE, attribute, expectedValue, currentValue));
        }
    }

    @Step("Navigate to page")
    public void navigateTo() {
        this.driver.get(pageUrl);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public NavBar getNavBar() {
        return new NavBar(driver);
    }

    public String getHeading() {
        return driver.findElement(PAGE_HEADING_LOCATOR).getText();
    }
}