package cn.com.zzwfang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;

public class DateUtils {

	public static String formatDate(String timeStr) {
		
		String result = "";
		if (TextUtils.isEmpty(timeStr)) {
			return result;
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse(timeStr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
