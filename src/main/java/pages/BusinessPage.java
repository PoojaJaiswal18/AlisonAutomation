package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitUtils;

public class BusinessPage {
	
	private WebDriver driver;
	private WaitUtils wait;
	
	private By requestDemoBtn=By.xpath(null);
	
	public BusinessPage(WebDriver driver) {
		this.driver=driver;
		this.wait=new WaitUtils(driver,15);
		
	}
	
	
	public void clickRequestDemo() {
		wait.waitForClickability(requestDemoBtn).click();
	}

}
