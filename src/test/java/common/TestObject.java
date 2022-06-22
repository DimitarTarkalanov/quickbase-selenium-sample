package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestObject {
    private WebDriver driver;

    @BeforeSuite
    protected void setupTestSuite() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    protected void setUpTest() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
    }

    @AfterMethod
    protected void tearDownTest(ITestResult testResult) {
        if (this.driver != null) {
            if (ITestResult.FAILURE == testResult.getStatus()) {
                takeScreenshot();
            }
            this.driver.quit();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
