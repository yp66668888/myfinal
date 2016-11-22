/**
 * Copyright (c) 2015-2020, chenyong.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.htwater.myfinal.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;

/*
 * 用户调用webservice 
 * */
public class HttpClientWSRequest {
	private String namespace;
	private String methodName;
	private String wsdlLocation;
	private String soapResponseData;
	private int statusCode;

	private boolean blDebug = false;

	private Logger logger = Logger.getLogger(HttpClientWSRequest.class.getName());

	public HttpClientWSRequest(String namespace, String methodName, String wsdlLocation) {
		this.namespace = namespace;
		this.methodName = methodName;
		this.wsdlLocation = wsdlLocation;
	}

	/*
	 * 获取调用结果中的状态码
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/*
	 * 获取调用后的数据
	 */
	public String getData() {
		return soapResponseData;
	}

	/*
	 * 调用webservice
	 */
	public HttpClientWSRequest invoke(Map<String, String> patameterMap) throws Exception {
		PostMethod postMethod = new PostMethod(wsdlLocation);
		String soapRequestData = buildRequestData(patameterMap);

		if (blDebug) {
			logger.info("HttpClientWSRequest invoke:" + soapRequestData);
		}
		byte[] bytes = soapRequestData.getBytes("utf-8");
		InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream, bytes.length, "application/soap+xml; charset=utf-8");
		postMethod.setRequestEntity(requestEntity);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(180000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(180000);
		statusCode = httpClient.executeMethod(postMethod);
		if (blDebug) {
			logger.info("statusCode = " + statusCode);
		}
		if (statusCode == 200) {
			soapResponseData = postMethod.getResponseBodyAsString();
			soapResponseData = getResponseBody(soapResponseData);
		}
		return this;
	}

	private String buildRequestData(Map<String, String> patameterMap) {
		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
				+ " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
		soapRequestData.append("<soap12:Body>");
		soapRequestData.append("<" + methodName + " xmlns=\"" + namespace + "\">");
		Set<String> nameSet = patameterMap.keySet();
		for (String name : nameSet) {
			soapRequestData.append("<" + name + ">" + patameterMap.get(name) + "</" + name + ">");
		}

		soapRequestData.append("</" + methodName + ">");
		soapRequestData.append("</soap12:Body>");
		soapRequestData.append("</soap12:Envelope>");

		return soapRequestData.toString();
	}

	private String getResponseBody(String data) {

		if (null == data || data.isEmpty()) {
			return null;
		}
		if (blDebug) {
			logger.info("getResponseBody:" + data);
		}
		try {

			int start = data.indexOf("<" + this.methodName + "Result>") + ("<" + this.methodName + "Result>").length();
			int end = data.indexOf("</" + this.methodName + "Result>");
			data = data.substring(start, end);
			return data;

		} catch (Exception e) {

		}
		return null;
	}
}
