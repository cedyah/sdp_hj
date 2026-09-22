package com.jebi.sdp.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * StringUtil / FormatUtil / DateUtil 의 기존 동작을 고정하는 특성(characterization) 테스트.
 * 기존 동작을 그대로 기록한 것이므로, 값이 바뀌면 의도한 변경인지 먼저 확인할 것.
 */
public class UtilTest {

	@Test
	public void nvl() {
		assertEquals("", StringUtil.nvl(null));
		assertEquals("", StringUtil.nvl("null"));
		assertEquals("＂a’&lt;b&gt;", StringUtil.nvl(" \"a'<b> "));
	}

	@Test
	public void expStrings() {
		assertEquals("20250102", FormatUtil.getExpDateString(" 2025-01/02 "));
		assertEquals("202501", FormatUtil.getExpDateString("2025.01,"));
		assertEquals("1234567.5", FormatUtil.getExpNumString(" 1,234,567.5 "));
	}

	@Test
	public void currentYear() {
		assertTrue(DateUtil.getYYYY().matches("\\d{4}"));
	}
}
