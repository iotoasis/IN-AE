package kr.co.modacom.iot.ltegwdev.model.type;

public enum MsgType {
	REQUEST(1, "Request"),
	RESPONSE(2, "Response"),
	NOTIFY(3, "Notify"),
	SEGMENT(4, "Segment"),
	CONTROL_REQUEST(5, "Control Request");
	
	private int code;
	private String name;
	
	private MsgType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static MsgType valueOf(int code) {
        switch (code) {
        case 1: return REQUEST;
        case 2: return RESPONSE;
        case 3: return NOTIFY;
        case 4: return SEGMENT;
        case 5: return CONTROL_REQUEST;
        }
        throw new IllegalArgumentException("Invalid ID");
	}
}
