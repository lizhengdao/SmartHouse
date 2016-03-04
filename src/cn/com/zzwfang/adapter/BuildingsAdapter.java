package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IdNameBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BuildingsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<IdNameBean> buildings;
	
	public BuildingsAdapter(Context context, ArrayList<IdNameBean> buildings) {
		this.context = context;
		this.buildings = buildings;
	}
	
	@Override
	public int getCount() {
		if (buildings == null) {
			return 0;
		}
		return buildings.size();
	}

	@Override
	public Object getItem(int position) {
		return buildings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_condition_item, null);
		}
		
		TextView tvConditionName = ViewHolder.get(convertView, R.id.adapter_conditon_name_tv);
		tvConditionName.setText(buildings.get(position).getName());
		return convertView;
	}

}
