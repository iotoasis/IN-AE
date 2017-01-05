package kr.co.modacom.iot.ltegwdev.sg100;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import kr.co.modacom.iot.ltegwdev.R;
import kr.co.modacom.iot.ltegwdev.control.DMController;
import kr.co.modacom.iot.ltegwdev.model.GatewayVO;
import kr.co.modacom.iot.ltegwdev.model.type.ConnectStatus;

public class LGGatewayFragment extends Fragment {
	private static final String TAG = LGGatewayFragment.class.getSimpleName();

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

	private GatewayVO mGateway;
	private DMController mDmCon;
	
	private Context ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lg_fragment_gateway, container, false);

		etLgGatewayWan = (EditText) view.findViewById(R.id.et_lg_gw_wan_status);
		etLgGatewayMac = (EditText) view.findViewById(R.id.et_lg_gw_mac_addr);
		etLgGatewayDip = (EditText) view.findViewById(R.id.et_lg_gw_dip);
		etLgGatewayPip = (EditText) view.findViewById(R.id.et_lg_gw_pip);
		etLgGatewayFwVer = (EditText) view.findViewById(R.id.et_lg_gw_firmware_version);
		etLgGatewayPairingN = (EditText) view.findViewById(R.id.et_lg_gw_pairing_n);
		etLgGatewayZwaveStatus = (EditText) view.findViewById(R.id.et_lg_gw_zwave_status);
		etLgGatewaySsid = (EditText) view.findViewById(R.id.et_lg_gw_ssid);
		etLgGatewayRssi = (EditText) view.findViewById(R.id.et_lg_gw_rssi);
		etLgGatewayRunningTime = (EditText) view.findViewById(R.id.et_lg_gw_running_time);
		etLgGatewaySessionStatus = (EditText) view.findViewById(R.id.et_lg_gw_session_status);

		if(getActivity() instanceof LGDMActivity) {
			ctx = ((LGDMActivity)getActivity()).getCtx();
		}
		
		mDmCon = DMController.getInstance(ctx);

		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		updateGatewayDisplay();
	}
	

	public GatewayVO getGatewayFromDM() {
		if (mDmCon.getDmItem() != null) {
			if (mDmCon.getDmItem().getGateway() != null)
				return mDmCon.getDmItem().getGateway();
			else
				return null;
		} else
			return null;
	}

	public void updateGatewayDisplay() {
		mGateway = getGatewayFromDM();

		if (mGateway != null) {
			etLgGatewayWan.setText((mGateway.getWan() == null ? "/" : ConnectStatus.valueOf(Integer.parseInt(mGateway.getWan())).getName()));
			etLgGatewayMac.setText((mGateway.getMac() == null ? "/" : mGateway.getMac()));
			etLgGatewayDip.setText((mGateway.getDip() == null ? "/" : mGateway.getDip()));
			etLgGatewayPip.setText((mGateway.getPip() == null ? "/" : mGateway.getPip()));
			etLgGatewayFwVer.setText((mGateway.getFwVer() == null ? "/" : mGateway.getFwVer()));
			etLgGatewayPairingN.setText((mGateway.getPairingN() == null ? "/" : mGateway.getPairingN()));
			
			String zwaveStatus = Integer.parseInt(mGateway.getPairingN()) != 0 ? ConnectStatus.CONNECT.getName() : ConnectStatus.DISCONNECT.getName();
			etLgGatewayZwaveStatus.setText(zwaveStatus);
			
			etLgGatewaySsid.setText((mGateway.getSsid() == null ? "/" : mGateway.getSsid()));
			etLgGatewayRssi.setText((mGateway.getRssi() == null ? "/" : mGateway.getRssi()));
			etLgGatewayRunningTime.setText((mGateway.getRunningTime() == null ? "/" : mGateway.getRunningTime()));
			etLgGatewaySessionStatus.setText((mGateway.getSessionStatus() == null ? "/" : ConnectStatus.valueOf(Integer.parseInt(mGateway.getSessionStatus())).getName()));
		}
	}
}