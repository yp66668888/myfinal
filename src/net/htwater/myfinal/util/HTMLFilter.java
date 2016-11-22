package net.htwater.myfinal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLFilter {

	public static String removeHTMLTags(String str, String tags) {
		if (str == null)
			return null;
		if (tags == null)
			return str;
		String regx = "(</?)(" + tags + ")([^>]*>)";
		Matcher matcher;
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);// 不区分大小写
		// 此处需要循环匹配，防止恶意构造的字符串
		while ((matcher = pattern.matcher(str)).find()) {
			str = matcher.replaceAll("");
		}

		return str;
	}

	public static String removeEvents(String content) {

		String regx = "(<[^<]*)(on\\w*\\x20*=|javascript:)";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);// 不区分大小写
		Matcher matcher;
		String ts = content;
		// 此处需要循环匹配，防止恶意构造的字符串如 onclick=onclick=XXX
		while ((matcher = pattern.matcher(ts)).find()) {
			ts = matcher.replaceAll("$1");
		}
		return ts;
	}

	public static String makeSafe(String content) {
		return removeEvents(removeHTMLTags(content,
				"html|body|head|title|style|video|canvas|script|iframe|frameset|frame|object|embed|xml|input|button|textarea|select|pre|option|plaintext|form"));
	}

	public static String makeSafe(String content, String tags) {
		if (tags == null)
			return makeSafe(content);
		return removeEvents(removeHTMLTags(content, tags));
	}

	public static void main(String[] args) {

		System.out.println(makeSafe(
				"dfdf<a href=\"111\" onclick=onclick=alert(1) href=\"javascript:alert(1)\">22233</a><plaintext><script type=\"javascript\">alert(123)</script><p onclick= '111'>111</p>"));
	}

}
