package com.jebi.sdp.common;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtil {
	@Autowired
	private CmmnDao dao;
	
	@Autowired
    private MailSender mailSender;
     
    @Autowired
    private SimpleMailMessage preConfiguredMessage;
	
    /*
	 * 엑셀 다운로드
	 * @return boolean
	 */
//   public void downloadExcel(String vo_type, List<Object> contents_array, String file_name) throws Exception {
//	   
//   }
    
	/**
	 * 메일 발송
	 * @return boolean
	 */
    public void sendMail(String from, String[] to, String subject, String contents) throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(contents);
		mailSender.send(message);
	}
    
    public void sendMail(EmailVO mailVO) throws Exception {
    	SimpleMailMessage message = new SimpleMailMessage();
    	
    	message.setFrom(mailVO.getFrom());
    	
    	if(!mailVO.getTo().equals("")) {
    		message.setTo(mailVO.getTo());
    	} else {
    		//ArrayList 를 StringArray 로 변환하여 message 객체 셋팅
    		String[] to_li =(String[]) (mailVO.getLi_to()).toArray(new String[(mailVO.getLi_to()).size()]); 
    		message.setTo(to_li);
    	}
//    	message.setTo("hjg2223@naver.com");
//    	message.setTo("byounghwa@kangnam.co.kr");
    	message.setSubject(mailVO.getSubject());
    	message.setText(mailVO.getContents());
    	mailSender.send(message);
    }
    
    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message) throws Exception {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
    
    /**
     * 첨부파일 서버에 저장
     * @param MultipartFile 저장될 파일, FileVO 저장된 파일 정보가 담겨진 object
     * @return boolean
     */
	public boolean uploadFile(MultipartFile multipartFile, FileVO fileVO) throws Exception{
		boolean result = false;
		
        File file = new File(fileVO.getFile_path());
        if(file.exists() == false){
            file.mkdirs();
        }

        if(multipartFile.isEmpty() == false){
        	file = new File(fileVO.getFile_path() + fileVO.getFile_nm());
        	multipartFile.transferTo(file);
        	result = true;
        }

        return result;
    }
	
	
	/**
	 * 첨부파일 서버에서 삭제
	 * @param FileVO 저장된 파일 정보가 담겨진 object
	 * @return void
	 */
	public void deleteFile(FileVO fileVO) throws Exception{
		String path = fileVO.getFile_path() + fileVO.getFile_nm(); // 삭제할 파일의 경로
		
		File file = new File(path);
		if(file.exists() == true){
			file.delete();
		}
	}
	
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

	public static String[] nvlArr(String[] str) {
		if (str == null) {
			str = new String[0];
		}
		return str;
	}

	public static String nvlDate(String str) {

		String retStr = null;

		if (str == null || str.equals("null")) {
			retStr = "";
		} else {
			retStr = getExpDateString(str);
		}

		return retStr;
	}

	public static String nvlNum(String str) {

		String retStr = null;

		if (str == null || str.equals("null")) {
			retStr = "";
		} else {
			retStr = getExpNumString(str);
		}

		return retStr;
	}

	/**
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String nvl2(String str1, String str2) {

		String retStr = null;

		if (str1 == null || str1.equals("null") || str1.trim().equals("")) {
			retStr = str2.trim();

		} else {
			retStr = str1.trim();
		}

		retStr = getReplaceString(retStr);
		return retStr;
	}

	public static String expNvlInit(String str1, String str2) {

		String retStr = null;

		if (str1 == null || str1.equals("null") || str1.trim().equals("")) {
			retStr = str2.trim();

		} else {
			retStr = str1.trim();
		}

		retStr = getReplaceString(retStr);
		return retStr;
	}

	public static int pageInit(String str) {

		int retPage = 1;

		if (str == null || str.equals("null") || str.equals("")) {
			retPage = 1;
		} else {
			retPage = Integer.parseInt(str);
		}

		return retPage;
	}

	public static int intInit(String str, int repNo) {

		if (str == null || str.equals("null") || str.equals("")) {
			return repNo;
		} else {
			return Integer.parseInt(str);
		}

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

	public static boolean makeDir(String uploadDir) {
		boolean result = false;

		String[] uploadDirArr = uploadDir.split("/");
		String path = "";

		try {
			for (int i = 0; i < uploadDirArr.length; i++) {
				path += (uploadDirArr[i] + "/");

				File tmp = new File(path);

				if (!tmp.exists()) {
					// tmp.mkdir();
					tmp.mkdirs();
					// tmp.setWritable( true, true);
				}
			}

			result = true;

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	public static String replaceHtml(String Str) {
		String retStr = Str;
		retStr = retStr.replaceAll("&lt;", "<");
		retStr = retStr.replaceAll("&gt;", ">");
		retStr = retStr.replaceAll("&amp;", "&");
		retStr = retStr.replaceAll("\n", "<br/>");
		retStr = retStr.replaceAll(" ", "&nbsp;");
		retStr = retStr.replaceAll("\"", "&#34;");
		retStr = retStr.replaceAll("'", "&#39;");
		return retStr;
	}

	public static String replaceScriptHtml(String Str) {
		String retStr = Str;
		retStr = retStr.replaceAll("&lt;", "<");
		retStr = retStr.replaceAll("&gt;", ">");
		retStr = retStr.replaceAll("&amp;", "&");
		retStr = retStr.replaceAll("\n", "<br/>");
		retStr = retStr.replaceAll("\r", "");
		// retStr = retStr.replaceAll("\r", "\\r");
		retStr = retStr.replaceAll(" ", "&nbsp;");
		retStr = retStr.replaceAll("\"", "&#34;");
		retStr = retStr.replaceAll("'", "&#39;");
		return retStr;
	}

	/**
	 * poi 엑셀 다운로드시 줄바뀜시 개행문자 ♪가 들어가는걸 삭제한다.
	 *
	 * @param Str
	 * @return
	 */
	public static String replaceExcel(String Str) {
		String retStr = Str;
		retStr = retStr.replaceAll("\\r", "");
		return retStr;
	}

	// 특정문자건수
	public static int getStrCnt(String text, String str) {
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(text);
		int count = 0;

		for (; m.find(); m.end()) {
			count++;
		}

		return count;
	}

	/**
	 * poi엑셀 높이 꼼수 병합된 셀에 대해서 wraptext가 적용이 안됨
	 *
	 * @param text
	 * @param str
	 * @return
	 */
	public static int getExcelHeight(String text) {
		String str = "\\n";
		return 260 * (getStrCnt(text, str) + 1);
	}

	public static String getReplaceAll(String str, String rep1, String rep2) {

		String retStr = "";

		if (str != null) {
			retStr = str.replaceAll(rep1, rep2);
		}

		return retStr;
	}

	public static String getReplaceAll_int(int str) {

		String retStr = "-";

		if (str != 0) {
			retStr = str + "";
		}
		return retStr;
	}

	public static String getRepServCode(String servCode) {

		if (servCode.equals("9999")) {
			return "";
		} else {
			return servCode;
		}

	}

	/*
	 * 년도나 월 기타 ... 전체(9999, 99)로 선택되었을경우 ""로 변경 request시 null이거나 ""인 경우 디폴트로
	 * 선언되는 값이 있으므로 셀렉트박스에 ""가 들어갈수 없음.
	 */
	public static String getRepBlank(String str) {

		if (str.equals("9999") || str.equals("99")) {
			return "";
		} else {
			return str;
		}

	}

	public static String getSubstring(String str, int sPos, int ePos) {

		String retStr = "";

		if (str.length() > ePos) {
			retStr = str.substring(sPos, ePos) + "...";
		} else if (str.length() == ePos) {
			retStr = str.substring(sPos, ePos);
		} else {
			retStr = str;
		}

		return retStr;
	}

	// 월 자리수 체크해서 앞에 0 붙이기
	public static String getNumZero(String str) {
		String returnStr = "";

		if (str.length() == 1) {
			returnStr = "0" + str;
		} else {
			returnStr = str;
		}
		return returnStr;
	}

	// 랜덤 key 생성
	public static String randomKey() {

		int idx = 0;

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 8; i++) {
			idx = (int) (charSet.length * Math.random());
			sb.append(charSet[idx]);
		}

		return sb.toString();

	}

	/*********************************************
	 * AES 방식 암호화 모듈 13.09.13
	 *********************************************/

	public static String key = "MCst_Encrypt_oRg"; // 암복호화 코드 키 값 (16자리)

	/**
	 * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
	 *
	 * @param hex
	 *            hex string
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0)
			return null;

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++)
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		return ba;
	}

	/**
	 * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 *
	 * @param ba
	 *            byte[]
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0)
			return null;

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	/**
	 * AES 방식의 암호화
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String message) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(message.getBytes());
		return byteArrayToHex(encrypted);
	}

	/**
	 * AES 방식의 복호화
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		byte[] original = cipher.doFinal(hexToByteArray(encrypted));
		return new String(original);
	}

	public static int NULLTOZERO(String NullString) {

		int ReturnInt = 0;

		try {
			if (NullString == null || NullString.equals("null")) {
				ReturnInt = 0;
			} else {
				ReturnInt = Integer.parseInt(getExpDateString(NullString));
			}
		} catch (Exception e) {
		}

		return ReturnInt;
	}

	public static String getFileAlias(String filename) {
		String origFname = filename;
		String saveFname = "";
		try {
			Thread.sleep(1);// 딜레이
			if (origFname != null) {
				int ext_pos = origFname.lastIndexOf(".");
				String ext = origFname.substring(ext_pos);

				Date time = new Date();
				saveFname = time.getTime() + ext;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saveFname;
	}

	/*
	 * 자리 수 글자 자르기
	 */
	public static String strSplit(String str, int leng) {
		String result = "";

		if ((str != null || str != "") && (str.length() > leng)) {
			result = str.substring(0, leng);
		}

		return result;
	}

	/*
	 * urlParsing
	 * 
	 * 참고 : http://data.seoul.go.kr/index.jsp -> "서울시 12개 분야의 새소식 정보" 검색
	 * 
	 * 수정중..............................................................
	 * 
	 * param - key : 임시 개발키(6c4644764168776139314b73476b6e) - infoName :
	 * SeoulNewsList(서울시 12개 분야의 새소식 정보) - gubun : 15 : 건강, 21 : 교통, 22 : 안전, 23
	 * : 주택, 24 : 경제, 25 : 환경, 26 : 문화/관광, 27 : 복지, 28 : 건설, 29 : 세금재정, 30 : 행정,
	 * 34 : 여성가족
	 */
	public static ArrayList<HashMap<String, String>> urlParsing(String key, String infoName, String gubun) {
		ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();

		try {
			URL url;
			url = new URL("http://openapi.seoul.go.kr:8088/" + key + "/xml/" + infoName + "/1/5/" + gubun);

			System.out.println("url : " + url);

			URLConnection urlConn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) urlConn;

			InputStream in;

			int responseCode = httpConn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {

				in = httpConn.getInputStream();

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(in);
				doc.getDocumentElement().normalize();

				NodeList node = doc.getDocumentElement().getChildNodes();

				for (int i = 0; i < node.getLength(); i++) {
					Node row = node.item(i);
					NodeList child = row.getChildNodes();

					boolean isNull = true;

					HashMap<String, String> map = new HashMap<String, String>();
					for (int a = 0; a < child.getLength(); a++) {
						Node nodeList = child.item(a);

						if (!"#text".equals(nodeList.getNodeName()) && !"CODE".equals(nodeList.getNodeName())
								&& !"MESSAGE".equals(nodeList.getNodeName())) {
							map.put(nodeList.getNodeName(), nodeList.getTextContent());
							isNull = false;
						}
					}

					if (!isNull) {// #text, CODE, MESSAGE 가 아닐경우 add
						array.add(map);
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	// 오늘 날짜와 비교하기 : 1 은 오늘 날짜가 크다, -1 은 오늘 날짜가 작다
	public int compareToToday(String value) {
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
	public int compareToDate(String compare, String standard) {
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
	public int compareTo3Month(String value) {
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

	/*
	 * 2015.06.29 황상근 추가(인터넷 돌아다니는거 StringUtil)
	 * 
	 * 
	 */

	/**
	 * 공백문자
	 */
	public final static char WHITE_SPACE = ' ';

	static String m_whiteSpace = " \t\n\r";

	static char m_citChar = '"';

	/**
	 * 문자열에서 Property형태의 값을 추출한다. Property 형태란 'key=value'형식으로 되어있는 것을 의미한다. 단,
	 * 여기에서는 하나의 문자열을 사용할 수 있게 하기 위해 각 Property의 구분자로 '::'를 사용한다. Example
	 * 
	 * @param source
	 *            프로퍼티를 검색할 원본 문자열
	 * @param key
	 *            검색할 키 문자열
	 * @param defaultValue
	 *            해당 Key에 해당하는 값이 없을때 반환할 기본값
	 * @return 검색된 Property의 Value
	 * 
	 *         <code>
	 * String source = "key1=value1::key2=value2::key3=value3";<br>
	 * String key = "key2";<br>
	 * String value = TextUtil.getParam(source,key,"Default Value");<br>
	 * </code> 위의 예제의 결과 값은 "value2" 이다.
	 */
	public static String getParam(String source, String key, String defaultValue) {
		if (source == null || key == null) {
			return defaultValue;
		}
		int i = source.indexOf(key + "=");
		if (i < 0) {
			return defaultValue;
		}
		int j = i + key.length() + 1;
		int k = source.indexOf("::", j);
		if (k < 0) {
			k = source.length();
		}
		try {
			return source.substring(j, k);
		} catch (Exception _ex) {
			return defaultValue;
		}
	}

	/**
	 * 문자열을 좌측 정렬한다. 이때 문자열뒤에 줄임표는 넣지 않는다.<br>
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @return 정렬이 이루어진 문자열
	 * 
	 *         <code>
	 * String source = "ABCDEFG";<br>
	 * String result = TextUtil.alignLeft(source, 10);<br>
	 * </code> <code>result</code>는 <code>"ABCDEFG   "</code> 을 가지게 된다.
	 */
	public static String alignLeft(String source, int length) {

		return alignLeft(source, length, false);
	}

	/**
	 * 문자열을 좌측부터 원하는만큼 자른다.(원한다면 끝에 ...을 붙인다.)<br>
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @param isEllipsis
	 *            마지막에 줄임표("...")의 여부
	 * @return 정렬이 이루어진 문자열
	 * 
	 *         <code>
	 * String source = "ABCDEFG";<br>
	 * String result = TextUtil.alignLeft(source, 5, true);<br>
	 * </code> <code>result</code>는 <code>"AB..."</code> 을 가지게 된다.
	 */
	public static String alignLeft(String source, int length, boolean isEllipsis) {

		if (source.length() <= length) {

			StringBuffer temp = new StringBuffer(source);
			for (int i = 0; i < (length - source.length()); i++) {
				temp.append(WHITE_SPACE);
			}
			return temp.toString();
		} else {
			if (isEllipsis) {

				StringBuffer temp = new StringBuffer(length);
				temp.append(source.substring(0, length - 3));
				temp.append("...");
				return temp.toString();
			} else {
				return source.substring(0, length);
			}
		}
	}

	/**
	 * 문자열을 우측 정렬한다. 이때 문자열뒤에 줄임표는 넣지 않는다.<br>
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @return 정렬이 이루어진 문자열
	 * 
	 *         <code>
	 * String source = "ABCDEFG";<br>
	 * String result = TextUtil.alignRight(source, 10);<br>
	 * </code> <code>result</code>는 <code>"   ABCDEFG"</code> 을 가지게 된다.
	 */
	public static String alignRight(String source, int length) {

		return alignRight(source, length, false);
	}

	/**
	 * 문자열을 우측 정렬한다.(원한다면 끝에 ...을 붙인다.)<br>
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @param isEllipsis
	 *            마지막에 줄임표("...")의 여부
	 * @return 정렬이 이루어진 문자열
	 * 
	 *         <code>
	 * String source = "ABCDEFG";<br>
	 * String result = TextUtil.alignRight(source, 5, true);<br>
	 * </code> <code>result</code>는 <code>"AB..."</code> 을 가지게 된다.
	 */
	public static String alignRight(String source, int length, boolean isEllipsis) {

		if (source.length() <= length) {

			StringBuffer temp = new StringBuffer(length);
			for (int i = 0; i < (length - source.length()); i++) {
				temp.append(WHITE_SPACE);
			}
			temp.append(source);
			return temp.toString();
		} else {
			if (isEllipsis) {

				StringBuffer temp = new StringBuffer(length);
				temp.append(source.substring(0, length - 3));
				temp.append("...");
				return temp.toString();
			} else {
				return source.substring(0, length);
			}
		}
	}

	/**
	 * 문자열을 중앙 정렬한다. 이때 문자열뒤에 줄임표는 넣지 않는다. 만약 공백이 홀수로 남는다면 오른쪽에 들어 간다.<br>
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @return 정렬이 이루어진 문자열
	 * 
	 *         <code>
	 * String source = "ABCDEFG";<br>
	 * String result = TextUtil.alignCenter(source, 10);<br>
	 * </code> <code>result</code>는 <code>" ABCDEFG "</code> 을 가지게 된다.
	 */
	public static String alignCenter(String source, int length) {

		return alignCenter(source, length, false);
	}

	/**
	 * 문자열을 중앙 정렬한다. 만약 공백이 홀수로 남는다면 오른쪽에 들어 간다.<br>
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @param isEllipsis
	 *            마지막에 줄임표("...")의 여부
	 * @return 정렬이 이루어진 문자열
	 * 
	 *         <code>
	 * String source = "ABCDEFG";<br>
	 * String result = TextUtil.alignCenter(source, 5,true);<br>
	 * </code> <code>result</code>는 <code>"AB..."</code> 을 가지게 된다.
	 */
	public static String alignCenter(String source, int length, boolean isEllipsis) {
		if (source.length() <= length) {

			StringBuffer temp = new StringBuffer(length);
			int leftMargin = (int) (length - source.length()) / 2;

			int rightMargin;
			if ((leftMargin * 2) == (length - source.length())) {
				rightMargin = leftMargin;
			} else {
				rightMargin = leftMargin + 1;
			}

			for (int i = 0; i < leftMargin; i++) {
				temp.append(WHITE_SPACE);
			}

			temp.append(source);

			for (int i = 0; i < rightMargin; i++) {
				temp.append(WHITE_SPACE);
			}

			return temp.toString();
		} else {
			if (isEllipsis) {

				StringBuffer temp = new StringBuffer(length);
				temp.append(source.substring(0, length - 3));
				temp.append("...");
				return temp.toString();
			} else {
				return source.substring(0, length);
			}
		}

	}

	/**
	 * 문자열의 제일 처음글자를 대문자화 한다.<br>
	 * 
	 * @param s
	 *            원본 문자였
	 * @return 대문자화 된 문자열
	 * 
	 *         <code>
	 * String source = "abcdefg";<br>
	 * String result = TextUtil.capitalize(source);<br>
	 * </code> <code>result</code>는 <code>"Abcdefg"</code> 을 가지게 된다.
	 */
	public static String capitalize(String s) {
		return !s.equals("") && s != null ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : s;
	}

	/**
	 * 원본 문자열에서 target 문자열을 찾아 해당 boolean으로 치환한다.<br>
	 * 
	 * @param s
	 *            원본 문자열
	 * @param s1
	 *            치환될 문자열
	 * @param flag
	 *            치환되어 들어갈 boolean
	 * @return 치환된 문자열
	 * 
	 *         <code>
	 * String source = "Onwer is [B] statues.";<br>
	 * String result = TextUtil.replace(source, "[B]",true);<br>
	 * </code> <code>result</code>는 <code>"Onwer is true statues."</code> 을 가지게
	 *         된다.
	 */
	public static String replace(String s, String s1, boolean flag) {
		return replace(s, s1, String.valueOf(flag));
	}

	/**
	 * 원본 문자열에서 target 문자열을 찾아 해당 숫자로 치환한다.<br>
	 * 
	 * @param s
	 *            원본 문자열
	 * @param s1
	 *            치환될 문자열
	 * @param i
	 *            치환되어 들어갈 숫자
	 * @return 치환된 문자열
	 * 
	 *         <code>
	 * String source = "Onwer is [I] statues.";<br>
	 * String result = TextUtil.replace(source, "[I]",15);<br>
	 * </code> <code>result</code>는 <code>"Onwer is 15 statues."</code> 을 가지게
	 *         된다.
	 */
	public static String replace(String s, String s1, int i) {
		return replace(s, s1, String.valueOf(i));
	}

	/**
	 * 원본 문자열에서 target 문자열을 찾아 치환한다.<br>
	 * 
	 * @param s
	 *            원본 문자열
	 * @param s1
	 *            치환될 문자열
	 * @param s2
	 *            치환되어 들어갈 문자열
	 * @return 치환된 문자열
	 * 
	 *         <code>
	 * String source = "Onwer is [I] statues.";<br>
	 * String result = TextUtil.replace(source, "[I]","fool");<br>
	 * </code> <code>result</code>는 <code>"Onwer is fool statues."</code> 을 가지게
	 *         된다.
	 */
	public static String replace(String s, String s1, String s2) {
		StringBuffer stringbuffer = new StringBuffer(s.length());
		int j = 0;
		for (int i = s.indexOf(s1, j); i != -1; i = s.indexOf(s1, j)) {
			stringbuffer.append(s.substring(j, i));
			stringbuffer.append(s2);
			j = i + s1.length();
		}

		if (j < s.length()) {
			stringbuffer.append(s.substring(j));
		}
		return stringbuffer.toString();
	}

	/**
	 * 배열을 받아 연결될 문자열로 연결한다. 이때 각 엘레멘트 사이에 구분문자열을 추가한다.<br>
	 * 
	 * @param aobj
	 *            문자열로 만들 배열
	 * @param s
	 *            각 엘레멘트의 구분 문자열
	 * @return 연결된 문자열
	 * 
	 *         <code>
	 * String[] source = new String[] {"AAA","BBB","CCC"};<br>
	 * String result = TextUtil.join(source,"+");<br>
	 * </code> <code>result</code>는 <code>"AAABBBCCC"</code>를 가지게 된다.
	 */
	public static String join(Object aobj[], String s) {
		StringBuffer stringbuffer = new StringBuffer();
		int i = aobj.length;
		if (i > 0) {
			stringbuffer.append(aobj[0].toString());
		}
		for (int j = 1; j < i; j++) {
			stringbuffer.append(s);
			stringbuffer.append(aobj[j].toString());
		}

		return stringbuffer.toString();
	}

	/**
	 * 문자열을 지정된 Token Seperator로 Tokenize한다.(문자열 배열을 리턴)<br>
	 * 
	 * @param s
	 *            원본 문지열
	 * @param s1
	 *            Token Seperators
	 * @return 토큰들의 배열
	 * 
	 *         <code>
	 * String source = "Text token\tis A Good\nAnd bad.";<br>
	 * String[] result = TextUtil.split(source, " \t\n");<br>
	 * </code> <code>result</code>는
	 *         <code>"Text","token","is","A","Good","And","bad."</code> 를 가지게
	 *         된다.
	 */
	public static String[] split(String s, String s1) {
		StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
		int i = stringtokenizer.countTokens();
		String as[] = new String[i];
		for (int j = 0; j < i; j++) {
			as[j] = stringtokenizer.nextToken();
		}

		return as;
	}

	/**
	 * 원본 문자열을 일반적인 공백문자(' ','\n','\t','\r')로 토큰화 한다.
	 * 
	 * @param s
	 *            원본문자열
	 * @return 토큰의 배열
	 * 
	 *         <code>
	 * String source = "Text token\tis A Good\nAnd\rbad.";<br>
	 * String[] result = TextUtil.splitwords(source);<br>
	 * </code> <code>result</code>는
	 *         <code>"Text","token","is","A","Good","And","bad."</code> 를 가지게
	 *         된다.
	 */
	public static String[] splitwords(String s) {
		return splitwords(s, m_whiteSpace);
	}

	/**
	 * 원본 문자열을 일반적인 공백문자(' ','\n','\t','\r')로 토큰화 한다.<br>
	 * 겹따옴표('"') 안의 내용은 하나의 토큰으로 판단하여 문자열을 토큰화 한다.
	 * 
	 * @param s
	 *            원본 문자열
	 * @param s1
	 *            Token Seperators
	 * @return Description of the Returned Value
	 * 
	 *         <code>
	 * String source = "Text token\tis A \"Good day\"\nAnd\r\"bad day.\"";<br>
	 * String[] result = TextUtil.splitwords(source);<br>
	 * </code> <code>result</code>는
	 *         <code>"Text","token","is","A","Good day","And","bad day."</code>
	 *         를 가지게 된다.
	 */
	public static String[] splitwords(String s, String s1) {
		boolean flag = false;
		StringBuffer stringbuffer = null;
		Vector vector = new Vector();
		for (int i = 0; i < s.length();) {
			char c = s.charAt(i);
			if (!flag && s1.indexOf(c) != -1) {
				if (stringbuffer != null) {
					vector.addElement(stringbuffer);
					stringbuffer = null;
				}
				for (; i < s.length() && s1.indexOf(s.charAt(i)) != -1; i++) {
					;
				}
			} else {
				if (c == m_citChar) {
					if (flag) {
						flag = false;
					} else {
						flag = true;
					}
				} else {
					if (stringbuffer == null) {
						stringbuffer = new StringBuffer();
					}
					stringbuffer.append(c);
				}
				i++;
			}
		}

		if (stringbuffer != null) {
			vector.addElement(stringbuffer);
		}
		String as[] = new String[vector.size()];
		for (int j = 0; j < vector.size(); j++) {
			as[j] = new String((StringBuffer) vector.elementAt(j));
		}

		return as;
	}

	/**
	 * 배열을 Vector로 만든다.<br>
	 * 
	 * @param array
	 *            원본 배열
	 * @return 배열과 같은 내용을 가지는 Vector
	 * 
	 */
	public static Vector toVector(Object[] array) {
		if (array == null) {
			return null;
		}

		Vector vec = new Vector(array.length);

		for (int i = 0; i < array.length; i++) {
			vec.add(i, array[i]);
		}
		return vec;
	}

	/**
	 * 문자열의 배열을 소팅한다.
	 * 
	 * @param array
	 *            원본 배열
	 * @return 배열과 같은 내용을 가지는 Vector
	 */
	public static String[] sortStringArray(String[] source) {

		java.util.Arrays.sort(source);

		return source;
	}

	/**
	 * 문자열의 Enemration을 소팅된 배열로 반환한다.
	 * 
	 * @param source
	 *            원본 열거형
	 * @return 열거형의 값을 가진 배열
	 */
	public static String[] sortStringArray(Enumeration source) {
		Vector buf = new Vector();
		while (source.hasMoreElements()) {
			buf.add(source.nextElement());
		}
		String[] buf2 = new String[buf.size()];

		for (int i = 0; i < buf.size(); i++) {

			Object obj = buf.get(i);
			if (obj instanceof String) {
				buf2[i] = (String) obj;
			} else {
				throw new IllegalArgumentException("Not String Array");
			}
		}
		java.util.Arrays.sort(buf2);
		return buf2;
	}

	/**
	 * 문자열이 입력한 길이보다 남는 공백에 좌측정렬후 원하는 문자를 삽입힌다.
	 * 
	 * @param source
	 *            원본 문자열
	 * @param length
	 *            정렬이 이루어질 길이
	 * @param ch
	 *            공백에 삽입할 원하는 문자
	 * @return 결과 문자열
	 * 
	 *         <code>
	 * String source = "ABC"<br>
	 * String result = TextUtil.insertLeftChar(source, 5, '*');<br>
	 * </code> <code>result</code>는 <code>"ABC**"</code> 을 가지게 된다.
	 */
	public static String insertLeftChar(String source, int length, char ch) {

		StringBuffer temp = new StringBuffer(length);

		if (source.length() <= length) {

			for (int i = 0; i < (length - source.length()); i++) {
				temp.append(ch);
			}
			temp.append(source);
		}
		return temp.toString();
	}
}
