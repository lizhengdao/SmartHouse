package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
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

import com.alibaba.fastjson.JSON;

/**
 * 找房神器具体筛选条件
 * @author lzd
 *
 */
public class SearchHouseArtifactRequirementActivity extends BaseActivity implements OnClickListener {
	
	/**
	 * 付款方式一次性1或者按揭0（Type,值为1或者0）
	 */
	public static final String INTENT_PAY_TYPE = "intent_pay_type";
	
	public static final String SalePriceRange = "SalePriceRange";
	
	/**
	 * 补充信息
	 */
	public static final String EstateLabel = "EstateLabel";
	/**
	 * 租房价格区间
	 */
	public static final String RentPriceRange = "RentPriceRange"; // 租金区间
	
	/**
	 * Excalibur/MatchingList 这个接口新增参数：Trade    0是买房那个  1是租房
	 */
	public static final String INTENT_SEARCH_HOUSE_TRADE_TYPE = "intent_search_house_trade_type";
	
	private TextView tvBack, tvCommit, tvBudget, tvWhere, tvMonthlyPay;
	private TextView tvPromptWhere, tvPromptHowManyRooms;
	private LinearLayout lltAdditionalInfoContainer;
	
	private LinearLayout lltBudget, lltWhere, lltMonthlyPay;
	private GridView  gridViewHouseRooms;
	private GridView gridViewAdditionalInfo;
	private SearchHouseArtifactRequirementAdapter adapter;
	
	private int payType = -1;
	
	/**
	 * 几居 
	 */
	private TextValueBean tvHouseRooms;
	
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
     * 特色标签(补充信息)
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
    
    private ArrayList<TextValueBean> houseRooms = new ArrayList<TextValueBean>();
    private SearchHouseArtifactRequirementAdapter houseRoomAdapter;
    private int tradeType = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		payType = intent.getIntExtra(INTENT_PAY_TYPE, -1);
		tradeType = intent.getIntExtra(INTENT_SEARCH_HOUSE_TRADE_TYPE, 0);
		initView();
		
		if (payType == 1) {
		    lltMonthlyPay.setVisibility(View.GONE);
		}
		
		if (tradeType == 0) {  // 0是买房那个 
			getConditionList(SalePriceRange);
			getConditionList(EstateLabel);
			getMonthlyPayData();
		}
		getAreaList();
		getHouseRoomsData();
		
		if (tradeType == 1) {  // 1是租房
			getConditionList(RentPriceRange);
		}
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
		
		tvPromptWhere = (TextView) findViewById(R.id.act_search_house_artifact_wheree_prompt_tv);
		tvPromptHowManyRooms = (TextView) findViewById(R.id.act_search_house_artifact_how_many_rooms_prompt_tv);
		lltAdditionalInfoContainer = (LinearLayout) findViewById(R.id.act_search_house_additional_info_container);
		
		gridViewHouseRooms = (GridView) findViewById(R.id.act_search_house_rooms);
		houseRoomAdapter = new SearchHouseArtifactRequirementAdapter(this, houseRooms);
		gridViewHouseRooms.setAdapter(houseRoomAdapter);
		
		gridViewAdditionalInfo = (GridView) findViewById(R.id.act_search_house_additional_info);
		adapter = new SearchHouseArtifactRequirementAdapter(this, estateLabels);
		gridViewAdditionalInfo.setAdapter(adapter);
		
		if (tradeType == 1) {
			tvPromptWhere.setText("您想在哪里租");
			tvPromptHowManyRooms.setText("您想租几居");
			lltAdditionalInfoContainer.setVisibility(View.GONE);
			lltMonthlyPay.setVisibility(View.GONE);
		}
		
		tvCommit = (TextView) findViewById(R.id.act_search_house_artifact_requirement_commit_tv);
		
		tvBack.setOnClickListener(this);
		
		lltBudget.setOnClickListener(this);
		lltWhere.setOnClickListener(this);
		lltMonthlyPay.setOnClickListener(this);
		
		
		
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
        
        gridViewHouseRooms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int size = houseRooms.size();
				for (int i = 0; i < size; i++) {
					TextValueBean temp = houseRooms.get(i);
					if (position == i) {
						temp.setSelected(!temp.isSelected());
					} else {
						temp.setSelected(false);
					}
				}
				
				houseRoomAdapter.notifyDataSetChanged();
			}
		});
        
        gridViewAdditionalInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextValueBean temp = estateLabels.get(position);
				temp.setSelected(!temp.isSelected());
				adapter.notifyDataSetChanged();
			}
		});
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
	        .putInt(SearchHouseArtifactResultActivity.INTENT_SEARCH_HOUSE_TRADE_TYPE, tradeType)
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
	        
	        for (TextValueBean temp : houseRooms) {
	        	if (temp.isSelected()) {
	        		tvHouseRooms = temp;
	        		break;
	        	}
	        }
	        
	        if (tvHouseRooms != null) {
	        	jumper = jumper.putString(SearchHouseArtifactResultActivity.INTENT_ROOMS, tvHouseRooms.getValue());
	        }
	         
	        jumper.putString(SearchHouseArtifactResultActivity.INTENT_REMARKS, additionalInfo);
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
                if (SalePriceRange.equals(conditionName) || RentPriceRange.equals(conditionName)) {
                    salePriceRanges.addAll(temp);
                } else if (EstateLabel.equals(conditionName)) {
                    estateLabels.addAll(temp);
                    adapter.notifyDataSetChanged();
                    setGridHeight(gridViewAdditionalInfo);
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
	
	private void getHouseRoomsData() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSearchHouseArtifactHouseType(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON.parseArray(result.getData(), TextValueBean.class);
				houseRooms.addAll(temp);
				houseRoomAdapter.notifyDataSetChanged();
				setGridHeight(gridViewHouseRooms);
			}
		});
	}
	
	private void setGridHeight(GridView gridView) {
		
		int count = gridView.getAdapter().getCount();
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
