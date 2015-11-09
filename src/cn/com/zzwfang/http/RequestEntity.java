package cn.com.zzwfang.http;

import org.apache.http.Header;

import cn.com.zzwfang.controller.DataWorker.Options;
import cn.com.zzwfang.controller.DataWorker.ProcessCallback;

/**
 * @author zhengdao.li 封装网络请求的各种数据 url RequestParams Header ProcessCallback
 */
public class RequestEntity {

	public static final int GET = 0xf101;

	public static final int POST = 0xf102;

	public static final int DELETE = 0xf103;

	public static final int PUT = 0xf104;

	public static final int DOWNLOAD = 0xf105;

	private String url;

	private RequestParams requestParams;

	private Header[] headers;

	private int type;

	private ProcessCallback processCallback;

	private Options opts;

	public ProcessCallback getProcessCallback() {
		return processCallback;
	}

	public void setProcessCallback(ProcessCallback processCallback) {
		this.processCallback = processCallback;
	}

	public Options getOpts() {
		return opts;
	}

	public void setOpts(Options opts) {
		this.opts = opts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RequestParams getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(RequestParams requestParams) {
		this.requestParams = requestParams;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		String string = null;
		if (url == null) {
			return super.toString();
		}
		// url=http://dev.api.itravel.mezone.me/scenic/nearby/&requestParams=platform=a&lng=121.16788&lat=31.053081&type=GET&
		// string = "url=" + url + "&";
		string = url + "?";
		if (requestParams != null) {
			// string += "requestParams=" + requestParams.getParamString() +
			// "&";
			string += requestParams.getParamString();
		}
		if (headers != null) {
			string += "headers=";
			for (Header header : headers) {
				String name = header.getName();
				String value = header.getValue();
				string += "name=" + name + "value=" + value;
			}
			// string += "&";
		}
		// switch(type){
		// case GET:
		// string += "type=GET&";
		// break;
		// case POST:
		// string += "type=POST&";
		// break;
		// case DELETE:
		// string += "type=DELETE&";
		// break;
		// case PUT:
		// string += "type=PUT&";
		// break;
		// case DOWNLOAD:
		// string += "type=DOWNLOAD&";
		// break;
		// }
		return string;
	}

}
