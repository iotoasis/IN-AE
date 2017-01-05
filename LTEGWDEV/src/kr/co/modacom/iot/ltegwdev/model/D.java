package kr.co.modacom.iot.ltegwdev.model;


public class D {

	public static final class param {
		
		// JSON Header & content
		public static final String INPUT_VALUE = "iv";
		public static final String OUTPUT_VALUE = "rr";
		
		// 공통 파라미터 명
		public static final String TITLE = "title";
		
		public static final String ON_OFF_STATUS = "onOffStatus";
		
		//device 관리
		public static final String DEVICE_SUB_CAT = "subCat";
		
		public static final String DEVICE_ITEM = "DeviceItem";
		public static final String DEVICE_UNIQ_ID = "deviceUniqId";
		public static final String DEVICE_ID = "deviceId";
		public static final String DEVICE_PARENT_ID = "parentDeviceId";
		public static final String DEVICE_NAME = "deviceName";
		public static final String DEVICE_CAT = "devCat";
		public static final String DEVICE_CATEGORY = "devCategory";
		public static final String DEVICE_TYPE = "devType";
		public static final String DEVICE_SUB_TYPE = "subDevType";
		public static final String DEVICE_MANUFACTURER_ID = "manufacturerId";
		public static final String DEVICE_PARAMS = "devSpecificParams";
		public static final String DEVICE_MODEL_NAME = "modelName";
		public static final String DEVICE_CONN_STATUS = "connectionStatus";
		public static final String DEVICE_SERIAL_NO = "serialNo";
		public static final String DEVICE_INSTALL_LOCATION = "installLocation";
		public static final String DEVICE_LIST = "deviceList";
		
		public static final String ZWAVE_HOME_ID = "HomeID";
		public static final String ZWAVE_NODE_ID = "deviceId";
		public static final String ZWAVE_BASIC = "Basic";
		public static final String ZWAVE_GENERIC = "Generic";
		public static final String ZWAVE_SPECIFIC = "Specific";
		public static final String ZWAVE_LISTENING = "Listening";
		public static final String ZWAVE_FREQUENT_KISTENING = "FrequentListening";
		public static final String ZWAVE_BEAMING = "Beaming";
		public static final String ZWAVE_ROUTING = "Routing";
		public static final String ZWAVE_SECURITY = "Security";
		public static final String ZWAVE_MAXBAUDRATE = "MaxBaudRate";
		public static final String ZWAVE_VERSION = "Version";
		
		public static final String ZWAVE_VALUE = "Value";
		public static final String ZWAVE_LABEL = "Label";
		public static final String ZWAVE_UNIT = "Unit";
		public static final String ZWAVE_BATTERY = "Battery";
		public static final String PAIRING_STATUS = "Pairing";
	}
	
	public static final class delimiter {
		
	    public static String COLON = ":";
	    public static String SEMICOLON = ";";
	    public static String CR = "\n";
	    public static String TAB = "\t";
	    public static String DTAB = "\t\t";		
	    public static String SPACE = " ";
	    public static String PERIOD = ".";
	    public static String COMMA = ",";
	}
}
