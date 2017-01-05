package kr.co.modacom.iot.ltegwdev.utils;

public class StringUtil {
	public static final int INT_NULL = -999999;
	public static final long LONG_NULL = -999999L;
	
	public static int parseInt(String str){
		return parseInt(str, INT_NULL);
	}
	
	public static int parseInt(String str, int def){
		int val = def;
		if( str == null )
			return val;
		
		try{
			val = Integer.valueOf(str);
		}catch (Exception e) {
			val = def;
		}
		return val;
	}
	
	public static long parseLong(String str){
		return parseLong(str, LONG_NULL);
	}
	
	public static long parseLong(String str, long def){
		long val = def;
		if( str == null )
			return val;
		
		try{
			val = Long.valueOf(str);
		}catch (Exception e) {
			val = def;
		}
		return val;
	}
	
	public static int parseIntAllowZero(String str){
		int val = 0;
		if( str == null )
			return val;
		
		try{
			val = Integer.valueOf(str);
		}catch (Exception e) {
			val = 0;
		}
		return val;
	}
	
	public static long parseLongAllowZero(String str){
		long val = 0;
		if( str == null )
			return val;
		
		try{
			val = Long.valueOf(str);
		}catch (Exception e) {
			val = 0;
		}
		return val;
	}
	
	/**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
