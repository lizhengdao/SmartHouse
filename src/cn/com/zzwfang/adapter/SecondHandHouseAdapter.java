package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.SecondHandHouseBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SecondHandHouseAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<SecondHandHouseBean> secondHandHouses;
	
	public SecondHandHouseAdapter(Context context, ArrayList<SecondHandHouseBean> secondHandHouses) {
		this.context = context;
		this.secondHandHouses = secondHandHouses;
	}
	
	@Override
	public int getCount() {
		if (secondHandHouses == null) {
			return 0;
		}
		return secondHandHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return secondHandHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_second_hand_house, null);
		}
		return convertView;
	}

}
