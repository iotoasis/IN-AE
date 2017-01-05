package kr.co.modacom.iot.ltegwdev.utils;

public class TimeUtil {

	public static void sleep(long milliSec){
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
