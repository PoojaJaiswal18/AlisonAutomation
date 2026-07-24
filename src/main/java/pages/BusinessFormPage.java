package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BusinessFormPage extends BasePage {

    @FindBy(xpath = "//input[contains(@placeholder,'First Name')]")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[contains(@placeholder,'Last Name')]")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[contains(@placeholder,'Business Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//input[contains(@placeholder,'Organisation Name')]")
    private WebElement organisationNameField;

    @FindBy(tagName = "textarea")
    private WebElement trainingRequirementField;

    @FindBy(xpath = "//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-6 ng-star-inserted\"]")
    private WebElement departmentDropdown;

    @FindBy(xpath = "//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-8 ng-star-inserted\"]")
    private WebElement productDropdown;

    @FindBy(xpath = "//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-10 ng-star-inserted\"]")
    private WebElement industryDropdown;

    @FindBy(xpath = "//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-13 ng-star-inserted\"]")
    private WebElement organisationTypeDropdown;

    @FindBy(xpath = "//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-15 ng-star-inserted\"]")
    private WebElement organisationSizeDropdown;

    @FindBy(xpath = "//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-17 ng-star-inserted\"]")
    private WebElement countryDropdown;

    @FindBy(xpath = "//button[@class=\"apply-now-button alison-button alison-button-lg alison-accent-hover w-full mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base\"]")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(),'valid') or contains(text(),'required')]")
    private WebElement validationMessage;

    @FindBy(xpath = "//mat-error[@class=\"mat-mdc-form-field-error mat-mdc-form-field-bottom-align ng-tns-c3100660499-2 ng-star-inserted\"]")
    private WebElement emailValidationMessage;

    @FindBy(xpath = "//span[contains(@class,'mdc-list-item__primary-text')]")
    private List<WebElement> dropdownOptions;

    public BusinessFormPage(WebDriver driver) {

        super(driver);
    }

    public void fillFirstName(String value) {
    	
        wait.waitForVisibility(firstNameField);

        safeType(firstNameField, value);
        
    }

    public void fillLastName(String value) {
    	
    	wait.waitForVisibility(lastNameField);

        safeType(lastNameField, value);
    }

    public void fillEmail(String value) {
    	
    	wait.waitForVisibility(emailField);

        safeType(emailField, value);
    }

    public void fillCompanyName(String value) {
    	
    	wait.waitForVisibility(organisationNameField);

        safeType(organisationNameField, value);
    }

    public void fillTrainingRequirement(String value) {
    	
    	wait.waitForVisibility(trainingRequirementField);

        safeType(trainingRequirementField, value);
    }

    public String getEmailValidationMessage() {

        try {

            return emailValidationMessage.getText().trim();

        } catch (Exception e) {

            return "";
        }
    }

    public void replaceEmail(String correctEmail) {

        emailField.clear();

        emailField.sendKeys(correctEmail);
    }

    private void selectDropdownOption(
            WebElement dropdown,
            String optionText) {

        scrollIntoView(dropdown);

        dropdown.click();

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }

        String expected = optionText.replaceAll("\\s+", "").trim().toLowerCase();

        for (WebElement option : dropdownOptions) {

            try {

                String actual =option.getText().replaceAll("\\s+", "").trim().toLowerCase();

                if (actual.equals(expected)) {

                    scrollIntoView(option);

                    try {

                        Thread.sleep(500);

                    } catch (Exception e) {
                    }

                    option.click();

                    System.out.println("[PASS] Selected : "+ optionText);

                    return;
                }

            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Dropdown option not found : "+ optionText);
    }

    public void selectDepartment(String department) {
    	
    	wait.waitForClickability(departmentDropdown);

        selectDropdownOption(departmentDropdown,department);
    }

    public void selectProduct(String product) {
    	
    	wait.waitForClickability(productDropdown);

        selectDropdownOption(productDropdown,product);
    }

    public void selectIndustry(String industry) {
    	
    	wait.waitForClickability(industryDropdown);

        selectDropdownOption(industryDropdown,industry);
    }

    public void selectOrganisationType(String type) {
    	
    	wait.waitForClickability(organisationTypeDropdown);

        selectDropdownOption(organisationTypeDropdown,type);
    }

    public void selectOrganisationSize(String size) {
    	
    	wait.waitForClickability(organisationSizeDropdown);
        selectDropdownOption(organisationSizeDropdown,size);
    }

    public void selectCountry(String country) {
    	
    	wait.waitForClickability(countryDropdown);

        selectDropdownOption(countryDropdown,country);
    }

    public void submitForm() {
    	wait.waitForClickability(submitButton).click();
    }

    public String getValidationErrorMessage() {

        try {

            return validationMessage.getText().trim();

        } catch (Exception e) {

            return "";
        }
    }
}