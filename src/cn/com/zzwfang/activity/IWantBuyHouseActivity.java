package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;

/**
 * 我要买房(委托买房)
 * @author lzd
 *
 */
public class IWantBuyHouseActivity extends BaseActivity implements OnClickListener {
	
	private static final int REQUEST_ESTATE = 120;

	private TextView tvBack, tvSelectEstateName, tvMonthlyPay, tvPhone, tvCommit;
	private LinearLayout lltMonthlyPay;
	
	private EditText edtMinSquare, edtMaxSquare;
	private EditText edtMinTotalPrice, edtMaxTotalPrice;
	private EditText edtRooms, edtHalls;
	private EditText edtOtherDesc;
	private EditText edtYourName;
	private CheckBox cbxSex;
	
	/**
     * 月供
     */
    private ArrayList<TextValueBean> monthlyPay = new ArrayList<TextValueBean>();
    private OnConditionSelectListener onMonthlyPaySelectListener;
    private TextValueBean monthlyPayCondition;
    
    /**
     * 选择的小区名称
     */
    private IdNameBean idNameBean;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMonthlyPayData();
	}
	
	private void initView() {
		setContentView(R.layout.act_i_want_buy_house);
		tvBack = (TextView) findViewById(R.id.act_i_want_buy_house_back);
		tvSelectEstateName = (TextView) findViewById(R.id.act_i_want_buy_house_select_estate_name);
		tvMonthlyPay = (TextView) findViewById(R.id.act_i_want_buy_house_monthly_pay_tv);
		lltMonthlyPay = (LinearLayout) findViewById(R.id.act_i_want_buy_house_monthly_pay_llt);
		
		edtMinSquare = (EditText) findViewById(R.id.act_i_want_buy_house_min_square);
	    edtMaxSquare = (EditText) findViewById(R.id.act_i_want_buy_house_max_square);
	    
	    edtMinTotalPrice = (EditText) findViewById(R.id.act_i_want_buy_house_min_total_price);
	    edtMaxTotalPrice = (EditText) findViewById(R.id.act_i_want_buy_house_max_total_price);
	    
	    edtRooms = (EditText) findViewById(R.id.act_i_want_buy_house_rooms);
	    edtHalls = (EditText) findViewById(R.id.act_i_want_buy_house_halls);
	    
	    edtOtherDesc = (EditText) findViewById(R.id.act_i_want_buy_house_other_desc);
	    edtYourName = (EditText) findViewById(R.id.act_i_want_buy_house_name);
	    
	    cbxSex = (CheckBox) findViewById(R.id.act_i_want_buy_house_sex_cbx);
	    tvCommit = (TextView) findViewById(R.id.act_i_want_buy_house_commit);
	    
	    tvPhone = (TextView) findViewById(R.id.act_i_want_buy_house_phone);
	    tvPhone.setText(ContentUtils.getLoginPhone(this));
	    
		tvBack.setOnClickListener(this);
		lltMonthlyPay.setOnClickListener(this);
		tvSelectEstateName.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		
        onMonthlyPaySelectListener = new OnConditionSelectListener() {
            
            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                // TODO Auto-generated method stub
//                if (monthlyPayCondition == null || monthlyPayCondition.getValue() == null || !monthlyPayCondition.getValue().equals(txtValueBean.getValue())) {
//                    
//                }
                monthlyPayCondition = txtValueBean;
                tvMonthlyPay.setText(txtValueBean.getText());
            }
        };
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_i_want_buy_house_back:
			finish();
			break;
		case R.id.act_i_want_buy_house_monthly_pay_llt:
			PopViewHelper.showSelectAreaPopWindow(this, lltMonthlyPay, monthlyPay, onMonthlyPaySelectListener);
			break;
		case R.id.act_i_want_buy_house_select_estate_name:
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jumpForResult(this, SelectEstateActivity.class,
					REQUEST_ESTATE);
			break;
		case R.id.act_i_want_buy_house_commit:
			tvCommit.setClickable(false);
			entrustBuyHouse();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_ESTATE:
				idNameBean = (IdNameBean) data
				.getSerializableExtra(SelectEstateActivity.INTENT_ESTATE);
		        tvSelectEstateName.setText(idNameBean.getName());
				break;
			}
		}
	}
	
	private void entrustBuyHouse() {
		if (idNameBean == null) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请选择小区名称");
			return;
		}
		if (monthlyPayCondition == null) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请选择月供范围");
			return;
		}
		
		String minSquare = edtMinSquare.getText().toString();
		String maxSquare = edtMaxSquare.getText().toString();
		if (TextUtils.isEmpty(minSquare)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入最小面积");
			return;
		}
		if (TextUtils.isEmpty(maxSquare)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入最大面积");
			return;
		}
		int minSquareInt = Integer.valueOf(minSquare);
		int maxSquareInt = Integer.valueOf(maxSquare);
		if (minSquareInt >= maxSquareInt) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "最小面积应小于最大面积");
			return;
		}
		
		String minTotalPriceStr = edtMinTotalPrice.getText().toString();
		String maxTotalPriceStr = edtMaxTotalPrice.getText().toString();
		if (TextUtils.isEmpty(minTotalPriceStr)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入最小总价");
			return;
		}
		if (TextUtils.isEmpty(maxTotalPriceStr)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入最大总价");
			return;
		}
		
		double minTotalPrice = Double.valueOf(minTotalPriceStr);
		double maxTotalPrice = Double.valueOf(maxTotalPriceStr);
		if (minTotalPrice >= maxTotalPrice) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "最小总价应小于最大总价");
			return;
		}
		
		String rooms = edtRooms.getText().toString();
		String halls = edtHalls.getText().toString();
		if (TextUtils.isEmpty(rooms)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入您想买几室的房");
			return;
		}
		if (TextUtils.isEmpty(halls)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入您想买几厅的房");
			return;
		}
		int countFang = Integer.valueOf(rooms);
		int hall = Integer.valueOf(halls);
		
		String otherDesc = edtOtherDesc.getText().toString();
		if (TextUtils.isEmpty(otherDesc)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入您的其他要求或描述");
			return;
		}
		
		String name = edtYourName.getText().toString();
		if (TextUtils.isEmpty(name)) {
			tvCommit.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入您的姓名");
			return;
		}
		boolean sex = cbxSex.isChecked();
		CityBean cityBean = ContentUtils.getCityBean(this);
		String cityId = cityBean.getSiteId();
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.entrustBuyHouse(cityId, userId, idNameBean.getId(), -1,
				minSquareInt, maxSquareInt, minTotalPrice, maxTotalPrice,
				monthlyPayCondition.getValue(),
				countFang, hall, otherDesc,
				name, sex, new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
						tvCommit.setClickable(true);
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
						tvCommit.setClickable(true);
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
						tvCommit.setClickable(true);
						finish();
						ToastUtils.SHORT.toast(IWantBuyHouseActivity.this, "提交成功");
					}
				});
	}
	
	private void getMonthlyPayData() {
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getMonthlyPayRange(new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON.parseArray(result.getData(), TextValueBean.class);
                monthlyPay.addAll(temp);
            }
        });
	}
}
