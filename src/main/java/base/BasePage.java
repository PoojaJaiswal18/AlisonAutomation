package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
 
public class BasePage {
 
    public static final String BASE_URL = "https://www.udemy.com/";
    public static final String EXCEL_PATH = "src/test/resources/TestData.xlsx";
    public static final int EXPLICIT_WAIT_SECONDS = 15;
 
    protected WebDriver driver;
 
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }
 
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}