package com.jebi.sdp.common;

/**
 * 날짜/숫자 입력값의 특수문자 제거 유틸리티
 */
public class FormatUtil {

	/**
	 * 날짜특수문자 공백처리
	 *
	 * @param str 날짜 문자열. null 이면 "" 를 반환한다.
	 * @return - , . / 를 제거한 문자열
	 */
	public static String getExpDateString(String str) {
		String retStr = "";

		if (str == null) {
			return "";
		}

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
	 * @param str 숫자 문자열. null 이면 "" 를 반환한다.
	 * @return , 를 제거한 문자열
	 */
	public static String getExpNumString(String str) {
		String retStr = "";

		if (str == null) {
			return "";
		}

		retStr = str.trim();
		retStr = retStr.replaceAll(",", "");
		return retStr;
	}
}
