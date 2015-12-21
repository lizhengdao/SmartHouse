package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 搜索列表（现在没用了）
 * @author lzd
 *
 */
public class SearchHouseAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<SearchHouseItemBean> searchHouses;
	
	public SearchHouseAdapter(Context context, ArrayList<SearchHouseItemBean> searchHouses) {
		this.context = context;
		this.searchHouses = searchHouses;
	}
	
	@Override
	public int getCount() {
		if (searchHouses == null) {
			return 0;
		}
		return searchHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return searchHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_search_house, null);
		}
		
		SearchHouseItemBean searchHouseItemBean = searchHouses.get(position);
		
		ImageView imgPhoto = ViewHolder.get(convertView, R.id.adapter_search_house_photo);
		TextView tvTitle  = ViewHolder.get(convertView, R.id.adapter_search_house_title);
		TextView tvTotalPrice = ViewHolder.get(convertView, R.id.adapter_search_house_rent_price);
		
		ImageAction.displayImage(searchHouseItemBean.getPhoto(), imgPhoto);
		tvTitle.setText(searchHouseItemBean.getName());
//		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_search_house_desc);
//		String desc = rentHouseBean.getTypeF() + "室" + 
//				rentHouseBean.getTypeT() + "厅    " + rentHouseBean.getSquare() + "㎡  " + rentHouseBean.getDirection();
//		tvDesc.setText(desc);
		
		tvTotalPrice.setText(searchHouseItemBean.getPrpAvg() + "万");
		
//		TextView tvPublishTime = (TextView) convertView.findViewById(R.id.adapter_search_house_publish_time);
//		tvPublishTime.setText(searchHouseItemBean.getAddTime());
		
		return convertView;
	}

}
