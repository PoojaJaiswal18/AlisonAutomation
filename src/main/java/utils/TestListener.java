package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
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
    }
 
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        WebDriver driver = getDriverFromTestInstance(result);
        if (driver != null) {
            String path = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
            test.addScreenCaptureFromPath(path);
        }
    }
 
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().log(Status.SKIP, "Test skipped: " + result.getThrowable());
    }
 
    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
        System.out.println("Suite finished: " + context.getName());
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