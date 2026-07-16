package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {
	
	public static String capture(WebDriver driver, String testName) {
		String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName=testName+"_"+timestamp+".png";
		String path="screenshots/"+fileName;
		
		try {
			
			File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File dest=new File(path);
			dest.getParentFile().mkdirs();
			FileUtils.copyFile(src,dest);
		}
		catch(IOException e) {
			
			e.printStackTrace();
		}
		
		return path;
		
	}

}
