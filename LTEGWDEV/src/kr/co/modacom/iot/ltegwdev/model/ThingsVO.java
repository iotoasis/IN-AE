package kr.co.modacom.iot.ltegwdev.model;

public class ThingsVO {
	private String modelInfo;
	private String fwVer;
	private String status;
	private String battery;
	
	public ThingsVO() {
		// TODO Auto-generated constructor stub
	}

	public String getModelInfo() {
		return modelInfo;
	}

	public void setModelInfo(String modelInfo) {
		this.modelInfo = modelInfo;
	}

	public String getFwVer() {
		return fwVer;
	}

	public void setFwVer(String fwVer) {
		this.fwVer = fwVer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}
	
	
}
