package kr.co.modacom.iot.ltegwdev.model.type;

public enum ServiceId {
	SECURITY(1, "Security"),
	POWER_AND_SWITCH(2, "Power And Switch"),
	LIFE_AND_ENVIRONMENT(3, "Life And Environment"),
	MULTI_SENSING(4, "Multi-Sencing"),
	OTHERS(5, "Others");
	
	private int code;
	private String name;
	
	private ServiceId(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static ServiceId valueOf(int code) {
        switch (code) {
        case 1: return SECURITY;
        case 2: return POWER_AND_SWITCH;
        case 3: return LIFE_AND_ENVIRONMENT;
        case 4: return MULTI_SENSING;
        case 5: return OTHERS;
        }
        throw new IllegalArgumentException("Invalid ID");
	}
}
