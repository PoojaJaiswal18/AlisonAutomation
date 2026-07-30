package listeners;

import tests.BaseTest;
import com.aventstack.extentreports.reporter.configuration.Theme;
import utils.ScreenshotUtils;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestListener implements ITestListener {

    private ExtentReports extent;
    public static ExtentTest test;;

 
    @Override
    public void onStart(ITestContext context) {

        ExtentSparkReporter spark =new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/ExtentReport.html");

        spark.config().setDocumentTitle("Alison Automation Report");

        spark.config().setReportName("Alison Automation");

        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();

        extent.attachReporter(spark);

        extent.setSystemInfo("Framework","Selenium + TestNG + POM");

        extent.setSystemInfo("Application", "Alison");

        extent.setSystemInfo("Reported By","Pooja Jaiswal");

        System.out.println("Suite started : " + context.getName());
    }
   
    @Override
    public void onTestStart(ITestResult result) {

        String testName ="USER "+ result.getParameters()[0].toString().replace("TC_", "")+ " JOURNEY";

        test = extent.createTest(testName);

        test.info("Executing : " + testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {

        test.pass("Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail(result.getThrowable());
        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver =baseTest.getDriver();

        if (driver != null) {

            String path =ScreenshotUtils.capture( driver,result.getMethod().getMethodName());

            try {

                test.addScreenCaptureFromPath(path);

            } catch (Exception e) {
            }
        }  
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.skip("Test Skipped");

    
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        System.out.println("Suite finished : "+ context.getName());
    }
    
    public static void logInfo(String message) {

        if (test != null) {

            test.info(message);
        }
    }

    public static void logPass(String message) {

        if (test != null) {

            test.pass(message);
        }
    }

    public static void logFail(String message) {

        if (test != null) {

            test.fail(message);
        }
    }

   


}