package kr.co.modacom.iot.ltegwdev.sg100;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.R.layout;
import kr.co.modacom.iot.ltegwdev.control.ZWaveController;
import kr.co.modacom.iot.ltegwdev.model.PropertyVO;
import kr.co.modacom.iot.ltegwdev.model.ZwaveDeviceVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.DevLabel;
import kr.co.modacom.iot.ltegwdev.model.type.DevModel;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.LockUnlockStatus;
import kr.co.modacom.iot.ltegwdev.model.type.OnOffStatus;
import kr.co.modacom.iot.ltegwdev.model.type.OpenCloseStatus;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;

public class LGZwaveDoorLockFragment extends Fragment {

	private static final String TAG = LGZwavePlugFragment.class.getSimpleName();

	private Button btnLgDoorLockLock;
	private Button btnLgDoorLockUnlock;

	private TextView tvInstanceId;
	private TextView tvStatus;

	private ZWaveController mZWaveCon;
	private ZwaveDeviceVO mItem;
	
	private Context mCtx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lg_fragment_zw_door_lock, container, false);
		
		if (getActivity() instanceof LGZwaveActivity) {
			mCtx = ((LGZwaveActivity)getActivity()).getContext();
		}
		
		mZWaveCon = ZWaveController.getInstance(mCtx);
		
		tvInstanceId = (TextView) view.findViewById(R.id.tv_lg_zwave_door_lock_instance_id);
		tvStatus = (TextView) view.findViewById(R.id.tv_lg_zwave_door_lock_status);

		btnLgDoorLockLock = (Button) view.findViewById(R.id.btn_lg_zwave_door_lock_lock);
		btnLgDoorLockUnlock = (Button) view.findViewById(R.id.btn_lg_zwave_door_lock_unlock);

		btnLgDoorLockLock.setOnClickListener(mOnClickListener);
		btnLgDoorLockUnlock.setOnClickListener(mOnClickListener);

		
		return view;
	}
	

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mItem = getItemFromList(true);
		updateZWaveItemStatus(mItem);
	}

	public ZwaveDeviceVO getItemFromList(boolean flag) {
		for (ZwaveDeviceVO item : mZWaveCon.getItems()) {
			if (!((LGZwaveActivity)mCtx).isInModelName(item)) {
				mItem = item;
				if (((LGZwaveActivity) mCtx).getUnpairingFlag() == 1) {
					((LGZwaveActivity) mCtx).showUnpairDialog();
				}
				return item;
			} else if (item.getDevCat() == DevCat.ZWAVE.getCode() && item.getModelName().equals(DevModel.DOOR_LOCK.getName())
					&& item.getPairingFlag() == flag) {
				return item;
			}
		}
		return null;
	}

	public ZwaveDeviceVO getItemFromList(String deviceId) {
		for (ZwaveDeviceVO item : mZWaveCon.getItems())
			if (item.getDeviceId().substring(0, item.getDeviceId().length() - 1)
					.equals(deviceId.substring(0, deviceId.length() - 1)))
				return item;
		return null;
	}

	public void requestDeviceInfo() {
		mItem = getItemFromList(true);

		if (mItem != null)
			mZWaveCon.requestDeviceInfo(mOnM2MSendListener, mItem.getDeviceId(), Target.ZWAVE);
	}

	public ZwaveDeviceVO getItem() {
		return mItem;
	}

	public void setItem(ZwaveDeviceVO mItem) {
		this.mItem = mItem;
	}

	public void updateZWaveItemStatus(ZwaveDeviceVO item) {
		if (item != null) {

			if (item.getModelName().contains("Unknown")) {
				return;
			}
			
			if (tvInstanceId != null) {
				tvInstanceId.setText(item.getDeviceId());
			}
			if (tvStatus != null) {
				for (PropertyVO property : item.getProperties()) {
					if (property.getLabel().equals(DevLabel.LOCKED.getName())) {
						tvInstanceId.setText(Integer.toString(property.getInstanceId()));
						tvStatus.setText(((Integer) property.getValue()) == 1 ? LockUnlockStatus.LOCK.getName()
								: LockUnlockStatus.UNLOCK.getName());
						break;
					}
				}
			}
			updateModelInfo();
		} else {
			clearModelInfo();
			clearZWaveItemStatus();
		}
	}

	public void clearZWaveItemStatus() {
		// TODO Auto-generated method stub
		mItem = null;

		if (tvInstanceId != null)
			tvInstanceId.setText("");

		if (tvStatus != null)
			tvStatus.setText("");
	}

	public void requestStateUpdate(LockUnlockStatus value) {
		if (mItem != null) {
			for (PropertyVO property : mItem.getProperties()) {
				if (property.getLabel().equals(DevLabel.LOCKED.getName())) {
					property.setValue(value.getCode());
					break;
				}
			}

			mZWaveCon.requestStateUpdate(mOnM2MSendListener, Target.ZWAVE, mItem);
		}
	}

	public void showProgressDialog(String msg) {
		Activity activity = getActivity();
		if (activity instanceof LGZwaveActivity) {
			((LGZwaveActivity) activity).showProgressDialog(msg);
		}
	}

	public void hideProgressDialog() {
		Activity activity = getActivity();
		if (activity instanceof LGZwaveActivity) {
			((LGZwaveActivity) activity).hideProgressDialog();
		}
	}

	public void updateModelInfo() {
		Activity activity = getActivity();
		if (activity instanceof LGZwaveActivity) {
			((LGZwaveActivity) activity).updateModelInfo(mItem.getModelInfo().substring(0, 4),
					mItem.getModelInfo().substring(4, 8), mItem.getModelInfo().substring(8, 12));
		}
	}

	private void clearModelInfo() {
		Activity activity = getActivity();
		if (activity instanceof LGZwaveActivity) {
			((LGZwaveActivity) activity).clearModelInfo();
		}
	}

	public void updateZWaveState(FunctionId funcId) {
		if (mZWaveCon == null) {
			return;
		}

		switch (funcId) {
		case DEVICE_INFO:
			hideProgressDialog();
			mItem = getItemFromList(true);
			updateZWaveItemStatus(mItem);
		case DEVICE_PAIRING:
			hideProgressDialog();
			mItem = getItemFromList(true);
			updateZWaveItemStatus(mItem);
			break;
		case DEVICE_UNPAIRING:
			hideProgressDialog();
			mItem = getItemFromList(mItem.getDeviceId());
			updateZWaveItemStatus(null);
			break;
		case DEVICE_CONTROL:
		case UPDATED_DEVICE:
			mItem = getItemFromList(mItem.getDeviceId());
			updateZWaveItemStatus(mItem);
			break;
		default:
			break;
		}
	}

	OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_lg_zwave_door_lock_lock:
				if(!tvStatus.getText().equals(LockUnlockStatus.LOCK.getName()))
					requestStateUpdate(LockUnlockStatus.LOCK);
				break;
			case R.id.btn_lg_zwave_door_lock_unlock:
				if(!tvStatus.getText().equals(LockUnlockStatus.UNLOCK.getName()))
					requestStateUpdate(LockUnlockStatus.UNLOCK);
				break;
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
