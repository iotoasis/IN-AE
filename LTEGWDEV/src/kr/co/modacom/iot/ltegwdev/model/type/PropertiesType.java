package kr.co.modacom.iot.ltegwdev.model.type;

public enum PropertiesType {
	UNKNOWN_PROPERTIES(-1, "unknownProperties"),
	DEVICE_PROPERTIES(1, "deviceProperties"),
	LAMP_PROPERTIES(2, "lampProperties"),
	BINARY_SWITCH_PROPERTIES(3, "binarySwitchProperties"),
	BINARY_SENSOR_PROPERTIES(4, "binarySensorProperties");
	
	int code;
	String name;
	
	private PropertiesType(int code, String name) {
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

	public PropertiesType valueOf(int code) {
		switch (code) {
		case -1: return UNKNOWN_PROPERTIES;
		case 1: return DEVICE_PROPERTIES;
		case 2: return LAMP_PROPERTIES;
		case 3: return BINARY_SWITCH_PROPERTIES;
		case 4: return BINARY_SENSOR_PROPERTIES;
		}
        throw new IllegalArgumentException("Invalid ID");
	}
}
