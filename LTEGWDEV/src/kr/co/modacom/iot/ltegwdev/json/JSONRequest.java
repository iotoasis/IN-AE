package kr.co.modacom.iot.ltegwdev.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONRequest extends JSONMessage {
	@SuppressWarnings("unused")
	private static final String TAG = JSONRequest.class.getSimpleName();

	public JSONRequest() {
		super();
	}

	public JSONRequest(String message) {
		super(message);

	}

	/*
	 * 멤버변수 mHeader 값을 이용하여 header object를 구성
	 */
	public JSONObject getHeaderObject() {
		JSONObject headerObj = new JSONObject();

		try {
			//headerObj.put(JSONConst.HEADER_NAME_OPERATION, mHeader.op);
			headerObj.put(JSONConst.HEADER_NAME_MSG_TYPE, mHeader.msgType);
			headerObj.put(JSONConst.HEADER_NAME_TO, mHeader.to);
			headerObj.put(JSONConst.HEADER_NAME_FROM, mHeader.from);
			if (mHeader.sessionId != null) {
				headerObj.put(JSONConst.HEADER_NAME_SESSION_ID, mHeader.sessionId);
			} else {
				headerObj.put(JSONConst.HEADER_NAME_SESSION_ID, JSONObject.NULL);
			}
			headerObj.put(JSONConst.HEADER_NAME_MESSAGE_ID, mHeader.msgId);
			headerObj.put(JSONConst.HEADER_NAME_FUNCTION_ID, mHeader.funcId);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return headerObj;
	}
	public void setHeader(int msgType, String target, int funcId) {
		setMsgType(msgType);
		setTo(target);
		setFromDefault();
		setMessageId();
		setSessionId("IOT_A_" + getMessageId());
		setFunctionId(funcId);
	}

	public JSONObject getMessageObject() {
		if (mMsgObject == null) {
			try {
				mMsgObject = getHeaderObject();
				mMsgObject.put(JSONConst.HEADER_NAME_CONTENT, mContentObject);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return mMsgObject;
		} else {
			return mMsgObject;
		}
	}
}
