package pl.dom.linkTest.model;

import pl.dom.linkTest.enums.ThreatType;

public class MessageToVerificationByGoogleRequest {

	private String uri; 
	private ThreatType[] threatTypes = new ThreatType[]{  
		ThreatType.SOCIAL_ENGINEERING, 
		ThreatType.MALWARE, 
		ThreatType.UNWANTED_SOFTWARE};
	private boolean allowScan;
	
	public MessageToVerificationByGoogleRequest(String uri, boolean allowScan) {
		this.uri = uri;
		this.allowScan = allowScan;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public ThreatType[] getThreatTypes() {
		return threatTypes;
	}
	public void setThreatTypes(ThreatType[] threatTypes) {
		this.threatTypes = threatTypes;
	}
	public boolean isAllowScan() {
		return allowScan;
	}
	public void setAllowScan(boolean allowScan) {
		this.allowScan = allowScan;
	}
}
