package kr.co.modacom.iot.ltegwdev.control;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import kr.co.modacom.iot.ltegwdev.json.JSONDeviceComposer;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.PropertyVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.DevLabel;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.MsgType;
import kr.co.modacom.iot.ltegwdev.model.type.OnOffStatus;
import kr.co.modacom.iot.ltegwdev.model.type.PropertiesType;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.model.type.UiComponentType;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MRecvListener;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.ResourceUtil;

public class LightController {
	private static final String TAG = LightController.class.getSimpleName();
	private ArrayList<PropertyVO> bProperties;
	private static LightController mLightController = null;
	private M2MManager mM2MManager = null;
	
	private static Context mCtx;

	private LightController() {

	}

	public static LightController getInstance(Context ctx) {
		if (mLightController == null)
			mLightController = new LightController();
		setContext(ctx);
		return mLightController;
	}
	
	public static void setContext(Context ctx) {
		mCtx = ctx;
	}

	public void setM2MManager(M2MManager m2mManager) {
		this.mM2MManager = m2mManager;
	}

	public void setM2MRecvListener() {
		mM2MManager.setOnM2MRecvListener(mOnM2MRecvListener);
	}

	public void sendMessage(String message, OnM2MSendListener listener, String mnId) {
		if (mM2MManager != null && mM2MManager.isIsM2MLibInit())
			mM2MManager.sendMessage(mCtx, message, listener, mOnM2MRecvListener, mnId);
	}
	
	public void sendMessageForLight(String message, OnM2MSendListener listener, String mnId) {
		if (mM2MManager != null && mM2MManager.isIsM2MLibInit())
			mM2MManager.sendMessageForLight(mCtx, message, listener, mOnM2MRecvListener, mnId);
	}

	// 가로등/경광등 제어 요청
	public void requestStateUpdate(OnM2MSendListener listener, Target target, OnOffStatus streetLight,
			OnOffStatus warningLight, String mnId, boolean flag) {
		bProperties = new ArrayList<PropertyVO>();

		PropertyVO slProperty = new PropertyVO();
		slProperty.setLabel(DevLabel.STRRET_LIGHT.getName());
		slProperty.setValue(OnOffStatus.OFF.getCode());
		slProperty.setUiComponentType(UiComponentType.BINARY_SENSOR.getCode());
		slProperty.setInstanceId(0);

		PropertyVO wlProperty = new PropertyVO();
		wlProperty.setLabel(DevLabel.WARNING_LIGHT.getName());
		wlProperty.setValue(OnOffStatus.OFF.getCode());
		wlProperty.setUiComponentType(UiComponentType.BINARY_SENSOR.getCode());
		wlProperty.setInstanceId(1);

		if (streetLight != null)
			bProperties.add(slProperty);
		if (warningLight != null)
			bProperties.add(wlProperty);

		for (PropertyVO item : bProperties) {
			if (item.getInstanceId() == 0)
				item.setValue(streetLight.getCode());
			if (item.getInstanceId() == 1)
				item.setValue(warningLight.getCode());
		}
		
		String gatewayId = null;
		
		if(!mnId.equals(Const.SG100_AE_ID))
			gatewayId = mnId.replace( "SG101_IOT_AGENT_", "");

		String jString = JSONDeviceComposer.requestCommand(MsgType.CONTROL_REQUEST, target, FunctionId.DEVICE_CONTROL,
				null, DevCat.DEVICE_MANAGER, null, null, null, bProperties, "00000000",
				PropertiesType.BINARY_SWITCH_PROPERTIES.getCode(), gatewayId);
		if(flag)
			sendMessage(jString, listener, mnId);
		else
			sendMessageForLight(jString, listener, mnId);
	}

	public void getResponse(String msg) {
		sendMessage(FunctionId.UPDATED_DEVICE.getCode(), DevCat.DEVICE_MANAGER, msg);
	}

	private void sendMessage(int type, DevCat devCat, String msg) {
		Intent intent;
		switch (devCat) {
		case DEVICE_MANAGER:
			intent = new Intent(Const.ACTION_LIGHT);
			intent.putExtra(Const.UPDATE_TYPE, type);
			intent.putExtra("msg", msg);
			ResourceUtil.sendBroadcast(intent);
			break;
		default:
			intent = null;
			break;
		}
	}

	private OnM2MRecvListener mOnM2MRecvListener = new OnM2MRecvListener() {

		@Override
		public void onRecv(String msg) {
			getResponse(msg);
		}
	};
}