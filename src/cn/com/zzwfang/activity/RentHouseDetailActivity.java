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
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.RentHouseDetailBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.MapView;

/**
 *  租房详情页
 * @author lzd
 *
 */
public class RentHouseDetailActivity extends BaseActivity implements OnClickListener,
OnPageChangeListener {

	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	private String houseSourceId = null;
	private RentHouseDetailBean rentHouseDetailBean;
	
	private TextView tvBack, tvPageTitle, tvShare, tvTitle,
	tvRentPrice, tvHouseType, tvSquare, tvFloor, tvDirection,
	tvDecoration, tvYear, tvEstateName, tvHouseNum, tvAgentName,
	tvAgentPhone, tvPhotoIndex;
	
	private AutoDrawableTextView tvAgentDial, tvAgentMsg;
	
	private ViewPager photoPager;
	private PhotoPagerAdapter photoAdapter;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();
	
	private MapView mapView;
	
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
		tvFloor = (TextView) findViewById(R.id.act_rent_house_detail_floor_tv);
		tvDirection = (TextView) findViewById(R.id.act_rent_house_detail_direction_tv);
		tvDecoration = (TextView) findViewById(R.id.act_rent_house_detail_decoration_tv);
		tvYear = (TextView) findViewById(R.id.act_rent_house_detail_year_tv);
		tvEstateName = (TextView) findViewById(R.id.act_rent_house_detail_estatename_tv);
		tvHouseNum = (TextView) findViewById(R.id.act_rent_house_detail_num_tv);
		
		tvAgentName = (TextView) findViewById(R.id.act_rent_house_detail_agent_name);
		tvAgentPhone = (TextView) findViewById(R.id.act_rent_house_detail_agent_phone);
		tvPhotoIndex = (TextView) findViewById(R.id.act_rent_house_detail_photo_index_tv);
		tvAgentDial = (AutoDrawableTextView) findViewById(R.id.act_rent_house_detail_agent_dial);
		tvAgentMsg = (AutoDrawableTextView) findViewById(R.id.act_rent_house_detail_agent_msg);
		
		photoPager = (ViewPager) findViewById(R.id.act_rent_house_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
		photoPager.setAdapter(photoAdapter);
		
		mapView = (MapView) findViewById(R.id.act_rent_house_detail_map);
		
		tvBack.setOnClickListener(this);
		photoPager.setOnPageChangeListener(this);
		tvAgentDial.setOnClickListener(this);
		tvAgentMsg.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_rent_house_detail_back:
			finish();
			break;
		case R.id.act_rent_house_detail_agent_dial:   // 拨打经纪人电话
			if (rentHouseDetailBean != null) {
				AgentBean agent = rentHouseDetailBean.getAgent();
				String phone = agent.getTel();
				if (!TextUtils.isEmpty(phone)) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phone));
					startActivity(intent);
				}
			}
			break;
		case R.id.act_rent_house_detail_agent_msg:   //  给经纪人发消息
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, ChatActivity.class);
			break;
		}
	}
	
	private void rendUI() {
		if (rentHouseDetailBean != null) {
			
			photos.addAll(rentHouseDetailBean.getPhoto());
			photoAdapter.notifyDataSetChanged();
			int photoTotalNum = photoAdapter.getCount();
			String txt = photoTotalNum + "/1";
			tvPhotoIndex.setText(txt);
			
			tvPageTitle.setText(rentHouseDetailBean.getTitle());
			tvTitle.setText(rentHouseDetailBean.getTitle());
			tvRentPrice.setText(rentHouseDetailBean.getRentPrice() + "元/月");
			tvHouseType.setText(rentHouseDetailBean.getFloor() + "室" + rentHouseDetailBean.getTypeT() + "厅");
			tvSquare.setText(rentHouseDetailBean.getSquare() + "㎡");
			tvFloor.setText(rentHouseDetailBean.getFloor() + "层/" + rentHouseDetailBean.getTotalFloor() + "层");
			tvDirection.setText(rentHouseDetailBean.getDirection());
			tvDecoration.setText(rentHouseDetailBean.getDecoration());
			tvYear.setText(rentHouseDetailBean.getBuildYear());
			tvEstateName.setText(rentHouseDetailBean.getEstateName());
			tvHouseNum.setText(rentHouseDetailBean.getNo());
			
			AgentBean agentBean = rentHouseDetailBean.getAgent();
			if (agentBean != null) {
				tvAgentName.setText(agentBean.getName());
				tvAgentPhone.setText(agentBean.getTel());
			}
			
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

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		int photoTotalNum = photoAdapter.getCount();
		String txt = photoTotalNum + "/" + (arg0 + 1);
		tvPhotoIndex.setText(txt);
	}

	
}
