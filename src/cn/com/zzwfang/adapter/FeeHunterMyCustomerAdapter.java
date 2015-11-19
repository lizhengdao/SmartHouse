package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.FeeHunterMyCustomerBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeeHunterMyCustomerAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<FeeHunterMyCustomerBean> myCustomers;
	
	public FeeHunterMyCustomerAdapter(Context context, ArrayList<FeeHunterMyCustomerBean> myCustomers) {
		this.context = context;
		this.myCustomers = myCustomers;
	}
	
	@Override
	public int getCount() {
//		if (myCustomers == null) {
//			return 0;
//		}
//		return myCustomers.size();
		
		return 3;
	}

	@Override
	public Object getItem(int position) {
//		return myCustomers.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_fee_hunter_my_customer, null);
		}
		TextView tvName = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_my_customer_name);
		TextView tvPhone = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_my_customer_phone);
		TextView tvEstate = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_my_customer_estate);
		TextView tvCheck = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_my_customer_check);
		return convertView;
	}

}
