package kr.co.modacom.iot.ltegwdev.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import kr.co.modacom.iot.ltegwdev.model.Const;

@SuppressLint("NewApi")
public class BleService extends Service {

	private static final String TAG = BleService.class.getSimpleName();

	private BluetoothAdapter mBTAdapter;
	private BluetoothLeAdvertiser mBTAdvertiser;
	private BluetoothGattServer mGattServer;
	private static int request_enable_bt = 1;
	String employeeId;

	private AdvertiseCallback mAdvCallback = new AdvertiseCallback() {
		public void onStartSuccess(AdvertiseSettings settingsInEffect) {
			if (settingsInEffect != null) {
			}
		};

		public void onStartFailure(int errorCode) {
		};
	};
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		switch(intent.getFlags()) {
		case 1:
			LightOnAdvertise();
			break;
		case 2:
			emergencyOnAdvertise();
			break;
		case 3:
			emergencyStopAdvertise();
			break;
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
        super.onDestroy();
		stopAdvertise();  
	}

	private void init() {
		// BLE check
		if (!BleUtil.isBLESupported(this)) {
			Toast.makeText(this, Const.BLE_NOT_SUPPORTED, Toast.LENGTH_SHORT).show();
			return;
		}

		// BT check
		BluetoothManager manager = BleUtil.getManager(this);
		if (manager != null) {
			mBTAdapter = manager.getAdapter();
		}
		if ((mBTAdapter == null) || (!mBTAdapter.isEnabled())) {
			Toast.makeText(this, Const.BT_UNAVAILABLE, Toast.LENGTH_SHORT).show();
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		}
	}
	
	private void sendStart(String employeeId) {
		if (request_enable_bt != 1)
			stopAdvertise();

		if (mBTAdapter == null) {
			return;
		}
		
		if (mBTAdvertiser == null) {
			mBTAdvertiser = mBTAdapter.getBluetoothLeAdvertiser();
		}
		if (mBTAdvertiser != null) {
			mBTAdvertiser.startAdvertising(BleUtil.createAdvSettings(true, 0),
					BleUtil.createEmployeeIDAdvertiseData(employeeId.getBytes(), 20 - employeeId.length()),
					mAdvCallback);
			request_enable_bt++;
		}
	}
	
	private void stopAdvertise() {
		if (mGattServer != null) {
			mGattServer.clearServices();
			mGattServer.close();
			mGattServer = null;
		}
		if (mBTAdvertiser != null) {
			mBTAdvertiser.stopAdvertising(mAdvCallback);
			mBTAdvertiser = null;
		}
	}
	

	private void LightOnAdvertise() {
		stopAdvertise();
		if (mBTAdapter == null) {
			return;
		}
		if (mBTAdvertiser == null) {
			mBTAdvertiser = mBTAdapter.getBluetoothLeAdvertiser();
		}
		if (mBTAdvertiser != null) {
			mBTAdvertiser.startAdvertising(BleUtil.createAdvSettings(true, 0), BleUtil.createLightOnAdvertiseData(),
					mAdvCallback);
		}
	}

	private void emergencyOnAdvertise() {
		stopAdvertise();
		if (mBTAdapter == null) {
			return;
		}
		if (mBTAdvertiser == null) {
			mBTAdvertiser = mBTAdapter.getBluetoothLeAdvertiser();
		}
		if (mBTAdvertiser != null) {
			mBTAdvertiser.startAdvertising(BleUtil.createAdvSettings(true, 0), BleUtil.createAllOnAdvertiseData(),
					mAdvCallback);
		}
	}

	private void emergencyStopAdvertise() {
		stopAdvertise();
        System.out.println("emergencyStopAdvertise");
        if (mBTAdapter == null) {
            return;
        }
        if (mBTAdvertiser == null) {
            mBTAdvertiser = mBTAdapter.getBluetoothLeAdvertiser();
        }
        if (mBTAdvertiser != null) {
            mBTAdvertiser.startAdvertising(
                    BleUtil.createAdvSettings(true, 0),
                    BleUtil.createAllOffAdvertiseData(),
                    mAdvCallback);
        }

	}
}
