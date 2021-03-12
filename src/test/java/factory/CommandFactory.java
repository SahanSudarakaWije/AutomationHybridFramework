package factory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.ini4j.Ini;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import objectmap.ObjectLocator;
import objectmap.ObjectMap;
import report.ExtendReporter;
import selenium.SeleneseTestNgHelper;

public class CommandFactory extends SeleneseTestNgHelper {
	private int retryInterval;
	ExtendReporter extendReporter;
	// private WebDriver driver;

	public CommandFactory() {
		File fname = new File(
				System.getProperty("user.dir") + File.separator + "Configuration" + File.separator + "config.ini");
		Ini prefs;
		try {
			prefs = new Ini(fname);
			retryInterval = Integer.parseInt(prefs.get("globalConfig", "retryInterval"));
			extendReporter = getExtendReporter();
			// driver = getDriver();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public final void open(final String objectName, final String waitTime) {
		open(objectName, "", waitTime);
	}

	public final void open(final String objectName, final String identifier, final String waitTime) {
		String url = ObjectMap.getResolvedSearchPath(objectName, identifier);
		ObjectLocator locator = new ObjectLocator(url, identifier, url);
		doOpen(locator, waitTime);
	}

	private void doOpen(final ObjectLocator locator, final String waitTime) {
		String url = "";
		WebDriver driver = getDriver();
		url = locator.getActualLocator();
		if (url.equals("")) {
			throw new WebDriverException("Empty URL ! ");
		} else {
			try {
				driver.get(url);
				extendReporter.setLogPassMessage("WAIT : COMMAND PASSED. open " + url);
			} catch (Exception e) {
				extendReporter.setFailScreenCapture(driver, getCurrentDateAndTime(),
						"OPEN : COMMAND FAILED. " + e.toString());
			}

		}

		try {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(waitTime), TimeUnit.MILLISECONDS);
			extendReporter.setLogPassMessage("WAIT : COMMAND PASSED. wait " + waitTime + " milliseconds.");
		} catch (Exception e) {
			extendReporter.setFailScreenCapture(driver, getCurrentDateAndTime(),
					"OPEN : COMMAND FAILED. invalid arguments passed for the wait time");
		}

	}
}
