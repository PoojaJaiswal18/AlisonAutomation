package tests;

import com.aventstack.extentreports.Status;
import base.BasePage;
import pages.HomePage;
import pages.CategoryFilterPage;
import utils.ExcelUtils;
import utils.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class CategoryExtractionTest extends BasePage {

    @DataProvider(name = "categoryProvider")
    public Object[][] categoryProvider() {
        return ExcelUtils.readTestCaseData(EXCEL_PATH);
    }

    @Test(
        dataProvider = "categoryProvider",
        description = "Extract Language filter options and corresponding course counts"
    )
    public void extractCategoryCatalog(
            String testCaseId,
            String keyword,
            String level,
            String language,
            String firstName,
            String lastName,
            String email,
            String phone) {

        System.out.println("====================================");
        System.out.println("[TEST CASE] " + testCaseId);
        System.out.println("[STEP] Searching Keyword : " + keyword);
        System.out.println("====================================");

        HomePage home = new HomePage(driver);
        home.searchCourse(keyword);

        ExtentManager.getTest().log(
                Status.INFO,
                "Search performed using keyword: " + keyword);

        CategoryFilterPage filterPage =
                new CategoryFilterPage(driver);

        Map<String, Integer> summary =
                filterPage.extractFilterOptionsWithCounts("Language");

        ExtentManager.getTest().log(
                Status.INFO,
                "Language options extracted: " + summary);

        Assert.assertTrue(
                summary.size() > 0,
                "No Language filter options found");

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {

            ExcelUtils.appendResult(
                    EXCEL_PATH,
                    "TestResults",
                    testCaseId,
                    "CATEGORY",
                    entry.getKey(),
                    String.valueOf(entry.getValue()),
                    "",
                    "PASS");
        }

        ExtentManager.getTest().pass(
                testCaseId + " : Extracted "
                        + summary.size()
                        + " language options successfully");
    }
}