package kr.co.modacom.iot.ltegwdev.model;


import java.util.ArrayList;

public class LifxDeviceVO {
	private String deviceId;
	private int serviceId;
	private int devCat;
	private int devUsage;
	private int manufacturerId;
	private String modelName;
	private String deviceName;
	private String lastUpdateTime;
	private int propertiesType;

	private String regiDate;
	private ArrayList<PropertyVO> mProperties;
	
	private boolean pairingFlag = false;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getDevCat() {
		return devCat;
	}

	public void setDevCat(int devCat) {
		this.devCat = devCat;
	}

	public int getDevUsage() {
		return devUsage;
	}

	public void setDevUsage(int devUsage) {
		this.devUsage = devUsage;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public ArrayList<PropertyVO> getProperties() {
		return mProperties;
	}

	public void setProperties(ArrayList<PropertyVO> mProperties) {
		this.mProperties = mProperties;
	}

	public void setValue(int value, int index) {
		this.mProperties.get(index).value = value;
	}

	public Object getValue(int index) {
		return this.mProperties.get(index).value;
	}

	public void setLabel(String label, int index) {
		this.mProperties.get(index).label = label;
	}

	public String getLabel(int index) {
		return this.mProperties.get(index).label;
	}

	public void setUnit(String unit, int index) {
		this.mProperties.get(index).unit = unit;
	}

	public String getUnit(int index) {
		return this.mProperties.get(index).unit;
	}

	public void setUiComponeteType(int uiComponentType, int index) {
		this.mProperties.get(index).uiComponentType = uiComponentType;
	}

	public int getUiComponentType(int index) {
		return this.mProperties.get(index).uiComponentType;
	}

	public void setInstanceId(int instanceId, int index) {
		this.mProperties.get(index).instanceId = instanceId;
	}

	public int getInstanceId(int index) {
		return this.mProperties.get(index).instanceId;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(String regiDate) {
		this.regiDate = regiDate;
	}	
	
	public int getPropertiesType() {
		return propertiesType;
	}

	public void setPropertiesType(int propertiesType) {
		this.propertiesType = propertiesType;
	}

	public boolean getPairingFlag() {
		return pairingFlag;
	}

	public void setPairingFlag(boolean pairingFlag) {
		this.pairingFlag = pairingFlag;
	}

	public boolean isDeviceId(String deviceId) {
		if(this.deviceId.equals(deviceId))
			return true;
		else return false;
	}

}
