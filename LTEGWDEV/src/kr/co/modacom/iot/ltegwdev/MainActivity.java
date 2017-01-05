package kr.co.modacom.iot.ltegwdev;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import kr.co.modacom.iot.ltegwdev.control.DMController;
import kr.co.modacom.iot.ltegwdev.control.LightController;
import kr.co.modacom.iot.ltegwdev.control.ZWaveController;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MInitilizeListener;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.sg100.LGMainActivity;
import kr.co.modacom.iot.ltegwdev.sg101.LG101MainActivity;
import kr.co.modacom.iot.ltegwdev.utils.PreferenceUtil;
import kr.co.modacom.iot.ltegwdev.utils.ResourceUtil;
import kr.co.modacom.iot.ltegwdev.utils.StringUtil;

public class MainActivity extends Activity {
	private Intent mIntent;

	private Button btnSg100;
	private Button btnSg101;
	private Button btnSetting;
	private TextView txtVersion;

	private PreferenceUtil mPrefUtil;
	private ZWaveController mZWaveCon;
	private LightController mLightCon;
	private DMController mDmCon;
	private M2MManager mM2MMgr;
	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler();
	private Context mCtx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCtx = this;
		mPrefUtil = PreferenceUtil.getInstance(getApplicationContext());

		btnSg100 = (Button) findViewById(R.id.btn_sg_100);
		btnSg101 = (Button) findViewById(R.id.btn_sg_101);
		btnSetting = (Button) findViewById(R.id.btn_setting);
		
		txtVersion = (TextView) findViewById(R.id.txt_version);
		txtVersion.setText("Ver. " + ResourceUtil.getVersion());
		
		btnSg100.setOnClickListener(mOnClickListener);
		btnSg101.setOnClickListener(mOnClickListener);
		btnSetting.setOnClickListener(mOnClickListener);

		mLightCon = LightController.getInstance(mCtx);
		mZWaveCon = ZWaveController.getInstance(mCtx);
		mDmCon = DMController.getInstance(mCtx);
		mM2MMgr = M2MManager.getInstance();

		String serverIp = mPrefUtil.getServerIp();
		String serverPort = mPrefUtil.getServerPort();
		String serverCse = mPrefUtil.getServerCse();
		String gatewayId = mPrefUtil.getGatewayId();
		String deviceAeId = mPrefUtil.getDeviceAeId();

		if (serverIp == null || serverPort == null || serverCse == null || gatewayId == null) {
			// 초기 실행 시 설정 정보 입력
			mIntent = new Intent(MainActivity.this, SettingActivity.class); 
			startActivity(mIntent);
			finish();
		} else {
			Const.CSE_ID = serverCse;
			Const.CSE_IP = serverIp; 
			Const.CSE_PORT = Integer.parseInt(serverPort);
			Const.GW_ID = gatewayId;
			Const.IN_AE_ID = deviceAeId;
			
			initialize();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	public void showProgressDialog(String msg) {
		if (mProgressDialog == null)
			mProgressDialog = new ProgressDialog(mCtx);

		if (!StringUtil.isEmpty(msg))
			mProgressDialog.setMessage(msg);

		mProgressDialog.show();
	}

	public void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.hide();
	}

	// oneM2M 서버 초기화 완료 후 Z-Wave 목록 초기화
	private void syncDevice() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mZWaveCon != null && mM2MMgr.isIsM2MLibInit()) {

					mZWaveCon.clearZWaveDevice();
				} else {
					Toast.makeText(mCtx, "초기화가 이루어지지 않았습니다.", Toast.LENGTH_LONG).show();
				}
			}
		}, 1000);
	}

	// oneM2M 서버 초기화
	public void initialize() {
		mM2MMgr.initialize(mCtx, new OnM2MInitilizeListener() {

			@Override
			public void OnInitialize(boolean mIsM2MLibInit) {
				// TODO Auto-generated method stub
				Toast.makeText(mCtx, "AE Library init : " + mIsM2MLibInit, 1000).show();
			}
		}, new OnM2MSendListener() {

			@Override
			public void onPreSend() {
				// TODO Auto-generated method stub
				showProgressDialog("초기화 메시지 요청 중...");
			}

			@Override
			public void onPostSend() {
				// TODO Auto-generated method stub
				hideProgressDialog();

				// 서버 초기화 완료후 각 Controller와 연동
				mZWaveCon.setM2MManager(mM2MMgr);
				mDmCon.setM2MManager(mM2MMgr);
				mLightCon.setM2MManager(mM2MMgr);
				syncDevice();
			}

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				hideProgressDialog();
			}
		});
	}

	OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_sg_100:
				mIntent = new Intent(MainActivity.this, LGMainActivity.class);
				startActivity(mIntent);
				break;
			case R.id.btn_sg_101:
				mIntent = new Intent(MainActivity.this, LG101MainActivity.class);
				startActivity(mIntent);
				break;
			case R.id.btn_setting:
				mIntent = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(mIntent);
				break;
			}
		}
	};
}
