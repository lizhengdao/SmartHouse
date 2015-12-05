/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.util;

import java.util.Calendar;
import java.util.Locale;

import cn.com.zzwfang.R;
import android.content.Context;


/**
 * @author Soo
 */
public class TimeUtils {
    
    public static final long SECONDS = 1000;
    public static final long MINUTE = 60 * SECONDS;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MOUNTH = 30 * DAY;
    public static final long YEAR = 365 * MOUNTH;
    
    public static int getTimePercent(long startTime, long tagTime, long endTime) {
        if (tagTime >= endTime) {
            return 100;
        }
        if (startTime >= endTime) {
            return 0;
        }
        if (startTime >= tagTime) {
            return 0;
        }
        
        double left = (tagTime - startTime) * 1.0;
        double right = (endTime - startTime) * 1.0;
        double p = left / right;
        
        if (p >= 100.0) {
            return 100;
        }
        
        p = Math.ceil(p);
        return (int) p;
    }
    
    public static String getTimeDevideSimple(Context context, long startTime, long tagTime, long endTime) {
        if (tagTime >= endTime) {
            return ended(context);
        }
        if (startTime >= endTime) {
            return unknow(context);
        }
        if (startTime >= tagTime) {
            return unStart(context);
        }
        int[] datas = timeDivider(startTime, tagTime);
        int lenght = datas.length;
        int count = 3;
        int i = 0;
        
        String[] labels = labelFields(context);
        
        StringBuilder sb = new StringBuilder();
        
        while (count > 0 && i < lenght) {
            int data = datas[i];
            if (data > 0) {
                sb.append(data);
                if (i < labels.length) {
                    sb.append(labels[i]);
                    count--;
                }
            }
            i++;
        }
        if (sb.length() == 0) {
            return unStart(context);
        }
        return sb.toString();
    }
    
    /**获取两个时间的间隔mime数组，最小精确到秒
     * @param leftTime
     * @param rightTime
     * @return 返回时间间隔的mime数组，如[年][月][日][时][分][秒],如果leftTime > rightTime返回null
     */
    public static int[] timeDivider(long leftTime, long rightTime) {
        if (leftTime > rightTime) {
            return null;
        }
        Calendar startC = Calendar.getInstance();
        startC.setTimeInMillis(leftTime);
        
        Calendar tagC = Calendar.getInstance();
        tagC.setTimeInMillis(rightTime);
        
        int startY = startC.get(Calendar.YEAR);
        int startM = startC.get(Calendar.MONTH) + 1;
        int startD = startC.get(Calendar.DAY_OF_MONTH);
        int startH = startC.get(Calendar.HOUR_OF_DAY);
        int startF = startC.get(Calendar.MINUTE);
        int startS = startC.get(Calendar.SECOND);
        
        int tagY = tagC.get(Calendar.YEAR);
        int tagM = tagC.get(Calendar.MONTH) + 1;
        int tagD = tagC.get(Calendar.DAY_OF_MONTH);
        int tagH = tagC.get(Calendar.HOUR_OF_DAY);
        int tagF = tagC.get(Calendar.MINUTE);
        int tagS = tagC.get(Calendar.SECOND);
        
        int dS = tagS - startS;
        if (dS < 0) {
            tagF--;
            dS += 60;
        }
        
        int dF = tagF - startF;
        if (dF < 0) {
            tagH--;
            dF += 60;
        }
        
        int dH = tagH - startH;
        if (dH < 0) {
            tagD--;
            dH += 24;
        }
        
        int dD = tagD - startD;
        if (dD < 0) {
            tagM--;
            tagC.add(Calendar.MONTH, -1);
            dD += tagC.getActualMaximum(Calendar.DAY_OF_MONTH);
            tagC.add(Calendar.MONTH, 1);
        }
        
        int dM = tagM - startM;
        if (dM < 0) {
            tagY--;
            dM += tagC.getActualMaximum(Calendar.MONTH);
        }
        
        int dY = tagY - startY;
        
        return new int[]{dY, dM, dD, dH, dF, dS};
    }
    
    /**获取目标时间距离系统当前时间simple表示方式
     * @param context
     * @param millions
     * @return
     */
    public static String levelSimple(Context context, long millions) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(millions);
        int destYear = calendar.get(Calendar.YEAR);
        int destMonth = calendar.get(Calendar.MONTH);
        int destDay = calendar.get(Calendar.DAY_OF_MONTH);
        String tag = "";
        int ds = currentDay - destDay;
        int ms = currentMonth - destMonth;
        int ys = currentYear - destYear;
        if (ys < 0) {
            tag = unknow(context);
        } else if (ys == 0) {
            if (ms < 0) {
                tag = unknow(context);
            } else if (ms == 0) {
                if (ds < 0) {
                    tag = unknow(context);
                } else if (ds == 0) {
                    tag = today(context);
                } else if (ds == 1) {
                    tag = lastday(context);
                } else {
                    tag = format(context, millions, Ems.MONTH, Ems.DAY);
                }
            } else {
                tag = format(context, millions, Ems.MONTH, Ems.DAY);
            }
        } else {
            tag = format(context, millions, Ems.YEAY, Ems.MONTH, Ems.DAY);
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourStr = "" + hour;
        String minuteStr = "" + minute;
        if (hour < 10) {
            hourStr = "0" + hour;
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        }
        tag += " " + hourStr + ":" + minuteStr;
        return tag;
    }

    public enum Ems {
        YEAY, MONTH, DAY, HOUR, MINUTES
    }

    /**格式化目标时间
     * @param context
     * @param millions
     * @param formats
     * @return 输出格式为：--年--月--日--时--分--秒
     */
    public static String format(Context context, long millions, Ems... formats) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(millions);
        StringBuilder sb = new StringBuilder();
        for (Ems format : formats) {
            switch (format) {
                case DAY:
                    sb.append(calendar.get(Calendar.DAY_OF_MONTH) + day(context));
                    break;
                case HOUR:
                    sb.append(calendar.get(Calendar.HOUR_OF_DAY) + hour(context));
                    break;
                case MINUTES:
                    sb.append(calendar.get(Calendar.MINUTE) + minutes(context));
                    break;
                case MONTH:
                    sb.append((calendar.get(Calendar.MONTH) + 1) + month(context));
                    break;
                case YEAY:sb.append(calendar.get(Calendar.YEAR) + year(context));
                    break;
            }
        }
        return sb.toString();
    }
    
    public static String[] labelFields(Context context) {
        return ResLoader.loadStringArray(context, R.array.label_fields);
    }

    public static String year(Context context) {
        return ResLoader.loadString(context, R.string.year);
    }

    public static String month(Context context) {
        return ResLoader.loadString(context, R.string.month);
    }

    public static String day(Context context) {
        return ResLoader.loadString(context, R.string.day);
    }
    
    public static String day_t(Context context) {
        return ResLoader.loadString(context, R.string.day_t);
    }

    public static String hour(Context context) {
        return ResLoader.loadString(context, R.string.hour);
    }

    public static String minutes(Context context) {
        return ResLoader.loadString(context, R.string.minutes);
    }

    public static String second(Context context) {
        return ResLoader.loadString(context, R.string.second);
    }
    
    public static String today(Context context) {
        return ResLoader.loadString(context, R.string.today);
    }
    
    public static String lastday(Context context) {
        return ResLoader.loadString(context, R.string.lastday);
    }
    
    public static String unknow(Context context) {
        return ResLoader.loadString(context, R.string.unknow);
    }
    
    public static String unStart(Context context) {
        return ResLoader.loadString(context, R.string.unstart);
    }
    
    public static String ended(Context context) {
        return ResLoader.loadString(context, R.string.ended);
    }
}
