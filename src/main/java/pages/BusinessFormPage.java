package pages;
 
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
public class BusinessFormPage {
    private WebDriver driver;
    private WaitUtils wait;
 
    // Real Udemy Business demo form: each field's label text IS its placeholder
    private By firstNameField = By.cssSelector("input[placeholder=\"First Name *\"]");
    private By lastNameField = By.cssSelector("input[placeholder='Last Name *']");
    private By workEmailField = By.cssSelector("input[placeholder='Work Email *']");
    private By phoneField = By.cssSelector("input[placeholder='Phone Number *']");
    private By companyNameField = By.cssSelector("input[placeholder='Company Name *']");
    private By jobTitleField = By.cssSelector("input[placeholder='Job Title *']");
    private By submitBtn = By.xpath("//button[contains(.,'Submit') or contains(.,'Request') or @type='submit']");
    private By inlineErrorMessage = By.cssSelector("[class*='error'], [role='alert'], .field-error");
 
    public BusinessFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }
 
    public void fillFirstName(String value) { wait.waitForVisibility(firstNameField).sendKeys(value); }
    public void fillLastName(String value) { wait.waitForVisibility(lastNameField).sendKeys(value); }
    public void fillWorkEmail(String value) { wait.waitForVisibility(workEmailField).sendKeys(value); }
    public void fillPhone(String value) { wait.waitForVisibility(phoneField).sendKeys(value); }
    public void fillCompanyName(String value) { wait.waitForVisibility(companyNameField).sendKeys(value); }
    public void fillJobTitle(String value) { wait.waitForVisibility(jobTitleField).sendKeys(value); }
 
    /**
     * Handles the custom "Select..." dropdowns (Where are you located?, Company Size,
     * Number of people to train, Job Level) -- click-to-open custom components, not native <select>.
     */
    public void selectCustomDropdown(String labelText, String optionText) {
        By dropdownTrigger = By.xpath(
            "//*[contains(normalize-space(.), '" + labelText + "')]/following::*[contains(normalize-space(.), 'Select...')][1]"
        );
        wait.waitForClickability(dropdownTrigger).click();
 
        By option = By.xpath("//*[self::li or self::div][contains(normalize-space(.), '" + optionText + "')]");
        wait.waitForClickability(option).click();
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