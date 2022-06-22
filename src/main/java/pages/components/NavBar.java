package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavBar {
    private static final By NAV_BAR_LOCATOR = By.className("navbar");
    private static final By SEARCH_BUTTON_LOCATOR = By.className("DocSearch-Button");
    private final WebDriver driver;
    private final WebElement navBar;

    public NavBar(WebDriver driver) {
        this.driver = driver;
        this.navBar = driver.findElement(NAV_BAR_LOCATOR);
    }

    public void clickOnItem(Item item) {
        navBar.findElement(By.linkText(item.getText())).click();
    }

    public SearchModal clickOnSearch() {
        navBar.findElement(SEARCH_BUTTON_LOCATOR).click();
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
