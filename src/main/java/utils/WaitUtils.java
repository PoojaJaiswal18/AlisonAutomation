package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
import java.time.Duration;
import java.util.List;
 
public class WaitUtils {
    private WebDriverWait wait;
 
    public WaitUtils(WebDriver driver, int timeoutSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }
 
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
 
    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
 
    public List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
 
    public boolean waitForUrlContains(String fragment) {
        return wait.until(ExpectedConditions.urlContains(fragment));
    }
 
    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}