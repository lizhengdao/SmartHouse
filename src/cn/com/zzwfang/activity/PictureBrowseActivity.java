package cn.com.zzwfang.activity;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.PictureBrowseAdapter;
import cn.com.zzwfang.bean.PhotoBean;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class PictureBrowseActivity extends BaseActivity implements OnPageChangeListener {
	
	public static final String INTENT_KEY_IMGS = "PictureBrowseActivity.photos";
    public static final String INTENT_KET_CUR_IMG_POSITION = "PictureBrowseActivity.img_position";

	private ViewPager viewPager;
	private TextView indicatorTxt;
	
	private PictureBrowseAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}

	private void initView() {
		setContentView(R.layout.act_picture_browse);
		viewPager = (ViewPager) findViewById(R.id.act_picturebrowse_viewpager);
		indicatorTxt = (TextView) findViewById(R.id.act_picturebrowse_indicator);

		adapter = new PictureBrowseAdapter(this);

		viewPager.setOnPageChangeListener(this);

		viewPager.setAdapter(adapter);
		
		Intent intent = getIntent();
        ArrayList<PhotoBean> photoBeans = (ArrayList<PhotoBean>) intent.getSerializableExtra(INTENT_KEY_IMGS);
        int positon = intent.getIntExtra(INTENT_KET_CUR_IMG_POSITION, 0);
        
        adapter.setResourceEntities(photoBeans);
        viewPager.setCurrentItem(positon);
        onPageSelected(positon);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		String indicatorStr = (++arg0) + "/" + adapter.getCount();
        indicatorTxt.setText(indicatorStr);
	}
}
