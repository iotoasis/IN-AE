package kr.co.modacom.iot.ltegwdev.model.type;

public enum ModelInfo {
	LG_UPLUS_IOT_ACCESS_CONTROL ("022c00030001", "LG U+ IoT Access Control"),
	LG_UPLUS_IOT_WALL_SWITCH ("023300490001", "LG U+ IoT Wall Switch (3구)"),
	LG_UPLUS_IOT_WALL_SWITCH_2 ("023300480001", "LG U+ IoT Wall Switch (2구)"),
	LG_UPLUS_IOT_GAS_LOCK ("018b00400001", "LG U+ IoT Gas Lock"),
	LG_UPLUS_IOT_PLUG ("018c00420001", "LG U+ IoT Plug"), // 2로 바꿔
	//LG_UPLUS_IOT_PLUG2 ("018c00420001", "LG U+ IoT Plug"),
	SAMSUNG_DOOR_LOCK ("022e00010001", "Samsung Door Lock");
	
	String code;
	String name;
	
	private ModelInfo(String code, String name) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.name = name;
 	}
	
	public static ModelInfo getModelInfo(String code) {
		if(code.equals("022c00030001"))
			return LG_UPLUS_IOT_ACCESS_CONTROL;
		else if(code.equals("023300490001"))
			return LG_UPLUS_IOT_WALL_SWITCH;
		else if(code.equals("023300480001"))
			return LG_UPLUS_IOT_WALL_SWITCH_2;
		else if(code.equals("018b00400001"))
			return LG_UPLUS_IOT_GAS_LOCK;
		else if(code.equals("018c00420001"))
			return LG_UPLUS_IOT_PLUG;
		/*else if(code.equals("018c00420001"))
			return LG_UPLUS_IOT_PLUG2;*/
		else if(code.equals("022e00010001"))
			return SAMSUNG_DOOR_LOCK;
		 
		throw new IllegalArgumentException("Invalid ID : ");
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
