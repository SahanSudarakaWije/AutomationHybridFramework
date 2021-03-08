package objectmap;

public class ObjectLocator {

	private String logicalName;
	private String identifier;
	private String actualLocator;

	public ObjectLocator(final String logicalNameString, final String identifierString,
			final String actualLocatorString) {
		this.logicalName = logicalNameString;
		this.identifier = identifierString;
		this.actualLocator = actualLocatorString;
	}

	public final String getLogicalName() {
		return logicalName;
	}

	public final String getIdentifier() {
		return identifier;
	}

	public final void setIdentifier(final String identifierValue) {
		this.identifier = identifierValue;
	}

	public final String getActualLocator() {
		return actualLocator;
	}

	public final void setActualLocator(final String actualLocatorString) {
		this.actualLocator = actualLocatorString;
	}
}
