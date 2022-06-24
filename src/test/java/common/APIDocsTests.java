package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.PageObject;
import pages.SearchPage;
import pages.apidocs.APIDocsNavMenu;
import pages.apidocs.APIDocsPage;
import pages.apidocs.ClickPage;
import pages.apidocs.DragAndDropPage;
import pages.components.NavBar;
import pages.components.SearchModal;

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

    @DataProvider(name = "setupSearchResultNavigation")
    protected Object[][] setupSearchResultNavigation() {

        return new Object[][]{
                {"Click", ClickPage.class, ClickPage.PAGE_HEADING},
                {"draganddrop", DragAndDropPage.class, DragAndDropPage.PAGE_HEADING},
        };
    }

    @Test(dataProvider = "setupSearchResultNavigation", description = "Verify whether user is redirected to the corresponding page after search result selection")
    public void testSearchResultNavigation(String searchCriteria, Class<? extends PageObject> pageType, String expectedHeading) throws Exception {
        WebDriver driver = super.getDriver();

        searchInAPIDocsPage(driver, searchCriteria)
                .clickOnResult(searchCriteria);

        PageObject page = pageType.getConstructor(WebDriver.class).newInstance(driver);
        page.onPage();
        String actualHeading = page.getHeading();
        Assert.assertEquals(actualHeading, expectedHeading);
    }

    @DataProvider(name = "setupSearchNoResults")
    protected Object[][] setupSearchNoResults() {
        return new Object[][]{
                {"43"},
                {"mn"},
                {"дс"},
        };
    }

    @Test(dataProvider = "setupSearchNoResults", description = "Verify whether a message is presented to the user when there are no results for a given search criteria")
    public void testSearchNoResults(String searchCriteria) {
        WebDriver driver = super.getDriver();

        SearchModal searchModal = searchInAPIDocsPage(driver, searchCriteria);
        Assert.assertFalse(searchModal.areResultsPresented(), "Results are presented!");

        String actualMessage = searchModal.getNoResultsMessage();
        String expectedMessage = String.format(SearchModal.NO_RESULT_MESSAGE_FORMAT, searchCriteria);
        Assert.assertEquals(actualMessage, expectedMessage, "The No result message is incorrect!");
    }

    @DataProvider(name = "setupSearchViewAllResults")
    protected Object[][] setupSearchViewAllResults() {

        return new Object[][]{
                {"click"},
                {"draganddrop"},
        };
    }

    @Test(dataProvider = "setupSearchViewAllResults", description = "Verify whether user is able to see all results after a search operation")
    public void testSearchViewAllResults(String searchCriteria) {
        WebDriver driver = super.getDriver();

        searchInAPIDocsPage(driver, searchCriteria).clickOnViewAllResults();
        SearchPage searchPage = new SearchPage(driver, searchCriteria);
        searchPage.onPage();
        Assert.assertTrue(searchPage.areResultsValid(searchCriteria), String.format("The results doesn't match the search criteria:[%s]", searchCriteria));
    }

    private SearchModal searchInAPIDocsPage(WebDriver driver, String searchCriteria) {
        APIDocsPage apiDocsPage = new APIDocsPage(driver);
        apiDocsPage.navigateTo();
        apiDocsPage.onPage();

        return apiDocsPage.getNavBar()
                .clickOnSearch()
                .search(searchCriteria);
    }
}
