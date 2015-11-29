package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.NewHouseDetailBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnShareTypeSelectListener;

/**
 * 小区详情
 * @author lzd
 *
 */
public class CourtDetailActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {

	public static final String INTENT_COURT_NAME = "intent_court_name";
	public static final String INTENT_COURT_ID = "intent_court_id";
	
	private TextView tvBack, tvPageTitle, tvTitle, tvPhotoIndex, tvShare, tvConsult;
	private TextView tvPropertyManageFee;
	private TextView tvBuildings, tvHouses;
	private TextView tvPlotRatio, tvBuildYear;
	private TextView tvGreenRatio, tvHouseType;
	private TextView tvBuildingType;
	
	
	private String courtId;
	private String courtName;
	
	/**
	 * 小区详情页，调用的是新房详情接口
	 */
	private NewHouseDetailBean newHouseDetailBean;
	private ViewPager photoPager;
	private PhotoPagerAdapter photoAdapter;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();
	
	private OnShareTypeSelectListener onShareTypeSelectListener;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		courtId = intent.getStringExtra(INTENT_COURT_ID);
		courtName = intent.getStringExtra(INTENT_COURT_NAME);
		initView();
		getCourtDetail();
	}
	
	private void initView() {
		setContentView(R.layout.act_court_detail);
		
		tvBack = (TextView) findViewById(R.id.act_court_detail_back);
		tvPageTitle = (TextView) findViewById(R.id.act_court_detail_title);
		tvTitle = (TextView) findViewById(R.id.act_court_detail_title_tv);
		tvPhotoIndex = (TextView) findViewById(R.id.act_court_detail_photo_index_tv);
		tvShare = (TextView) findViewById(R.id.act_court_detail_detail_share);
		tvConsult = (TextView) findViewById(R.id.act_court_detail_consult_tv);
		
		tvPropertyManageFee = (TextView) findViewById(R.id.act_court_detail_property_management_fee);
		tvBuildings = (TextView) findViewById(R.id.act_court_detail_buildings);
		tvHouses = (TextView) findViewById(R.id.act_court_detail_houses);
		tvPlotRatio = (TextView) findViewById(R.id.act_court_detail_plot_ratio_tv);
		tvBuildYear = (TextView) findViewById(R.id.act_court_detail_build_year_tv);
		tvGreenRatio = (TextView) findViewById(R.id.act_court_detail_greening_rate_tv);
		tvHouseType = (TextView) findViewById(R.id.act_court_detail_house_type_tv);
		tvBuildingType = (TextView) findViewById(R.id.act_court_detail_building_type);
		
		tvPageTitle.setText(courtName);
		tvTitle.setText(courtName);
		
		photoPager = (ViewPager) findViewById(R.id.act_court_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
		photoPager.setAdapter(photoAdapter);
		
		tvBack.setOnClickListener(this);
		tvShare.setOnClickListener(this);
		tvConsult.setOnClickListener(this);
		
		onShareTypeSelectListener = new OnShareTypeSelectListener() {
			
			@Override
			public void onShareTypeSelected(int shareType) {
				switch (shareType) {
				case OnShareTypeSelectListener.Share_Type_WeiXin:  // 微信分享
					// TODO
					break;
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_court_detail_back:
			finish();
			break;
		case R.id.act_court_detail_detail_share:
			PopViewHelper.showSharePopupWindow(this, getWindow().getDecorView(), onShareTypeSelectListener);
			break;
		case R.id.act_court_detail_consult_tv:   //  我要咨询
			if (newHouseDetailBean != null) {
				AgentBean agent = newHouseDetailBean.getAgent();
				if (agent != null) {
					Jumper.newJumper()
					.setAheadInAnimation(R.anim.activity_push_in_right)
					.setAheadOutAnimation(R.anim.activity_alpha_out)
					.setBackInAnimation(R.anim.activity_alpha_in)
					.setBackOutAnimation(R.anim.activity_push_out_right)
					.putString(ChatActivity.INTENT_MESSAGE_TO_ID, agent.getAgentId())
					.putString(ChatActivity.INTENT_MESSAGE_TO_NAME, agent.getName())
					.jump(this, ChatActivity.class);
				}
			}
			break;
		}
	}
	
	private void rendUI() {
		if (newHouseDetailBean != null) {
			tvPageTitle.setText(newHouseDetailBean.getName());
			tvTitle.setText(newHouseDetailBean.getName());
			
			tvGreenRatio.setText(newHouseDetailBean.getGreeningRate());
			tvPlotRatio.setText(newHouseDetailBean.getPlotRatio());
			tvHouseType.setText(newHouseDetailBean.getPropertyType());
//			tvBuildingType.setText(newHouseDetailBean.getPropertyType());
			
//			tvAveragePrice.setText(newHouseDetailBean.getUnitPrice());
//			tvArea.setText(newHouseDetailBean.getFloorArea());
//			tvSquare.setText(newHouseDetailBean.getFloorArea());
//			tvPropertyType.setText(newHouseDetailBean.getMgtCompany());
//			tvPlotRatio.setText(newHouseDetailBean.getPlotRatio());
//			tvDecoration.setText(newHouseDetailBean.getDecoration());
//			tvGreenRatio.setText(newHouseDetailBean.getGreeningRate());
//			tvMgtCompany.setText(newHouseDetailBean.getMgtCompany());
//			tvBuilderCompany.setText(newHouseDetailBean.getBuilder());

			photos.addAll(newHouseDetailBean.getPhoto());
			photoAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * 获取小区详情（调用的是新房详情接口）
	 */
	private void getCourtDetail() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewHouseDetail(courtId, new ResultHandlerCallback() {

			@Override
			public void rc999(RequestEntity entity, Result result) {

			}

			@Override
			public void rc3001(RequestEntity entity, Result result) {

			}

			@Override
			public void rc0(RequestEntity entity, Result result) {
				newHouseDetailBean = JSON.parseObject(result.getData(),
						NewHouseDetailBean.class);
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
}
