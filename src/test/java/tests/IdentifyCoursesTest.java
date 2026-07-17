package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BasePage;
import model.CourseData;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.ExcelUtils;
import utils.ExtentManager;
 
public class IdentifyCoursesTest extends BasePage {
 
    @Test(description = "Search, filter Beginner+English, extract top 2 courses")
    public void identifyTopBeginnerCourses() {
        HomePage home = new HomePage(driver);
        home.searchCourse(config.getProperty("searchKeyword"));
        ExtentManager.getTest().log(Status.INFO, "Searched for: " + config.getProperty("searchKeyword"));
 
        SearchResultsPage results = new SearchResultsPage(driver);
        results.applyBeginnerFilter();
        results.applyEnglishFilter();
        ExtentManager.getTest().log(Status.INFO, "Applied Beginner + English filters");
 
        List<CourseData> topCourses = results.extractTopCourses(2);
        ExtentManager.getTest().log(Status.INFO, "Extracted: " + topCourses);
 
        Assert.assertEquals(topCourses.size(), 2, "Expected exactly 2 courses extracted");
        for (CourseData course : topCourses) {
            Assert.assertNotEquals(course.getName(), "N/A", "Course name should not be missing");
        }
 
        ExcelUtils.writeCourseResults(config.getProperty("excelPath"), "TopCourses", topCourses);
        ExtentManager.getTest().pass("Top 2 courses extracted and written to Excel");
    }
}
