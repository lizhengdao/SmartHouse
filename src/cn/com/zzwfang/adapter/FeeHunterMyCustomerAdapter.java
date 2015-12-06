package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.FeeHunterProgressDetailActivity;
import cn.com.zzwfang.bean.FeeHunterRecommendClientBean;
import cn.com.zzwfang.bean.FeeHunterRecommendHouseSourceListItem;
import cn.com.zzwfang.util.Jumper;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeeHunterMyCustomerAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<FeeHunterRecommendClientBean> myCustomers;
	
	public FeeHunterMyCustomerAdapter(Context context, ArrayList<FeeHunterRecommendClientBean> myCustomers) {
		this.context = context;
		this.myCustomers = myCustomers;
	}
	
	@Override
	public int getCount() {
		if (myCustomers == null) {
			return 0;
		}
		return myCustomers.size();
	}

	@Override
	public Object getItem(int position) {
		return myCustomers.get(position);
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
		
		final FeeHunterRecommendClientBean temp = myCustomers.get(position);
		tvName.setText(temp.getName());
		tvPhone.setText(temp.getTel());
		tvEstate.setText(temp.getEstName());
		
		tvCheck.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO 跳进度查看页（客户进度）
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(FeeHunterProgressDetailActivity.INTENT_CLIENT_ID, temp.getId())
                .jump((BaseActivity)context, FeeHunterProgressDetailActivity.class);
            }
        });
		return convertView;
	}

}
