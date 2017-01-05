package kr.co.modacom.iot.ltegwdev.model;

public class PropertyVO {
	Object value;
	String label;
	String unit;
	int uiComponentType;
	int instanceId;
	
	public PropertyVO() {
		// TODO Auto-generated constructor stub
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getUiComponentType() {
		return uiComponentType;
	}

	public void setUiComponentType(int uiComponentType) {
		this.uiComponentType = uiComponentType;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
}