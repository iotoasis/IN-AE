package kr.co.modacom.iot.ltegwdev;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import kr.co.modacom.iot.ltegwdev.R;

public class SettingDeviceListAdapter extends BaseAdapter {

	ArrayList<SettingDeviceListVO> datas;
	Button btnDeviceRemove;
	TextView tvDevice;
	TextView tvDeviceId;
	LayoutInflater inflater;

	public SettingDeviceListAdapter(LayoutInflater inflater, ArrayList<SettingDeviceListVO> datas) {
		// TODO Auto-generated constructor stub
		this.datas = datas;
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (SettingDeviceListVO)datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lg_listview_setting, null);
		}
		tvDevice = (TextView) convertView.findViewById(R.id.tv_setting_device);
		tvDevice.setText("LTE Device MN-CSE ID " + (position + 1) + " : ");
		
		tvDeviceId = (TextView) convertView.findViewById(R.id.tv_setting_device_serial_number);
		tvDeviceId.setText(datas.get(position).getDeviceId());
		 
		btnDeviceRemove = (Button) convertView.findViewById(R.id.btn_setting_device_remove);
		final int positionInt = position;
		btnDeviceRemove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datas.remove(positionInt);
				SettingActivity.setListViewHeightBasedOnItems();
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
	
	public ArrayList<SettingDeviceListVO> getDatas() {
		return datas;
	}

}
