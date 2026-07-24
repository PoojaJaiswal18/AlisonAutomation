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

    @FindBy(xpath = "//div[contains(@class,'card--white')]")
    private List<WebElement> courseCards;

    public SearchResultsPage(WebDriver driver) {

        super(driver);
    }

    private void scrollToFilter(WebElement filter) {

        scrollIntoView(filter);

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }
    }

    private void expandFilter(WebElement filter,WebElement heading) {

        scrollToFilter(filter);

        jsClick(heading);

        try {

            Thread.sleep(800);

        } catch (Exception e) {
        }
    }


    public void applyLanguageFilter( String language) {

        expandFilter(languageFilter,languageHeading);

        By option =By.xpath("//div[@data-url-var='translation']//label[" + "contains(translate(normalize-space(.)," +"'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +"'abcdefghijklmnopqrstuvwxyz'),'" +language.toLowerCase() +"')]");

        WebElement element =wait.waitForClickability(option);

        jsClick(element);

        System.out.println("[PASS] Language Applied : "+ language);
    }

    public void applyLevelFilter(String level) {

        expandFilter(levelFilter,levelHeading);

        By option =By.xpath("//div[@data-url-var='level']//label[" +"contains(translate(normalize-space(.)," +"'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +"'abcdefghijklmnopqrstuvwxyz'),'" +level.toLowerCase() +"')]");

        WebElement element =wait.waitForClickability(option);

        jsClick(element);

        System.out.println("[PASS] Level Applied : "+ level);
    }

    public List<String> getAvailableLanguages() {

        expandFilter(languageFilter,languageHeading);

        List<WebElement> elements =driver.findElements(By.xpath( "//div[@data-url-var='translation']//label"));

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

        List<WebElement> elements = driver.findElements(By.xpath("//div[@data-url-var='level']//label"));

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

        if (!courseCards.isEmpty()) {

            scrollIntoView(courseCards.get(0));
        }
    }

    public List<CourseData> extractVisibleCourses(int count) {

        scrollUntilCardsVisible();

        List<CourseData> courses =new ArrayList<>();

        for (int i = 0;i < Math.min(count, courseCards.size());i++) {

            WebElement card =courseCards.get(i);

            courses.add(new CourseData( 
            		        safeGetText(card,".//div[contains(@class,'card__top')]//h3"),
                            safeGetText(card,".//*[contains(text(),'hrs')]"),
                            safeGetText(card,".//*[contains(text(),'learners')]")));
        }

        return courses;
    }

    public void openFirstCourseDetails() {

        WebElement card =courseCards.get(0);

        scrollIntoView(card);

        new Actions(driver).moveToElement(card).perform();

        WebElement moreInfo =card.findElement(By.xpath(".//a[contains(@class,'card__more')]"));

        scrollIntoView(moreInfo);

        try {

            Thread.sleep(750);

        } catch (Exception e) {
        }

        jsClick(moreInfo);

        System.out.println("[PASS] Course Opened");
    }

    public String getFirstCourseTitle() {

        return safeGetText(courseCards.get(0),".//div[contains(@class,'card__top')]//h3");
    }

    private String safeGetText(WebElement parent,String xpath) {

        try {

            return parent.findElement(By.xpath(xpath)).getText().trim();

        } catch (Exception e) {

            return "N/A";
        }
    }
}