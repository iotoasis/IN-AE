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
import kr.co.modacom.iot.ltegwdev.model.PropertyVO;
import kr.co.modacom.iot.ltegwdev.model.ZwaveDeviceVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.DevModel;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.ManufacturerId;
import kr.co.modacom.iot.ltegwdev.model.type.ModelInfo;
import kr.co.modacom.iot.ltegwdev.model.type.MsgType;
import kr.co.modacom.iot.ltegwdev.model.type.RspCode;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MRecvListener;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.ResourceUtil;

public class ZWaveController {
	private static final String TAG = ZWaveController.class.getSimpleName();

	private static ZWaveController mZWaveController = null;

	private M2MManager mM2MManager = null;
	private ArrayList<ZwaveDeviceVO> mItems;

	private static Context mCtx;

	private ZWaveController() {
		mItems = new ArrayList<ZwaveDeviceVO>();
	}

	public static ZWaveController getInstance(Context ctx) {
		if (mZWaveController == null)
			mZWaveController = new ZWaveController();
		setContext(ctx);
		return mZWaveController;
	}

	public static void setContext(Context ctx) {
		mCtx = ctx;
	}

	public OnM2MRecvListener getM2MRecvListener() {
		return mOnM2MRecvListener;
	}

	public ArrayList<ZwaveDeviceVO> getItems() {
		return mItems;
	}

	public void setItems(ArrayList<ZwaveDeviceVO> mItems) {
		this.mItems = mItems;
	}

	public void setM2MManager(M2MManager m2mManager) {
		this.mM2MManager = m2mManager;
	}

	public void setM2MRecvListener() {
		mM2MManager.setOnM2MRecvListener(mOnM2MRecvListener);
	}

	public void clearZWaveDevice() {
		mItems.clear();
	}

	// Z-Wave Device 목록 조회 요청
	public void requestDeviceList(OnM2MSendListener listener, Target target, DevCat devCat) {
		String jString = JSONDeviceComposer.requestCommand(MsgType.REQUEST, target, FunctionId.DEVICE_LIST, null,
				devCat, null, null, null, null, null, -1, null);
		sendMessage(jString, listener, Const.SG100_AE_ID);
	}

	// Z-Wave Device 상세 정보 조회 요청
	public void requestDeviceInfo(OnM2MSendListener listener, String deviceId, Target target) {
		String jString = JSONDeviceComposer.requestCommand(MsgType.REQUEST, target, FunctionId.DEVICE_INFO, null, null,
				null, null, null, null, deviceId, -1, null);
		sendMessage(jString, listener, Const.SG100_AE_ID);
	}

	// Z-Wave Device Pairing 요청
	public void requestPairing(OnM2MSendListener listener, Target target, DevCat devCat, DevModel modelName,
			ManufacturerId manufacturerId) {
		String jString = JSONDeviceComposer.requestCommand(MsgType.REQUEST, target, FunctionId.DEVICE_PAIRING, null,
				devCat, null, modelName, manufacturerId, null, null, -1, null);
		sendMessage(jString, listener, Const.SG100_AE_ID);
	}

	// Z-Wave Device Unpairing 요청
	public void requestUnpairing(OnM2MSendListener listener, Target target, String deviceId) {
		String jString = JSONDeviceComposer.requestCommand(MsgType.REQUEST, target, FunctionId.DELETE_DEVICE, null,
				null, null, null, null, null, deviceId, -1, null);
		sendMessage(jString, listener, Const.SG100_AE_ID);
	}

	// Z-Wave Device 상태 제어 요청
	public void requestStateUpdate(OnM2MSendListener listener, Target target, ZwaveDeviceVO item) {
		String jString = JSONDeviceComposer.requestCommand(MsgType.CONTROL_REQUEST, target, FunctionId.DEVICE_CONTROL,
				null, DevCat.valueOf(item.getDevCat()), null, null, null, item.getProperties(), item.getDeviceId(),
				item.getPropertiesType(), null);
		sendMessage(jString, listener, Const.SG100_AE_ID);
	}

	public void sendMessage(String message, OnM2MSendListener listener, String mnId) {
		if (mM2MManager != null && mM2MManager.isIsM2MLibInit())
			mM2MManager.sendMessage(mCtx, message, listener, mOnM2MRecvListener, mnId);
	}

	// 응답 Z-Wave Device 목록 Message Parsing
	public void getDeviceList(JSONMessage jMsg) {
		try {
			if (jMsg.getResponseCode() == RspCode.OK.getCode()) {
				JSONObject jContent = jMsg.getContentObject();

				if (jContent.has(JSONConst.CONTENT_NAME_DEVICE_LIST)) {
					if (!jContent.isNull(JSONConst.CONTENT_NAME_DEVICE_LIST)) {
						JSONArray jDeviceList = jContent.getJSONArray(JSONConst.CONTENT_NAME_DEVICE_LIST);

						for (int i = 0; i < jDeviceList.length(); i++) {
							JSONObject jDevice = jDeviceList.getJSONObject(i);
							ZwaveDeviceVO item = new ZwaveDeviceVO();

							if (jDevice.has(JSONConst.CONTENT_NAME_DEVICE_ID))
								if (jDevice.getString(JSONConst.CONTENT_NAME_DEVICE_ID) != null)
									item.setDeviceId(jDevice.getString(JSONConst.CONTENT_NAME_DEVICE_ID));
							if (jDevice.has(JSONConst.CONTENT_NAME_SERVICE_ID))
								if ((Integer) (jDevice.getInt(JSONConst.CONTENT_NAME_SERVICE_ID)) != null)
									item.setServiceId(jDevice.getInt(JSONConst.CONTENT_NAME_SERVICE_ID));
							if (jDevice.has(JSONConst.CONTENT_NAME_DEVICE_CATEGORY))
								if ((Integer) (jDevice.getInt(JSONConst.CONTENT_NAME_DEVICE_CATEGORY)) != null)
									item.setDevCat(jDevice.getInt(JSONConst.CONTENT_NAME_DEVICE_CATEGORY));
							if (jDevice.has(JSONConst.CONTENT_NAME_DEVICE_USAGE))
								if ((Integer) (jDevice.getInt(JSONConst.CONTENT_NAME_DEVICE_USAGE)) != null)
									item.setDevUsage(jDevice.getInt(JSONConst.CONTENT_NAME_DEVICE_USAGE));
							if (jDevice.has(JSONConst.CONTENT_NAME_MANUFACTURER_ID))
								if ((Integer) (jDevice.getInt(JSONConst.CONTENT_NAME_MANUFACTURER_ID)) != null)
									item.setManufacturerId(jDevice.getInt(JSONConst.CONTENT_NAME_MANUFACTURER_ID));
							if (jDevice.has(JSONConst.CONTENT_NAME_MODEL_NAME)) {
								if (jDevice.getString(JSONConst.CONTENT_NAME_MODEL_NAME) != null)
									item.setModelName(jDevice.getString(JSONConst.CONTENT_NAME_MODEL_NAME));
							} else {
								item.setModelName(ModelInfo.LG_UPLUS_IOT_ACCESS_CONTROL.getName());
							}
							if (jDevice.has(JSONConst.CONTENT_NAME_MODEL_INFO)) {
								if (jDevice.getString(JSONConst.CONTENT_NAME_MODEL_INFO) != null)
									item.setModelInfo(jDevice.getString(JSONConst.CONTENT_NAME_MODEL_INFO));
							}
							if (jDevice.has(JSONConst.CONTENT_NAME_DEVICE_NAME))
								if (jDevice.getString(JSONConst.CONTENT_NAME_DEVICE_NAME) != null)
									item.setDeviceName(jDevice.getString(JSONConst.CONTENT_NAME_DEVICE_NAME));
							if (jDevice.has(JSONConst.CONTENT_NAME_LAST_UPDATE_TIME))
								if (jDevice.getString(JSONConst.CONTENT_NAME_LAST_UPDATE_TIME) != null)
									item.setLastUpdateTime(jDevice.getString(JSONConst.CONTENT_NAME_LAST_UPDATE_TIME));

							mItems.add(item);
						}
					}
					sendMessage(FunctionId.DEVICE_LIST.getCode(), DevCat.ZWAVE);
				}
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 응답 Z-Wave Device 상세 정보 Message Parsing
	public void getDeviceInfo(JSONMessage jMsg) {
		if (jMsg.getResponseCode() == RspCode.OK.getCode()) {
			JSONObject jContent = jMsg.getContentObject();

			for (int i = 0; i < mItems.size(); i++) {
				ZwaveDeviceVO item = mItems.get(i);
				try {
					if (item.getDeviceId().equals(jContent.getString(JSONConst.CONTENT_NAME_DEVICE_ID))) {
						item.setPairingFlag(true);
						item.setPropertiesType(jContent.getInt(JSONConst.CONTENT_NAME_PROPERTIES_TYPE));
						if (jContent.has(JSONConst.CONTENT_NAME_MODEL_INFO)) {
							if (jContent.getString(JSONConst.CONTENT_NAME_MODEL_INFO) != null)
								item.setModelInfo(jContent.getString(JSONConst.CONTENT_NAME_MODEL_INFO));
						} else {
							item.setModelInfo(ModelInfo.LG_UPLUS_IOT_ACCESS_CONTROL.getCode());
						}

						item.setProperties(new ArrayList<PropertyVO>());
						JSONArray jProperties = jContent.getJSONArray(JSONConst.CONTENT_NAME_PROPERTIES);

						for (int j = 0; j < jProperties.length(); j++) {
							JSONObject jProperty = jProperties.getJSONObject(j);
							PropertyVO property = new PropertyVO();

							if (jProperty.has(JSONConst.PROPERTIES_NAME_UNIT))
								if (jProperty.getString(JSONConst.PROPERTIES_NAME_UNIT) != null)
									property.setUnit(jProperty.getString(JSONConst.PROPERTIES_NAME_UNIT));
							if (jProperty.has(JSONConst.PROPERTIES_NAME_LABEL))
								if (jProperty.getString(JSONConst.PROPERTIES_NAME_LABEL) != null)
									property.setLabel(jProperty.getString(JSONConst.PROPERTIES_NAME_LABEL));
							if (jProperty.has(JSONConst.PROPERTIES_NAME_INSTANCE_ID))
								if ((Integer) (jProperty.getInt(JSONConst.PROPERTIES_NAME_INSTANCE_ID)) != null)
									property.setInstanceId(jProperty.getInt(JSONConst.PROPERTIES_NAME_INSTANCE_ID));
							if (jProperty.has(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE))
								if ((Integer) (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE)) != null)
									property.setUiComponentType(
											jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE));

							if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 1) {
								property.setValue(jProperty.getInt(JSONConst.PROPERTIES_NAME_VALUE));
							} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 2) {
								property.setValue(jProperty.getInt(JSONConst.PROPERTIES_NAME_VALUE));
							} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 3) {
								property.setValue(jProperty.getDouble(JSONConst.PROPERTIES_NAME_VALUE));
							} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 4) {
								property.setValue(jProperty.getDouble(JSONConst.PROPERTIES_NAME_VALUE));
							} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 5) {
								property.setValue(jProperty.getString(JSONConst.PROPERTIES_NAME_VALUE));
							}

							item.getProperties().add(property);
						}
					}
					sendMessage(FunctionId.DEVICE_INFO.getCode(), DevCat.ZWAVE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// 응답 Z-Wave Device Pairing Message Parsing
	public void getPairing(JSONMessage jMsg) {
		if (jMsg.getResponseCode() == RspCode.OK.getCode()) {
			try {
				JSONObject jContent = jMsg.getContentObject();
				ZwaveDeviceVO item = new ZwaveDeviceVO();
				if (jContent.has(JSONConst.CONTENT_NAME_DEVICE_CATEGORY))
					if ((Integer) (jContent.getInt(JSONConst.CONTENT_NAME_DEVICE_CATEGORY)) != null)
						item.setDevCat(jContent.getInt(JSONConst.CONTENT_NAME_DEVICE_CATEGORY));
				if (jContent.has(JSONConst.CONTENT_NAME_MODEL_NAME))
					if (jContent.getString(JSONConst.CONTENT_NAME_MODEL_NAME) != null)
						item.setModelName(jContent.getString(JSONConst.CONTENT_NAME_MODEL_NAME));
				if (jContent.has(JSONConst.CONTENT_NAME_MODEL_INFO))
					if (jContent.getString(JSONConst.CONTENT_NAME_MODEL_INFO) != null)
						item.setModelInfo(jContent.getString(JSONConst.CONTENT_NAME_MODEL_INFO));
				if (jContent.has(JSONConst.CONTENT_NAME_DEVICE_ID))
					if (jContent.getString(JSONConst.CONTENT_NAME_DEVICE_ID) != null)
						item.setDeviceId(jContent.getString(JSONConst.CONTENT_NAME_DEVICE_ID));
				if (jContent.has(JSONConst.CONTENT_NAME_PROPERTIES_TYPE))
					if ((Integer) (jContent.getInt(JSONConst.CONTENT_NAME_PROPERTIES_TYPE)) != null)
						item.setPropertiesType(jContent.getInt(JSONConst.CONTENT_NAME_PROPERTIES_TYPE));

				if (jContent.has(JSONConst.CONTENT_NAME_PROPERTIES)) {
					item.setProperties(new ArrayList<PropertyVO>());
					JSONArray jProperties = jContent.getJSONArray(JSONConst.CONTENT_NAME_PROPERTIES);

					for (int i = 0; i < jProperties.length(); i++) {
						JSONObject jProperty = jProperties.getJSONObject(i);
						PropertyVO property = new PropertyVO();

						if (jProperty.has(JSONConst.PROPERTIES_NAME_LABEL))
							if (jProperty.getString(JSONConst.PROPERTIES_NAME_LABEL) != null)
								property.setLabel(jProperty.getString(JSONConst.PROPERTIES_NAME_LABEL));
						if (jProperty.has(JSONConst.PROPERTIES_NAME_UNIT))
							if (jProperty.getString(JSONConst.PROPERTIES_NAME_UNIT) != null)
								property.setUnit(jProperty.getString(JSONConst.PROPERTIES_NAME_UNIT));
						if (jProperty.has(JSONConst.PROPERTIES_NAME_INSTANCE_ID))
							if ((Integer) (jProperty.getInt(JSONConst.PROPERTIES_NAME_INSTANCE_ID)) != null)
								property.setInstanceId(jProperty.getInt(JSONConst.PROPERTIES_NAME_INSTANCE_ID));
						if (jProperty.has(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE))
							if ((Integer) (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE)) != null)
								property.setUiComponentType(
										jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE));

						if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 1) {
							property.setValue(jProperty.getInt(JSONConst.PROPERTIES_NAME_VALUE));
						} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 2) {
							property.setValue(jProperty.getInt(JSONConst.PROPERTIES_NAME_VALUE));
						} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 3) {
							property.setValue(jProperty.getDouble(JSONConst.PROPERTIES_NAME_VALUE));
						} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 4) {
							property.setValue(jProperty.getDouble(JSONConst.PROPERTIES_NAME_VALUE));
						} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 5) {
							property.setValue(jProperty.getString(JSONConst.PROPERTIES_NAME_VALUE));
						}

						item.getProperties().add(property);
					}
				}
				
				item.setPairingFlag(true);
				mItems.add(item);
				sendMessage(FunctionId.DEVICE_PAIRING.getCode(), DevCat.ZWAVE);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// 응답 Z-Wave Device Unpairing Message Parsing
	public void getUnpairing(JSONMessage jMsg) {

		if (jMsg.getResponseCode() == RspCode.OK.getCode()) {
			JSONObject jContent = jMsg.getContentObject();
			try {
				if (jContent.has(JSONConst.CONTENT_NAME_DEVICE_ID)) {
					String deviceId = jContent.getString(JSONConst.CONTENT_NAME_DEVICE_ID);

					for (int i = 0; i < mItems.size(); i++) {
						ZwaveDeviceVO item = mItems.get(i);

						if (item.getDeviceId().equals(deviceId)) {
							mItems.remove(i);
							break;
						}
					}
				}
				sendMessage(FunctionId.DEVICE_UNPAIRING.getCode(), DevCat.ZWAVE);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// 응답 Z-Wave Device 상태 제어 Message Parsing
	public void getDeviceStateUpdate(JSONMessage jMsg) {
		JSONObject jContent = jMsg.getContentObject();
		if (jContent.has(JSONConst.CONTENT_NAME_DEVICE_ID)) {
			try {
				String deviceId = jContent.getString(JSONConst.CONTENT_NAME_DEVICE_ID);
				deviceId = deviceId.substring(0, deviceId.length() - 1);

				for (int i = 0; i < mItems.size(); i++) {
					ZwaveDeviceVO item = mItems.get(i);

					if ((item.getDeviceId().substring(0, item.getDeviceId().length() - 1)).equals(deviceId)) {
						if (jContent.has(JSONConst.CONTENT_NAME_PROPERTIES)) {
							JSONArray jProperties = jContent.getJSONArray(JSONConst.CONTENT_NAME_PROPERTIES);

							for (int j = 0; j < item.getProperties().size(); j++) {
								PropertyVO property = item.getProperties().get(j);

								for (int k = 0; k < jProperties.length(); k++) {
									JSONObject jProperty = jProperties.getJSONObject(k);
									if (property.getInstanceId() == jProperty
											.getInt(JSONConst.PROPERTIES_NAME_INSTANCE_ID)
											&& property.getLabel()
													.equals(jProperty.getString(JSONConst.PROPERTIES_NAME_LABEL))) {
										if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 1) {
											property.setValue(jProperty.getInt(JSONConst.PROPERTIES_NAME_VALUE));
										} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 2) {
											property.setValue(jProperty.getInt(JSONConst.PROPERTIES_NAME_VALUE));
										} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 3) {
											property.setValue(jProperty.getDouble(JSONConst.PROPERTIES_NAME_VALUE));
										} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 4) {
											property.setValue(jProperty.getDouble(JSONConst.PROPERTIES_NAME_VALUE));
										} else if (jProperty.getInt(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE) == 5) {
											property.setValue(jProperty.getString(JSONConst.PROPERTIES_NAME_VALUE));
										}
									}
								}
							}
						}
						break;
					}
				}

				sendMessage(jMsg.getFunctionId(), DevCat.ZWAVE);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(int type, DevCat devCat) {
		Intent intent;
		switch (devCat) {
		case ZWAVE:
			intent = new Intent(Const.ACTION_ZWAVE);
			intent.putExtra(Const.UPDATE_TYPE, type);
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

			JSONMessage jMsg = new JSONMessage(msg);

			switch (MsgType.valueOf(jMsg.getMsgType())) {
			case RESPONSE:
				switch (FunctionId.valueOf(jMsg.getFunctionId())) {
				case DEVICE_LIST:
					getDeviceList(jMsg);
					break;
				case DEVICE_INFO:
					getDeviceInfo(jMsg);
					break;
				case DEVICE_PAIRING:
					getPairing(jMsg);
					break;
				case DELETE_DEVICE:
					getUnpairing(jMsg);
					break;
				default:
					break;
				}

				break;

			case NOTIFY:
				switch (FunctionId.valueOf(jMsg.getFunctionId())) {
				case UPDATED_DEVICE:
					getDeviceStateUpdate(jMsg);
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
