package kr.co.modacom.iot.ltegwdev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

public class ResourceUtil {
	private static Context ctx;

	private static String pkg;

	private static Resources res;

	private static Locale defLocale;

	/**
	 * Application onCreate()
	 * 
	 * @param ctx
	 */
	public static void init(Context ctx) {
		ResourceUtil.ctx = ctx;
		ResourceUtil.res = ctx.getResources();
		ResourceUtil.pkg = ctx.getPackageName();

		if (defLocale != null)
			defLocale = Locale.getDefault();

		// TODO : Cache directory 초기화
	}

	/**
	 * Application Context
	 * 
	 * @return Application Context
	 */
	public static Context getContext() {
		return ctx;
	}
	
	/**
	 * Get Package Name
	 * 
	 * @return package name
	 */
	public static String getPackageName() {
		return pkg;
	}

	/**
	 * Send Broadcast
	 * 
	 * @param intent
	 */
	public static void sendBroadcast(Intent intent) {
		if (intent == null)
			return;

		ctx.sendBroadcast(intent);
	}

	public static void startService(Intent intent) {
		if (intent == null)
			return;

		ctx.startService(intent);
	}

	public static void startActivity(Intent intent) {
		if (intent == null)
			return;

		ctx.startActivity(intent);
	}

	public static void showSoftKeyboard(View view) {
		InputMethodManager mgr = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void hideSoftKeyboard(View view) {
		InputMethodManager mgr = (InputMethodManager) (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * Resource : View Id 를 이용하여 해당 View를 생성하고 생성된 View instance 를 전달한다.
	 * 
	 * @param context
	 *            context
	 * @param viewIdb
	 *            layout id
	 * @return View
	 */
	public static View getView(Context context, int viewId) {
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(viewId, null);
	}

	/**
	 * 특정 Parent View 에 viewId 를 이용하여 생성한 view 를 child 로 등록한다.
	 * 
	 * @param context
	 * @param viewId
	 * @param root
	 * @return
	 */
	public static View addView(Context context, int viewId, ViewGroup root) {
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(viewId, root, true);
	}

	/**
	 * <PRE>
	 * String Resource 에서 StringId 에 해당하는 String 값을 가져와서 전달한다.
	 * </PRE>
	 * 
	 * @author : gustmd38
	 * @date : 2013. 1. 17.
	 * @version :
	 * 
	 * @param strId
	 * @return
	 * @throws NotFoundException
	 */
	public static String getString(int strId) throws NotFoundException {
		return ctx.getString(strId);
	}

	public static String getString(Context context, int strId) throws NotFoundException {
		return context.getResources().getString(strId);
	}

	public static String[] getStringArray(int strId) throws NotFoundException {
		return ctx.getResources().getStringArray(strId);
	}

	public static String[] getStringArray(Context context, int strId) throws NotFoundException {
		return context.getResources().getStringArray(strId);
	}

	public static String getAbsolutePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getLocalPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	public static int getResourceId(String defType, String name){
		return res.getIdentifier(name, defType, getPackageName());
	}
	
	public static int getResourceId(String defType, String name, String defPackage){
		return res.getIdentifier(name, defType, defPackage);
	}
	
	/**
	 * Resource : Drawable Id 를 이용하여 해당 Drawable를 생성하고 생성된 Drawable instance 를
	 * 전달한다.
	 * 
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(int id) {
		return res.getDrawable(id);
	}

	public static Drawable getDrawable(Bitmap b) {
		return new BitmapDrawable(res, b);
	}

	/**
	 * Resource : Bitmap Id 를 이용하여 해당 Bitmap를 생성하고 생성된 Bitmap instance 를 전달한다.
	 * 
	 * @param id
	 * @return
	 */
	public static Bitmap getBitmap(int id) {
		Bitmap bitmap = null;

		try {
			bitmap = BitmapFactory.decodeResource(res, id);
		} catch (Exception e) {
			e.printStackTrace();
			bitmap = null;
		}

		return bitmap;
	}

	/**
	 * Color Id 를 이용하여 Resource Color 정보에서 해당 Color 값을 가져온다.
	 * 
	 * @param id
	 * @return
	 */
	public static int getColor(int id) {
		return res.getColor(id);
	}

	/**
	 * * 픽셀단위를 현재 디스플레이 화면에 비례한 크기로 반환합니다.
	 * 
	 * @param pixel
	 *            픽셀
	 * @return 변환된 값(DP)
	 */
	public static int DPFromPixel(int pixel) {
		float scale = res.getDisplayMetrics().density;
		return (int) (pixel / scale);
	}

	/**
	 * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다.
	 * 
	 * @param DP
	 *            픽셀
	 * @return 변환된 값(pixel)
	 */
	public static int PixelFromDP(int DP) {
		float scale = res.getDisplayMetrics().density;
		return (int) (DP * scale);
	}

	public static int PixelFromDP(float DP) {
		float scale = res.getDisplayMetrics().density;
		return (int) (DP * scale);
	}

	public static DisplayMetrics getDisplayMetrics() {
		DisplayMetrics displayMetrics = new DisplayMetrics();

		Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		display.getMetrics(displayMetrics);

		System.out.println("Device Density : " + displayMetrics.density);
		System.out.println("Device DensityDpi : " + displayMetrics.densityDpi);
		System.out.println("Device heightPixels    : " + displayMetrics.heightPixels);
		System.out.println("Device scaledDensity   : " + displayMetrics.scaledDensity);
		System.out.println("Device widthPixels     : " + displayMetrics.widthPixels);
		System.out.println("Device xdpi            : " + displayMetrics.xdpi);
		System.out.println("Device ydpi            : " + displayMetrics.ydpi);

		return displayMetrics;
	}

	/**
	 * Animation : Animation Id 를 이용하여 해당 Animation를 생성하고 생성된 Animation instance
	 * 를 전달한다.
	 * 
	 * @param resId
	 * @return
	 */
	public static Animation loadAnimation(int resId) {
		return AnimationUtils.loadAnimation(ctx, resId);
	}

	/**
	 * Get current version code.
	 */
	public static int getVersionCode() {
		int version = 0;

		try {
			PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			version = pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			System.out.println("Package name not found");
			e.printStackTrace();
		}

		return version;
	}

	/**
	 * Get current version number.
	 */
	public static String getVersionName() {
		String version = "?";

		try {
			PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			version = pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			System.out.println("Package name not found");
			e.printStackTrace();
		}

		return version;
	}

	/**
	 * Forground Activity
	 * 
	 * @param activityName
	 * @return
	 */
	public static boolean checkActivityForground(String pkgName) {
		if (pkgName == null)
			return false;

		try {
			ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

			List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
			ComponentName topActivity = taskInfo.get(0).topActivity;
			String name = topActivity.getPackageName();

			return name.equalsIgnoreCase(pkgName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Forground Activity
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean checkActivityForground(Activity activity) {
		try {
			ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

			List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
			ComponentName topActivity = taskInfo.get(0).topActivity;
			String name = topActivity.getPackageName();

			return name.equalsIgnoreCase(activity.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int getActivityState(Activity activity) {
		return 0;
	}

	public static InputStream openAssetFile(String filePath) throws IOException {
		return ctx.getResources().getAssets().open(filePath);
	}

	public static ApplicationInfo getApplicationInfo(String pkgName) {
		ApplicationInfo appInfo = null;
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(pkgName.trim(), PackageManager.GET_META_DATA);
			appInfo = pi.applicationInfo;
		} catch (NameNotFoundException e) {
			// 패키지가 없을 경우.
			e.printStackTrace();
		}
		return appInfo;
	}

	public static String getVersion() {
		return getVersion(ctx.getPackageName());
	}
	
	public static String getVersion(String pakName) {
		String version;
		try {
			PackageInfo i = ctx.getPackageManager().getPackageInfo(pakName, 0);
			version = i.versionName;
		} catch (NameNotFoundException e) {
			version = "";
		}
		
		return version;
	}
}
