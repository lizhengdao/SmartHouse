package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.RentHouseDetailBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;

import com.alibaba.fastjson.JSON;

public class RentHouseDetailActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	private String houseSourceId = null;
	private RentHouseDetailBean rentHouseDetailBean;
	
	private TextView tvBack, tvPageTitle, tvShare, tvTitle,
	tvRentPrice, tvHouseType, tvSquare;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		getRentHouseDetail();
	}
	
	private void initView() {
		setContentView(R.layout.act_rent_house_detail);
		tvBack = (TextView) findViewById(R.id.act_rent_house_detail_back);
		tvPageTitle = (TextView) findViewById(R.id.act_rent_house_detail_page_title);
		tvShare = (TextView) findViewById(R.id.act_rent_house_detail_share);
		tvTitle = (TextView) findViewById(R.id.act_rent_house_detail_title);
		tvRentPrice = (TextView) findViewById(R.id.act_rent_house_detail_rent_price);
		tvHouseType = (TextView) findViewById(R.id.act_rent_house_detail_house_type);
		tvSquare = (TextView) findViewById(R.id.act_rent_house_detail_square);
		
		tvBack.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_rent_house_detail_back:
			finish();
			break;
		}
	}
	
	private void rendUI() {
		if (rentHouseDetailBean != null) {
			tvPageTitle.setText(rentHouseDetailBean.getTitle());
			tvTitle.setText(rentHouseDetailBean.getTitle());
			tvRentPrice.setText(rentHouseDetailBean.getRentPrice() + "元/月");
			tvHouseType.setText(rentHouseDetailBean.getFloor() + "室" + rentHouseDetailBean.getTypeT() + "厅");
			tvSquare.setText(rentHouseDetailBean.getSquare() + "㎡");
		}
	}
	
	private void getRentHouseDetail() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getRentHouseDetail(houseSourceId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				rentHouseDetailBean = JSON.parseObject(result.getData(), RentHouseDetailBean.class);
				rendUI();
			}
		});
	}

	
}
