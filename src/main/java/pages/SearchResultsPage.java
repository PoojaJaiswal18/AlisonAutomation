package pages;

import model.CourseData;
import utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private WaitUtils wait;

    /*
     * FILTERS
     */

    private By languageFilter =
            By.xpath("//div[@data-url-var='translation']");

    private By levelFilter =
            By.xpath("//div[@data-url-var='level']");

    private By languageHeading =
            By.xpath("//div[@data-url-var='translation']//span[contains(@class,'filter-heading')]");

    private By levelHeading =
            By.xpath("//div[@data-url-var='level']//span[contains(@class,'filter-heading')]");

    /*
     * COURSE CARDS
     */

    private By courseCards =
            By.xpath("//div[contains(@class,'card--white')]");

    private By courseTitle =
            By.xpath(".//div[contains(@class,'card__top')]//h3");

    private By courseDuration =
            By.xpath(".//*[contains(text(),'hrs')]");

    private By courseLearners =
            By.xpath(".//*[contains(text(),'learners')]");

    private By moreInfoButton =
            By.xpath(".//a[contains(@class,'card__more')]");

    /*
     * HOVER TOOLTIP
     */

    private By hoverMessage =
            By.xpath("//div[contains(@class,'tooltip') or contains(@class,'popover')]");

    public SearchResultsPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    private void scrollToFilter(By filterLocator) {

        WebElement filter =
                wait.waitForVisibility(filterLocator);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        filter);

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }
    }

    private void expandFilter(
            By filterLocator,
            By headingLocator) {

        scrollToFilter(filterLocator);

        WebElement heading =
                wait.waitForClickability(
                        headingLocator);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        heading);

        try {

            Thread.sleep(800);

        } catch (Exception e) {
        }
    }

    /*
     * LANGUAGE
     */

    public void applyLanguageFilter(
            String language) {

        expandFilter(
                languageFilter,
                languageHeading);

        By option =
                By.xpath(
                        "//div[@data-url-var='translation']//label[" +
                        "contains(translate(normalize-space(.)," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                        "'abcdefghijklmnopqrstuvwxyz'),'" +
                        language.toLowerCase() +
                        "')]");

        WebElement element =
                wait.waitForClickability(option);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element);

        System.out.println(
                "[PASS] Language Applied : "
                        + language);
    }

    /*
     * LEVEL
     */

    public void applyLevelFilter(
            String level) {

        expandFilter(
                levelFilter,
                levelHeading);

        By option =
                By.xpath(
                        "//div[@data-url-var='level']//label[" +
                        "contains(translate(normalize-space(.)," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                        "'abcdefghijklmnopqrstuvwxyz'),'" +
                        level.toLowerCase() +
                        "')]");

        WebElement element =
                wait.waitForClickability(option);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element);

        System.out.println(
                "[PASS] Level Applied : "
                        + level);
    }

    public List<String> getAvailableLanguages() {

        expandFilter(
                languageFilter,
                languageHeading);

        List<WebElement> elements =
                driver.findElements(
                        By.xpath(
                                "//div[@data-url-var='translation']//label"));

        List<String> results =
                new ArrayList<>();

        for (WebElement element : elements) {

            String text =
                    element.getText().trim();

            if (!text.isEmpty()) {

                results.add(text);
            }
        }

        return results;
    }

    public List<String> getAvailableLevels() {

        expandFilter(
                levelFilter,
                levelHeading);

        List<WebElement> elements =
                driver.findElements(
                        By.xpath(
                                "//div[@data-url-var='level']//label"));

        List<String> results =
                new ArrayList<>();

        for (WebElement element : elements) {

            String text =
                    element.getText().trim();

            if (!text.isEmpty()) {

                results.add(text);
            }
        }

        return results;
    }

    /*
     * COURSES
     */

    public void scrollUntilCardsVisible() {

        WebElement card =
                wait.waitForVisibility(courseCards);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        card);
    }

    public List<CourseData> extractVisibleCourses(
            int count) {

        scrollUntilCardsVisible();

        List<WebElement> cards =
                wait.waitForAllVisible(courseCards);

        List<CourseData> courses =
                new ArrayList<>();

        for (int i = 0;
             i < Math.min(count, cards.size());
             i++) {

            WebElement card =
                    cards.get(i);

            courses.add(
                    new CourseData(
                            safeGetText(card, courseTitle),
                            safeGetText(card, courseDuration),
                            safeGetText(card, courseLearners)));
        }

        return courses;
    }

    public String captureHoverMessageFromFirstCourse() {

        WebElement card =
                wait.waitForAllVisible(courseCards)
                        .get(0);

        new Actions(driver)
                .moveToElement(card)
                .perform();

        try {

            return wait.waitForVisibility(
                    hoverMessage)
                    .getText()
                    .trim();

        } catch (Exception e) {

            return "";
        }
    }

    public void openFirstCourseDetails() {

        WebElement card =
                wait.waitForAllVisible(courseCards)
                        .get(0);

        new Actions(driver)
                .moveToElement(card)
                .perform();

        WebElement moreInfo =
                card.findElement(
                        moreInfoButton);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        moreInfo);

        System.out.println(
                "[PASS] Course Opened");
    }

    public String getFirstCourseTitle() {

        return safeGetText(
                wait.waitForAllVisible(courseCards)
                        .get(0),
                courseTitle);
    }

    private String safeGetText(
            WebElement parent,
            By locator) {

        try {

            return parent.findElement(locator)
                    .getText()
                    .trim();

        } catch (Exception e) {

            return "N/A";
        }
    }
}