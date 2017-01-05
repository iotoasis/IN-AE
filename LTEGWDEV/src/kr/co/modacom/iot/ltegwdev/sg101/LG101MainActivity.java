package kr.co.modacom.iot.ltegwdev.sg101;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import kr.co.modacom.iot.ltegwdev.R;

public class LG101MainActivity extends AppCompatActivity {
	private ActionBar actionBar;
	
	private Button btnLg101Ble;
	private Button btnLg101Light;
	private Button btnLg101DM;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_101_acivity_main);

		actionBar = getSupportActionBar();
		actionBar.hide();
		
		btnLg101Ble = (Button) findViewById(R.id.btn_lg_101_ble);
		btnLg101Light = (Button) findViewById(R.id.btn_lg_101_light);
		btnLg101DM = (Button) findViewById(R.id.btn_lg_101_dm);
		
		btnLg101Ble.setOnClickListener(mOnClickListener);
		btnLg101Light.setOnClickListener(mOnClickListener);
		btnLg101DM.setOnClickListener(mOnClickListener);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub 
			switch (v.getId()) {
			case R.id.btn_lg_101_ble:
				intent = new Intent(LG101MainActivity.this, LGBLEActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_lg_101_light:
				intent = new Intent(LG101MainActivity.this, LGLightActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_lg_101_dm:
				intent = new Intent(LG101MainActivity.this, LG101DMActivity.class);
				startActivity(intent);
				break;
				
			}
		}
	};
}
