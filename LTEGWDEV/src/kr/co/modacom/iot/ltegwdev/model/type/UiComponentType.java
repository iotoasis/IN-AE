package kr.co.modacom.iot.ltegwdev.model.type;

public enum UiComponentType {
	BINARY_SENSOR(1, "binarySensor"),
	BINARY_SWITCH(2, "binarySwitch"),
	RANGE_SENSOR(3, "rangeSensor"),
	RANGE_SWITCH(4, "rangeSwitch"),
	OTHERS(5, "others");
	
	int code;
	String name;
	
	private UiComponentType(int code, String name) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public UiComponentType valueOf(int code) {
		switch (code) {
		case 1: return BINARY_SENSOR;
		case 2: return BINARY_SWITCH;
		case 3: return RANGE_SENSOR;
		case 4: return RANGE_SWITCH;
		case 5: return OTHERS;
		}
        throw new IllegalArgumentException("Invalid ID");
	}
}
