package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
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
 * 找房神器具体筛选条件
 * @author lzd
 *
 */
public class SearchHouseArtifactRequirementActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
	
	/**
	 * 付款方式一次性或者按揭（Type,值为1或者0）
	 */
	public static final String INTENT_PAY_TYPE = "intent_pay_type";
	
	public static final String SalePriceRange = "SalePriceRange";
	
	public static final String EstateLabel = "EstateLabel";
	
	private TextView tvBack, tvCommit, tvBudget, tvWhere, tvMonthlyPay;
	
	private LinearLayout lltBudget, lltWhere, lltMonthlyPay;
	private GridView gridView;
	
	private RadioButton rbOneRoom, rbTwoRooms, rbThreeRooms, rbFourRooms;
	private CheckBox cbxSubwayHouse, cbxSchoolHouse, cbxElectricHouse, cbxCarSpaceHouse;
	
	private int payType = -1;
	
	/**
	 * 几居    属于几房House(值为1，2，3，4，四房以上)
	 */
	private int rooms = 1;
	
	/**
	 * 补充信息   多个用逗号隔开（，）
	 */
	private String label;
	
	/**
     * 总价
     */
    private ArrayList<TextValueBean> salePriceRanges = new ArrayList<TextValueBean>();
    
    /**
     * 区域
     */
    private ArrayList<TextValueBean> areas = new ArrayList<TextValueBean>();
    
    /**
     * 月供
     */
    private ArrayList<TextValueBean> monthlyPay = new ArrayList<TextValueBean>();
    
    /**
     * 特色标签
     */
    private ArrayList<TextValueBean> estateLabels = new ArrayList<TextValueBean>();
    
    /**
     * 总价监听
     */
    private OnConditionSelectListener onTotalPriceSelectListener;
    
    private TextValueBean totalPriceCondition;
	
    /**
     * 区域监听
     */
    private OnConditionSelectListener onAreaSelectListener;
    
    private TextValueBean areaCondition;
    
    /**
     * 区域监听
     */
    private OnConditionSelectListener onMonthlyPaySelectListener;
    
    private TextValueBean monthlyPayCondition;
    
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		payType = getIntent().getIntExtra(INTENT_PAY_TYPE, -1);
		initView();
		getConditionList(SalePriceRange);
		getConditionList(EstateLabel);
		getAreaList();
		getMonthlyPayData();
	}
	
	private void initView() {
		setContentView(R.layout.act_search_house_artifact_requirement);
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact_requirement_back);
		
		lltBudget = (LinearLayout) findViewById(R.id.act_search_house_artifact_budget_llt);
		lltWhere = (LinearLayout) findViewById(R.id.act_search_house_artifact_where_llt);
		lltMonthlyPay = (LinearLayout) findViewById(R.id.act_search_house_month_pay_llt);
		
		tvBudget = (TextView) findViewById(R.id.act_search_house_artifact_budget_tv);
		tvWhere = (TextView) findViewById(R.id.act_search_house_artifact_wheree_tv);
		tvMonthlyPay = (TextView) findViewById(R.id.act_search_house_month_pay_tv);
		
		gridView = (GridView) findViewById(R.id.act_search_house_additional_info);
		
		rbOneRoom = (RadioButton) findViewById(R.id.rb_one_room);
		rbTwoRooms = (RadioButton) findViewById(R.id.rb_two_rooms);
		rbThreeRooms = (RadioButton) findViewById(R.id.rb_three_rooms);
		rbFourRooms = (RadioButton) findViewById(R.id.rb_four_rooms);
		
		cbxSubwayHouse = (CheckBox) findViewById(R.id.rb_subway_house);
		cbxSchoolHouse = (CheckBox) findViewById(R.id.rb_school_house);
		cbxElectricHouse = (CheckBox) findViewById(R.id.rb_electric_house);
		cbxCarSpaceHouse = (CheckBox) findViewById(R.id.rb_car_space_house);
		
		tvCommit = (TextView) findViewById(R.id.act_search_house_artifact_requirement_commit_tv);
		
		tvBack.setOnClickListener(this);
		
		lltBudget.setOnClickListener(this);
		lltWhere.setOnClickListener(this);
		lltMonthlyPay.setOnClickListener(this);
		
		rbOneRoom.setOnCheckedChangeListener(this);
		rbTwoRooms.setOnCheckedChangeListener(this);
		rbThreeRooms.setOnCheckedChangeListener(this);
		rbFourRooms.setOnCheckedChangeListener(this);
		
		cbxSubwayHouse.setOnCheckedChangeListener(this);
		cbxSchoolHouse.setOnCheckedChangeListener(this);
		cbxElectricHouse.setOnCheckedChangeListener(this);
		cbxCarSpaceHouse.setOnCheckedChangeListener(this);
		
		tvCommit.setOnClickListener(this);
		
        onTotalPriceSelectListener = new OnConditionSelectListener() {
            
            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                totalPriceCondition = txtValueBean;
                tvBudget.setText(txtValueBean.getText());
            }
        };
        
        onAreaSelectListener = new OnConditionSelectListener() {
            
            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                
                if (areaCondition == null || areaCondition.getValue() == null || !areaCondition.getValue().equals(txtValueBean.getValue())) {
                    areaCondition = txtValueBean;
                    tvWhere.setText(txtValueBean.getText());
                    
                }
            }
        };
        
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
		case R.id.act_search_house_artifact_requirement_back:  // 返回
			finish();
			break;
			
		case R.id.act_search_house_artifact_requirement_commit_tv:  //  提交
		    Jumper jumper = Jumper.newJumper();
			
		    jumper = jumper.setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .putInt(SearchHouseArtifactResultActivity.INTENT_PAY_TYPE, payType)
	        .putString(SearchHouseArtifactResultActivity.INTENT_BUDGET, totalPriceCondition.getValue())
	        .putString(SearchHouseArtifactResultActivity.INTENT_WHERE, areaCondition.getValue())
	        .putString(SearchHouseArtifactResultActivity.INTENT_MONTHLY_PAY, monthlyPayCondition.getValue())
	        .putInt(SearchHouseArtifactResultActivity.INTENT_ROOMS, rooms)
			.putString(SearchHouseArtifactResultActivity.INTENT_REMARKS, "");
		    jumper.jump(this, SearchHouseArtifactResultActivity.class);
			break;
		case R.id.act_search_house_artifact_budget_llt:   //  预算
		    PopViewHelper.showSelectTotalPricePopWindow(this, lltBudget, salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.act_search_house_artifact_where_llt:   //  买在哪
		    PopViewHelper.showSelectAreaPopWindow(this, lltWhere, areas, onAreaSelectListener);
			break;
		case R.id.act_search_house_month_pay_llt:   //  月供范围
		    PopViewHelper.showSelectAreaPopWindow(this, lltMonthlyPay, monthlyPay, onMonthlyPaySelectListener);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.rb_one_room:  // 一房
				rooms = 1;
				break;
			case R.id.rb_two_rooms:  // 二房
				rooms = 2;
				break;
			case R.id.rb_three_rooms:  // 三房
				rooms = 3;
				break;
			case R.id.rb_four_rooms:  // 四房
				rooms = 4;
				break;
			case R.id.rb_subway_house:  // 地铁房
				break;
			case R.id.rb_school_house:  // 学区房
				break;
			case R.id.rb_electric_house:  // 电梯房
				break;
			case R.id.rb_car_space_house:  // 带车位
				break;
			}
		}
	}
	
	public void getConditionList(final String conditionName) {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getConditionByName(conditionName, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON.parseArray(result.getData(), TextValueBean.class);
                if (SalePriceRange.equals(conditionName)) {
                    salePriceRanges.addAll(temp);
                } else if (EstateLabel.equals(conditionName)) {
                    estateLabels.addAll(temp);
                }
            }
        });
    }
	
	private void getAreaList() {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        CityBean cityBean = ContentUtils.getCityBean(this);
        if (cityBean == null) {
            ToastUtils.SHORT.toast(this, "请选择城市");
            return;
        }
        actionImpl.getAreaList(cityBean.getSiteId(), new ResultHandlerCallback() {
            
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
                areas.addAll(temp);
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
