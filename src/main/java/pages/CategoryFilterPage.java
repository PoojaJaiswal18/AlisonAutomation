package pages;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;
 
public class CategoryFilterPage {
    private WebDriver driver;
    private WaitUtils wait;
 
    public CategoryFilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }
 
    private By filterGroupHeader(String groupName) {
        return By.xpath("//div[@data-purpose='" + groupName + "'] | //fieldset[contains(.,'" + groupName + "')]");
    }
 
    private By filterOptionsInGroup(String groupName) {
        return By.xpath("//div[@data-purpose='" + groupName + "']//label | //fieldset[contains(.,'" + groupName + "')]//label");
    }
 
    public void scrollToFilterGroup(String groupName) {
        WebElement el = wait.waitForVisibility(filterGroupHeader(groupName));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
 
    public Map<String, Integer> extractFilterOptionsWithCounts(String groupName) {
        scrollToFilterGroup(groupName);
        List<WebElement> options = wait.waitForAllVisisble(filterOptionsInGroup(groupName));
        Map<String, Integer> optionCounts = new LinkedHashMap<>();
 
        for (WebElement option : options) {
            String rawText = option.getText().trim();
            if (rawText.isEmpty()) continue;
 
            String name = rawText.replaceAll("\\(.*\\)", "").trim();
            String countStr = rawText.replaceAll("[^0-9]", "");
            int count = countStr.isEmpty() ? 0 : Integer.parseInt(countStr);
 
            if (!name.isEmpty()) {
                optionCounts.put(name, count);
            }
        }
        return optionCounts;
    }
}