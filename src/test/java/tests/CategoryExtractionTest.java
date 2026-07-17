package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BasePage;
import pages.CategoryFilterPage;
import pages.HomePage;
import utils.ExcelUtils;
import utils.ExtentManager;
 
public class CategoryExtractionTest extends BasePage {
 
    @Test(description = "Extract all Language filter options with course counts")
    public void extractLanguageCatalog() {
        HomePage home = new HomePage(driver);
        home.searchCourse("programming");
        ExtentManager.getTest().log(Status.INFO, "Landed on results page to access filter panel");
 
        CategoryFilterPage filterPage = new CategoryFilterPage(driver);
        Map<String, Integer> languageSummary = filterPage.extractFilterOptionsWithCounts("Language");
 
        ExtentManager.getTest().log(Status.INFO, "Language options extracted: " + languageSummary);
        Assert.assertTrue(languageSummary.size() > 0, "Expected at least one language option");
 
        ExcelUtils.writeCategorySummary(config.getProperty("excelPath"), "LanguageSummary", languageSummary);
        ExtentManager.getTest().pass("Language catalog extracted and written to Excel");
    }
}