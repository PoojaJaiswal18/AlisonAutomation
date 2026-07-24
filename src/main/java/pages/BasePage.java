package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtils wait;

    public BasePage(WebDriver driver) {

        this.driver = driver;

        this.wait =new WaitUtils(driver,20);
    }

    protected void scrollIntoView(WebElement element) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",element);
    }

    protected void jsClick(
            WebElement element) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
    }

    protected void safeType(WebElement element,String value) {

        try {

            element.clear();

            element.sendKeys(value);

        } catch (Exception e) {

            throw new RuntimeException("Unable to enter value : "+ value, e);
        }
    }

    protected String safeGetText( WebElement element) {

        try {

            String text =element.getText().trim();

            return text.isEmpty()? "N/A": text;

        } catch (Exception e) {

            return "N/A";
        }
    }

    protected String getPageTitle() {

        return driver.getTitle();
    }

    protected String getCurrentUrl() {

        return driver.getCurrentUrl();
    }
}