package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.adapter.RentHouseAdapter;
import cn.com.zzwfang.bean.RentHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnRentHouseMoreConditionListener;

import com.alibaba.fastjson.JSON;

/**
 * 租房列表页
 * 获取租房列表，输入
 * 城市ID、
 * 区域ID、
 * 租金范围、
 * 面积范围、
 * 排序（0：默认 1：租金低到高 2：租金高到低）、
 * 分页大小、页码。
 * 
 * 租金区间
 * 面积范围
 * 排序
 * 朝向
 * 房型
 * 
 * @author lzd
 *
 */
public class RentHouseActivity extends BaseActivity implements OnClickListener,
        OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

	public static final String INTENT_KEY_WORDS = "RentHouseActivity.intent_keywords";
    private TextView tvBack;

    private LinearLayout lltArea, lltRentPrice, lltHouseType, lltMore;
    private TextView tvArea, tvRentPrice, tvHouseType;

    private AbPullToRefreshView pullView;
    private ListView lstRentHouseView;
    private RentHouseAdapter adapter;

//    public static final String SalePriceRange = "SalePriceRange";
    public static final String HouseType = "HouseType";  //  房型
//    public static final String PrpUsage = "PrpUsage";
//    public static final String EstateLabel = "EstateLabel";
//    public static final String EstateStatus = "EstateStatus";
//    public static final String FloorRange = "FloorRange";
    public static final String RentPriceRange = "RentPriceRange"; // 租金区间
    
    //  TODO 面积范围  的字符串还需要问问
    public static final String SquareRange = "SquareRange";  // 面积范围
    public static final String Direction = "Direction";  //  朝向
//    public static final String Sort = "Sort";  // 排序 写死

    private String cityId = "";

    private ArrayList<RentHouseBean> rentHouses = new ArrayList<RentHouseBean>();

    // 区域
    private ArrayList<TextValueBean> areas = new ArrayList<TextValueBean>();
    // 总价
//    private ArrayList<TextValueBean> salePriceRanges = new ArrayList<TextValueBean>();
    // 户型
    private ArrayList<TextValueBean> houseTypes = new ArrayList<TextValueBean>();
    // 物业类型
//    private ArrayList<TextValueBean> prpUsages = new ArrayList<TextValueBean>();

    // 特色标签
//    private ArrayList<TextValueBean> estateLabels = new ArrayList<TextValueBean>();
    //  售卖状态
//    private ArrayList<TextValueBean> estateStatus = new ArrayList<TextValueBean>();
    //  楼层范围
//    private ArrayList<TextValueBean> floorRanges = new ArrayList<TextValueBean>();

    //  租价范围
    private ArrayList<TextValueBean> rentPriceRanges = new ArrayList<TextValueBean>();
    // 朝向
    private ArrayList<TextValueBean> directions = new ArrayList<TextValueBean>();
    // 面积
    private ArrayList<TextValueBean> squareRanges = new ArrayList<TextValueBean>();
    //  排序
    private ArrayList<TextValueBean> sorts = new ArrayList<TextValueBean>();

    //  区域监听
    private OnConditionSelectListener onAreaSelectListener;
    //  租金范围监听
    private OnConditionSelectListener onRentPriceSelectListener;
    //  房型监听
    private OnConditionSelectListener onHouseTypeSelectListener;

    private ArrayList<String> moreType = new ArrayList<String>();
    private OnRentHouseMoreConditionListener onRentHouseMoreConditionListener;

    private TextValueBean areaCondition;
    private TextValueBean rentPriceCondition;
    private TextValueBean squareCondition;
//    private TextValueBean labelCondition;
    private TextValueBean roomTypeCondition;
    private String sort, keyWords, direction;
    private int pageIndex = 0;
    private int pageTotal = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_rent_house);
        cityId = getIntent().getStringExtra(
                HomeRecommendHouseAdapter.INTENT_CITY_ID);
        initView();
        initData();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.act_rent_house_back);
        pullView = (AbPullToRefreshView) findViewById(R.id.pull_rent_house);
        lstRentHouseView = (ListView) findViewById(R.id.lst_rent_house);

        lltArea = (LinearLayout) findViewById(R.id.act_rent_house_area_llt);
        lltRentPrice = (LinearLayout) findViewById(R.id.act_rent_house_rent_price_llt);
        lltHouseType = (LinearLayout) findViewById(R.id.act_rent_house_type_llt);
        lltMore = (LinearLayout) findViewById(R.id.act_rent_house_more_llt);

        tvArea = (TextView) findViewById(R.id.act_rent_house_area_tv);
        tvRentPrice = (TextView) findViewById(R.id.act_rent_house_rent_price_tv);
        tvHouseType = (TextView) findViewById(R.id.act_rent_house_type_tv);

        tvBack.setOnClickListener(this);
        pullView.setPullRefreshEnable(true);
        pullView.setLoadMoreEnable(true);
        pullView.setOnHeaderRefreshListener(this);
        pullView.setOnFooterLoadListener(this);

        lltArea.setOnClickListener(this);
        lltRentPrice.setOnClickListener(this);
        lltHouseType.setOnClickListener(this);
        lltMore.setOnClickListener(this);

        adapter = new RentHouseAdapter(this, rentHouses);
        lstRentHouseView.setAdapter(adapter);
        lstRentHouseView.setOnItemClickListener(this);

        onRentPriceSelectListener = new OnConditionSelectListener() {

            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                rentPriceCondition = txtValueBean;
                tvRentPrice.setText(txtValueBean.getText());
                getRentHouseList(cityId, areaCondition, rentPriceCondition,
                        squareCondition, sort, keyWords, direction,
                        roomTypeCondition, 10, true);
            }
        };

        onHouseTypeSelectListener = new OnConditionSelectListener() {

            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                roomTypeCondition = txtValueBean;
                tvHouseType.setText(txtValueBean.getText());
                getRentHouseList(cityId, areaCondition, rentPriceCondition,
                        squareCondition, sort, keyWords, direction,
                        roomTypeCondition, 10, true);
            }
        };

        onAreaSelectListener = new OnConditionSelectListener() {

            @Override
            public void onConditionSelect(TextValueBean txtValueBean) {
                areaCondition = txtValueBean;
                tvArea.setText(txtValueBean.getText());
                getRentHouseList(cityId, areaCondition, rentPriceCondition,
                        squareCondition, sort, keyWords, direction,
                        roomTypeCondition, 10, true);
            }
        };
        
        onRentHouseMoreConditionListener = new OnRentHouseMoreConditionListener() {
			
			@Override
			public void onRentHouseMoreConditon(TextValueBean sortConditionData,
					TextValueBean squareConditionData,
					TextValueBean directionConditonData) {
				// TODO Auto-generated method stub
				if (sortConditionData != null) {
					sort = sortConditionData.getValue();
				}
				squareCondition = squareConditionData;
				if (directionConditonData != null) {
					direction = directionConditonData.getValue();
				}
				getRentHouseList(cityId, areaCondition, rentPriceCondition,
                        squareCondition, sort, keyWords, direction,
                        roomTypeCondition, 10, true);
			}
		};
    }

    private void initData() {
    	initSortsData();
        moreType.add("排序");
        moreType.add("面积范围");
        moreType.add("朝向");
        
//        moreType.add("标签");
//        moreType.add("楼层");
//        moreType.add("房源编号");
//        getConditionList(SalePriceRange);
        getConditionList(HouseType);
//        getConditionList(PrpUsage);
//        getConditionList(EstateLabel);
//        getConditionList(EstateStatus);
//        getConditionList(FloorRange);
        getConditionList(RentPriceRange);
        getConditionList(Direction);
        getConditionList(SquareRange);
        
//        getConditionList(Sort);
        getAreaList();

        getRentHouseList(cityId, areaCondition, rentPriceCondition,
                squareCondition, sort, keyWords, direction, roomTypeCondition, 10,
                true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_rent_house_back: // 返回
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView()
                    .getWindowToken(), 0);
            finish();
            break;
        case R.id.act_rent_house_area_llt: // 区域
            PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas,
                    onAreaSelectListener);
            break;
        case R.id.act_rent_house_rent_price_llt: // 租金
            PopViewHelper.showSelectRentPricePopWindow(this, lltRentPrice,
                    rentPriceRanges, onRentPriceSelectListener);
            break;
        case R.id.act_rent_house_type_llt: // 房型
            PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType,
                    houseTypes, onHouseTypeSelectListener);
            break;
        case R.id.act_rent_house_more_llt: // 更多
        	// TODO
//            PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType,
//                    sorts, directions, estateLabels, lltMore);
        	PopViewHelper.showRentHouseMorePopWindow(this, moreType, sorts,
        			squareRanges, directions, lltMore, onRentHouseMoreConditionListener);
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        RentHouseBean rentHouseBean = rentHouses.get(position);
        Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(RentHouseDetailActivity.INTENT_HOUSE_SOURCE_ID,
                        rentHouseBean.getId())
                .jump(this, RentHouseDetailActivity.class);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        if (pageIndex > pageTotal) {
            getRentHouseList(cityId, areaCondition, rentPriceCondition,
                    squareCondition, sort, keyWords, direction,
                    roomTypeCondition, 10, false);
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        getRentHouseList(cityId, areaCondition, rentPriceCondition,
                squareCondition, sort, keyWords, direction,
                roomTypeCondition, 10, true);
    }

    public void getConditionList(final String conditionName) {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getConditionByName(conditionName,
                new ResultHandlerCallback() {

                    @Override
                    public void rc999(RequestEntity entity, Result result) {

                    }

                    @Override
                    public void rc3001(RequestEntity entity, Result result) {

                    }

                    @Override
                    public void rc0(RequestEntity entity, Result result) {
                        ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON
                                .parseArray(result.getData(),
                                        TextValueBean.class);
//                        if (SalePriceRange.equals(conditionName)) {
//                            salePriceRanges.addAll(temp);
//                        } else 
                        if (HouseType.equals(conditionName)) {
                            houseTypes.addAll(temp);
                        }
//                        else if (PrpUsage.equals(conditionName)) {
//                            prpUsages.addAll(temp);
//                        } else if (EstateLabel.equals(conditionName)) {
//                            estateLabels.addAll(temp);
//                        } else if (EstateStatus.equals(conditionName)) {
//                            estateStatus.addAll(temp);
//                        } else if (FloorRange.equals(conditionName)) {
//                            floorRanges.addAll(temp);
//                        }
                        else if (RentPriceRange.equals(conditionName)) {
                            rentPriceRanges.addAll(temp);
                        } else if (Direction.equals(conditionName)) {
                            directions.addAll(temp);
                        } else if (SquareRange.equals(conditionName)) {
                        	squareRanges.addAll(temp);
                        }
//                        else if (Sort.equals(conditionName)) {
//                            sorts.addAll(temp);
//                        }
                    }
                });
    }

    private void getAreaList() {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getAreaList(cityId, new ResultHandlerCallback() {

            @Override
            public void rc999(RequestEntity entity, Result result) {

            }

            @Override
            public void rc3001(RequestEntity entity, Result result) {

            }

            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON
                        .parseArray(result.getData(), TextValueBean.class);
                areas.addAll(temp);
            }
        });
    }

    private void getRentHouseList(String cityId, TextValueBean areaCondition,
            TextValueBean priceCondition, TextValueBean squareCondition,
            String sort, String keyWords, String direction,
            TextValueBean roomTypeCondition, int pageSize,
            final boolean isRefresh) {

        if (isRefresh) {
            pageIndex = 0;
        }

        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getRentHouseList(cityId, areaCondition, priceCondition,
                squareCondition, sort, keyWords, direction, roomTypeCondition,
                10, pageIndex, new ResultHandlerCallback() {

                    @Override
                    public void rc999(RequestEntity entity, Result result) {
                        if (isRefresh) {
                            pullView.onHeaderRefreshFinish();
                        } else {
                            pullView.onFooterLoadFinish();
                        }
                    }

                    @Override
                    public void rc3001(RequestEntity entity, Result result) {
                        if (isRefresh) {
                            pullView.onHeaderRefreshFinish();
                        } else {
                            pullView.onFooterLoadFinish();
                        }
                    }

                    @Override
                    public void rc0(RequestEntity entity, Result result) {
                        int total = result.getTotal();
                        pageTotal = (int) Math.ceil(((double) total / (double) 10));
                        
                        ArrayList<RentHouseBean> temp = (ArrayList<RentHouseBean>) JSON
                                .parseArray(result.getData(),
                                        RentHouseBean.class);
                        
                        if (isRefresh) {
                            rentHouses.clear();
                        }
                        if (temp != null) {
                            pageIndex++;
                        }
                        rentHouses.addAll(temp);
                        adapter.notifyDataSetChanged();
                        
                        if (isRefresh) {
                            pullView.onHeaderRefreshFinish();
                        } else {
                            pullView.onFooterLoadFinish();
                        }
                    }
                });
    }
    
	/**
	 * 排序    0：默认     1：租金低到高     2：租金高到低
	 */
	private void initSortsData() {
		TextValueBean tv1 = new TextValueBean();
		tv1.setText("默认");
		tv1.setValue("0");
		tv1.setSelected(true);
		
		TextValueBean tv2 = new TextValueBean();
		tv2.setText("租金低到高");
		tv2.setValue("1");
		
		TextValueBean tv3 = new TextValueBean();
		tv3.setText("租金高到低");
		tv3.setValue("2");
		
		sorts.add(tv1);
		sorts.add(tv2);
		sorts.add(tv3);
	}

}
