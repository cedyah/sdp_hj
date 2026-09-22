package com.jebi.sdp.common;

/**
 * 날짜/숫자 특수문자 제거, 콤마, 사업자번호·주민번호 등 표시 형식 유틸리티
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

	/**
	 * 3자리마다 , 붙이기(String 형)
	 */
	public static String getComma(String str) {

		String flag = "N";// 소숫점 체크
		int iPos = 0;
		int p = 0;
		String retVal = "";

		if (str == null || str.equals("") || str.equals("null")) {
			return "";
		} else {

			iPos = str.length();

			for (int i = 0; i < str.length(); i++) {
				if (str.substring(i, (i + 1)).equals(".")) {
					flag = "Y";
					iPos = i;
				}
			}

			StringBuffer sb = new StringBuffer(str.substring(0, iPos).toString());
			StringBuffer rsb = new StringBuffer();
			StringBuffer sbf = new StringBuffer(str.substring(iPos, str.length()));
			sb = sb.reverse();

			for (int i = 0; i < iPos; i++) {
				p = i % 3;
				if (i > 0)
					if (p == 0 && !sb.substring(i, (i + 1)).equals("-"))
						rsb.append(",");
				rsb.append(sb.substring(i, (i + 1)));
			}

			retVal = (rsb.reverse()).toString();

			if (flag.equals("Y")) {
				retVal = retVal + sbf.toString();
			}
		}

		return addZero(retVal);
	}

	/**
	 * 사업자번호 패턴
	 *
	 * @param bizId
	 * @return
	 */
	public static String getFormatBizId(String bizId) {

		String retBizId = "";

		try {
			if (bizId.length() < 10) {
				retBizId = bizId;
			} else {
				retBizId = bizId.substring(0, 3) + "-" + bizId.substring(3, 5) + "-" + bizId.substring(5, 10);
			}
		} catch (Exception e) {
		}

		return retBizId;
	}

	/**
	 * 주민번호 패턴
	 *
	 * @param juminNo
	 * @param cipherYn
	 *            : * 처리 여부
	 * @return
	 */
	public static String getFormatJuminNo(String juminNo, String cipherYn) {

		String retJuminNo = "";

		try {
			if (juminNo.length() < 13) {
				retJuminNo = juminNo;
			} else {
				if (cipherYn.equals("Y")) {
					retJuminNo = juminNo.substring(0, 6) + "-" + juminNo.substring(6, 7) + "******";
				} else {
					retJuminNo = juminNo.substring(0, 6) + "-" + juminNo.substring(6, 13);
				}
			}
		} catch (Exception e) {
		}

		return retJuminNo;
	}

	/**
	 * .000으로 시작하는 스트링 앞에 0 붙이기
	 *
	 * @param str
	 * @return
	 */
	public static String addZero(String str) {
		String returnStr = str;
		if (str == null || str.equals("null") || str.equals("")) {
			returnStr = "";
		} else {
			if (str.substring(0, 1).equals(".")) {
				returnStr = "0" + str;
			} else if (str.substring(0, 1).equals("-")) {
				if (str.length() > 1) {
					if (str.substring(1, 2).equals(".")) {
						returnStr = "-0" + str.substring(1);
					}
				}
			}
		}
		return returnStr;
	}
}
