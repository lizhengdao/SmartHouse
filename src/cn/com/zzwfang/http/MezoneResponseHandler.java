//package cn.com.zzwfang.http;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Set;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import org.apache.http.Header;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import cn.com.zzwfang.activity.BaseActivity;
//
//import com.lianbi.mezone.b.activity.MeZone3BaseActivity;
//import com.lianbi.mezone.b.bean.ResponseBean;
//import com.lianbi.mezone.b.contants.ResponseCode;
//import com.lianbi.mezone.b.utils.ContentUtils;
//import com.lianbi.mezone.b.utils.JsonUtils;
//import com.lianbi.mezone.b.view.HtmlDialog;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
///**
// * 返回消息处理
// * @author shaowei.ma
// * @date 2014年9月23日
// * @param <T>
// */
//public abstract class MezoneResponseHandler<T> extends AsyncHttpResponseHandler{
//	
//	
///* ======================ProgressDialog相关=========================*/
//	private ProgressDialog 	progressDialog;
//	private Handler			handler;
//	private Activity		activity;
//	private RestTemplate api;
//	private MezoneRequestContent responseContent;
//	
//	public MezoneResponseHandler(Activity activity){
//		this.activity = activity;
//		try {
//			BaseActivity mzAct = (BaseActivity) activity;
//			api = mzAct.getApi();
//		} catch (Exception e) {
//		}
//	}
//
//	public MezoneRequestContent getResponseContent() {
//		return responseContent;
//	}
//
//	public void setResponseContent(MezoneRequestContent responseContent) {
//		this.responseContent = responseContent;
//	}
//
//	/**
//	 * 设置ProgressDialog属性
//	 * @param progressDialog
//	 */
//	public void setProgressDialog(ProgressDialog progressDialog){
//		
//	}
//	/**
//	 * 创建ProgressDialog并显示
//	 */
//	private void createProgressDialog(String msg){
//		progressDialog = new ProgressDialog(activity);
//		progressDialog.setTitle("提示");
//		progressDialog.setMessage(msg==null?"请稍等...":msg);
//		setProgressDialog(progressDialog);
//		progressDialog.show();
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				dismissProgressDialog();
//			}
//		}, getProgressMaxShowTime());
//	}
//	/**
//	 * 返回ProgressDialog最大显示时间
//	 * @return
//	 */
//	public long getProgressMaxShowTime(){
//		return 3000;
//	}
//	/**
//	 * 显示ProgressDialog
//	 */
//	protected void showProgress(){
//		showProgress("请稍等...");
//	}
//	protected void showProgress(final String msg){
//		if(activity != null){
//			handler = new Handler(activity.getMainLooper());
//		}else{
//			return;
//		}
//		
//		if(handler != null) {
//			handler.post(new Runnable() {
//				@Override
//				public void run() {
//					createProgressDialog(msg);
//				}
//			});
//		}
//			
//	}
//	/**
//	 * 隐藏ProgressDialog
//	 */
////	protected void hideProgress(){
////		if(activity != null && !activity.isFinishing() && progressDialog != null && handler != null)
////			handler.postDelayed(new Runnable() {
////				@Override
////				public void run() {
////					dismissProgressDialog();
////				}
////			}, 500);
////	}
//	/**
//	 * 隐藏ProgressDialog
//	 */
//	private void dismissProgressDialog(){
//		if(activity != null && !activity.isFinishing() && progressDialog != null && progressDialog.isShowing()){
//			progressDialog.dismiss();
////			hideProgress();
//		}
//		
//	}
///* ======================ProgressDialog相关END=========================*/
//	
//	
//	
//	/**
//	 * 请求开始
//	 */
//	@Override
//	public void onStart() {
//		super.onStart();
//	}
//	
//	/**
//	 * 提交成功，返回成功消息
//	 * @author shaowei.ma
//	 * @date 2014年9月23日
//	 * @param result
//	 */
//	public abstract void onSuccess(T result);
//	
//	/**
//	 * 提交失败，返回错误消息
//	 * @author shaowei.ma
//	 * @date 2014年9月23日
//	 * @param error
//	 */
//	public void onFailure(String error){
//		if(!TextUtils.isEmpty(error))
//			ContentUtils.showMsg(activity, error);
//	}
//	
//	public void onFailure(String error, int errorCode){
////		if(!TextUtils.isEmpty(error)) {
////			ContentUtils.showMsg(activity, error);
////		}
//		onFailure(error);
//			
//	}
//	
//
//	/**
//	 * 提交进度
//	 */
//	@Override
//	public void onProgress(int bytesWritten, int totalSize) {
//		super.onProgress(bytesWritten, totalSize);
//	}
//	
//	/**
//	 * 提交取消
//	 */
//	@Override
//	public void onCancel() {
//		super.onCancel();
//	}
//	
//	/**
//	 * 再次提交
//	 */
//	@Override
//	public void onRetry(int retryNo) {
//		super.onRetry(retryNo);
//	}
//	
//	/**
//	 * 流程结束
//	 */
//	@Override
//	public void onFinish() {
//		if(responseContent != null && api != null)api.clearHistory(responseContent);
//		super.onFinish();
//		// 隐藏进度框
//		dismissProgressDialog();
////		hideProgress();
//	}
//	
//	/**
//	 * 获得缓存成功，直接返回缓存对象
//	 * @author shaowei.ma
//	 * @date 2014年10月29日
//	 * @param result
//	 * @return 是否在取得缓存对象后继续发送请求
//	 */
//	public boolean onCacheSuccess(T result){
//		return true;
//	}
//	
//	/**
//	 * 因为服务器框架原因需要进行封装
//	 */
//	@Override
//	public final void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//		if(statusCode == 200){
//			String json = new String(responseBody);
//			if(TextUtils.isEmpty(json)){
//				onFailure("服务器返回空字符串");
//				return;
//			}else{
//				try {
//					JSONObject js = new JSONObject(json);
//				} catch (JSONException e) {
//					//若服务器未返回json格式的数据，则使用webview将结果显示出来
//					HtmlDialog htmlDialog = new HtmlDialog(activity);
//					htmlDialog.show(json);
//					return;
//				}
//			}
//			
//			//第一层处理，获得status、message、result、form_errors字段
//			ResponseBean result = null;
//			try {
//				PMap map = PMapUtils.parseJsonToPMap(json);
//				result = new ResponseBean(map);
//			} catch (JSONException e) {
//				Log.e(MezoneResponseHandler.class.getName() + ".onSuccess", e.getMessage());
//			}
//			
//			T t = null;
//			
//			//status=0,成功
//			if(result.getStatus() == 0){
//				try {
//					String str_result = result.getResult().toString();
//					Type superType = getClass().getGenericSuperclass();
//					Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();
//					Type tType = generics[0];
//					
//					
//					if(tType instanceof ParameterizedType){
//						ParameterizedType tParamsType = (ParameterizedType) generics[0];
//						Class<T> mTClass = (Class<T>) tParamsType.getRawType();
//						if(mTClass.isAssignableFrom(List.class) || mTClass.isAssignableFrom(ArrayList.class)){
//							List lt = (List) JsonUtils.getListFromJSON(tParamsType, str_result);
//							t = (T) lt;
//							api.putResultCache(responseContent, t);
//							onSuccess(t);
//						}else{
//							t = JsonUtils.fromJSON(mTClass, result.getResult().toString());
//							api.putResultCache(responseContent, t);
//							onSuccess(t);
//						}
//					}
//					else {
//						Class<T> mTClass = (Class<T>) tType;
//						if(mTClass.isAssignableFrom(String.class)){
//							t = (T) result.getResult();
//						}
//						else t = JsonUtils.fromJSON(mTClass, result.getResult());
//						api.putResultCache(responseContent, t);
//						onSuccess(t);
//					}
//				} catch (Exception e) {
//					MezoneResponseError error = new MezoneResponseError();
//					error.statusCode = statusCode;
//					error.status = result.getStatus();
//					error.exception = e;
//					if(!TextUtils.isEmpty(e.getMessage())){
//						Log.e(MezoneResponseHandler.class.getName() + ".onSuccess", e.getMessage());
//					}
//					onFailure(helpFailure(error));
//				}
//			}
//			//失败
//			else{
//				MezoneResponseError error = new MezoneResponseError();
//				error.statusCode = result.getStatus();
//				error.form_errors = (HashMap<String, String[]>) result.getForm_errors();
//				error.message = result.getMessage();
//				String errorMsg = helpFailure(error);
////				onFailure(errorMsg);
//				onFailure(errorMsg, error.statusCode);
//			}
//		}else{
//			String json = new String(responseBody);
//			try {
//				JSONObject js = new JSONObject(json);
//			} catch (JSONException e) {
//				//若服务器未返回json格式的数据，则使用webview将结果显示出来
//				HtmlDialog htmlDialog = new HtmlDialog(activity);
//				htmlDialog.show(json);
//				return;
//			}
//		}
//	}
//	
//	/**
//	 * 因为服务器框架原因进行封装
//	 */
//	@Override
//	public final void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//		
//		String json = null;
//		if(responseBody == null || responseBody.length == 0) json = "";
//		else json = new String(responseBody);
//		if(TextUtils.isEmpty(json)){
//			onFailure("未连接到服务器");
//			return;
//		}else if(json.contains("<!DOCTYPE html>")){
//			//若服务器未返回json格式的数据，则使用webview将结果显示出来
//			HtmlDialog htmlDialog = new HtmlDialog(activity);
//			htmlDialog.show(json);
//			return;
//		}
//
//		MezoneResponseError responseError = new MezoneResponseError();
//		try {
//			PMap result = PMapUtils.parseJsonToPMap(json);
//			if (responseError != null && result != null) {
//				responseError.statusCode = statusCode;
//				responseError.status = result.getInteger("status");
//				responseError.form_errors = (HashMap<String, String[]>) result.get("form_errors");
//			}
//		}catch (Exception e) {
//			responseError.exception = e;
//			Log.e(MezoneResponseHandler.class.getName() + ".onFailure", "exception", e);
//			//若服务器未返回json格式的数据，则使用webview将结果显示出来
//			HtmlDialog htmlDialog = new HtmlDialog(activity);
//			htmlDialog.show(json);
//			return;
//		}finally{
//			onFailure(helpFailure(responseError));
//		}
//	}
//	
//	/**
//	 * 
//	 * @author shaowei.ma
//	 * @date 2014年9月22日
//	 * @param statusCode 403 404 500 
//	 * @param headers
//	 * @param responseBody
//	 */
//	private String helpFailure(MezoneResponseError error){
//		
//		if (error != null) {
//			
//			int statusCode = error.statusCode;
//			
//			if (statusCode == 1002) {  // 表单错误，错误信息服务端返回
//				HashMap<String, String[]> errorMap = error.getForm_errors();
//				if(errorMap != null){
//					Set<String> keys = errorMap.keySet();
//					for(String key : keys){
//						Object values = errorMap.get(key);
//						if(values instanceof String) return (String)values;
//						else return ((String[])values)[0];
//					}
//				}
//				return error.getMessage();
//			} else {   // 其他错误码的错误信息定义在本地
//				String errorMsg = ResponseCode.getMessage(error.statusCode);
//				if (TextUtils.isEmpty(errorMsg)) {
//					errorMsg = error.message;
//				}
//				return errorMsg;
//			}
//		}
//		
////		if(error != null){
////			HashMap<String, String[]> errorMap = error.getForm_errors();
////			if(errorMap != null){
////				Set<String> keys = errorMap.keySet();
////				for(String key : keys){
////					Object values = errorMap.get(key);
////					if(values instanceof String) return (String)values;
////					else return ((String[])values)[0];
////				}
////			}
////			return error.getMessage();
////		}
////		
////		
////		//TODO 对已定义的code类型进行处理（toast友好文字）
////		
////		
////		
////		if(error.statusCode == 403){
////			
////		}else if(error.statusCode == 404){
////			
////		}else if(error.statusCode == 500){
////			
////		}
//		return null;
//	}
//	
//	
//}