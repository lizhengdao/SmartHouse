package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.InqFollowListBean;
import cn.com.zzwfang.view.AutoDrawableTextView;

public class SeeHouseRecordAdapter extends BaseAdapter {

	private Context context;
	
	/**
	 * 带看记录
	 */
	private ArrayList<InqFollowListBean> inqFollowList;
	
	public SeeHouseRecordAdapter(Context context, ArrayList<InqFollowListBean> inqFollowList) {
		this.context = context;
		this.inqFollowList = inqFollowList;
	}
	
	@Override
	public int getCount() {
		if (inqFollowList == null) {
			return 0;
		}
		
		return inqFollowList.size();
	}

	@Override
	public Object getItem(int position) {
		return inqFollowList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_see_house_record, null);
		}
		
		ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.adapter_see_house_record_avtar);
		TextView tvName = (TextView) convertView.findViewById(R.id.adapter_see_house_record_name);
		AutoDrawableTextView tvPhone = (AutoDrawableTextView) convertView.findViewById(R.id.adapter_see_house_record_phone);
		
		InqFollowListBean inqFollowListBean = inqFollowList.get(position);
		tvName.setText(inqFollowListBean.getName());
		tvPhone.setText(inqFollowListBean.getTel());
		String url = inqFollowListBean.getHead();
		if (!TextUtils.isEmpty(url)) {
			ImageAction.displayImage(inqFollowListBean.getHead(), imgAvatar);
		}
		
		return convertView;
	}

}
