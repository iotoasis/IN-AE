package kr.co.modacom.iot.ltegwdev.model.type;

public enum LockUnlockStatus {
	NULL(-1, ""),
	UNLOCK(0, "Unlocked"),
	LOCK(1, "Locked");
	
	int code;
	String name;
	
	private LockUnlockStatus(int code, String name) {
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
	
	public static LockUnlockStatus valueOf(int code) {
		switch (code) {
		case -1: return NULL;
		case 0: return UNLOCK;
		case 1: return LOCK;
		}
		
		throw new IllegalArgumentException("Invalid ID : ");
	}
}
