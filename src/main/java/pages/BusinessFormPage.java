package pages;

import utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BusinessFormPage {

    private WebDriver driver;
    private WaitUtils wait;

    private By firstNameField =
            By.xpath("//input[contains(@placeholder,'First Name')]");

    private By lastNameField =
            By.xpath("//input[contains(@placeholder,'Last Name')]");

    private By emailField =
            By.xpath("//input[contains(@placeholder,'Business Email')]");

    private By organisationNameField =
            By.xpath("//input[contains(@placeholder,'Organisation Name')]");

    private By trainingRequirementField =
            By.tagName("textarea");

    private By departmentDropdown =
            By.xpath("//*[contains(text(),'Your Department')]");

    private By productDropdown =
            By.xpath("//*[contains(text(),'Select Product')]");

    private By industryDropdown =
            By.xpath("//*[contains(text(),'Industry')]");

    private By organisationTypeDropdown =
            By.xpath("//*[contains(text(),'Organisation Type')]");

    private By organisationSizeDropdown =
            By.xpath("//*[contains(text(),'Size of the Organisation')]");

    private By countryDropdown =
            By.xpath("//*[contains(text(),'Country')]");

    private By submitButton =
            By.xpath("//button[contains(.,'Submit')]");

    private By validationMessage =
            By.xpath("//*[contains(text(),'valid') or contains(text(),'required')]");

    public BusinessFormPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    public void fillFirstName(String value) {

        wait.waitForVisibility(
                firstNameField)
                .sendKeys(value);
    }

    public void fillLastName(String value) {

        wait.waitForVisibility(
                lastNameField)
                .sendKeys(value);
    }

    public void fillEmail(String value) {

        wait.waitForVisibility(
                emailField)
                .sendKeys(value);
    }

    public void fillCompanyName(String value) {

        wait.waitForVisibility(
                organisationNameField)
                .sendKeys(value);
    }

    public void fillTrainingRequirement(String value) {

        wait.waitForVisibility(
                trainingRequirementField)
                .sendKeys(value);
    }

    private void selectDropdownOption(
            By dropdown,
            String optionText) {

        wait.waitForClickability(
                dropdown)
                .click();

        By option =
                By.xpath(
                        "//*[contains(text(),'"
                                + optionText
                                + "')]");

        wait.waitForClickability(
                option)
                .click();
    }

    public void selectDepartment(String department) {

        selectDropdownOption(
                departmentDropdown,
                department);
    }

    public void selectProduct(String product) {

        selectDropdownOption(
                productDropdown,
                product);
    }

    public void selectIndustry(String industry) {

        selectDropdownOption(
                industryDropdown,
                industry);
    }

    public void selectOrganisationType(String type) {

        selectDropdownOption(
                organisationTypeDropdown,
                type);
    }

    public void selectOrganisationSize(String size) {

        selectDropdownOption(
                organisationSizeDropdown,
                size);
    }

    public void selectCountry(String country) {

        selectDropdownOption(
                countryDropdown,
                country);
    }

    public void submitForm() {

        wait.waitForClickability(
                submitButton)
                .click();
    }

    public String getValidationErrorMessage() {

        try {

            WebElement error =
                    wait.waitForVisibility(
                            validationMessage);

            return error.getText().trim();

        } catch (Exception e) {

            return "";
        }
    }
}