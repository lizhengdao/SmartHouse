package cn.com.zzwfang.controller;

import android.content.Context;
import android.text.TextUtils;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.config.API;
import cn.com.zzwfang.controller.DataWorker.Options;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.http.RequestParams;

public class ActionImpl implements Action {

	private Context context;

	private ActionImpl(Context context) {
		this.context = context;
	}

	public static ActionImpl newInstance(Context context) {
		return new ActionImpl(context);
	}

	/**
	 * 获取绝对路径
	 * 
	 * @param relativeUrl
	 * @return
	 */
	private static String getAbsoluteUrl(String relativeUrl) {
		if (relativeUrl.startsWith("http://"))
			return relativeUrl;
		return API.host + relativeUrl; // + "?platform=a";
	}

	@Override
	public void fetchVerifyCode(String phoneNum, ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("tel", phoneNum);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		// opt.toHttpCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_SMS_VERIFYCODE));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void register(String phoneNum, String pwd, String captcha,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("tel", phoneNum);
		requestParams.put("tassword", pwd);
		requestParams.put("code", captcha);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_REGISTER));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void checkPhoneNumRegistered(String phoneNum,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("param", phoneNum);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_REGISTER));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void login(String userName, String pwd,
			ResultHandlerCallback callback) {

		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("tel", userName);
		requestParams.put("password", pwd);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		// opt.toHttpCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.API_POST_LOGIN));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getToken(ResultHandlerCallback callback) {

		RequestParams requestParams = new RequestParams();

		requestParams.put("platform", "a");
		// Log.i("--->", "ActionImpl getToken requestParams.hashcode == "
		// + requestParams.hashCode());
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		// opt.toHttpCacheAble = false;
		opt.toMemCacheAble = false;
		opt.isShowErrorMsg = false;
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.api_get_token_post));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		// Log.i("--->", "ActionImpl getToken requestEntity.hashcode == "
		// + requestEntity.hashCode());

		ResultHandler handler = new ResultHandler();
		handler.setResultHandlerCallback(callback);
		requestEntity.setProcessCallback(handler);
		requestEntity.setOpts(opt);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	/**
	 * 忘记密码
	 */
	@Override
	public void resetPwd(String username, String password_1, String password_2,
			String captcha, String client, ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", username);
		requestParams.put("password_1", password_1);
		requestParams.put("password_2", password_2);
		requestParams.put("captcha", captcha);
		requestParams.put("client", client);
		requestParams.put("platform", "a");
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_RESET_PWD));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		Options opts = new Options();

		// opts.fromDiskCacheAble = false;
		// opts.fromMemCacheAble = false;
		opts.fromDiskCacheAble = false;
		opts.fromHttpCacheAble = true;
		opts.fromMemCacheAble = false;
		opts.toDiskCacheAble = false;
		// opts.toHttpCacheAble = false;
		opts.toMemCacheAble = false;
		requestEntity.setOpts(opts);
		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);
		requestEntity.setProcessCallback(handler);

		DataWorker.getWorker(context).load(requestEntity);
	}

	/**
	 * 检查更新
	 */
	@Override
	public void getAppVersionCheck(String version, String client,
			ResultHandlerCallback callback) {
		// RequestParams requestParams = new RequestParams();
		// requestParams.put("version", version);
		// requestParams.put("client", client);
		// requestParams.put("platform", "a");
		// RequestEntity requestEntity = new RequestEntity();
		// requestEntity.setUrl(getAbsoluteUrl(API.GET_APP_VERSIONCHECK));
		// requestEntity.setRequestParams(requestParams);
		// requestEntity.setType(RequestEntity.GET);
		//
		// Options opts = new Options();
		//
		// // opts.fromDiskCacheAble = false;
		// // opts.fromMemCacheAble = false;
		// opts.fromDiskCacheAble = false;
		// opts.fromHttpCacheAble = true;
		// opts.fromMemCacheAble = false;
		// opts.toDiskCacheAble = false;
		// // opts.toHttpCacheAble = false;
		// opts.toMemCacheAble = false;
		// requestEntity.setOpts(opts);
		// ProgressDialogResultHandler handler = new
		// ProgressDialogResultHandler(
		// context, "请稍后...");
		// handler.setResultHandlerCallback(callback);
		// requestEntity.setProcessCallback(handler);
		//
		// DataWorker.getWorker(context).load(requestEntity);

		RequestParams requestParams = new RequestParams();
		requestParams.put("version", version);
		requestParams.put("client", client);
		requestParams.put("platform", "a");
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_APP_VERSIONCHECK));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		Options opts = new Options();

		// opts.fromDiskCacheAble = false;
		// opts.fromMemCacheAble = false;
		opts.fromDiskCacheAble = false;
		opts.fromHttpCacheAble = true;
		opts.fromMemCacheAble = false;
		opts.toDiskCacheAble = false;
		// opts.toHttpCacheAble = false;
		opts.toMemCacheAble = false;
		requestEntity.setOpts(opts);
		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);
		requestEntity.setProcessCallback(handler);

		DataWorker.getWorker(context).load(requestEntity);
	}

	@Override
	public void changePwd(String oldpwd, String newpwd1, String newpwd2,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("platform", "a");
		requestParams.put("password", oldpwd);
		requestParams.put("password_1", newpwd1);
		requestParams.put("password_2", newpwd2);

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_CHANGE_PWD));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		Options opts = new Options();
		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		opts.fromDiskCacheAble = false;
		opts.fromHttpCacheAble = true;
		opts.fromMemCacheAble = false;
		opts.toDiskCacheAble = false;
		opts.toMemCacheAble = false;
		requestEntity.setOpts(opts);
		requestEntity.setProcessCallback(handler);
		DataWorker.getWorker(context).load(requestEntity);
	}

	@Override
	public void getMine(ResultHandlerCallback callback) {
		// TODO Auto-generated method stub

	}

	// ----首页相关接口---------------------------------------------
	@Override
	public void getRecommendHouseSource(String cityId,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("id", cityId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_RECOMMEND_HOUSE_SOURCE));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	// ----首页相关接口------完---------------------------------------

	@Override
	public void getCityList(ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_CITY_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getSearchHouseArtifactResut(String house, int pageIndex,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("house", house);
		requestParams.put("pageIndex", pageIndex + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_SEARCH_HOUSE_ARTIFACT));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getNewHouseList(String areaId, String price, int roomType,
			int usage, int specials, int status, String cityId, int pageSize,
			int pageNum, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("areaId", areaId);
		requestParams.put("price", price);
		requestParams.put("roomType", roomType + "");
		requestParams.put("usage", usage + "");
		requestParams.put("specials", specials + "");
		requestParams.put("status", status + "");
		requestParams.put("siteId", cityId + "");
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageNum", pageNum + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_NEW_HOUSE_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getSecondHandHouseList(String cityId, TextValueBean areaCondition, String direction,
			TextValueBean squareCondition, TextValueBean labelCondition, 
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			String buildYear, String floor, String proNum, String sort,
			int pageSize, int pageIndex, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);
		if (areaCondition != null && !TextUtils.isEmpty(areaCondition.getValue())) {
			requestParams.put("area", areaCondition.getValue());
		}
		requestParams.put("direction", direction);
		if (squareCondition != null && !TextUtils.isEmpty(squareCondition.getValue())) {
			requestParams.put("square", squareCondition.getValue());
		}
		if (labelCondition != null && !TextUtils.isEmpty(labelCondition.getValue())) {
			requestParams.put("label", labelCondition.getValue());
		}
		if (priceCondition != null && !TextUtils.isEmpty(priceCondition.getValue())) {
			requestParams.put("price", priceCondition.getValue());
		}
		if (roomTypeCondition != null && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
			requestParams.put("roomType", roomTypeCondition.getValue());
		}
		if (!TextUtils.isEmpty(buildYear)) {
			requestParams.put("buildYear", buildYear);
		}
		if (!TextUtils.isEmpty(floor)) {
			requestParams.put("floor", floor);
		}
		if (!TextUtils.isEmpty(sort)) {
			requestParams.put("sort", sort);
		}
		
		if (!TextUtils.isEmpty(proNum)) {
			requestParams.put("proNo", proNum);
		}
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageNum", pageIndex + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_SECNOD_HAND_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	/* (总价SalePriceRange、户型HouseType、物业类型PrpUsage、特色标签EstateLabel、售卖状态EstateStatus)、楼层范围（FloorRange）、租价范围（RentPriceRange）
	 * (non-Javadoc)
	 * @see cn.com.zzwfang.controller.Action#getConditionByName(java.lang.String, cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
	 */
	@Override
	public void getConditionByName(String name, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("name", name);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_CONDITION_LIST_BY_NAME));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getAreaList(String cityId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_AREA_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getSecondHandHouseDetail(String proId,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("proId", proId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_SECOND_HAND_HOUSE_DETAIL));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getRentHouseList(String cityId, TextValueBean areaCondition,
			TextValueBean priceCondition, TextValueBean squareCondition, String sort,
			String keyWords, String direction, TextValueBean roomTypeCondition,
			int pageSize, int pageIndex, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);
		if (areaCondition != null && !TextUtils.isEmpty(areaCondition.getValue())) {
			requestParams.put("area", areaCondition.getValue());
		}
		requestParams.put("direction", direction);
		if (squareCondition != null && !TextUtils.isEmpty(squareCondition.getValue())) {
			requestParams.put("square", squareCondition.getValue());
		}
		if (priceCondition != null && !TextUtils.isEmpty(priceCondition.getValue())) {
			requestParams.put("price", priceCondition.getValue());
		}
		if (roomTypeCondition != null && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
			requestParams.put("roomType", roomTypeCondition.getValue());
		}
		if (!TextUtils.isEmpty(sort)) {
			requestParams.put("sort", sort);
		}
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageNum", pageIndex + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_RENT_HOUSE_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getRentHouseDetail(String proId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("proId", proId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_RENT_HOUSE_DETAIL));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}
}
