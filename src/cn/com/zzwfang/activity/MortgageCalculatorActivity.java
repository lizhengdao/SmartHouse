package cn.com.zzwfang.activity;

import java.text.DecimalFormat;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnMortgageTypeSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnMortgageYearSelectListener;

/**
 * 房贷计算器
 * @author lzd
 *
 */
public class MortgageCalculatorActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private TextView tvBack, tvMortgageType, tvMortgageYears, tvCalculate;
	
	private LinearLayout lltFundsResult;
	
	private TextView tvFundsTotalLoan, tvFundsTotalInterests, tvFundsTotalPay, tvFundsMonthPay, tvFundsMonths;
	
	private RadioButton rbFund, rbBusiness, rbMix;
	
	private EditText edtTotalMoney;
	
	private LinearLayout lltMortgageType, lltMortgageYears;
	
	private OnMortgageTypeSelectListener onMortgageTypeSelectListener;
	
	/**
	 * 贷款方式  1：等额本金     2：等额本息
	 */
	private int mortgageType = 1;
	
	
	private OnMortgageYearSelectListener onMortgageYearSelectListener;
	private String [] years;
	private int indexYear = 0;
	
	/**
	 * 贷款类别     1：公积金   2：商贷  3：混合贷
	 */
	private int mortgageCategory = 1;
	
	/**
	 * 贷款月数
	 */
	private int mortgageMonths = 12;
	
	/**
	 * 公积金利率  3.25%
	 */
	private double fundsRate = 0.0325 / 12;
	
	/**
	 * 商贷利率  4.9%
	 */
	private double businessRate = 0.049 / 12;
	
	private DecimalFormat df = new DecimalFormat("##0.00");
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_mortgage_calculator);
		
		initView();
		initData();
	}
	
	private void initView() {
		
		tvBack = (TextView) findViewById(R.id.act_mortgage_calculator_back);
		
		rbFund = (RadioButton) findViewById(R.id.act_mortgage_cal_funds_rb);
		rbBusiness = (RadioButton) findViewById(R.id.act_mortgage_cal_business_rb);
		rbMix = (RadioButton) findViewById(R.id.act_mortgage_cal_mix_rb);
		
		edtTotalMoney = (EditText) findViewById(R.id.act_mortgage_cal_total_money);
		
		lltMortgageType = (LinearLayout) findViewById(R.id.act_mortgage_type_llt);
		tvMortgageType = (TextView) findViewById(R.id.act_mortgage_type_tv);
		
		lltMortgageYears = (LinearLayout) findViewById(R.id.act_mortgage_years_llt);
		tvMortgageYears = (TextView) findViewById(R.id.act_mortgage_years_tv);
		tvCalculate = (TextView) findViewById(R.id.act_mortgage_calculate_tv);
		
		lltFundsResult = (LinearLayout) findViewById(R.id.act_mortgage_calculate_funds_result_llt);
		tvFundsTotalLoan = (TextView) findViewById(R.id.view_mortgage_funds_loan_total);
		tvFundsTotalInterests = (TextView) findViewById(R.id.view_mortgage_funds_loan_interests);
		tvFundsTotalPay = (TextView) findViewById(R.id.view_mortgage_funds_loan_total_pay);
		tvFundsMonthPay = (TextView) findViewById(R.id.view_mortgage_funds_loan_month_pay);
		tvFundsMonths = (TextView) findViewById(R.id.view_mortgage_funds_loan_months);
		
		tvBack.setOnClickListener(this);
		rbFund.setOnCheckedChangeListener(this);
		rbBusiness.setOnCheckedChangeListener(this);
		rbMix.setOnCheckedChangeListener(this);
		lltMortgageType.setOnClickListener(this);
		lltMortgageYears.setOnClickListener(this);
		tvCalculate.setOnClickListener(this);
		
		onMortgageTypeSelectListener = new OnMortgageTypeSelectListener() {
			
			@Override
			public void onMortgageTypeSelect(String typeName, int typeId) {
				mortgageType = typeId;
				tvMortgageType.setText(typeName);
			}
		};
		
		onMortgageYearSelectListener = new OnMortgageYearSelectListener() {
			
			@Override
			public void onMortgageYearSelect(int index) {
				String text = years[index];
				tvMortgageYears.setText(text);
				indexYear= index;
                String year = text.substring(0, text.indexOf("年"));
                mortgageMonths = Integer.valueOf(year) * 12;
			}
		};
	}
	
	private void initData() {
		years = getResources().getStringArray(R.array.loan_years_array);
		tvMortgageYears.setText(years[indexYear]);
	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
		switch (v.getId()) {
		case R.id.act_mortgage_calculator_back:  //  返回
			finish();
			break;
		case R.id.act_mortgage_years_llt:  //  贷款年限
			PopViewHelper.showSelectMortgageYears(this, this.getWindow().getDecorView(), years, onMortgageYearSelectListener);
			break;
		case R.id.act_mortgage_type_llt:  //  贷款方式
			PopViewHelper.showMortgageType(this, getWindow().getDecorView(), onMortgageTypeSelectListener);
			break;
		case R.id.act_mortgage_calculate_tv:  // 计算房贷
			calculateMortgage();
			break;
		
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_mortgage_cal_funds_rb:   //  公积金贷款
				mortgageCategory = 1;
				lltFundsResult.setVisibility(View.VISIBLE);
				break;
			case R.id.act_mortgage_cal_business_rb:   //  商业贷款
				mortgageCategory = 2;
				lltFundsResult.setVisibility(View.GONE);
				break;
			case R.id.act_mortgage_cal_mix_rb:   //  组合贷款
				mortgageCategory = 3;
				lltFundsResult.setVisibility(View.GONE);
				break;
			}
		}
	}
	
	private void calculateMortgage() {
		switch (mortgageCategory) {
		case 1:  // 公积金
			calculateFundMortgage();
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}
	
	/**
	 * 计算公积金贷款
	 */
	private void calculateFundMortgage() {
		String loanFundsTotalStr = edtTotalMoney.getText().toString();
		if (TextUtils.isEmpty(loanFundsTotalStr)) {
			return;
		}
		double loanFundsTotal = Double.valueOf(loanFundsTotalStr) * 10000;
		tvFundsTotalLoan.setText(loanFundsTotal + "元");
		tvFundsMonths.setText(mortgageMonths + "月");
		switch (mortgageType) {
		case OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENJIN:  // 等额本金
			double monthFundsPrincipal = loanFundsTotal / mortgageMonths; // 公积金每月还的本金
	        double fundsTotalInterest = 0;  // 公积金总利息
	        for (int i = 0; i < mortgageMonths; i++) {
	        	double fundsInterest = (loanFundsTotal - monthFundsPrincipal * i) * fundsRate;
                fundsTotalInterest += fundsInterest;
	        }
	        double totalPay = fundsTotalInterest + loanFundsTotal;
	        String tempTotalPay = df.format(totalPay);
	        tvFundsTotalInterests.setText(df.format(fundsTotalInterest) + "元");
	        tvFundsTotalPay.setText(tempTotalPay + "元");
	        
	        //  等额本金每月还款不一样，暂时算平均值
	        double monthFundsPayTemp = totalPay / mortgageMonths;
	        tvFundsMonthPay.setText(df.format(monthFundsPayTemp) + "元");
	        
			break;
		case OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENXI:  // 等额本息
			double monthFundsPay = calculateMonthBenxi(loanFundsTotal, mortgageMonths,
                    fundsRate);
			totalPay = monthFundsPay * mortgageMonths;
            double totalInterest = totalPay - loanFundsTotal;
            String tempMonthFundsPay = df.format(monthFundsPay);
            tvFundsMonthPay.setText(tempMonthFundsPay + "元");
            String tempTotalPay1 = df.format(totalPay);
            tvFundsTotalPay.setText(tempTotalPay1 + "元");
            String tempTotalInterest1 = df.format(totalInterest);
            tvFundsTotalInterests.setText(tempTotalInterest1 + "元");
			break;
		}
		
	}
	
	private double calculateMonthBenxi(double loanTotal, int months, double rate) {
        return (double) (loanTotal * rate * StrictMath.pow(1.0 + rate, months) / (StrictMath.pow(
                1.0 + rate, months) - 1));
    }
	
}
