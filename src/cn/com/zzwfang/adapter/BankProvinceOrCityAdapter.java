package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.ProvinceCityBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BankProvinceOrCityAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<ProvinceCityBean> data;
	
	public BankProvinceOrCityAdapter(Context context, ArrayList<ProvinceCityBean> data) {
		this.context = context;
		this.data = data;
	}
	
    @Override
    public int getCount() {
    	if (data == null) {
    		return 0;
    	}
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
    	
    	ProvinceCityBean temp = data.get(position);
    	
    	TextView tvBankProvinceOrCity = ViewHolder.get(convertView, R.id.adapter_bank_province_or_city);
    	tvBankProvinceOrCity.setText(temp.getName());
        return convertView;
    }

}
