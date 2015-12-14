package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.WalletAdapter;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.WalletDetailItemBean;
import cn.com.zzwfang.bean.WalletInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;

import com.alibaba.fastjson.JSON;

public class FeeHunterWalletActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvLastMonthIncome, tvTotalIncome;
	
	private ListView lstWallet;
	
	private WalletAdapter adapter;
	
	private WalletInfoBean walletInfoBean;
	
	private ArrayList<WalletDetailItemBean> walletInfos = new ArrayList<WalletDetailItemBean>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getWalletInfo();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_wallet);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_wallet_back);
		tvLastMonthIncome = (TextView) findViewById(R.id.act_fee_hunter_wallet_last_month_income);
		tvTotalIncome = (TextView) findViewById(R.id.act_fee_hunter_wallet_total_income);
		lstWallet = (ListView) findViewById(R.id.act_fee_hunter_wallet_lst);
		
		adapter = new WalletAdapter(this, walletInfos);
		lstWallet.setAdapter(adapter);
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_wallet_back:
			finish();
			break;
		}
	}
	
	private void rendUI() {
		if (walletInfoBean != null) {
			tvLastMonthIncome.setText("上月收益(" + walletInfoBean.getMonthly() + "元)");
			tvTotalIncome.setText(walletInfoBean.getCumulative() + "元");
			walletInfos.addAll(walletInfoBean.getDetailed());
			adapter.notifyDataSetChanged();
		}
	}
	
	private void getWalletInfo() {
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getWalletInfo(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				walletInfoBean = JSON.parseObject(result.getData(), WalletInfoBean.class);
				rendUI();
			}
		});
	}

}
