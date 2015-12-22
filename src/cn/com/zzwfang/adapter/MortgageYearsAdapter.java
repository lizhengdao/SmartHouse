package cn.com.zzwfang.adapter;

import cn.com.zzwfang.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MortgageYearsAdapter extends BaseAdapter {

	
	private Context context;
	
	private String [] years;
	
	public MortgageYearsAdapter(Context context, String [] years) {
		this.context = context;
		this.years = years;
	}
	
	@Override
	public int getCount() {
		if (years == null) {
			return 0;
		}
		return years.length;
	}

	@Override
	public Object getItem(int position) {
		return years[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_mortgage_years_item, null);
		}
		
		TextView tvYear = ViewHolder.get(convertView, R.id.adapter_mortgage_year_tv);
		
		tvYear.setText(years[position]);
		return convertView;
	}

}
