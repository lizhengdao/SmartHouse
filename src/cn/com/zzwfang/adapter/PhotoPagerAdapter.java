package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.PictureBrowseActivity;
import cn.com.zzwfang.bean.PhotoBean;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class PhotoPagerAdapter extends PagerAdapter {

	
	private Context context;
	private ArrayList<PhotoBean> photos;
	
	public PhotoPagerAdapter(Context context, ArrayList<PhotoBean> photos) {
		this.context = context;
		this.photos = photos;
	}
	
	@Override
	public int getCount() {
		if (photos == null) {
			return 0;
		}
		return photos.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View child = (View)object;
        ViewGroup parent = (ViewGroup)child.getParent();
        parent.removeView(child);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		
		ViewHolder viewHolder;
        View convertView = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_photo, null);
            viewHolder = new ViewHolder();
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.adapter_photo_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        PhotoBean photoBean = photos.get(position);
        if (photoBean != null) {
        	String url = photoBean.getPath();
        	if (!TextUtils.isEmpty(url)) {
        		ImageAction.displayImage(url, viewHolder.imgView);
        	}
        }
        
        convertView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, PictureBrowseActivity.class);
                intent.putExtra(PictureBrowseActivity.INTENT_KEY_IMGS, photos);
                intent.putExtra(PictureBrowseActivity.INTENT_KET_CUR_IMG_POSITION, position);
                context.startActivity(intent);
            }
        });
        container.addView(convertView);
        return convertView;
	}
	
	class ViewHolder {
        ImageView imgView;
    }

}
