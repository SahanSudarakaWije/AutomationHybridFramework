package testcases;

import org.testng.annotations.Test;

import factory.CommandFactory;
import libs.LIB_01;

public class TS_01 extends CommandFactory {

	@Test
	public final void tc_01() {
		LIB_01.openURL(this, "3000");
	}
}
