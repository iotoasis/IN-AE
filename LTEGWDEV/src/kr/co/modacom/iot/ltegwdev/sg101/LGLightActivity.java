package kr.co.modacom.iot.ltegwdev.sg101;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.SettingDeviceListVO;
import kr.co.modacom.iot.ltegwdev.control.LightController;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.type.OnOffStatus;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.PreferenceUtil;
import kr.co.modacom.iot.ltegwdev.utils.StringUtil;

public class LGLightActivity extends AppCompatActivity {
	private static final String TAG = LGLightActivity.class.getSimpleName();

	private ActionBar actionBar;
	private Context mCtx;

	private Spinner spinLgLightId;

	private Button btnLgLightOn;
	private Button btnLgLightOff;
	private Button btnLgAlertOn;
	private Button btnLgAlertOff;
	private Button btnLgLightAllOn;
	private Button btnLgLightAllOff;
	private Button btnLgAlertAllOn;
	private Button btnLgAlertAllOff;
	private Button btnLgLightAlertOn;
	private Button btnLgLightAlertOff;
	private Button btnLgLightAlertAllOn;
	private Button btnLgLightAlertAllOff;

	private LightController mLightCon;
	private PreferenceUtil mPrefUtil;
	private ArrayList<String> datas;
	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler();

	public int lightFlag = 1;
	public int lightCount = 0;
	public int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_activity_light);

		mCtx = this;

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("가로등/경광등");

		btnLgLightOn = (Button) findViewById(R.id.btn_lg_light_on);
		btnLgLightOff = (Button) findViewById(R.id.btn_lg_light_off);
		btnLgAlertOn = (Button) findViewById(R.id.btn_lg_alert_on);
		btnLgAlertOff = (Button) findViewById(R.id.btn_lg_alert_off);
		btnLgLightAllOn = (Button) findViewById(R.id.btn_lg_all_light_on);
		btnLgLightAllOff = (Button) findViewById(R.id.btn_lg_all_light_off);
		btnLgAlertAllOn = (Button) findViewById(R.id.btn_lg_all_alert_on);
		btnLgAlertAllOff = (Button) findViewById(R.id.btn_lg_all_alert_off);
		btnLgLightAlertOn = (Button) findViewById(R.id.btn_lg_light_alert_on);
		btnLgLightAlertOff = (Button) findViewById(R.id.btn_lg_light_alert_off);
		btnLgLightAlertAllOn = (Button) findViewById(R.id.btn_lg_all_light_alert_on);
		btnLgLightAlertAllOff = (Button) findViewById(R.id.btn_lg_all_light_alert_off);

		btnLgLightOn.setOnClickListener(mOnClickListener);
		btnLgLightOff.setOnClickListener(mOnClickListener);
		btnLgAlertOn.setOnClickListener(mOnClickListener);
		btnLgAlertOff.setOnClickListener(mOnClickListener);
		btnLgLightAllOn.setOnClickListener(mOnClickListener);
		btnLgLightAllOff.setOnClickListener(mOnClickListener);
		btnLgAlertAllOn.setOnClickListener(mOnClickListener);
		btnLgAlertAllOff.setOnClickListener(mOnClickListener);
		btnLgLightAlertOn.setOnClickListener(mOnClickListener);
		btnLgLightAlertOff.setOnClickListener(mOnClickListener);
		btnLgLightAlertAllOn.setOnClickListener(mOnClickListener);
		btnLgLightAlertAllOff.setOnClickListener(mOnClickListener);

		spinLgLightId = (Spinner) findViewById(R.id.sp_lg_light_id);
		mPrefUtil = PreferenceUtil.getInstance(mCtx);
		datas = new ArrayList<String>();

		final IntentFilter filter = new IntentFilter(Const.ACTION_LIGHT);
		registerReceiver(mReceiver, filter);

		for (SettingDeviceListVO item : mPrefUtil.getDeviceId()) {
			datas.add(item.getDeviceId());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);

		spinLgLightId.setAdapter(adapter);
		spinLgLightId.setOnItemSelectedListener(mOnItemSelectedListener);

		mLightCon = LightController.getInstance(mCtx);
		mLightCon.setM2MRecvListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	public void requestLightControl(OnOffStatus streetLight, OnOffStatus warningLight) {
		mLightCon.requestStateUpdate(mOnM2MSendListener, Target.DEV_MGR, streetLight, warningLight,
				datas.get(spinLgLightId.getSelectedItemPosition()), true);
	}

	public void requestAllLightControl(final OnOffStatus streetLight, final OnOffStatus warningLight) {
		for (final String data : datas) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mLightCon.requestStateUpdate(mOnM2MSendListener, Target.DEV_MGR, streetLight, warningLight, data,
							false);
				}
			}).start();
		}
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

	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	};

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			count = 0;
			
			switch (v.getId()) {
			case R.id.btn_lg_light_on:
				requestLightControl(OnOffStatus.ON, null);
				lightFlag = 1;
				lightCount = 1;
				break;
			case R.id.btn_lg_light_off:
				requestLightControl(OnOffStatus.OFF, null);
				lightFlag = 2;
				lightCount = 1;
				break;
			case R.id.btn_lg_alert_on:
				requestLightControl(null, OnOffStatus.ON);
				lightFlag = 3;
				lightCount = 1;
				break;
			case R.id.btn_lg_alert_off:
				requestLightControl(null, OnOffStatus.OFF);
				lightFlag = 4;
				lightCount = 1;
				break;
			case R.id.btn_lg_all_light_on:
				requestAllLightControl(OnOffStatus.ON, null);
				lightFlag = 5;
				lightCount = datas.size();
				break;
			case R.id.btn_lg_all_light_off:
				requestAllLightControl(OnOffStatus.OFF, null);
				lightFlag = 6;
				lightCount = datas.size();
				break;
			case R.id.btn_lg_all_alert_on:
				requestAllLightControl(null, OnOffStatus.ON);
				lightFlag = 7;
				lightCount = datas.size();
				break;
			case R.id.btn_lg_all_alert_off:
				requestAllLightControl(null, OnOffStatus.OFF);
				lightFlag = 8;
				lightCount = datas.size();
				break;
			case R.id.btn_lg_light_alert_on:
				requestLightControl(OnOffStatus.ON, OnOffStatus.ON);
				lightFlag = 9;
				lightCount = 1;
				break;
			case R.id.btn_lg_light_alert_off:
				requestLightControl(OnOffStatus.OFF, OnOffStatus.OFF);
				lightFlag = 10;
				lightCount = 1;
				break;
			case R.id.btn_lg_all_light_alert_on:
				requestAllLightControl(OnOffStatus.ON, OnOffStatus.ON);
				lightFlag = 11;
				lightCount = datas.size();
				break;
			case R.id.btn_lg_all_light_alert_off:
				requestAllLightControl(OnOffStatus.OFF, OnOffStatus.OFF);
				lightFlag = 12;
				lightCount = datas.size();
				break;
			}
		}
	};

	private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, final Intent intent) {
			if (intent == null || intent.getAction() == null) 
				return;
			
			if (Const.ACTION_LIGHT.equals(intent.getAction())) {
				final int type = intent.getIntExtra(Const.UPDATE_TYPE, -1);
				count += 1;

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideProgressDialog();

						String successMsg = "";
						if (count == lightCount) {
							switch (lightFlag) {
							case 1:
								successMsg = "가로등 ON 제어가 성공하였습니다.";
								break;
							case 2:
								successMsg = "가로등 OFF 제어가 성공하였습니다.";
								break;
							case 3:
								successMsg = "경광등 ON 제어가 성공하였습니다.";
								break;
							case 4:
								successMsg = "경광등 OFF 제어가 성공하였습니다.";
								break;
							case 5:
								successMsg = "전체 가로등 ON 제어가 성공하였습니다.";
								break;
							case 6:
								successMsg = "전체 가로등 OFF 제어가 성공하였습니다.";
								break;
							case 7:
								successMsg = "전체 경광등 ON 제어가 성공하였습니다.";
								break;
							case 8:
								successMsg = "전체 경광등 OFF 제어가 성공하였습니다.";
								break;
							case 9:
								successMsg = "가로등/경광등 ON 제어가 성공하였습니다.";
								break;
							case 10:
								successMsg = "가로등/경광등 OFF 제어가 성공하였습니다.";
								break;
							case 11:
								successMsg = "전체 가로등/경광등 ON 제어가 성공하였습니다.";
								break;
							case 12:
								successMsg = "전체 가로등/경광등 OFF 제어가 성공하였습니다.";
								break;
							default:
								break;
							}
							Toast.makeText(mCtx, successMsg, Toast.LENGTH_SHORT).show();
						}
					}
				}, 100);
			}
		}
	};

	protected OnM2MSendListener mOnM2MSendListener = new OnM2MSendListener() {
		@Override
		public void onPreSend() {
			if (lightFlag != 5 && lightFlag != 6 && lightFlag != 7 && lightFlag != 8 && lightFlag != 11
					&& lightFlag != 12)
				showProgressDialog("메시지 전송 요청 중...");
		}

		@Override
		public void onPostSend() {
		}

		@Override
		public void onCancelled() {
			hideProgressDialog();
		}
	};
}