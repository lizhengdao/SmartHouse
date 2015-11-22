package cn.com.zzwfang.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class UnScrollableViewPager extends ViewPager {

	private boolean isCanScroll = false;

	public UnScrollableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnScrollableViewPager(Context context) {
		super(context);
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// return super.onTouchEvent(event);
		// if (isCanScroll) {
		// return super.onTouchEvent(event);
		// } else {
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// super.onTouchEvent(event);
		// }
		// return false;
		// }

		if (isCanScroll == false) {
			return false;
		} else {
			return super.onTouchEvent(event);
		}
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {

		if (isCanScroll == false) {
			return false;
		} else {
			return super.onInterceptTouchEvent(event);
		}
		// if (isCanScroll) {
		// return super.onInterceptTouchEvent(event);
		// } else {
		// float x = 0;
		// float y = 0;
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// x = event.getX();
		// y = event.getY();
		// break;
		// case MotionEvent.ACTION_MOVE:
		// float deltaX = event.getX() - x;
		// float deltaY = event.getY() - y;
		// if (Math.abs(deltaX) > Math.abs(deltaY)) {
		// return true;
		// } else {
		// return super.onInterceptTouchEvent(event);
		// }
		// }
		// return super.onInterceptTouchEvent(event);
		// }

	}
}
