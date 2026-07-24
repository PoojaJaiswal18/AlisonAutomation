package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {


    private By searchBox =
            By.xpath("//input[@id='autocomplete']");

    private By forBusinessLink =
            By.xpath(
                    "//a[contains(@class,'header__lms') and contains(.,'For Business')]");

    private By bookDemoButton =
            By.xpath("//a[@href='/lms/contact-us']");

    public HomePage(WebDriver driver) {

        super(driver);
    }

    /*
     * Search Course
     */

    public void searchCourse(String keyword) {

        System.out.println(
                "====================================");

        System.out.println(
                "[STEP] Searching Keyword : "
                        + keyword);

        System.out.println(
                "====================================");

        WebElement box =
                wait.waitForVisibility(
                        searchBox);

        box.clear();

        box.sendKeys(
                keyword);

        box.sendKeys(
                Keys.ENTER);

        System.out.println(
                "[INFO] Current URL : "
                        + getCurrentUrl());

        System.out.println(
                "[INFO] Title : "
                        + getPageTitle());

        System.out.println(
                "[PASS] Search submitted");
    }

    /*
     * Navigate To Business Page
     */

    public String goToBusinessPage() {

        String currentWindow =
                driver.getWindowHandle();

        System.out.println(
                "[STEP] Navigating To Alison Business");

        WebElement businessLink =
                wait.waitForClickability(
                        forBusinessLink);

        jsClick(
                businessLink);

        System.out.println(
                "[PASS] Business Page Opened");

        WebElement demoButton =
                wait.waitForClickability(
                        bookDemoButton);

        scrollIntoView(
                demoButton);

        jsClick(
                demoButton);

        System.out.println(
                "[PASS] Book Demo Clicked");

        return currentWindow;
    }

    /*
     * Verify Search Results Page
     */

    public boolean isSearchResultsLoaded() {

        String url =
                getCurrentUrl()
                        .toLowerCase();

        return url.contains("/courses")
                || url.contains("/tag");
    }
}