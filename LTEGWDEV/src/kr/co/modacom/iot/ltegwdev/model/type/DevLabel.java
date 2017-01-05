package kr.co.modacom.iot.ltegwdev.model.type;

public enum DevLabel {
	ON_OFF_STATUS("onOffStatus", "onOffStatus"),
	TEMPERATURE("Temperature", "Temperature"),
	ILLUMINANCE("Illuminance","Illuminance"),
	RELATIVE_HUMIDITY("Relative Humidity", "Relative Humidity"),
	BATTERY_LEVEL("Battery Level", "Battery Level"),
	Energy("Energy","Energy"),
	POWER("Power", "Power"),
	THERMOSTAT("Thermostat","Thermostat"),
	BUGLAR("Buglar","Buglar"),
	TAMPER("Tamper", "Tamper"),
	LOCKED("Locked", "Locked"),
	LOCKED_ADVANCE("Locked(Advanced)", "Locked(Advanced)"),
	ACCESS_CONTROL("Access Control", "Access Control"),
	SMOKE("Smoke", "Smoke"),
	CARBON_MONOXIDE("Carbon Monoxide", "Carbon Monoxide"),
	CARBON_DIOXIDE("Carbon Dioxide", "Carbon Dioxide"),
	FLOOD("Flood", "Flood"),
	SWITCH("Switch", "Switch"),
	STRRET_LIGHT("streetlight", "streetlight"),
	WARNING_LIGHT("warninglight", "warninglight"),
	EMPLOYEE_ID("employeeId", "employeeId"),
	EMPLOYEE_STATUS("employeeStatus", "employeeStatus");
	
	String code;
	String name;
	
	private DevLabel(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}
