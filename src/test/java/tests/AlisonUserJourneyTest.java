package tests;

import model.CourseData;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import listeners.TestListener;

import pages.BusinessFormPage;
import pages.CourseDetailsPage;
import pages.HomePage;
import pages.SearchResultsPage;

import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class AlisonUserJourneyTest extends BaseTest {

    @DataProvider(name = "journeyData")
    public Object[][] journeyData() {

        return ExcelUtils.readTestCaseData(EXCEL_PATH);
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

        System.out.println("Executing Journey : " + testCaseId);

        System.out.println("==========================================");

        System.out.println("STARTING USER JOURNEY : " + testCaseId);

        System.out.println("==========================================");


        HomePage home =new HomePage(driver);

        home.searchCourse(keyword);
        TestListener.logPass("Course Search Completed : "+ keyword);

        Assert.assertTrue(driver.getCurrentUrl().contains("courses"),"Search results page not opened");

        SearchResultsPage results =new SearchResultsPage(driver);

        results.applyLanguageFilter(language);
        TestListener.logPass("Language Applied : "+ language);

        results.applyLevelFilter(level);
        TestListener.logPass("Level Applied : "+ level);

        List<CourseData> courses =results.extractVisibleCourses(2);
        TestListener.logPass("Courses Extracted : " + courses.size());

        Assert.assertTrue( courses.size() > 0,"No courses returned");

        String firstCourseTitle =results.getFirstCourseTitle();

        Assert.assertFalse(firstCourseTitle.equals("N/A"),"Course title not found");


        results.openFirstCourseDetails();
        TestListener.logPass("Course Details Page Opened");

        CourseDetailsPage detailsPage = new CourseDetailsPage(driver);

        Map<String, String> details =detailsPage.getCourseDetails();
        TestListener.logPass("Course Details Extracted");

        Assert.assertNotNull(details,"Course details not loaded");

        Assert.assertFalse(details.getOrDefault("Course","").isBlank(),"Course name missing");

        System.out.println("[PASS] Course Details Extracted");

        ExcelUtils.appendCourseDetails(EXCEL_PATH,testCaseId,

                details.getOrDefault( "Course",""),

                details.getOrDefault( "Description",""),

                details.getOrDefault("Duration",""),

                details.getOrDefault( "Enrollments",""),

                details.getOrDefault("Modules",""),

                details.getOrDefault("Publisher", ""));
        
        TestListener.logPass("Course Details Written To Excel");

        driver.get(BASE_URL);

        home.goToBusinessPage();
        TestListener.logPass("Business Page Opened");

        BusinessFormPage form =new BusinessFormPage(driver);

        form.fillFirstName(firstName);

        form.fillLastName(lastName);

        form.fillEmail(email);

        form.fillCompanyName( organisationName);

        String emailValidationMessage =form.getEmailValidationMessage();

        System.out.println( "Error message encountered : "+ emailValidationMessage);

        

        if (!emailValidationMessage.isBlank()) {

            System.out.println("[PASS] Email validation displayed");
            TestListener.logPass("Email Validation Displayed : " + emailValidationMessage);
        }

        form.replaceEmail(correctEmail);

        System.out.println("[PASS] Email Updated : "+ correctEmail);
        TestListener.logPass("Email Updated : "+ correctEmail);

        form.selectDepartment(department);
        TestListener.logPass("Department Selected : "+ department);

        form.selectProduct(product);
        TestListener.logPass("Product Selected : "+ product);

        form.selectIndustry(industry);
        TestListener.logPass("Industry Selected : "+ industry);

        form.selectOrganisationType(organisationType);
        TestListener.logPass("Organisation Type Selected : " + organisationType);

        form.selectOrganisationSize(organisationSize);
        TestListener.logPass("Organisation Size Selected : "+ organisationSize);

        form.selectCountry(country);
        TestListener.logPass("Country Selected : "+ country);

        form.fillTrainingRequirement(trainingRequirement);
        TestListener.logPass("Training Requirement Entered");

        form.submitForm();

        String validationMessage =form.getValidationErrorMessage();

        if (!validationMessage.isEmpty()) {

            System.out.println("[INFO] Validation Message : "+ validationMessage);
            TestListener.logInfo("Validation Message : "+ validationMessage);

        } else {

            System.out.println("[PASS] Form Submitted Successfully");
            TestListener.logPass("Form Submitted Successfully");
        }


        Assert.assertTrue(validationMessage.isEmpty(),"Form submission failed : " + validationMessage);
        TestListener.logPass("USER JOURNEY COMPLETE : "+ testCaseId);
        System.out.println("USER JOURNEY COMPLETE : "+ testCaseId);
    }
}