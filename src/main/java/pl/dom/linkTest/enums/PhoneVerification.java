package pl.dom.linkTest.enums;

public enum PhoneVerification {

	INVALID ("data - invalid"),
	CREATED ("data - created"),
	MODIFIED ("data - modified"),
	NOT_CHANGED("data - not changed");

	private final String description;
	
	PhoneVerification(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
