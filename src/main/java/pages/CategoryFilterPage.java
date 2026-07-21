package pages;
 
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
public class CategoryFilterPage {
    private WebDriver driver;
    private WaitUtils wait;
 
    public CategoryFilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }
 
    private By filterGroupHeading(String groupName) {
        return By.xpath("//*[normalize-space(text())='" + groupName + "']");
    }
 
    private By filterOptionsInGroup(String groupName) {
        return By.xpath(
            "//label[contains(@class,'ud-form-label') and contains(.,'" + groupName + "')]" +
            "/ancestor::div[contains(@class,'ud-form-group')]" +
            "//label[contains(@class,'ud-toggle-input-container')]"
        );
    }
 
    public void scrollToFilterGroup(String groupName) {

        System.out.println(
                "[STEP] Searching filter group : " + groupName);

        WebElement el =
                wait.waitForVisibility(
                        filterGroupHeading(groupName));

        System.out.println(
                "[PASS] Found filter group : " + groupName);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        el);
    }
 
    /**
     * Extracts every option label + its course count from a filter group
     * (e.g. "Language" -> {"English"=10000, "Español"=886, ...}).
     */
    public Map<String, Integer> extractFilterOptionsWithCounts(String groupName) {
        scrollToFilterGroup(groupName);
        List<WebElement> options = wait.waitForAllVisible(filterOptionsInGroup(groupName));
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