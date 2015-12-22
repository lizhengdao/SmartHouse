package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<CityBean> cities;
	
	public CityListAdapter(Context context, ArrayList<CityBean> cities) {
		this.context = context;
		this.cities = cities;
	}
	@Override
	public int getCount() {
		if (cities == null) {
			return 0;
		}
		return cities.size();
	}

	@Override
	public Object getItem(int position) {
		return cities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_city_list, null);
		}
		
		TextView tvCityName = ViewHolder.get(convertView, R.id.adapter_city_name_tv);
		tvCityName.setText(cities.get(position).getName());
		return convertView;
	}

}
