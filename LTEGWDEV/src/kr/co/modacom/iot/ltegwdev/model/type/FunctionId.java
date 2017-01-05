package kr.co.modacom.iot.ltegwdev.model.type;

public enum FunctionId {
	JOIN_ACCOUNT(10100100, "joinAccount"),
	USER_LIST(10110100, "userList"),
	USER_INFO(10110200, "userInfo"),
	CHECK_DUPLICATION(10110300, "checkDuplication"),
	EDIT_ACCOUNT(10120100, "editAccount"),
	LOGIN(10120200, "login"),
	LOGOUT(10120300, "logout"),
	APPROVAL_ACCOUNT(10120400, "approvalAccount"),
	REJECT_ACCOUNT(10120500, "rejectApproval"),			
	TRANSFER_RIGHT(10120600, "transferRight"),
	DELETE_ACCOUNT(10130100, "deleteAccount"),
	SECESSION(10130200, "secession"),
	
	ADD_DEVICE(11100100, "addDevice"),
	DEVICE_PAIRING(11110100, "devicePairing"),
	DEVICE_UNPAIRING(11110200, "deviceUnpairing"),
	DEVICE_LIST(11110300, "deviceList"),
	DEVICE_HISTORY(11110400, "deviceHistory"),
	DEVICE_INFO(11110500, "deviceInfo"),
	DEVICE_CURRENT_STATE(11110600, "deviceCurrentState"),
	DEVICE_LAST_STATE(11110700, "deviceLastState"),
	EDIT_DEVICE(11120100, "editDevice"),
	UPDATED_DEVICE(11120200, "updatedDevice"),
	DELETE_DEVICE(11130100, "deleteDevice"),
	DEVICE_CONTROL(11140100, "deviceControl"),
	ADD_MODE(12100100, "addMode"),
	MODE_LIST(12110100, "modeList"),
	MODE_INFO(12110200, "modeInfo"),
	MODE_HISTORY_LIST(12110300, "modeHistoryList"),
	EDIT_MODE(12120100, "editMode"),
	ACTIVE_MODE(12120200, "activeMode"),
	DEACTIVE_MODE(12120300, "deactiveMode"),
	DELETE_MODE(12130100, "deleteMode"),
	MODE_EXCUTION(12140100, "modeExcution"),
	
	INTERNET_CONNECTION_STATE(13110100, "internetConnectionState"),
	UPDATE_INTERNET_CONNECTION(13120100, "updateInternetConnection"),
	WIFI_STATE(13110200, "wifiState"),
	UPDATE_WIFI(13120200, "updateWifi"),
	GET_PUSH_CONFIG(13110300, "getPushConfig"),
	SET_PUSH_CONFIG(13120300, "setPushConfig"),
	FIRMWARE_VERSION(13110500, "firmwareVersion"),
	UPGRADE_FIRMWARE(13120600, "upgradeFirmware"),
	UPGRADE_SUPPORT_LIST(13110600, "upgradeSupportList"),
	UPGRADE_SUPPORT_DEVICE(13120700, "upgradeSupportDevice"),
	BEACON_STATE(13110700, "beaconState"),				
	BLE_OBSERVER_STATE(13110800, "bleObserverState"),	
	UPDATE_BEACON(13120800, "updateBeacon"),				
	UPDATE_BLE_OBSERVER(13120900, "updateBLEObserver"),	
	UPDATED_BLE_GPIO(13121000, "updatedBLEGPIO"),
	
	ADD_PLACE(14100100, "addPlace"),
	ADD_DEVICE_TO_PLACE(14100200, "addDeviceToPlace"),
	PLACE_LIST(14110100, "placeList"),
	EDIT_PLACE(14120100, "editPlace"),
	DELETE_PLACE(14130100, "deletePlace"),
	DELETE_DEVICE_FROM_PLACE(14130200, "deleteDeviceFromPlace"),
	
	SERVICE_LIST(15110100, "serviceList"),
	
	DEVICE_USAGE_LIST(16110100, "deviceUsageList"),
	
	MANUFACTURER_LIST(17110100, "manufacturerList"),
	
	SUPPORT_MODE_LIST(18110100, "supportModeList"),
	
	DEVICE_MANAGER(19140100, "deviceManager"),
	DIAGNOSIS_DM_INFO(19110100, "diagnosisDMInfo");

	private int code;
	private String name;
	
	private FunctionId(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static FunctionId valueOf(int code) {
        switch (code) {
        case 10100100: return JOIN_ACCOUNT;
        case 10110100: return USER_LIST;
        case 10110200: return USER_INFO;
        case 10110300: return CHECK_DUPLICATION;
        case 10120100: return EDIT_ACCOUNT;
        case 10120200: return LOGIN;
        case 10120300: return LOGOUT;
        case 10120400: return APPROVAL_ACCOUNT;
        case 10120500: return REJECT_ACCOUNT;
        case 10120600: return TRANSFER_RIGHT;
        case 10130100: return DELETE_ACCOUNT;
        case 10130200: return SECESSION;
        case 11100100: return ADD_DEVICE;
        case 11110100: return DEVICE_PAIRING;
        case 11110200: return DEVICE_UNPAIRING;
        case 11110300: return DEVICE_LIST;
        case 11110400: return DEVICE_HISTORY;
        case 11110500: return DEVICE_INFO;
        case 11110600: return DEVICE_CURRENT_STATE;
        case 11110700: return DEVICE_LAST_STATE;
        case 11120100: return EDIT_DEVICE;
        case 11120200: return UPDATED_DEVICE;
        case 11130100: return DELETE_DEVICE;
        case 11140100: return DEVICE_CONTROL;
        case 12100100: return ADD_MODE;
        case 12110100: return MODE_LIST;
        case 12110200: return MODE_INFO;
        case 12110300: return MODE_HISTORY_LIST;
        case 12120100: return EDIT_MODE;
        case 12120200: return ACTIVE_MODE;
        case 12120300: return DEACTIVE_MODE;
        case 12130100: return DELETE_MODE;
        case 12140100: return MODE_EXCUTION;
        case 13110100: return INTERNET_CONNECTION_STATE;
        case 13120100: return UPDATE_INTERNET_CONNECTION;
        case 13110200: return WIFI_STATE;
        case 13120200: return UPDATE_WIFI;
        case 13110300: return GET_PUSH_CONFIG;
        case 13120300: return SET_PUSH_CONFIG;
        case 13110500: return FIRMWARE_VERSION;
        case 13120600: return UPGRADE_FIRMWARE;
        case 13110600: return UPGRADE_SUPPORT_LIST;
        case 13120700: return UPGRADE_SUPPORT_DEVICE;
        case 13110700: return BEACON_STATE;
        case 13110800: return BLE_OBSERVER_STATE;
        case 13120800: return UPDATE_BEACON;
        case 13120900: return UPDATE_BLE_OBSERVER;
        case 14100100: return ADD_PLACE;
        case 14100200: return ADD_DEVICE_TO_PLACE;
        case 14110100: return PLACE_LIST;
        case 14120100: return EDIT_PLACE;
        case 14130100: return DELETE_PLACE;
        case 14130200: return DELETE_DEVICE_FROM_PLACE;
        case 15110100: return SERVICE_LIST;
        case 16110100: return DEVICE_USAGE_LIST;
        case 18110100: return SUPPORT_MODE_LIST;
        case 19110100: return DIAGNOSIS_DM_INFO;
        case 19140100: return DEVICE_MANAGER;
        
        }
        throw new IllegalArgumentException("Invalid ID");
	}
}
