package cn.com.zzwfang.view.helper;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.ConditionAdapter;
import cn.com.zzwfang.adapter.MoreDetailAdapter;
import cn.com.zzwfang.adapter.MoreTypeAdapter;
import cn.com.zzwfang.adapter.MortgageYearsAdapter;
import cn.com.zzwfang.adapter.MyCustomerConditionAdapter;
import cn.com.zzwfang.adapter.NewsMoreAdapter;
import cn.com.zzwfang.bean.FeeHunterMyCustomerConditionBean;
import cn.com.zzwfang.bean.IdTitleBean;
import cn.com.zzwfang.bean.NewsItemBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.util.DevUtils;

public class PopViewHelper {

	public interface OnHouseTypeSelectListener {
		public static int HOUSE_TYPE_NEW_HOUSE = 0;
		public static int HOUSE_TYPE_SECOND_HAND_HOUSE = 1;
		public static int HOUSE_TYPE_RENT_HOUSE = 2;
		void onHouseTypeSelect(int houseType, String houseTypeTxt);
	}
	/**
	 * 搜索选择 新房  二手房  租房
	 * @param context
	 */
	public static void showSearchHouseTypePopWindow(Context context, View anchorView, final OnHouseTypeSelectListener onHouseTypeSelectListener) {
		
		View view = View.inflate(context, R.layout.popup_search_house_type, null);
		TextView tvNewHouse = (TextView) view.findViewById(R.id.pop_search_new_house);
		TextView tvSecondHandHouse = (TextView) view.findViewById(R.id.pop_search_second_hand_house);
		TextView tvRentHouse = (TextView) view.findViewById(R.id.pop_search_rent_house);
		
		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    OnClickListener clickListener = new OnClickListener() {
	    	int houseType = 0;
	    	String houseTypeTxt = "";
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.pop_search_new_house:
					houseType = OnHouseTypeSelectListener.HOUSE_TYPE_NEW_HOUSE;
					houseTypeTxt = "新房";
					break;
				case R.id.pop_search_second_hand_house:
					houseType = OnHouseTypeSelectListener.HOUSE_TYPE_SECOND_HAND_HOUSE;
					houseTypeTxt = "二手房";
					break;
				case R.id.pop_search_rent_house:
					houseType = OnHouseTypeSelectListener.HOUSE_TYPE_RENT_HOUSE;
					houseTypeTxt = "租房";
					break;
				}
				
				if (onHouseTypeSelectListener != null) {
					onHouseTypeSelectListener.onHouseTypeSelect(houseType, houseTypeTxt);
				}
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		};
		tvNewHouse.setOnClickListener(clickListener);
		tvSecondHandHouse.setOnClickListener(clickListener);
		tvRentHouse.setOnClickListener(clickListener);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	/**
	 * 区域选择
	 * @param context
	 * @param anchorView
	 */
	public static void showSelectAreaPopWindow(Context context, View anchorView,
			final ArrayList<TextValueBean> conditions, final OnConditionSelectListener onConditionSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    ConditionAdapter adapter = new ConditionAdapter(context, conditions);
	    lstCondition.setAdapter(adapter);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onConditionSelectListener != null) {
					onConditionSelectListener.onConditionSelect(conditions.get(position));
				}
			}
		});
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	public interface OnConditionSelectListener {
		void onConditionSelect(TextValueBean txtValueBean);
	}
	
	/**
	 * 总价选择
	 */
	public static void showSelectTotalPricePopWindow(Context context, View anchorView,
			final ArrayList<TextValueBean> conditions, final OnConditionSelectListener onConditionSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, DevUtils.getScreenTools(context).dip2px(200));
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    ConditionAdapter adapter = new ConditionAdapter(context, conditions);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onConditionSelectListener != null) {
					onConditionSelectListener.onConditionSelect(conditions.get(position));
				}
			}
		});
	    lstCondition.setAdapter(adapter);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	/**
	 * 房型选择
	 */
	public static void showSelectHouseTypePopWindow(Context context, View anchorView,
			final ArrayList<TextValueBean> conditions, final OnConditionSelectListener onConditionSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    ConditionAdapter adapter = new ConditionAdapter(context, conditions);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onConditionSelectListener != null) {
					onConditionSelectListener.onConditionSelect(conditions.get(position));
				}
			}
		});
	    
	    lstCondition.setAdapter(adapter);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	/**
	 * 二手房更多选择
	 */
	public static void showSecondHandHouseMorePopWindow(Context context, ArrayList<String> types,
			ArrayList<TextValueBean> detail, ArrayList<TextValueBean> directions,
			ArrayList<TextValueBean> estateLabels, View anchorView) {
		View view = View.inflate(context, R.layout.popup_second_hand_house_more, null);
		PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	    
	    ListView lstType = (ListView) view.findViewById(R.id.popup_second_hand_more_type_lst);
	    final ListView lstDetail = (ListView) view.findViewById(R.id.popup_second_hand_more_type_detail);
	    
	    MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(context, types);
	    lstType.setAdapter(moreTypeAdapter);
	    
	    final MoreDetailAdapter sortAdapter = new MoreDetailAdapter(context, detail);
	    lstDetail.setAdapter(sortAdapter);
	    
	    final MoreDetailAdapter directionAdapter = new MoreDetailAdapter(context, directions);
	    final MoreDetailAdapter labelAdapter = new MoreDetailAdapter(context, estateLabels);
	    
	    lstType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:  //  排序
					lstDetail.setAdapter(sortAdapter);
					break;
				case 1:  //  朝向
					lstDetail.setAdapter(directionAdapter);
					break;
				case 2:  //  面积
					break;
				case 3:  //  标签
					lstDetail.setAdapter(labelAdapter);
					break;
				case 4:  //  楼层
					break;
				case 5:  //  房源编号
					break;
				}
			}
		});
	}
	
	/**
	 * 房型选择
	 */
	public static void showSelectRentPricePopWindow(Context context, View anchorView,
			final ArrayList<TextValueBean> conditions, final OnConditionSelectListener onConditionSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    ConditionAdapter adapter = new ConditionAdapter(context, conditions);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onConditionSelectListener != null) {
					onConditionSelectListener.onConditionSelect(conditions.get(position));
				}
			}
		});
	    
	    lstCondition.setAdapter(adapter);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	public interface OnFeeHunterRecommendHouseTypeSelecetListener {
		public void onFeeHunterRecommendHouseTypeSelecet(int type);
		
		public static final int FEE_HUNTER_HOUSE_TYPE_NEW_HOUSE = 1;
		
		public static final int FEE_HUNTER_HOUSE_TYPE_SECOND_HAND_HOUSE = 2;
	}
	/**
	 * 赏金猎人  推荐买房卖房弹出框
	 * @param context
	 * @param anchorView
	 */
	public static void showFeeHunterRecommendBuyAndSell(Context context, View anchorView,
			final OnFeeHunterRecommendHouseTypeSelecetListener listener) {
		View view = View.inflate(context, R.layout.popup_fee_hunter_recommend_buy_sell, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    popupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
	    
	    TextView tvNewHouse = (TextView) view.findViewById(R.id.popwindow_fee_hunter_recommend_new_house);
	    TextView tvSecondHandHouse = (TextView) view.findViewById(R.id.popwindow_fee_hunter_recommend_second_hand_house);
	    
	    OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.popwindow_fee_hunter_recommend_new_house:
					popupWindow.dismiss();
					if (listener != null) {
						listener.onFeeHunterRecommendHouseTypeSelecet(OnFeeHunterRecommendHouseTypeSelecetListener.FEE_HUNTER_HOUSE_TYPE_NEW_HOUSE);
					}
					break;
				case R.id.popwindow_fee_hunter_recommend_second_hand_house:
					popupWindow.dismiss();
					if (listener != null) {
						listener.onFeeHunterRecommendHouseTypeSelecet(OnFeeHunterRecommendHouseTypeSelecetListener.FEE_HUNTER_HOUSE_TYPE_SECOND_HAND_HOUSE);
					}
					break;
				}
			}
		};
		
		tvNewHouse.setOnClickListener(clickListener);
		tvSecondHandHouse.setOnClickListener(clickListener);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
	}
	
	public interface OnMortgageTypeSelectListener {
		public static int MORTGAGE_TYPE_BENJIN = 1;
		public static int MORTGAGE_TYPE_BENXI = 2;
		void onMortgageTypeSelect(String typeName, int typeId);
	}
	/**
	 * 选择贷款方式  等额本金  等额本息
	 * @param context
	 * @param anchorView
	 */
	public static void showMortgageType(Context context, View anchorView,
			final OnMortgageTypeSelectListener listener) {
		View view = View.inflate(context, R.layout.popup_mortgage_type, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    popupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
	    
	    TextView tvBenJin = (TextView) view.findViewById(R.id.popwindow_mortgage_type_benjin);
	    TextView tvBenXi = (TextView) view.findViewById(R.id.popwindow_mortgage_type_benxi);
	    
	    OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.popwindow_mortgage_type_benjin:
					popupWindow.dismiss();
					if (listener != null) {
						listener.onMortgageTypeSelect("等额本金", OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENJIN);
					}
					break;
				case R.id.popwindow_mortgage_type_benxi:
					popupWindow.dismiss();
					if (listener != null) {
						listener.onMortgageTypeSelect("等额本息", OnMortgageTypeSelectListener.MORTGAGE_TYPE_BENXI);
					}
					break;
				}
			}
		};
		
		tvBenJin.setOnClickListener(clickListener);
		tvBenXi.setOnClickListener(clickListener);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
	}
	
	public interface OnMortgageYearSelectListener {
		void onMortgageYearSelect(int index);
	}
	
	public static void showSelectMortgageYears(Context context, View anchorView, String [] years, final OnMortgageYearSelectListener listener) {
		View view = View.inflate(context, R.layout.popup_mortgage_years, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, DevUtils.getScreenTools(context).dip2px(300));
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstYears = (ListView) view.findViewById(R.id.popup_mortgage_years_lst);
	    MortgageYearsAdapter adapter = new MortgageYearsAdapter(context, years);
	    lstYears.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (listener != null) {
					listener.onMortgageYearSelect(position);
				}
			}
		});
	    
	    lstYears.setAdapter(adapter);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
        	popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
	}
	
	
	public interface OnMyCustomerConditionSelectListener {
		void onMyCustomerConditonSelect(FeeHunterMyCustomerConditionBean conditon);
	}
	/**
	 * 赏金猎人   我的客户筛选条件
	 */
	public static void showSelectMyCustomerConditionPopWindow(Context context, View anchorView,
			final ArrayList<FeeHunterMyCustomerConditionBean> conditions, final OnMyCustomerConditionSelectListener onMyCustomerConditionSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), DevUtils.getScreenTools(context).dip2px(200));
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    MyCustomerConditionAdapter adapter = new MyCustomerConditionAdapter(context, conditions);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onMyCustomerConditionSelectListener != null) {
					onMyCustomerConditionSelectListener.onMyCustomerConditonSelect(conditions.get(position));
				}
			}
		});
	    
	    lstCondition.setAdapter(adapter);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	public interface OnNewsMoreSelectedListener {
		void onOnNewsMoreSelected(int position, IdTitleBean newsType);
	}
	/**
	 * 咨询更多
	 */
	public static void showNewsMorePopWindow(Context context, View anchorView,
			final ArrayList<IdTitleBean> news, final OnNewsMoreSelectedListener onNewsMoreSelectedListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    NewsMoreAdapter adapter = new NewsMoreAdapter(context, news);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onNewsMoreSelectedListener != null) {
					onNewsMoreSelectedListener.onOnNewsMoreSelected(position, news.get(position));
				}
			}
		});
	    
	    lstCondition.setAdapter(adapter);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
}
