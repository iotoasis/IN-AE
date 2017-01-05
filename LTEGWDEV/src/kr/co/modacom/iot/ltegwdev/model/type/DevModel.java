package kr.co.modacom.iot.ltegwdev.model.type;

public enum DevModel {
	DOOR_WINDOW_SENSOR("Door/Window Sensor", "Door/Window Sensor"),
	IOT_DOOR_WINDOW_SENSOR("LG U+ IoT Access Control","LG U+ IoT Access Control"),
	STRIPS_MAZW("Strips-MAZW", "Strips-MAZW"),
	PIR_MOTION_SENSOR("PIR Motion Sensor","PIR Motion Sensor"),
	FOUR_IN_ONE_PIR_SENSOR("4in1 PIR Sensor", "4in1 PIR Sensor"),
	WEMO_MOTION("WeMO Motion", "WeMO Motion"),
	GATEMAN("GATEMAN", "GATEMAN"),
	DOOR_LOCK("Samsung Door Lock", "Samsung Door Lock"),
	GAS_LOCK("LG U+ IoT Gas Lock", "LG U+ IoT Gas Lock"),
	SHOCK_AND_VIBRATION_SENSOR("Shock and Vibration Sensor", "Shock and Vibration Sensor"),
	CO_DETECTOR("CO Detector", "CO Detector"),
	BATTERY_OPERATED_SIREN("Battery Operated Siren", "Battery Operated Siren"),
	IOT_PLUG("LG U+ IoT Plug", "LG U+ IoT Plug"),
	PLUGIN_ON_OFF("Plugin ON/OFF", "Plugin ON/OFF"),
	IN_WALL_ONE_RELAY("IN WALL ONE RELAY", "IN WALL ONE RELAY"),
	IN_WALL_TWO_RELAY("In-wall Two Relay", "In-wall Two Relay"),
	BANDI_WALL_SWITCH("BANDI Wall Switch", "BANDI Wall Switch"),
	WEMO_SWITCH("WeMO SWITCH", "WeMO SWITCH"),
	IOT_LAMP_SWITCH("IoT 전등 스위치", "IoT 전등 스위치"),
	LIFX("LIFX", "LIFX"),
	HUE_LAMP("Hue Lamp", "Hue Lamp"),
	HUE_BRIDGE("Hue Bridge", "Hue Bridge"),
	LG_SMART_TV("LG Smart TV", "LG Smart TV"),
	POWER_MONITOR("Power Monitor", "Power Monitor"),
	REPEATER("Repeater", "Repeater"),
	CURTAIN_MODULE("Curtain Module", "Curtain Module"),
	WALL_SWITCH("LG U+ IoT Wall Switch", "LG U+ IoT Wall Switch");

	private String code;
	private String name;

	private DevModel(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static DevModel getDevModel(String name) {
		if (name.equals("Door/Window Sensor"))
			return DOOR_WINDOW_SENSOR;
		else if (name.equals("LG U+ IoT Access Control"))
			return IOT_DOOR_WINDOW_SENSOR;
		else if (name.equals("Strips-MAZW"))
			return STRIPS_MAZW;
		else if (name.equals("PIR Motion Sensor"))
			return PIR_MOTION_SENSOR;
		else if (name.equals("4in1 PIR Sensor"))
			return FOUR_IN_ONE_PIR_SENSOR;
		else if (name.equals("WeMO Motion"))
			return WEMO_MOTION;
		else if (name.equals("GATEMAN"))
			return GATEMAN;
		else if (name.equals("Door Lock"))
			return DOOR_LOCK;
		else if (name.equals("Gas Lock"))
			return GAS_LOCK;
		else if (name.equals("Shock and Vibration Sensor"))
			return SHOCK_AND_VIBRATION_SENSOR;
		else if (name.equals("CO Detector"))
			return CO_DETECTOR;
		else if (name.equals("Battery Operated Siren"))
			return BATTERY_OPERATED_SIREN;
		else if (name.equals("LG U+ IoT Plug"))
			return IOT_PLUG;
		else if (name.equals("Plugin ON/OFF"))
			return PLUGIN_ON_OFF;
		else if (name.equals("IN WALL ONE RELAY"))
			return IN_WALL_ONE_RELAY;
		else if (name.equals("In-wall Two Relay"))
			return IN_WALL_TWO_RELAY;
		else if (name.equals("BANDI Wall Switch"))
			return BANDI_WALL_SWITCH;
		else if (name.equals("WeMO SWIRCH"))
			return WEMO_SWITCH;
		else if (name.equals("IoT 전등 스위치"))
			return IOT_LAMP_SWITCH;
		else if (name.equals("LIFX"))
			return LIFX;
		else if (name.equals("Hue Lamp"))
			return HUE_LAMP;
		else if (name.equals("Hue Bridge"))
			return HUE_BRIDGE;
		else if (name.equals("LG Smart TV"))
			return LG_SMART_TV;
		else if (name.equals("Power Monitor"))
			return POWER_MONITOR;
		else if (name.equals("Repeater"))
			return REPEATER;
		else if (name.equals("Curtain Module"))
			return CURTAIN_MODULE;
		else
			return null;
		
	}

}
