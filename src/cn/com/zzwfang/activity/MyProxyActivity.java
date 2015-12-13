package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MyProxySellHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnProxyDialogListener;

import com.alibaba.fastjson.JSON;

public class MyProxyActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvSellHouse, tvSeeMyBoughtHouses;
	
//	private ArrayList<MyBoughtHouseBean> houses = new ArrayList<MyBoughtHouseBean>();
	
	private OnProxyDialogListener onProxyDialogListener;
	
	private ArrayList<MyProxySellHouseBean> myProxySellHouses = new ArrayList<MyProxySellHouseBean>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_my_proxy);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_my_proxy_back);
		tvSellHouse = (TextView) findViewById(R.id.act_my_proxy_sell_house);
		tvSeeMyBoughtHouses = (TextView) findViewById(R.id.act_my_proxy_see_my_houses); // 我的购房
		
		tvBack.setOnClickListener(this);
		tvSellHouse.setOnClickListener(this);
		tvSeeMyBoughtHouses.setOnClickListener(this);
		
		onProxyDialogListener = new OnProxyDialogListener() {
			
			@Override
			public void onGoToSee() {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(MyProxyActivity.this, ProxySellHouseActivity.class);
			}
			
			@Override
			public void onCancel() {
				
			}
		};
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getMyProxySellHouse();
//		getMyBoughtHouses();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_proxy_back:  //  返回
			finish();
			break;
		case R.id.act_my_proxy_sell_house:  //委托售房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, ProxySellHouseActivity.class);
			break;
		case R.id.act_my_proxy_see_my_houses:  // 跳我的卖房列表页
			if (myProxySellHouses == null || myProxySellHouses.size() == 0) {
				PopViewHelper.showProxyDialog(this, onProxyDialogListener);
			} else {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MySellHouseListActivity.class);  
			}
			break;
		}
	}
	
	
	private void getMyProxySellHouse() {
		String userPhone = ContentUtils.getLoginPhone(this);
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getMySellHouseList(userPhone, cityBean.getSiteId(), 10, 1, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<MyProxySellHouseBean> temp = (ArrayList<MyProxySellHouseBean>) JSON.parseArray(result.getData(), MyProxySellHouseBean.class);
				myProxySellHouses.addAll(temp);
			}
		});
	}
	
//	private void getMyBoughtHouses() {
//		String userId = ContentUtils.getUserId(this);
//		ActionImpl actionImpl = ActionImpl.newInstance(this);
//		actionImpl.getMyBoughtHouses(userId, new ResultHandlerCallback() {
//			
//			@Override
//			public void rc999(RequestEntity entity, Result result) {
//			}
//			
//			@Override
//			public void rc3001(RequestEntity entity, Result result) {
//			}
//			
//			@Override
//			public void rc0(RequestEntity entity, Result result) {
//				// TODO Auto-generated method stub
//				ArrayList<MyBoughtHouseBean> temp = (ArrayList<MyBoughtHouseBean>) JSON.parseArray(result.getData(), MyBoughtHouseBean.class);
//				if (temp != null) {
//					houses.addAll(temp);
//				}
//				
//			}
//		});
//	}
	
}
