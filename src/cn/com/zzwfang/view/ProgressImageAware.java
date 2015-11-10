/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.view;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * @author Soo
 */
public class ProgressImageAware extends ImageViewAware implements ImageLoadingProgressListener, ImageLoadingListener {
    
    private ProgressBar progressBar;

    public ProgressImageAware(ImageView imageView, ProgressBar progressBar) {
        super(imageView);
        this.progressBar = progressBar;
    }

    @Override
    public void onProgressUpdate(String imageUri, View view, int current, int total) {
        if (checkActualViewSize) {
            Log.d("--->", imageUri + "    " + current + "/" + total);
        }
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        progressBar.setVisibility(View.GONE);
    }
}
