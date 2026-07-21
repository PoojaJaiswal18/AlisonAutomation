package pages;
	 
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
	 
	public class HomePage {
	    private WebDriver driver;
	    private WaitUtils wait;
	 
	    private By searchBox=By.xpath("//input[@name=\"q\"]");
		private By udemyBusinessLink=By.xpath("//span[normalize-space()='Udemy Business']");
	 
	    public HomePage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WaitUtils(driver, 15);
	    }
	 
	    public void searchCourse(String keyword) {
	        WebElement box = wait.waitForVisibility(searchBox);
	        box.clear();
	        box.sendKeys(keyword);
	        box.sendKeys(Keys.ENTER);
	        wait.waitForUrlContains("/courses/search/");
	    }
	 
	    public String goToUdemyBusiness() {
	        String originalHandle = driver.getWindowHandle();
	        WebElement link = wait.waitForClickability(udemyBusinessLink);
	        link.click();
	        return originalHandle;
	    }
	}