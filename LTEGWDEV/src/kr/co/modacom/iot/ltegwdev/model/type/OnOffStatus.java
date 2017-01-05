package kr.co.modacom.iot.ltegwdev.model.type;


public enum OnOffStatus {
	NULL(-1, "null"),
	OFF(0, "Off"), 
	ON(1, "On");
	
	private int code;
	private String name;
	
	private OnOffStatus(int code, String name) { 
		this.code = code;
		this.name = name;
	}
	
	public int getCode() { return code; }
	public String getName() { return name; }
	
	public static OnOffStatus valueOf(int code) {
		switch (code) {
		case -1: return NULL;
		case 0: return OFF;
		case 1: return ON;
		}
		throw new IllegalArgumentException("Invalid ID");
	}			
}
