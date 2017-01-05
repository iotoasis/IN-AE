package kr.co.modacom.iot.ltegwdev.sg100;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.control.ZWaveController;
import kr.co.modacom.iot.ltegwdev.model.Const;
import kr.co.modacom.iot.ltegwdev.model.ZwaveDeviceVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.DevModel;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.ManufacturerId;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;
import kr.co.modacom.iot.ltegwdev.utils.StringUtil;

public class LGZwaveActivity extends AppCompatActivity {

	private static final String TAG = LGZwaveActivity.class.getSimpleName();

	private int mZWFlag = 1;
	private int mUnpairingFlag = 1;
	private ActionBar actionBar;
	private Fragment fr;

	private Button btnLgZwavePlug;
	private Button btnLgZwaveWallSwitch;
	private Button btnLgZwaveGasValve;
	private Button btnLgZwaveDoorSensor;
	private Button btnLgZwaveDoorLock;

	private Button btnLgPairing;
	private Button btnLgUnpairing;

	private TextView txtManufactureId;
	private TextView txtProductTypeId;
	private TextView txtProductId;

	private ImageView imageView;

	private ZWaveController mZWaveCon = null;
	private Handler mHandler = new Handler();
	private ProgressDialog mProgressDialog;
	private Context mCtx;
	private AlertDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_activity_zwave);

		mCtx = this;

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Z-Wave");

		btnLgZwavePlug = (Button) findViewById(R.id.btn_lg_zwave_plug);
		btnLgZwaveWallSwitch = (Button) findViewById(R.id.btn_lg_zwave_wall_switch);
		btnLgZwaveGasValve = (Button) findViewById(R.id.btn_lg_zwave_gas_valve);
		btnLgZwaveDoorSensor = (Button) findViewById(R.id.btn_lg_zwave_door_sensor);
		btnLgZwaveDoorLock = (Button) findViewById(R.id.btn_lg_zwave_door_lock);

		btnLgPairing = (Button) findViewById(R.id.btn_lg_zwave_pairing);
		btnLgUnpairing = (Button) findViewById(R.id.btn_lg_zwave_unpairing);

		txtManufactureId = (TextView) findViewById(R.id.txt_lg_zwave_manufacture_id);
		txtProductTypeId = (TextView) findViewById(R.id.txt_lg_zwave_product_type_id);
		txtProductId = (TextView) findViewById(R.id.txt_lg_zwave_product_id);

		imageView = (ImageView) findViewById(R.id.zwave_image_view);

		btnLgZwavePlug.setOnClickListener(mOnClickListener);
		btnLgZwaveWallSwitch.setOnClickListener(mOnClickListener);
		btnLgZwaveGasValve.setOnClickListener(mOnClickListener);
		btnLgZwaveDoorSensor.setOnClickListener(mOnClickListener);
		btnLgZwaveDoorLock.setOnClickListener(mOnClickListener);
		btnLgPairing.setOnClickListener(mOnClickListener);
		btnLgUnpairing.setOnClickListener(mOnClickListener);

		// ZWave 장시 변경 Event 처리를 위한 Broadcast Receiver 등록
		final IntentFilter filter = new IntentFilter(Const.ACTION_ZWAVE);
		registerReceiver(mReceiver, filter);

		mZWaveCon = ZWaveController.getInstance(mCtx);
		mZWaveCon.setM2MRecvListener();

		mZWaveCon.requestDeviceList(mOnM2MSendListener, Target.ZWAVE, DevCat.ZWAVE);
		fr = new LGZwavePlugFragment();
		changeFragment(fr);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		unregisterReceiver(mReceiver);

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	public Context getContext() {
		return mCtx;
	}

	public void changeFragment(Fragment fr) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_lg_zw, fr);
		fragmentTransaction.commit();
	}

	public void requestDeviceInfo() {
		for (ZwaveDeviceVO item : mZWaveCon.getItems()) {
			if (!item.getPairingFlag())
				mZWaveCon.requestDeviceInfo(mOnM2MSendListener, item.getDeviceId(), Target.ZWAVE);
		}
	}

	public void requestPairing() {
		Target target = Target.ZWAVE;
		DevCat devCat = DevCat.ZWAVE;
		DevModel devModel;
		ManufacturerId manufacturerId;

		if (mZWFlag == 1) {
			if (((LGZwavePlugFragment) fr).getItem() == null) {
				devModel = DevModel.IOT_PLUG;
				manufacturerId = ManufacturerId.DAWON_DNS;
				showProgressDialog("Pairing...");
				mZWaveCon.requestPairing(mOnM2MSendListener, target, devCat, devModel, manufacturerId);
			}
		} else if (mZWFlag == 2) {
			if (((LGZwaveWallSwitchFragment) fr).getItem() == null) {
				devModel = DevModel.WALL_SWITCH;
				manufacturerId = ManufacturerId.LG;
				showProgressDialog("Pairing...");
				mZWaveCon.requestPairing(mOnM2MSendListener, target, devCat, devModel, manufacturerId);
			}
		} else if (mZWFlag == 3) {
			if (((LGZwaveGasValveFragment) fr).getItem() == null) {
				devModel = DevModel.GAS_LOCK;
				manufacturerId = ManufacturerId.RAMAX;
				showProgressDialog("Pairing...");
				mZWaveCon.requestPairing(mOnM2MSendListener, target, devCat, devModel, manufacturerId);
			}
		} else if (mZWFlag == 4) {
			if (((LGZwaveDoorSensorFragment) fr).getItem() == null) {
				devModel = DevModel.IOT_DOOR_WINDOW_SENSOR;
				manufacturerId = ManufacturerId.REMOTE_SOLUTION;
				showProgressDialog("Pairing...");
				mZWaveCon.requestPairing(mOnM2MSendListener, target, devCat, devModel, manufacturerId);
			}
		} else if (mZWFlag == 5) {
			if (((LGZwaveDoorLockFragment) fr).getItem() == null) {
				devModel = DevModel.DOOR_LOCK;
				manufacturerId = ManufacturerId.SAMSUNG;
				showProgressDialog("Pairing...");
				mZWaveCon.requestPairing(mOnM2MSendListener, target, devCat, devModel, manufacturerId);
			}
		}
	}

	public void requestUnpairing() {

		Target target = Target.ZWAVE;

		if (mZWFlag == 1) {
			if (((LGZwavePlugFragment) fr).getItem() != null) {
				showProgressDialog("Un Pairing...");
				mZWaveCon.requestUnpairing(mOnM2MSendListener, target,
						((LGZwavePlugFragment) fr).getItem().getDeviceId());
			}
		} else if (mZWFlag == 2) {
			if (((LGZwaveWallSwitchFragment) fr).getItem() != null) {
				showProgressDialog("Un Pairing...");
				mZWaveCon.requestUnpairing(mOnM2MSendListener, target,
						((LGZwaveWallSwitchFragment) fr).getItem().getDeviceId());
			}
		} else if (mZWFlag == 3) {
			if (((LGZwaveGasValveFragment) fr).getItem() != null) {
				showProgressDialog("Un Pairing...");
				mZWaveCon.requestUnpairing(mOnM2MSendListener, target,
						((LGZwaveGasValveFragment) fr).getItem().getDeviceId());
			}
		} else if (mZWFlag == 4) {
			if (((LGZwaveDoorSensorFragment) fr).getItem() != null) {
				showProgressDialog("Un Pairing...");
				mZWaveCon.requestUnpairing(mOnM2MSendListener, target,
						((LGZwaveDoorSensorFragment) fr).getItem().getDeviceId());
			}
		} else if (mZWFlag == 5) {
			if (((LGZwaveDoorLockFragment) fr).getItem() != null) {
				showProgressDialog("Un Pairing...");
				mZWaveCon.requestUnpairing(mOnM2MSendListener, target,
						((LGZwaveDoorLockFragment) fr).getItem().getDeviceId());
			}
		}
	}

	public void updateModelInfo(String manufactureId, String ProductTypeId, String ProductId) {
		if (txtManufactureId != null && txtProductTypeId != null && txtProductId != null) {
			txtManufactureId.setText(manufactureId);
			txtProductTypeId.setText(ProductTypeId);
			txtProductId.setText(ProductId);
		}
	}

	public void clearModelInfo() {
		txtManufactureId.setText("");
		txtProductTypeId.setText("");
		txtProductId.setText("");
	}

	public void updateImageView(int drawableRes) {
		imageView.setImageResource(drawableRes);
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

	public boolean isInModelName(ZwaveDeviceVO item) {
		boolean result = false;

		if (item.getModelName().equals(DevModel.IOT_PLUG.getName())
				|| item.getModelName().equals(DevModel.WALL_SWITCH.getName())
				|| item.getModelName().equals(DevModel.GAS_LOCK.getName())
				|| item.getModelName().equals(DevModel.IOT_DOOR_WINDOW_SENSOR.getName())
				|| item.getModelName().equals(DevModel.DOOR_LOCK.getName())) {
			result = true;
		}

		return result;
	}

	public void showUnpairDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(mCtx);
		ab.setTitle("Z-Wave");
		ab.setMessage("지원되지 않는 디바이스가 확인되었습니다.\n확인버튼을 누르면 Unpairing 명령을 전송합니다.\nUnpairing이 완료되면 뒤로가기 후 다시 실행해 주세요.");
		ab.setCancelable(false);
		ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				requestUnpairing();
			}
		});

		ab.show();
	}

	public int getUnpairingFlag() {
		return mUnpairingFlag++;
	}

	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	};

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null || intent.getAction() == null)
				return;

			// ZWave 장치에 대한 이벤트가 발생한 경우
			if (Const.ACTION_ZWAVE.equals(intent.getAction())) {
				final int type = intent.getIntExtra(Const.UPDATE_TYPE, -1);

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideProgressDialog();
						FunctionId funcId = FunctionId.valueOf(type);
						switch (funcId) {
						case DEVICE_LIST:
							requestDeviceInfo();
							break;
						default:
							Fragment mCurrentPage = null;
							switch (mZWFlag) {
							case 1:
								mCurrentPage = fr;
								if (mCurrentPage != null)
									((LGZwavePlugFragment) mCurrentPage).updateZWaveState(funcId);
								break;
							case 2:
								mCurrentPage = fr;
								if (mCurrentPage != null)
									((LGZwaveWallSwitchFragment) mCurrentPage).updateZWaveState(funcId);
								break;
							case 3:
								mCurrentPage = fr;
								if (mCurrentPage != null)
									((LGZwaveGasValveFragment) mCurrentPage).updateZWaveState(funcId);
								break;
							case 4:
								mCurrentPage = fr;
								if (mCurrentPage != null)
									((LGZwaveDoorSensorFragment) mCurrentPage).updateZWaveState(funcId);
								break;
							case 5:
								mCurrentPage = fr;
								if (mCurrentPage != null)
									((LGZwaveDoorLockFragment) mCurrentPage).updateZWaveState(funcId);
								break;
							}
							break;
						}
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

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_lg_zwave_plug:
				if (mZWFlag != 1) {
					mZWFlag = 1;
					fr = new LGZwavePlugFragment();
					changeFragment(fr);
					updateImageView(R.drawable.iot_plug);
				}
				break;
			case R.id.btn_lg_zwave_wall_switch:
				if (mZWFlag != 2) {
					mZWFlag = 2;
					fr = new LGZwaveWallSwitchFragment();
					changeFragment(fr);
					updateImageView(R.drawable.iot_switch);
				}
				break;
			case R.id.btn_lg_zwave_gas_valve:
				if (mZWFlag != 3) {
					mZWFlag = 3;
					fr = new LGZwaveGasValveFragment();
					changeFragment(fr);
					updateImageView(R.drawable.iot_gas_lock);
				}
				break;
			case R.id.btn_lg_zwave_door_sensor:
				if (mZWFlag != 4) {
					mZWFlag = 4;
					fr = new LGZwaveDoorSensorFragment();
					changeFragment(fr);
					updateImageView(R.drawable.iot_door_sensor);
				}
				break;
			case R.id.btn_lg_zwave_door_lock:
				if (mZWFlag != 5) {
					mZWFlag = 5;
					fr = new LGZwaveDoorLockFragment();
					changeFragment(fr);
					updateImageView(R.drawable.iot_door_lock);
				}
				break;
			case R.id.btn_lg_zwave_pairing:
				requestPairing();
				break;
			case R.id.btn_lg_zwave_unpairing:
				requestUnpairing();
				break;
			}
		}
	};
}
