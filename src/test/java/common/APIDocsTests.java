package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.apidocs.APIDocsNavMenu;
import pages.apidocs.APIDocsPage;
import pages.components.NavBar;

import java.util.List;

public class APIDocsTests extends TestObject {

    @DataProvider(name = "setupDataSearchResults")
    public Object[][] setupDataSearchResults() {
        return new Object[][]{
                {"Click", NavBar.Item.API},
        };
    }

    @Test(dataProvider = "setupDataSearchResults", description = "Verify whether user is able to search and receive related results")
    public void testSearchResults(String searchCriteria, NavBar.Item navBarItem) {
        WebDriver driver = super.getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.navigateTo();
        landingPage.onPage();
        landingPage.getNavBar().clickOnItem(navBarItem);

        APIDocsPage apiDocsPage = new APIDocsPage(driver);
        apiDocsPage.onPage();
        List<String> results = apiDocsPage.getNavBar()
                .clickOnSearch()
                .search(searchCriteria)
                .getResults();

        results.forEach(result -> Assert.assertTrue(StringUtils.containsIgnoreCase(result, searchCriteria), String.format("The result [%s] doesn't contain search criteria [%s]%n", result, searchCriteria)));
    }

    @DataProvider(name = "setupDataProtocolsList")
    public Object[][] setupDataProtocolsList() {
        return new Object[][]{
                {APIDocsNavMenu.Section.PROTOCOLS, APIDocsNavMenu.PROTOCOLS_ITEM_LIST},
        };
    }

    @Test(dataProvider = "setupDataProtocolsList", description = "Verify whether the Protocols section in the left navigation menu has the correct sub-sections")
    public void testProtocolsList(APIDocsNavMenu.Section section, List<String> expectedItemList) {
        WebDriver driver = super.getDriver();

        APIDocsPage apiDocsPage = new APIDocsPage(driver);
        apiDocsPage.navigateTo();
        apiDocsPage.onPage();

        APIDocsNavMenu navMenu = apiDocsPage.getSideNavMenu();
        List<String> sectionItemList = navMenu.getSectionList(section);

        Assert.assertEquals(sectionItemList, expectedItemList);
    }
}
