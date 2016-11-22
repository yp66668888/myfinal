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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Log 输出，依赖于log4j.properties文件
 * */
public class MyLog {
	/*
	 * 系统的log输出功能
	 */

	static final Log myLog = LogFactory.getLog(MyLog.class);
	static final boolean LOGGING = MyProp.getBoolean("logging", false);
	/*
	 * system console print info log
	 * 
	 * @param msg , log message
	 */
	public static void i(String msg) {
		if (LOGGING) {
			myLog.info(msg);
		}
	}
	
	public static void v(String msg) {
		if (LOGGING) {
			myLog.info(msg);
		}
	}
	/*
	 * system console print error log
	 * 
	 * @param msg , log message
	 */
	public static void e(String msg) {
		myLog.error(msg);
	}

	public static void e(String msg, Exception e) {

		StringWriter sw = new StringWriter();

		e.printStackTrace(new PrintWriter(sw, true));

		String str = sw.toString();
		myLog.error(msg + "exception:" + str);
	}

	/*
	 * system console print Map as string log
	 * 
	 * @param paramsMap
	 */
	public static void printMap(Map<String, Object> paramsMap) {

		String params = "";
		Set<Map.Entry<String, Object>> set = paramsMap.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			params += entry.getKey() + "=" + entry.getValue();
		}
		i(params);
	}

	public static void printList(List<Map<String, Object>> list) {

		if (null == list || list.size() == 0) {
			i("list is empty");
		}
		for (Map<String, Object> map : list) {
			printMap(map);
		}
	}

}
