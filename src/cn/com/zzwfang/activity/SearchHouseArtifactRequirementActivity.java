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
import cn.com.zzwfang.adapter.SearchHouseArtifactRequirementAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.DevUtils;
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
	
	private LinearLayout lltBudget, lltWhere, lltMonthlyPay, lltGridContainer;
	private GridView gridView;
	private SearchHouseArtifactRequirementAdapter adapter;
	
	private RadioButton rbOneRoom, rbTwoRooms, rbThreeRooms, rbFourRooms;
	
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
		lltGridContainer = (LinearLayout) findViewById(R.id.act_search_house_additional_info_grid_container);
		
		tvBudget = (TextView) findViewById(R.id.act_search_house_artifact_budget_tv);
		tvWhere = (TextView) findViewById(R.id.act_search_house_artifact_wheree_tv);
		tvMonthlyPay = (TextView) findViewById(R.id.act_search_house_month_pay_tv);
		
		gridView = (GridView) findViewById(R.id.act_search_house_additional_info);
		adapter = new SearchHouseArtifactRequirementAdapter(this, estateLabels);
		gridView.setAdapter(adapter);
		
		rbOneRoom = (RadioButton) findViewById(R.id.rb_one_room);
		rbTwoRooms = (RadioButton) findViewById(R.id.rb_two_rooms);
		rbThreeRooms = (RadioButton) findViewById(R.id.rb_three_rooms);
		rbFourRooms = (RadioButton) findViewById(R.id.rb_four_rooms);
		
		
		tvCommit = (TextView) findViewById(R.id.act_search_house_artifact_requirement_commit_tv);
		
		tvBack.setOnClickListener(this);
		
		lltBudget.setOnClickListener(this);
		lltWhere.setOnClickListener(this);
		lltMonthlyPay.setOnClickListener(this);
		
		rbOneRoom.setOnCheckedChangeListener(this);
		rbTwoRooms.setOnCheckedChangeListener(this);
		rbThreeRooms.setOnCheckedChangeListener(this);
		rbFourRooms.setOnCheckedChangeListener(this);
		
		
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
		    String additionalInfo = "";
		    for (TextValueBean temp : estateLabels) {
		    	if (temp.isSelected()) {
		    		additionalInfo += (temp.getValue() + ",");
		    	}
		    }
		    
		    if (additionalInfo.endsWith(",")) {
		    	additionalInfo = additionalInfo.substring(0, additionalInfo.length() -1);
		    }
			
		    jumper = jumper.setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .putInt(SearchHouseArtifactResultActivity.INTENT_PAY_TYPE, payType);
		    
		    if (totalPriceCondition != null) {
		    	jumper = jumper.putString(SearchHouseArtifactResultActivity.INTENT_BUDGET, totalPriceCondition.getValue());
		    }
		    if (areaCondition != null) {
		    	jumper = jumper.putString(SearchHouseArtifactResultActivity.INTENT_WHERE, areaCondition.getValue());
		    }
	        if (monthlyPayCondition != null) {
	        	jumper = jumper.putString(SearchHouseArtifactResultActivity.INTENT_MONTHLY_PAY, monthlyPayCondition.getValue());
	        }
	        
	        jumper.putInt(SearchHouseArtifactResultActivity.INTENT_ROOMS, rooms)
			.putString(SearchHouseArtifactResultActivity.INTENT_REMARKS, additionalInfo);
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
                    adapter.notifyDataSetChanged();
                    setGridHeight(gridView);
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
	
	private void setGridHeight(GridView gridView) {
		
		int count = adapter.getCount();
		int column = 4;
        int displayRowNum = 0;
        int tmp = count % column;
        displayRowNum = tmp > 0 ? count/column + 1  : count / column ;

       //使用此方法获取GridView Item 的高度，Adapte的布局必须使用LinearLayout，但是使用此方法获取的Item的宽度达不到预期效果
       int gridHeight = 0;
       if (gridView.getAdapter().getCount() > 0) {
           View view = gridView.getAdapter().getView(gridView.getFirstVisiblePosition(), null, gridView);
           view.measure(0, 0);
           int itemHeight = view.getMeasuredHeight();
           gridHeight = (int)((itemHeight) * displayRowNum + DevUtils.getScreenTools(this).dip2px(10) * (displayRowNum - 1));
       }

       LinearLayout.LayoutParams lpGrid = (LinearLayout.LayoutParams)gridView.getLayoutParams();
       lpGrid.height = gridHeight;

  }
}
