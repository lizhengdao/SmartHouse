/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.view.ProgressImageAware;


/**
 * @author Soo
 */
public class PictureBrowseAdapter extends PagerAdapter {
    
    private Context context;
    private SparseArray<View> viewPool;
    
    private List<PhotoBean> photos;

    public PictureBrowseAdapter(Context context) {
        this.context = context;
        this.viewPool = new SparseArray<View>();
        this.photos = new ArrayList<PhotoBean>();
    }
    
    public void setResourceEntities(List<PhotoBean> resourceEntities) {
        if (resourceEntities == null) {
            return;
        }
        this.photos.addAll(resourceEntities);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == viewPool.get((Integer) arg1);
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewPool.get(position));
        viewPool.remove(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.adapter_picture_browse, null);
        
        ImageView zMg = (ImageView) view.findViewById(R.id.adapter_picturebrowse_zmg);
        ProgressBar pb = (ProgressBar) view.findViewById(R.id.adapter_picturebrowse_pb);
        
        ProgressImageAware pia = new ProgressImageAware(zMg, pb);
        
        viewPool.put(position, view);
        container.addView(view);
        
        ImageAction.displayImage(photos.get(position).getPath(), pia, pia, pia);
        
        return position;
    }
}
