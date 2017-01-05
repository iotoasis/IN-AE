package kr.co.modacom.iot.ltegwdev.onem2m;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.widget.Toast;
import kr.co.modacom.ae.InAE;
import kr.co.modacom.iot.ltegwdev.model.Const;

public class M2MManager {
	private static final String TAG = M2MManager.class.getSimpleName();
	private static M2MManager m2mManager = null;
	private boolean mIsM2MLibInit;

	private M2MHandler m2mHandler = new M2MHandler();

	private static int resCode;
	private final InAE inAe = new InAE();
	private String preAeId;

	private Context mCtx;

	public interface OnM2MInitilizeListener {
		public void OnInitialize(boolean mIsM2MLibInit);
	}

	public interface OnM2MSendListener {
		/** 메세지를 비동기로 전송하기 이전에 발생하는 Event */
		public void onPreSend();

		/** 메시지를 비동기로 전송완료한 이후 발생하는 Event */
		public void onPostSend();

		/** 메시지 비동기 전송 취소시 발생하는 Event */
		public void onCancelled();
	}

	public interface OnM2MRecvListener {
		public void onRecv(String msg);
	}

	private OnM2MInitilizeListener mOnM2MInitilizeListener;

	private M2MManager() {
		// TODO Auto-generated constructor stub
	}

	public static M2MManager getInstance() {
		if (m2mManager == null)
			m2mManager = new M2MManager();

		return m2mManager;
	}

	public void setOnM2MRecvListener(OnM2MRecvListener listener) {
		m2mHandler.setOnM2MRecvListener(listener);
	}

	public void setOnM2MInitilizeListener(OnM2MInitilizeListener mOnM2MInitilizeListener) {
		this.mOnM2MInitilizeListener = mOnM2MInitilizeListener;
	}

	public void setIsM2MLibInit(boolean mIsM2MLibInit) {
		this.mIsM2MLibInit = mIsM2MLibInit;
	}

	public boolean isIsM2MLibInit() {
		return mIsM2MLibInit;
	}

	public void setContext(Context ctx) {
		mCtx = ctx;
	}

	public void showResultToast(final String msg) {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(mCtx, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void initialize(Context ctx, OnM2MInitilizeListener initListener, OnM2MSendListener onSendListener) {
		setContext(ctx);
		setOnM2MInitilizeListener(initListener);

		M2MTask m2mTask = new M2MTask(onSendListener) {
			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub

				resCode = inAe.initInAe(Const.CSE_ID, Const.CSE_IP, Const.CSE_PORT, Const.IN_AE_ID, null, 30);
				showResultToast("Ae Init Result : " + resCode);
				

				resCode = inAe.addSubscription(Const.SG100_CSE_ID + Const.GW_ID, Const.SG100_AE_ID);
				showResultToast("[IoT Gateway] Subscription Add Result : " + resCode);


				resCode = inAe.recvMessage(m2mHandler);
				showResultToast("Register RecvMessage Result : " + resCode);

				preAeId = Const.SG100_AE_ID;
				mIsM2MLibInit = true;
				return true;
			}
		};
		m2mTask.execute();
	}

	public void sendMessage(final Context ctx, String msg, OnM2MSendListener sendListener,
			final OnM2MRecvListener recvListener, final String cseId) {

		final String encodedMsg = Base64.encodeToString(msg.getBytes(), 0);
		M2MTask m2mTask = new M2MTask(sendListener) {

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				setContext(ctx);
				if (!preAeId.equals(cseId)) {
					if (preAeId.equals(Const.SG100_AE_ID))
						resCode = inAe.removeSubscription(Const.SG100_CSE_ID + Const.GW_ID, preAeId);
					else
						resCode = inAe.removeSubscription(preAeId, Const.SG101_AE_ID);

					if (cseId.equals(Const.SG100_AE_ID)) {
						resCode = inAe.addSubscription(Const.SG100_CSE_ID + Const.GW_ID, cseId);
						showResultToast("[IoT Gateway] Subscription Add Result : " + resCode);
					} else {
						resCode = inAe.addSubscription(cseId, Const.SG101_AE_ID);
						showResultToast("[IoT Device(" + cseId + ")] Subscription Add Result : " + resCode);
					}
					preAeId = cseId;

					m2mHandler = new M2MHandler();
					resCode = inAe.recvMessage(m2mHandler);
				}

				setOnM2MRecvListener(recvListener);

				if (cseId.equals(Const.SG100_AE_ID)) {
					resCode = inAe.sendMessage(Const.SG100_CSE_ID + Const.GW_ID, cseId, encodedMsg);
					showResultToast("[IoT Gateway] Message Send Result : " + resCode);
				} else {
					resCode = inAe.sendMessage(cseId, Const.SG101_AE_ID, encodedMsg);
					showResultToast("[IoT Device(" + cseId + ")] Message Send Result : " + resCode);
				}

				return true;
			}
		};

		m2mTask.execute();
	}

	public void sendMessageForLight(final Context ctx, String msg, OnM2MSendListener sendListener,
			final OnM2MRecvListener recvListener, final String cseId) {

		final String encodedMsg = Base64.encodeToString(msg.getBytes(), 0);
		M2MTask m2mTask = new M2MTask(sendListener) {

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				setContext(ctx);

				resCode = inAe.addSubscription(cseId, Const.SG101_AE_ID);

				resCode = inAe.sendMessage(cseId, Const.SG101_AE_ID, encodedMsg);

				showResultToast("Message Send Result : " + resCode + ", " + cseId);
				return true;
			}
		};
		m2mTask.execute();
	}
}