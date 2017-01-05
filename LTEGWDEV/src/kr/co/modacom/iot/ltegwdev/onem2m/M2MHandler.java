package kr.co.modacom.iot.ltegwdev.onem2m;

import android.util.Base64;
import kr.co.modacom.callback.LongPollingCb;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MRecvListener;

public class M2MHandler implements LongPollingCb {
	private static final String TAG = M2MHandler.class.getSimpleName();

	private OnM2MRecvListener mOnM2MRecvListener;

	public M2MHandler() {
		// TODO Auto-generated constructor stub
	}

	public void setOnM2MRecvListener(OnM2MRecvListener listener) {
		this.mOnM2MRecvListener = listener;
	}

	@Override
	public void oneM2MRecvMessage(String msg) {
		// TODO Auto-generated method stub
		if(msg != null) {
			String decodedMsg = new String(Base64.decode(msg, 0));
	
			if (mOnM2MRecvListener != null) {
				mOnM2MRecvListener.onRecv(decodedMsg);
			}
		}
	}
}