package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.NameValueBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HouseSourceParamAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NameValueBean> houseSourceParams;
	
	public HouseSourceParamAdapter(Context context, ArrayList<NameValueBean> houseSourceParams) {
		this.context = context;
		this.houseSourceParams = houseSourceParams;
	}
	@Override
	public int getCount() {
		if (houseSourceParams == null) {
			return 0;
		}
		return houseSourceParams.size();
	}

	@Override
	public Object getItem(int position) {
		return houseSourceParams.get(position);
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
		
		NameValueBean nameValueBean = houseSourceParams.get(position);
		tvConditionName.setText(nameValueBean.getName());
		
		if (nameValueBean.isSelected()) {
			tvConditionName.setTextColor(context.getResources().getColor(R.color.color_0ed86c));
		} else {
			tvConditionName.setTextColor(context.getResources().getColor(R.color.color_cfcfcf));
		}
		
		return convertView;
	}

}
