package com.mrathena.common.toolkit;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mrathena
 */
@Slf4j
public final class HttpKit {

	public static void main(String[] args) {
		getWithQueryString(getClient(), "http://www.baidu.com", null);
		getWithQueryString(getClient(), "http://www.baidu.com", "a=a");
		getWithQueryString(getClient(), "http://www.baidu.com?b=b", null);
		getWithQueryString(getClient(), "http://www.baidu.com?b=b", "a=a");
	}

	private HttpKit() {}

	private static final String UTF_8 = "UTF-8";


	private static CloseableHttpClient getClient() {
		return HttpClients.createDefault();
	}

	private static String getResponse(CloseableHttpClient client, HttpUriRequest request) throws IOException {
		try (CloseableHttpResponse response = client.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, UTF_8);
			}
			return null;
		}
	}

	public static String postWithMap(CloseableHttpClient client, String url, Map<String, String> map) {
		try {
			HttpPost request = new HttpPost(url);
			if (MapUtils.isNotEmpty(map)) {
				List<BasicNameValuePair> pairList = new ArrayList<>();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(pairList, UTF_8);
				request.setEntity(uefEntity);
			}
			return getResponse(client, request);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public static String post(CloseableHttpClient client, String url) {
		return postWithMap(client, url, null);
	}

	/**
	 * queryString 格式 name1=value1&name2=value2
	 */
	public static String getWithQueryString(CloseableHttpClient client, String url, String queryString) {
		try {
			String uri = url;
			if (StringUtils.isNotBlank(queryString)) {
				if (uri.contains(Constant.QUESTION)) {
					uri += Constant.AND + queryString;
				} else {
					uri += Constant.QUESTION + queryString;
				}
			}
			System.out.println(uri);
			HttpGet request = new HttpGet(uri);
			return getResponse(client, request);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public static String get(CloseableHttpClient client, String url) {
		return getWithQueryString(client, url, null);
	}

	public static String getWithMap(CloseableHttpClient client, String url, Map<String, String> map) {
		String queryString = Constant.EMPTY;
		if (MapUtils.isNotEmpty(map)) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append(Constant.AND).append(entry.getKey()).append(Constant.EQUAL).append(entry.getValue());
			}
			queryString = sb.substring(1);
		}
		return getWithQueryString(client, url, queryString);
	}


	public static String postWithMap(String url, Map<String, String> map) {
		return postWithMap(getClient(), url, map);
	}

	public static String post(String url) {
		return postWithMap(url, null);
	}

	/**
	 * queryString 格式 name1=value1&name2=value2
	 */
	public static String getWithQueryString(String url, String queryString) {
		return getWithQueryString(getClient(), url, queryString);
	}

	public static String get(String url) {
		return getWithQueryString(url, null);
	}

	public static String getWithMap(String url, Map<String, String> map) {
		return getWithMap(getClient(), url, map);
	}

}