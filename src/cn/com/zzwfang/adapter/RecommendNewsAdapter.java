package cn.com.zzwfang.adapter;

import java.util.ArrayList;
import java.util.Iterator;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.RecommendNewsBean;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendNewsAdapter extends PagerAdapter {

	private Context context;
	private ArrayList<RecommendNewsBean> recommendNews;
	private ArrayList<View> mCaches;
	
	public RecommendNewsAdapter(Context context) {
		this.context = context;
		recommendNews = new ArrayList<RecommendNewsBean>();
		mCaches = new ArrayList<View>();
	}
	
	public void refreshData(ArrayList<RecommendNewsBean> data) {
		if (recommendNews == null) {
			recommendNews = new ArrayList<RecommendNewsBean>();
		}
		recommendNews.clear();
		recommendNews.addAll(data);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (recommendNews == null) {
			return 0;
		}
		return recommendNews.size();
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		
		View convertView = null;
        ViewHolder viewHolder = null;
        if (mCaches.size() == 0) {
            Log.i("--->", "mCaches.size() == 0  inflate");
            convertView = View.inflate(context, R.layout.adapter_recommend_news, null);
            viewHolder = new ViewHolder();
            viewHolder.ivNews = (ImageView) convertView.findViewById(R.id.iv_recommend_news);
            viewHolder.tvNews = (TextView) convertView.findViewById(R.id.tv_recommend_news_content);
            convertView.setTag(viewHolder);
            mCaches.add(convertView);
        } else {
            Iterator<View> iterator = mCaches.iterator();
            while (iterator.hasNext()) {
                View view = iterator.next();
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent == null) {
                    convertView = view;
                    Log.i("--->", "mCaches   found");
                    break;
                }
            }

            if (convertView == null) {
                Log.i("--->", "mCaches not found  inflate");
                convertView = View.inflate(context, R.layout.adapter_recommend_news, null);
                viewHolder = new ViewHolder();
                viewHolder.ivNews = (ImageView) convertView.findViewById(R.id.iv_recommend_news);
                viewHolder.tvNews = (TextView) convertView.findViewById(R.id.tv_recommend_news_content);
                convertView.setTag(viewHolder);
                mCaches.add(convertView);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

        }

        Log.i("--->", "mCaches.size() == " + mCaches.size());
        RecommendNewsBean recommendNewsBean = recommendNews.get(position);
        
        ImageAction.displayImage(recommendNewsBean.getImages(), viewHolder.ivNews);
        viewHolder.tvNews.setText(recommendNewsBean.getContent());



        container.addView(convertView);

		
		
		return super.instantiateItem(container, position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		
		
		
		return false;
	}
	
	static class ViewHolder {
		ImageView ivNews;
		TextView tvNews;
	}

}
