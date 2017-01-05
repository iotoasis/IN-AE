package kr.co.modacom.iot.ltegwdev.model.type;

public enum ManufacturerId {
	VISION(1, "Vision"),
	REMOTE_SOLUTION(2, "R@mote Solution"),
	SENSATIVE(3, "SENSATIVE"),
	BELKIN(4, "belkin"),
	ASSA_ABOLY(5, "ASSA ABOLY"),
	DANALOCK(6, "danalock"),
	RAMAX(7, "RaMaX"),
	DAWON_DNS(8, "DAWON DNS"),
	BANDI_COMMTECH(9, "BANDI Commtech"),
	PARA_ENT(10, "PARA ENT"),
	LIFX(11, "LIFX"),
	PHILLIPSE(12, "PHILLIPSE"),
	LG(13, "LG"),
	SAMSUNG(14, "Samsung");
	
	private int code;
	private String name;
	
	private ManufacturerId(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static ManufacturerId valueOf(int code) {
        switch (code) {
        case 1: return VISION;
        case 2: return REMOTE_SOLUTION;
        case 3: return SENSATIVE;
        case 4: return BELKIN;
        case 5: return ASSA_ABOLY;
        case 6: return DANALOCK;
        case 7: return RAMAX;
        case 8: return DAWON_DNS;
        case 9: return BANDI_COMMTECH;
        case 10: return PARA_ENT;
        case 11: return LIFX;
        case 12: return PHILLIPSE;
        case 13: return LG;
        case 14: return SAMSUNG;
        }
        throw new IllegalArgumentException("Invalid ID");
	}
}
