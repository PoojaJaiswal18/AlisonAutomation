package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BasePage;
import pages.BusinessFormPage;
import pages.BusinessPage;
import pages.HomePage;
import utils.ExtentManager;
import utils.WindowUtils;
 
public class BusinessFormTest extends BasePage {
 
    @Test(description = "Fill Udemy Business demo form with an invalid email and capture the error")
    public void submitFormWithInvalidEmail() {
        HomePage home = new HomePage(driver);
        String originalHandle = home.goToUdemyBusiness();
 
        if (driver.getWindowHandles().size() > 1) {
            WindowUtils.switchToNewWindow(driver, originalHandle);
            ExtentManager.getTest().log(Status.INFO, "Switched to new tab for Udemy Business");
        }
 
        BusinessPage businessPage = new BusinessPage(driver);
        businessPage.clickRequestDemo();
 
        BusinessFormPage form = new BusinessFormPage(driver);
        form.fillName("Test Automation");
        form.fillCompany("QA Hackathon Inc");
        form.fillPhone("9999999999");
        form.fillEmail(config.getProperty("invalidEmail"));
        ExtentManager.getTest().log(Status.INFO, "Filled form with invalid email: " + config.getProperty("invalidEmail"));
 
        form.submitForm();
 
        String errorMessage = form.getValidationErrorMessage();
        ExtentManager.getTest().log(Status.INFO, "Captured validation message: " + errorMessage);
 
        Assert.assertFalse(errorMessage.isEmpty(), "Expected a validation error for invalid email");
        ExtentManager.getTest().pass("Validation error correctly captured: " + errorMessage);
    }
}