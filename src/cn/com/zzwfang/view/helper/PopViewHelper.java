package cn.com.zzwfang.view.helper;


import java.util.ArrayList;

import com.baidu.location.b.d;

import android.R.color;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.BankNameAdapter;
import cn.com.zzwfang.adapter.BankProvinceOrCityAdapter;
import cn.com.zzwfang.adapter.BuildingsAdapter;
import cn.com.zzwfang.adapter.ConditionAdapter;
import cn.com.zzwfang.adapter.EstateRoomAdapter;
import cn.com.zzwfang.adapter.HouseSourceParamAdapter;
import cn.com.zzwfang.adapter.HouseSourceParamMoreAdapter;
import cn.com.zzwfang.adapter.HouseSourceSortTypeAdapter;
import cn.com.zzwfang.adapter.MoreDetailAdapter;
import cn.com.zzwfang.adapter.MoreTypeAdapter;
import cn.com.zzwfang.adapter.MortgageYearsAdapter;
import cn.com.zzwfang.adapter.MyCustomerConditionAdapter;
import cn.com.zzwfang.adapter.NewsMoreAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.FeeHunterMyCustomerConditionBean;
import cn.com.zzwfang.bean.FieldNameValueBean;
import cn.com.zzwfang.bean.HouseSourceParamBean;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.bean.IdNameFloorBean;
import cn.com.zzwfang.bean.IdTitleBean;
import cn.com.zzwfang.bean.NameValueBean;
import cn.com.zzwfang.bean.ProvinceCityBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.util.DevUtils;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.PathImage;

public class PopViewHelper {

	public interface OnHouseTypeSelectListener {
		public static int HOUSE_TYPE_NEW_HOUSE = 0;
		public static int HOUSE_TYPE_SECOND_HAND_HOUSE = 1;
		public static int HOUSE_TYPE_RENT_HOUSE = 2;  // 租房和二手房都是1，为了区分3种，为2，上传还是传1
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
	
	public interface OnHouseSourceParamPickListener {
		
		void onHouseSourceParamPick(int fieldPosition, NameValueBean houseSourceParam);
	}
	/**
	 * 房源参数选择
	 * @param context
	 * @param anchorView
	 */
	public static void showPickHouseSourceParamPopWindow(Context context, View anchorView, final int fieldPosition,
			final ArrayList<HouseSourceParamBean> houseSourceParams, final OnHouseSourceParamPickListener listener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    
	    final ArrayList<NameValueBean> values = houseSourceParams.get(fieldPosition).getValues();
	    HouseSourceParamAdapter adapter = new HouseSourceParamAdapter(context, values);
	    lstCondition.setAdapter(adapter);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NameValueBean temp = values.get(position);
				temp.setSelected(!temp.isSelected());
				
				if (temp.isSelected()) {
					int size = values.size();
					for (int i = 0; i < size; i++) {
						if (i != position) {
							values.get(i).setSelected(false);
						}
					}
				}
				popupWindow.dismiss();
				
				if (listener != null) {
					listener.onHouseSourceParamPick(fieldPosition, temp);
				}
				
			}
		});
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	/**
	 * 房源参数更多选择
	 * @param context
	 * @param anchorView
	 */
	public static void showPickHouseSourceParamMorePopWindow(Context context, View anchorView, final int fieldPosition,
			final ArrayList<HouseSourceParamBean> houseSourceParams, final OnHouseSourceParamPickListener listener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    
	    lstCondition.setDivider(null);
	    lstCondition.setDividerHeight(0);
	    HouseSourceParamMoreAdapter adapter = new HouseSourceParamMoreAdapter(context, houseSourceParams, new OnHouseSourceParamPickListener() {
			
			@Override
			public void onHouseSourceParamPick(int fieldPosition,
					NameValueBean houseSourceParam) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onHouseSourceParamPick(fieldPosition, houseSourceParam);
				}
				popupWindow.dismiss();
			}
		});
	    lstCondition.setAdapter(adapter);
	    
//	    final ArrayList<NameValueBean> values = houseSourceParams.get(fieldPosition).getValues();
//	    HouseSourceParamAdapter adapter = new HouseSourceParamAdapter(context, values);
//	    lstCondition.setAdapter(adapter);
//	    lstCondition.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				NameValueBean temp = values.get(position);
//				temp.setSelected(!temp.isSelected());
//				
//				if (temp.isSelected()) {
//					int size = values.size();
//					for (int i = 0; i < size; i++) {
//						if (i != position) {
//							values.get(i).setSelected(false);
//						}
//					}
//				}
//				popupWindow.dismiss();
//				
//				if (listener != null) {
//					listener.onHouseSourceParamPick(fieldPosition, temp);
//				}
//				
//			}
//		});
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
	
	public interface OnSecondHandHouseMoreConditionListener {
		void onSecondHandHouseMoreConditon(TextValueBean sortCondition,
				TextValueBean directionCondition, TextValueBean squareCondition,
				TextValueBean labelCondition, TextValueBean buildingAgeCondition,
				TextValueBean floorRangeCondition);
	}
	/**
	 * 二手房更多选择
	 * @param context
	 * @param types
	 * @param sorts
	 * @param directions
	 * @param squares
	 * @param estateLabels
	 * @param buildingAges
	 * @param floorRanges
	 * @param anchorView
	 */
	public static void showSecondHandHouseMorePopWindow(Context context, ArrayList<String> types,
			final ArrayList<TextValueBean> sorts,
			final ArrayList<TextValueBean> directions,
			final ArrayList<TextValueBean> squares,
			final ArrayList<TextValueBean> estateLabels,
			final ArrayList<TextValueBean> buildingAges,
			final ArrayList<TextValueBean> floorRanges,
			View anchorView, final OnSecondHandHouseMoreConditionListener onSecondHandHouseMoreConditionListener) {
		View view = View.inflate(context, R.layout.popup_second_hand_house_more, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
	    TextView tvOk = (TextView) view.findViewById(R.id.popup_second_hand_more_type_ok);
	    
	    MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(context, types);
	    lstType.setAdapter(moreTypeAdapter);
	    
	    // 排序Adapter
	    final MoreDetailAdapter sortAdapter = new MoreDetailAdapter(context, sorts);
	    lstDetail.setAdapter(sortAdapter);
	    // 朝向Adapter
	    final MoreDetailAdapter directionAdapter = new MoreDetailAdapter(context, directions);
	    // 面积Adapter
	    final MoreDetailAdapter squareAdapter = new MoreDetailAdapter(context, squares);
	    // 标签Adapter
	    final MoreDetailAdapter labelAdapter = new MoreDetailAdapter(context, estateLabels);
	    // 楼龄Adapter
	    final MoreDetailAdapter buildingAgeAdapter = new MoreDetailAdapter(context, buildingAges);
	    // 楼层Adapter
	    final MoreDetailAdapter floorAdapter = new MoreDetailAdapter(context, floorRanges);
	    
	    lstDetail.setTag(0);
	    
	    lstType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:  //  排序
					lstDetail.setAdapter(sortAdapter);
					lstDetail.setTag(0);
					break;
				case 1:  //  朝向
					lstDetail.setAdapter(directionAdapter);
					lstDetail.setTag(1);
					break;
				case 2:  //  面积
					lstDetail.setAdapter(squareAdapter);
					lstDetail.setTag(2);
					break;
				case 3:  //  标签
					lstDetail.setAdapter(labelAdapter);
					lstDetail.setTag(3);
					break;
				case 4:  //  楼龄
					lstDetail.setAdapter(buildingAgeAdapter);
					lstDetail.setTag(4);
					break;
				case 5:  //  楼层
					lstDetail.setAdapter(floorAdapter);
					lstDetail.setTag(5);
					break;
				}
			}
		});
	    lstDetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int tag = (Integer) lstDetail.getTag();
				switch (tag) {
				case 0:  //  排序
					if (sorts.get(position).isSelected()) {
						sorts.get(position).setSelected(false);
					} else {
						for (int i = 0; i < sorts.size(); i++) {
							if (i == position) {
								sorts.get(i).setSelected(true);
							} else {
								sorts.get(i).setSelected(false);
							}
						}
					}
					sortAdapter.notifyDataSetChanged();
					break;
				case 1:  //  朝向
					if (directions.get(position).isSelected()) {
						directions.get(position).setSelected(false);
					} else {
						for (int i = 0; i < directions.size(); i++) {
							if (i == position) {
								directions.get(i).setSelected(true);
							} else {
								directions.get(i).setSelected(false);
							}
						}
					}
					directionAdapter.notifyDataSetChanged();
					break;
				case 2:  //  面积
					if (squares.get(position).isSelected()) {
						squares.get(position).setSelected(false);
					} else {
						for (int i = 0; i < squares.size(); i++) {
							if (i == position) {
								squares.get(i).setSelected(true);
							} else {
								squares.get(i).setSelected(false);
							}
						}
					}
					squareAdapter.notifyDataSetChanged();
					break;
				case 3:  //  标签
					if (estateLabels.get(position).isSelected()) {
						estateLabels.get(position).setSelected(false);
					} else {
						for (int i = 0; i < estateLabels.size(); i++) {
							if (i == position) {
								estateLabels.get(i).setSelected(true);
							} else {
								estateLabels.get(i).setSelected(false);
							}
						}
					}
					labelAdapter.notifyDataSetChanged();
					break;
				case 4:  //  楼龄
					if (buildingAges.get(position).isSelected()) {
						buildingAges.get(position).setSelected(false);
					} else {
						for (int i = 0; i < buildingAges.size(); i++) {
							if (i == position) {
								buildingAges.get(i).setSelected(true);
							} else {
								buildingAges.get(i).setSelected(false);
							}
						}
					}
					buildingAgeAdapter.notifyDataSetChanged();
					break;
				case 5:  //  楼层
					if (floorRanges.get(position).isSelected()) {
						floorRanges.get(position).setSelected(false);
					} else {
						for (int i = 0; i < floorRanges.size(); i++) {
							if (i == position) {
								floorRanges.get(i).setSelected(true);
							} else {
								floorRanges.get(i).setSelected(false);
							}
						}
					}
					floorAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
	    
	    OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextValueBean sortCondition = null;
				TextValueBean directionCondition = null;
				TextValueBean squareCondition = null;
				TextValueBean labelCondition = null;
				TextValueBean buildingAgeCondition = null;
				TextValueBean floorRangeCondition = null;
				switch (v.getId()) {
				case R.id.popup_second_hand_more_type_ok:
					for (TextValueBean tvb : sorts) {
						if (tvb.isSelected()) {
							sortCondition = tvb;
							break;
						}
					}
					for (TextValueBean tvb : directions) {
						if (tvb.isSelected()) {
							directionCondition = tvb;
							break;
						}
					}
					for (TextValueBean tvb : squares) {
						if (tvb.isSelected()) {
							squareCondition = tvb;
							break;
						}
					}
					for (TextValueBean tvb : estateLabels) {
						if (tvb.isSelected()) {
							labelCondition = tvb;
							break;
						}
					}
					for (TextValueBean tvb : buildingAges) {
						if (tvb.isSelected()) {
							buildingAgeCondition = tvb;
							break;
						}
					}
					for (TextValueBean tvb : floorRanges) {
						if (tvb.isSelected()) {
							floorRangeCondition = tvb;
							break;
						}
					}
					break;
				}
				popupWindow.dismiss();
				if (onSecondHandHouseMoreConditionListener != null) {
					onSecondHandHouseMoreConditionListener.onSecondHandHouseMoreConditon(sortCondition,
							directionCondition, squareCondition, labelCondition, buildingAgeCondition, floorRangeCondition);
				}
			}
		};
		tvOk.setOnClickListener(clickListener);
	}
	
	public interface OnNewHouseMoreConditionListener {
		void onNewHouseMoreConditon(TextValueBean houseUsageConditionData,
				TextValueBean labelConditionData, TextValueBean saleStatusConditonData);
	}
	/**
	 * 新房更多选择
	 */
	public static void showNewHouseMorePopWindow(Context context, ArrayList<String> types,
			final ArrayList<TextValueBean> houseUsages, final ArrayList<TextValueBean> estateLabels,
			final ArrayList<TextValueBean> estateStatus, View anchorView,
			final OnNewHouseMoreConditionListener onNewHouseMoreConditionListener) {
		View view = View.inflate(context, R.layout.popup_second_hand_house_more, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
	    TextView tvOk = (TextView) view.findViewById(R.id.popup_second_hand_more_type_ok);
	    
	    MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(context, types);
	    lstType.setAdapter(moreTypeAdapter);
	    
	    //  房屋用途Adapter
	    final MoreDetailAdapter usageAdapter = new MoreDetailAdapter(context, houseUsages);
	    lstDetail.setAdapter(usageAdapter);
	    //  特色标签Adapter
	    final MoreDetailAdapter labelAdapter = new MoreDetailAdapter(context, estateLabels);
	    //  售卖状态Adapter
	    final MoreDetailAdapter saleStatusAdapter = new MoreDetailAdapter(context, estateStatus);
	    lstDetail.setTag(0);
	    lstType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:  //  房屋用途
					lstDetail.setTag(0);
					lstDetail.setAdapter(usageAdapter);
					break;
				case 1:  //  标签
					lstDetail.setTag(1);
					lstDetail.setAdapter(labelAdapter);
					break;
				case 2:  //  售卖状态
					lstDetail.setTag(2);
					lstDetail.setAdapter(saleStatusAdapter);
					break;
				}
			}
		});
	    
	    lstDetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int tag = (Integer) lstDetail.getTag();
				switch (tag) {
				case 0:  //  房屋用途
					if (houseUsages.get(position).isSelected()) {
						houseUsages.get(position).setSelected(false);
					} else {
						for (int i = 0; i < houseUsages.size(); i++) {
							if (i == position) {
								houseUsages.get(i).setSelected(true);
							} else {
								houseUsages.get(i).setSelected(false);
							}
						}
					}
					usageAdapter.notifyDataSetChanged();
					break;
				case 1:  //  标签
					if (estateLabels.get(position).isSelected()) {
						estateLabels.get(position).setSelected(false);
					} else {
						for (int i = 0; i < estateLabels.size(); i++) {
							if (i == position) {
								estateLabels.get(i).setSelected(true);
							} else {
								estateLabels.get(i).setSelected(false);
							}
						}
					}
					labelAdapter.notifyDataSetChanged();
					break;
				case 2:  //  售卖状态
					if (estateStatus.get(position).isSelected()) {
						estateStatus.get(position).setSelected(false);
					} else {
						for (int i = 0; i < estateStatus.size(); i++) {
							if (i == position) {
								estateStatus.get(i).setSelected(true);
							} else {
								estateStatus.get(i).setSelected(false);
							}
						}
					}
					saleStatusAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
	    
	    tvOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextValueBean usageCondition = null;
				TextValueBean labelCondition = null;
				TextValueBean saleStatusCondition = null;
				for (TextValueBean tvb : houseUsages) {  // 房屋用途
					if (tvb.isSelected()) {
						usageCondition = tvb;
						break;
					}
				}
				for (TextValueBean tvb : estateLabels) {  // 标签
					if (tvb.isSelected()) {
						labelCondition = tvb;
						break;
					}
				}
				for (TextValueBean tvb : estateStatus) {  // 售卖状态
					if (tvb.isSelected()) {
						saleStatusCondition = tvb;
						break;
					}
				}
				
				popupWindow.dismiss();
				if (onNewHouseMoreConditionListener != null) {
					onNewHouseMoreConditionListener.onNewHouseMoreConditon(
							usageCondition, labelCondition, saleStatusCondition);
				}
				
			}
		});
	}
	
	public interface OnRentHouseMoreConditionListener {
		void onRentHouseMoreConditon(TextValueBean sortConditionData,
				TextValueBean squareConditionData, TextValueBean directionConditonData);
	}
	/**
	 * 租房房更多选择
	 */
	public static void showRentHouseMorePopWindow(Context context, ArrayList<String> types,
			final ArrayList<TextValueBean> sorts, final ArrayList<TextValueBean> squares, final ArrayList<TextValueBean> directions,
		    View anchorView, final OnRentHouseMoreConditionListener onRentHouseMoreConditionListener) {
		View view = View.inflate(context, R.layout.popup_second_hand_house_more, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
	    TextView tvOk = (TextView) view.findViewById(R.id.popup_second_hand_more_type_ok);
	    
	    MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(context, types);
	    lstType.setAdapter(moreTypeAdapter);
	    // 排序Adapter
	    final MoreDetailAdapter sortAdapter = new MoreDetailAdapter(context, sorts);
	    lstDetail.setAdapter(sortAdapter);
	    // 面积范围Adapter
	    final MoreDetailAdapter squareAdapter = new MoreDetailAdapter(context, squares);
	    // 朝向Adapter
	    final MoreDetailAdapter directionAdapter = new MoreDetailAdapter(context, directions);
	    
	    lstDetail.setTag(0);
	    lstType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:  //  排序
					lstDetail.setTag(0);
					lstDetail.setAdapter(sortAdapter);
					break;
				case 1:  //  面积
					lstDetail.setTag(1);
					lstDetail.setAdapter(squareAdapter);
					break;
				case 2:  //  朝向
					lstDetail.setTag(2);
					lstDetail.setAdapter(directionAdapter);
					break;
				}
			}
		});
	    
	    lstDetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int tag = (Integer) lstDetail.getTag();
				switch (tag) {
				case 0:  //  排序
					if (sorts.get(position).isSelected()) {
						sorts.get(position).setSelected(false);
					} else {
						for (int i = 0; i < sorts.size(); i++) {
							if (i == position) {
								sorts.get(i).setSelected(true);
							} else {
								sorts.get(i).setSelected(false);
							}
						}
					}
					sortAdapter.notifyDataSetChanged();
					break;
				case 1:  //  面积
					if (squares.get(position).isSelected()) {
						squares.get(position).setSelected(false);
					} else {
						for (int i = 0; i < squares.size(); i++) {
							if (i == position) {
								squares.get(i).setSelected(true);
							} else {
								squares.get(i).setSelected(false);
							}
						}
					}
					squareAdapter.notifyDataSetChanged();
					break;
				case 2:  //  朝向
					if (directions.get(position).isSelected()) {
						directions.get(position).setSelected(false);
					} else {
						for (int i = 0; i < directions.size(); i++) {
							if (i == position) {
								directions.get(i).setSelected(true);
							} else {
								directions.get(i).setSelected(false);
							}
						}
					}
					directionAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
	    
	    tvOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextValueBean sortCondition = null;
				TextValueBean squareCondition = null;
				TextValueBean directionCondition = null;
				for (TextValueBean tvb : sorts) {  // 排序
					if (tvb.isSelected()) {
						sortCondition = tvb;
						break;
					}
				}
				for (TextValueBean tvb : squares) {  // 面积
					if (tvb.isSelected()) {
						squareCondition = tvb;
						break;
					}
				}
				for (TextValueBean tvb : directions) {  // 售卖状态
					if (tvb.isSelected()) {
						directionCondition = tvb;
						break;
					}
				}
				
				popupWindow.dismiss();
				if (onRentHouseMoreConditionListener != null) {
					onRentHouseMoreConditionListener.onRentHouseMoreConditon(
							sortCondition, squareCondition, directionCondition);
				}
			}
		});
	}
	
	
	public interface OnMapFindHouseMoreConditionListener {
		void onMapFindHouseMoreConditon(TextValueBean typeConditionData,
				TextValueBean labelConditionData, TextValueBean saleStatusConditonData);
	}
	/**
	 * 地图找房更多选择
	 */
	public static void showMapFindHouseMorePopWindow(Context context, ArrayList<String> types,
			final ArrayList<TextValueBean> propertyTypes, final ArrayList<TextValueBean> labels,
			final ArrayList<TextValueBean> saleStatus,
		    View anchorView, final OnMapFindHouseMoreConditionListener onMapFindHouseMoreConditionListener) {
		View view = View.inflate(context, R.layout.popup_second_hand_house_more, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
	    TextView tvOk = (TextView) view.findViewById(R.id.popup_second_hand_more_type_ok);
	    
	    MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(context, types);
	    lstType.setAdapter(moreTypeAdapter);
	    // 类型Adapter
	    // TODO  具体什么类型，接口文档没说清楚，要问
	    final MoreDetailAdapter typeAdapter = new MoreDetailAdapter(context, propertyTypes);
	    lstDetail.setAdapter(typeAdapter);
	    // 特色标签Adapter
	    final MoreDetailAdapter labelAdapter = new MoreDetailAdapter(context, labels);
	    // 售卖状态Adapter
	    final MoreDetailAdapter saleStatusAdapter = new MoreDetailAdapter(context, saleStatus);
	    
	    lstDetail.setTag(0);
	    lstType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:  //  类型
					lstDetail.setTag(0);
					lstDetail.setAdapter(typeAdapter);
					break;
				case 1:  //  特色标签
					lstDetail.setTag(1);
					lstDetail.setAdapter(labelAdapter);
					break;
				case 2:  //  售卖状态
					lstDetail.setTag(2);
					lstDetail.setAdapter(saleStatusAdapter);
					break;
				}
			}
		});
	    
	    lstDetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int tag = (Integer) lstDetail.getTag();
				switch (tag) {
				case 0:  //  类型
					if (propertyTypes.get(position).isSelected()) {
						propertyTypes.get(position).setSelected(false);
					} else {
						for (int i = 0; i < propertyTypes.size(); i++) {
							if (i == position) {
								propertyTypes.get(i).setSelected(true);
							} else {
								propertyTypes.get(i).setSelected(false);
							}
						}
					}
					typeAdapter.notifyDataSetChanged();
					break;
				case 1:  //  特色标签
					if (labels.get(position).isSelected()) {
						labels.get(position).setSelected(false);
					} else {
						for (int i = 0; i < labels.size(); i++) {
							if (i == position) {
								labels.get(i).setSelected(true);
							} else {
								labels.get(i).setSelected(false);
							}
						}
					}
					labelAdapter.notifyDataSetChanged();
					break;
				case 2:  //  售卖状态
					if (saleStatus.get(position).isSelected()) {
						saleStatus.get(position).setSelected(false);
					} else {
						for (int i = 0; i < saleStatus.size(); i++) {
							if (i == position) {
								saleStatus.get(i).setSelected(true);
							} else {
								saleStatus.get(i).setSelected(false);
							}
						}
					}
					saleStatusAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
	    
	    tvOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextValueBean typeCondition = null;
				TextValueBean labelCondition = null;
				TextValueBean saleStatusCondition = null;
				for (TextValueBean tvb : propertyTypes) {  // 类型
					if (tvb.isSelected()) {
						typeCondition = tvb;
						break;
					}
				}
				for (TextValueBean tvb : labels) {  // 特色标签
					if (tvb.isSelected()) {
						labelCondition = tvb;
						break;
					}
				}
				for (TextValueBean tvb : saleStatus) {  // 售卖状态
					if (tvb.isSelected()) {
						saleStatusCondition = tvb;
						break;
					}
				}
				
				popupWindow.dismiss();
				if (onMapFindHouseMoreConditionListener != null) {
					onMapFindHouseMoreConditionListener.onMapFindHouseMoreConditon(
							typeCondition, labelCondition, saleStatusCondition);
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
		View view = View.inflate(context, R.layout.popup_fee_hunter_my_customer, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, DevUtils.getScreenTools(context).dip2px(200));
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstConditionLeft = (ListView) view.findViewById(R.id.popup_fee_hunter_my_customer_left_list);
	    final ListView lstConditionRight = (ListView) view.findViewById(R.id.popup_fee_hunter_my_customer_right_list);
	    
	    MyCustomerConditionAdapter leftAdapter = new MyCustomerConditionAdapter(context, conditions);
	    lstConditionLeft.setAdapter(leftAdapter);
	    
	    final ArrayList<FeeHunterMyCustomerConditionBean> rightListData = new ArrayList<FeeHunterMyCustomerConditionBean>();
	    
	    if (conditions.size() > 0) {
	    	rightListData.addAll(conditions.get(0).getChildren());
	    }
	    final MyCustomerConditionAdapter rightAdapter = new MyCustomerConditionAdapter(context, rightListData);
	    lstConditionRight.setAdapter(rightAdapter);
	    
	    lstConditionLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                if (position == 0) {
                	popupWindow.dismiss();
                	FeeHunterMyCustomerConditionBean allClient = conditions.get(0);
    				if (onMyCustomerConditionSelectListener != null) {
    					onMyCustomerConditionSelectListener.onMyCustomerConditonSelect(allClient);
    				}
				} else {
					rightListData.clear();
					rightListData.addAll(conditions.get(position).getChildren());
					rightAdapter.notifyDataSetChanged();
				}
				
//				if (onMyCustomerConditionSelectListener != null) {
//					onMyCustomerConditionSelectListener.onMyCustomerConditonSelect(conditions.get(position));
//				}
//				popupWindow.dismiss();
			}
		});
	    
	    lstConditionRight.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    popupWindow.dismiss();
				FeeHunterMyCustomerConditionBean rightData = rightListData.get(position);
				if (onMyCustomerConditionSelectListener != null) {
					onMyCustomerConditionSelectListener.onMyCustomerConditonSelect(rightData);
				}
			}
		});
	    
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
	 * 咨讯更多
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
	
	/**
	 * 小区成交记录筛选条件选择
	 * @param context
	 * @param anchorView
	 */
	public static void showSelectTransactionHistoryFilterPopWindow(Context context, View anchorView,
			final ArrayList<TextValueBean> filters, final OnConditionSelectListener onConditionSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    ConditionAdapter adapter = new ConditionAdapter(context, filters);
	    lstCondition.setAdapter(adapter);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onConditionSelectListener != null) {
					onConditionSelectListener.onConditionSelect(filters.get(position));
				}
			}
		});
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
	}
	
	public interface OnShareTypeSelectListener {
		public static final int Share_Type_WeiXin = 1;
		
		public static final int Share_Type_QQ = 2;
		
		public static final int Share_Type_Sina_Weibo = 3;
		
		public static final int Share_Type_WeiXin_Friend = 4;
		
		void onShareTypeSelected(int shareType);
	}
	/**
	 * 分享弹出框
	 * @param context
	 * @param anchorView
	 */
	public static void showSharePopupWindow(Context context, View anchorView, final OnShareTypeSelectListener onShareTypeSelectListener) {
		View view = View.inflate(context, R.layout.popwindow_share, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, DevUtils.getScreenTools(context).dip2px(140));
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    popupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
	    
	    TextView tvShareByWeixin = (TextView) view.findViewById(R.id.popwindow_share_weixin);
	    AutoDrawableTextView tvShareByWeixinFriend = (AutoDrawableTextView) view.findViewById(R.id.popwindow_share_weixin_friend);
	    TextView tvShareByQQ = (TextView) view.findViewById(R.id.popwindow_share_qq);
	    TextView tvShareBySinaWeibo = (TextView) view.findViewById(R.id.popwindow_share_sina_weibo);
	    
	    OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.popwindow_share_weixin:
					popupWindow.dismiss();
					if (onShareTypeSelectListener != null) {
						onShareTypeSelectListener.onShareTypeSelected(OnShareTypeSelectListener.Share_Type_WeiXin);
					}
					break;
				case R.id.popwindow_share_qq:
					if (onShareTypeSelectListener != null) {
						onShareTypeSelectListener.onShareTypeSelected(OnShareTypeSelectListener.Share_Type_QQ);
					}
					break;
				case R.id.popwindow_share_sina_weibo:
					if (onShareTypeSelectListener != null) {
						onShareTypeSelectListener.onShareTypeSelected(OnShareTypeSelectListener.Share_Type_Sina_Weibo);
					}
					break;
				case R.id.popwindow_share_weixin_friend:
				    if (onShareTypeSelectListener != null) {
                        onShareTypeSelectListener.onShareTypeSelected(OnShareTypeSelectListener.Share_Type_WeiXin_Friend);
                    }
				    break;
				}
			}
		};
		
		tvShareByWeixin.setOnClickListener(clickListener);
		tvShareByQQ.setOnClickListener(clickListener);
		tvShareBySinaWeibo.setOnClickListener(clickListener);
		tvShareByWeixinFriend.setOnClickListener(clickListener);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
	}
	
	//==========================更换头像【start】===========================================
    public interface OnAvatarOptionsClickListener {
        public static final int ACTION_CAMERA = 0;
        public static final int ACTION_ALBUM = 1;
        
        /**
         * 选择从相机或者系统相册更改头像
         * @param action
         */
        void onAvatarOptionClick(int action);
    }
    public static void showUpdateAvatarPopupWindow(Context context, View anchor, final OnAvatarOptionsClickListener listener) {
    	
    	final Dialog dialog = new Dialog(context, R.style.DefaultDialogTheme);
        dialog.setContentView(R.layout.popup_updateavatar);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        win.setWindowAnimations(R.style.timepopwindow_anim_style);
        win.setGravity(Gravity.BOTTOM);
        
//        final PopupWindow avatarPopupWindow = new PopupWindow(context);
//        LinearLayout contentView = (LinearLayout) View.inflate(context, R.layout.popup_updateavatar, null);
//        avatarPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        avatarPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        
//        avatarPopupWindow.setContentView(contentView);
//        avatarPopupWindow.setFocusable(true);
//        avatarPopupWindow.setOutsideTouchable(true);
//        
//        ColorDrawable dw = new ColorDrawable(color.transparent);
////        avatarPopupWindow.setBackgroundDrawable(dw);
//        avatarPopupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
        
        OnClickListener clickListener = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    switch (v.getId()) {
                    case R.id.popup_update_avatar_from_camera_tv:
                        listener.onAvatarOptionClick(OnAvatarOptionsClickListener.ACTION_CAMERA);
                        break;
                    case R.id.popup_update_avatar_from_album_tv:
                        listener.onAvatarOptionClick(OnAvatarOptionsClickListener.ACTION_ALBUM);
                        break;
                    }
                }
                dialog.dismiss();
            }
        };
        
        TextView tvCamera = (TextView) dialog.findViewById(R.id.popup_update_avatar_from_camera_tv);
        TextView tvAlbum = (TextView) dialog.findViewById(R.id.popup_update_avatar_from_album_tv);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.popup_exit_account_cancel_tv);
        tvCamera.setOnClickListener(clickListener);
        tvAlbum.setOnClickListener(clickListener);
        tvCancel.setOnClickListener(clickListener);
        dialog.show();
//        avatarPopupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }
//==========================更换头像【end】===========================================
    
  //==========================二手房详情   更多（分享  关注）【start】===========================================
    public interface OnMoreShareAndAttentionListener {
		void onShare();
		void onAttention();
	}
	/**
	 * 选择贷款方式  等额本金  等额本息
	 * @param context
	 * @param anchorView
	 */
	public static void showMoreShareAndAttention(Context context, View anchorView, boolean isShowAttention,
			final OnMoreShareAndAttentionListener listener) {
		View view = View.inflate(context, R.layout.popup_more_share_attention, null);
		final PopupWindow popupWindow = new PopupWindow(view, DevUtils.getScreenTools(context).dip2px(140), LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
//	    popupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
	    
	    TextView tvShare = (TextView) view.findViewById(R.id.popwindow_more_share);
	    TextView tvAttention = (TextView) view.findViewById(R.id.popwindow_more_attention);
	    
	    if (isShowAttention) {
	    	tvAttention.setVisibility(View.VISIBLE);
	    } else {
	    	tvAttention.setVisibility(View.GONE);
	    }
	    
	    OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.popwindow_more_share:
					popupWindow.dismiss();
					if (listener != null) {
						listener.onShare();
					}
					break;
				case R.id.popwindow_more_attention:
					popupWindow.dismiss();
					if (listener != null) {
						listener.onAttention();
					}
					break;
				}
			}
		};
		
		tvShare.setOnClickListener(clickListener);
		tvAttention.setOnClickListener(clickListener);
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
        	popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
//            popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
	}
	
	//==========================二手房详情   更多（分享  关注）【end】===========================================
	
	
	public interface OnBankProvinceOrCitySelectedListener {
		void onBankProvinceOrCitySelect(ProvinceCityBean data);
	}
	/**
     * 选择银行卡省或者城市
     */
    public static void showSelectBankProvinceOrCityPopWindow(Context context, View anchorView,
            final ArrayList<ProvinceCityBean> conditions, final OnBankProvinceOrCitySelectedListener onBankProvinceOrCitySelectedListener) {
        View view = View.inflate(context, R.layout.popup_condition, null);
        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        
        ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
        BankProvinceOrCityAdapter adapter = new BankProvinceOrCityAdapter(context, conditions);
        
        lstCondition.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                popupWindow.dismiss();
                if (onBankProvinceOrCitySelectedListener != null) {
                	onBankProvinceOrCitySelectedListener.onBankProvinceOrCitySelect(conditions.get(position));
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
    
    public interface OnBankNameSelectedListener {
		void onBankNameSelect(String bankName);
	}
	/**
     * 选择银行卡省或者城市
     */
    public static void showSelectBankNamePopWindow(Context context, View anchorView,
            final ArrayList<String> conditions, final OnBankNameSelectedListener onBankNameSelectedListener) {
        View view = View.inflate(context, R.layout.popup_condition, null);
        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        
        ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
        BankNameAdapter adapter = new BankNameAdapter(context, conditions);
        
        lstCondition.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                popupWindow.dismiss();
                if (onBankNameSelectedListener != null) {
                	onBankNameSelectedListener.onBankNameSelect(conditions.get(position));
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
    
  //==========================赞对话框【start】===========================================
    public interface OnProxyDialogListener {
        void onCancel();
        void onGoToSee();
    }
    
    /**弹出赞对话框
     * @param context
     */
    public static void showProxyDialog(final Context context, final OnProxyDialogListener onProxyDialogListener) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialogTheme);
        dialog.setContentView(R.layout.dialog_proxy);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        win.setGravity(Gravity.CENTER);
        
        TextView tvCancel = (TextView) dialog.findViewById(R.id.dialog_proxy_cancel);
        TextView tvGo = (TextView) dialog.findViewById(R.id.dialog_proxy_go);
        
        OnClickListener listener = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                switch (v.getId()) {
                case R.id.dialog_proxy_cancel:
                	dialog.dismiss();
                    break;
                case R.id.dialog_proxy_go:
                	if (onProxyDialogListener != null) {
                		onProxyDialogListener.onGoToSee();
                	}
                    break;
                }
            }
        };
        tvCancel.setOnClickListener(listener);
        tvGo.setOnClickListener(listener);
        
        dialog.show();
    }
    
    public interface OnBuildingSelectListener {
		void onBuildingSelect(IdNameBean idNameBean);
	}
	
	/**
	 * 推荐房源选择几栋
	 */
	public static void showSelectBuildingPopWindow(Context context, View anchorView,
			final ArrayList<IdNameBean> buildings, final OnBuildingSelectListener onBuildingSelectListener) {
		View view = View.inflate(context, R.layout.popup_condition, null);
		final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
	    popupWindow.setOutsideTouchable(true);
	    popupWindow.update();
	    ColorDrawable dw = new ColorDrawable(0000000000);
	    popupWindow.setBackgroundDrawable(dw);
	    
	    ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
	    BuildingsAdapter adapter = new BuildingsAdapter(context, buildings);
	    lstCondition.setAdapter(adapter);
	    lstCondition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();
				if (onBuildingSelectListener != null) {
					onBuildingSelectListener.onBuildingSelect(buildings.get(position));
				}
			}
		});
	    
	    if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, 0, 0);
            
//            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
            
//            popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
	}
	
	public interface OnEstateCellSelectListener {
        void onEstateCellSelect(IdNameBean idNameBean);
    }
    
    /**
     * 推荐房源选择几单元
     */
    public static void showSelectEstateCellPopWindow(Context context, View anchorView,
            final ArrayList<IdNameBean> cells, final OnEstateCellSelectListener onEstateCellSelectListener) {
        View view = View.inflate(context, R.layout.popup_condition, null);
        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        
        ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
        BuildingsAdapter adapter = new BuildingsAdapter(context, cells);
        lstCondition.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                popupWindow.dismiss();
                if (onEstateCellSelectListener != null) {
                    onEstateCellSelectListener.onEstateCellSelect(cells.get(position));
                }
            }
        });
        lstCondition.setAdapter(adapter);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, 0, 0);
//            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
    }
    
    public interface OnEstateRoomSelectListener {
        void onEstateRoomSelect(IdNameFloorBean idNameBean);
    }
    
    /**
     * 推荐房源选择几号房间
     */
    public static void showSelectEstateRoomPopWindow(Context context, View anchorView,
            final ArrayList<IdNameFloorBean> rooms, final OnEstateRoomSelectListener onEstateRoomSelectListener) {
        View view = View.inflate(context, R.layout.popup_condition, null);
        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        
        ListView lstCondition = (ListView) view.findViewById(R.id.popup_condition_lst);
        EstateRoomAdapter adapter = new EstateRoomAdapter(context, rooms);
        lstCondition.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                popupWindow.dismiss();
                if (onEstateRoomSelectListener != null) {
                    onEstateRoomSelectListener.onEstateRoomSelect(rooms.get(position));
                }
            }
        });
        lstCondition.setAdapter(adapter);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(anchorView, 0, 0);
//            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
        }
    }
    
    public interface OnHouseDetailAgentActionListener {
        void onHouseDetailAgentAction(int actionId);
    }
    
    public static void showHouseDetailAgentWindow(Context context, View anchorView, AgentBean agentBean, 
    		final OnHouseDetailAgentActionListener onHouseDetailAgentActionListener) {
    	View view = View.inflate(context, R.layout.popup_house_detail_agent, null);
        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        ColorDrawable dw = new ColorDrawable(00000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
        
        FrameLayout fltWindow = (FrameLayout) view.findViewById(R.id.flt_pop_house_detail_agent);
        LinearLayout lltAgent = (LinearLayout) view.findViewById(R.id.llt_pop_house_detail_agent);
        PathImage agentAvatar = (PathImage) view.findViewById(R.id.iv_pop_house_detail_agent_avatar);
        TextView tvName = (TextView) view.findViewById(R.id.tv_pop_house_detail_agent_name);
        TextView tvPhoneNum = (TextView) view.findViewById(R.id.tv_pop_house_detail_agent_phone);
        ImageView ivCall = (ImageView) view.findViewById(R.id.iv_pop_house_detail_agent_call);
        ImageView ivMsg = (ImageView) view.findViewById(R.id.iv_pop_house_detail_agent_msg);
        if (agentBean != null) {
        	ImageAction.displayBrokerAvatar(agentBean.getPhoto(),
    				agentAvatar);
        	tvName.setText(agentBean.getName());
        	tvPhoneNum.setText(agentBean.getTel());
        }
        
        
        OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.iv_pop_house_detail_agent_call:
					if (onHouseDetailAgentActionListener != null) {
						onHouseDetailAgentActionListener.onHouseDetailAgentAction(1);
					}
					break;
				case R.id.iv_pop_house_detail_agent_msg:
					if (onHouseDetailAgentActionListener != null) {
						onHouseDetailAgentActionListener.onHouseDetailAgentAction(2);
					}
					break;
				case R.id.llt_pop_house_detail_agent:
					if (onHouseDetailAgentActionListener != null) {
						onHouseDetailAgentActionListener.onHouseDetailAgentAction(3);
					}
					break;
				}
				popupWindow.dismiss();
			}
		};
		
		ivCall.setOnClickListener(listener);
		ivMsg.setOnClickListener(listener);
		fltWindow.setOnClickListener(listener);
		lltAgent.setOnClickListener(listener);
		
		if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
//            popupWindow.showAsDropDown(anchorView, 0, 0);
//            popupWindow.showAsDropDown(anchorView, (anchorView.getWidth() - popupWindow.getWidth()) / 2, 0);
            popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, DevUtils.getScreenTools(context).dip2px(140));
        }
		
    }
    
    
  //==========================经纪人店铺咨询【start】===========================================
    public interface OnBrokerConsultClickListener {
        public static final int ACTION_PHONE = 0;
        public static final int ACTION_CHAT = 1;
        public static final int ACTION_SMS = 2;
        
        /**
         * 经纪人店铺咨询
         * @param action
         */
        void onAvatarOptionClick(int action);
    }
    public static void showBrokerConsultPopupWindow(Context context, View anchor, final OnBrokerConsultClickListener listener) {
        
    	final Dialog dialog = new Dialog(context, R.style.DefaultDialogTheme);
        dialog.setContentView(R.layout.popup_broker_info_consult);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
        win.setGravity(Gravity.BOTTOM);
    	
    	
//        final PopupWindow brokerInfoConsultPopupWindow = new PopupWindow(context);
//        FrameLayout contentView = (FrameLayout) View.inflate(context, R.layout.popup_broker_info_consult, null);
//        brokerInfoConsultPopupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//        brokerInfoConsultPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        
//        brokerInfoConsultPopupWindow.setContentView(contentView);
//        brokerInfoConsultPopupWindow.setFocusable(true);
//        brokerInfoConsultPopupWindow.setOutsideTouchable(true);
//        
//        ColorDrawable dw = new ColorDrawable(color.transparent);
////        avatarPopupWindow.setBackgroundDrawable(dw);
//        brokerInfoConsultPopupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
        
        OnClickListener clickListener = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    switch (v.getId()) {
                    case R.id.tv_popup_broker_consult_phone_call:
                        listener.onAvatarOptionClick(OnBrokerConsultClickListener.ACTION_PHONE);
                        break;
                    case R.id.tv_popup_broker_consult_chat:
                        listener.onAvatarOptionClick(OnBrokerConsultClickListener.ACTION_CHAT);
                        break;
                    case R.id.tv_popup_broker_consult_sms:
                    	listener.onAvatarOptionClick(OnBrokerConsultClickListener.ACTION_SMS);
                    	break;
                    case R.id.llt_pop_broker_info_consult:
                    	break;
                    }
                }
                dialog.dismiss();
            }
        };
        
        AutoDrawableTextView tvPhoneCall = (AutoDrawableTextView) dialog.findViewById(R.id.tv_popup_broker_consult_phone_call);
        AutoDrawableTextView tvChat = (AutoDrawableTextView) dialog.findViewById(R.id.tv_popup_broker_consult_chat);
        AutoDrawableTextView tvSMS = (AutoDrawableTextView) dialog.findViewById(R.id.tv_popup_broker_consult_sms);
        LinearLayout lltConsult = (LinearLayout) dialog.findViewById(R.id.llt_pop_broker_info_consult);
        tvPhoneCall.setOnClickListener(clickListener);
        tvChat.setOnClickListener(clickListener);
        tvSMS.setOnClickListener(clickListener);
        lltConsult.setOnClickListener(clickListener);
        dialog.show();
//        dialog.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }
//==========================经纪人店铺咨询【end】===========================================
    
  //==========================房源列表 排序【start】===========================================
    public interface OnHouseSourceSortTypeClickListener {
        
        /**
         * 房源列表 排序
         * @param sortType
         */
        void onHouseSourceSortTypeClick(FieldNameValueBean sortType);
    }
    public static void showHouseSourceSortTypeDialog(Context context, final ArrayList<FieldNameValueBean> sortParamList, final OnHouseSourceSortTypeClickListener listener) {
        
    	final Dialog dialog = new Dialog(context, R.style.DefaultDialogTheme);
        dialog.setContentView(R.layout.dialog_house_source_sort_type);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.timepopwindow_anim_style);
    	
        ListView lstSortType = (ListView) dialog.findViewById(R.id.lst_adapter_source_sort_type);
        final HouseSourceSortTypeAdapter adapter = new HouseSourceSortTypeAdapter(context, sortParamList);
        lstSortType.setAdapter(adapter);
        
        lstSortType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FieldNameValueBean temp = sortParamList.get(position);
				temp.setSelected(!temp.isSelected());
				if (temp.isSelected()) {
					int size = sortParamList.size();
					for (int i = 0; i < size; i++) {
						if (i != position) {
							sortParamList.get(i).setSelected(false);
						}
					}
				}
				adapter.notifyDataSetChanged();
				
				if (temp.isSelected() && listener != null) {
					listener.onHouseSourceSortTypeClick(temp);
					dialog.dismiss();
				}
			}
		});
        
        
        dialog.show();
    }
//==========================房源列表 排序【end】===========================================
}
