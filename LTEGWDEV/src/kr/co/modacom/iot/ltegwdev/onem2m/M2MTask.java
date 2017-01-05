package kr.co.modacom.iot.ltegwdev.onem2m;

import android.os.AsyncTask;
import kr.co.modacom.iot.ltegwdev.onem2m.M2MManager.OnM2MSendListener;

public class M2MTask extends AsyncTask<Void, Void, Boolean>{

	private OnM2MSendListener mOnMcaListener;
	
	public M2MTask(OnM2MSendListener listener){
		this.mOnMcaListener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if( mOnMcaListener != null )
			mOnMcaListener.onPreSend();
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		if( mOnMcaListener != null )
			mOnMcaListener.onPostSend();
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		
		if( mOnMcaListener != null )
			mOnMcaListener.onCancelled();
	}
}
