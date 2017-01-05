package kr.co.modacom.iot.ltegwdev.model.type;

public enum ConnectStatus {
	NULL(-1, "null"),
	DISCONNECT(0, "disconnect"),
	CONNECT(1, "connect");
	
	int code;
	String name;
	
	private ConnectStatus(int code, String name) {
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

	public static ConnectStatus valueOf(int code) {
		switch (code) {
		case -1: return NULL;
		case 0: return DISCONNECT;
		case 1: return CONNECT;
		default: return NULL;
		}
		
	}
}
