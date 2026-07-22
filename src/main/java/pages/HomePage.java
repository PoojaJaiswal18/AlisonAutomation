package pages;

import utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    private WebDriver driver;
    private WaitUtils wait;

    private By searchBox = By.xpath(
            "//input[@id=\"autocomplete\"]");

  
    private By forBusinessLink = By.xpath(
            "//a[contains(@class,'header__lms') and contains(.,'For Business')]");
    
    private By bookDemoButton =
            By.xpath("//a[@href='/lms/contact-us']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

  
    public void searchCourse(String keyword) {

        System.out.println("====================================");
        System.out.println("[STEP] Searching Keyword : " + keyword);
        System.out.println("====================================");

        WebElement box =
                wait.waitForVisibility(searchBox);

        box.clear();

        box.sendKeys(keyword);

        box.sendKeys(Keys.ENTER);

        System.out.println(
                "[INFO] Current URL : "
                        + driver.getCurrentUrl());

        System.out.println(
                "[INFO] Title : "
                        + driver.getTitle());

        System.out.println(
                "[PASS] Search submitted");
    }

  
    public String goToBusinessPage() {

        String currentWindow =
                driver.getWindowHandle();

        System.out.println(
                "[STEP] Navigating To Alison Business");

        WebElement business =
                wait.waitForClickability(
                        forBusinessLink);

        business.click();

        System.out.println(
                "[PASS] Business Page Opened");

        WebElement demoButton =
                wait.waitForClickability(
                        bookDemoButton);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        demoButton);

        demoButton.click();

        System.out.println(
                "[PASS] Book Demo Clicked");

        return currentWindow;
    }
    public boolean isSearchResultsLoaded() {

        String url =
                driver.getCurrentUrl().toLowerCase();

        return url.contains("/courses")
                || url.contains("/tag");
    }
}