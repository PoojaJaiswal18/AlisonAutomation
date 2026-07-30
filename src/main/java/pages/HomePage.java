package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(id = "autocomplete")
    private WebElement searchBox;

    @FindBy(xpath = "//a[contains(@class,'header__lms') and contains(.,'For Business')]")
    private WebElement forBusinessLink;

    @FindBy(xpath = "//a[@href='/lms/contact-us']")
    private WebElement bookDemoButton;

    public HomePage(WebDriver driver) {

        super(driver);
    }


    public void searchCourse(String keyword) {

        System.out.println( "====================================");

        System.out.println( "[STEP] Searching Keyword : "+ keyword);

        System.out.println("====================================");

        searchBox.clear();

        searchBox.sendKeys(keyword);

        searchBox.sendKeys(Keys.ENTER);

        System.out.println("[INFO] Current URL : " + getCurrentUrl());

        System.out.println("[INFO] Title : "+ getPageTitle());

        System.out.println("[PASS] Search submitted");
    }


    public String goToBusinessPage() {

        String currentWindow =driver.getWindowHandle();

        System.out.println("[STEP] Navigating To Alison Business");

        jsClick(forBusinessLink);

        System.out.println("[PASS] Business Page Opened");

        scrollIntoView(bookDemoButton);

        jsClick(bookDemoButton);

        System.out.println("[PASS] Book Demo Clicked");

        return currentWindow;
    }


    public boolean isSearchResultsLoaded() {

        String url = getCurrentUrl().toLowerCase();

        return url.contains("/courses")|| url.contains("/tag");
    }
}