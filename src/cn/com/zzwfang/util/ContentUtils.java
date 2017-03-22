package cn.com.zzwfang.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baidu.mapapi.model.LatLng;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.constant.Constants;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 操作文件/uri/sharedpreference的工具类
 * 
 * @author fenfei.she
 * @version v3.0
 * @date 2014年7月30日
 */
public final class ContentUtils {

	// public static void showNotification(Context context, String text) {
	// Notification notification = new NotificationCompat.Builder(context)
	// .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
	// R.drawable.ic_launcher)).setSmallIcon(R.drawable.ic_launcher)
	// .setTicker("showNormal").setContentInfo("contentInfo")
	// .setContentTitle("ContentTitle").setContentText(text)
	// .setNumber(1)
	// .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
	// .build();
	//
	// NotificationManager manager = (NotificationManager)
	// context.getSystemService(Context.NOTIFICATION_SERVICE);
	// manager.notify(0, notification);
	// }

	// 动态计算listview的高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 判断Service是不是在运行
	 * 
	 * @param mContext
	 * @param className
	 * @return
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);

		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 生成二维码Bitmap
	 * 
	 * @param str
	 * @return
	 * @author shaowei.ma
	 * @date 2014年11月19日
	 */
	// public static Bitmap createQrCodeBitmap(String str) {
	// if (TextUtils.isEmpty(str)) return null;
	// try {
	// BitMatrix matrix = new MultiFormatWriter().encode(str,
	// BarcodeFormat.QR_CODE, 300, 300);
	// int width = matrix.width;
	// int height = matrix.height;
	// int[] pixels = new int[width * height];
	// for (int y = 0; y < width; ++y) {
	// for (int x = 0; x < height; ++x) {
	// if (matrix.get(x, y)) {
	// pixels[y * width + x] = 0xff000000; // black pixel
	// } else {
	// pixels[y * width + x] = 0xffffffff; // white pixel
	// }
	// }
	// }
	// Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	// bmp.setPixels(pixels, 0, width, 0, 0, width, height);
	// return bmp;
	// } catch (WriterException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * uri2path
	 * 
	 * @param context
	 * @param contentUri
	 * @return
	 */
	public static String uri2path(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri, proj, null,
					null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * Bitmap2File
	 * 
	 * @param bitName
	 * @param bitmap
	 * @return
	 * @throws java.io.IOException
	 */
	public static File bitmap2file(String bitName, Bitmap bitmap)
			throws IOException {
		File f = new File("mnt/sdcard/" + bitName + ".jpg");
		f.createNewFile();
		FileOutputStream fOut = null;
		fOut = new FileOutputStream(f);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		fOut.flush();
		fOut.close();
		return f;
	}

	/**
	 * @param mContext
	 *            上下文，来区别哪一个activity调用的
	 * @param whichSp
	 *            使用的SharedPreferences的名字
	 * @param field
	 *            SharedPreferences的哪一个字段
	 * @return
	 */
	// 取出whichSp中field字段对应的string类型的值
	public static String getSharePreStr(Context mContext, String whichSp,
			String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		String s = sp.getString(field, "");// 如果该字段没对应值，则取出字符串0
		return s;
	}

	// 取出whichSp中field字段对应的int类型的值
	public static int getSharePreInt(Context mContext, String whichSp,
			String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		int i = sp.getInt(field, 0);// 如果该字段没对应值，则取出0
		return i;
	}

	// 取出whichSp中field字段对应的boolean类型的值
	public static boolean getSharePreBoolean(Context mContext, String whichSp,
			String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		boolean i = sp.getBoolean(field, false);
		return i;
	}

	// 保存string类型的value到whichSp中的field字段
	public static void putSharePre(Context mContext, String whichSp,
			String field, String value) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		sp.edit().putString(field, value).commit();
	}

	// 保存int类型的value到whichSp中的field字段
	public static void putSharePre(Context mContext, String whichSp,
			String field, int value) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		sp.edit().putInt(field, value).commit();
	}

	public static String getStringFromSharedPreference(Context mContext,
			String whichSp, String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		String s = sp.getString(field, "");// 如果该字段没对应值，则取出空字符串
		return s;
	}

	// 保存boolen类型的value到whichSp中的field字段(主要做登陆状态)
	public static void putSharePre(Context mContext, String whichSp,
			String field, boolean value) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		sp.edit().putBoolean(field, value).commit();
	}

	// 删除某个表
	public static void deleteField(Context mContext, String whichSp) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(whichSp, 0);
		sp.edit().clear();
	}
	
	/**
	 * 保存登录用户信息
	 * @param context
	 * @param userInfo
	 */
	public static void saveUserInfo(Context context, UserInfoBean userInfo) {
		
		if (context == null) {
			return;
		}
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		Editor editor = sp.edit();
		editor.putString(Constants.ZZWFANG_USER_NAME, userInfo.getUserName());
		editor.putString(Constants.USER_ID, userInfo.getId());
		editor.putString(Constants.USER_PHONE, userInfo.getPhone());
		editor.putString(Constants.USER_PHOTO, userInfo.getPhoto());
		editor.putBoolean(Constants.IS_BIND_BANK_CARD, userInfo.isIsBindBank());
		editor.putInt(Constants.RECOMMEND_CLIENT_NUM, userInfo.getRecommendClientsNum());
		editor.putInt(Constants.RECOMMEND_OWNERS, userInfo.getRecommendOwners());
		editor.putString(Constants.FEE_HUNTER_BOUNTY, userInfo.getBounty());
		editor.putBoolean(Constants.USER_SEX, userInfo.isSex());
		editor.putInt(Constants.USER_TYPE, userInfo.getUserType());
		editor.commit();
	}
	
	public static void setUserLoginStatus(Context context, boolean loginStatus) {
		if (context == null) {
			return;
		}
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		Editor editor = sp.edit();
		editor.putBoolean(Constants.LOGINED_IN, loginStatus).commit();
	}
	
	public static boolean getUserLoginStatus(Context context) {
	    if (context == null) {
	        return false;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		return sp.getBoolean(Constants.LOGINED_IN, false);
	}
	
	public static void setUserHasLogin(Context context, boolean loginStatus) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putBoolean(Constants.HAS_LOGIN, loginStatus).commit();
	}
	
	public static boolean getUserHasLogin(Context context) {
		if (context == null) {
	        return false;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        return sp.getBoolean(Constants.HAS_LOGIN, false);
	}
	
	public static void updateUserType(Context context, int userType) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putInt(Constants.USER_TYPE, userType);
        editor.commit();
	}
	
	public static UserInfoBean getUserInfo(Context context) {
		if (context == null) {
	        return null;
	    }
		UserInfoBean userInfoBean = new UserInfoBean();
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		String userName = sp.getString(Constants.ZZWFANG_USER_NAME, "");
		userInfoBean.setUserName(userName);
		String userId = sp.getString(Constants.USER_ID, "");
		userInfoBean.setId(userId);
		String photo = sp.getString(Constants.USER_PHOTO, "");
		userInfoBean.setPhoto(photo);
		String userPhone = sp.getString(Constants.USER_PHONE, "");
		userInfoBean.setPhone(userPhone);
		boolean sex = sp.getBoolean(Constants.USER_SEX, true);
		userInfoBean.setSex(sex);
		boolean isBindCard = sp.getBoolean(Constants.IS_BIND_BANK_CARD, false);
		userInfoBean.setIsBindBank(isBindCard);
		int recommendClientNum = sp.getInt(Constants.RECOMMEND_CLIENT_NUM, 0);
		userInfoBean.setRecommendClientsNum(recommendClientNum);
		int recommendOwners = sp.getInt(Constants.RECOMMEND_OWNERS, 0);
		userInfoBean.setRecommendOwners(recommendOwners);
		String bounty = sp.getString(Constants.FEE_HUNTER_BOUNTY, "");
		userInfoBean.setBounty(bounty);
		int userType = sp.getInt(Constants.USER_TYPE, 1);  //  用户类型    0经济人，1普通会员，2赏金猎人
		userInfoBean.setUserType(userType);
		return userInfoBean;
	}
	
	public static boolean isUserBindBankCard(Context context) {
		if (context == null) {
	        return false;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        return sp.getBoolean(Constants.IS_BIND_BANK_CARD, false);
	}
	
	public static void setUserBindBankCard(Context context, boolean isBindBankCard) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putBoolean(Constants.IS_BIND_BANK_CARD, isBindBankCard).commit();
	}
	
	public static void setMessageReceiveSetting(Context context, boolean isReceiveMsg) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putBoolean(Constants.USER_IM_MSG_RECEIVE_SETTING, isReceiveMsg).commit();
	}
	
	public static boolean getMessageReceiveSetting(Context context) {
		if (context == null) {
	        return false;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
	    return sp.getBoolean(Constants.USER_IM_MSG_RECEIVE_SETTING, true);
	}
	
	/**
	 * 获取登录用户的用户类型    0经济人，1普通会员，2赏金猎人
	 * 
	 *    默认为  1  普通会员
	 * @param context
	 * @return
	 */
	public static int getUserType(Context context) {
		if (context == null) {
	        return -1;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		return sp.getInt(Constants.USER_TYPE, -1);  //  用户类型    0经济人，1普通会员，2赏金猎人
	}
	
	public static void updateUserAvatar(Context context, String avatarUrl) {
		if (context == null) {
	        return;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		Editor editor = sp.edit();
		editor.putString(Constants.USER_PHOTO, avatarUrl).commit();
	}
	
	public static String getUserAvatar(Context context) {
		if (context == null) {
	        return "";
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		return sp.getString(Constants.USER_PHOTO, "");
	}
	
	public static void updateUserNickName(Context context, String nickName) {
		if (context == null) {
	        return;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		Editor editor = sp.edit();
		editor.putString(Constants.ZZWFANG_USER_NAME, nickName).commit();
	}
	
	public static void saveSelectedCityLatLng(Context context, double lat, double lng) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putString(Constants.LAT_SELECTED_CITY, String.valueOf(lat));
        editor.putString(Constants.LNG_SELECTED_CITY, String.valueOf(lng));
        editor.commit();
	}
	
	public static LatLng getSelectedCityLatLng(Context context) {
		if (context == null) {
	        return null;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
	    double lat = Double.valueOf(sp.getString(Constants.LAT_SELECTED_CITY, "0"));
	    double lng = Double.valueOf(sp.getString(Constants.LNG_SELECTED_CITY, "0"));
	    LatLng latLng = new LatLng(lat, lng);
	    return latLng;
	}
	
	public static void saveLocatedCity(Context context, String city) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putString(Constants.CITY_LOCATED, city);
        editor.commit();
	}
	
	
	public static String getLocatedCity(Context context) {
		if (context == null) {
	        return null;
	    }
		
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		return sp.getString(Constants.CITY_LOCATED, null);
		
	}
	
	/**
	 * 用户退出登录，清除登录信息
	 * @param context
	 */
	public static void clearUserInfo(Context context) {
		if (context == null) {
	        return;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		sp.edit()
		.remove(Constants.ZZWFANG_USER_NAME)
		.remove(Constants.USER_ID)
		.remove(Constants.USER_PHOTO)
		.remove(Constants.IS_BIND_BANK_CARD)
		.remove(Constants.RECOMMEND_CLIENT_NUM)
		.remove(Constants.RECOMMEND_OWNERS)
		.remove(Constants.FEE_HUNTER_BOUNTY)
		.remove(Constants.USER_TYPE)
		.commit();
	}
	
	public static void saveLoginPhone(Context context, String phone) {
		if (context == null) {
	        return;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		Editor editor = sp.edit();
		editor.putString(Constants.USER_LOGIN_PHONE, phone).commit();
	}
	
	public static String getLoginPhone(Context context) {
		if (context == null) {
	        return "";
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		String userLoginPhone = sp.getString(Constants.USER_LOGIN_PHONE, "");
		return userLoginPhone;
	}
	
	public static void saveLoginPwd(Context context, String pwd) {
		if (context == null) {
	        return;
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        Editor editor = sp.edit();
        editor.putString(Constants.USER_LOGIN_PWD, pwd).commit();
	}
	
	public static String getLoginPwd(Context context) {
		if (context == null) {
	        return "";
	    }
	    SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
        String userLoginPwd = sp.getString(Constants.USER_LOGIN_PWD, "");
        return userLoginPwd;
	}
	
	public static String getUserId(Context context) {
		if (context == null) {
	        return "";
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		String userId = sp.getString(Constants.USER_ID, "");
		return userId;
	}
	
	public static void saveCityBeanData(Context context, CityBean cityBean) {
		if (context == null || cityBean == null) {
	        return;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		Editor editor = sp.edit();
		editor.putString(Constants.CITY_NAME, cityBean.getName());
		editor.putString(Constants.CITY_ID, cityBean.getSiteId());
		editor.putString(Constants.CITY_LAT, String.valueOf(cityBean.getLat()));
		editor.putString(Constants.CITY_LNG, String.valueOf(cityBean.getLng()));
		editor.putBoolean(Constants.CITY_OPEN_MONEY, cityBean.isOpenMoney());
		editor.commit();
	}
	
	public static CityBean getCityBean(Context context) {
	    if (context == null) {
	        return null;
	    }
		SharedPreferences sp = (SharedPreferences) context.getApplicationContext()
				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
		
		String cityName = sp.getString(Constants.CITY_NAME, null);
		String cityId = sp.getString(Constants.CITY_ID, null);
		String latStr = sp.getString(Constants.CITY_LAT, null);
		String lngStr = sp.getString(Constants.CITY_LNG, null);
		Boolean isOpenMoney = sp.getBoolean(Constants.CITY_OPEN_MONEY, false);
		
		CityBean cityBean = new CityBean();
		cityBean.setName(cityName);
		cityBean.setSiteId(cityId);
		if (!TextUtils.isEmpty(latStr)) {
		    cityBean.setLat(Double.valueOf(latStr));
		}
		if (!TextUtils.isEmpty(lngStr)) {
		    cityBean.setLng(Double.valueOf(lngStr));
		}
		cityBean.setOpenMoney(isOpenMoney);
		
		return cityBean;
	}

//	public static void putNearByFlag(Context context, boolean flag) {
//		SharedPreferences sp = (SharedPreferences) context
//				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
//		sp.edit().putBoolean(Constants.NEAR_BY, flag).commit();
//	}
//
//	public static boolean getNearBy(Context context) {
//		SharedPreferences sp = (SharedPreferences) context
//				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
//		return sp.getBoolean(Constants.NEAR_BY, true);
//	}
	
	//保持是否接收系统消息flag
//	public static void putSysMessageFlag(Context context, boolean flag) {
//		SharedPreferences sp = (SharedPreferences) context
//				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
//		sp.edit().putBoolean(Constants.SYSTEM_MESSAGE, flag).commit();
//	}
//	
//	public static boolean getSysMessage(Context context) {
//		SharedPreferences sp = (SharedPreferences) context
//				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
//		return sp.getBoolean(Constants.SYSTEM_MESSAGE, true);
//	}
	
	//保持是否接收用户消息flag
//	public static void putUserMessageFlag(Context context, boolean flag) {
//		SharedPreferences sp = (SharedPreferences) context
//				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
//		sp.edit().putBoolean(Constants.USER_MESSAGE, flag).commit();
//	}
//	
//	public static boolean getUserMessage(Context context) {
//		SharedPreferences sp = (SharedPreferences) context
//				.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, 0);
//		return sp.getBoolean(Constants.USER_MESSAGE, true);
//	}

	/**
	 * Toast的封装
	 * 
	 * @param mContext
	 *            上下文，来区别哪一个activity调用的
	 * @param msg
	 *            你希望显示的值。
	 */
	public static void showMsg(Context mContext, String msg) {
		Toast toast = new Toast(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.layout_toast, null);
		TextView textView = (TextView) view.findViewById(R.id.tv_toast_content);
		textView.setText(msg);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);// 设置居中
		toast.show();// 显示,(缺了这句不显示)
	}

	// ----------------------------以下是想着的相机操作--------------------------------

	/**
	 * 选择相册图片并切割
	 */
	public static Intent pickAndCrop(Uri uri) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);// 向外输出uri
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		return intent;
	}

	/**
	 * 拍照并切割
	 */
	public static void camreaAndCrop() {
		// TODO
	}

	/**
	 * 获取短信验证码
	 * 
	 * @param context
	 */
	public static void getSmsFromPhone(Context context) {
		ContentResolver cr = context.getContentResolver();
		String[] projection = new String[] { "body" };// "_id", "address",
														// "person",, "date",
														// "type
		String where = " address = '10690042229208' AND date >  "
				+ (System.currentTimeMillis() - 10 * 60 * 1000);
		Cursor cur = cr.query(Uri.parse("content://sms/"), projection, where,
				null, "date desc");
		if (null == cur)
			return;
		if (cur.moveToNext()) {
			String number = cur.getString(cur.getColumnIndex("address"));// 手机号
			String body = cur.getString(cur.getColumnIndex("body"));
			// 这里我是要获取自己短信服务号码中的验证码~~
			Pattern pattern = Pattern.compile("(?<!\\d)\\d{6}(?!\\d)");
			Matcher matcher = pattern.matcher(body);
			if (matcher.find()) {
				String res = matcher.group();
				// .substring(1, 11);
			}
		}
		cur.close();
	}

	/**
	 * 计算view的数量
	 * 
	 * @return
	 */
	public static int viewsCount(int viewsCount) {
		int num = 0;
		if (viewsCount > 6) {
			if ((viewsCount % 6) == 0) {
				num = viewsCount / 6;
				return num;
			} else {
				num = viewsCount / 6 + 1;
				return num;
			}
		}
		return viewsCount;
	}

	/**
	 * 获取设备id/imei码
	 * 
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		if (imei == null || imei.equals("")) {
			String did = Settings.Secure.getString(
					context.getContentResolver(), Settings.Secure.ANDROID_ID);
			imei = did;
			if (did == null || did.equals("")) {
				imei = "无法获取到设备id";
			}
		}
		return imei;
	}

	/**
	 * 转换xmppmessage
	 * 
	 * @param msg
	 * @return
	 */
	// public static String convertMsg(String msg) {
	// String message = JsonUtils.convertJson(msg);
	// return message;
	// }

	/**
	 * 发送自定底义广播
	 * 
	 * @param context
	 * @param intent
	 */
	public static void sendBroast(Context context, Intent intent) {
		context.sendBroadcast(intent);
	}

	/**
	 * 获取当前的版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentVersionCode(Context context) {
		String versionCode = "";
		PackageManager manager = context.getPackageManager();
		try {
			versionCode = manager.getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return versionCode;
	}

//	public static void showSuccessToast(Context context) {
//		Toast toast = new Toast(context);
//		View toastView = View.inflate(context, R.layout.toast_success, null);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.setView(toastView);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.show();
//	}

//	public static boolean getLoginStatus(Context context) {
//		return ContentUtils.getSharePreBoolean(context,
//				Constants.SHARED_PREFERENCE_NAME, Constants.LOGINED_IN);
//	}
//
//	public static void setLocateStatus(Context context, boolean locateStatus) {
//		ContentUtils.putSharePre(context, Constants.SHARED_PREFERENCE_NAME,
//				Constants.LOCATE_STATUS, locateStatus);
//	}
//
//	public static boolean getLocateStatus(Context context) {
//		return ContentUtils.getSharePreBoolean(context,
//				Constants.SHARED_PREFERENCE_NAME, Constants.LOCATE_STATUS);
//	}
//
//	public static void saveToken(Context context, String token) {
//		ContentUtils.putSharePre(context, Constants.SHARED_PREFERENCE_NAME,
//				Constants.TOKEN, token);
//	}
//
//	public static String getToken(Context context) {
//		return getStringFromSharedPreference(context,
//				Constants.SHARED_PREFERENCE_NAME, Constants.TOKEN);
//	}
//
//	public static void saveSessionId(Context context, String sessionId) {
//		ContentUtils.putSharePre(context, Constants.SHARED_PREFERENCE_NAME,
//				Constants.SESSION_ID, sessionId);
//	}
//
//	public static String getSessionId(Context context) {
//		return getStringFromSharedPreference(context,
//				Constants.SHARED_PREFERENCE_NAME, Constants.SESSION_ID);
//	}

}
