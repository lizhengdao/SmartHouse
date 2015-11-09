/*
 * Copyright (c) 2013-2014 Soo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.zzwfang.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;

import javax.net.ssl.SSLException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

/**
 * 
 * 对Apache DefaultHttpClient 进行封装
 * 
 * 封装具体Http 请求 request()
 * 
 * 构造方法中 初始化 Apache DefaultHttpClient 参数
 * 
 * 
 * @author Soo
 */
public class HttpClient implements HttpClientInterface {

	private static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;

	private static final int DEFAULT_MAX_CONNECTIONS = 10;

	private static final int DEFAULT_TOTAL_CONNECTIONS = 10;

	private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;

	private static final int DEFAULT_MAX_RETRIES = 5;

	private static HttpContext httpContext;

	private static DefaultHttpClient httpClient;

	private int totalConnections = DEFAULT_TOTAL_CONNECTIONS;

	private int maxConnections = DEFAULT_MAX_CONNECTIONS;

	private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;

	private String userAgent = "USERAGENT_ASP";

	/**
	 * 缓存Cookie
	 */
//	private PersistentCookieStore cookieStore;

	public HttpClient(Context context) {
		if (httpClient != null) {
			return;
		}
		Log.i("--->", "HttpClient 初始化httpClient-------------");
		BasicHttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(httpParams, socketTimeout);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
				new ConnPerRouteBean(maxConnections));
		ConnManagerParams.setMaxTotalConnections(httpParams, totalConnections);

		HttpConnectionParams.setConnectionTimeout(httpParams, socketTimeout);
		HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);
		HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpConnectionParams.setSocketBufferSize(httpParams,
				DEFAULT_SOCKET_BUFFER_SIZE);

		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(httpParams, userAgent);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				httpParams, schemeRegistry);

		httpParams.setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.BROWSER_COMPATIBILITY);

		httpContext = new SyncBasicHttpContext(new BasicHttpContext());
		httpClient = new DefaultHttpClient(cm, httpParams);
		httpClient.setHttpRequestRetryHandler(new InnerRetryHandler(
				DEFAULT_MAX_RETRIES));
//		cookieStore = new PersistentCookieStore(context);
//		httpContext.setAttribute("http.cookie-store", cookieStore);
//		httpClient.setCookieStore(cookieStore);

	}

	public HttpContext getHttpContext() {
		return httpContext;
	}

	public void setHttpContext(HttpContext httpContext) {
		this.httpContext = httpContext;
	}

	public void setCookieStore(CookieStore cookieStore) {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}

	private static class InnerRetryHandler implements HttpRequestRetryHandler {

		private static final int RETRY_SLEEP_TIME_MILLIS = 1500;
		private static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();
		private static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();

		static {
			// Retry if the server dropped connection on us
			exceptionWhitelist.add(NoHttpResponseException.class);
			// retry-this, since it may happens as part of a Wi-Fi to 3G
			// failover
			exceptionWhitelist.add(UnknownHostException.class);
			// retry-this, since it may happens as part of a Wi-Fi to 3G
			// failover
			exceptionWhitelist.add(SocketException.class);
			// never retry timeouts
			exceptionBlacklist.add(InterruptedIOException.class);
			// never retry SSL handshake failures
			exceptionBlacklist.add(SSLException.class);
		}

		private int maxRetries;

		public InnerRetryHandler(int maxRetries) {
			this.maxRetries = maxRetries;
		}

		@Override
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			boolean retry = true;

			Boolean b = (Boolean) context
					.getAttribute(ExecutionContext.HTTP_REQ_SENT);
			boolean sent = (b != null && b.booleanValue());

			if (executionCount > maxRetries) {
				// Do not retry if over max retry count
				retry = false;
			} else if (isInList(exceptionBlacklist, exception)) {
				// immediately cancel retry if the error is blacklisted
				retry = false;
			} else if (isInList(exceptionWhitelist, exception)) {
				// immediately retry if error is whitelisted
				retry = true;
			} else if (!sent) {
				// for most other errors, retry only if request hasn't been
				// fully sent yet
				retry = true;
			}

			if (retry) {
				// resend all idempotent requests
				HttpUriRequest currentReq = (HttpUriRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				String requestType = currentReq.getMethod();
				retry = !requestType.equals("POST");
			}

			if (retry) {
				SystemClock.sleep(RETRY_SLEEP_TIME_MILLIS);
			} else {
				exception.printStackTrace();
			}

			return retry;
		}

		protected boolean isInList(HashSet<Class<?>> list, Throwable error) {
			Iterator<Class<?>> itr = list.iterator();
			while (itr.hasNext()) {
				if (itr.next().isInstance(error)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public HttpResponse request(HttpUriRequest httpUriRequest) {
		if (httpUriRequest == null || httpClient == null) {
			return null;
		}
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpUriRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpResponse;
	}

	public DefaultHttpClient getDefaultHttpClient() {
		return httpClient;
	}

}
