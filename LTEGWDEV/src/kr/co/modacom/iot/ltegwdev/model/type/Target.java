package kr.co.modacom.iot.ltegwdev.model.type;

public enum Target {
	ALL("ALL", "ALL"),
	AGENT("AGENT", "AGENT"),
	ALLJOYN("ALLJOYN", "AllJoyn"),
	BLE("BLE", "BLE"),
	BLUETOOTH("BLUETOOTH", "BLUETOOTH"),
	HUE("HUE", "Hue"),
	LIFX("LIFX", "LIFX"),
	WEBSERVER("WEBSERVER", "WebServer"),
	OMNIPASS("OMNIPASS", "OMNIPASS"),
	OMNIPUB("OMNIPUB", "OMNIPUB"),
	ONEM2M("ONEM2M", "ONEM2M"),
	URC("URC", "URC"),
	ZIGBEE("ZIGBEE", "ZigBee"),
	ZWAVE("ZWAVE", "Z-Wave"),
	WIFI("WIFI", "WIFI"),
	DEV_MGR("DEV_MGR", "DEV_MGR"),
	DM("DM","DM");
	
	String code;
	String name;
	
	private Target(String code, String name) {
		// TODO Auto-generated constructor stub
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
