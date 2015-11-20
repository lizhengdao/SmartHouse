package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.SearchHouseAdapter;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseTypeSelectListener;

import com.alibaba.fastjson.JSON;

public class SearchHouseActivity extends BaseActivity implements
		OnClickListener, OnHouseTypeSelectListener, OnHeaderRefreshListener,
		OnFooterLoadListener, OnItemClickListener {

	private TextView tvCancel, tvHouseType, tvArea, tvTotalPrice,
			tvHouseRoomsType, tvMore;
	private EditText edtKeyWords;
	private ImageView imgClearKeyWords;

	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;

	private AbPullToRefreshView pullView;
	private ListView lstSearchHouseView;
	private SearchHouseAdapter adapter;
	private ArrayList<SearchHouseItemBean> searchHouses = new ArrayList<SearchHouseItemBean>();

	private String cityId = "";

	public static final String SalePriceRange = "SalePriceRange";
	public static final String HouseType = "HouseType";
	public static final String PrpUsage = "PrpUsage";
	public static final String EstateLabel = "EstateLabel";
	public static final String EstateStatus = "EstateStatus";

	/**
	 * 区域
	 */
	private ArrayList<TextValueBean> areas = new ArrayList<TextValueBean>();
	/**
	 * 总价范围
	 */
	private ArrayList<TextValueBean> salePriceRanges = new ArrayList<TextValueBean>();
	/**
	 * 户型
	 */
	private ArrayList<TextValueBean> houseTypes = new ArrayList<TextValueBean>();
	/**
	 * 物业类型
	 */
	private ArrayList<TextValueBean> prpUsages = new ArrayList<TextValueBean>();

	/**
	 * 特色标签
	 */
	private ArrayList<TextValueBean> estateLabels = new ArrayList<TextValueBean>();

	/**
	 * 售卖状态
	 */
	private ArrayList<TextValueBean> estateStatus = new ArrayList<TextValueBean>();

	/**
	 * 区域
	 */
	private OnConditionSelectListener onAreaSelectListener;
	/**
	 * 总价
	 */
	private OnConditionSelectListener onTotalPriceSelectListener;
	/**
	 * 房型
	 */
	private OnConditionSelectListener onHouseTypeSelectListener;

	private ArrayList<String> moreType = new ArrayList<String>();

	private TextValueBean areaCondition;
	private TextValueBean totalPriceCondition;
	private TextValueBean labelCondition;
	private TextValueBean statusCondition;
	private TextValueBean roomTypeCondition;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		setListener();
		initData();
	}

	private void initView() {
		setContentView(R.layout.act_search_house);

		tvCancel = (TextView) findViewById(R.id.act_search_house_cancel_tv);
		edtKeyWords = (EditText) findViewById(R.id.act_search_house_key_words);
		tvHouseType = (TextView) findViewById(R.id.act_search_house_house_type_tv);
		imgClearKeyWords = (ImageView) findViewById(R.id.act_search_house_clear_key_wrods);

		lltArea = (LinearLayout) findViewById(R.id.act_search_house_area_llt);
		lltTotalPrice = (LinearLayout) findViewById(R.id.act_search_house_total_price_llt);
		lltHouseType = (LinearLayout) findViewById(R.id.act_search_house_type_llt);
		lltMore = (LinearLayout) findViewById(R.id.act_search_house_more_llt);

		tvArea = (TextView) findViewById(R.id.act_search_house_area_tv);
		tvTotalPrice = (TextView) findViewById(R.id.act_search_house_total_price_tv);
		tvHouseRoomsType = (TextView) findViewById(R.id.act_search_house_rooms_type_tv);
		tvMore = (TextView) findViewById(R.id.act_search_house_more_tv);

		pullView = (AbPullToRefreshView) findViewById(R.id.pull_search_house);
		lstSearchHouseView = (ListView) findViewById(R.id.lst_search_house);
	}

	private void setListener() {
		tvCancel.setOnClickListener(this);
		tvHouseType.setOnClickListener(this);
		imgClearKeyWords.setOnClickListener(this);

		pullView.setPullRefreshEnable(false);
		pullView.setLoadMoreEnable(false);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		lltArea.setOnClickListener(this);
		lltTotalPrice.setOnClickListener(this);
		lltHouseType.setOnClickListener(this);
		lltMore.setOnClickListener(this);

		adapter = new SearchHouseAdapter(this, searchHouses);
		lstSearchHouseView.setAdapter(adapter);
		lstSearchHouseView.setOnItemClickListener(this);

		onTotalPriceSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				totalPriceCondition = txtValueBean;
				tvTotalPrice.setText(txtValueBean.getText());
				getSearchHouseList();
			}
		};

		onHouseTypeSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				roomTypeCondition = txtValueBean;
				tvHouseType.setText(txtValueBean.getText());
				getSearchHouseList();
			}
		};

		onAreaSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				areaCondition = txtValueBean;
				tvArea.setText(txtValueBean.getText());
				getSearchHouseList();
			}
		};
		edtKeyWords
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							getSearchHouseList();
							return true;
						}
						return false;
					}

				});
	}

	private void initData() {
		moreType.add("朝向");
		moreType.add("面积");
		moreType.add("标签");
		moreType.add("楼层");
		moreType.add("房源编号");
		getAreaList();
		getConditionList(SalePriceRange);
		getConditionList(HouseType);
		getConditionList(PrpUsage);
		getConditionList(EstateLabel);
		getConditionList(EstateStatus);
		getSearchHouseList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_cancel_tv:
			finish();
			break;
		case R.id.act_search_house_house_type_tv:
			PopViewHelper.showSearchHouseTypePopWindow(this, tvHouseType, this);
			break;
		case R.id.act_search_house_clear_key_wrods: // 清除关键字
			edtKeyWords.setText("");
			break;
		case R.id.act_search_house_area_llt: // 区域
			PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas,
					onAreaSelectListener);
			break;
		case R.id.act_search_house_total_price_llt: // 总价
			PopViewHelper.showSelectTotalPricePopWindow(this, lltTotalPrice,
					salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.act_search_house_type_llt: // 类型
			PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType,
					houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.act_search_house_more_llt: // 更多
			PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType,
					null, null, estateLabels, lltMore);
			break;

		}
	}

	@Override
	public void onHouseTypeSelect(int houseType, String houseTypeTxt) {
		tvHouseType.setText(houseTypeTxt);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	private void getSearchHouseList() {
		String keyWords = edtKeyWords.getText().toString();
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSearchHouseList(areaCondition, totalPriceCondition, null,
				null, labelCondition, statusCondition, keyWords,
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {

					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {

					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						searchHouses.clear();
						ArrayList<SearchHouseItemBean> temp = (ArrayList<SearchHouseItemBean>) JSON
								.parseArray(result.getData(),
										SearchHouseItemBean.class);
						searchHouses.addAll(temp);
						adapter.notifyDataSetChanged();
					}
				});
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
						if (SalePriceRange.equals(conditionName)) {
							salePriceRanges.addAll(temp);
						} else if (HouseType.equals(conditionName)) {
							houseTypes.addAll(temp);
						} else if (PrpUsage.equals(conditionName)) {
							prpUsages.addAll(temp);
						} else if (EstateLabel.equals(conditionName)) {
							estateLabels.addAll(temp);
						} else if (EstateStatus.equals(conditionName)) {
							estateStatus.addAll(temp);
						}
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
				// TODO Auto-generated method stub
				ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON
						.parseArray(result.getData(), TextValueBean.class);
				areas.addAll(temp);
			}
		});
	}

}
