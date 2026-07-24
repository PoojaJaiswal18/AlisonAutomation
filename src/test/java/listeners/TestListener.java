package listeners;

import tests.BaseTest;
import utils.ExcelUtils;
import utils.ScreenshotUtils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    private ExtentReports extent;
    private ExtentTest test;

 
    @Override
    public void onStart(ITestContext context) {

        ExtentSparkReporter spark =new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/ExtentReport.html");

        spark.config().setDocumentTitle("Alison Automation Report");

        spark.config().setReportName("Alison End-to-End Automation Suite");

        spark.config().setTheme( com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        extent = new ExtentReports();

        extent.attachReporter(spark);

        extent.setSystemInfo("Framework","Selenium + TestNG + POM");

        extent.setSystemInfo("Application", "Alison");

        extent.setSystemInfo("Reported By","Pooja Jaiswal");

        System.out.println("Suite started : " + context.getName());
    }
    @Override
    public void onTestStart(ITestResult result) {

        test =extent.createTest(result.getName());

        test.info( "Executing : "+ result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.pass("Test Passed");

        logToExcel(result,"PASS","");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail(result.getThrowable());

        WebDriver driver =getDriver(result);

        if (driver != null) {

            String path =ScreenshotUtils.capture( driver,result.getMethod().getMethodName());

            try {

                test.addScreenCaptureFromPath(path);

            } catch (Exception e) {
            }
        }

        logToExcel(result,"FAIL", result.getThrowable() != null? result.getThrowable().getMessage(): "");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.skip("Test Skipped");

        logToExcel(result,"SKIP","");
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        System.out.println("Suite finished : "+ context.getName());
    }

    private void logToExcel(ITestResult result, String status,String notes) {

        Object[] params =result.getParameters();

        String testCaseId =(params != null && params.length > 0)? String.valueOf(params[0]): result.getMethod().getMethodName();

       
    }

    private WebDriver getDriver(ITestResult result) {

        try {

            Object instance =result.getInstance();

            Field driverField =instance.getClass().getSuperclass().getDeclaredField("driver");

            driverField.setAccessible(true);

            return (WebDriver)driverField.get(instance);

        } catch (Exception e) {

            return null;
        }
    }
}