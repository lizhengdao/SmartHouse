package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.MyBoughtHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnProxyDialogListener;

public class MyProxyActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvSellHouse, tvIBuyHouse, tvSeeMyBoughtHouses;
	
	private ArrayList<MyBoughtHouseBean> houses = new ArrayList<MyBoughtHouseBean>();
	
	private OnProxyDialogListener onProxyDialogListener;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_my_proxy);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_my_proxy_back);
		tvSellHouse = (TextView) findViewById(R.id.act_my_proxy_sell_house);
		tvIBuyHouse = (TextView) findViewById(R.id.act_my_proxy_i_buy_house);
		tvSeeMyBoughtHouses = (TextView) findViewById(R.id.act_my_proxy_see_my_houses); // 我的购房
		
		tvBack.setOnClickListener(this);
		tvSellHouse.setOnClickListener(this);
		tvIBuyHouse.setOnClickListener(this);
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
		getMyBoughtHouses();
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
		case R.id.act_my_proxy_i_buy_house:  // 我要买房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, IWantBuyHouseActivity.class);
			break;
		case R.id.act_my_proxy_see_my_houses:
			if (houses == null || houses.size() == 0) {
				PopViewHelper.showProxyDialog(this, onProxyDialogListener);
			} else {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MyBoughtHousesActivity.class);  // 跳我的购房列表
			}
			break;
		}
	}
	
	
	private void getMyBoughtHouses() {
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getMyBoughtHouses(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				ArrayList<MyBoughtHouseBean> temp = (ArrayList<MyBoughtHouseBean>) JSON.parseArray(result.getData(), MyBoughtHouseBean.class);
				if (temp != null) {
					houses.addAll(temp);
				}
				
			}
		});
	}
	
}
