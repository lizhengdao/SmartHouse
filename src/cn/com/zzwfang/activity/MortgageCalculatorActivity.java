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
	
	private LinearLayout lltFundsBuness, lltMix;
	
	private TextView tvFundsTotalLoan, tvFundsTotalInterests, tvFundsTotalPay, tvFundsMonthPay, tvFundsMonths;
	
	private LinearLayout lltFundsResult, lltBusinessResult, lltMixResult;
	
	private TextView tvBusinessTotalLoan, tvBusinessTotalInterests, tvBusinessTotalPay, tvBusinessMonthPay, tvBusinessMonths;
	
	private RadioButton rbFund, rbBusiness, rbMix;
	
	private EditText edtTotalMoney;
	
	private LinearLayout lltMortgageType, lltMortgageYears;
	
	private EditText edtMixFundsMoney, edtMixBusinessMoney;
	
	private LinearLayout lltMortgageMixYears, lltMortgageMixType;
	
	private TextView tvMixYears, tvMixType;
	
	private TextView tvMixTotalLoan, tvMixTotalInterests, tvMixTotalPay, tvMixMonthPay, tvMixMonths;
	
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
		
		lltFundsBuness = (LinearLayout) findViewById(R.id.act_mortgage_funds_business);
		
		lltFundsResult = (LinearLayout) findViewById(R.id.act_mortgage_calculate_funds_result_llt);
		tvFundsTotalLoan = (TextView) findViewById(R.id.view_mortgage_funds_loan_total);
		tvFundsTotalInterests = (TextView) findViewById(R.id.view_mortgage_funds_loan_interests);
		tvFundsTotalPay = (TextView) findViewById(R.id.view_mortgage_funds_loan_total_pay);
		tvFundsMonthPay = (TextView) findViewById(R.id.view_mortgage_funds_loan_month_pay);
		tvFundsMonths = (TextView) findViewById(R.id.view_mortgage_funds_loan_months);
		
		lltBusinessResult = (LinearLayout) findViewById(R.id.act_mortgage_calculate_business_result_llt);
		tvBusinessTotalLoan = (TextView) findViewById(R.id.view_mortgage_business_loan_total);
		tvBusinessTotalInterests = (TextView) findViewById(R.id.view_mortgage_business_loan_interests);
		tvBusinessTotalPay = (TextView) findViewById(R.id.view_mortgage_business_loan_total_pay);
		tvBusinessMonthPay = (TextView) findViewById(R.id.view_mortgage_business_loan_month_pay);
		tvBusinessMonths = (TextView) findViewById(R.id.view_mortgage_business_loan_months);
		
		lltMix = (LinearLayout) findViewById(R.id.act_mortgage_mix);
		
		edtMixFundsMoney = (EditText) findViewById(R.id.act_mortgage_cal_mix_funds_money);
		edtMixBusinessMoney = (EditText) findViewById(R.id.act_mortgage_cal_mix_business_money);
		lltMortgageMixYears = (LinearLayout) findViewById(R.id.act_mortgage_mix_years_llt);
		lltMortgageMixType = (LinearLayout) findViewById(R.id.act_mortgage_mix_type_llt);
		tvMixYears = (TextView) findViewById(R.id.act_mortgage_mix_years_tv);
		tvMixType = (TextView) findViewById(R.id.act_mortgage_mix_type_tv);
		
		lltMixResult = (LinearLayout) findViewById(R.id.act_mortgage_calculate_mix_result_llt);
		tvMixTotalLoan = (TextView) findViewById(R.id.view_mortgage_mix_loan_total);
		tvMixTotalInterests = (TextView) findViewById(R.id.view_mortgage_mix_loan_interests);
		tvMixTotalPay = (TextView) findViewById(R.id.view_mortgage_mix_loan_total_pay);
		tvMixMonthPay = (TextView) findViewById(R.id.view_mortgage_mix_loan_month_pay);
		tvMixMonths = (TextView) findViewById(R.id.view_mortgage_mix_loan_months);
		
		tvBack.setOnClickListener(this);
		rbFund.setOnCheckedChangeListener(this);
		rbBusiness.setOnCheckedChangeListener(this);
		rbMix.setOnCheckedChangeListener(this);
		lltMortgageType.setOnClickListener(this);
		lltMortgageYears.setOnClickListener(this);
		tvCalculate.setOnClickListener(this);
		
		lltMortgageMixYears.setOnClickListener(this);
		lltMortgageMixType.setOnClickListener(this);
		
		onMortgageTypeSelectListener = new OnMortgageTypeSelectListener() {
			
			@Override
			public void onMortgageTypeSelect(String typeName, int typeId) {
				mortgageType = typeId;
				tvMortgageType.setText(typeName);
				tvMixType.setText(typeName);
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
                tvMixYears.setText(text);
			}
		};
	}
	
	private void initData() {
		years = getResources().getStringArray(R.array.loan_years_array);
		tvMortgageYears.setText(years[indexYear]);
		tvMixYears.setText(years[indexYear]);
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
		case R.id.act_mortgage_mix_years_llt:   // 混合贷款年限
			PopViewHelper.showSelectMortgageYears(this, this.getWindow().getDecorView(), years, onMortgageYearSelectListener);
			break;
		case R.id.act_mortgage_mix_type_llt:  //  混合贷款方式
			PopViewHelper.showMortgageType(this, getWindow().getDecorView(), onMortgageTypeSelectListener);
			break;
		
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_mortgage_cal_funds_rb:   //  公积金贷款
				mortgageCategory = 1;
				lltFundsBuness.setVisibility(View.VISIBLE);
				lltFundsResult.setVisibility(View.VISIBLE);
				lltBusinessResult.setVisibility(View.GONE);
				lltMix.setVisibility(View.GONE);
				lltMixResult.setVisibility(View.GONE);
				break;
			case R.id.act_mortgage_cal_business_rb:   //  商业贷款
				mortgageCategory = 2;
				lltFundsBuness.setVisibility(View.VISIBLE);
				lltFundsResult.setVisibility(View.GONE);
				lltBusinessResult.setVisibility(View.VISIBLE);
				lltMix.setVisibility(View.GONE);
				lltMixResult.setVisibility(View.GONE);
				break;
			case R.id.act_mortgage_cal_mix_rb:   //  组合贷款
				mortgageCategory = 3;
				lltFundsBuness.setVisibility(View.GONE);
				lltFundsResult.setVisibility(View.GONE);
				lltBusinessResult.setVisibility(View.GONE);
				lltMix.setVisibility(View.VISIBLE);
				lltMixResult.setVisibility(View.VISIBLE);
				break;
			}
		}
	}
	
	private void calculateMortgage() {
		switch (mortgageCategory) {
		case 1:  // 公积金
			calculateFundMortgage();
			break;
		case 2:  // 商贷
			calculateBusinessMortgage();
			break;
		case 3:
			calculateFundsBusinessMixMortgage();
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
	
	/**
	 * 计算商业贷款
	 */
	private void calculateBusinessMortgage() {
		String loanBusinessTotalStr = edtTotalMoney.getText().toString();
		if (TextUtils.isEmpty(loanBusinessTotalStr)) {
			return;
		}
		
		double loanBusinessTotal = Double.valueOf(loanBusinessTotalStr) * 10000;
		tvBusinessTotalLoan.setText(loanBusinessTotal + "元");
		tvBusinessMonths.setText(mortgageMonths + "月");
		
		switch (mortgageType) {
		case OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENJIN:  // 等额本金
			double monthBusinessPrincipal = loanBusinessTotal / mortgageMonths; // 商贷每月还的本金
	        double businessTotalInterest = 0;  // 商贷总利息
	        for (int i = 0; i < mortgageMonths; i++) {
	        	double bInterest = (loanBusinessTotal - monthBusinessPrincipal * i) * fundsRate;
	        	businessTotalInterest += bInterest;
	        }
	        double totalPay = businessTotalInterest + loanBusinessTotal;
	        String tempTotalPay = df.format(totalPay);
	        tvBusinessTotalInterests.setText(df.format(businessTotalInterest) + "元");
	        tvBusinessTotalPay.setText(tempTotalPay + "元");
	        
	        //  等额本金每月还款不一样，暂时算平均值
	        double monthFundsPayTemp = totalPay / mortgageMonths;
	        tvBusinessMonthPay.setText(df.format(monthFundsPayTemp) + "元");
	        
			break;
		case OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENXI:  // 等额本息
			
			double monthBusinessPay = calculateMonthBenxi(loanBusinessTotal, mortgageMonths,
                    businessRate);
            totalPay = monthBusinessPay * mortgageMonths;
            double totalInterest = totalPay - loanBusinessTotal;
            String tempMonthBusinessPay = df.format(monthBusinessPay);
            tvBusinessMonthPay.setText(tempMonthBusinessPay + "元");
            String tempTotalPay1 = df.format(totalPay);
            tvBusinessTotalPay.setText(tempTotalPay1 + "元");
            String tempTotalInterest = df.format(totalInterest);
            tvBusinessTotalInterests.setText(tempTotalInterest + "元");
			break;
		}
	}
	
	/**
	 * 计算混合贷款
	 */
	private void calculateFundsBusinessMixMortgage() {
		String loanFundsTotalStr = edtMixFundsMoney.getText().toString();
		if (TextUtils.isEmpty(loanFundsTotalStr)) {
			return;
		}
		
		String loanBusinessTotalStr = edtMixBusinessMoney.getText().toString();
		if (TextUtils.isEmpty(loanBusinessTotalStr)) {
			return;
		}
		
		double loanFundsTotal = Double.valueOf(loanFundsTotalStr) * 10000;
		double loanBusinessTotal = Double.valueOf(loanBusinessTotalStr) * 10000;
		
		
		tvMixTotalLoan.setText((loanFundsTotal + loanBusinessTotal) + "元");
		tvMixMonths.setText(mortgageMonths + "月");
		
		switch (mortgageType) {
		case OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENJIN:  // 等额本金:
			
			double monthFundsPrincipal = loanFundsTotal / mortgageMonths; // 公积金每月还的本金
	        double fundsTotalInterest = 0;
			
			double monthBusinessPrincipal = loanBusinessTotal / mortgageMonths; // 商贷每月还的本金
	        double businessTotalInterest = 0;

	        if (loanBusinessTotal > 0 || loanFundsTotal > 0) {
	            for (int i = 0; i < mortgageMonths; i++) {
	            	
	            	double fundsInterest = (loanFundsTotal - monthFundsPrincipal * i) * fundsRate;
	                fundsTotalInterest += fundsInterest;
	            	
	                double bInterest = (loanBusinessTotal - monthBusinessPrincipal * i) * businessRate;
	                businessTotalInterest += bInterest;

	                double monthPay = monthBusinessPrincipal + monthFundsPrincipal + bInterest
	                        + fundsInterest;

//	                String temp = df.format(monthPay);
//	                payPerMonth.add(temp + "元");
	            }
	        }

	        double totalPay = businessTotalInterest + loanBusinessTotal + fundsTotalInterest
	                + loanFundsTotal;
	        String tempTotalPay = df.format(totalPay);
	        tvMixTotalPay.setText(tempTotalPay + "元");

	        double totalInterest = businessTotalInterest + fundsTotalInterest;
	        String tempTotalInterest = df.format(totalInterest);
	        tvMixTotalInterests.setText(tempTotalInterest + "元");
			
			break;
		case OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENXI:  // 等额本息
			
			double monthBusinessMixPay = calculateMonthBenxi(loanBusinessTotal, mortgageMonths,
                    businessRate);
            double monthFundsMixPay = calculateMonthBenxi(loanFundsTotal, mortgageMonths,
                    fundsRate);
            double totalMonth = monthBusinessMixPay + monthFundsMixPay;
            double totalMixPay = totalMonth * mortgageMonths;
            totalInterest = totalMixPay - loanBusinessTotal - loanFundsTotal;
            String tempTotalMonth = df.format(totalMonth);
            tvMixMonthPay.setText(tempTotalMonth + "元");
            String tempTotalMixPay = df.format(totalMixPay);
            tvMixTotalPay.setText(tempTotalMixPay + "元");
            String tempTotalInterest2 = df.format(totalInterest);
            tvMixTotalInterests.setText(tempTotalInterest2 + "元");
			break;
		}
		
	}
	
	
	
	
	private double calculateMonthBenxi(double loanTotal, int months, double rate) {
        return (double) (loanTotal * rate * StrictMath.pow(1.0 + rate, months) / (StrictMath.pow(
                1.0 + rate, months) - 1));
    }

	
}
