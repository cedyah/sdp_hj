package com.jebi.sdp.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Vector;

import org.junit.Test;

/**
 * CommonUtil 리팩토링 전 동작을 고정하는 특성(characterization) 테스트.
 * 기존 동작(버그 포함)을 그대로 기록한 것이므로, 값이 바뀌면 의도한 변경인지 먼저 확인할 것.
 */
public class CommonUtilTest {

	@Test
	public void nvl() {
		assertEquals("", CommonUtil.nvl(null));
		assertEquals("", CommonUtil.nvl("null"));
		assertEquals("＂a’&lt;b&gt;", CommonUtil.nvl(" \"a'<b> "));
		assertArrayEquals(new String[0], CommonUtil.nvlArr(null));
		assertEquals("20250101", CommonUtil.nvlDate("2025-01.01"));
		assertEquals("1234", CommonUtil.nvlNum("1,234"));
		assertEquals("b", CommonUtil.nvl2(" ", " b "));
		assertEquals("a", CommonUtil.nvl2(" a ", "b"));
		assertEquals("b", CommonUtil.expNvlInit(null, "b"));
		assertEquals(1, CommonUtil.pageInit(""));
		assertEquals(3, CommonUtil.pageInit("3"));
		assertEquals(7, CommonUtil.intInit(null, 7));
		assertEquals(5, CommonUtil.intInit("5", 7));
		assertEquals(0, CommonUtil.NULLTOZERO("null"));
		assertEquals(0, CommonUtil.NULLTOZERO("abc"));
		assertEquals(20250101, CommonUtil.NULLTOZERO("2025-01-01"));
	}

	@Test
	public void expStrings() {
		assertEquals("20250102", CommonUtil.getExpDateString(" 2025-01/02 "));
		assertEquals("202501", CommonUtil.getExpDateString("2025.01,"));
		assertEquals("1234567.5", CommonUtil.getExpNumString(" 1,234,567.5 "));
	}

	@Test
	public void formatDate() {
		assertEquals("2025-01-02", CommonUtil.getFormatDate("20250102", "-"));
		assertEquals("202501021", CommonUtil.getFormatDate("202501021", "-"));
		assertEquals("", CommonUtil.getFormatDate(null, "-"));
		assertEquals("2025.01.02", CommonUtil.getFormatDate422("202501021", "."));
		assertEquals("2025", CommonUtil.getFormatDate422("2025", "."));
		assertEquals("2025-01-02 03:04:05", CommonUtil.getFormatDateTime("20250102030405", "-"));
		assertEquals("", CommonUtil.getFormatDateTime("2025010203040", "-"));
		assertEquals("2025년01월02일", CommonUtil.getFormatDate("20250102"));
		assertEquals("2025.01", CommonUtil.getFormatDate42("202501", "."));
		assertEquals("", CommonUtil.getFormatDate42("", "."));
		assertEquals("03:04", CommonUtil.getFormatTime("0304"));
		assertEquals("03:04:05", CommonUtil.getFormatTime("030405"));
		assertEquals("", CommonUtil.getFormatTime("03"));
	}

	@Test
	public void currentDateShapes() {
		assertTrue(CommonUtil.getYYYY().matches("\\d{4}"));
		assertTrue(CommonUtil.getMM().matches("\\d{2}"));
		assertTrue(CommonUtil.getDD().matches("\\d{2}"));
		assertTrue(CommonUtil.getLastDD().matches("\\d{2}"));
		assertTrue(CommonUtil.getYYYYMM().matches("\\d{6}"));
		assertTrue(CommonUtil.getYYYYMMDD().matches("\\d{8}"));
		assertTrue(CommonUtil.getYYYYMMDDTIME().matches("\\d{14}"));
		assertTrue(CommonUtil.getYMD().matches("\\d{4}-\\d{2}-\\d{2}"));
		assertTrue(CommonUtil.getPrevMM().matches("\\d{1,2}"));
	}

	@Test
	public void compareDates() {
		CommonUtil util = new CommonUtil();
		assertEquals(1, util.compareToToday("2000-01-01"));
		assertEquals(-1, util.compareToToday("2999-01-01"));
		assertEquals(1, util.compareToDate("20250101", "2025-02-01"));
		assertEquals(-1, util.compareToDate("20250201", "20250101"));
		assertEquals(1, util.compareTo3Month("20000101"));
		assertEquals(-1, util.compareTo3Month("29990101"));
	}

	@Test
	public void numberAndIdFormats() {
		assertEquals("1,234,567", CommonUtil.getComma("1234567"));
		assertEquals("-1,234.56", CommonUtil.getComma("-1234.56"));
		assertEquals("0.5", CommonUtil.getComma(".5"));
		assertEquals("", CommonUtil.getComma("null"));
		assertEquals("123-45-67890", CommonUtil.getFormatBizId("1234567890"));
		assertEquals("123", CommonUtil.getFormatBizId("123"));
		assertEquals("900101-1******", CommonUtil.getFormatJuminNo("9001011234567", "Y"));
		assertEquals("900101-1234567", CommonUtil.getFormatJuminNo("9001011234567", "N"));
		assertEquals("-0.5", CommonUtil.addZero("-.5"));
		assertEquals("0.5", CommonUtil.addZero(".5"));
		assertEquals("", CommonUtil.addZero(null));
		assertEquals("05", CommonUtil.getNumZero("5"));
		assertEquals("12", CommonUtil.getNumZero("12"));
	}

	@Test
	public void htmlAndReplace() {
		assertEquals("<a>&<br/>&nbsp;&#34;&#39;", CommonUtil.replaceHtml("&lt;a&gt;&amp;\n \"'"));
		assertEquals("<br/>&nbsp;", CommonUtil.replaceScriptHtml("\r\n "));
		assertEquals("ab", CommonUtil.replaceExcel("a\rb"));
		assertEquals(2, CommonUtil.getStrCnt("a\nb\nc", "\\n"));
		assertEquals(780, CommonUtil.getExcelHeight("a\nb\nc"));
		assertEquals("", CommonUtil.getReplaceAll(null, "a", "b"));
		assertEquals("xbx", CommonUtil.getReplaceAll("aba", "a", "x"));
		assertEquals("-", CommonUtil.getReplaceAll_int(0));
		assertEquals("3", CommonUtil.getReplaceAll_int(3));
		assertEquals("", CommonUtil.getRepServCode("9999"));
		assertEquals("0100", CommonUtil.getRepServCode("0100"));
		assertEquals("", CommonUtil.getRepBlank("99"));
		assertEquals("01", CommonUtil.getRepBlank("01"));
		assertEquals("abc...", CommonUtil.getSubstring("abcdef", 0, 3));
		assertEquals("abc", CommonUtil.getSubstring("abc", 0, 3));
		assertEquals("ab", CommonUtil.getSubstring("ab", 0, 3));
		assertEquals("ab", CommonUtil.strSplit("abc", 2));
		assertEquals("", CommonUtil.strSplit("ab", 2));
	}

	@Test
	public void textUtils() {
		assertEquals("value2", CommonUtil.getParam("key1=value1::key2=value2", "key2", "d"));
		assertEquals("d", CommonUtil.getParam("key1=value1", "key9", "d"));
		assertEquals("ABC  ", CommonUtil.alignLeft("ABC", 5));
		assertEquals("AB...", CommonUtil.alignLeft("ABCDEFG", 5, true));
		assertEquals("  ABC", CommonUtil.alignRight("ABC", 5));
		assertEquals("ABCDE", CommonUtil.alignRight("ABCDEFG", 5));
		assertEquals(" ABC  ", CommonUtil.alignCenter("ABC", 6));
		assertEquals("AB...", CommonUtil.alignCenter("ABCDEFG", 5, true));
		assertEquals("Abc", CommonUtil.capitalize("aBC"));
		assertEquals("x true y", CommonUtil.replace("x [B] y", "[B]", true));
		assertEquals("x 15 y", CommonUtil.replace("x [I] y", "[I]", 15));
		assertEquals("fool fool", CommonUtil.replace("[I] [I]", "[I]", "fool"));
		assertEquals("A+B+C", CommonUtil.join(new String[] { "A", "B", "C" }, "+"));
		assertArrayEquals(new String[] { "a", "b", "c" }, CommonUtil.split("a b\tc", " \t"));
		assertArrayEquals(new String[] { "Text", "Good day", "bad" }, CommonUtil.splitwords("Text \"Good day\"\nbad"));
		assertArrayEquals(new String[] { "a", "b c" }, CommonUtil.splitwords("a,\"b c\"", ","));
		Vector v = CommonUtil.toVector(new Object[] { "a", "b" });
		assertEquals(2, v.size());
		assertEquals(null, CommonUtil.toVector(null));
		assertArrayEquals(new String[] { "a", "b" }, CommonUtil.sortStringArray(new String[] { "b", "a" }));
		assertArrayEquals(new String[] { "a", "b" }, CommonUtil.sortStringArray(new Vector<String>(java.util.Arrays.asList("b", "a")).elements()));
		assertEquals("**ABC", CommonUtil.insertLeftChar("ABC", 5, '*'));
		assertEquals("", CommonUtil.insertLeftChar("ABCDEF", 5, '*'));
		assertEquals(' ', CommonUtil.WHITE_SPACE);
	}

	@Test
	public void crypto() throws Exception {
		assertEquals("MCst_Encrypt_oRg", CommonUtil.key);
		String enc = CommonUtil.encrypt("hello 한진");
		assertEquals(enc, CommonUtil.encrypt("hello 한진"));
		assertEquals("hello 한진", CommonUtil.decrypt(enc));
		// 기존에 저장된 암호문이 계속 복호화되는지 확인 (키/알고리즘 변경 감지용)
		assertEquals("b4bf99cdd0d9ae0cc6f983df4f3c3810", CommonUtil.encrypt("sdp"));
		assertEquals("sdp", CommonUtil.decrypt("b4bf99cdd0d9ae0cc6f983df4f3c3810"));
		assertEquals("0aff", CommonUtil.byteArrayToHex(new byte[] { 10, -1 }));
		assertArrayEquals(new byte[] { 10, -1 }, CommonUtil.hexToByteArray("0aff"));
		assertEquals(null, CommonUtil.hexToByteArray(""));
		assertEquals(null, CommonUtil.byteArrayToHex(new byte[0]));
		assertTrue(CommonUtil.randomKey().matches("[0-9A-Za-z]{8}"));
	}

	@Test
	public void files() throws Exception {
		assertTrue(CommonUtil.getFileAlias("report.pdf").matches("\\d+\\.pdf"));
		assertEquals("", CommonUtil.getFileAlias(null));
		File tmp = File.createTempFile("cutest", "");
		tmp.delete();
		String dir = tmp.getAbsolutePath() + "/a/b";
		assertTrue(CommonUtil.makeDir(dir));
		assertTrue(new File(dir).isDirectory());
	}
}
