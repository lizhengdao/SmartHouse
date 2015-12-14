package cn.com.zzwfang.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomePageAdapter extends PagerAdapter {

    private Context context;
//    private ArrayList<Building> buildings;
//    private BitmapUtils bimUtils;
//    public HomePageAdapter(Context context, ArrayList<Building> buildings) {
//        this.context = context;
////        bimUtils = new BitmapUtils(context);
////        bimUtils.configDefaultLoadingImage(R.drawable.xiexin_default_img);
////        bimUtils.configDefaultLoadFailedImage(R.drawable.xiexin_default_img);
////        this.buildings = buildings;
//    }
    @Override
    public int getCount() {
//        return buildings.size();
    	return 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        View child = (View)object;
        ViewGroup parent = (ViewGroup)child.getParent();
        parent.removeView(child);
    }
    
    class ViewHolder {
        ImageView imgView;
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ViewHolder viewHolder;
        View convertView = null;
        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.adapter_home_page, null);
            viewHolder = new ViewHolder();
//            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.adapter_home_page_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        final Building building = buildings.get(position);
//        bimUtils.display(viewHolder.imgView, building.cover);
        convertView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
//                Intent intent = new Intent(context, BuildingDetailActivity.class);
//                intent.putExtra("building_id", building.id);
//                intent.putExtra("building_name", building.name);
//                context.startActivity(intent);
            }
        });
        container.addView(convertView);
        return convertView;
    }

}
