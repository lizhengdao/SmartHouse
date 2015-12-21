package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.FeeHunterMyCustomerConditionBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyCustomerConditionAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<FeeHunterMyCustomerConditionBean> conditions;
	
	public MyCustomerConditionAdapter(Context context, ArrayList<FeeHunterMyCustomerConditionBean> conditions) {
		this.context = context;
		this.conditions = conditions;
	}
	
	@Override
	public int getCount() {
		if (conditions == null) {
			return 0;
		}
		return conditions.size();
	}

	@Override
	public Object getItem(int position) {
		return conditions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_my_customer_condition, null);
		}
		
		FeeHunterMyCustomerConditionBean condition = conditions.get(position);
		
		TextView tvCustomerConditon = ViewHolder.get(convertView, R.id.adapter_my_customer_condition);
		
		tvCustomerConditon.setText(condition.getName());
		
		return convertView;
	}

}
