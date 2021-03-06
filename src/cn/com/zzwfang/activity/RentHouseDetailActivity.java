package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.PhotoPagerAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.RentHouseDetailBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.config.API;
import cn.com.zzwfang.constant.Constants;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.share.WeiXinShareHelper;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.RSAUtil;
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
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

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
	
	private TextView tvBack, tvPageTitle, tvMore, tvTitle,
	tvRentPrice, tvHouseType, tvSquare, tvFloor, tvDirection,
	tvDecoration, tvYear, tvEstateName, tvHouseNum, tvAgentName,
	tvAgentPhone, tvPhotoIndex, tvNearbyDeatil;
	
	private TextView inner3D, court3D;
	private View lineInner3D, lineCourt3D;
	
	private AutoDrawableTextView tvAgentDial, tvAgentMsg, tvShare;
	
	private PathImage agentAvatar;
	
	private ViewPager photoPager;
	private PhotoPagerAdapter photoAdapter;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();
	
	private MapView mapView;
	private WebView webViewPriceTrend;
	
	private OnMoreShareAndAttentionListener onMoreShareAndAttentionListener;
	private OnShareTypeSelectListener onShareTypeSelectListener;
	
	private IWXAPI apiWeixin;
	private String sharedPictureUrl;
    private Bitmap sharedBitmap;
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		apiWeixin = WXAPIFactory.createWXAPI(this, Constants.App_Id_Weixin, false);
		apiWeixin.registerApp(Constants.App_Id_Weixin);
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		initView();
		initListener();
		getRentHouseDetail();
	}
	
	private void initView() {
		setContentView(R.layout.act_rent_house_detail);
		tvBack = (TextView) findViewById(R.id.act_rent_house_detail_back);
		tvPageTitle = (TextView) findViewById(R.id.act_rent_house_detail_page_title);
		tvMore = (TextView) findViewById(R.id.act_rent_house_detail_more);
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
		tvShare = (AutoDrawableTextView) findViewById(R.id.act_rent_house_detail_share);
		agentAvatar = (PathImage) findViewById(R.id.act_rent_house_detail_agent_avatar);
		
		inner3D = (TextView) findViewById(R.id.act_rent_house_detail_inner_three_dimession_display);
		court3D = (TextView) findViewById(R.id.act_rent_house_detail_sand_table_display);
		lineInner3D = findViewById(R.id.act_rent_house_detail_inner_three_dimession_display_line);
		lineCourt3D = findViewById(R.id.act_rent_house_detail_sand_table_display_line);
		tvNearbyDeatil = (TextView) findViewById(R.id.act_rent_house_nearby_detail_tv);
		
		photoPager = (ViewPager) findViewById(R.id.act_rent_house_detail_pager);
		photoAdapter = new PhotoPagerAdapter(this, photos);
		photoPager.setAdapter(photoAdapter);
		
		mapView = (MapView) findViewById(R.id.act_rent_house_detail_map);
		
		webViewPriceTrend = (WebView) findViewById(R.id.act_rent_house_detail_price_trend);
		WebSettings ws = webViewPriceTrend.getSettings();
        ws.setBuiltInZoomControls(false);
        ws.setJavaScriptEnabled(true);
	}
	
	private void initListener() {
		tvBack.setOnClickListener(this);
		tvMore.setOnClickListener(this);
		photoPager.setOnPageChangeListener(this);
		tvAgentDial.setOnClickListener(this);
		tvAgentMsg.setOnClickListener(this);
		tvShare.setOnClickListener(this);
		tvEstateName.setOnClickListener(this);
		inner3D.setOnClickListener(this);
		court3D.setOnClickListener(this);
		tvNearbyDeatil.setOnClickListener(this);
        onMoreShareAndAttentionListener = new OnMoreShareAndAttentionListener() {
			
			@Override
			public void onShare() {  //  分享
				PopViewHelper.showSharePopupWindow(RentHouseDetailActivity.this,
						getWindow().getDecorView(), onShareTypeSelectListener);
			}
			
			@Override
			public void onAttention() {  // 关注
				attentionToHouse();
			}
		};
		
        onShareTypeSelectListener = new OnShareTypeSelectListener() {
			
			@Override
			public void onShareTypeSelected(int shareType) {
			    if (rentHouseDetailBean != null) {
			        switch (shareType) {
	                case OnShareTypeSelectListener.Share_Type_WeiXin:  // 微信分享
	                    // TODO
	                    WeiXinShareHelper weixinShareHelper = new WeiXinShareHelper();
	                    weixinShareHelper.shareWebpage(RentHouseDetailActivity.this, apiWeixin,
	                            "智住网", sharedBitmap, rentHouseDetailBean.getTitle(), rentHouseDetailBean.getShare(), true);
	                    break;
	                case OnShareTypeSelectListener.Share_Type_QQ:
	                    break;
	                case OnShareTypeSelectListener.Share_Type_Sina_Weibo:
	                    break;
	                case OnShareTypeSelectListener.Share_Type_WeiXin_Friend:
                        WeiXinShareHelper weixinShareHelper2 = new WeiXinShareHelper();
                        weixinShareHelper2.shareWebpage(RentHouseDetailActivity.this, apiWeixin,
                                "智住网", sharedBitmap, rentHouseDetailBean.getTitle(), rentHouseDetailBean.getShare(), false);
                        break;
	                }
			    }
				
			}
		};
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_rent_house_detail_back:
			finish();
			break;
		case R.id.act_rent_house_detail_more:  // // 更多 （收藏  分享）
			PopViewHelper.showMoreShareAndAttention(this, tvMore, false, onMoreShareAndAttentionListener);
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
			boolean loginStatus = ContentUtils.getUserLoginStatus(this);
			if (loginStatus) {
				if (rentHouseDetailBean != null) {
					AgentBean agent = rentHouseDetailBean.getAgent();
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
			} else {
				Jumper.newJumper().setAheadInAnimation(R.anim.slide_in_style1)
                .setAheadOutAnimation(R.anim.alpha_out_style1)
                .setBackInAnimation(R.anim.alpha_in_style1)
                .setBackOutAnimation(R.anim.slide_out_style1)
                .jump(this, LoginActivity.class);
			}
			break;
			
		case R.id.act_rent_house_detail_share:
			PopViewHelper.showSharePopupWindow(RentHouseDetailActivity.this,
					getWindow().getDecorView(), onShareTypeSelectListener);
			break;
			
		case R.id.act_rent_house_detail_estatename_tv:  //  小区详情
		    if (rentHouseDetailBean != null) {
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(CourtDetailActivity.INTENT_COURT_NAME, rentHouseDetailBean.getEstateName())
                .putString(CourtDetailActivity.INTENT_COURT_ID, rentHouseDetailBean.getEstateId())
                .jump(this, CourtDetailActivity.class);
            }
		    break;
		case R.id.act_rent_house_detail_inner_three_dimession_display:
		    if (rentHouseDetailBean != null) {
		        Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .putString(SandTableDisplayActivity.INTENT_SAND_TABLE_DISPLAY_URL, rentHouseDetailBean.getPrp360())
	            .putString(SandTableDisplayActivity.INTENT_ESTATE_NAME, rentHouseDetailBean.getEstateName())
	            .jump(this, SandTableDisplayActivity.class);
		    }
		    
            break;
		case R.id.act_rent_house_detail_sand_table_display:
		    if (rentHouseDetailBean != null) {
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(SandTableDisplayActivity.INTENT_SAND_TABLE_DISPLAY_URL, rentHouseDetailBean.getEstate360())
                .putString(SandTableDisplayActivity.INTENT_ESTATE_NAME, rentHouseDetailBean.getEstateName())
                .jump(this, SandTableDisplayActivity.class);
            }
		    break;
		case R.id.act_rent_house_nearby_detail_tv:
			if (rentHouseDetailBean != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putDouble(NearbyDetailActivity.INTENT_LAT, rentHouseDetailBean.getLat())
				.putDouble(NearbyDetailActivity.INTENT_LNG, rentHouseDetailBean.getLng())
				.jump(this, NearbyDetailActivity.class);
			}
			break;
		}
	}
	
	private void rendUI() {
		if (rentHouseDetailBean != null) {
		    
		    if (rentHouseDetailBean.getPhoto() != null && rentHouseDetailBean.getPhoto().size() > 0) {
                sharedPictureUrl = rentHouseDetailBean.getPhoto().get(0).getPath();
                sharedBitmap = ImageAction.loadBitmap(sharedPictureUrl);
            }
		    
		    if (TextUtils.isEmpty(rentHouseDetailBean.getEstate360())) {
		        court3D.setVisibility(View.GONE);
		        lineCourt3D.setVisibility(View.GONE);
            }
            
            if (TextUtils.isEmpty(rentHouseDetailBean.getPrp360())) {
                inner3D.setVisibility(View.GONE);
                lineInner3D.setVisibility(View.GONE);
            }
			
			photos.addAll(rentHouseDetailBean.getPhoto());
			photoAdapter.notifyDataSetChanged();
			int photoTotalNum = photoAdapter.getCount();
			if (photoTotalNum > 0) {
			    String txt = "1/" + photoTotalNum;
	            tvPhotoIndex.setText(txt);
			}
			tvPageTitle.setText(rentHouseDetailBean.getTitle());
			tvTitle.setText(rentHouseDetailBean.getTitle());
			tvRentPrice.setText(rentHouseDetailBean.getRentPrice() + "元/月");
			tvHouseType.setText(rentHouseDetailBean.getFloor() + "室" + rentHouseDetailBean.getTypeT() + "厅");
			tvSquare.setText(rentHouseDetailBean.getSquare() + "㎡");
			tvFloor.setText(rentHouseDetailBean.getFloor() + "/" + rentHouseDetailBean.getTotalFloor() + "层");
			tvDirection.setText(rentHouseDetailBean.getDirection());
			tvDecoration.setText(rentHouseDetailBean.getDecoration());
			tvYear.setText(rentHouseDetailBean.getBuildYear());
			tvEstateName.setText(rentHouseDetailBean.getEstateName());
			tvHouseNum.setText(rentHouseDetailBean.getNo());
			
			
			
			LatLng latLng = new LatLng(rentHouseDetailBean.getLat(), rentHouseDetailBean.getLng());
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

            AgentBean agentBean = rentHouseDetailBean.getAgent();
            if (agentBean != null) {
                tvAgentName.setText(agentBean.getName());
                tvAgentPhone.setText(agentBean.getTel());
                ImageAction.displayBrokerAvatar(agentBean.getPhoto(),
                        agentAvatar);
            }
            
            CityBean cityBean = ContentUtils.getCityBean(this);
            if (cityBean != null) {
//              String urlPriceTrend = API.host + "TrendChart/GetCityChartDataJson?siteId="
//                             + cityBean.getSiteId() + "&sign=1111&timestamp=2222";
                
                String timestamp = String.valueOf(System.currentTimeMillis());
                String sign = RSAUtil.encryptByPublic(timestamp);
                String urlPriceTrend = API.host + "Estate/Chart?id="
                           + rentHouseDetailBean.getEstateId() + "&sign=" + sign + "&timestamp=" + timestamp;
                
//                String urlPriceTrend = API.host + "Estate/Chart?id="
//                           + rentHouseDetailBean.getEstateId() + "&sign=1111&timestamp=2222";
                
                webViewPriceTrend.loadUrl(urlPriceTrend);
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
		String txt = (arg0 + 1) + "/" + photoTotalNum;
		tvPhotoIndex.setText(txt);
	}
	
	private void attentionToHouse() {
		boolean loginStatus = ContentUtils.getUserLoginStatus(this);
		if (!loginStatus) {
			ToastUtils.SHORT.toast(this, "您还未登录");
			return;
		}
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
								RentHouseDetailActivity.this, "关注成功");
					}
				});
	}

	
}
