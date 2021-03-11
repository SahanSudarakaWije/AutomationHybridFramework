package report;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.Helper;

public class ExtendReporter {
	private ExtentReports report;
	private ExtentTest logger;

	public void setExtendReport(String curr_TimeStamp) {
		report = new ExtentReports("./Reports/" + curr_TimeStamp + ".html", true);
	}

	public ExtentReports getExtendReport() {
		return this.report;
	}

	public void setExtendLogger(String testCaseName) {
		logger = report.startTest(testCaseName);
	}

	public ExtentTest getExtendLogger() {
		return this.logger;
	}

	public void setLogInfo(String infoMessage) {
		logger.log(LogStatus.INFO, infoMessage);
	}

	public void setLogPassMessage(String passMessage) {
		logger.log(LogStatus.PASS, passMessage);
	}

	public void setLogFailMessage(String failMessage) {
		logger.log(LogStatus.FAIL, failMessage);
	}

	public void setScreenCapture(WebDriver driver, String ssName) {
		logger.log(LogStatus.INFO, logger.addScreenCapture(Helper.captureScreenshot(driver, ssName)));
	}

	public void setFailScreenCapture(WebDriver driver, String ssName) {
		logger.log(LogStatus.FAIL, logger.addScreenCapture(Helper.captureScreenshot(driver, ssName)));
	}

	public void endTestCreateReport() {
		report.endTest(logger);
	}

	public void flushReport() {
		report.flush();
	}
}
