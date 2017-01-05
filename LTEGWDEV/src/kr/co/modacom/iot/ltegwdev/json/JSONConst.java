package kr.co.modacom.iot.ltegwdev.json;


public class JSONConst {
	
	
	/************************************************
	 * JSON message related definitions
	 ************************************************/
	/*
	 * JSON Message header & content body part
	 */
	// JSON name definitions for message header
	public static final String HEADER_NAME_RESPONSE_CODE = "rspCode";
	public static final String HEADER_NAME_OPERATION = "op";
	public static final String HEADER_NAME_MSG_TYPE = "msgType";
	public static final String HEADER_NAME_TO = "to";
	public static final String HEADER_NAME_FROM = "from";
	public static final String HEADER_NAME_MESSAGE_ID = "msgId";
	public static final String HEADER_NAME_SESSION_ID = "sessionId";
	public static final String HEADER_NAME_FUNCTION_ID = "functionId";
	public static final String HEADER_NAME_OTIME = "oTime";
	public static final String HEADER_NAME_FUNC_CATEGORY = "funcCat";
	public static final String HEADER_NAME_SUB_CATEGORY = "subCat";
	public static final String HEADER_NAME_DEVICE_CATEGORY = "devCat";
	public static final String HEADER_NAME_DEVICE_TYPE = "devType";
	public static final String HEADER_NAME_SUB_DEVICE_TYPE = "subDevType";
	
	public static final String HEADER_NAME_CONTENT = "ct";
	 
	public static final String CONTENT_NAME_GATEWAY_ID = "gwId";
	public static final String CONTENT_NAME_SERVICE_ID = "serviceId";
	public static final String CONTENT_NAME_DEVICE_CATEGORY = "devCat";
	public static final String CONTENT_NAME_DEVICE_USAGE = "devUsage";
	public static final String CONTENT_NAME_MANUFACTURER_ID = "manufactuerId";
	public static final String CONTENT_NAME_MODEL_NAME = "modelName";
	public static final String CONTENT_NAME_MODEL_INFO = "modelInfo";
	public static final String CONTENT_NAME_DEVICE_ID = "deviceId";
	public static final String CONTENT_NAME_DEVICE_NAME = "deviceName";
	public static final String CONTENT_NAME_DEVICE_LIST = "deviceList";
	public static final String CONTENT_NAME_LAST_UPDATE_TIME = "lastUpdateTime";
	public static final String CONTENT_NAME_USER_ID = "userId";
	public static final String CONTENT_NAME_REGI_DATE = "regiDate";
	public static final String CONTENT_NAME_PROPERTIES_TYPE = "propertiesType";
	public static final String CONTENT_NAME_PROPERTIES = "properties";
	public static final String CONTENT_NAME_GATEWAY = "gateway";
	public static final String CONTENT_NAME_THINGS = "things";
	
	public static final String PROPERTIES_NAME_VALUE = "value";
	public static final String PROPERTIES_NAME_LABEL = "label";
	public static final String PROPERTIES_NAME_UNIT = "unit";
	public static final String PROPERTIES_NAME_INSTANCE_ID = "instanceId";
	public static final String PROPERTIES_NAME_UI_COMPONENT_TYPE = "uiComponentType";
	
	public static final String MESSAGE_ID_PATTERN = "yyyyMMddHHmmssSSS";
	public static final String OTIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";

	public static final String GATEWAY_NAME_WAN = "WAN";
	public static final String GATEWAY_NAME_MAC = "MAC";
	public static final String GATEWAY_NAME_DIP = "DIP";
	public static final String GATEWAY_NAME_PIP = "PIP";
	public static final String GATEWAY_NAME_FW_VER = "FWVer";
	public static final String GATEWAY_NAME_PAIRING_N = "PairingN";
	public static final String GATEWAY_NAME_ZWAVE_STATUS = "ZWaveStatus";
	public static final String GATEWAY_NAME_SSID = "SSID";
	public static final String GATEWAY_NAME_RSSI = "RSSI";
	public static final String GATEWAY_NAME_RUNNING_TIME = "RunningTime";
	public static final String GATEWAY_NAME_SESSION_STATUS = "SessionStatus";
	public static final String GATEWAY_NAME_LATITUDE = "latitude";
	public static final String GATEWAY_NAME_LONGITUDE = "longitude";
	public static final String THINGS_NAME_MODEL_INFO = "modelInfo";
	public static final String THINGS_NAME_STATUS = "Status";
	public static final String THINGS_NAME_BATTERY = "Battery";
	
	public static final int RESULT_OK =	1;
	public static final int RESULT_FAIL = 0;
	public static final String NULL = "null";
}