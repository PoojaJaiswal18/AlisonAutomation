package tests;
 
import com.aventstack.extentreports.Status;
import base.BasePage;
import model.CourseData;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.ExcelUtils;
import utils.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 
import java.util.List;
 
public class IdentifyCoursesTest extends BasePage {
 
	@DataProvider(name = "searchKeywordProvider")
	public Object[][] searchKeywordProvider() {

	    return ExcelUtils.readTestCaseData(
	            EXCEL_PATH);
	}
	
    @Test(dataProvider = "searchKeywordProvider",
          description = "Search a keyword, apply Level+Language filters, extract top N courses")
    public void identifyTopCourses(
            String testCaseId,
            String keyword,
            String level,
            String language,
            String firstName,
            String lastName,
            String email,
            String phone) {
        ExtentManager.getTest().log(Status.INFO, testCaseId + " — Keyword: " + keyword + ", Level: " + level + ", Language: " + language);
 
        HomePage home = new HomePage(driver);
        home.searchCourse(keyword);
 
        SearchResultsPage results = new SearchResultsPage(driver);
        results.applyFilterByText(level);
        results.applyFilterByText(language);
        ExtentManager.getTest().log(Status.INFO, "Applied filters: " + level + ", " + language);
 
        List<CourseData> topCourses =results.extractTopCourses(2);
        ExtentManager.getTest().log(Status.INFO, "Extracted: " + topCourses);
 
        Assert.assertTrue(topCourses.size() > 0, testCaseId + ": expected at least 1 course extracted");
        for (CourseData course : topCourses) {
            ExcelUtils.appendResult(EXCEL_PATH, "TestResults", testCaseId, "COURSE",
                    course.getName(), course.getDuration(), course.getRating(), "PASS");
        }
        ExtentManager.getTest().pass(testCaseId + ": " + topCourses.size() + " courses extracted for \"" + keyword + "\"");
    }
}