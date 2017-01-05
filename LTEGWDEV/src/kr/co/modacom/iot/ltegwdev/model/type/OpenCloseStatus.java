package kr.co.modacom.iot.ltegwdev.model.type;

public enum OpenCloseStatus {
	NULL(-1, "null"),
	CLOSE(0, "Close"),
	OPEN(1, "Open");
	
	int code;
	String name;
	
	private OpenCloseStatus(int code, String name) {
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
	
	public static OpenCloseStatus valueOf(int code) {
		switch(code) {
		case -1: return NULL;
		case 0: return CLOSE;
		case 1: return OPEN;
		}
		throw new IllegalArgumentException("Invalid ID : ");
	}
}
