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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.SettingDeviceListVO;
import kr.co.modacom.iot.ltegwdev.control.DMController;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.GatewayVO;
import kr.co.modacom.iot.ltegwdev.model.type.ConnectStatus;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.PreferenceUtil;
import kr.co.modacom.iot.ltegwdev.utils.StringUtil;

public class LG101DMActivity extends AppCompatActivity {
	private static final String TAG = LG101DMActivity.class.getSimpleName();

	private ActionBar actionBar;
	private Context mCtx;

	private Spinner spinLg101LightId;

	private EditText etLgGatewayWan;
	private EditText etLgGatewayMac;
	private EditText etLgGatewayDip;
	private EditText etLgGatewayPip;
	private EditText etLgGatewayFwVer;
	private EditText etLgGatewayPairingN;
	private EditText etLgGatewayZwaveStatus;
	private EditText etLgGatewaySsid;
	private EditText etLgGatewayRssi;
	private EditText etLgGatewayRunningTime;
	private EditText etLgGatewaySessionStatus;
	private EditText etLgGatewayLatLong;

	private DMController mDmCon;
	private PreferenceUtil mPrefUtil;
	private ArrayList<String> datas;
	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler();

	private GatewayVO mGateway;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_101_activity_dm);

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("진단/DM");
		mCtx = this;

		spinLg101LightId = (Spinner) findViewById(R.id.sp_lg_101_light_id);

		etLgGatewayWan = (EditText) findViewById(R.id.et_lg_101_gw_wan);
		etLgGatewayMac = (EditText) findViewById(R.id.et_lg_101_gw_mac);
		etLgGatewayDip = (EditText) findViewById(R.id.et_lg_101_gw_dip);
		etLgGatewayPip = (EditText) findViewById(R.id.et_lg_101_gw_pip);
		etLgGatewayFwVer = (EditText) findViewById(R.id.et_lg_101_gw_fw_ver);
		etLgGatewayPairingN = (EditText) findViewById(R.id.et_lg_101_gw_pairing_n);
		etLgGatewayZwaveStatus = (EditText) findViewById(R.id.et_lg_101_gw_zwave_status);
		etLgGatewaySsid = (EditText) findViewById(R.id.et_lg_101_gw_ssid);
		etLgGatewayRssi = (EditText) findViewById(R.id.et_lg_101_gw_rssi);
		etLgGatewayRunningTime = (EditText) findViewById(R.id.et_lg_101_gw_running_time);
		etLgGatewaySessionStatus = (EditText) findViewById(R.id.et_lg_101_gw_session_status);
		etLgGatewayLatLong = (EditText) findViewById(R.id.et_lg_101_gw_lat_long);

		mDmCon = DMController.getInstance(mCtx);
		mDmCon.setM2MRecvListener();
		mDmCon.setDmItem(null);

		mPrefUtil = PreferenceUtil.getInstance(mCtx);
		datas = new ArrayList<String>();

		final IntentFilter filter = new IntentFilter(Const.ACTION_DM_101);
		registerReceiver(mReceiver, filter);

		datas.add("선택");

		for (SettingDeviceListVO item : mPrefUtil.getDeviceId())
			datas.add(item.getDeviceId());

		if (datas.size() != 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);

			spinLg101LightId.setAdapter(adapter);
			spinLg101LightId.setOnItemSelectedListener(mOnItemSelectedListener);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	public GatewayVO getGatewayFromDM() {
		if (mDmCon.getDmItem() != null) {
			if (mDmCon.getDmItem().getGateway() != null) {
				return mDmCon.getDmItem().getGateway();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void updateGatewayDisplay() {
		mGateway = getGatewayFromDM();

		if (mGateway != null) {
			etLgGatewayWan.setText((mGateway.getWan() == null ? "/"
					: ConnectStatus.valueOf(Integer.parseInt(mGateway.getWan())).getName()));
			etLgGatewayMac.setText((mGateway.getMac() == null ? "/" : mGateway.getMac()));
			etLgGatewayDip.setText((mGateway.getDip() == null ? "/" : mGateway.getDip()));
			etLgGatewayPip.setText((mGateway.getPip() == null ? "/" : mGateway.getPip()));
			etLgGatewayFwVer.setText((mGateway.getFwVer() == null ? "/" : mGateway.getFwVer()));
			etLgGatewayPairingN.setText((mGateway.getPairingN() == null ? "/"
					: ConnectStatus.valueOf(Integer.parseInt(mGateway.getPairingN())).getName()));
			etLgGatewayZwaveStatus.setText((mGateway.getZwaveStatus() == null ? "/"
					: ConnectStatus.valueOf(Integer.parseInt(mGateway.getZwaveStatus())).getName()));
			etLgGatewaySsid.setText((mGateway.getSsid() == null ? "/" : mGateway.getSsid()));
			etLgGatewayRssi.setText((mGateway.getRssi() == null ? "/" : mGateway.getRssi()));
			etLgGatewayRunningTime.setText((mGateway.getRunningTime() == null ? "/" : mGateway.getRunningTime()));
			etLgGatewaySessionStatus.setText((mGateway.getSessionStatus() == null ? "/"
					: ConnectStatus.valueOf(Integer.parseInt(mGateway.getSessionStatus())).getName()));

			String latLong = (mGateway.getLatitude() == null ? "" : mGateway.getLatitude() + " ") + "/"
					+ (mGateway.getLongitude() == null ? "" : mGateway.getLongitude());
			etLgGatewayLatLong.setText(latLong);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	};

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

	private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			if (!datas.get(arg2).equals("선택"))
				mDmCon.requestDmInfo(mOnM2MSendListener, datas.get(arg2));
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null || intent.getAction() == null)
				return;

			if (Const.ACTION_DM_101.equals(intent.getAction())) {
				final int type = intent.getIntExtra(Const.UPDATE_TYPE, -1);
				final String jMsg = intent.getStringExtra("jMsg");

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideProgressDialog();
						updateGatewayDisplay();
					}
				}, 100);
			}
		}
	};

	protected OnM2MSendListener mOnM2MSendListener = new OnM2MSendListener() {

		@Override
		public void onPreSend() {
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