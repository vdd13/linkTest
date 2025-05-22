package pl.dom.linkTest.service;

import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dom.linkTest.dao.NumberToVerificationMalwareRepo;
import pl.dom.linkTest.configuration.GoogleWebRiskRestClient;
import pl.dom.linkTest.model.NumberToVerificationMalware;
import pl.dom.linkTest.enums.BooleanValue;
import pl.dom.linkTest.enums.ConfidenceLevel;
import pl.dom.linkTest.model.MessageToVerification;
import pl.dom.linkTest.model.MessageToVerificationByGoogleRequest;
import pl.dom.linkTest.model.MessageToVerificationByGoogleResponse;

@Service
public class GoogleWebRiskService {

	@Autowired 
	NumberToVerificationMalwareRepo phoneNumberSMSMalwareRepo;
	
	@Autowired
	GoogleWebRiskRestClient googleRiskRestClient;
	
	private final int URI_LENGHT_MIN = 8;
	private final String NOT_VERIFIED = "NOT VERIFIED";
	
	public String messageToVerification(MessageToVerification message) {
		if(numberToCheckMalware(message.recipient())) 
			return findMalwareInText(message);
		return NOT_VERIFIED;
	}
	
	private String findMalwareInText(MessageToVerification message) {
		StringTokenizer stringTokenizer = new StringTokenizer(message.message());
		while(stringTokenizer.hasMoreTokens()) {
			String text = stringTokenizer.nextToken().toLowerCase();
			if(text.contains("http") && text.length() > URI_LENGHT_MIN) {
				MessageToVerificationByGoogleRequest messageToVerificationByGoogle = new MessageToVerificationByGoogleRequest(text, true);;
				MessageToVerificationByGoogleResponse response = sendMessageToVerifyByGoogle(messageToVerificationByGoogle);
				
				if(!ConfidenceLevel.SAFE.name().equals(response.confidenceLevel().name())){
					return response.confidenceLevel().name();
				}
			}
		}
		return ConfidenceLevel.SAFE.name();
	}

	private MessageToVerificationByGoogleResponse sendMessageToVerifyByGoogle(MessageToVerificationByGoogleRequest messageToVerificationByGoogle) {
		return (MessageToVerificationByGoogleResponse) googleRiskRestClient.getRestClient()
		.post()
		.body(messageToVerificationByGoogle)
		.retrieve()
		.toEntity(MessageToVerificationByGoogleResponse.class).getBody();
	}
	
	private boolean numberToCheckMalware(Long number) {
		Optional<NumberToVerificationMalware> numberData = phoneNumberSMSMalwareRepo.findByPhoneNumber(number);
		if(!numberData.isEmpty() && BooleanValue.START.getValue().equals(numberData.get().getVerification()))
			return true;
		return false;
	}

}
