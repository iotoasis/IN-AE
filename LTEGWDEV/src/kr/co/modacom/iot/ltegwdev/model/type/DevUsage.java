package kr.co.modacom.iot.ltegwdev.model.type;

public enum DevUsage {
	DOOR_WINDOWS_SENSOR(1, "Door/Windows Sensor"),
	TWO_IN_ONE_SENSOR(2, "2-in-1 Sensor"),
	FOUR_IN_ONE_SENSOR (3, "4-in-1 센서"),
	MOTION_SENSOR(4, "Motion Sensor"),
	DOOR_LOCK(5, "Door Lock"),
	GAS_LOCK(6, "Gas Lock"),
	SHOCK_WAVE_SENSOR(7, "Shock Wave Sensor"),
	CARBON_MONOXIDE_SENSOR(8, "Carbon monoxide Sensor"),
	SIREN(9, "Siren"),
	PLUG(10, "Plug"),
	SWITCH(11, "Switch"),
	LAMP(12, "Lamp"),
	TV(13, "TV"),
	ZWAVE_REAPETER(14, "Z-Wave Reapeter"),
	MOTOR_CONTROL(15, "Moter Control");
	
	private int code;
	private String name;
	
	private DevUsage(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static DevUsage valueOf(int code) {
        switch (code) {
        case 1: return DOOR_WINDOWS_SENSOR;
        case 2: return TWO_IN_ONE_SENSOR;
        case 3: return FOUR_IN_ONE_SENSOR;
        case 4: return MOTION_SENSOR;
        case 5: return DOOR_LOCK;
        case 6: return GAS_LOCK;
        case 7: return SHOCK_WAVE_SENSOR;
        case 8: return CARBON_MONOXIDE_SENSOR;
        case 9: return SIREN;
        case 10: return PLUG;
        case 11: return SWITCH;
        case 12: return LAMP;
        case 13: return TV;
        case 14: return ZWAVE_REAPETER;
        case 15: return MOTOR_CONTROL;
        }
        throw new IllegalArgumentException("Invalid ID");
	}
}
