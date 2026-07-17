package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;
 
public class BusinessFormPage {
    private WebDriver driver;
    private WaitUtils wait;
 
    private By nameField = By.cssSelector("input[name='name'], input[id*='name']");
    private By emailField = By.cssSelector("input[name='email'], input[type='email']");
    private By companyField = By.cssSelector("input[name='company'], input[id*='company']");
    private By phoneField = By.cssSelector("input[name='phone'], input[type='tel']");
    private By submitBtn = By.cssSelector("button[type='submit']");
    private By inlineErrorMessage = By.cssSelector("[class*='error'], [role='alert'], .field-error");
 
    public BusinessFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }
 
    public void fillName(String name) {
        wait.waitForVisibility(nameField).sendKeys(name);
    }
 
    public void fillEmail(String email) {
        wait.waitForVisibility(emailField).sendKeys(email);
    }
 
    public void fillCompany(String company) {
        wait.waitForVisibility(companyField).sendKeys(company);
    }
 
    public void fillPhone(String phone) {
        try {
            wait.waitForVisibility(phoneField).sendKeys(phone);
        } catch (Exception ignored) {
        }
    }
 
    public void submitForm() {
        wait.waitForClickability(submitBtn).click();
    }
 
    public String getValidationErrorMessage() {
        try {
            WebElement error = wait.waitForVisibility(inlineErrorMessage);
            return error.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
