package cn.com.zzwfang.activity;

import java.io.File;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;

public class FillBankCardInfoActivity extends BasePickPhotoActivity implements OnClickListener {

	private TextView tvBack, tvCommit;
	
	private EditText edtUserName, edtBankCode, edtOpenAccountBankName;
	
	private LinearLayout lltBankName, lltOpenAccountBankCity;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fill_bank_card_info);
		
		tvBack = (TextView) findViewById(R.id.act_fill_bank_card_info_back);
		tvCommit = (TextView) findViewById(R.id.act_fill_card_info_commit_tv);
		
		edtUserName = (EditText) findViewById(R.id.act_fill_card_info_name_edt);
		edtBankCode = (EditText) findViewById(R.id.act_fill_card_info_card_num_edt);
		edtOpenAccountBankName = (EditText) findViewById(R.id.act_fill_card_info_open_bank_name_edt);
		
		lltBankName = (LinearLayout) findViewById(R.id.act_fill_card_info_bank_name_llt);
		lltOpenAccountBankCity = (LinearLayout) findViewById(R.id.act_fill_card_info_open_account_city_llt);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		lltBankName.setOnClickListener(this);
		lltOpenAccountBankCity.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fill_bank_card_info_back:
			finish();
			break;
			
		case R.id.act_fill_card_info_commit_tv:   //  跳转赏金猎人个人中心
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jump(this, FeeHunterInfoActivity.class);
			break;
		case R.id.act_fill_card_info_bank_name_llt:   //  银行名称选择
			// TODO
			
			break;
		case R.id.act_fill_card_info_open_account_city_llt:  //  开户行城市选择
			// TODO
			
			break;
		}
	}
	
	private void commitBankInfo() {
		String userId = ContentUtils.getUserId(this);
		String realName = edtUserName.getText().toString();
		if (TextUtils.isEmpty(realName)) {
			ToastUtils.SHORT.toast(this, "请输入收款人姓名");
			return;
		}
		String bankCode = edtBankCode.getText().toString();
        if (TextUtils.isEmpty(bankCode)) {
        	ToastUtils.SHORT.toast(this, "请输入银行卡号");
			return;
		}
        
        String openAccountBankName = edtOpenAccountBankName.getText().toString();
        if (TextUtils.isEmpty(openAccountBankName)) {
        	ToastUtils.SHORT.toast(this, "请输入开户行");
			return;
		}


		
//		ActionImpl actionImpl = ActionImpl.newInstance(this);
//		actionImpl.commitFeeHunterBankInfo(userId, realName,
//				bankCode, bankName, bankCity,
//				bankImage, openAccountBankName, new ResultHandlerCallback() {
//					
//					@Override
//					public void rc999(RequestEntity entity, Result result) {
//						
//					}
//					
//					@Override
//					public void rc3001(RequestEntity entity, Result result) {
//						
//					}
//					
//					@Override
//					public void rc0(RequestEntity entity, Result result) {
//						
//					}
//				});
	}

	@Override
	public void onPickedPhoto(File file, Bitmap bm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDisplayWidth() {
		return 200;
	}

	@Override
	public int getDisplayHeight() {
		// TODO Auto-generated method stub
		return 200;
	}
}
