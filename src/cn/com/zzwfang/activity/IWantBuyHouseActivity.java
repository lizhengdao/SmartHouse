package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;

/**
 * 我要买房
 * @author lzd
 *
 */
public class IWantBuyHouseActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvMonthlyPay;
	private LinearLayout lltMonthlyPay;
	/**
     * 月供
     */
    private ArrayList<TextValueBean> monthlyPay = new ArrayList<TextValueBean>();
    private OnConditionSelectListener onMonthlyPaySelectListener;
    private TextValueBean monthlyPayCondition;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMonthlyPayData();
	}
	
	private void initView() {
		setContentView(R.layout.act_i_want_buy_house);
		tvBack = (TextView) findViewById(R.id.act_i_want_buy_house_back);
		tvMonthlyPay = (TextView) findViewById(R.id.act_i_want_buy_house_monthly_pay_tv);
		lltMonthlyPay = (LinearLayout) findViewById(R.id.act_i_want_buy_house_monthly_pay_llt);
		
		tvBack.setOnClickListener(this);
		lltMonthlyPay.setOnClickListener(this);
		
        onMonthlyPaySelectListener = new OnConditionSelectListener() {
            
            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                // TODO Auto-generated method stub
                if (monthlyPayCondition == null || monthlyPayCondition.getValue() == null || !monthlyPayCondition.getValue().equals(txtValueBean.getValue())) {
                    monthlyPayCondition = txtValueBean;
                    tvMonthlyPay.setText(txtValueBean.getText());
                }
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
		}
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
