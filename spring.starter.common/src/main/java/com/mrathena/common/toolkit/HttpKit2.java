package com.mrathena.common.toolkit;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public final class HttpKit2 {

	private static final String EMPTY = "";
	private static final String CHARSET_UTF_8 = "UTF-8";

	public static CloseableHttpClient httpClient = null;
	public static HttpClientContext context = null;
	public static CookieStore cookieStore = null;
	public static RequestConfig requestConfig = null;

	static {
		init();
	}

	private static void init() {
		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(60000).setConnectionRequestTimeout(60000).build();
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
	}

	public static String get(String url) {
		return getWithString(url, null);
	}

	/**queryString 格式 name1=value1&name2=value2*/
	public static String getWithString(String url, String queryString) {
		CloseableHttpResponse response = null;
		try {
			String append = queryString == null || queryString.isEmpty() ? "" : "?" + queryString;
			HttpGet get = new HttpGet(url + append);
			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, CHARSET_UTF_8);
			}
			return null;
		} catch (Exception e) {
			log.error(EMPTY, e);
			return null;
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error(EMPTY, e);
			}
		}
	}

	public static String getWithMap(String url, Map<String, String> map) {
		String uri = EMPTY;
		if (map == null || map.isEmpty()) {
			uri = url;
		} else {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
			uri = url + (map == null || map.isEmpty() ? "" : "?" + sb.toString().substring(1));
		}
		return get(uri);
	}

	public static String post(String url) {
		return postWithMap(url, null);
	}

	public static String postWithMap(String url, Map<String, String> map) {
		CloseableHttpResponse response = null;
		try {
			HttpPost post = new HttpPost(url);
			// 创建参数队列
			List<BasicNameValuePair> parameters = new ArrayList<>();
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, CHARSET_UTF_8);
			post.setEntity(uefEntity);
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, CHARSET_UTF_8);
			}
			return null;
		} catch (Exception e) {
			log.error(EMPTY, e);
			return null;
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error(EMPTY, e);
			}
		}
	}

	public static void addCookie(String name, String value, String domain, String path) {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookieStore.addCookie(cookie);
	}

	public static void printCookies() {
		System.out.println("Cookies begin");
		for (Cookie cookie : cookieStore.getCookies()) {
			System.out.println("name:" + cookie.getName() + " value:" + cookie.getValue() + " domain:" + cookie.getDomain());
		}
		System.out.println("Cookies end");
	}

}
