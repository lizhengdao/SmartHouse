package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BankNameAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<String> bankNames;
	
	public BankNameAdapter(Context context, ArrayList<String> bankNames) {
		this.context = context;
		this.bankNames = bankNames;
	}
	@Override
	public int getCount() {
		if (bankNames == null) {
			return 0;
		}
		return bankNames.size();
	}

	@Override
	public Object getItem(int position) {
		return bankNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_bank_province_or_city, null);
		}
		
		String temp = bankNames.get(position);
		
		TextView tvBankProvinceOrCity = ViewHolder.get(convertView, R.id.adapter_bank_province_or_city);
    	tvBankProvinceOrCity.setText(temp);
		return convertView;
	}

}
