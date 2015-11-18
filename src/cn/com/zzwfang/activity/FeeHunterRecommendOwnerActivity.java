package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 赏金猎人    推荐业主   页
 * @author lzd
 *
 */
public class FeeHunterRecommendOwnerActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private TextView tvBack;
	
	private RadioButton rbRent, rbSell, rbRentSell;
	
	private EditText edtOwnerName, edtOwnerPhone, tvOwnerEstateName;
	
	private TextView tvWhichBuilding, tvWhichUnit,  tvWhichFloor, tvWhichHouse;
	
	private EditText edtMark;
	
	private TextView tvCommit;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_recommend_owner);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_back);
		rbRent = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_owner_rb_rent);
		rbSell = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_owner_rb_sell);
		rbRentSell = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_owner_rb_rent_sell);
		
		edtOwnerName = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_name);
		edtOwnerPhone = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_phone);
		tvOwnerEstateName = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_estate_name);
		
		tvWhichBuilding = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_building);
		tvWhichUnit = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_unit);
		tvWhichFloor = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_floor);
		tvWhichHouse = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_house);
		
		edtMark = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_mark);
		
		tvCommit = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_commit);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_recommend_owner_back:
			finish();
			break;
			
		case R.id.act_fee_hunter_recommend_owner_commit:   //  提交
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_fee_hunter_recommend_owner_rb_rent:
				break;
			case R.id.act_fee_hunter_recommend_owner_rb_sell:
				break;
			case R.id.act_fee_hunter_recommend_owner_rb_rent_sell:
				break;
			}
		}
		
	}
}
