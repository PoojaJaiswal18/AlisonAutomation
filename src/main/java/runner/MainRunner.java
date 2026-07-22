package runner;
 
import utils.TestListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
public class MainRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.addListener(new TestListener());
 
        XmlSuite suite = new XmlSuite();
        suite.setName("UdemyAutomationSuite");
 
        addTest(
                suite,
                "UdemyUserJourney",
                "tests.AlisonUserJourneyTest");
 
        testng.setXmlSuites(Collections.singletonList(suite));
        testng.run();
    }
 
    private static void addTest(XmlSuite suite, String testName, String className) {
        XmlTest test = new XmlTest(suite);
        test.setName(testName);
        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass(className));
        test.setXmlClasses(classes);
    }
}