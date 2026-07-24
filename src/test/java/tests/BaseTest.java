package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

	    public static final String BASE_URL = "https://alison.com/";

	    public static final String EXCEL_PATH ="src/test/resources/TestData.xlsx";

	    public static final int EXPLICIT_WAIT_SECONDS = 20;

	    protected WebDriver driver;

	    @BeforeMethod
	    public void setUp() {

	        ChromeOptions options = new ChromeOptions();

	        options.addArguments("--start-maximized");

	        options.addArguments("--disable-blink-features=AutomationControlled");

	        options.addArguments("--disable-notifications");

	        options.addArguments("--disable-popup-blocking");

	        driver = new ChromeDriver(options);

	        driver.manage().window().maximize();

	        driver.get(BASE_URL);

	        System.out.println("====================================");
	        System.out.println("[INFO] Browser Launched");
	        System.out.println("[INFO] URL Opened : " + BASE_URL);
	        System.out.println("====================================");
	    }

	    @AfterMethod
	    public void tearDown() {

	        if (driver != null) {

	            System.out.println( "[INFO] Closing Browser");

	            driver.quit();
	        }
	    }
	

}
