package pages;
 
import model.CourseData;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
import java.util.ArrayList;
import java.util.List;
 
public class SearchResultsPage {
    private WebDriver driver;
    private WaitUtils wait;
 
    private By courseCards = By.xpath("//div[@class='condensed-card-module--primary---Djnw']");
    private By courseTitle = By.xpath("//div[@class='card-title-module--clipped--DPJnT']");
    private By courseDuration = By.xpath("//ul[contains(@class,'tag-list-module--list')]//li[3]/div");//if there is  a best seller tage then answer is different due to additional tag
    private By courseRating = By.xpath("//ul[contains(@class,'tag-list-module--list')]//li[2]/div");//same as above
 
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }
 
    /**
     * Clicks a filter checkbox by its visible option text (e.g. "Beginner", "English").
     * Matches the real filter panel: Level/Language are plain headings with checkbox
     * options showing "OptionName (count)" as visible text.
     */
    public void applyFilterByText(String optionText) {

        By option = By.xpath(
            "//label[contains(@class,'ud-toggle-input-container') and contains(.,'" 
            + optionText + "')]"
        );

        WebElement element = wait.waitForVisibility(option);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
 
    public List<CourseData> extractTopCourses(int n) {
        List<WebElement> cards = wait.waitForAllVisible(courseCards);
        List<CourseData> results = new ArrayList<>();
 
        for (int i = 0; i < Math.min(n, cards.size()); i++) {
            WebElement card = cards.get(i);
            String name = safeGetText(card, courseTitle);
            String duration = safeGetText(card, courseDuration);
            String rating = safeGetText(card, courseRating);
            results.add(new CourseData(name, duration, rating));
        }
        return results;
    }
 
    private String safeGetText(WebElement parent, By childLocator) {
        try {
            return parent.findElement(childLocator).getText().trim();
        } catch (Exception e) {
            return "N/A";
        }
    }
}