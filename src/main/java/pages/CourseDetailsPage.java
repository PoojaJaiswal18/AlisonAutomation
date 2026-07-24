package pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

public class CourseDetailsPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement title;

    @FindBy(xpath = "//div[contains(@class,'description')]")
    private WebElement description;

    @FindBy(xpath = "//li[.//span[contains(@class,'course-avg_duration')]]")
    private WebElement duration;

    @FindBy(xpath = "//div[@class='l-mods__module-num']")
    private List<WebElement> modules;

    @FindBy(xpath = "//span[@class='course-publisher l-pub__name']")
    private WebElement publisher;

    @FindBy(xpath = "//span[@class='course-enrolled']")
    private WebElement enrollments;

    public CourseDetailsPage(WebDriver driver) {

        super(driver);
    }

    public String getModuleCount() {

        try {

            return String.valueOf(modules.size());

        } catch (Exception e) {

            return "0";
        }
    }

    public Map<String, String> getCourseDetails() {

        Map<String, String> data =
                new LinkedHashMap<>();

        try {

            Thread.sleep(1500);

        } catch (Exception e) {
        }

        data.put("Course",getText(title));

        data.put("Description",getText(description));

        data.put("Duration",getText(duration));

        data.put("Modules",getModuleCount());

        data.put("Publisher",getText(publisher));

        data.put("Enrollments",getText(enrollments));

        System.out.println("[PASS] Course Details Extracted");

        return data;
    }

    private String getText(WebElement element) {

        try {

            scrollIntoView(element);

            try {

                Thread.sleep(500);

            } catch (Exception e) {
            }

            String value = element.getText().trim();

            return value.isEmpty()? "N/A": value;

        } catch (Exception e) {

            return "N/A";
        }
    }
}