package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IdTitleBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsTypeAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<IdTitleBean> newsTypes;
	
	public NewsTypeAdapter(Context context, ArrayList<IdTitleBean> newsTypes) {
		this.context = context;
		this.newsTypes = newsTypes;
	}
	
	@Override
	public int getCount() {
		if (newsTypes == null) {
			return 0;
		}
		return newsTypes.size();
	}

	@Override
	public Object getItem(int position) {
		return newsTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_news_type, null);
		}
		
		TextView tvNewsName = (TextView) convertView.findViewById(R.id.adapter_news_type_name);
		tvNewsName.setText(newsTypes.get(position).getTitle());
		return convertView;
	}

}
