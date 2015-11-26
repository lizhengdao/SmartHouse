package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IdNameBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EstateSelectAdapter extends BaseAdapter {

	
	private Context context;
	private ArrayList<IdNameBean> estates;
	
	public EstateSelectAdapter(Context context, ArrayList<IdNameBean> estates) {
		this.context = context;
		this.estates = estates;
	}
	
	@Override
	public int getCount() {
		if (estates == null) {
			return 0;
		}
		return estates.size();
	}

	@Override
	public Object getItem(int position) {
		return estates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_estate_select, null);
		}
		
		IdNameBean idNameBean = estates.get(position);
		TextView tvName = (TextView) convertView.findViewById(R.id.adapter_estate_select_name);
		tvName.setText(idNameBean.getName());
		return convertView;
	}

}
