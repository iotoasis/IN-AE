package kr.co.modacom.iot.ltegwdev.model.type;

public enum DevCat {
	NULL(-1, "NULL"),
	NONE(0, "NONE"),
	ZWAVE(1, "Z-Wave"),
	ALLJOYN(2, "AllJoyn"),
	LIFX(3, "LIFX"),
	HUE(4, "HUE"),
	BLUETOOTH(5, "Bluetooth"),
	WIFI(6, "WiFi"),
	WEMO(7, "WeMO"),
	ZIGBEE(8, "ZigBee"),
	ALLJOYN_NOTIFICATION(9, "AllJoyn Notification"),
	IR(10, "IR"),
	UNKNOWN(11, "UNKNOWN"),
	DEVICE_MANAGER(12, "Device Manager");
	
	private int code;
	private String name;
	
	private DevCat(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static DevCat valueOf(int code) {
        switch (code) {
        case -1: return NULL;
        case 0: return NONE;
        case 1: return ZWAVE;
        case 2: return ALLJOYN;
        case 3: return LIFX;
        case 4: return HUE;
        case 5: return BLUETOOTH;
        case 6: return WIFI;
        case 7: return WEMO;
        case 8: return ZIGBEE;
        case 9: return ALLJOYN_NOTIFICATION;
        case 10: return IR;
        case 11: return UNKNOWN;
        case 12: return DEVICE_MANAGER;
        }
        throw new IllegalArgumentException("Invalid ID");
	}
	
	@Override
	public String toString() {
		if( this == ZWAVE )
			return "Z-Wave";
		else if (this == DevCat.ALLJOYN)
			return "AllJoyn";
		else if (this == DevCat.LIFX)
			return "LIFX";
		else if (this == DevCat.HUE)
			return "HUE";
		else if (this == DevCat.BLUETOOTH)
			return "Bluetooth";
		else if (this == DevCat.WIFI)
			return "WiFi";
		else if (this == DevCat.WEMO)
			return "WeMO";
		else if (this == DevCat.ZIGBEE)
			return "ZigBee";
		else if (this == DevCat.ALLJOYN_NOTIFICATION)
			return "AllJoyn Notification";
		else if (this == DevCat.IR)
			return "IR";
		else if (this == DevCat.UNKNOWN)
			return "UNKNOWN";
		return getName();
	}
}
