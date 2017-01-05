package kr.co.modacom.iot.ltegwdev.model;

public class Const {

	public static final String ZWAVE = "ZWave";
	public static final String UPDATE_TYPE = "update_type";
	public static final String USER_ID = "IOT";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_COLON = ":";

	public static String GW_ID = "";
	public static String CSE_ID = "";
	public static String CSE_IP = "";
	public static int CSE_PORT = 0;
	public static int CSE_TIMEOUT = 0;
	
	public static String IN_AE_ID = "";
	public static String SG100_AE_ID = "mit_sg100_iot_agent";
	public static String SG100_CSE_ID = "moda-cse";
	public static String SG101_AE_ID = "IOT_AGENT";
	
	public static final String ACTION_ZWAVE = "kr.co.modacom.iot.zwave";
	public static final String ACTION_ALLJOYN = "kr.co.modacom.iot.alljoyn";
	public static final String ACTION_BLE = "kr.co.modacom.iot.ble";
	public static final String ACTION_DM = "kr.co.modacom.iot.dm";
	public static final String ACTION_DM_101 = "kr.co.modacom.iot.dm101";
	public static final String ACTION_LIGHT = "kr.co.modacom.iot.light";

	public static final int BLE_NOT_SUPPORTED = 0x7f060001;
	public static final int BT_UNAVAILABLE = 0x7f060002;
	public static final int LABEL_IAS_BUTTON = 0x7f060004;
	public static final int LABEL_IBEACON_BUTTON = 0x7f060003;
	public static final int LABEL_STOP_BUTTON = 0x7f060005;
}
