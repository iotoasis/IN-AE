package kr.co.modacom.iot.ltegwdev.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import kr.co.modacom.iot.ltegwdev.SettingDeviceListVO;

public class PreferenceUtil {
	private static final String PREF_NAME = ResourceUtil.getPackageName() + "_preferences";

	private static final String PREF_SERVER_IP = "pref.server.ip";
	private static final String PREF_SERVER_PORT = "pref.server.port";
	private static final String PREF_SERVER_CSE = "pref.server.cse";
	private static final String PREF_SERVER_GROUP = "pref.server.group";
	private static final String PREF_GATEWAY_ID = "pref.gateway.id";
	private static final String PREF_DEVICE_ID = "pref.device.id";
	private static final String PREF_AE_ID = "pref.ae.id";

	private static final String PREF_EMPLOYEE_ID = "pref.emp.id";
	private static final String PREF_EMPLOYEE_DEPT = "pref.emp.dept";
	private static final String PREF_EMPLOYEE_NAME = "pref.emp.name";

	private static PreferenceUtil mInstance = null;
	private final SharedPreferences sharedPreferences;

	private PreferenceUtil(Context context) {
		// TODO Auto-generated constructor stub
		sharedPreferences = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

	}

	public synchronized static PreferenceUtil getInstance(Context context) {
		if (mInstance == null)
			mInstance = new PreferenceUtil(context);

		return mInstance;
	}

	public String getServerIp() {
		return sharedPreferences.getString(PREF_SERVER_IP, null);
	}

	public void setServerIp(String serverIp) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_SERVER_IP, serverIp);
		editor.commit();
	}

	public String getServerPort() {
		return sharedPreferences.getString(PREF_SERVER_PORT, null);
	}

	public void setServerPort(String serverPort) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_SERVER_PORT, serverPort);
		editor.commit();
	}

	public String getServerCse() {
		return sharedPreferences.getString(PREF_SERVER_CSE, null);
	}

	public void setServerCse(String serverCse) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_SERVER_CSE, serverCse);
		editor.commit();
	}

	public String getServerGroup() {
		return sharedPreferences.getString(PREF_SERVER_GROUP, null);
	}

	public void setServerGroup(String serverGroup) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_SERVER_GROUP, serverGroup);
		editor.commit();
	}
	
	public String getDeviceAeId() {
		return sharedPreferences.getString(PREF_AE_ID, null);
	}
	
	public void setDeviceAeId(String deviceAeId) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_AE_ID, deviceAeId);
		editor.commit();
	}

	public String getGatewayId() {
		return sharedPreferences.getString(PREF_GATEWAY_ID, null);
	}

	public void setGatewayId(String gatewayId) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_GATEWAY_ID, gatewayId);
		editor.commit();
	}

	public String getEmployeeId() {
		return sharedPreferences.getString(PREF_EMPLOYEE_ID, null);
	}

	public void setEmployeeId(String empId) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_EMPLOYEE_ID, empId);
		editor.commit();
	}

	public String getEmployeeDept() {
		return sharedPreferences.getString(PREF_EMPLOYEE_DEPT, null);
	}

	public void setEmployeeDept(String empDept) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_EMPLOYEE_DEPT, empDept);
		editor.commit();
	}

	public String getEmployeeName() {
		return sharedPreferences.getString(PREF_EMPLOYEE_NAME, null);
	}

	public void setEmployeeName(String empName) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_EMPLOYEE_NAME, empName);
		editor.commit();
	}

	public ArrayList<SettingDeviceListVO> getDeviceId() {
		ArrayList<SettingDeviceListVO> list = new ArrayList<SettingDeviceListVO>();

		for (int i = 0; i < 10; i++) {
			if (sharedPreferences.getString(PREF_DEVICE_ID + "_" + i, null) != null)
				list.add(new SettingDeviceListVO(sharedPreferences.getString(PREF_DEVICE_ID + "_" + i, null)));
		}
		return list;
	}

	public void setDeviceId(ArrayList<SettingDeviceListVO> list) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		for (int i = 0; i < 10; i++) {
			editor.remove(PREF_DEVICE_ID + "_" + i);
		}
		for (int i = 0; i < list.size(); i++) {
			editor.putString(PREF_DEVICE_ID + "_" + i, list.get(i).getDeviceId());
		}
		editor.commit();
	}
}
