package utils;
 
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
 
import java.lang.reflect.Field;
 
public class TestListener implements ITestListener {
 
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Suite started: " + context.getName());
    }
 
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentTest extentTest = ExtentManager.getInstance().createTest(
                testName, description != null ? description : ""
        );
        ExtentManager.setTest(extentTest);
    }
 
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
        logToExcel(result, "PASS", "");
    }
 
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentManager.getTest();
        String errorMsg = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());
 
        WebDriver driver = getDriverFromTestInstance(result);
        if (driver != null) {
            String path = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
            test.addScreenCaptureFromPath(path);
        }
        logToExcel(result, "FAIL", errorMsg);
    }
 
    @Override
    public void onTestSkipped(ITestResult result) {
        String reason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped";
        ExtentManager.getTest().log(Status.SKIP, "Test skipped: " + result.getThrowable());
        logToExcel(result, "SKIP", reason);
    }
 
    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
        System.out.println("Suite finished: " + context.getName());
    }
 
    private void logToExcel(ITestResult result, String status, String notes) {
        Object[] params = result.getParameters();
        // TestCaseID is the first @DataProvider parameter when present; falls back to the method name
        // for CategoryExtractionTest, which has no data provider.
        String testCaseId = (params != null && params.length > 0) ? String.valueOf(params[0]) : result.getMethod().getMethodName();
        ExcelUtils.appendResult(BasePage.EXCEL_PATH, "TestResults", testCaseId, "RUN_STATUS", notes, "", "", status);
    }
 
    private WebDriver getDriverFromTestInstance(ITestResult result) {
        try {
            Object instance = result.getInstance();
            Field driverField = instance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(instance);
        } catch (Exception e) {
            return null;
        }
    }
}