package pl.dom.linkTest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import pl.dom.linkTest.model.NumberToVerification;
import pl.dom.linkTest.model.MessageToVerification;
import pl.dom.linkTest.service.GoogleWebRiskService;
import pl.dom.linkTest.service.PhoneNumberService;

@RestController
@RequestMapping("messageVerification")
public class TextVerificationControler {

	@Autowired
	GoogleWebRiskService googleRiskService;
	
	@Autowired 
	PhoneNumberService numberService;
	
	@PostMapping(path="textToVerification", consumes="application/json")
	public ResponseEntity<String> messageToVerification(@RequestBody MessageToVerification message){
		String response = googleRiskService.messageToVerification(message);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping(path="numberToVerification", consumes="application/json")
	public ResponseEntity<String> numberToVerification(@RequestBody NumberToVerification number){
		String response = numberService.numberToVerification(number);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleHTTPClientError(HttpClientErrorException e){
        if (e.getStatusCode() == HttpStatus.FORBIDDEN){
        	return new ResponseEntity<Map<String, Object>>(
            		Map.of("status",HttpStatus.FORBIDDEN.name(), "message", "Forbidden access"), HttpStatus.FORBIDDEN);
	    } 
	    else 
	    	return new ResponseEntity<Map<String,Object>>(
	    			Map.of("status", "4xx", "message", "Client Error"), HttpStatus.NOT_ACCEPTABLE);
    }
}
