package kr.co.modacom.iot.ltegwdev;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import kr.co.modacom.iot.ltegwdev.utils.BlankException;
import kr.co.modacom.iot.ltegwdev.utils.PreferenceUtil;
import kr.co.modacom.iot.ltegwdev.utils.StringUtil;

public class SettingActivity extends AppCompatActivity {

	private ActionBar actionBar;
	private PreferenceUtil mPrefUtil;

	private EditText etServerIp;
	private EditText etServerPort;
	private EditText etServerCse;
	private EditText etGatewayId;
	private EditText etTempDeviceId;
	private EditText etDeviceAeId;
	
	private Button btnSave;
	private Button btnCancel;
	private Button btnDeviceAdd;

	private static ListView listDevice = null;
	private SettingDeviceListAdapter mAdapter = null;
	private ArrayList<SettingDeviceListVO> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("설정");

		etServerIp = (EditText) findViewById(R.id.et_server_ip);
		etServerPort = (EditText) findViewById(R.id.et_server_port);
		etServerCse = (EditText) findViewById(R.id.et_server_cse_id);
		etGatewayId = (EditText) findViewById(R.id.et_gw_id);
		etTempDeviceId = (EditText) findViewById(R.id.et_temp_device_id);
		etDeviceAeId = (EditText) findViewById(R.id.et_device_ae_id);

		btnSave = (Button)findViewById(R.id.btn_save);
		btnCancel = (Button)findViewById(R.id.btn_cancel);
		btnDeviceAdd = (Button)findViewById(R.id.btn_device_add);
		
		btnSave.setOnClickListener(mOnClickListener);
		btnCancel.setOnClickListener(mOnClickListener);
		btnDeviceAdd.setOnClickListener(mOnClickListener);
		
		mPrefUtil = PreferenceUtil.getInstance(getApplicationContext());

		listDevice = (ListView) findViewById(R.id.list_device);
	}

	@Override
	public void onStart() {
		super.onStart();

		etServerIp.setText(mPrefUtil.getServerIp());
		etServerPort.setText(mPrefUtil.getServerPort());
		etServerCse.setText(mPrefUtil.getServerCse());
		etGatewayId.setText(mPrefUtil.getGatewayId());
		etDeviceAeId.setText(mPrefUtil.getDeviceAeId());

		datas = mPrefUtil.getDeviceId();

		mAdapter = new SettingDeviceListAdapter(getLayoutInflater(), datas);
		listDevice.setAdapter(mAdapter);
		setListViewHeightBasedOnItems();
	}
	
	OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()) {
			case R.id.btn_save:
				saveSetting();
				break;
			case R.id.btn_cancel:
				finish();
				break;
			case R.id.btn_device_add:
				deviceAdd();
				break;
			}
		}
	};

	public void saveSetting() {

		try {
			if (etServerIp.getText() != null && !StringUtil.isEmpty(etServerIp.getText().toString())) {
				mPrefUtil.setServerIp(etServerIp.getText().toString());
			} else {
				throw new BlankException();
			}

			if (etServerPort.getText() != null && !StringUtil.isEmpty(etServerPort.getText().toString())) {
				mPrefUtil.setServerPort(etServerPort.getText().toString());
			} else {
				throw new BlankException();
			}

			if (etServerCse.getText() != null && !StringUtil.isEmpty(etServerCse.getText().toString())) {
				mPrefUtil.setServerCse(etServerCse.getText().toString());
			} else {
				throw new BlankException();
			}

			if (etDeviceAeId.getText() != null && !StringUtil.isEmpty(etDeviceAeId.getText().toString())) {
				mPrefUtil.setDeviceAeId(etDeviceAeId.getText().toString());
			} else {
				throw new BlankException();
			}
			
			if (etGatewayId.getText() != null && !StringUtil.isEmpty(etGatewayId.getText().toString())) {
				mPrefUtil.setGatewayId(etGatewayId.getText().toString());
			} else {
				throw new BlankException();
			}

			mPrefUtil.setDeviceId(mAdapter.getDatas());
			
			Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(SettingActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		} catch (BlankException be) {
			Toast.makeText(getApplicationContext(), "설정 정보를 확인하세요.", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deviceAdd() {
		String str = etTempDeviceId.getText().toString();
		if (mAdapter.getCount() < 10 && !StringUtil.isEmpty(str) && str != null) {
			etTempDeviceId.setText("");

			datas.add(new SettingDeviceListVO(str));

			SettingDeviceListAdapter adapter = new SettingDeviceListAdapter(getLayoutInflater(), datas);
			listDevice.setAdapter(adapter);
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etTempDeviceId.getWindowToken(), 0);
			setListViewHeightBasedOnItems();
		}
	}

	public static void setListViewHeightBasedOnItems() {
		 
	      // Get list adpter of listview;
	        ListAdapter listAdapter = listDevice.getAdapter();
	        if (listAdapter == null)  return;
	 
	        int numberOfItems = listAdapter.getCount();
	 
	        // Get total height of all items.
	        int totalItemsHeight = 0;
	        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
	            View item = listAdapter.getView(itemPos, null, listDevice);
	            item.measure(0, 0);
	            totalItemsHeight += item.getMeasuredHeight();
	        }
	 
	        // Get total height of all item dividers.
	        int totalDividersHeight = listDevice.getDividerHeight() *  (numberOfItems - 1);
	 
	        // Set list height.
	        ViewGroup.LayoutParams params = listDevice.getLayoutParams();
	        params.height = totalItemsHeight + totalDividersHeight;
	        listDevice.setLayoutParams(params);
	        listDevice.requestLayout();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
}
