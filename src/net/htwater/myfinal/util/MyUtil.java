package net.htwater.myfinal.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jfinal.plugin.activerecord.Record;

public class MyUtil {

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * get date from string and string pattern
	 * 
	 * @param string
	 *            ,pattern
	 * @return date
	 **/
	public static Date str2Date(String string, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(string);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * get date from string and string pattern
	 * 
	 * @param string
	 *            ,pattern
	 * @return date
	 **/
	public static Date str2Date(String string) {
		DateFormat format = new SimpleDateFormat(DEFAULT_PATTERN);
		try {
			return format.parse(string);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static Date sqlTime2Date(java.sql.Timestamp timestamp) {
		return str2Date(new SimpleDateFormat(DEFAULT_PATTERN).format(timestamp));
	}

	public static String map2Str(Map<String, Object> paramsMap) {

		String params = "";
		Set<Map.Entry<String, Object>> set = paramsMap.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			params += entry.getKey() + "=" + entry.getValue();
		}
		return params;

	}

	/**
	 * get string from date and string pattern
	 * 
	 * @param date
	 *            ,pattern
	 * @return String
	 **/
	public static String date2Str(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String getCurDateStr(String pattern) {

		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());

	}

	public static String getCurDateStr() {

		DateFormat format = new SimpleDateFormat(DEFAULT_PATTERN);
		return format.format(new Date());
	}

	public static int getIntVal(Record record, String val) {
		Object obj = null;
		if (null == record || val == null) {
			return 0;
		}
		obj = record.get(val);

		if (null == obj) {
			return 0;
		}

		if (obj instanceof Double) {
			return ((Double) obj).intValue();
		}
		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).intValue();
		}

		if (obj instanceof Long) {
			return ((Long) obj).intValue();
		}

		if (obj instanceof Integer) {

			return (Integer) obj;
		}
		return Integer.parseInt(obj.toString());
	}
}
