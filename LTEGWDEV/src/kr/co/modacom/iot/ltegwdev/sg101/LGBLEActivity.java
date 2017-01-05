package kr.co.modacom.iot.ltegwdev.sg101;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.utils.BleService;
import kr.co.modacom.iot.ltegwdev.utils.BleUtil;
import kr.co.modacom.iot.ltegwdev.utils.PreferenceUtil;

@SuppressLint("NewApi")
public class LGBLEActivity extends AppCompatActivity {

	private ActionBar actionBar;
	private Context mCtx;

	private Button btnLgBleLightOn;
	private Button btnLgBleAllLightOn;
	private Button btnLgBleAllLightOff;
	private Button btnLgBleSendStart;
	private Button btnLgBleSendStop;
	
	private EditText etLgBleSendInfo;
	
	private PreferenceUtil mPrefUtil;
	public static String employeeId;
	
	private BluetoothAdapter mBTAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_activity_ble);

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("BLE");

		mCtx = this;

		btnLgBleLightOn = (Button) findViewById(R.id.btn_lg_ble_light_on);
		btnLgBleAllLightOn = (Button) findViewById(R.id.btn_lg_ble_all_light_on);
		btnLgBleAllLightOff = (Button) findViewById(R.id.btn_lg_ble_all_light_off);
		btnLgBleSendStart = (Button) findViewById(R.id.btn_lg_ble_send_start);
		btnLgBleSendStop = (Button) findViewById(R.id.btn_lg_ble_send_stop);

		etLgBleSendInfo = (EditText) findViewById(R.id.et_lg_ble_send_info);
		
		btnLgBleLightOn.setOnClickListener(mOnClickListener);
		btnLgBleAllLightOn.setOnClickListener(mOnClickListener);
		btnLgBleAllLightOff.setOnClickListener(mOnClickListener);
		btnLgBleSendStart.setOnClickListener(mOnClickListener);
		btnLgBleSendStop.setOnClickListener(mOnClickListener);

		mPrefUtil = PreferenceUtil.getInstance(mCtx);

		if (mPrefUtil.getEmployeeId() != null)
			employeeId = mPrefUtil.getEmployeeId();
		else
			employeeId = "";
		
		init();	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void init() {
		// BLE check
		if (!BleUtil.isBLESupported(this)) {
			return;
		}

		// BT check
		BluetoothManager manager = BleUtil.getManager(this);
		if (manager != null) {
			mBTAdapter = manager.getAdapter();
		}
		if ((mBTAdapter == null) || (!mBTAdapter.isEnabled())) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, 1);
		}
	}
	
	private void lightOnAdvertise() {
		Intent intent = new Intent(getApplicationContext(), BleService.class);
		intent.setFlags(1);
		startService(intent); // 서비스 시작
	}

	private void emergencyOnAdvertise() {
		Intent intent = new Intent(getApplicationContext(), BleService.class);
		intent.setFlags(2);
		startService(intent); // 서비스 시작
	}

	private void emergencyStopAdvertise() {
		Intent intent = new Intent(getApplicationContext(), BleService.class);
		intent.setFlags(3);
		startService(intent); // 서비스 시작
	}

	private void sendStart() { 
		if(!etLgBleSendInfo.getText().toString().equals("")) {
			employeeId = etLgBleSendInfo.getText().toString();
		}
		
		Intent intent = new Intent(getApplicationContext(), BleService.class);
		intent.setFlags(1);
		startService(intent); // 서비스 시작
	}

	private void stopAdvertise() {
		Intent intent = new Intent(getApplicationContext(), BleService.class);
		stopService(intent);
		setProgressBarIndeterminateVisibility(false);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	};

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if(Build.VERSION.SDK_INT < 21) {
				Toast.makeText(mCtx, "BLE 송신을 지원하지 않는 안드로이드 버전입니다.", Toast.LENGTH_SHORT).show();
			} else {
				switch (v.getId()) {
				case R.id.btn_lg_ble_light_on:
					lightOnAdvertise();
					break;
				case R.id.btn_lg_ble_all_light_on:
					emergencyOnAdvertise();
					break;
				case R.id.btn_lg_ble_all_light_off:
					emergencyStopAdvertise();
					break;
				case R.id.btn_lg_ble_send_start:
					sendStart();
					break;
				case R.id.btn_lg_ble_send_stop:
					stopAdvertise();
					break;
				}
			}
		}
	};
}
