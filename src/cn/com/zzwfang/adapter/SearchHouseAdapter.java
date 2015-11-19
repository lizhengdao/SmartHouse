package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
		
		return convertView;
	}

}
