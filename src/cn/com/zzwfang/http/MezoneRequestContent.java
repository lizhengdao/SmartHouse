package cn.com.zzwfang.http;

import java.io.Serializable;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 请求内容
 * @author shaowei.ma
 * @date 2014年10月29日
 */
public class MezoneRequestContent implements Serializable {
	String url;
	RequestParams params;
	public MezoneRequestContent(){
	}
	
	public MezoneRequestContent(String url, RequestParams params) {
		super();
		this.url = url;
		this.params = params;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public RequestParams getParams() {
		return params;
	}
	public void setParams(RequestParams params) {
		this.params = params;
	}
}
