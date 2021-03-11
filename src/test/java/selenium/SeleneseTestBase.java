package selenium;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.relevantcodes.extentreports.ExtentTest;

import factory.DataProviderFactory;

public class SeleneseTestBase {
	private WebDriver driver = null;

	public WebDriver getBrowser(String environment, String browserName) {
		if (driver == null) {
			if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						DataProviderFactory.getConfig().getBrowserDriverPath(environment, "firefox"));
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						DataProviderFactory.getConfig().getBrowserDriverPath(environment, "chrome"));
				driver = new ChromeDriver();
			} else {
				System.setProperty("webdriver.gecko.driver",
						DataProviderFactory.getConfig().getBrowserDriverPath(environment, "firefox"));
				driver = new FirefoxDriver();
			}
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;

	}

	public void closeBrowser(WebDriver driver) {
		driver.close();
		driver.quit();
	}

	public static String captureScreenshot(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator
				+ screenshotName + System.currentTimeMillis() + ".png";
		try {
			FileUtils.copyFile(src, new File(destination));
		} catch (IOException e) {
			System.out.println("Failed to capture screenshot. " + e.getMessage());
		}
		return destination;
	}

	public final void sleep(final int milisec) {
		try {
			Thread.sleep(milisec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
