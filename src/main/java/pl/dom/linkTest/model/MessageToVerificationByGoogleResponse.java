package pl.dom.linkTest.model;

import pl.dom.linkTest.enums.ConfidenceLevel;
import pl.dom.linkTest.enums.ThreatType;

public record MessageToVerificationByGoogleResponse (
		 ThreatType threatType,
		 ConfidenceLevel confidenceLevel
		){}

