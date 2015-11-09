package cn.com.zzwfang.util;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

public class ImageHelper {


	public static Intent getCamaraPickIntent(Uri uri){
		if(uri == null) return null;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		return intent;
	}
	public static Intent getCamaraPickIntent(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra( "return-data", true );
		return intent;
	}

	
	public static Intent getAlbumPickIntent(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		return intent;
	}
	
	
	
	/**
	 * 获取裁切intent
	 * @param uri
	 * @param outputX
	 * @param outputY
	 * @param aspectX
	 * @param aspectY
	 * @param outputFormat
	 * @return
	 */
	public static Intent getCropIntent(Intent intent, Uri uri, int outputX, int outputY, float aspectX, float aspectY, String outputFormat) {
		if(intent == null)intent = new Intent();
		intent.setAction("com.android.camera.action.CROP");
		intent.setDataAndType( uri, "image/*" );
		intent.putExtra( "crop", "true" );
		// intent.putExtra( "circleCrop", "of course" ); // circleCrop不为空则进行圆形裁切
		intent.putExtra( "aspectX", (int)(aspectX + .5) );
		intent.putExtra( "aspectY", (int)(aspectY + .5) );
		intent.putExtra( "outputX", outputX );
		intent.putExtra( "outputY", outputY );
		intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
		if (uri != null) {
			intent.putExtra( MediaStore.EXTRA_OUTPUT, uri );
			intent.putExtra( "return-data", false );
//			intent.putExtra( "return-data", true );
		} else
			intent.putExtra( "return-data", true );
		if(TextUtils.isEmpty( outputFormat ))
			intent.putExtra( "outputFormat", Bitmap.CompressFormat.JPEG.toString() );
		else intent.putExtra( "outputFormat", outputFormat );
		intent.putExtra( "noFaceDetection", true );
		return intent;
	}

}
