package tests;
 
import com.aventstack.extentreports.Status;
import base.BasePage;
import pages.HomePage;
import pages.BusinessPage;
import pages.BusinessFormPage;
import utils.ExcelUtils;
import utils.ExtentManager;
import utils.WindowUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 
public class BusinessFormTest extends BasePage {
 
    @DataProvider(name = "businessFormProvider")
    public Object[][] businessFormProvider() {
    	return ExcelUtils.readTestCaseData(EXCEL_PATH);
    }
 
    @Test(dataProvider = "businessFormProvider",
          description = "Fill Udemy Business demo form with a deliberately invalid email and capture the validation error")
    public void submitFormWithInvalidEmail(
            String testCaseId,
            String keyword,
            String level,
            String language,
            String firstName,
            String lastName,
            String email,
            String phone) {
        ExtentManager.getTest().log(Status.INFO, testCaseId + " — Email under test: " + email);
 
        HomePage home = new HomePage(driver);
        home.goToUdemyBusiness();
        if (driver.getWindowHandles().size() > 1) {
            WindowUtils.switchToNewWindow(driver, driver.getWindowHandle());
            ExtentManager.getTest().log(Status.INFO, "Switched to Udemy Business tab");
        }
 
        BusinessPage businessPage = new BusinessPage(driver);
        businessPage.clickRequestDemo();
 
        BusinessFormPage form = new BusinessFormPage(driver);
        form.fillFirstName(firstName);
        form.fillLastName(lastName);
        form.fillWorkEmail(email);
        form.fillPhone(phone);
        form.fillCompanyName("Cognizant");
        form.fillJobTitle("QA Engineer");
        form.selectCustomDropdown("Where are you located?", "India");
        form.selectCustomDropdown("Company Size", "1-20");
        form.selectCustomDropdown("Number of people to train", "1-20");
        form.selectCustomDropdown("Job Level", "Manager");
 
        form.submitForm();
        String validationMessage = form.getValidationErrorMessage();
        ExtentManager.getTest().log(Status.INFO, testCaseId + " — Captured: " + validationMessage);
 
        ExcelUtils.appendResult(EXCEL_PATH, "TestResults", testCaseId, "FORM",
                validationMessage, "", "", validationMessage.isEmpty() ? "FAIL" : "PASS");
 
        Assert.assertFalse(validationMessage.isEmpty(), testCaseId + ": expected a validation error for email \"" + email + "\"");
        ExtentManager.getTest().pass(testCaseId + ": validation error correctly captured — " + validationMessage);
    }
}