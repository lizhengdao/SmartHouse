package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseTypeSelectListener;

public class SearchHouseActivity extends BaseActivity implements OnClickListener, OnHouseTypeSelectListener {

	private TextView tvCancel, tvHouseType;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_search_house);
		
		tvCancel = (TextView) findViewById(R.id.act_search_house_cancel_tv);
		tvHouseType = (TextView) findViewById(R.id.act_search_house_house_type_tv);
		
		tvCancel.setOnClickListener(this);
		tvHouseType.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.act_search_house_cancel_tv:
				finish();
				break;
			case R.id.act_search_house_house_type_tv:
				PopViewHelper.showSearchHouseTypePopWindow(this, tvHouseType, this);
				break;
		}
	}

	@Override
	public void onHouseTypeSelect(int houseType, String houseTypeTxt) {
		tvHouseType.setText(houseTypeTxt);
	}
}
