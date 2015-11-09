package cn.com.zzwfang.view.helper;


import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.ConditionAdapter;
import cn.com.zzwfang.adapter.MoreDetailAdapter;
import cn.com.zzwfang.adapter.MoreTypeAdapter;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.util.DevUtils;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

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
				case 4:  //  楼龄
					break;
				case 5:  //  楼层
					break;
				case 6:  //  房源编号
					break;
				}
			}
		});
	}
}
