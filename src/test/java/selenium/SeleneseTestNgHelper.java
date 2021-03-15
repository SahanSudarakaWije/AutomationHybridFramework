package selenium;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import factory.DataProviderFactory;
import report.ExtendReporter;
import utility.Helper;

public class SeleneseTestNgHelper extends SeleneseTestBase {
	private WebDriver driver;
	int retryCount = 0;
	int timeOutPeriod = 0;
	File fname = null;
	Ini prefs;
	ExtendReporter extendReporter = new ExtendReporter();

	@BeforeTest(alwaysRun = true)
	public final void setUp() {
		extendReporter.setExtendReport(getCurrentDateAndTime());
	}

	@BeforeSuite(alwaysRun = true)
	public final void readOrInitiate() throws InvalidFileFormatException, IOException {
		fname = new File(
				System.getProperty("user.dir") + File.separator + "Configuration" + File.separator + "config.ini");
		prefs = new Ini(fname);
		System.out.println("Execution Browser : " + prefs.get("globalConfig", "executionBrowser"));
		System.out.println("Execution Environment : " + prefs.get("globalConfig", "executionEnv"));
		retryCount = Integer.parseInt(prefs.get("globalConfig", "retryCount"));
		timeOutPeriod = Integer.parseInt(prefs.get("globalConfig", "timeOutPeriod"));
		System.out.println("Retry Count : " + prefs.get("globalConfig", "retryCount"));
		System.out.println("Time Out Period : " + prefs.get("globalConfig", "timeOutPeriod"));
	}

	public int getRetryCount() {
		return retryCount;
	}

	public int getTimeOutPeriod() {
		return timeOutPeriod;
	}

	@BeforeClass(alwaysRun = true)
	public final void getSelenium() {
		System.out.println("before class executed successfully !");
	}

	@BeforeMethod(alwaysRun = true)
	public final void setTestContext(final Method method) {
		driver = getBrowser(prefs.get("globalConfig", "executionEnv"), prefs.get("globalConfig", "executionBrowser"));
		System.out.println(
				"Executing Test Case : " + method.getDeclaringClass().getSimpleName() + "." + method.getName());
		extendReporter.setExtendLogger(method.getDeclaringClass().getSimpleName() + "." + method.getName());
		if (!prefs.get(prefs.get("globalConfig", "executionEnv"), "url").equals("")) {
			driver.get(prefs.get(prefs.get("globalConfig", "executionEnv"), "url"));
			extendReporter
					.setLogInfo("Running test on URL : " + prefs.get(prefs.get("globalConfig", "executionEnv"), "url"));
		}

	}
	/*
	 * @AfterMethod(alwaysRun = true) public final void closeDriver() {
	 * closeBrowser(driver); }
	 */

	@AfterMethod(alwaysRun = true)
	@Parameters({ "selenium.path", "selenium.browser" })
	public final void tearDown(@Optional("test") String path, @Optional("chrome") final String browser,
			ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				path = captureScreenshot(driver, result.getName());
				extendReporter.setFailScreenCapture(driver, path, result.getTestName() + " Failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterSuite(alwaysRun = true)
	public final void tearDownSuite() {
		try {
			closeBrowser(driver);
			extendReporter.endTestCreateReport();
			extendReporter.flushReport();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public WebDriver getDriver() {
		return driver;
	}

	public ExtendReporter getExtendReporter() {
		return extendReporter;
	}

}
