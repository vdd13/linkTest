package pl.dom.linkTest.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dom.linkTest.configuration.GoogleWebRiskRestClient;
import pl.dom.linkTest.dao.NumberToVerificationMalwareRepo;
import pl.dom.linkTest.enums.BooleanValue;
import pl.dom.linkTest.enums.PhoneVerification;
import pl.dom.linkTest.model.NumberToVerification;
import pl.dom.linkTest.model.NumberToVerificationMalware;

@Service
public class PhoneNumberService {
	
	@Autowired 
	NumberToVerificationMalwareRepo phoneNumberSMSMalwareRepo;
	
	@Autowired
	GoogleWebRiskRestClient googleRiskRestClient;

	public String numberToVerification(NumberToVerification number) {

		Optional<Integer> permitValue = BooleanValue.convertToInteger(number.message());
		if(permitValue.isEmpty())
			return PhoneVerification.INVALID.getDescription();

		Optional<NumberToVerificationMalware> numberData = phoneNumberSMSMalwareRepo.findByPhoneNumber(number.number());
		if(numberData.isEmpty()) {
			NumberToVerificationMalware newPhoneNumberVerification = createPhoneNumberVerification(number, permitValue);
			phoneNumberSMSMalwareRepo.save(newPhoneNumberVerification);
			return PhoneVerification.CREATED.getDescription();
		} else if (isPhoneNumberInDB(permitValue, numberData)){
			changePhoneNumberVerificationData(numberData, permitValue);
			phoneNumberSMSMalwareRepo.save(numberData.get());
			return PhoneVerification.MODIFIED.getDescription();
		}
		return PhoneVerification.NOT_CHANGED.getDescription();
	}

	private NumberToVerificationMalware createPhoneNumberVerification(NumberToVerification numberData, Optional<Integer> permitValue) {
		NumberToVerificationMalware newPhoneNumberVerification = new NumberToVerificationMalware();
		newPhoneNumberVerification.setDate(LocalDate.now());
		newPhoneNumberVerification.setPhoneNumber(numberData.number());
		newPhoneNumberVerification.setVerification(permitValue.get());
		return newPhoneNumberVerification;
	}
	
	private void changePhoneNumberVerificationData(Optional<NumberToVerificationMalware> numberData, Optional<Integer> permitValue) {
		numberData.get().setDate(LocalDate.now());
		numberData.get().setVerification(permitValue.get());
	}

	private boolean isPhoneNumberInDB(Optional<Integer> permitValue, Optional<NumberToVerificationMalware> numberData) {
		return !numberData.get().getVerification().equals(permitValue.get());
	}	
}
