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
package net.htwater.myfinal.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jfinal.aop.Interceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

import net.htwater.myfinal.util.MyLog;

/*
 *系统的入口 
 * 
 * */

public class BaseConfig extends JFinalConfig {
	private static Logger logger = Logger.getLogger(BaseConfig.class.getName());
	private static List<DataSourceItem> dsList;
	private static List<ControllerItem> controllerList;
	private static List<InterceptorItem> interceptorList;
	private static List<HandlerItem> handlerList;

	@Override
	public final void configConstant(Constants constants) {
		PropKit.use("config.properties");
		constants.setDevMode(PropKit.getBoolean("debug", false));
		constants.setBaseUploadPath(PropKit.get("fileserverpath"));
		constants.setBaseDownloadPath(PropKit.get("fileserverpath"));
	}

	@Override
	public final void configPlugin(Plugins plugins) {
		
		logger.info("db count=" + dsList.size());

		for (int i = 0; i < dsList.size(); i++) {

			DataSourceItem item = dsList.get(i);
			if (item.isEnable()) {
				try {
					C3p0Plugin cp = new C3p0Plugin(item.getUrl(), item.getUsername(), item.getPassword(),
							item.getDriverClassName());// 使用C3P0
					cp.setInitialPoolSize(50).setMinPoolSize(50).setMaxPoolSize(500);
					plugins.add(cp);
					ActiveRecordPlugin arp = new ActiveRecordPlugin(item.getDatasourceName(), cp);
					
					logger.info("db"+ i + ":" + item.getUrl());
					plugins.add(arp);
					item.setActiveRecord(arp);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("add db plugin  error", e.getCause());
				}
			}
		}
		plugins.add(new EhCachePlugin());
	}

	@Override
	public final void configHandler(Handlers handlers) {

		logger.info("handler count="+ handlerList.size());
		for (int i = 0; i < handlerList.size(); i++) {

			HandlerItem item = handlerList.get(i);
			if (item.isEnable()) {
				try {
					Handler handler = (Handler) Class.forName(item.getClassName()).newInstance();
					handlers.add(handler);
					logger.info("handler"+ i +":" + item.getClassName());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("add handler error", e.getCause());
				}
			}

		}

	}

	@Override
	public final void configInterceptor(Interceptors interceptors) {
		logger.info("interceptor count="+ interceptorList.size());

		for (int i = 0; i < interceptorList.size(); i++) {

			InterceptorItem item = interceptorList.get(i);
			if (item.isEnable()) {
				try {
					Interceptor interceptor = (Interceptor) Class.forName(item.getClassName()).newInstance();
					interceptors.add(interceptor);
					logger.info("interceptor"+ i +":" + item.getClassName());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("add Controller error", e.getCause());
				}
			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public final void configRoute(Routes routes) {

		logger.info("controller count="+ controllerList.size());
		for (int i = 0; i < controllerList.size(); i++) {

			ControllerItem item = controllerList.get(i);
			if (item.isEnable()) {
				try {
					routes.add(item.getPath(), (Class<? extends Controller>) Class.forName(item.getClassName()));
					
					logger.info("controller"+ i +":" + item.getClassName());
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("add Controller error", e.getCause());
				}
			}

		}

	}

	@Override
	public void afterJFinalStart() {
		logger.info("<------------App Start success--------->");

	}

	@Override
	public void beforeJFinalStop() {
		logger.info("<------------App Will Stop ------------>");
	}

	@SuppressWarnings("rawtypes")
	private void init() {
		dsList = new ArrayList<DataSourceItem>();
		interceptorList = new ArrayList<InterceptorItem>();
		controllerList = new ArrayList<ControllerItem>();
		handlerList = new ArrayList<HandlerItem>();
		SAXReader saxReader = new SAXReader();
		InputStream in = BaseConfig.class.getResourceAsStream("/config.xml");
		try {
			Document document = saxReader.read(new InputStreamReader(in));
			List listDs = document.selectNodes("//ds");
			for (Iterator localIterator1 = listDs.iterator(); localIterator1.hasNext();) {
				Object obj = localIterator1.next();
				Element element = (Element) obj;
				String name = element.attributeValue("name");
				Iterator nodeIterator = element.elementIterator();
				DataSourceItem datasouce = new DataSourceItem(name);
				while (nodeIterator.hasNext()) {
					Element node = (Element) nodeIterator.next();
					if ("classname".equals(node.getName()))
						datasouce.setDriverClassName(node.getTextTrim());
					else if ("username".equals(node.getName()))
						datasouce.setUsername(node.getTextTrim());
					else if ("dburl".equals(node.getName()))
						datasouce.setUrl(node.getTextTrim());
					else if ("enable".equals(node.getName())) {
						if (node.getTextTrim().equals("1")) {
							datasouce.setEnable(true);
						} else {
							datasouce.setEnable(false);
						}
					} else if ("password".equals(node.getName())) {
						datasouce.setPassword(node.getTextTrim());
					}
				}
				dsList.add(datasouce);
			}

			List listInterceptor = document.selectNodes("//Interceptor");
			for (Iterator localIterator1 = listInterceptor.iterator(); localIterator1.hasNext();) {
				Object obj = localIterator1.next();
				Element element = (Element) obj;
				Iterator nodeIterator = element.elementIterator();
				InterceptorItem item = new InterceptorItem();
				while (nodeIterator.hasNext()) {
					Element node = (Element) nodeIterator.next();
					if ("classname".equals(node.getName()))
						item.setClassName(node.getTextTrim());
					else if ("enable".equals(node.getName())) {
						if (node.getTextTrim().equals("1")) {
							item.setEnable(true);
						} else {
							item.setEnable(false);
						}
					}
				}
				interceptorList.add(item);
			}

			List listHandler = document.selectNodes("//Handler");
			for (Iterator localIterator1 = listHandler.iterator(); localIterator1.hasNext();) {
				Object obj = localIterator1.next();
				Element element = (Element) obj;
				Iterator nodeIterator = element.elementIterator();
				HandlerItem item = new HandlerItem();
				while (nodeIterator.hasNext()) {
					Element node = (Element) nodeIterator.next();
					if ("classname".equals(node.getName()))
						item.setClassName(node.getTextTrim());
					else if ("enable".equals(node.getName())) {
						if (node.getTextTrim().equals("1")) {
							item.setEnable(true);
						} else {
							item.setEnable(false);
						}
					}
				}
				handlerList.add(item);
			}

			List listController = document.selectNodes("//Controller");
			for (Iterator localIterator1 = listController.iterator(); localIterator1.hasNext();) {
				Object obj = localIterator1.next();
				Element element = (Element) obj;
				Iterator nodeIterator = element.elementIterator();
				ControllerItem item = new ControllerItem();
				while (nodeIterator.hasNext()) {
					Element node = (Element) nodeIterator.next();
					if ("classname".equals(node.getName()))
						item.setClassName(node.getTextTrim());
					else if ("enable".equals(node.getName())) {
						if (node.getTextTrim().equals("1")) {
							item.setEnable(true);
						} else {
							item.setEnable(false);
						}
					} else if ("path".equals(node.getName())) {

						item.setPath(node.getTextTrim());
					}
				}
				controllerList.add(item);
			}

		} catch (DocumentException e) {
			new RuntimeException("config.xml can not found!");
			e.printStackTrace();
			MyLog.e("config.xml can not found!", e);
		} catch (Exception e) {
			new RuntimeException("config init failed");
			e.printStackTrace();
			MyLog.e("config init failed", e);
		}
	}

	public BaseConfig() {
		init();
	}

}
