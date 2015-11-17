package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.NewHouseDetailBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.PathImage;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewHouseDetailActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_ESTATE_ID = "intent_estate_id";
	
	private TextView tvBack, tvPageTitle, tvTitle, tvPhotoIndex,
	tvAveragePrice, tvConsultant, tvArea, tvSquare, tvPropertyType,
	tvPlotRatio, tvDecoration, tvGreenRatio, tvMgtCompany,
	tvBuilderCompany, tvMortgageCalculate;
	
	private PathImage brokerAvatar;
	private TextView tvBrokerName, tvBrokerPhone;
	
	private ViewPager viewPager;
	private PhotoPagerAdapter photoPagerAdapter;
    
	private AutoDrawableTextView tvDial, tvMsg;
	
	private String estateId;
	
	private NewHouseDetailBean newHouseDetailBean;
	
	private ViewPager photoPager;
    private PhotoPagerAdapter photoAdapter;
    private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();
	
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
		tvDial = (AutoDrawableTextView) findViewById(R.id.act_new_house_detail_agent_dial);
		tvMsg = (AutoDrawableTextView) findViewById(R.id.act_new_house_detail_agent_msg);
		brokerAvatar = (PathImage) findViewById(R.id.act_new_house_detail_agent_avatar);
		tvBrokerName = (TextView) findViewById(R.id.act_new_house_detail_agent_name);
		tvBrokerPhone = (TextView) findViewById(R.id.act_new_house_detail_agent_phone);
		tvMortgageCalculate = (TextView) findViewById(R.id.act_new_house_detail_calculator_tv);
		
		photoPager = (ViewPager) findViewById(R.id.act_new_house_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
        photoPager.setAdapter(photoAdapter);
		
		tvBack.setOnClickListener(this);
		tvDial.setOnClickListener(this);
		tvMsg.setOnClickListener(this);
		tvMortgageCalculate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_new_house_detail_back:
			finish();
			break;
		case R.id.act_new_house_detail_agent_dial:  // 打经纪人电话
			if (newHouseDetailBean != null) {
				AgentBean agent = newHouseDetailBean.getAgent();
				String phone = agent.getTel();
				if (!TextUtils.isEmpty(phone)) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phone));
					startActivity(intent);
				}
			}
			break;
		case R.id.act_new_house_detail_agent_msg:   // 经纪人发消息
			break;
			
		case R.id.act_new_house_detail_calculator_tv:   //  房贷计算器
		    Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jump(this, MortgageCalculatorActivity.class);
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
			
			AgentBean agent = newHouseDetailBean.getAgent();
			if (agent != null) {
				tvBrokerName.setText(agent.getName());
				tvBrokerPhone.setText(agent.getTel());
				String url = agent.getPhoto();
				if (!TextUtils.isEmpty(url)) {
					ImageAction.displayBrokerAvatar(url, brokerAvatar);
				}
			}
			
			photos.addAll(newHouseDetailBean.getPhoto());
            photoAdapter.notifyDataSetChanged();
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