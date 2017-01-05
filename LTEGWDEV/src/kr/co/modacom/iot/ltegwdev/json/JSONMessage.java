package kr.co.modacom.iot.ltegwdev.json;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.co.modacom.iot.ltegwdev.model.Const;

public class JSONMessage {
	@SuppressWarnings("unused")
	private static final String TAG = JSONMessage.class.getSimpleName();

	protected JSONObject mMsgObject;
	protected JSONObject mContentObject;

	protected Header mHeader;
	protected Content mContent;

	protected String mMessage = null;

	protected int mType = -1;

	// 메시지 타입 정의
	public static int UNDEFINED = 0;
	public static int REQUEST = 1;
	public static int RESPONSE = 2;

	protected class Header {
		protected int rspCode;
		protected int op;
		protected int msgType;
		protected String to;
		protected String from;
		protected String sessionId;
		protected String msgId;
		protected int funcId;

		public Header() {
		}

	}

	protected class Content {
		String gwId;
		int serviceId;
		int devCat;
		int devUsage;
		int manufacturerId;
		String modelName;
		String modelInfo;
		String deviceId;
		String deviceName;
		String lastUpdateTime;
		String userId;
		String regiDate;
		int propertiesType;
		JSONArray properties;
		JSONObject gateway;
		JSONArray things;
		
		@Override
		public String toString() {
			String str = null;
			try {
				if (properties != null)
					str = properties.toString(4);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	private String mIndent;

	public JSONMessage() {
		mHeader = new Header();
		mContent = new Content();

		mIndent = null;
	}

	public JSONMessage(String message) {
		mHeader = new Header();
		mContent = new Content();

		mMessage = message;

		decompose(mMessage);

		mIndent = null;
	}

	public void setResponseCode(int rspCode) {
		mHeader.rspCode = rspCode;
	}

	public void setOperation(int operation) {
		mHeader.op = operation;
	}

	public void setMsgType(int msgType) {
		mHeader.msgType = msgType;
	}

	public void setTo(String to) {
		mHeader.to = to;
	}

	public void setFrom(String from) {
		mHeader.from = from;
	}

	public void setMessageId(String msgId) {
		mHeader.msgId = msgId;
	}

	public void setSessionId(String sessionId) {
		mHeader.sessionId = sessionId;
	}

	public void setFunctionId(int funcId) {
		mHeader.funcId = funcId;
	}

	public void setGatewayId(String gwId) {
		mContent.gwId = gwId;
	}

	public void setServiceId(int serviceId) {
		mContent.serviceId = serviceId;
	}

	public void setDevCategory(int devCat) {
		mContent.devCat = devCat;
	}

	public void setDeviceUsage(int devUsage) {
		mContent.devUsage = devUsage;
	}

	public void setManufacturerId(int manufacturerId) {
		mContent.manufacturerId = manufacturerId;
	}

	public void setModelName(String modelName) {
		mContent.modelName = modelName;
	}
	
	public void setModelInfo(String modelInfo) {
		mContent.modelInfo = modelInfo;
	}

	public void setDeviceId(String deviceId) {
		mContent.deviceId = deviceId;
	}

	public void setDeviceName(String deviceName) {
		mContent.deviceName = deviceName;
	}

	public void setUserId(String userId) {
		mContent.userId = userId;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		mContent.lastUpdateTime = lastUpdateTime;
	}

	public void setRegiDate(String regiDate) {
		mContent.regiDate = regiDate;
	}

	public void setContentObject(JSONObject ctObj) {
		mContentObject = ctObj;
	}

	public void setProperties(JSONArray properties) {
		mContent.properties = properties;

		if (mContentObject == null)
			mContentObject = new JSONObject();

		try {
			mContentObject.put(JSONConst.CONTENT_NAME_PROPERTIES, properties);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void setGateway(JSONObject gateway) {
		mContent.gateway = gateway;

		if (mContentObject == null)
			mContentObject = new JSONObject();

		try {
			mContentObject.put(JSONConst.CONTENT_NAME_GATEWAY, gateway);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void setThings(JSONArray things) {
		mContent.properties = things;

		if (mContentObject == null)
			mContentObject = new JSONObject();

		try {
			mContentObject.put(JSONConst.CONTENT_NAME_THINGS, things);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public int getResponseCode() {
		return mHeader.rspCode;
	}

	public int getOperation() {
		return mHeader.op;
	}

	public int getMsgType() {
		return mHeader.msgType;
	}

	public String getTo() {
		return mHeader.to;
	}

	public String getFrom() {
		return mHeader.from;
	}

	public String getMessageId() {
		return mHeader.msgId;
	}

	public String getSessionId() {
		return mHeader.sessionId;
	}

	public int getFunctionId() {
		return mHeader.funcId;
	}

	public String getUserId() {
		return mContent.userId;
	}

	public String getLastUpdateTime() {
		return mContent.lastUpdateTime;
	}

	public String getRegiDate() {
		return mContent.regiDate;
	}

	public int getPropertiesType() {
		return mContent.propertiesType;
	}

	public JSONObject getContentObject() {

		try {
			return mMsgObject.getJSONObject(JSONConst.HEADER_NAME_CONTENT);

		} catch (JSONException e) {
			e.printStackTrace();
			return mContentObject;
		}
	}

	public JSONArray getProperties() {
		if (mMsgObject != null) {
			try {
				mContentObject = mMsgObject.getJSONObject(JSONConst.HEADER_NAME_CONTENT);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_PROPERTIES)) {
					return mContentObject.getJSONArray(JSONConst.CONTENT_NAME_PROPERTIES);
				} else {
					return null;
				}
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}
		return null;
	}	

	public JSONObject getGateway() {
		if (mMsgObject != null) {
			try {
				mContentObject = mMsgObject.getJSONObject(JSONConst.HEADER_NAME_CONTENT);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_GATEWAY)) {
					return mContentObject.getJSONObject(JSONConst.CONTENT_NAME_GATEWAY);
				} else {
					return null;
				}
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}
		return null;
	}	

	public JSONArray getThings() {
		if (mMsgObject != null) {
			try {
				mContentObject = mMsgObject.getJSONObject(JSONConst.HEADER_NAME_CONTENT);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_THINGS)) {
					return mContentObject.getJSONArray(JSONConst.CONTENT_NAME_THINGS);
				} else {
					return null;
				}
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}
		return null;
	}	
	
	public int getMessageType() {
		return mType;
	}

	public void setToDefault() {
		/* SBC에서 to에 OMNIPUB로 지정하면 지원하지 않음. 디폴트는 SBC로 지정 */
		mHeader.to = Const.ZWAVE + Const.DELIMITER_SEMICOLON + Const.GW_ID;
	}

	public void setFromDefault() {
		mHeader.from = Const.USER_ID + Const.DELIMITER_SEMICOLON + Const.GW_ID;
	}

	/*
	 * 현재 시간으로 messageId를 설정함
	 */
	public void setMessageId() {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(JSONConst.MESSAGE_ID_PATTERN, Locale.US);
		String msgId = sdf.format(currentDate);

		mHeader.msgId = msgId;
	}

	public int decompose(String jsonString) {

		try {
			mMessage = jsonString;

			mMsgObject = new JSONObject(jsonString);

			if (mMsgObject.has(JSONConst.HEADER_NAME_RESPONSE_CODE)) {
				mType = RESPONSE;
				mHeader.rspCode = mMsgObject.getInt(JSONConst.HEADER_NAME_RESPONSE_CODE);
			} else {
				mType = REQUEST;
			}

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_MSG_TYPE))
				mHeader.msgType = mMsgObject.getInt(JSONConst.HEADER_NAME_MSG_TYPE);

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_TO))
				mHeader.to = mMsgObject.getString(JSONConst.HEADER_NAME_TO);

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_FROM))
				mHeader.from = mMsgObject.getString(JSONConst.HEADER_NAME_FROM);

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_SESSION_ID))
				mHeader.sessionId = mMsgObject.getString(JSONConst.HEADER_NAME_SESSION_ID);

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_MESSAGE_ID))
				mHeader.msgId = mMsgObject.getString(JSONConst.HEADER_NAME_MESSAGE_ID);

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_FUNCTION_ID))
				mHeader.funcId = mMsgObject.getInt(JSONConst.HEADER_NAME_FUNCTION_ID);

			if (!mMsgObject.isNull(JSONConst.HEADER_NAME_CONTENT)) {
				// mContentObject = new JSONObject();
				mContentObject = mMsgObject.getJSONObject(JSONConst.HEADER_NAME_CONTENT);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_GATEWAY_ID))
					mContent.gwId = mContentObject.getString(JSONConst.CONTENT_NAME_GATEWAY_ID);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_SERVICE_ID))
					mContent.serviceId = mContentObject.getInt(JSONConst.CONTENT_NAME_SERVICE_ID);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_DEVICE_CATEGORY))
					mContent.devCat = mContentObject.getInt(JSONConst.CONTENT_NAME_DEVICE_CATEGORY);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_DEVICE_USAGE))
					mContent.devUsage = mContentObject.getInt(JSONConst.CONTENT_NAME_DEVICE_USAGE);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_MANUFACTURER_ID))
					mContent.manufacturerId = mContentObject.getInt(JSONConst.CONTENT_NAME_MANUFACTURER_ID);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_DEVICE_ID))
					mContent.deviceId = mContentObject.getString(JSONConst.CONTENT_NAME_DEVICE_ID);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_MODEL_NAME))
					mContent.modelName = mContentObject.getString(JSONConst.CONTENT_NAME_MODEL_NAME);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_MODEL_INFO))
					mContent.modelInfo = mContentObject.getString(JSONConst.CONTENT_NAME_MODEL_INFO);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_LAST_UPDATE_TIME))
					mContent.lastUpdateTime = mContentObject.getString(JSONConst.CONTENT_NAME_LAST_UPDATE_TIME);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_PROPERTIES_TYPE))
					mContent.propertiesType = mContentObject.getInt(JSONConst.CONTENT_NAME_PROPERTIES_TYPE);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_PROPERTIES))
					mContent.properties = mContentObject.getJSONArray(JSONConst.CONTENT_NAME_PROPERTIES);

				if (!mContentObject.isNull(JSONConst.CONTENT_NAME_REGI_DATE))
					mContent.regiDate = mContentObject.getString(JSONConst.CONTENT_NAME_REGI_DATE);
				
				if(!mContentObject.isNull(JSONConst.CONTENT_NAME_GATEWAY))
					mContent.gateway = mContentObject.getJSONObject(JSONConst.CONTENT_NAME_GATEWAY);
				
				if(!mContentObject.isNull(JSONConst.CONTENT_NAME_THINGS))
					mContent.things = mContentObject.getJSONArray(JSONConst.CONTENT_NAME_THINGS);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return JSONConst.RESULT_FAIL;
		}
		return JSONConst.RESULT_OK;
	}

	@Override
	public String toString() {
		String result = null;

		int indentSpaces = 4;

		try {
			if (mMsgObject != null)
				result = mMsgObject.toString(indentSpaces);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	private void setIntent(int indentSpaces) {
		char[] indentChars = new char[indentSpaces];
		Arrays.fill(indentChars, ' ');
		mIndent = new String(indentChars);
	}
}
