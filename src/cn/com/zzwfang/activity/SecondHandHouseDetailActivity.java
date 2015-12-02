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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SecondHandHouseDetailBean;
import cn.com.zzwfang.config.API;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.PathImage;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnMoreShareAndAttentionListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnShareTypeSelectListener;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 二手房详情
 * 
 * @author lzd
 * 
 */
public class SecondHandHouseDetailActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener, IUiListener {
    
    public static final String Tencent_app_id = "";

	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	private TextView tvBack, tvTitle, tvMore, tvDetailtitle;
	private TextView tvTotalPrice, tvHouseType, tvSquare, tvLabel, tvUnitPrice;
	private TextView tvPartialPrice, tvMonthlyPay, tvFloor, tvDirection,
			tvDecoration, tvEstateName, tvAgentName, tvAgentPhone,
			tvPhotoIndex, tvSeeHouseRecord, tvNearbyDetail,
			tvSandTableDisplay, tvTransactionHistory, tvInnerThreeDimensionDisplay;
	
	private TextView tvApartmentLayout, tvBidPrice, tvBidDate, tvFollowPersons;
	
	private View lineSandTableDisplay, lineInnerThreeDimensionDisplay;
	
	private LinearLayout lltBrokerInfo, lltCourt;
	private AutoDrawableTextView tvAgentDial, tvAgentMsg;
	private TextView tvMortgageCalculate;
	private MapView mapView;
	private WebView webViewPriceTrend;
	private ViewPager photoPager;
	private PhotoPagerAdapter photoAdapter;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();

	private PathImage agentAvatar;
	private String houseSourceId = null;

	private SecondHandHouseDetailBean secondHandHouseDetail;
	
	private OnMoreShareAndAttentionListener onMoreShareAndAttentionListener;
	
	private OnShareTypeSelectListener onShareTypeSelectListener;
	
	private Tencent tencent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		tencent = Tencent.createInstance(Tencent_app_id, this.getApplicationContext());
		initView();
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		getSecondHandHouseDetail();
	}

	private void initView() {
		setContentView(R.layout.act_second_hand_house_detail);

		tvBack = (TextView) findViewById(R.id.act_second_hand_house_detail_back);
		tvTitle = (TextView) findViewById(R.id.act_second_hand_house_detail_title);
		tvMore = (TextView) findViewById(R.id.act_second_hand_house_detail_more);
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
		tvNearbyDetail = (TextView) findViewById(R.id.act_second_handhouse_nearby_detail_tv);
		tvSandTableDisplay = (TextView) findViewById(R.id.act_second_hand_house_detail_sand_table_display);
		tvTransactionHistory = (TextView) findViewById(R.id.act_second_handhouse_transaction_history_tv);
		lltBrokerInfo = (LinearLayout) findViewById(R.id.act_second_hand_house_detail_broker_info_llt);
		tvInnerThreeDimensionDisplay = (TextView) findViewById(R.id.act_second_hand_house_detail_inner_three_dimession_display);
		lltCourt = (LinearLayout) findViewById(R.id.act_second_hand_house_detail_court);
		tvBidPrice = (TextView) findViewById(R.id.act_second_hand_house_detail_bid_price);
		tvApartmentLayout = (TextView) findViewById(R.id.act_second_hand_house_detail_apartment_layout);
		tvBidDate = (TextView) findViewById(R.id.act_second_hand_house_detail_bid_date);
		tvFollowPersons = (TextView) findViewById(R.id.act_second_hand_house_detail_follow_persons);
		
		lineSandTableDisplay = findViewById(R.id.act_second_hand_house_detail_sand_table_display_line);
		lineInnerThreeDimensionDisplay = findViewById(R.id.act_second_hand_house_detail_inner_three_dimession_display_line);
		
		mapView = (MapView) findViewById(R.id.act_second_hand_house_detail_map_view);
		
		webViewPriceTrend = (WebView) findViewById(R.id.act_second_hand_house_detail_price_trend);
		WebSettings ws = webViewPriceTrend.getSettings();
		ws.setBuiltInZoomControls(false);
		ws.setJavaScriptEnabled(true);

		photoPager = (ViewPager) findViewById(R.id.act_second_house_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
		photoPager.setAdapter(photoAdapter);

		tvBack.setOnClickListener(this);
		tvMore.setOnClickListener(this);
		tvSeeHouseRecord.setOnClickListener(this);
		photoPager.setOnPageChangeListener(this);
		tvMortgageCalculate.setOnClickListener(this);
		tvNearbyDetail.setOnClickListener(this);
		tvSandTableDisplay.setOnClickListener(this);
		tvTransactionHistory.setOnClickListener(this);
		tvAgentDial.setOnClickListener(this);
		tvAgentMsg.setOnClickListener(this);
		lltBrokerInfo.setOnClickListener(this);
		tvInnerThreeDimensionDisplay.setOnClickListener(this);
		lltCourt.setOnClickListener(this);
		
		onMoreShareAndAttentionListener = new OnMoreShareAndAttentionListener() {
			
			@Override
			public void onShare() {  //  分享
				PopViewHelper.showSharePopupWindow(SecondHandHouseDetailActivity.this, getWindow().getDecorView(), onShareTypeSelectListener);
			}
			
			@Override
			public void onAttention() {  // 关注
				attentionToHouse();
			}
		};
		
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
		case R.id.act_second_hand_house_detail_back:
			finish();
			break;
		case R.id.act_second_hand_house_detail_title: // 详情title
			break;
		case R.id.act_second_hand_house_detail_more: // 更多 （收藏  分享）
			PopViewHelper.showMoreShareAndAttention(this, tvMore, onMoreShareAndAttentionListener);
			break;
		case R.id.act_second_handhouse_see_house_record_tv: // 看房记录
			if (secondHandHouseDetail != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putSerializable(SeeHouseRecordActivity.INTENT_RECORD,
						secondHandHouseDetail.getInqFollowList())
				.jump(this, SeeHouseRecordActivity.class);
			}
			
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
			if (secondHandHouseDetail != null) {
				AgentBean agent = secondHandHouseDetail.getAgent();
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
		case R.id.act_second_hand_house_detail_court:   // 跳小区详情
			if (secondHandHouseDetail != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putString(CourtDetailActivity.INTENT_COURT_NAME, secondHandHouseDetail.getEstateName())
				.putString(CourtDetailActivity.INTENT_COURT_ID, secondHandHouseDetail.getEstateId())
				.jump(this, CourtDetailActivity.class);
			}
			break;
		case R.id.act_second_handhouse_nearby_detail_tv:  //  周边详情
			if (secondHandHouseDetail != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putDouble(NearbyDetailActivity.INTENT_LAT, secondHandHouseDetail.getLat())
				.putDouble(NearbyDetailActivity.INTENT_LNG, secondHandHouseDetail.getLng())
				.jump(this, NearbyDetailActivity.class);
			}
			
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
		case R.id.act_second_hand_house_detail_inner_three_dimession_display:
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.putString(SandTableDisplayActivity.INTENT_SAND_TABLE_DISPLAY_URL, secondHandHouseDetail.getPrp360())
			.putString(SandTableDisplayActivity.INTENT_ESTATE_NAME, secondHandHouseDetail.getEstateName())
			.jump(this, SandTableDisplayActivity.class);
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
		case R.id.act_second_hand_house_detail_broker_info_llt:  //  经纪人详情
			if (secondHandHouseDetail != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putSerializable(BrokerInfoActivity.INTENT_AGENT_DATA, secondHandHouseDetail.getAgent())
				.jump(this, BrokerInfoActivity.class);
			}
			break;
		}
	}

	private void rendUI() {
		if (secondHandHouseDetail != null) {
			
			if (TextUtils.isEmpty(secondHandHouseDetail.getEstate360())) {
				tvSandTableDisplay.setVisibility(View.GONE);
				lineSandTableDisplay.setVisibility(View.GONE);
			}
			
			if (TextUtils.isEmpty(secondHandHouseDetail.getPrp360())) {
				tvInnerThreeDimensionDisplay.setVisibility(View.GONE);
				lineInnerThreeDimensionDisplay.setVisibility(View.GONE);
			}
			
			photos.addAll(secondHandHouseDetail.getPhoto());
			photoAdapter.notifyDataSetChanged();
			int photoTotalNum = photoAdapter.getCount();
			if (photoTotalNum > 0) {
			    String txt = "1/" + photoTotalNum;
			    tvPhotoIndex.setText(txt);
			}
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
			tvPartialPrice.setText(secondHandHouseDetail.getPartialPrice() + "万");
			tvMonthlyPay.setText(secondHandHouseDetail.getMonthlyPayment()
					+ "元");
			tvFloor.setText(secondHandHouseDetail.getFloor() + "/"
					+ secondHandHouseDetail.getTotalFloor() + "层");
			tvDirection.setText(secondHandHouseDetail.getDirection());
			tvDecoration.setText(secondHandHouseDetail.getDecoration());
			tvEstateName.setText(secondHandHouseDetail.getEstateName());
			
			tvBidPrice.setText(secondHandHouseDetail.getPrice() + "万");
			tvApartmentLayout.setText(houseType);
			// 近一月新增31位看房客户，共52位
			tvFollowPersons.setText("近一月新增" + secondHandHouseDetail.getMonthAddFollow()
					+ "位看房客户，共" + secondHandHouseDetail.getTotalFollow() + "位");
			
			LatLng latLng = new LatLng(secondHandHouseDetail.getLat(), secondHandHouseDetail.getLng());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
			BaiduMap baiduMap = mapView.getMap();
			baiduMap.animateMapStatus(u);

			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_cur_location);
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().position(latLng)
					.icon(bitmap);
			// 在地图上添加Marker，并显示
			baiduMap.addOverlay(option);

			AgentBean agentBean = secondHandHouseDetail.getAgent();
			if (agentBean != null) {
				tvAgentName.setText(agentBean.getName());
				tvAgentPhone.setText(agentBean.getTel());
				ImageAction.displayBrokerAvatar(agentBean.getPhoto(),
						agentAvatar);
			}
			
			CityBean cityBean = ContentUtils.getCityBean(this);
			if (cityBean != null) {
//				String urlPriceTrend = API.host + "TrendChart/GetCityChartDataJson?siteId="
//			                   + cityBean.getSiteId() + "&sign=1111&timestamp=2222";
				
				String urlPriceTrend = API.host + "Estate/Chart?id="
		                   + secondHandHouseDetail.getEstateId() + "&sign=1111&timestamp=2222";
				
				webViewPriceTrend.loadUrl(urlPriceTrend);
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

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		int photoTotalNum = photoAdapter.getCount();
		String txt = (arg0 + 1) + "/" + photoTotalNum;
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
	
	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}

    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onComplete(Object arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onError(UiError arg0) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tencent.onActivityResult(requestCode, resultCode, data);
    }
}
