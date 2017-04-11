package cn.com.zzwfang.controller;

import android.content.Context;
import android.util.Log;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.config.API;
import cn.com.zzwfang.controller.DataWorker.ProcessCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ToastUtils;

public class ResultHandler implements ProcessCallback {

	private ResultHandlerCallback callback;

	private boolean intercept;

	public ResultHandler() {

	}

	public void setResultHandlerCallback(ResultHandlerCallback callback) {
		this.callback = callback;
	}

	public ResultHandlerCallback getResultHandlerCallback() {
		return callback;
	}

	@Override
	public void onPreExecute(RequestEntity entity) {
		if (intercept) {
			return;
		}
	}

	@Override
	public void onProcess(RequestEntity entity, int process) {
		if (intercept) {
			return;
		}
	}

	@Override
	public void onPostExecute(Context context, RequestEntity entity, byte[] data) {
		if (intercept) {
			return;
		}
		String result = null;
		Result r = null;
		if (data != null) {
			try {
				result = new String(data, "utf-8");
				r = Result.parseToReuslt(result);
				if (r != null) {
					String statusStr = r.getCode();
					int statusCode = Integer.parseInt(statusStr);
					
					switch (statusCode) {

					case API.SUCCESS_EXIST:
//						Log.i("--->", "statusCode == " + statusCode);
                      
						if (callback != null) {
							// 成功返回数据
//							Log.i("--->", entity.getUrl() + "   rc0 -- result ==  " + r.getData());
							callback.rc0(entity, r);
						}
						break;
					case API.ERROR_FAILURE:
//					    Log.i("--->", entity.getUrl() + "   rc0 -- result ==  " + r.getData());
						ToastUtils.SHORT.toast(context, r.getMessage());
						if (callback != null) {
							callback.rc999(entity, r);
						}
						break;
					case API.ERROR_TOKEN_INVALID:
						// token失效，此时需要重新获取网络数据,次处不做处理,在Task中做处理
						// if (callback != null) {
						// callback.rc1001(entity, r);
						// }
						break;
					case API.ERROR_FORM_ERROR:   // 1002错误需要在此处理
						// 1002表单错误需要Toast显示出来
						
						break;
					case API.ERROR_NEED_LOGIN:
						callback.rc3001(entity, r);
						break;
						default:
						    callback.rc999(entity, r);
//							Log.i("--->", "statusCode == " + statusCode);
							break;

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (callback != null) {
					callback.rc999(entity, r);
				}
			}
		} else {
			//  请求数据失败，回掉
			if (callback != null) {
				callback.rc999(entity, r);
			}
		}

	}

	@Override
	public void onCancelled(RequestEntity entity) {
		intercept();
		if (callback != null) {
			callback.rc999(entity, null);
		}
	}

	public void intercept() {
		intercept = true;

	}

	public void disIntercept() {
		intercept = false;
	}

	public interface ResultHandlerCallback {

		/**
		 * 成功返回数据(有数据集)
		 */
		void rc0(RequestEntity entity, Result result);

		/**
		 * 需要登录,需要跳转登录页面
		 */
		void rc3001(RequestEntity entity, Result result);

		/**
		 * 【保留】回调方法
		 */
		void rc999(RequestEntity entity, Result result);

	}

	public static class DefaultResultHandlerCallback implements
			ResultHandlerCallback {

		private Context context;

		public DefaultResultHandlerCallback(Context context) {
			this.context = context;
		}

		public Context getContext() {
			return context;
		}

		@Override
		public void rc0(RequestEntity entity, Result result) {
			
//			Log.i("--->", entity.getUrl() + "   rc0 -- result ==  " + result.getData());

		}

		// 需要登录
		@Override
		public void rc3001(RequestEntity entity, Result result) {
//			ContentUtils.putSharePre(context,
//					Constants.SHARED_PREFERENCE_NAME,
//					Constants.LOGINED_IN, false);
		}

		// 保留的回掉方法
		@Override
		public void rc999(RequestEntity entity, Result result) {
//			Log.d(this.getClass().getName() + "->rc999[entity="
//					+ entity.toString() + "]");
		}

		// @Override
		// public void rc1002(RequestEntity preEntity, Result result) {
		//
		// // token失效 这里重新获得Token 但是我们自己的要怎么做呢
		// Log.d(this.getClass().getName() + "->rc1002[entity=" +
		// preEntity.toString() + "]");
		// Action action = ActionImpl.newInstance(context);
		// action.getToken(new TokenProcessHandler(context, preEntity,
		// preEntity.getOpts()
		// , preEntity.getProcessCallback()));
		// }

	}

}
