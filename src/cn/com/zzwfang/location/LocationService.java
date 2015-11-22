package cn.com.zzwfang.location;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationService {

	private static LocationClient mLocationClient;
    private static LocationMode tempMode = LocationMode.Hight_Accuracy;
    public static MyLocationListener mMyLocationListener;
    private static String tempcoor="bd09ll";
    private static OnLocationListener onLocationListener;
    
    private static LocationService instance;
    
    public static LocationService getInstance(Context context) {
        if (instance == null) {
            initLocationService(context.getApplicationContext());
            instance = new LocationService();
        }
        return instance;
    }
    
    private static void initLocationService(Context context) {
        mLocationClient = new LocationClient(context);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        initLocation();
    }
    
    
    
    public void startLocationService(OnLocationListener locationListener) {
        this.onLocationListener = locationListener;
        mLocationClient.start();
    }
    
    public static void stopLocationService() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }
    
    private static void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);   //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);   //可选，默认gcj02，设置返回的定位结果坐标系，
        int span = 30000;
        option.setScanSpan(span);   //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);   //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);   //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);   //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);   //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
       
        mLocationClient.setLocOption(option);
    }
    
    
    /**
     * 实现实时位置回调监听
     */
    public static class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet() + location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            
            Log.i("BaiduLocationApiDem", sb.toString());
            if (onLocationListener != null) {
//                AddressEntity address = new AddressEntity();
//                address.setContry(location.getCountry());
//                address.setProvince(location.getProvince());
//                address.setCity(location.getCity());
//                address.setAera(location.getDistrict());
//                address.setStreet(location.getStreet());
//                address.setNumber(location.getStreetNumber());
//                address.setLongitude(location.getLongitude() + "");
//                address.setLatitude(location.getLatitude() + "");
//                onLocationListener.onLocationCompletion(address);
            	onLocationListener.onLocationCompletion(location);
            }
        }
    }
    
    public interface OnLocationListener {
        void onLocationCompletion(BDLocation location);
    }


}
