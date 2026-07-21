package utils;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
 
public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
 
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/SparkReport.html");
            spark.config().setDocumentTitle("Udemy Automation Report");
            spark.config().setReportName("Udemy End-to-End Test Suite");
            spark.config().setTheme(Theme.STANDARD);
 
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM");
            extent.setSystemInfo("Target Site", "Udemy");
        }
        return extent;
    }
 
    public static void setTest(ExtentTest t) { test.set(t); }
    public static ExtentTest getTest() { return test.get(); }
}