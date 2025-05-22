package pl.dom.linkTest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GoogleWebRiskRestClient {

	@Value("${application.googleUri}")
	private String uri;
	
	private RestClient googleRiskRestClient;
	
	public RestClient getRestClient() {		
		googleRiskRestClient = RestClient.builder()
			.baseUrl(uri)
			.defaultHeader("Accept", "application/json")
			.build();
		return googleRiskRestClient;
	}
}
