package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import model.CourseData;
import utils.WaitUtils;

public class SearchResultsPage {
	
	private WebDriver driver;
	private WaitUtils wait;
	
	private By courseCards=By.xpath(null);
	private By courseTitle= By.xpath(null);
	private By courseDuration=By.xpath(null);
	private By courseRating= By.xpath(null);
	private By levelBeginnerCheckBox=By.xpath(null);
	private By languageEnglishCheckBox=By.xpath(null);
	
	public SearchResultsPage(WebDriver driver) {	
		this.driver=driver;
		this.wait=new WaitUtils(driver,15);
	}
	
	public void applyBeginnerFilter() {
		scrollToAndClick(levelBeginnerCheckBox);
	}
	
	 public void applyEnglishFilter() {
	        scrollToAndClick(languageEnglishCheckBox);
	    }
	
	public void scrollToAndClick(By locator) {
		
		WebElement el=wait.waitForVisibility(locator);
		((JavascriptExecutor)driver).executeScript("argument[0].scrollIntoView({block:'center'})",el);
		wait.waitForClickability(locator).click();	
	}
	
	
	public List<CourseData> extractTopCourses(int n){
		List<WebElement> cards =wait.waitForAllVisisble(courseCards);
		List<CourseData> results=new ArrayList<>();
		
		
		for(int i=0;i<Math.min(n, cards.size());i++) {
			WebElement card=cards.get(i);
			String name=safeGetText(card,courseTitle);
			String duration=safeGetText(card,courseDuration);
			String rating=safeGetText(card,courseRating);
			results.add(new CourseData(name,duration,rating));
		}
		
		return results;
	}
	
	
	private String safeGetText(WebElement parent, By childLocator) {
		try {
			return parent.findElement(childLocator).getText().trim();
		}
		catch(Exception e) {
			return "N/A";
		}
	}

}
