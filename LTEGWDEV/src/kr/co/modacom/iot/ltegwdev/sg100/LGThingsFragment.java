package kr.co.modacom.iot.ltegwdev.sg100;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.control.DMController;
import kr.co.modacom.iot.ltegwdev.model.ThingsVO;
import kr.co.modacom.iot.ltegwdev.model.type.LockUnlockStatus;
import kr.co.modacom.iot.ltegwdev.model.type.ModelInfo;
import kr.co.modacom.iot.ltegwdev.model.type.OnOffStatus;
import kr.co.modacom.iot.ltegwdev.model.type.OpenCloseStatus;

public class LGThingsFragment extends Fragment {
	private static final String TAG = LGThingsFragment.class.getSimpleName();

	private Spinner spinThings;

	private EditText etLgThingsMac;
	private EditText etLgThingsFwVer;
	private EditText etLgThingsRssi;
	private EditText etLgThingsRunningTime;
	private EditText etLgThingsStatus1;
	private EditText etLgThingsStatus2;
	private EditText etLgThingsStatus3;
	private EditText etLgThingsStatus4;
	private EditText etLgThingsBattery;
	private EditText etLgThingsTemperature;

	private LinearLayout layoutLgThingsStatus2;
	private LinearLayout layoutLgThingsStatus3;
	private LinearLayout layoutLgThingsStatus4;

	private DMController mDmCon;
	private ArrayList<ThingsVO> mThings;

	private Context ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lg_fragment_things, container, false);

		etLgThingsMac = (EditText) view.findViewById(R.id.et_lg_things_dev_mac_addr);
		etLgThingsFwVer = (EditText) view.findViewById(R.id.et_lg_things_dev_firmware_version);
		etLgThingsRssi = (EditText) view.findViewById(R.id.et_lg_things_dev_rssi);
		etLgThingsRunningTime = (EditText) view.findViewById(R.id.et_lg_things_dev_running_time);
		etLgThingsStatus1 = (EditText) view.findViewById(R.id.et_lg_things_dev_status_1);
		etLgThingsStatus2 = (EditText) view.findViewById(R.id.et_lg_things_dev_status_2);
		etLgThingsStatus3 = (EditText) view.findViewById(R.id.et_lg_things_dev_status_3);
		etLgThingsStatus4 = (EditText) view.findViewById(R.id.et_lg_things_dev_status_4);
		etLgThingsBattery = (EditText) view.findViewById(R.id.et_lg_things_dev_battery);
		etLgThingsTemperature = (EditText) view.findViewById(R.id.et_lg_things_dev_temp);

		layoutLgThingsStatus2 = (LinearLayout) view.findViewById(R.id.layout_lg_things_status_2);
		layoutLgThingsStatus3 = (LinearLayout) view.findViewById(R.id.layout_lg_things_status_3);
		layoutLgThingsStatus4 = (LinearLayout) view.findViewById(R.id.layout_lg_things_status_4);

		layoutLgThingsStatus2.setVisibility(View.GONE);
		layoutLgThingsStatus3.setVisibility(View.GONE);
		layoutLgThingsStatus4.setVisibility(View.GONE);

		spinThings = (Spinner) view.findViewById(R.id.spin_lg_things_device);

		if (getActivity() instanceof LGDMActivity) {
			ctx = ((LGDMActivity) getActivity()).getCtx();
		}

		mDmCon = DMController.getInstance(ctx);

		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mThings = getThingsFromDM();

		if (mThings != null) {
			ArrayList<String> thingsName = new ArrayList<String>();

			for (ThingsVO things : mThings) {
				try {
					thingsName.add(ModelInfo.getModelInfo(things.getModelInfo()).getName());
				} catch (Exception e) {
				}
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
					thingsName);
			spinThings.setAdapter(adapter);
			spinThings.setOnItemSelectedListener(mOnItemSelectedListener);

			if (thingsName.size() == 0) {
				Toast.makeText(getActivity(), "Z-wave 장치가 없습니다.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getActivity(), "Z-wave 장치가 없습니다.", Toast.LENGTH_SHORT).show();
		}
	}

	public ArrayList<ThingsVO> getThingsFromDM() {
		if (mDmCon.getDmItem() != null) {
			if (mDmCon.getDmItem().getThings() != null)
				return mDmCon.getDmItem().getThings();
			else
				return null;
		} else
			return null;
	}

	public void updateThingsDisplay(int index) {
		if (mThings != null && mThings.size() >= index) {
			ThingsVO things = mThings.get(index);

			if (things != null) {
				etLgThingsMac.setText("/");
				etLgThingsFwVer.setText(things.getFwVer());
				etLgThingsRssi.setText("/");
				etLgThingsRunningTime.setText("/");
				etLgThingsBattery.setText(things.getBattery());
				etLgThingsTemperature.setText("/");

				if (things.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_WALL_SWITCH.getCode())) {
					layoutLgThingsStatus2.setVisibility(View.VISIBLE);
					layoutLgThingsStatus3.setVisibility(View.VISIBLE);
					layoutLgThingsStatus4.setVisibility(View.VISIBLE);

					String status1 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(0, 1)))
							.getName();
					String status2 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(1, 2)))
							.getName();
					String status3 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(2, 3)))
							.getName();
					String status4 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(3, 4)))
							.getName();

					etLgThingsStatus1.setText(status1);
					etLgThingsStatus2.setText(status2);
					etLgThingsStatus3.setText(status3);
					etLgThingsStatus4.setText(status4);
				} else if (things.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_WALL_SWITCH_2.getCode())) {
					layoutLgThingsStatus2.setVisibility(View.VISIBLE);
					layoutLgThingsStatus3.setVisibility(View.VISIBLE);

					String status1 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(0, 1)))
							.getName();
					String status2 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(1, 2)))
							.getName();
					String status3 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus().substring(2, 3)))
							.getName();

					etLgThingsStatus1.setText(status1);
					etLgThingsStatus2.setText(status2);
					etLgThingsStatus3.setText(status3);

				} else {
					layoutLgThingsStatus2.setVisibility(View.GONE);
					layoutLgThingsStatus3.setVisibility(View.GONE);
					layoutLgThingsStatus4.setVisibility(View.GONE);

					String status1 = "";

					if (things.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_PLUG.getCode()))
						status1 = OnOffStatus.valueOf(Integer.parseInt(things.getStatus())).getName();
					else if (things.getModelInfo().equals(ModelInfo.SAMSUNG_DOOR_LOCK.getCode()))
						status1 = LockUnlockStatus.valueOf(Integer.parseInt(things.getStatus())).getName();
					else if (things.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_ACCESS_CONTROL.getCode()))
						status1 = OpenCloseStatus.valueOf(Integer.parseInt(things.getStatus())).getName();
					else if (things.getModelInfo().equals(ModelInfo.LG_UPLUS_IOT_GAS_LOCK.getCode()))
						status1 = OpenCloseStatus.valueOf(Integer.parseInt(things.getStatus())).getName();

					etLgThingsStatus1.setText(status1);
				}
			}
		}
	}

	private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			updateThingsDisplay(arg2);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
}
