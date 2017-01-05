package kr.co.modacom.iot.ltegwdev;

import android.app.Application;
import kr.co.modacom.iot.ltegwdev.utils.ResourceUtil;

public class MainApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// 전역 Resource initialize
		ResourceUtil.init(this);
	}
}
