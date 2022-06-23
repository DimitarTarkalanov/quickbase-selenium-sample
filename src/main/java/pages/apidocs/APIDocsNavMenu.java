package pages.apidocs;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageObjectException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class APIDocsNavMenu {
    public static final List<String> PROTOCOLS_ITEM_LIST = List.of("WebDriver Protocol", "Appium", "Mobile JSON Wire Protocol", "Chromium", "Firefox", "Sauce Labs", "Selenium Standalone", "JSON Wire Protocol");
    private static final By NAV_MENU_LOCATOR = By.className("menu");
    private static final By NAV_MENU_SECTION_LOCATOR = By.cssSelector(".theme-doc-sidebar-menu > .menu__list-item");
    private static final By NAV_MENU_SECTION_LIST_ITEM_LOCATOR = By.cssSelector("ul > li > a");
    private final WebDriver driver;
    private final WebElement navMenu;

    public APIDocsNavMenu(WebDriver driver) {
        this.driver = driver;
        this.navMenu = driver.findElement(NAV_MENU_LOCATOR);
    }

    private WebElement getSectionElement(Section section) {
        List<WebElement> sections = navMenu.findElements(NAV_MENU_SECTION_LOCATOR);
        for (WebElement sectionElement : sections) {
            if (StringUtils.containsIgnoreCase(sectionElement.getText(), section.name())) {
                return sectionElement;
            }
        }

        throw new PageObjectException(String.format("Section [%s] not found!", section.name()));
    }

    @Step("Get section items list")
    public List<String> getSectionList(Section section) {
        List<String> sectionList = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement sectionElement = getSectionElement(section);
        sectionElement.click();
        List<WebElement> sectionItems = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(sectionElement, NAV_MENU_SECTION_LIST_ITEM_LOCATOR));
        sectionItems.forEach(e -> {
            wait.until(ExpectedConditions.attributeToBe(e, "tabindex", "0"));
            sectionList.add(e.getText());
        });

        return sectionList;
    }

    public enum Section {
        INTRODUCTION, EXPECT, PROTOCOLS, BROWSER, ELEMENT, MOCK
    }
}
