package kr.co.modacom.iot.ltegwdev.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.PropertyVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.DevModel;
import kr.co.modacom.iot.ltegwdev.model.type.DevUsage;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.ManufacturerId;
import kr.co.modacom.iot.ltegwdev.model.type.MsgType;
import kr.co.modacom.iot.ltegwdev.model.type.ServiceId;
import kr.co.modacom.iot.ltegwdev.model.type.Target;

/**
 * Device 관련 연동 메세지 구성을 위한 Wrapper class 이다.
 * 
 * @author gustmd38
 */
public class JSONDeviceComposer {

	private static final int INDENT_SPACE = 4;

	public static String requestCommand(MsgType msgType, Target target, FunctionId funcId, ServiceId serviceId,
			DevCat devCat, DevUsage devUsage, DevModel devModel, ManufacturerId manufacturerId,
			ArrayList<PropertyVO> properties, String deviceId, int propertiesType, String gateWayID) {
		String requestMsg = null;
		JSONObject msgObj = null;
		JSONRequest request = new JSONRequest();

		try {
			if (msgType != null || target != null || funcId != null)
				request.setHeader(msgType.getCode(), target.getCode(), funcId.getCode());

			// "ct" object 구성
			JSONObject ctObj = new JSONObject();
			request.setContentObject(ctObj);
			
			if (gateWayID != null)
				ctObj.put(JSONConst.CONTENT_NAME_GATEWAY_ID, gateWayID);
			else			
				ctObj.put(JSONConst.CONTENT_NAME_GATEWAY_ID, Const.GW_ID);
			
			if (serviceId != null)
				ctObj.put(JSONConst.CONTENT_NAME_SERVICE_ID, serviceId.getCode());
			if (devCat != null)
				ctObj.put(JSONConst.CONTENT_NAME_DEVICE_CATEGORY, devCat.getCode());
			if (devUsage != null)
				ctObj.put(JSONConst.CONTENT_NAME_DEVICE_USAGE, devUsage.getCode());
			if (manufacturerId != null)
				ctObj.put(JSONConst.CONTENT_NAME_MANUFACTURER_ID, manufacturerId.getCode());
			if (devModel != null)
				ctObj.put(JSONConst.CONTENT_NAME_MODEL_NAME, devModel.getName());
			if (deviceId != null)
				ctObj.put(JSONConst.CONTENT_NAME_DEVICE_ID, deviceId);
			if (funcId == FunctionId.DEVICE_CONTROL || funcId == FunctionId.DEVICE_UNPAIRING)
				ctObj.put(JSONConst.CONTENT_NAME_USER_ID, request.getFrom());

			if (funcId == FunctionId.DEVICE_CONTROL)
				ctObj.put(JSONConst.CONTENT_NAME_PROPERTIES_TYPE, propertiesType);

			if (properties != null) {
				JSONArray jProperties = new JSONArray();
				for (int i = 0; i < properties.size(); i++) {
					JSONObject jProperty = new JSONObject();
					PropertyVO property = properties.get(i);

					if ((Integer) (property.getUiComponentType()) != null)
						jProperty.put(JSONConst.PROPERTIES_NAME_UI_COMPONENT_TYPE, property.getUiComponentType());
					if ((Integer) (property.getInstanceId()) != null)
						jProperty.put(JSONConst.PROPERTIES_NAME_INSTANCE_ID, property.getInstanceId());
					if (property.getUnit() != null)
						jProperty.put(JSONConst.PROPERTIES_NAME_UNIT, property.getUnit());
					if (property.getLabel() != null)
						jProperty.put(JSONConst.PROPERTIES_NAME_LABEL, property.getLabel());
					if (property.getValue() != null) {
						if (property.getUiComponentType() == 1)
							jProperty.put(JSONConst.PROPERTIES_NAME_VALUE, (Integer) property.getValue());
						else if (property.getUiComponentType() == 2)
							jProperty.put(JSONConst.PROPERTIES_NAME_VALUE, (Integer) property.getValue());
						else if (property.getUiComponentType() == 3)
							jProperty.put(JSONConst.PROPERTIES_NAME_VALUE, (Double) property.getValue());
						else if (property.getUiComponentType() == 4)
							jProperty.put(JSONConst.PROPERTIES_NAME_VALUE, (Double) property.getValue());
						else if (property.getUiComponentType() == 5)
							jProperty.put(JSONConst.PROPERTIES_NAME_VALUE, (String) property.getValue());
					}

					jProperties.put(jProperty);
				}

				ctObj.put(JSONConst.CONTENT_NAME_PROPERTIES, jProperties);
			}

			// Attach content to message
			msgObj = request.getMessageObject();
			requestMsg = msgObj.toString(INDENT_SPACE);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return requestMsg;
	}
}
