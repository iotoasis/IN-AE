package kr.co.modacom.iot.ltegwdev.control;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import kr.co.modacom.iot.ltegwdev.json.JSONConst;
import kr.co.modacom.iot.ltegwdev.json.JSONDeviceComposer;
import kr.co.modacom.iot.ltegwdev.json.JSONMessage;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.DmVO;
import kr.co.modacom.iot.ltegwdev.model.GatewayVO;
import kr.co.modacom.iot.ltegwdev.model.ThingsVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.ModelInfo;
import kr.co.modacom.iot.ltegwdev.model.type.MsgType;
import kr.co.modacom.iot.ltegwdev.model.type.RspCode;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MRecvListener;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.ResourceUtil;

public class DMController {
	private static final String TAG = DMController.class.getSimpleName();

	private static DMController mDmController = null;
	private M2MManager mM2MManager = null;
	private DmVO mDmItem;
	private boolean dmFlag = false;

	private static Context mCtx;
	
	private DMController() {
		// TODO Auto-generated constructor stub
	}
	
	public static void setContext(Context ctx) {
		mCtx = ctx;
	}

	public static DMController getInstance(Context ctx) {
		if (mDmController == null)
			mDmController = new DMController();
		
		setContext(ctx);
		return mDmController;
	}

	public DmVO getDmItem() {
		return mDmItem;
	}
	
	public void setDmItem(DmVO item) {
		mDmItem = item;
	}

	public OnM2MRecvListener getM2MRecvListener() {
		return mOnM2MRecvListener;
	}

	public void setM2MManager(M2MManager m2mManager) {
		this.mM2MManager = m2mManager;
	}

	public void setM2MRecvListener() {
		mM2MManager.setOnM2MRecvListener(mOnM2MRecvListener);
	}
	
	// DM 정보 요청
	public void requestDmInfo(OnM2MSendListener listener, String mnId) {
		String gatewayId = null;
		
		if(!mnId.equals(Const.SG100_AE_ID)) {
			gatewayId = mnId.replace("SG101_IOT_AGENT_", "");
			dmFlag = true;
		} else
			dmFlag = false;
		
			
		String jString = JSONDeviceComposer.requestCommand(MsgType.REQUEST, Target.DM, FunctionId.DIAGNOSIS_DM_INFO,
				null, null, null, null, null, null, null, -1, gatewayId);
		sendMessage(jString, listener, mnId);
	}

	public void sendMessage(String message, OnM2MSendListener listener, String mnId) {
		if (mM2MManager != null && mM2MManager.isIsM2MLibInit())
			mM2MManager.sendMessage(mCtx, message, listener, mOnM2MRecvListener, mnId);
	}

	// 응답 DM Message Parsing
	public void getDmInfo(JSONMessage jMsg) {
		try {
			if (jMsg.getResponseCode() == RspCode.OK.getCode()) {
				JSONObject jContent = jMsg.getContentObject();
				mDmItem = new DmVO();
				
				// SG100, SG101 DM 정보
				if (jContent.has(JSONConst.CONTENT_NAME_GATEWAY)) {
					if (!jContent.isNull(JSONConst.CONTENT_NAME_GATEWAY)) {
						JSONObject jGateway = jContent.getJSONObject(JSONConst.CONTENT_NAME_GATEWAY);
						GatewayVO gateway = new GatewayVO();

						if (jGateway.has(JSONConst.GATEWAY_NAME_WAN))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_WAN))
								gateway.setWan(Integer.toString(jGateway.getInt(JSONConst.GATEWAY_NAME_WAN)));

						if (jGateway.has(JSONConst.GATEWAY_NAME_MAC))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_MAC))
								gateway.setMac(jGateway.getString(JSONConst.GATEWAY_NAME_MAC));

						if (jGateway.has(JSONConst.GATEWAY_NAME_DIP))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_DIP))
								gateway.setDip(jGateway.getString(JSONConst.GATEWAY_NAME_DIP));

						if (jGateway.has(JSONConst.GATEWAY_NAME_PIP))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_PIP))
								gateway.setPip(jGateway.getString(JSONConst.GATEWAY_NAME_PIP));

						if (jGateway.has(JSONConst.GATEWAY_NAME_FW_VER))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_FW_VER))
								gateway.setFwVer(jGateway.getString(JSONConst.GATEWAY_NAME_FW_VER));

						if (jGateway.has(JSONConst.GATEWAY_NAME_PAIRING_N))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_PAIRING_N))
								gateway.setPairingN(
										Integer.toString(jGateway.getInt(JSONConst.GATEWAY_NAME_PAIRING_N)));

						if (jGateway.has(JSONConst.GATEWAY_NAME_ZWAVE_STATUS))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_ZWAVE_STATUS))
								gateway.setZwaveStatus(
										Integer.toString(jGateway.getInt(JSONConst.GATEWAY_NAME_ZWAVE_STATUS)));

						if (jGateway.has(JSONConst.GATEWAY_NAME_SSID))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_SSID))
								gateway.setSsid(jGateway.getString(JSONConst.GATEWAY_NAME_SSID));
						
						if (jGateway.has(JSONConst.GATEWAY_NAME_RSSI))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_RSSI))
								gateway.setRssi(jGateway.getString(JSONConst.GATEWAY_NAME_RSSI));

						if (jGateway.has(JSONConst.GATEWAY_NAME_RUNNING_TIME))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_RUNNING_TIME))
								gateway.setRunningTime(jGateway.getString(JSONConst.GATEWAY_NAME_RUNNING_TIME));

						if (jGateway.has(JSONConst.GATEWAY_NAME_SESSION_STATUS))
							if (!jGateway.isNull(JSONConst.GATEWAY_NAME_SESSION_STATUS))
								gateway.setSessionStatus(Integer.toString(jGateway.getInt(JSONConst.GATEWAY_NAME_SESSION_STATUS)));

						if(jGateway.has(JSONConst.GATEWAY_NAME_LATITUDE))
							if(!jGateway.isNull(JSONConst.GATEWAY_NAME_LATITUDE))
								gateway.setLatitude(jGateway.getString(JSONConst.GATEWAY_NAME_LATITUDE));
						
						if(jGateway.has(JSONConst.GATEWAY_NAME_LONGITUDE))
							if(!jGateway.isNull(JSONConst.GATEWAY_NAME_LONGITUDE))
								gateway.setLongitude(jGateway.getString(JSONConst.GATEWAY_NAME_LONGITUDE));
						
						mDmItem.setGateway(gateway);
					}
				}
				
				// Z-Wave Device DM 정보
				if (jContent.has(JSONConst.CONTENT_NAME_THINGS)) {
					if (!jContent.isNull(JSONConst.CONTENT_NAME_THINGS)) {
						JSONArray jThingsList = jContent.getJSONArray(JSONConst.CONTENT_NAME_THINGS);
						ArrayList<ThingsVO> thingsList = new ArrayList<ThingsVO>(); 

						for (int i = 0; i < jThingsList.length(); i++) {
							JSONObject jThings = jThingsList.getJSONObject(i);
							ThingsVO things = new ThingsVO();

							if (jThings.has(JSONConst.THINGS_NAME_MODEL_INFO)){
								if (!jThings.isNull(JSONConst.THINGS_NAME_MODEL_INFO))
									things.setModelInfo(jThings.getString(JSONConst.THINGS_NAME_MODEL_INFO));
							} else {
								things.setModelInfo(ModelInfo.LG_UPLUS_IOT_ACCESS_CONTROL.getCode());
							}

							if (jThings.has(JSONConst.GATEWAY_NAME_FW_VER))
								if (!jThings.isNull(JSONConst.GATEWAY_NAME_FW_VER))
									things.setFwVer(jThings.getString(JSONConst.GATEWAY_NAME_FW_VER));

							if (jThings.has(JSONConst.THINGS_NAME_STATUS))
								if (!jThings.isNull(JSONConst.THINGS_NAME_STATUS))
									things.setStatus(jThings.getString(JSONConst.THINGS_NAME_STATUS));

							if (jThings.has(JSONConst.THINGS_NAME_BATTERY))
								if (!jThings.isNull(JSONConst.THINGS_NAME_BATTERY))
									things.setBattery(jThings.getString(JSONConst.THINGS_NAME_BATTERY));

							thingsList.add(things);
						}
						mDmItem.setThings(thingsList);
					}
				}

				sendMessage(jMsg.getFunctionId(), null, jMsg);
			}
		} catch (JSONException e) { 
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(int type, DevCat devCat, JSONMessage jMsg) {
		Intent intent;

		if (dmFlag == true) {
			intent = new Intent(Const.ACTION_DM_101);
			intent.putExtra("jMsg", jMsg.toString());
		} else
			intent = new Intent(Const.ACTION_DM);

		intent.putExtra(Const.UPDATE_TYPE, type);
		ResourceUtil.sendBroadcast(intent);
	}

	private OnM2MRecvListener mOnM2MRecvListener = new OnM2MRecvListener() {

		@Override
		public void onRecv(String msg) {
			JSONMessage jMsg = new JSONMessage(msg);

			switch (MsgType.valueOf(jMsg.getMsgType())) {
			case RESPONSE:
				switch (FunctionId.valueOf(jMsg.getFunctionId())) {
				case DIAGNOSIS_DM_INFO:
					getDmInfo(jMsg);
					break;
				default:
					break;
				}

				break;
			default:
				break;
			}
		}
	};
}
