package kr.co.modacom.iot.ltegwdev.model;

import java.util.ArrayList;

public class DmVO {
	private GatewayVO gateway;
	private ArrayList<ThingsVO> things;
	
	public DmVO() {
		// TODO Auto-generated constructor stub
	}

	public GatewayVO getGateway() {
		return gateway;
	}

	public void setGateway(GatewayVO gateway) {
		this.gateway = gateway;
	}

	public ArrayList<ThingsVO> getThings() {
		return things;
	}

	public void setThings(ArrayList<ThingsVO> things) {
		this.things = things;
	}
	
}
