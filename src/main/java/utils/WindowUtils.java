package utils;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
 
import java.time.Duration;
import java.util.Set;
 
public class WindowUtils {
 
    public static String switchToNewWindow(WebDriver driver, String originalHandle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.getWindowHandles().size() > 1);
 
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                wait.until(
                	    d -> ((String)((org.openqa.selenium.JavascriptExecutor)d)
                	    .executeScript("return document.readyState"))
                	    .equals("complete")
                	);
                return handle;
            }
        }
        return originalHandle;
    }
 
    public static void closeAndSwitchBack(WebDriver driver, String originalHandle) {
        driver.close();
        driver.switchTo().window(originalHandle);
    }
}