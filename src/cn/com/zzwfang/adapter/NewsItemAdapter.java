package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.NewsItemBean;

public class NewsItemAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<NewsItemBean> news;
	
	public NewsItemAdapter(Context context, ArrayList<NewsItemBean> news) {
		this.context = context;
		this.news = news;
	}
	
	@Override
	public int getCount() {
		if (news == null) {
			return 0;
		}
		return news.size();
	}

	@Override
	public Object getItem(int position) {
		return news.get(position);
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
		tvNewsName.setText(news.get(position).getTitle());
		return convertView;
	}

}
