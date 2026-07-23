package tests;

import com.aventstack.extentreports.Status;

import base.BasePage;

import model.CourseData;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.BusinessFormPage;
import pages.CourseDetailsPage;
import pages.HomePage;
import pages.SearchResultsPage;

import utils.ExcelUtils;
import utils.ExtentManager;

import java.util.List;
import java.util.Map;

public class AlisonUserJourneyTest extends BasePage {

    @DataProvider(name = "journeyData")
    public Object[][] journeyData() {

        return ExcelUtils.readTestCaseData(
                EXCEL_PATH);
    }

    @Test(
            dataProvider = "journeyData",
            description = "Complete Alison User Journey")
    public void completeJourney(

            String testCaseId,
            String keyword,
            String level,
            String language,

            String firstName,
            String lastName,
            String email,

            String department,
            String product,
            String industry,
            String organisationName,
            String organisationType,
            String organisationSize,
            String country,
            String trainingRequirement,
            String correctEmail) {

        ExtentManager.getTest().log(
                Status.INFO,
                "Executing Journey : "
                        + testCaseId);

        System.out.println(
                "==========================================");

        System.out.println(
                "STARTING USER JOURNEY : "
                        + testCaseId);

        System.out.println(
                "==========================================");

        /*
         * STEP 1
         * SEARCH COURSE
         */

        HomePage home =
                new HomePage(driver);

        home.searchCourse(keyword);

        SearchResultsPage results =
                new SearchResultsPage(driver);

        /*
         * STEP 2
         * APPLY FILTERS
         */

        results.applyLanguageFilter(language);

        results.applyLevelFilter(level);

        /*
         * STEP 3
         * EXTRACT COURSES
         */

        List<CourseData> courses =
                results.extractVisibleCourses(2);

        Assert.assertTrue(
                courses.size() > 0,
                "No courses returned");

        for (CourseData course : courses) {

            ExcelUtils.appendResult(
                    EXCEL_PATH,
                    "TestResults",
                    testCaseId,
                    "COURSE",
                    course.getName(),
                    course.getDuration(),
                    course.getLearners(),
                    "PASS");
        }


        /*
         * STEP 5
         * COURSE DETAILS
         */

        results.openFirstCourseDetails();

        CourseDetailsPage detailsPage =
                new CourseDetailsPage(driver);

        Map<String, String> details =
                detailsPage.getCourseDetails();

        ExcelUtils.appendCourseDetails(
                EXCEL_PATH,
                testCaseId,
                details.getOrDefault(
                        "Course",
                        ""),
                details.getOrDefault(
                        "Description",
                        ""),
                details.getOrDefault(
                        "Duration",
                        ""),
                details.getOrDefault(
                        "Enrollments",
                        ""),
                details.getOrDefault(
                        "Modules",
                        ""),
                details.getOrDefault(
                        "Publisher",
                        ""));

        /*
         * STEP 6
         * GO BACK TO HOME
         */

        driver.get(BASE_URL);

        /*
         * STEP 7
         * BUSINESS PAGE
         */

        home.goToBusinessPage();

        BusinessFormPage form =
                new BusinessFormPage(driver);

        /*
         * STEP 8
         * FILL FORM
         */

        form.fillFirstName(firstName);

        form.fillLastName(lastName);

        /*
         * Enter Invalid Email
         */

        form.fillEmail(email);

        /*
         * Trigger Validation
         */

        form.fillCompanyName(
                organisationName);

        /*
         * Capture Validation
         */

        String emailValidationMessage =
                form.getEmailValidationMessage();

        /*
         * Store Validation
         */

        ExcelUtils.appendResult(
                EXCEL_PATH,
                "TestResults",
                testCaseId,
                "EMAIL_VALIDATION",
                emailValidationMessage,
                "",
                "",
                emailValidationMessage.isEmpty()
                        ? "FAIL"
                        : "PASS");

        /*
         * Always Replace With Correct Email
         */

        form.replaceEmail(correctEmail);

        System.out.println(
                "[PASS] Email Updated : "
                        + correctEmail);

        /*
         * Continue Form Filling
         */

        form.selectDepartment(
                department);

        form.selectProduct(
                product);

        form.selectIndustry(
                industry);

        form.selectOrganisationType(
                organisationType);

        form.selectOrganisationSize(
                organisationSize);

        form.selectCountry(
                country);

        form.fillTrainingRequirement(
                trainingRequirement);

        /*
         * STEP 9
         * SUBMIT FORM
         */

        form.submitForm();

        /*
         * Post Submit Validation
         */

        String validationMessage =
                form.getValidationErrorMessage();

        ExcelUtils.appendResult(
                EXCEL_PATH,
                "TestResults",
                testCaseId,
                "FORM",
                validationMessage,
                "",
                "",
                validationMessage.isEmpty()
                        ? "PASS"
                        : "FAIL");

        /*
         * Report Only
         */

        if (!validationMessage.isEmpty()) {

            ExtentManager.getTest()
                    .warning(
                            validationMessage);

        } else {

            ExtentManager.getTest()
                    .pass(
                            "Form Submitted Successfully");
        }

        ExtentManager.getTest().pass(
                "Journey Completed Successfully");

        System.out.println(
                "USER JOURNEY COMPLETE : "
                        + testCaseId);
}
    }