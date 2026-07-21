package pages;
 
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
 
public class BusinessPage {
    private WebDriver driver;
    private WaitUtils wait;
 
    private By requestDemoBtn = By.xpath("//a[@class=\"section-header single btn-get-started\"]");
 
    public BusinessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }
 
    public void clickRequestDemo() {
        wait.waitForClickability(requestDemoBtn).click();
    }
}