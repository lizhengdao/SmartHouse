package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class FeeHunterRecommendSecondHandHouseActivity extends BaseActivity implements OnClickListener,
OnCheckedChangeListener{

	
	private TextView tvBack;
	
	private RadioButton rbBuy, rbRent;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_recommend_second_hand_house);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_back);
		rbBuy = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_rb_buy);
		rbRent = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_rb_rent);
		
		tvBack.setOnClickListener(this);
		rbBuy.setOnCheckedChangeListener(this);
		rbRent.setOnCheckedChangeListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_recommend_second_hand_house_back:
			finish();
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_fee_hunter_recommend_second_hand_house_rb_buy:  //  求购
				break;
				
			case R.id.act_fee_hunter_recommend_second_hand_house_rb_rent:   //  租求购
				break;
			}
		}
	}
}
