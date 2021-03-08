package pages;

public enum TestPage {
	tf_UserName("//input[@name='username']"), tf_PassWord("//input[@name='password']"), btn_Submit("//input[@name='submit']");

	private String searchPath;

	private TestPage(final String psearchPath) {
		this.searchPath = psearchPath;
	}

	public final String getSearchPath() {
		return searchPath;
	} 
}
