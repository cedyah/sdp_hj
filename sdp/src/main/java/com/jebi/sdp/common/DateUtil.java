package com.jebi.sdp.common;

import java.util.Calendar;

/**
 * 현재 날짜 조회 유틸리티
 */
public class DateUtil {

	/**
	 * 현재 yyyy
	 *
	 * @return
	 */
	public static String getYYYY() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue = Integer.toString(cal.get(Calendar.YEAR));
		return strTValue;
	}
}
