package pl.dom.linkTest.enums;

import java.util.Optional;

public enum BooleanValue {
	
	START (1),
	STOP (0);
	
	private final Integer value;
	
	BooleanValue(Integer value) {
		this.value = value;
	}
	
	public static Optional<Integer> convertToInteger(String name) {
		for(BooleanValue value : BooleanValue.values()) {
			if(value.name().equals(name))
				return Optional.of(value.value);
		}
		return Optional.empty();
	}
	
	public static Optional<String> convertToString(Integer number) {
		for(BooleanValue value : BooleanValue.values()) {
			if(value.value.equals(number))
				return Optional.of(value.name());
		}
		return Optional.empty();
	}
	
	public Integer getValue() {
		return value;
	}
	
}
