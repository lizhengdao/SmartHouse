package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zzwfang.R;

public class MoreTypeAdapter extends BaseAdapter {

	private ArrayList<String> types;
	
	private Context context;
	
	public MoreTypeAdapter(Context context, ArrayList<String> types) {
		this.context = context;
		this.types = types;
	}
	
	@Override
	public int getCount() {
		if (types == null) {
			return 0;
		}
		return types.size();
	}

	@Override
	public Object getItem(int position) {
		return types.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_more_type, null);
		}
		
		TextView tvTypeName = ViewHolder.get(convertView, R.id.adapter_more_type_txt);
		
		tvTypeName.setText(types.get(position));
		return convertView;
	}

}
