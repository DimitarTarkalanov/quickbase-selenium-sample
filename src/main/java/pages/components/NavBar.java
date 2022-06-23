package pages.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavBar {
    private static final By NAV_BAR_LOCATOR = By.className("navbar");
    private static final By SEARCH_BUTTON_LOCATOR = By.className("DocSearch-Button");
    private final WebDriver driver;
    private final WebElement navBarElement;

    public NavBar(WebDriver driver) {
        this.driver = driver;
        this.navBarElement = driver.findElement(NAV_BAR_LOCATOR);
    }

    @Step("Click on Nav Bar menu")
    public void clickOnItem(Item item) {
        navBarElement.findElement(By.linkText(item.getText())).click();
    }

    @Step("Click on Search")
    public SearchModal clickOnSearch() {
        navBarElement.findElement(SEARCH_BUTTON_LOCATOR).click();
        return new SearchModal(driver);
    }

    public enum Item {
        DOCS("Docs"), API("API"), BLOG("Blog"), CONTRIBUTE("Contribute"), COMMUNITY("Community");

        private final String text;

        Item(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
