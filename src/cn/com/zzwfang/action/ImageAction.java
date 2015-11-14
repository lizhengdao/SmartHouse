/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.action;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.DevUtils;
import cn.com.zzwfang.util.DevUtils.ScreenTools;
import cn.com.zzwfang.util.ResLoader;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * @author lzd
 * 
 */
public final class ImageAction {

	private static ImageLoader loader;

	private static DisplayImageOptions DIP_AVATAR;
	private static DisplayImageOptions DIP_IMAGE;
	private static DisplayImageOptions DIP_IMAGE_PRO;
	private static DisplayImageOptions DIP_AVATAR_BROKER;

	public static void init(Context conetxt) {
		if (loader != null) {
			return;
		}

		ScreenTools screenTools = DevUtils.getScreenTools(conetxt);
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				conetxt);
		builder.memoryCacheExtraOptions(screenTools.getScreenWidth(),
				screenTools.getScreenHeight());
		builder.memoryCache(new WeakMemoryCache());
		builder.diskCacheSize(50 * 1024 * 1024);
		builder.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		builder.diskCacheFileCount(200);
		builder.threadPoolSize(5);
		builder.tasksProcessingOrder(QueueProcessingType.FIFO);

		loader = ImageLoader.getInstance();
		loader.init(builder.build());

		initOptions(conetxt);
	}

	private static void initOptions(Context conetxt) {
		DIP_AVATAR = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_avatar)
				.showImageForEmptyUri(R.drawable.ic_avatar)
				.showImageOnFail(R.drawable.ic_avatar)
				.resetViewBeforeLoading(false).bitmapConfig(Config.RGB_565)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY).build();

		DIP_AVATAR_BROKER = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_broker_avatar_round)
				.showImageForEmptyUri(R.drawable.ic_broker_avatar_round)
				.showImageOnFail(R.drawable.ic_broker_avatar_round)
				.resetViewBeforeLoading(false).bitmapConfig(Config.RGB_565)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY).build();

		DIP_IMAGE = new DisplayImageOptions.Builder()
				.showImageOnLoading(
						ResLoader.loadDrawable(conetxt,
								R.drawable.bg_default_img))
				.showImageForEmptyUri(
						ResLoader.loadDrawable(conetxt,
								R.drawable.bg_default_img))
				.showImageOnFail(
						ResLoader.loadDrawable(conetxt,
								R.drawable.bg_default_img))
				.resetViewBeforeLoading(false).bitmapConfig(Config.RGB_565)
				.cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(false)
				.imageScaleType(ImageScaleType.EXACTLY).build();

		DIP_IMAGE_PRO = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(false).bitmapConfig(Config.RGB_565)
				.cacheInMemory(false).cacheOnDisk(true)
				.considerExifParams(false)
				.imageScaleType(ImageScaleType.EXACTLY).build();
	}

	public static ImageLoader getImageLoader() {
		return loader;
	}

	public static void displayAvatar(String uri, ImageAware imageAware) {
		loader.displayImage(uri, imageAware, DIP_AVATAR);
	}
	
	public static void displayBrokerAvatar(String uri, ImageAware imageAware) {
		loader.displayImage(uri, imageAware, DIP_AVATAR_BROKER);
	}
	
	public static void displayBrokerAvatar(String uri, ImageView imageView) {
		loader.displayImage(uri, imageView, DIP_AVATAR_BROKER);
	}

	public static void displayAvatar(String uri, ImageView imageView) {
		loader.displayImage(uri, imageView, DIP_AVATAR);
	}

	public static void displayImage(String uri, ImageAware imageAware) {
		loader.displayImage(uri, imageAware, DIP_IMAGE);
	}

	public static void displayImage(String uri, ImageView imageView) {
		loader.displayImage(uri, imageView, DIP_IMAGE);
	}

	public static void displayImage(String uri, ImageView imageView,
			ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener) {
		loader.displayImage(uri, imageView, DIP_IMAGE_PRO, listener,
				progressListener);
	}

	public static void displayImage(String uri, ImageAware imageAware,
			ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener) {
		loader.displayImage(uri, imageAware, DIP_IMAGE_PRO, listener,
				progressListener);
	}

	private ImageAction() {
	}

	public static class AbsListViewPauseOnScrollListenerProxy implements
			AbsListView.OnScrollListener {

		private ImageLoader imageLoader;
		private final boolean pauseOnScroll;
		private final boolean pauseOnFling;
		private final AbsListView.OnScrollListener onScrollListener;

		public AbsListViewPauseOnScrollListenerProxy(ImageLoader imageLoader,
				boolean pauseOnScroll, boolean pauseOnFling) {
			this(imageLoader, pauseOnScroll, pauseOnFling, null);
		}

		public AbsListViewPauseOnScrollListenerProxy(ImageLoader imageLoader,
				boolean pauseOnScroll, boolean pauseOnFling,
				AbsListView.OnScrollListener onScrollListener) {
			this.imageLoader = imageLoader;
			this.pauseOnScroll = pauseOnScroll;
			this.pauseOnFling = pauseOnFling;
			this.onScrollListener = onScrollListener;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				imageLoader.resume();
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				if (pauseOnScroll) {
					imageLoader.pause();
				}
				break;
			case OnScrollListener.SCROLL_STATE_FLING:
				if (pauseOnFling) {
					imageLoader.pause();
				}
				break;
			}
			if (onScrollListener != null) {
				onScrollListener.onScrollStateChanged(view, scrollState);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (onScrollListener != null) {
				onScrollListener.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
			}
		}
	}

}
