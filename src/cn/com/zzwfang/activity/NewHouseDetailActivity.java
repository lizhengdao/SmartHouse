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
import cn.com.zzwfang.bean.NewHouseDetailBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;

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

public class NewHouseDetailActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {

	public static final String INTENT_ESTATE_ID = "intent_estate_id";

	private TextView tvBack, tvPageTitle, tvTitle, tvPhotoIndex,
			tvAveragePrice, tvConsultant, tvArea, tvSquare, tvPropertyType,
			tvPlotRatio, tvDecoration, tvGreenRatio, tvMgtCompany,
			tvBuilderCompany, tvMortgageCalculate, tv3d, tvIntroduction,
			tvLocation;
	
	private MapView mapView;

	private String estateId;

	private NewHouseDetailBean newHouseDetailBean;

	private ViewPager photoPager;
	private PhotoPagerAdapter photoAdapter;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();
	
	private ViewPager housePhotoPager;
	private PhotoPagerAdapter housePhotoAdapter;
	private ArrayList<PhotoBean> housePhotos = new ArrayList<PhotoBean>();

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
		tvMortgageCalculate = (TextView) findViewById(R.id.act_new_house_detail_calculator_tv);
		tvConsultant = (TextView) findViewById(R.id.act_new_house_detail_consult_tv);
		tv3d = (TextView) findViewById(R.id.act_new_house_detail_3d);
		tvIntroduction = (TextView) findViewById(R.id.act_new_house_detail_house_introduction_tv);
		tvLocation = (TextView) findViewById(R.id.act_new_house_detail_location);
		
		mapView = (MapView) findViewById(R.id.act_new_house_detail_map_view);
		
		housePhotoPager = (ViewPager) findViewById(R.id.act_new_house_detail_house_photo_pager);
		housePhotoAdapter = new PhotoPagerAdapter(this, housePhotos);
		housePhotoPager.setAdapter(housePhotoAdapter);

		photoPager = (ViewPager) findViewById(R.id.act_new_house_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
		photoPager.setAdapter(photoAdapter);

		tvBack.setOnClickListener(this);
		tvMortgageCalculate.setOnClickListener(this);
		tvConsultant.setOnClickListener(this);
		tv3d.setOnClickListener(this);
		tvLocation.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_new_house_detail_back:
			finish();
			break;

		case R.id.act_new_house_detail_calculator_tv: // 房贷计算器
			Jumper.newJumper()
					.setAheadInAnimation(R.anim.activity_push_in_right)
					.setAheadOutAnimation(R.anim.activity_alpha_out)
					.setBackInAnimation(R.anim.activity_alpha_in)
					.setBackOutAnimation(R.anim.activity_push_out_right)
					.jump(this, MortgageCalculatorActivity.class);
			break;
		case R.id.act_new_house_detail_consult_tv:
		    if (newHouseDetailBean != null) {
//                AgentBean agent = newHouseDetailBean.getAgent();
//                if (agent != null) {
//                    Jumper.newJumper()
//                    .setAheadInAnimation(R.anim.activity_push_in_right)
//                    .setAheadOutAnimation(R.anim.activity_alpha_out)
//                    .setBackInAnimation(R.anim.activity_alpha_in)
//                    .setBackOutAnimation(R.anim.activity_push_out_right)
//                    .putString(ChatActivity.INTENT_MESSAGE_TO_ID, agent.getAgentId())
//                    .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, agent.getName())
//                    .jump(this, ChatActivity.class);
//                }
		        
		        AgentBean agent = newHouseDetailBean.getAgent();
                String phone = agent.getTel();
                if (!TextUtils.isEmpty(phone)) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            }
		    break;
		case R.id.act_new_house_detail_3d:
		    if (newHouseDetailBean != null) {
		        Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .putString(SandTableDisplayActivity.INTENT_SAND_TABLE_DISPLAY_URL, newHouseDetailBean.getV360())
	            .putString(SandTableDisplayActivity.INTENT_ESTATE_NAME, newHouseDetailBean.getName())
	            .jump(this, SandTableDisplayActivity.class);
		    }
		    break;
		case R.id.act_new_house_detail_location:
		    if (newHouseDetailBean != null) {
		        Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putDouble(NearbyDetailActivity.INTENT_LAT, newHouseDetailBean.getLat())
                .putDouble(NearbyDetailActivity.INTENT_LNG, newHouseDetailBean.getLng())
                .jump(this, NearbyDetailActivity.class);
		    }
		    break;
		}
	}

	private void rendUI() {
		if (newHouseDetailBean != null) {
			tvPageTitle.setText(newHouseDetailBean.getName());
			tvTitle.setText(newHouseDetailBean.getName());
			tvAveragePrice.setText(newHouseDetailBean.getUnitPrice());
			tvArea.setText(newHouseDetailBean.getAeraName());
			tvSquare.setText(newHouseDetailBean.getFloorArea());
			tvPropertyType.setText(newHouseDetailBean.getMgtCompany());
			tvPlotRatio.setText(newHouseDetailBean.getPlotRatio());
			tvDecoration.setText(newHouseDetailBean.getDecoration());
			tvGreenRatio.setText(newHouseDetailBean.getGreeningRate());
			tvMgtCompany.setText(newHouseDetailBean.getMgtCompany());
			tvBuilderCompany.setText(newHouseDetailBean.getBuilder());
			tvIntroduction.setText(newHouseDetailBean.getRemark());

			photos.addAll(newHouseDetailBean.getPhoto());
			housePhotos.addAll(newHouseDetailBean.getHousePhoto());
			photoAdapter.notifyDataSetChanged();
			housePhotoAdapter.notifyDataSetChanged();
			
			LatLng latLng = new LatLng(newHouseDetailBean.getLat(), newHouseDetailBean.getLng());
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
		String txt = (arg0 + 1) + "/" + photoTotalNum;
		tvPhotoIndex.setText(txt);
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

}
