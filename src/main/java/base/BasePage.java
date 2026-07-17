package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;

import utils.ExtentManager;
import utils.ScreenshotUtils;

public class BasePage {
	
	protected WebDriver driver;
	protected static Properties config=new Properties(); 
	// A Java class used to store key-value pairs, commonly for configuration data
	
	
	
	public void loadConfig() throws IOException{
		
		try(FileInputStream fis=new FileInputStream("src/test/resources/config.properties")){
			
			config.load(fis);
		}	
	}
	
	
	public void SetUp() {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(config.getProperty("baseUrl"));
	}
	
	public void tearDown() {
		
		if(driver!= null) {
			driver.quit();
			}	
	}
	
	
	
	

}
