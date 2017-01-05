package kr.co.modacom.iot.ltegwdev.sg100;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.control.DMController;
import kr.co.modacom.iot.ltegwdev.json.JSONMessage;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.StringUtil;

public class LGDMActivity extends AppCompatActivity {
	private static final String TAG = LGDMActivity.class.getSimpleName();
	private boolean isFragmentG = true;

	private ActionBar actionBar;

	private Fragment fr;

	Button btnGateWay;
	Button btnThings;

	private DMController mDmCon = null;
	private Handler mHandler = new Handler();
	private ProgressDialog mProgressDialog;
	private Context mCtx;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_activity_dm);

		mCtx = this;

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("진단");

		btnGateWay = (Button) findViewById(R.id.btn_lg_md_gateway);
		btnThings = (Button) findViewById(R.id.btn_lg_md_things);

		btnGateWay.setOnClickListener(mOnClickListener);
		btnThings.setOnClickListener(mOnClickListener);

		final IntentFilter filter = new IntentFilter(Const.ACTION_DM);
		registerReceiver(mReceiver, filter);

		mDmCon = DMController.getInstance(mCtx);
		mDmCon.setM2MRecvListener();
		mDmCon.setDmItem(null);

		fr = new LGGatewayFragment();
		changeFragment(fr);
		mDmCon.requestDmInfo(mOnM2MSendListener, "mit_sg100_iot_agent");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	public void changeFragment(Fragment fr) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_lg_md, fr);
		fragmentTransaction.commit();
	}

	public Context getCtx() {
		return mCtx;
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

	public boolean onOptionsItemSelected(MenuItem item) {
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
			switch (v.getId()) {
			case R.id.btn_lg_md_gateway:
				if (!isFragmentG) {
					fr = new LGGatewayFragment();
					isFragmentG = true;
					changeFragment(fr);
				}
				break;
			case R.id.btn_lg_md_things:
				if (isFragmentG) {
					fr = new LGThingsFragment();
					isFragmentG = false;
					changeFragment(fr);
				}
				break; 
			}
		}
	};

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null || intent.getAction() == null)
				return;

			if (Const.ACTION_DM.equals(intent.getAction())) {
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideProgressDialog();
						((LGGatewayFragment) fr).updateGatewayDisplay();
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
