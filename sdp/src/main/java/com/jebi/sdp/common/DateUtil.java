package com.jebi.sdp.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 날짜 형식 변환, 현재 날짜 조회, 날짜 비교 유틸리티
 */
public class DateUtil {

	public static String getFormatDate(String dateString, String gubun) {

		String dateFormat = "";

		try {
			if (dateString.length() == 8) {
				dateFormat = dateString.substring(0, 4) + gubun + dateString.substring(4, 6) + gubun
						+ dateString.substring(6, 8);
			} else {
				dateFormat = dateString;
			}
		} catch (Exception e) {
		}

		return dateFormat;
	}

	/**
	 * yyyymmdd -> yyyy.mm.dd
	 *
	 * @param dateString
	 * @return
	 */
	public static String getFormatDate422(String dateString, String gubun) {

		String dateFormat422 = "";

		try {
			if (dateString.length() < 8) {
				dateFormat422 = dateString;
			} else {
				dateFormat422 = dateString.substring(0, 4) + gubun + dateString.substring(4, 6) + gubun
						+ dateString.substring(6, 8);
			}
		} catch (Exception e) {
		}

		return dateFormat422;
	}

	/**
	 * yyyymmdd -> yyyy.mm.dd
	 *
	 * @param dateString
	 * @return
	 */
	public static String getFormatDateTime(String dateString, String gubun) {

		String dateFormat422 = "";

		try {
			if (dateString.length() < 12) {
				dateFormat422 = dateString;
			} else {

				dateFormat422 = dateString.substring(0, 4) + gubun + dateString.substring(4, 6) + gubun
						+ dateString.substring(6, 8) + " " + dateString.substring(8, 10) + ":"
						+ dateString.substring(10, 12) + ":" + dateString.substring(12, 14);
			}
		} catch (Exception e) {
		}

		return dateFormat422;
	}

	/**
	 * yyyymmdd -> yyyy년mm월dd일
	 *
	 * @param dateString
	 * @return
	 */
	public static String getFormatDate(String dateString) {

		String dateFormat422 = "";

		try {
			if (dateString.length() < 8) {
				dateFormat422 = dateString;
			} else {
				dateFormat422 = dateString.substring(0, 4) + "년" + dateString.substring(4, 6) + "월"
						+ dateString.substring(6, 8) + "일";
			}
		} catch (Exception e) {
		}

		return dateFormat422;
	}

	/**
	 * yyyymm -> yyyy.mm
	 *
	 * @param dateString
	 * @return
	 */
	public static String getFormatDate42(String dateString, String gubun) {

		String dateFormat42 = "";

		try {
			if (dateString == null || dateString.equals("")) {
				dateFormat42 = dateString;
			} else {
				if (dateString.length() == 6) {
					dateFormat42 = dateString.substring(0, 4) + gubun + dateString.substring(4, 6);
				} else {
					dateFormat42 = dateString;
				}
			}
		} catch (Exception e) {
		}

		return dateFormat42;
	}

	public static String getFormatTime(String dateString) {

		String dateFormat = "";

		try {
			if (dateString.length() == 4) {
				dateFormat = dateString.substring(0, 2) + ":" + dateString.substring(2, 4);
			} else if (dateString.length() == 6) {
				dateFormat = dateString.substring(0, 2) + ":" + dateString.substring(2, 4) + ":"
						+ dateString.substring(4, 6);
			} else {
				;
			}
		} catch (Exception e) {
		}

		return dateFormat;
	}

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

	/**
	 * 현재 mm
	 *
	 * @return
	 */
	public static String getMM() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue += (cal.get(Calendar.MONTH) + 1 >= 10) ? Integer.toString(cal.get(Calendar.MONTH) + 1)
				: "0" + (cal.get(Calendar.MONTH) + 1);
		return strTValue;
	}

	public static String getPrevMM() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue += (cal.get(Calendar.MONTH) + 1 >= 10) ? Integer.toString(cal.get(Calendar.MONTH))
				: "0" + (cal.get(Calendar.MONTH) + 1);
		return strTValue;
	}

	/**
	 * 현재 dd
	 *
	 * @return
	 */
	public static String getDD() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue += (cal.get(Calendar.DATE) >= 10) ? Integer.toString(cal.get(Calendar.DATE))
				: "0" + (cal.get(Calendar.DATE));
		return strTValue;
	}

	/**
	 * 현재월의 마지막일자
	 *
	 * @return
	 */
	public static String getLastDD() {
		Calendar cal = Calendar.getInstance();
		return Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 현재 yyyymm
	 *
	 * @return
	 */
	public static String getYYYYMM() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue = Integer.toString(cal.get(Calendar.YEAR));
		strTValue += (cal.get(Calendar.MONTH) + 1 >= 10) ? Integer.toString(cal.get(Calendar.MONTH) + 1)
				: "0" + (cal.get(Calendar.MONTH) + 1);
		return strTValue;
	}

	/**
	 * 현재 yyyymmdd
	 *
	 * @return
	 */
	public static String getYYYYMMDD() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue = Integer.toString(cal.get(Calendar.YEAR));
		strTValue += (cal.get(Calendar.MONTH) + 1 >= 10) ? Integer.toString(cal.get(Calendar.MONTH) + 1)
				: "0" + (cal.get(Calendar.MONTH) + 1);
		strTValue += (cal.get(Calendar.DATE) >= 10) ? Integer.toString(cal.get(Calendar.DATE))
				: "0" + (cal.get(Calendar.DATE));
		return strTValue;
	}

	/**
	 * 현재 yyyymmddHHMMSS
	 *
	 * @return
	 */
	public static String getYYYYMMDDTIME() {
		Calendar cal = Calendar.getInstance();
		String strTValue = "";
		strTValue = Integer.toString(cal.get(Calendar.YEAR));
		strTValue += (cal.get(Calendar.MONTH) + 1 >= 10) ? Integer.toString(cal.get(Calendar.MONTH) + 1)
				: "0" + (cal.get(Calendar.MONTH) + 1);
		strTValue += (cal.get(Calendar.DATE) >= 10) ? Integer.toString(cal.get(Calendar.DATE))
				: "0" + (cal.get(Calendar.DATE));
		strTValue += (cal.get(Calendar.HOUR_OF_DAY) >= 10) ? Integer.toString(cal.get(Calendar.HOUR_OF_DAY))
				: "0" + (cal.get(Calendar.HOUR_OF_DAY));
		strTValue += (cal.get(Calendar.MINUTE) >= 10) ? Integer.toString(cal.get(Calendar.MINUTE))
				: "0" + (cal.get(Calendar.MINUTE));
		strTValue += (cal.get(Calendar.SECOND) >= 10) ? Integer.toString(cal.get(Calendar.SECOND))
				: "0" + (cal.get(Calendar.SECOND));
		return strTValue;
	}

	/**
	 * 현재 yyyy-mm-dd 형태로 가져온다.
	 *
	 * @return
	 */
	public static String getYMD() {
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String TodayDate = sdf1.format(new java.util.Date());
		return TodayDate;
	}

	// 오늘 날짜와 비교하기 : 1 은 오늘 날짜가 크다, -1 은 오늘 날짜가 작다
	public static int compareToToday(String value) {
		int chk = 0;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			date = (Date) sdf.parse(value.replace("-", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar compareDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		compareDate.setTime(date);

		chk = today.compareTo(compareDate);

		return chk;
	}

	// 두개의 날짜 비교하기 : 1 은 StandardDate 가 크다, -1 은 StandardDate 가 작다
	public static int compareToDate(String compare, String standard) {
		int chk = 0;
		Date compareDt = null;
		Date standardDt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			compareDt = (Date) sdf.parse(compare.replace("-", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			standardDt = (Date) sdf.parse(standard.replace("-", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar compareDate = Calendar.getInstance();
		Calendar standardDate = Calendar.getInstance();
		compareDate.setTime(compareDt);
		standardDate.setTime(standardDt);

		chk = standardDate.compareTo(compareDate);

		return chk;
	}

	// 3개월 전 오늘 날짜와 비교하기 : 1 은 3개월전 오늘 날짜가 크다, -1 은 3개월전 오늘 날짜가 작다
	public static int compareTo3Month(String value) {
		int chk = 0;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			date = (Date) sdf.parse(value.replace("-", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar compareDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		compareDate.setTime(date);
		today.add(Calendar.MONTH, -3);

		chk = today.compareTo(compareDate);

		System.out.println("chk : " + chk);

		return chk;
	}
}
