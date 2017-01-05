package kr.co.modacom.iot.ltegwdev.sg100;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import kr.co.modacom.iot.ltegwdev.R;

public class LGMainActivity extends AppCompatActivity {

	private ActionBar actionBar;
	
	private Button btnMd;
	private Button btnZwave;
	
	private Intent mIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_activity_main);

		actionBar = getSupportActionBar();
		actionBar.hide();

		btnMd = (Button)findViewById(R.id.btn_lg_main_md);
		btnZwave = (Button)findViewById(R.id.btn_lg_main_zwave);
		
		btnMd.setOnClickListener(mOnClickListener);
		btnZwave.setOnClickListener(mOnClickListener);
	}
	
	OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()) {
			case R.id.btn_lg_main_md:
				mIntent = new Intent(LGMainActivity.this, LGDMActivity.class);
				startActivity(mIntent);
				break;
			case R.id.btn_lg_main_zwave:
				mIntent = new Intent(LGMainActivity.this, LGZwaveActivity.class);
				startActivity(mIntent);
				break;
			}
		}
	};
}