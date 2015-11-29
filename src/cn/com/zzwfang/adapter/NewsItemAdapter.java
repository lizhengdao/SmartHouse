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
import cn.com.zzwfang.bean.NewsItemBean;
import cn.com.zzwfang.util.DateUtils;

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
			convertView = View.inflate(context, R.layout.adapter_news_item, null);
		}
		
		NewsItemBean newsItemBean = news.get(position);
		ImageView imgNewsPhoto = (ImageView) convertView.findViewById(R.id.adapter_news_item_photo);
		TextView tvNewsName = (TextView) convertView.findViewById(R.id.adapter_news_item_name);
		tvNewsName.setText(newsItemBean.getTitle());
		
		TextView tvTime = (TextView) convertView.findViewById(R.id.adapter_news_item_time);
//		String time = DateUtils.formatDate(newsItemBean.getAddTime());
		tvTime.setText(newsItemBean.getAddTime());
//		ArrayList<String> photos = newsItemBean.getImages();
//		if (photos != null && photos.size() > 0) {
//			ImageAction.displayImage(photos.get(0), imgNewsPhoto);
//		}
		
		String url = newsItemBean.getImages();
		if (!TextUtils.isEmpty(url)) {
			ImageAction.displayImage(url, imgNewsPhoto);
		}
		
		return convertView;
	}

}
