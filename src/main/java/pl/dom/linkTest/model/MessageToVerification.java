package pl.dom.linkTest.model;

public record MessageToVerification (
		Long sender,
		Long recipient,
		String message
		){}
