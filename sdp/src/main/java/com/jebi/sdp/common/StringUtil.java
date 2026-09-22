package com.jebi.sdp.common;

/**
 * 문자열 null 처리 유틸리티
 */
public class StringUtil {

	/**
	 * @param str
	 * @return
	 */
	public static String nvl(String str) {

		String retStr = null;

		if (str == null || str.equals("null")) {
			retStr = "";
		} else {
			retStr = getReplaceString(str);
		}

		return retStr;
	}

	public static String getReplaceString(String str) {
		String retStr = "";

		retStr = str.trim();
		retStr = retStr.replaceAll("\"", "＂");
		retStr = retStr.replaceAll("'", "’");
		// str = str.replaceAll("\"", "&#34;");
		// str = str.replaceAll("'", "&#39;");
		retStr = retStr.replaceAll("<", "&lt;");
		retStr = retStr.replaceAll(">", "&gt;");
		// retStr = retStr.replaceAll("&","&amp");

		return retStr;
	}
}
