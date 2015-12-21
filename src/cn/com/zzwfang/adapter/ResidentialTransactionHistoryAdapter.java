package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.ChatActivity;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.ResidentialTransactionHistoryBean;
import cn.com.zzwfang.util.DateUtils;
import cn.com.zzwfang.util.Jumper;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResidentialTransactionHistoryAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<ResidentialTransactionHistoryBean> historys;
	
	public ResidentialTransactionHistoryAdapter(Context context, ArrayList<ResidentialTransactionHistoryBean> historys) {
		this.context = context;
		this.historys = historys;
	}
	@Override
	public int getCount() {
		if (historys == null) {
			return 0;
		}
		return historys.size();
	}

	@Override
	public Object getItem(int position) {
		return historys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_residential_transaction_history, null);
		}
		
		final ResidentialTransactionHistoryBean temp = historys.get(position);
		
		ImageView imgPhoto = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_photo);
		TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_desc);
		TextView tvTotalPrice = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_total_price);
		TextView tvTime = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_publish_time);
		TextView tvBrokerName = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_broker_name);
		TextView tvConsult = ViewHolder.get(convertView, R.id.adapter_residential_transaction_history_consult);
		
		ArrayList<PhotoBean> photos = temp.getPhoto();
		if (photos != null && photos.size() > 0) {
			PhotoBean photo = photos.get(0);
			ImageAction.displayImage(photo.getPath(), imgPhoto);
		}
		String title = temp.getTypeF() + "室" + temp.getTypeT() + "厅   " + temp.getSquare() + "平";
		tvTitle.setText(title);
		
		String desc = temp.getDirection() + "  " + temp.getFloor() + "层/" + temp.getTotalFloor() + "层";
		tvDesc.setText(desc);
		
		tvTotalPrice.setText(temp.getPrice() + "万");
		
		String time = DateUtils.formatDate(temp.getSaleDate());
		tvTime.setText(time);
		
		tvBrokerName.setText(temp.getAgentName());
		
		tvConsult.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(ChatActivity.INTENT_MESSAGE_TO_ID, temp.getAgentId())
                .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, temp.getAgentName())
                .jump((BaseActivity)context, ChatActivity.class);
            }
        });
		
		return convertView;
	}

}
