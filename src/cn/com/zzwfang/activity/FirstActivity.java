package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.FirstAdapter;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;

public class FirstActivity extends BaseActivity implements OnClickListener {

	private ListView listView;

	private FirstAdapter adapter;

	private TextView tvDownScroll, tvUpScroll, tvStartFindHouse;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_first);
		initView();
		getGuiderPageData();
	}

	private void initView() {

		listView = (ListView) findViewById(R.id.act_first_lst);
		tvDownScroll = (TextView) findViewById(R.id.act_first_down_scroll_tv);
		tvUpScroll = (TextView) findViewById(R.id.act_first_up_scroll_tv);
		tvStartFindHouse = (TextView) findViewById(R.id.act_first_start_find_house_tv);
		
		final Animation anim = AnimationUtils.loadAnimation(this, R.anim.shake_vertical);
		tvUpScroll.startAnimation(anim);
		tvDownScroll.startAnimation(anim);

		adapter = new FirstAdapter(this);
		listView.setAdapter(adapter);
		tvStartFindHouse.setOnClickListener(this);

		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					if (view.getLastVisiblePosition() == view.getCount() -1) {
						tvDownScroll.setVisibility(View.VISIBLE);
						tvUpScroll.setVisibility(View.GONE);
						
						tvDownScroll.startAnimation(anim);
						tvUpScroll.clearAnimation();
						
					} else {
						
						if (tvUpScroll.getVisibility() == View.GONE) {
							tvDownScroll.setVisibility(View.GONE);
							tvUpScroll.setVisibility(View.VISIBLE);
							tvUpScroll.startAnimation(anim);
							tvDownScroll.clearAnimation();
						}
						
					}
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_first_start_find_house_tv:
//			Jumper.newJumper()
//            .setAheadInAnimation(R.anim.alpha_out_style1)
//            .setAheadOutAnimation(R.anim.alpha_in_style1)
//            .jump(FirstActivity.this, LoginActivity.class);
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.alpha_out_style1)
            .setAheadOutAnimation(R.anim.alpha_in_style1)
            .jump(FirstActivity.this, MainActivity.class);
            finish();
			break;
		}
	}
	
	private void getGuiderPageData() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getGuiderPageData(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
