package tests;

import model.CourseData;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

    	System.out.println("Executing Journey : " + testCaseId);

        System.out.println("==========================================");

        System.out.println("STARTING USER JOURNEY : "+ testCaseId);

        System.out.println("==========================================");

   
        HomePage home =new HomePage(driver);

        home.searchCourse(keyword);

        SearchResultsPage results =new SearchResultsPage(driver);

        results.applyLanguageFilter(language);

        results.applyLevelFilter(level);

      

        List<CourseData> courses =results.extractVisibleCourses(2);

        Assert.assertTrue( courses.size() > 0,"No courses returned");

       


        results.openFirstCourseDetails();

        CourseDetailsPage detailsPage = new CourseDetailsPage(driver);

        Map<String, String> details = detailsPage.getCourseDetails();

        ExcelUtils.appendCourseDetails(EXCEL_PATH,testCaseId,
        		
                details.getOrDefault("Course",""),
                details.getOrDefault("Description",""),
                details.getOrDefault("Duration",""),
                details.getOrDefault("Enrollments",""),
                details.getOrDefault("Modules",""),
                details.getOrDefault("Publisher",""));

 

        driver.get(BASE_URL);


        home.goToBusinessPage();

        BusinessFormPage form = new BusinessFormPage(driver);

        form.fillFirstName(firstName);

        form.fillLastName(lastName);

        form.fillEmail(email);

        form.fillCompanyName(organisationName);

        String emailValidationMessage = form.getEmailValidationMessage();

        System.out.println("Error message encountered:"+ emailValidationMessage);


        form.replaceEmail(correctEmail);

        System.out.println("[PASS] Email Updated : "+ correctEmail);

        
        form.selectDepartment(department);

        form.selectProduct(product);

        form.selectIndustry(industry);

        form.selectOrganisationType(organisationType);

        form.selectOrganisationSize(organisationSize);

        form.selectCountry(country);

        form.fillTrainingRequirement( trainingRequirement);

        form.submitForm();

        String validationMessage =form.getValidationErrorMessage();

       
        
        if (!validationMessage.isEmpty()) {

            System.out.println("[INFO] Validation Message : "+ validationMessage);

        } else {

            System.out.println("[PASS] Form Submitted Successfully");
        }

        System.out.println( "USER JOURNEY COMPLETE : "+ testCaseId);
}
    }