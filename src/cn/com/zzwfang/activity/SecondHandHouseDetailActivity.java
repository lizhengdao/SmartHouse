package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SecondHandHouseDetailBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.PathImage;
import cn.com.zzwfang.view.helper.PopViewHelper;

import com.alibaba.fastjson.JSON;

/**
 * 二手房详情
 * 
 * @author lzd
 * 
 */
public class SecondHandHouseDetailActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {

	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	private TextView tvBack, tvTitle, tvShare, tvDetailtitle;
	private TextView tvTotalPrice, tvHouseType, tvSquare, tvLabel, tvUnitPrice;
	private TextView tvPartialPrice, tvMonthlyPay, tvFloor, tvDirection,
			tvDecoration, tvEstateName, tvAgentName, tvAgentPhone,
			tvPhotoIndex, tvSeeHouseRecord, tvAttention, tvNearbyDetail,
			tvSandTableDisplay, tvTransactionHistory;

	private AutoDrawableTextView tvAgentDial, tvAgentMsg;

	private TextView tvMortgageCalculate;

	private ViewPager photoPager;
	private PhotoPagerAdapter photoAdapter;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();

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
		tvAgentDial = (AutoDrawableTextView) findViewById(R.id.act_second_hand_house_detail_agent_dial);
		tvAgentMsg = (AutoDrawableTextView) findViewById(R.id.act_second_hand_house_detail_agent_msg);
		tvPhotoIndex = (TextView) findViewById(R.id.act_second_house_detail_photo_index_tv);
		tvSeeHouseRecord = (TextView) findViewById(R.id.act_second_handhouse_see_house_record_tv);
		tvMortgageCalculate = (TextView) findViewById(R.id.act_second_hand_house_detail_calculator_tv);
		tvAttention = (TextView) findViewById(R.id.act_second_handhouse_attenton_tv);
		tvNearbyDetail = (TextView) findViewById(R.id.act_second_handhouse_nearby_detail_tv);
		tvSandTableDisplay = (TextView) findViewById(R.id.act_second_hand_house_detail_sand_table_display);
		tvTransactionHistory = (TextView) findViewById(R.id.act_second_handhouse_transaction_history_tv);

		photoPager = (ViewPager) findViewById(R.id.act_second_house_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
		photoPager.setAdapter(photoAdapter);

		tvBack.setOnClickListener(this);
		tvShare.setOnClickListener(this);
		tvSeeHouseRecord.setOnClickListener(this);
		photoPager.setOnPageChangeListener(this);
		tvMortgageCalculate.setOnClickListener(this);
		tvAttention.setOnClickListener(this);
		tvNearbyDetail.setOnClickListener(this);
		tvSandTableDisplay.setOnClickListener(this);
		tvTransactionHistory.setOnClickListener(this);
		tvAgentDial.setOnClickListener(this);
		tvAgentMsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_second_hand_house_detail_back:
			finish();
			break;
		case R.id.act_second_hand_house_detail_title: // 详情title
			break;
		case R.id.act_second_hand_house_detail_share: // 分享
			PopViewHelper.showSharePopupWindow(this, getWindow().getDecorView());
			break;
		case R.id.act_second_handhouse_see_house_record_tv: // 看房记录

			Jumper.newJumper()
					.setAheadInAnimation(R.anim.activity_push_in_right)
					.setAheadOutAnimation(R.anim.activity_alpha_out)
					.setBackInAnimation(R.anim.activity_alpha_in)
					.setBackOutAnimation(R.anim.activity_push_out_right)
					.putSerializable(SeeHouseRecordActivity.INTENT_RECORD,
							secondHandHouseDetail.getInqFollowList())
					.jump(this, SeeHouseRecordActivity.class);
			break;

		case R.id.act_second_hand_house_detail_calculator_tv: // 房贷计算器
			Jumper.newJumper()
					.setAheadInAnimation(R.anim.activity_push_in_right)
					.setAheadOutAnimation(R.anim.activity_alpha_out)
					.setBackInAnimation(R.anim.activity_alpha_in)
					.setBackOutAnimation(R.anim.activity_push_out_right)
					.jump(this, MortgageCalculatorActivity.class);
			break;
		case R.id.act_second_hand_house_detail_agent_dial: // 拨打经纪人电话
			if (secondHandHouseDetail != null) {
				AgentBean agent = secondHandHouseDetail.getAgent();
				String phone = agent.getTel();
				if (!TextUtils.isEmpty(phone)) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phone));
					startActivity(intent);
				}
			}
			break;
		case R.id.act_second_hand_house_detail_agent_msg: // 给经纪人发消息
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, ChatActivity.class);
			break;

		case R.id.act_second_handhouse_attenton_tv: // 关注
			attentionToHouse();
			break;
		case R.id.act_second_handhouse_nearby_detail_tv:  //  周边详情
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, NearbyDetailActivity.class);
			break;
		case R.id.act_second_hand_house_detail_sand_table_display:  // 沙盘展示
			if (secondHandHouseDetail != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putString(SandTableDisplayActivity.INTENT_SAND_TABLE_DISPLAY_URL, secondHandHouseDetail.getEstate360())
				.putString(SandTableDisplayActivity.INTENT_ESTATE_NAME, secondHandHouseDetail.getEstateName())
				.jump(this, SandTableDisplayActivity.class);
			}
			break;
			
		case R.id.act_second_handhouse_transaction_history_tv:   //  小区成交记录
			if (secondHandHouseDetail != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putString(ResidentialTransactionHistoryActivity.INTENT_ESTATE_ID, secondHandHouseDetail.getEstateId())
				.putString(ResidentialTransactionHistoryActivity.INTENT_ESTATE_NAME, secondHandHouseDetail.getEstateName())
				.jump(this, ResidentialTransactionHistoryActivity.class);
			}
			break;
		}
	}

	private void rendUI() {
		if (secondHandHouseDetail != null) {
			photos.addAll(secondHandHouseDetail.getPhoto());
			photoAdapter.notifyDataSetChanged();
			int photoTotalNum = photoAdapter.getCount();
			String txt = photoTotalNum + "/1";
			tvPhotoIndex.setText(txt);

			tvTitle.setText(secondHandHouseDetail.getTitle());
			tvDetailtitle.setText(secondHandHouseDetail.getTitle());
			tvTotalPrice.setText(secondHandHouseDetail.getPrice() + "万");
			String houseType = secondHandHouseDetail.getTypeF() + "房"
					+ secondHandHouseDetail.getTypeT() + "室";
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
			tvMonthlyPay.setText(secondHandHouseDetail.getMonthlyPayment()
					+ "元");
			tvFloor.setText(secondHandHouseDetail.getFloor() + "/"
					+ secondHandHouseDetail.getTotalFloor() + "层");
			tvDirection.setText(secondHandHouseDetail.getDirection());
			tvDecoration.setText(secondHandHouseDetail.getDecoration());
			tvEstateName.setText(secondHandHouseDetail.getEstateName());

			AgentBean agentBean = secondHandHouseDetail.getAgent();
			if (agentBean != null) {
				tvAgentName.setText(agentBean.getName());
				tvAgentPhone.setText(agentBean.getTel());
				ImageAction.displayBrokerAvatar(agentBean.getPhoto(),
						agentAvatar);
			}

		}
	}

	private void getSecondHandHouseDetail() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSecondHandHouseDetail(houseSourceId,
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {
					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						secondHandHouseDetail = JSON.parseObject(
								result.getData(),
								SecondHandHouseDetailBean.class);
						rendUI();
					}
				});
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		int photoTotalNum = photoAdapter.getCount();
		String txt = photoTotalNum + "/" + (arg0 + 1);
		tvPhotoIndex.setText(txt);
	}

	private void attentionToHouse() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.attentionToHouse(userId, houseSourceId,
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {

					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {

					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						ToastUtils.SHORT.toast(
								SecondHandHouseDetailActivity.this, "关注成功");
					}
				});
	}
}
