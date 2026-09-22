package com.jebi.sdp.common;

/**
 * 날짜/숫자 입력값의 특수문자 제거 유틸리티
 */
public class FormatUtil {

	/**
	 * 날짜특수문자 공백처리
	 *
	 * @param str
	 * @return
	 */
	public static String getExpDateString(String str) {
		String retStr = "";

		retStr = str.trim();
		retStr = retStr.replaceAll("-", "");
		retStr = retStr.replaceAll(",", "");
		retStr = retStr.replaceAll("\\.", "");
		retStr = retStr.replaceAll("/", "");

		return retStr;
	}

	/**
	 * 숫자타입에 특수문자(콤마) 공백처리
	 *
	 * @param str
	 * @return
	 */
	public static String getExpNumString(String str) {
		String retStr = "";

		retStr = str.trim();
		retStr = retStr.replaceAll(",", "");
		return retStr;
	}
}
