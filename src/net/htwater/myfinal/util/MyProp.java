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

import com.jfinal.kit.Prop;

/*
 * 系统配置文件读取功能，系统默认只有的一个config.properties文件
 * */
public class MyProp {

	private static Prop prop = new Prop("config.properties", "UTF-8");;

	public static String get(String key) {
		return prop.get(key);
	}

	public static String get(String key, String defaultValue) {
		return prop.get(key, defaultValue);
	}

	public static Integer getInt(String key) {
		return prop.getInt(key);
	}

	public static Integer getInt(String key, Integer defaultValue) {
		return prop.getInt(key, defaultValue);
	}

	public static Long getLong(String key) {
		return prop.getLong(key);
	}

	public static Long getLong(String key, Long defaultValue) {
		return prop.getLong(key, defaultValue);
	}

	public static Boolean getBoolean(String key) {
		return prop.getBoolean(key);
	}

	public static Boolean getBoolean(String key, Boolean defaultValue) {
		return prop.getBoolean(key, defaultValue);
	}
	
	public static Boolean blLog(){
		return prop.getBoolean("logging", true);
	}

}
