package cn.com.zzwfang.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.PopupWindow;
import cn.com.zzwfang.util.AbImageUtil;
import cn.com.zzwfang.util.FileUtils;
import cn.com.zzwfang.util.ImageHelper;
import cn.com.zzwfang.util.PickImageDescribe;
import cn.com.zzwfang.util.ToastUtils;

public abstract class BasePickPhotoActivity extends BaseActivity {

	
	/**
	 * SD卡可用状态
	 */
	static boolean isSDCardAvaliable = true;

	/**
	 * 相册选择并裁剪
	 */
	public static final int REQUEST_IMAGE_FROM_ALBUM_AND_CROP = 0x1001;
	/**
	 * 相册选择不裁剪
	 */
	public static final int REQUEST_IMAGE_FROM_ALBUM = 0x1002;
	/**
	 * 相机拍照并裁剪
	 */
	public static final int REQUEST_IMAGE_FROM_CAMERA_AND_CROP = 0x1003;
	/**
	 * 相机拍照不裁剪
	 */
	public static final int REQUEST_IMAGE_FROM_CAMERA = 0x1004;
	/**
	 * 裁剪
	 */
	public static final int REQUEST_IMAGE_CROP = 0x1005;

	/**
	 * 默认裁剪图片输出宽度像素
	 */
	protected int DEFAULT_IMG_X = 1200;
	/**
	 * 默认裁剪图片输出高度像素
	 */
	protected int DEFAULT_IMG_Y = 800;

	/**
	 * 默认裁剪图片输出宽度比例
	 */
	protected int DEFAULT_IMG_ASPECT_X = 3;
	/**
	 * 默认裁剪图片输出高度比例
	 */
	protected int DEFAULT_IMG_ASPECT_Y = 2;

	/**
	 * 默认裁剪图片输出格式（JPEG）
	 */
	public static final String DEFAULT_IMG_FORMAT = Bitmap.CompressFormat.JPEG
			.toString();

	/**
	 * 缓存文件路径
	 */
	static File cacheDir;

	/**
	 * 缓存文件的图片路径
	 */
	static File cacheImageDir;

	/**
	 * 当前正在处理的图片的Uri
	 */
	static Uri photoCurrentUri;
	/**
	 * 当前正在处理的图片的File
	 */
	static File photoCurrentFile;

	/**
	 * 选择完成的图片文件List
	 */
	static ArrayList<File> photoFiles;

	/**
	 * 图片裁剪设置
	 */
	static PickImageDescribe defaultImageDescribe;

	/**
	 * 底部弹窗
	 */
	PopupWindow pw = null;

	/**
	 * 底部弹窗View
	 */
	View pickView;

	/**
	 * 是否裁剪
	 */
	boolean isCrop = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPickPhotoData(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		cleanFile(cacheImageDir);
		super.onDestroy();
	}
	
	private void initPickPhotoData(Bundle savedInstanceState) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sd card 可用
			isSDCardAvaliable = true;
		} else if (isSDCardAvaliable != false) {
			// 当前不可用
			ToastUtils.SHORT.toast(this, "当前您的SD卡不可用，您将无法使用相册/拍照上传功能");
			isSDCardAvaliable = false;
		}

		if (cacheDir == null)
			cacheDir = this.getExternalCacheDir();
		if (cacheDir == null) {
			// TODO 提示没有获得缓存路径
			ToastUtils.SHORT.toast(this, "当前您的SD卡不可用，您将无法使用相册/拍照上传功能");
			isSDCardAvaliable = false;
		} else {
			cacheImageDir = cacheDir;
			if (!cacheImageDir.exists()) {
				cacheImageDir.mkdirs();
			}
		}

		defaultImageDescribe = new PickImageDescribe();
		defaultImageDescribe.setFile(photoCurrentFile);
		defaultImageDescribe.setOutputX(DEFAULT_IMG_X);
		defaultImageDescribe.setOutputY(DEFAULT_IMG_Y);
		defaultImageDescribe.setAspectX(DEFAULT_IMG_ASPECT_X);
		defaultImageDescribe.setAspectY(DEFAULT_IMG_ASPECT_Y);
		defaultImageDescribe.setOutputFormat(DEFAULT_IMG_FORMAT);

		setNewImageFile();

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("photoFile")) {
				photoCurrentFile = (File) savedInstanceState
						.getSerializable("photoFile");
			}
			if (savedInstanceState.containsKey("photoUri")) {
				photoCurrentUri = (Uri) savedInstanceState
						.getParcelable("photoUri");
			}
		}
		
	}
	
	/**
	 * 获得新的图片文件路径
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月24日
	 */
	protected void setNewImageFile() {
		photoCurrentFile = new File(cacheImageDir, new Date().getTime() + "."
				+ DEFAULT_IMG_FORMAT);

		photoCurrentFile.setReadable(true);
		photoCurrentFile.setWritable(true);
		photoCurrentFile.setExecutable(true);

		photoCurrentUri = Uri.fromFile(photoCurrentFile);
		defaultImageDescribe.setFile(photoCurrentFile);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {
		} finally {

			if (resultCode == -1) {
				if (requestCode == REQUEST_IMAGE_FROM_ALBUM_AND_CROP
						|| requestCode == REQUEST_IMAGE_FROM_ALBUM) {
					Uri uri = data.getData();

					String filePath = getPath(this, uri);
					FileUtils.copyFile(filePath, photoCurrentFile.toString(),
							true);

					if (requestCode == REQUEST_IMAGE_FROM_ALBUM_AND_CROP) {
						startCropImage();
						return;
					}
				} else if (requestCode == REQUEST_IMAGE_FROM_CAMERA_AND_CROP
						|| requestCode == REQUEST_IMAGE_FROM_CAMERA) {

					if (requestCode == REQUEST_IMAGE_FROM_CAMERA_AND_CROP) {
						startCropImage();
						return;
					}
				}

				if (requestCode == REQUEST_IMAGE_CROP
						|| requestCode == REQUEST_IMAGE_FROM_ALBUM
						|| requestCode == REQUEST_IMAGE_FROM_CAMERA) {

					Bitmap bm = null;
					if (bm == null) {
						try {
							int w = getDisplayWidth();
							int h = getDisplayHeight();
							if (w > 0 && h > 0) {
								Options opt = new Options();
								opt.inJustDecodeBounds = true;
								BitmapFactory.decodeStream(new FileInputStream(
										photoCurrentFile), null, opt);
								opt.inSampleSize = AbImageUtil
										.calculateInSampleSize(opt, w, h);
								opt.inJustDecodeBounds = false;
								bm = BitmapFactory.decodeStream(
										new FileInputStream(photoCurrentFile),
										null, opt);
							} else {
								bm = BitmapFactory
										.decodeStream(new FileInputStream(
												photoCurrentFile));
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					// photoFiles.add(photoCurrentFile);
					onPickedPhoto(photoCurrentFile, bm);
					return;
				}
			}
		}
	}

	/**
	 * 照相并裁剪
	 */
	protected void startPickPhotoFromCameraWithCrop() {
		if (!isSDCardAvaliable) {
			ToastUtils.SHORT.toast(this, "当前您的SD卡不可用，您将无法使用相册/拍照上传功能");
			return;
		}
		setNewImageFile();
		Intent captureIntent = ImageHelper.getCamaraPickIntent(photoCurrentUri);
//		startActivityForResult(captureIntent,
//				REQUEST_IMAGE_FROM_CAMERA_AND_CROP, false);
		startActivityForResult(captureIntent, REQUEST_IMAGE_FROM_CAMERA_AND_CROP);
	}

	/**
	 * 照相不裁剪
	 */
	protected void startPickPhotoFromCamara() {
		if (!isSDCardAvaliable) {
			ToastUtils.SHORT.toast(this, "当前您的SD卡不可用，您将无法使用相册/拍照上传功能");
			return;
		}
		setNewImageFile();
		Intent captureIntent = ImageHelper.getCamaraPickIntent(photoCurrentUri);
//		startActivityForResult(captureIntent, REQUEST_IMAGE_FROM_CAMERA, false);
		startActivityForResult(captureIntent, REQUEST_IMAGE_FROM_CAMERA);
	}

	/**
	 * 相册选择并裁剪
	 */
	protected void startPickPhotoFromAlbumWithCrop() {
		if (!isSDCardAvaliable) {
			ToastUtils.SHORT.toast(this, "当前您的SD卡不可用，您将无法使用相册/拍照上传功能");
			return;
		}
		setNewImageFile();
		Intent captureIntent = ImageHelper.getAlbumPickIntent();
//		startActivityForResult(captureIntent,
//				REQUEST_IMAGE_FROM_ALBUM_AND_CROP, false);
		startActivityForResult(captureIntent,
				REQUEST_IMAGE_FROM_ALBUM_AND_CROP);
	}

	/**
	 * 相册选择不裁剪
	 */
	protected void startPickPhotoFromAlbum() {
		if (!isSDCardAvaliable) {
			ToastUtils.SHORT.toast(this, "当前您的SD卡不可用，您将无法使用相册/拍照上传功能");
			return;
		}
		setNewImageFile();
		Intent captureIntent = ImageHelper.getAlbumPickIntent();
//		startActivityForResult(captureIntent, REQUEST_IMAGE_FROM_ALBUM, false);
		startActivityForResult(captureIntent, REQUEST_IMAGE_FROM_ALBUM);
	}
	

	/**
	 * 启动图片裁剪
	 */
	protected void startCropImage() {
		PickImageDescribe pid = getPickImageDescribe();
		Intent cropIntent = ImageHelper.getCropIntent(null, photoCurrentUri,
				pid.getOutputX(), pid.getOutputY(), pid.getAspectX(),
				pid.getAspectY(), pid.getOutputFormat());
//		startActivityForResult(cropIntent, REQUEST_IMAGE_CROP, false);
		startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
	}
	
	/**
	 * 获得图片裁切设置，可重载此方法设置裁切参数
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月24日
	 * @return
	 */
	protected PickImageDescribe getPickImageDescribe() {
		return defaultImageDescribe;
	}

	/**
	 * 清除图片缓存
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月30日
	 * @param dir
	 */
	protected void cleanFile(File dir) {
		if (dir == null)
			return;
		if (!dir.exists())
			return;
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				cleanFile(f);
			} else if (f.isFile()) {
				f.delete();
			}
		}
	}

	@SuppressLint("NewApi")
	public static String getPath(Context context, Uri uri) {

		boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider

			if (isExternalStorageDocument(uri)) {
				String docId = DocumentsContract.getDocumentId(uri);
				String[] split = docId.split(":");
				String type = split[0];
				if ("primary".equals(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}
				// TODO handle non-primary volumes

			} else if (isDownloadDocument(uri)) { // DownloadsProvider

				String id = DocumentsContract.getDocumentId(uri);
				Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			} else if (isMediaDocument(uri)) { // MediaProvider

				String docId = DocumentsContract.getDocumentId(uri);
				String[] split = docId.split(":");
				String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				String selection = "_id=?";
				String[] selectionArgs = new String[] { split[1] };
				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) { // MediaStore
																	// (and
																	// general)

			// Return the remote address
			if (isGooglePhotosUri(uri)) {
				return uri.getLastPathSegment();
			}
			return getDataColumn(context, uri, null, null);
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The contex
	 * @param uri
	 *            The Uri to query
	 * @param selection
	 *            selection (Optional) Filter used in the query
	 * @param selectionArgs
	 *            selectionArgs (Optional) Selection arguments used in the query
	 * @return The value of the _data columnm, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		String column = "_data";
		String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check
	 * @return Whether the Uri authority is ExternalStorageProvider
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check
	 * @return Whether the Uri authority is DownloadsProvider
	 */
	public static boolean isDownloadDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check
	 * @return Whether the Uri authority is MediaProvider
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}
	
	/**
	 * 图片选择返回
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月24日
	 * @param file
	 * @param bm
	 */
	public abstract void onPickedPhoto(File file, Bitmap bm);

	/**
	 * 获得显示返回图片的宽（像素），用于对图片进行裁剪显示，节省内存
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月25日
	 * @return
	 */
	public abstract int getDisplayWidth();

	/**
	 * 获得显示返回图片的高（像素），用于对图片进行裁剪显示，节省内存
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月25日
	 * @return
	 */
	public abstract int getDisplayHeight();
}
