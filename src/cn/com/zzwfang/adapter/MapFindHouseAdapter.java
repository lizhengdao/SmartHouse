//package cn.com.zzwfang.adapter;
//
//import java.util.ArrayList;
//
//import cn.com.zzwfang.R;
//import cn.com.zzwfang.action.ImageAction;
//import cn.com.zzwfang.bean.SearchHouseItemBean;
//import cn.com.zzwfang.bean.SecondHandHouseBean;
//import cn.com.zzwfang.util.DateUtils;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///**
// * 地图找房列表    默认显示二手房列表
// * @author MISS-万
// *
// */
//public class MapFindHouseAdapter extends BaseAdapter { 
//	private Context context;
//	private ArrayList<SearchHouseItemBean> estates;
//	
//	public MapFindHouseAdapter(Context context, ArrayList<SearchHouseItemBean> estates) {
//		this.context = context;
//		this.estates =  estates;
//	}
//	
//	@Override
//	public int getCount() {
//		if (estates == null) {
//			return 0;
//		}
//		return estates.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return estates.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			convertView = View.inflate(context, R.layout.adapter_second_hand_house, null);
//		}
//		SearchHouseItemBean secondHandHouseBean = estates.get(position);
//		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.adapter_second_hand_house_photo);
//		ImageAction.displayImage(secondHandHouseBean.getPhoto(), imgPhoto);
//		TextView tvTitle  = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_title);
//		tvTitle.setText(secondHandHouseBean.getName());
//		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_desc);
//		String desc = secondHandHouseBean.getTypeF() + "室" + 
//		secondHandHouseBean.getTypeT() + "厅    " + secondHandHouseBean.getDirection() + "   "  +
//		secondHandHouseBean.getStructure() + "/" + secondHandHouseBean.getFloor() + "层";
//		tvDesc.setText(desc);
//		
//		TextView tvEstName = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_est_name);
//		tvEstName.setText(secondHandHouseBean.getEstName());
//		
//		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_total_price);
//		tvTotalPrice.setText(secondHandHouseBean.getPrice() + "万");
//		
//		TextView tvPublishTime = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_publish_time);
//		String time = DateUtils.formatDate(secondHandHouseBean.getAddTime());
//		tvPublishTime.setText(time);
//		
//		
//		return convertView;
//	}
//
//}
