package kr.co.modacom.iot.ltegwdev.sg100;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.control.ZWaveController;
import kr.co.modacom.iot.ltegwdev.model.PropertyVO;
import kr.co.modacom.iot.ltegwdev.model.ZwaveDeviceVO;
import kr.co.modacom.iot.ltegwdev.model.type.DevCat;
import kr.co.modacom.iot.ltegwdev.model.type.DevLabel;
import kr.co.modacom.iot.ltegwdev.model.type.DevModel;
import kr.co.modacom.iot.ltegwdev.model.type.FunctionId;
import kr.co.modacom.iot.ltegwdev.model.type.ModelInfo;
import kr.co.modacom.iot.ltegwdev.model.type.OnOffStatus;
import kr.co.modacom.iot.ltegwdev.model.type.Target;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;

public class LGZwaveWallSwitchFragment extends Fragment {

	private static final String TAG = LGZwaveWallSwitchFragment.class.getSimpleName();

	private TextView tvInstanceId1;
	private TextView tvStatus1;
	private Button btnLGWallSwitchOn1;
	private Button btnLGWallSwitchOff1;

	private TextView tvInstanceId2;
	private TextView tvStatus2;
	private Button btnLGWallSwitchOn2;
	private Button btnLGWallSwitchOff2;

	private TextView tvInstanceId3;
	private TextView tvStatus3;
	private Button btnLGWallSwitchOn3;
	private Button btnLGWallSwitchOff3;
	private LinearLayout layout3;

	private TextView tvInstanceId4;
	private TextView tvStatus4;
	private Button btnLGWallSwitchOn4;
	private Button btnLGWallSwitchOff4;
	private LinearLayout layout4;

	private ArrayList<TextView> instanceList;
	private ArrayList<TextView> statusList;
	private ArrayList<Button> onList;
	private ArrayList<Button> offList;

	private ZWaveController mZWaveCon;
	private ZwaveDeviceVO mItem;

	private Context mCtx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lg_fragment_zw_wall_switch, container, false);

		if (getActivity() instanceof LGZwaveActivity) {
			mCtx = ((LGZwaveActivity) getActivity()).getContext();
		}

		mZWaveCon = ZWaveController.getInstance(mCtx);
		tvInstanceId1 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_instance_id_1);
		tvStatus1 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_status_1);
		btnLGWallSwitchOn1 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_on_1);
		btnLGWallSwitchOff1 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_off_1);

		tvInstanceId2 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_instance_id_2);
		tvStatus2 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_status_2);
		btnLGWallSwitchOn2 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_on_2);
		btnLGWallSwitchOff2 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_off_2);

		tvInstanceId3 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_instance_id_3);
		tvStatus3 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_status_3);
		btnLGWallSwitchOn3 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_on_3);
		btnLGWallSwitchOff3 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_off_3);
		layout3 = (LinearLayout) view.findViewById(R.id.layout_lg_zwave_switch_3);

		tvInstanceId4 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_instance_id_4);
		tvStatus4 = (TextView) view.findViewById(R.id.tv_lg_zwave_switch_status_4);
		btnLGWallSwitchOn4 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_on_4);
		btnLGWallSwitchOff4 = (Button) view.findViewById(R.id.btn_lg_zwave_switch_off_4);
		layout4 = (LinearLayout) view.findViewById(R.id.layout_lg_zwave_switch_4);
		layout4.setVisibility(View.GONE);

		btnLGWallSwitchOn1.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOff1.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOn2.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOff2.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOn3.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOff3.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOn4.setOnClickListener(mOnClickListener);
		btnLGWallSwitchOff4.setOnClickListener(mOnClickListener);

		instanceList = new ArrayList<TextView>();
		instanceList.add(tvInstanceId1);
		instanceList.add(tvInstanceId2);
		instanceList.add(tvInstanceId3);
		instanceList.add(tvInstanceId4);

		statusList = new ArrayList<TextView>();
		statusList.add(tvStatus1);
		statusList.add(tvStatus2);
		statusList.add(tvStatus3);
		statusList.add(tvStatus4);

		onList = new ArrayList<Button>();
		onList.add(btnLGWallSwitchOn1);
		onList.add(btnLGWallSwitchOn2);
		onList.add(btnLGWallSwitchOn3);
		onList.add(btnLGWallSwitchOn4);

		offList = new ArrayList<Button>();
		offList.add(btnLGWallSwitchOff1);
		offList.add(btnLGWallSwitchOff2);
		offList.add(btnLGWallSwitchOff3);
		offList.add(btnLGWallSwitchOff4);

		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// requestDeviceInfo();
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
			} else if (item.getDevCat() == DevCat.ZWAVE.getCode() && item.getModelName().equals(DevModel.WALL_SWITCH.getName())
					&& item.getPairingFlag() == flag) {
				return item;
			}
		}
		return null;
	}

	public ZwaveDeviceVO getItemFromList(String deviceId) {
		for (ZwaveDeviceVO item : mZWaveCon.getItems())
			if (item.getDeviceId().equals(deviceId))
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
			int cnt = 0;

			if (item.getModelName().contains("Unknown")) {
				return;
			}
			
			if(mItem.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_WALL_SWITCH_2.getCode())) {
				layout3.setVisibility(View.GONE);
			} else if (mItem.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_WALL_SWITCH.getCode())) {
				layout3.setVisibility(View.VISIBLE);
			}

			for (int i = 0; i < item.getProperties().size(); i++) {
				PropertyVO property = item.getProperties().get(i);
				if(property.getInstanceId() != 1) {
					if (property.getLabel().equals(DevLabel.SWITCH.getName())) {
						if (instanceList.get(cnt) != null && statusList.get(cnt) != null) {
							int status = -1;
							status = (Integer) property.getValue();
	
							instanceList.get(cnt).setText(Integer.toString(property.getInstanceId()));
							statusList.get(cnt).setText(status == -1 ? ""
									: (status == 0 ? OnOffStatus.OFF.getName() : OnOffStatus.ON.getName()));
							cnt++;
						}
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
		
		for(TextView tvStatus : statusList) {
			tvStatus.setText("");
		}
		
		for(TextView tvInstanceId : instanceList) {
			tvInstanceId.setText("");
		}
		
		layout3.setVisibility(View.VISIBLE);

	}

	public void requestStateUpdate(OnOffStatus value, int index) {
		if (mItem != null) {
			for (PropertyVO property : mItem.getProperties()) {
				if (property.getLabel().equals(DevLabel.SWITCH.getName())
						&& property.getInstanceId() == Integer.parseInt(instanceList.get(index).getText().toString())) {
					mItem.setDeviceId(mItem.getDeviceId().substring(0, mItem.getDeviceId().length() - 1)
							+ property.getInstanceId());
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

	public void clearModelInfo() {
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
			case R.id.btn_lg_zwave_switch_on_1:
				if (!statusList.get(0).getText().equals(OnOffStatus.ON.getName()))
					requestStateUpdate(OnOffStatus.ON, 0);
				break;
			case R.id.btn_lg_zwave_switch_off_1:
				if (!statusList.get(0).getText().equals(OnOffStatus.OFF.getName()))
					requestStateUpdate(OnOffStatus.OFF, 0);
				break;
			case R.id.btn_lg_zwave_switch_on_2:
				if (!statusList.get(1).getText().equals(OnOffStatus.ON.getName()))
					requestStateUpdate(OnOffStatus.ON, 1);
				break;
			case R.id.btn_lg_zwave_switch_off_2:
				if (!statusList.get(1).getText().equals(OnOffStatus.OFF.getName()))
					requestStateUpdate(OnOffStatus.OFF, 1);
				break;
			case R.id.btn_lg_zwave_switch_on_3:
				if (!statusList.get(2).getText().equals(OnOffStatus.ON.getName()))
					requestStateUpdate(OnOffStatus.ON, 2);
				break;
			case R.id.btn_lg_zwave_switch_off_3:
				if (!statusList.get(2).getText().equals(OnOffStatus.OFF.getName()))
					requestStateUpdate(OnOffStatus.OFF, 2);
				break;
			case R.id.btn_lg_zwave_switch_on_4:
				if (!statusList.get(3).getText().equals(OnOffStatus.ON.getName()))
					requestStateUpdate(OnOffStatus.ON, 3);
				break;
			case R.id.btn_lg_zwave_switch_off_4:
				if (!statusList.get(3).getText().equals(OnOffStatus.OFF.getName()))
					requestStateUpdate(OnOffStatus.OFF, 3);
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