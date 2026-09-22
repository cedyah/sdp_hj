package com.jebi.sdp.common;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jebi.sdp.model.EmailVO;
import com.jebi.sdp.model.FileVO;

/**
 * 컨트롤러 공통 부모 클래스.
 * 실제 구현은 StringUtil, FormatUtil, DateUtil, CryptoUtil, MailService, FileService 로 분리되어 있고,
 * 이 클래스는 기존 컨트롤러(extends CommonUtil) 호환을 위해 같은 시그니처로 위임만 한다.
 */
public class CommonUtil {
	@Autowired
	private MailService mailService;

	@Autowired
	private FileService fileService;

    /*
	 * 엑셀 다운로드
	 * @return boolean
	 */
//   public void downloadExcel(String vo_type, List<Object> contents_array, String file_name) throws Exception {
//	   
//   }

	/** 공백문자 */
	public final static char WHITE_SPACE = StringUtil.WHITE_SPACE;

	/** AES 암복호화 키. 값 참조용이며, 실제 암복호화는 CryptoUtil.KEY 를 사용한다. */
	public static String key = CryptoUtil.KEY;

	// ---- 문자열 (StringUtil) ----

	public static String nvl(String str) {
		return StringUtil.nvl(str);
	}

	public static String[] nvlArr(String[] str) {
		return StringUtil.nvlArr(str);
	}

	public static String nvlDate(String str) {
		return StringUtil.nvlDate(str);
	}

	public static String nvlNum(String str) {
		return StringUtil.nvlNum(str);
	}

	public static String nvl2(String str1, String str2) {
		return StringUtil.nvl2(str1, str2);
	}

	public static String expNvlInit(String str1, String str2) {
		return StringUtil.expNvlInit(str1, str2);
	}

	public static int pageInit(String str) {
		return StringUtil.pageInit(str);
	}

	public static int intInit(String str, int repNo) {
		return StringUtil.intInit(str, repNo);
	}

	public static String getReplaceString(String str) {
		return StringUtil.getReplaceString(str);
	}

	public static String replaceHtml(String Str) {
		return StringUtil.replaceHtml(Str);
	}

	public static String replaceScriptHtml(String Str) {
		return StringUtil.replaceScriptHtml(Str);
	}

	public static String replaceExcel(String Str) {
		return StringUtil.replaceExcel(Str);
	}

	public static int getStrCnt(String text, String str) {
		return StringUtil.getStrCnt(text, str);
	}

	public static int getExcelHeight(String text) {
		return StringUtil.getExcelHeight(text);
	}

	public static String getReplaceAll(String str, String rep1, String rep2) {
		return StringUtil.getReplaceAll(str, rep1, rep2);
	}

	public static String getReplaceAll_int(int str) {
		return StringUtil.getReplaceAll_int(str);
	}

	public static String getRepServCode(String servCode) {
		return StringUtil.getRepServCode(servCode);
	}

	public static String getRepBlank(String str) {
		return StringUtil.getRepBlank(str);
	}

	public static String getSubstring(String str, int sPos, int ePos) {
		return StringUtil.getSubstring(str, sPos, ePos);
	}

	public static String getNumZero(String str) {
		return StringUtil.getNumZero(str);
	}

	public static int NULLTOZERO(String NullString) {
		return StringUtil.NULLTOZERO(NullString);
	}

	public static String strSplit(String str, int leng) {
		return StringUtil.strSplit(str, leng);
	}

	public static String getParam(String source, String key, String defaultValue) {
		return StringUtil.getParam(source, key, defaultValue);
	}

	public static String alignLeft(String source, int length) {
		return StringUtil.alignLeft(source, length);
	}

	public static String alignLeft(String source, int length, boolean isEllipsis) {
		return StringUtil.alignLeft(source, length, isEllipsis);
	}

	public static String alignRight(String source, int length) {
		return StringUtil.alignRight(source, length);
	}

	public static String alignRight(String source, int length, boolean isEllipsis) {
		return StringUtil.alignRight(source, length, isEllipsis);
	}

	public static String alignCenter(String source, int length) {
		return StringUtil.alignCenter(source, length);
	}

	public static String alignCenter(String source, int length, boolean isEllipsis) {
		return StringUtil.alignCenter(source, length, isEllipsis);
	}

	public static String capitalize(String s) {
		return StringUtil.capitalize(s);
	}

	public static String replace(String s, String s1, boolean flag) {
		return StringUtil.replace(s, s1, flag);
	}

	public static String replace(String s, String s1, int i) {
		return StringUtil.replace(s, s1, i);
	}

	public static String replace(String s, String s1, String s2) {
		return StringUtil.replace(s, s1, s2);
	}

	public static String join(Object aobj[], String s) {
		return StringUtil.join(aobj, s);
	}

	public static String[] split(String s, String s1) {
		return StringUtil.split(s, s1);
	}

	public static String[] splitwords(String s) {
		return StringUtil.splitwords(s);
	}

	public static String[] splitwords(String s, String s1) {
		return StringUtil.splitwords(s, s1);
	}

	public static Vector toVector(Object[] array) {
		return StringUtil.toVector(array);
	}

	public static String[] sortStringArray(String[] source) {
		return StringUtil.sortStringArray(source);
	}

	public static String[] sortStringArray(Enumeration source) {
		return StringUtil.sortStringArray(source);
	}

	public static String insertLeftChar(String source, int length, char ch) {
		return StringUtil.insertLeftChar(source, length, ch);
	}

	// ---- 표시 형식 (FormatUtil) ----

	public static String getExpDateString(String str) {
		return FormatUtil.getExpDateString(str);
	}

	public static String getExpNumString(String str) {
		return FormatUtil.getExpNumString(str);
	}

	public static String getComma(String str) {
		return FormatUtil.getComma(str);
	}

	public static String getFormatBizId(String bizId) {
		return FormatUtil.getFormatBizId(bizId);
	}

	public static String getFormatJuminNo(String juminNo, String cipherYn) {
		return FormatUtil.getFormatJuminNo(juminNo, cipherYn);
	}

	public static String addZero(String str) {
		return FormatUtil.addZero(str);
	}

	// ---- 날짜 (DateUtil) ----

	public static String getFormatDate(String dateString, String gubun) {
		return DateUtil.getFormatDate(dateString, gubun);
	}

	public static String getFormatDate422(String dateString, String gubun) {
		return DateUtil.getFormatDate422(dateString, gubun);
	}

	public static String getFormatDateTime(String dateString, String gubun) {
		return DateUtil.getFormatDateTime(dateString, gubun);
	}

	public static String getFormatDate(String dateString) {
		return DateUtil.getFormatDate(dateString);
	}

	public static String getFormatDate42(String dateString, String gubun) {
		return DateUtil.getFormatDate42(dateString, gubun);
	}

	public static String getFormatTime(String dateString) {
		return DateUtil.getFormatTime(dateString);
	}

	public static String getYYYY() {
		return DateUtil.getYYYY();
	}

	public static String getMM() {
		return DateUtil.getMM();
	}

	public static String getPrevMM() {
		return DateUtil.getPrevMM();
	}

	public static String getDD() {
		return DateUtil.getDD();
	}

	public static String getLastDD() {
		return DateUtil.getLastDD();
	}

	public static String getYYYYMM() {
		return DateUtil.getYYYYMM();
	}

	public static String getYYYYMMDD() {
		return DateUtil.getYYYYMMDD();
	}

	public static String getYYYYMMDDTIME() {
		return DateUtil.getYYYYMMDDTIME();
	}

	public static String getYMD() {
		return DateUtil.getYMD();
	}

	public int compareToToday(String value) {
		return DateUtil.compareToToday(value);
	}

	public int compareToDate(String compare, String standard) {
		return DateUtil.compareToDate(compare, standard);
	}

	public int compareTo3Month(String value) {
		return DateUtil.compareTo3Month(value);
	}

	// ---- 암복호화 (CryptoUtil) ----

	public static String randomKey() {
		return CryptoUtil.randomKey();
	}

	public static byte[] hexToByteArray(String hex) {
		return CryptoUtil.hexToByteArray(hex);
	}

	public static String byteArrayToHex(byte[] ba) {
		return CryptoUtil.byteArrayToHex(ba);
	}

	public static String encrypt(String message) throws Exception {
		return CryptoUtil.encrypt(message);
	}

	public static String decrypt(String encrypted) throws Exception {
		return CryptoUtil.decrypt(encrypted);
	}

	// ---- 메일 (MailService) ----

	public void sendMail(String from, String[] to, String subject, String contents) throws Exception {
		mailService.sendMail(from, to, subject, contents);
	}

	public void sendMail(EmailVO mailVO) throws Exception {
		mailService.sendMail(mailVO);
	}

	public void sendPreConfiguredMail(String message) throws Exception {
		mailService.sendPreConfiguredMail(message);
	}

	// ---- 파일 (FileService) ----

	public boolean uploadFile(MultipartFile multipartFile, FileVO fileVO) throws Exception {
		return fileService.uploadFile(multipartFile, fileVO);
	}

	public void deleteFile(FileVO fileVO) throws Exception {
		fileService.deleteFile(fileVO);
	}

	public static boolean makeDir(String uploadDir) {
		return FileService.makeDir(uploadDir);
	}

	public static String getFileAlias(String filename) {
		return FileService.getFileAlias(filename);
	}

	// ---- 기타 ----

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
}
