package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BusinessFormPage extends BasePage {

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
            By.xpath("//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-6 ng-star-inserted\"]");

    private By productDropdown =
            By.xpath("//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-8 ng-star-inserted\"]");

    private By industryDropdown =
            By.xpath("//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-10 ng-star-inserted\"]");

    private By organisationTypeDropdown =
            By.xpath("//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-13 ng-star-inserted\"]");

    private By organisationSizeDropdown =
            By.xpath("//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-15 ng-star-inserted\"]");

    private By countryDropdown =
            By.xpath("//i[@class=\"icon-caret-down alison-select-arrow ng-tns-c777274391-17 ng-star-inserted\"]");

    private By submitButton =
            By.xpath("//button[@class=\"apply-now-button alison-button alison-button-lg alison-accent-hover w-full mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base\"]");

    private By validationMessage =
            By.xpath("//*[contains(text(),'valid') or contains(text(),'required')]");

    private By emailValidationMessage =
            By.xpath("//mat-error[@class=\"mat-mdc-form-field-error mat-mdc-form-field-bottom-align ng-tns-c3100660499-2 ng-star-inserted\"]");

    public BusinessFormPage(WebDriver driver) {

        super(driver);
    }

    public void fillFirstName(String value) {

        safeType(
                wait.waitForVisibility(firstNameField),
                value);
    }

    public void fillLastName(String value) {

        safeType(
                wait.waitForVisibility(lastNameField),
                value);
    }

    public void fillEmail(String value) {

        safeType(
                wait.waitForVisibility(emailField),
                value);
    }

    public String getEmailValidationMessage() {

        try {

            return wait.waitForVisibility(
                            emailValidationMessage)
                    .getText()
                    .trim();

        } catch (Exception e) {

            return "";
        }
    }

    public void replaceEmail(String correctEmail) {

        WebElement email =
                wait.waitForVisibility(
                        emailField);

        email.clear();

        email.sendKeys(
                correctEmail);
    }

    public void fillCompanyName(String value) {

        safeType(
                wait.waitForVisibility(
                        organisationNameField),
                value);
    }

    public void fillTrainingRequirement(String value) {

        safeType(
                wait.waitForVisibility(
                        trainingRequirementField),
                value);
    }

    private void selectDropdownOption(
            By dropdown,
            String optionText) {

        WebElement dropdownElement =
                wait.waitForClickability(
                        dropdown);

        scrollIntoView(
                dropdownElement);

        dropdownElement.click();

        try {

            Thread.sleep(1000);

        } catch (Exception e) {
        }

        java.util.List<WebElement> options =
                driver.findElements(
                        By.xpath(
                                "//span[contains(@class,'mdc-list-item__primary-text')]"));

        String expected =
                optionText
                        .replaceAll("\\s+", "")
                        .trim()
                        .toLowerCase();

        for (WebElement option : options) {

            try {

                String actual =
                        option.getText()
                                .replaceAll("\\s+", "")
                                .trim()
                                .toLowerCase();

                if (actual.equals(expected)) {

                    scrollIntoView(
                            option);

                    try {

                        Thread.sleep(500);

                    } catch (Exception e) {
                    }

                    option.click();

                    System.out.println(
                            "[PASS] Selected : "
                                    + optionText);

                    return;
                }

            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException(
                "Dropdown option not found : "
                        + optionText);
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

            return error.getText()
                    .trim();

        } catch (Exception e) {

            return "";
        }
    }
}