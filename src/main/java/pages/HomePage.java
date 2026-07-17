package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class HomePage {
	
	private WebDriver driver;
	private WaitUtils wait;
	
	private By SearchBox=By.xpath("//input[@name=\"q\"]");
	private By UdemyBusinessLink=By.xpath("//span[normalize-space()='Udemy Business']");
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		this.wait=new WaitUtils(driver,15);
	}
	
	public void searchCourse(String Keyword) {
		
		WebElement box=wait.waitForVisibility(SearchBox);
		box.clear();
		box.sendKeys(Keyword);
		box.sendKeys(Keys.ENTER);
		wait.waitForUrlContains("/courses/search/");
	}
	
	public String goToUdemyBusiness() {
		String originalHandle=driver.getWindowHandle();
		WebElement link=wait.waitForClickability(UdemyBusinessLink);
		link.click();
		return originalHandle;
	}
	
	

}
