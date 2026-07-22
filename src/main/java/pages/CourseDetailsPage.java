package pages;

import utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
            By.xpath("//*[contains(text(),'Duration')]/following::*[1]");

    private By modules =
            By.xpath("//*[contains(text(),'Modules')]/following::*[1]");

    private By publisher =
            By.xpath("//*[contains(text(),'Publisher')]/following::*[1]");

    private By enrollments =
            By.xpath("//*[contains(text(),'Learners')]/following::*[1]");

    public CourseDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    public Map<String,String> getCourseDetails() {

        Map<String,String> data =
                new LinkedHashMap<>();

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
                getText(modules));

        data.put(
                "Publisher",
                getText(publisher));

        data.put(
                "Enrollments",
                getText(enrollments));

        return data;
    }

    private String getText(By locator) {

        try {

            return wait.waitForVisibility(locator)
                    .getText()
                    .trim();

        } catch(Exception e) {

            return "N/A";
        }
    }
}