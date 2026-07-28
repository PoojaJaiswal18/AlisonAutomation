package pages;

import model.CourseData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//div[@data-url-var='translation']")
    private WebElement languageFilter;

    @FindBy(xpath = "//div[@data-url-var='level']")
    private WebElement levelFilter;

    @FindBy(xpath = "//div[@data-url-var='translation']//span[contains(@class,'filter-heading')]")
    private WebElement languageHeading;

    @FindBy(xpath = "//div[@data-url-var='level']//span[contains(@class,'filter-heading')]")
    private WebElement levelHeading;

    private final By languageOptions =By.xpath("//div[@data-url-var='translation']//label");

    private final By levelOptions =By.xpath("//div[@data-url-var='level']//label");
    private final By languageFilterLocator =By.xpath("//div[@data-url-var='translation']");

    private final By levelFilterLocator = By.xpath("//div[@data-url-var='level']");

    private final By courseCards =By.xpath("//div[contains(@class,'card--white')]");

    private final By courseTitle =By.xpath(".//div[contains(@class,'card__top')]//h3");

    private final By courseDuration =By.xpath(".//*[contains(text(),'hrs')]");

    private final By courseLearners = By.xpath(".//*[contains(text(),'learners')]");

    private final By moreInfoButton =By.xpath(".//a[contains(@class,'card__more')]");

    public SearchResultsPage(WebDriver driver) {

        super(driver);
    }

    private void scrollToFilter(WebElement filter) {

        wait.waitForVisibility(filter);

        scrollIntoView(filter);

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }
    }

    private void expandFilter( WebElement filter, WebElement heading) {

        scrollToFilter(filter);

        wait.waitForClickability(heading);

        jsClick(heading);

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }
    }

    private void selectOption(By optionsLocator, String value) {

        List<WebElement> options =driver.findElements(optionsLocator);

        for (WebElement option : options) {

            if (option.getText().trim().equalsIgnoreCase(value)) {

                jsClick(option);

                return;
            }
        }

        throw new RuntimeException("Option not found : "+ value);
    }

    public boolean isLanguageFilterPresent() {

        return !driver.findElements( languageFilterLocator).isEmpty();
    }
    public boolean isLevelFilterPresent() {

        return !driver.findElements(levelFilterLocator).isEmpty();
    }
    public void applyLanguageFilter( String language) {

        if (!isLanguageFilterPresent()) {

            System.out.println("[INFO] Language filter not available");

            return;
        }

        expandFilter(languageFilter,languageHeading);

        selectOption(languageOptions,language);

        try {

            Thread.sleep(3000);

        } catch (Exception e) {
        }

        System.out.println("[PASS] Language Applied : "+ language);
    }

    public void applyLevelFilter(
            String level) {

        if (!isLevelFilterPresent()) {

            System.out.println("[INFO] Level filter not available");
            return;
        }

        expandFilter(levelFilter,levelHeading);

        selectOption(levelOptions,level);

        try {

            Thread.sleep(3000);

        } catch (Exception e) {
        }

        System.out.println( "[PASS] Level Applied : " + level);
    }

    public List<String> getAvailableLanguages() {

        expandFilter(languageFilter,languageHeading);

        List<WebElement> elements = driver.findElements(languageOptions);

        List<String> results =new ArrayList<>();

        for (WebElement element : elements) {

            String text =element.getText().trim();

            if (!text.isEmpty()) {
                results.add(text);
            }
        }

        return results;
    }

    public List<String> getAvailableLevels() {

        expandFilter(levelFilter,levelHeading);

        List<WebElement> elements =driver.findElements(levelOptions);

        List<String> results = new ArrayList<>();

        for (WebElement element : elements) {

            String text =element.getText().trim();

            if (!text.isEmpty()) {

                results.add(text);
            }
        }

        return results;
    }

    public void scrollUntilCardsVisible() {

        WebElement firstCard =wait.waitForVisibility(courseCards);

        scrollIntoView(firstCard);
    }

    public List<CourseData> extractVisibleCourses( int count) {

        scrollUntilCardsVisible();

        try {

            Thread.sleep(2000);

        } catch (Exception e) {
        }

        List<WebElement> cards = driver.findElements(courseCards);

        List<CourseData> courses =new ArrayList<>();

        for (int i = 0;i < Math.min(count, cards.size()); i++) {

            WebElement card =cards.get(i);

            courses.add(new CourseData(safeGetText(card,courseTitle),
                            safeGetText(card,courseDuration),
                            safeGetText(card,courseLearners)));
        }

        return courses;
    }

    public void openFirstCourseDetails() {

        List<WebElement> cards =driver.findElements(courseCards);

        if (cards.isEmpty()) {

            throw new RuntimeException("No course cards found");
        }

        WebElement card =cards.get(0);

        scrollIntoView(card);

        new Actions(driver).moveToElement(card).perform();

        WebElement moreInfo =card.findElement(moreInfoButton);

        scrollIntoView(moreInfo);

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }

        jsClick(moreInfo);

        System.out.println("[PASS] Course Opened");
    }

    public String getFirstCourseTitle() {

        List<WebElement> cards =driver.findElements(courseCards);

        if (cards.isEmpty()) {

            return "N/A";
        }

        return safeGetText(cards.get(0), courseTitle);
    }

    private String safeGetText(WebElement parent,By locator) {

        try {

            return parent.findElement(locator).getText().trim();

        } catch (Exception e) {

            return "N/A";
        }
    }
}