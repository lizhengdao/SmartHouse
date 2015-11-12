package cn.com.zzwfang.activity;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.NewHouseDetailBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewHouseDetailActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_ESTATE_ID = "intent_estate_id";
	
	private TextView tvBack, tvPageTitle, tvTitle, tvPhotoIndex,
	tvAveragePrice, tvConsultant, tvArea, tvSquare, tvPropertyType,
	tvPlotRatio, tvDecoration, tvGreenRatio, tvMgtCompany,
	tvBuilderCompany;
	private ViewPager viewPager;
	private PhotoPagerAdapter photoPagerAdapter;
    
	
	private String estateId;
	
	private NewHouseDetailBean newHouseDetailBean;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		estateId = getIntent().getStringExtra(INTENT_ESTATE_ID);
		initView();
		getNewHouseDetail(estateId);
	}
	
	private void initView() {
		setContentView(R.layout.act_new_house_detail);
		
		tvBack = (TextView) findViewById(R.id.act_new_house_detail_back);
		tvPageTitle = (TextView) findViewById(R.id.act_new_house_detail_title);
		tvTitle = (TextView) findViewById(R.id.act_new_house_detail_title_tv);
		viewPager = (ViewPager) findViewById(R.id.act_new_house_detail_pager);
		tvPhotoIndex = (TextView) findViewById(R.id.act_new_house_detail_photo_index_tv);
		tvAveragePrice = (TextView) findViewById(R.id.act_new_house_detail_average_price_tv);
		tvArea = (TextView) findViewById(R.id.act_new_house_detail_area_tv);
		tvSquare = (TextView) findViewById(R.id.act_new_house_detail_square_tv);
		tvPropertyType = (TextView) findViewById(R.id.act_new_house_detail_property_type_tv);
		tvPlotRatio = (TextView) findViewById(R.id.act_new_house_detail_plot_ratio_tv);
		tvDecoration = (TextView) findViewById(R.id.act_new_house_detail_decoration_tv);
		tvGreenRatio = (TextView) findViewById(R.id.act_new_house_detail_greening_rate_tv);
		tvMgtCompany = (TextView) findViewById(R.id.act_new_house_detail_mgt_company_tv);
		tvBuilderCompany = (TextView) findViewById(R.id.act_new_house_detail_builder_company_tv);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_new_house_detail_back:
			finish();
			break;
		}
	}
	
	private void rendUI() {
		if (newHouseDetailBean != null) {
			tvPageTitle.setText(newHouseDetailBean.getName());
			tvTitle.setText(newHouseDetailBean.getName());
			tvAveragePrice.setText(newHouseDetailBean.getUnitPrice());
			tvArea.setText(newHouseDetailBean.getFloorArea());
			tvSquare.setText(newHouseDetailBean.getFloorArea());
			tvPropertyType.setText(newHouseDetailBean.getMgtCompany());
			tvPlotRatio.setText(newHouseDetailBean.getPlotRatio());
			tvDecoration.setText(newHouseDetailBean.getDecoration());
			tvGreenRatio.setText(newHouseDetailBean.getGreeningRate());
			tvMgtCompany.setText(newHouseDetailBean.getMgtCompany());
			tvBuilderCompany.setText(newHouseDetailBean.getBuilder());
		}
		
	}
	
	private void getNewHouseDetail(String estateId) {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewHouseDetail(estateId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				newHouseDetailBean = JSON.parseObject(result.getData(), NewHouseDetailBean.class);
				rendUI();
			}
		});
	}
	
	
}
