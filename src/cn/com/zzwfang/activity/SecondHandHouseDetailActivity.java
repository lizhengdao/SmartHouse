package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SecondHandHouseDetailBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.view.PathImage;

import com.alibaba.fastjson.JSON;

/**
 * 二手房详情
 * @author lzd
 *
 */
public class SecondHandHouseDetailActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	private TextView tvBack, tvTitle, tvShare, tvDetailtitle;
	private TextView tvTotalPrice, tvHouseType, tvSquare, tvLabel, tvUnitPrice;
	private TextView tvPartialPrice, tvMonthlyPay, tvFloor, tvDirection,
	tvDecoration, tvEstateName, tvAgentName, tvAgentPhone, tvAgentDial, tvAgentMsg;
	
	private PathImage agentAvatar;
	private String houseSourceId = null;
	
	private SecondHandHouseDetailBean secondHandHouseDetail;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		getSecondHandHouseDetail();
	}
	
	private void initView() {
		setContentView(R.layout.act_second_hand_house_detail);
		
		tvBack = (TextView) findViewById(R.id.act_second_hand_house_detail_back);
		tvTitle = (TextView) findViewById(R.id.act_second_hand_house_detail_title);
		tvShare = (TextView) findViewById(R.id.act_second_hand_house_detail_share);
		tvDetailtitle = (TextView) findViewById(R.id.act_second_house_detail_title);
		tvTotalPrice = (TextView) findViewById(R.id.act_second_house_detail_total_price);
		tvHouseType = (TextView) findViewById(R.id.act_second_house_detail_house_type);
		tvSquare = (TextView) findViewById(R.id.act_second_house_detail_square);
		tvLabel = (TextView) findViewById(R.id.act_second_house_detail_label);
		tvUnitPrice = (TextView) findViewById(R.id.act_second_hand_house_detail_unit_price_tv);
		tvPartialPrice = (TextView) findViewById(R.id.act_second_hand_house_detail_partialprice_tv);
		tvMonthlyPay = (TextView) findViewById(R.id.act_second_hand_house_detail_monthly_pay_tv);
		tvFloor = (TextView) findViewById(R.id.act_second_hand_house_detail_floor_tv);
		tvDirection = (TextView) findViewById(R.id.act_second_hand_house_detail_direction_tv);
		tvDecoration = (TextView) findViewById(R.id.act_second_hand_house_detail_decoration_tv);
		tvEstateName = (TextView) findViewById(R.id.act_second_hand_house_detail_estatename_tv);
		agentAvatar = (PathImage) findViewById(R.id.act_second_hand_house_detail_agent_avatar);
		tvAgentName = (TextView) findViewById(R.id.act_second_hand_house_detail_agent_name);
		tvAgentPhone = (TextView) findViewById(R.id.act_second_hand_house_detail_agent_phone);
		tvAgentDial = (TextView) findViewById(R.id.act_second_hand_house_detail_agent_dial);
		tvAgentMsg = (TextView) findViewById(R.id.act_second_hand_house_detail_agent_msg);
	
		tvBack.setOnClickListener(this);
		tvShare.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact_back:
			finish();
			break;
		case R.id.act_second_hand_house_detail_title:  // 详情title
			break;
		case R.id.act_second_hand_house_detail_share:   //  分享
			break;
		}
	}
	
	private void rendUI() {
		if (secondHandHouseDetail != null) {
			tvTitle.setText(secondHandHouseDetail.getTitle());
			tvDetailtitle.setText(secondHandHouseDetail.getTitle());
			tvTotalPrice.setText(secondHandHouseDetail.getPrice() + "万");
			String houseType = secondHandHouseDetail.getTypeF() + "房" + secondHandHouseDetail.getTypeT() + "室";
			tvHouseType.setText(houseType);
			tvSquare.setText(secondHandHouseDetail.getSquare() + "㎡");
			ArrayList<String> labels = secondHandHouseDetail.getLabel();
			StringBuilder label = new StringBuilder();
			for (String temp : labels) {
				label.append(temp + "  ");
			}
			tvLabel.setText(label.toString());
			tvUnitPrice.setText(secondHandHouseDetail.getUnitPrice() + "元");
			tvPartialPrice.setText(secondHandHouseDetail.getPartialPrice());
			tvMonthlyPay.setText(secondHandHouseDetail.getMonthlyPayment() + "元");
			tvFloor.setText(secondHandHouseDetail.getFloor() + "/" + secondHandHouseDetail.getTotalFloor() + "层");
			tvDirection.setText(secondHandHouseDetail.getDirection());
			tvDecoration.setText(secondHandHouseDetail.getDecoration());
			tvEstateName.setText(secondHandHouseDetail.getEstateName());
			
			AgentBean agentBean = secondHandHouseDetail.getAgent();
			if (agentBean != null) {
				tvAgentName.setText(agentBean.getName());
				tvAgentPhone.setText(agentBean.getTel());
				ImageAction.displayAvatar(agentBean.getPhoto(), agentAvatar);
			}
			
			
		}
	}
	
	private void getSecondHandHouseDetail() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSecondHandHouseDetail(houseSourceId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				secondHandHouseDetail = JSON.parseObject(result.getData(), SecondHandHouseDetailBean.class);
				rendUI();
			}
		});
	}
}
