package libs;

import factory.CommandFactory;

public class LIB_01 {
	public final static void openURL(final CommandFactory caller, final String waitTime) {
		caller.open("TestPage.openURL", waitTime);
	}
}
