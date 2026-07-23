package pages;

import utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class CourseDetailsPage {

    private WebDriver driver;
    private WaitUtils wait;

    private By title =
            By.tagName("h1");

    private By description =
            By.xpath("//div[contains(@class,'description')]");

    private By duration =
            By.xpath("//li[.//span[contains(@class,'course-avg_duration')]]");

    private By modules =
            By.xpath("//div[@class=\"l-mods__module-num\"]");

    private By publisher =
            By.xpath("//span[@class=\"course-publisher l-pub__name\"]");

    private By enrollments =
            By.xpath("//span[@class=\"course-enrolled\"]");

    public CourseDetailsPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }
    
    public String getModuleCount() {

        try {

            List<WebElement> moduleList =
                    driver.findElements(modules);

            return String.valueOf(
                    moduleList.size());

        } catch (Exception e) {

            return "0";
        }
    }

    public Map<String, String> getCourseDetails() {

        Map<String, String> data =
                new LinkedHashMap<>();

        /*
         * Allow page content to load completely
         */

        try {

            Thread.sleep(1500);

        } catch (Exception e) {
        }

        data.put(
                "Course",
                getText(title));

        data.put(
                "Description",
                getText(description));

        data.put(
                "Duration",
                getText(duration));

        data.put(
                "Modules",
                getModuleCount());

        data.put(
                "Publisher",
                getText(publisher));

        data.put(
                "Enrollments",
                getText(enrollments));

        System.out.println(
                "[PASS] Course Details Extracted");

        return data;
    }

    private String getText(By locator) {

        try {

            WebElement element =
                    wait.waitForVisibility(locator);

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            element);

            try {

                Thread.sleep(500);

            } catch (Exception e) {
            }

            String value =
                    element.getText()
                            .trim();

            return value.isEmpty()
                    ? "N/A"
                    : value;

        } catch (Exception e) {

            return "N/A";
        }
    }
}