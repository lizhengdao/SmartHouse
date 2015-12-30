package cn.com.zzwfang.activity;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.MyDemandInfoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

/**
 * 我是客户
 * @author lzd
 *
 */
public class IAmCustomerActivity extends BaseActivity implements OnClickListener {

	public static final int CODE_EDIT_PROXY_BUY_HOUSE_INFO = 100;
	
	private TextView tvBack;
	private TextView tvCourtName, tvTotalPrice, tvSquare, tvHouseType;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
		getMyDemandInfo();
	}
	
	private void initView() {
		setContentView(R.layout.act_i_am_customer);
		
		tvBack = (TextView) findViewById(R.id.act_i_am_customer_back);
		tvCourtName = (TextView) findViewById(R.id.act_i_am_customer_court_name);
		tvTotalPrice = (TextView) findViewById(R.id.act_i_am_customer_total_price);
		tvSquare = (TextView) findViewById(R.id.act_i_am_customer_square);
		tvHouseType = (TextView) findViewById(R.id.act_i_am_customer_house_type);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_i_am_customer_back:
			finish();
			break;
		}
	}
	
	private void rendUI(MyDemandInfoBean myDemandInfoBean) {
		if (myDemandInfoBean != null) {
			tvCourtName.setText(myDemandInfoBean.getEstateName());
			tvTotalPrice.setText(myDemandInfoBean.getMinPrice() + "万元  至  " + myDemandInfoBean.getMaxPrice() + "万元");
			tvSquare.setText(myDemandInfoBean.getSquareMin() + "㎡  至  " + myDemandInfoBean.getSquareMax() + "㎡");
			tvHouseType.setText(myDemandInfoBean.getHouseType());
		}
	}
	
	/**
	 * 获取自己委托买房信息
	 */
	private void getMyDemandInfo() {
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.getMyDemandInfo(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				MyDemandInfoBean myDemandInfoBean = JSON.parseObject(result.getData(), MyDemandInfoBean.class);
				if (myDemandInfoBean == null) {  // 跳转委托买房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jumpForResult(IAmCustomerActivity.this, IWantBuyHouseActivity.class, CODE_EDIT_PROXY_BUY_HOUSE_INFO);
				} else {
					rendUI(myDemandInfoBean);
				}
				
			}
		});
	}
}
