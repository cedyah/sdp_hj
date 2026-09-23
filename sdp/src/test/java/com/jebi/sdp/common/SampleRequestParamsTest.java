package com.jebi.sdp.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jebi.sdp.model.SampleRequestVO;

/**
 * 샘플의뢰 헤더 프로시저 파라미터 구성이 Sdph0050/Sdph0052 에 있던 기존 코드와
 * 같은 값을 만드는지 고정하는 테스트.
 */
public class SampleRequestParamsTest {

	private SampleRequestVO vo;

	@Before
	public void setUp() throws Exception {
		bindEmptyRequest();
		vo = new SampleRequestVO();
		vo.setSaeobjang("1000");
		vo.setIlja("2025-01-02");
		vo.setJeonpyo_no("A001");
		vo.setNabpum_ilja("2025.03.04");
		vo.setBigo_1("비고");
	}

	@After
	public void tearDown() {
		RequestContextHolder.resetRequestAttributes();
	}

	@Test
	public void insertNormalizesDates() {
		HashMap<String, Object> map = SampleRequestParams.forInsert(vo);

		assertEquals("INSERT", map.get("ARG_FLAG"));
		// 작성 시에는 - . / 를 제거해서 넘긴다
		assertEquals("20250102", map.get("ARG_ILJA"));
		assertEquals("20250304", map.get("ARG_NABPUM_ILJA"));
		assertEquals("1000", map.get("ARG_SAEOBJANG"));
		assertEquals("A001", map.get("ARG_JEONPYO_NO"));
		assertEquals("비고", map.get("ARG_BIGO1"));
		assertEquals("", map.get("OUT_PARAM"));
	}

	@Test
	public void updateKeepsRawDates() {
		HashMap<String, Object> map = SampleRequestParams.forUpdate(vo);

		assertEquals("update", map.get("ARG_FLAG"));
		// 수정 시에는 화면 입력값 그대로 넘긴다 (기존 동작)
		assertEquals("2025-01-02", map.get("ARG_ILJA"));
		assertEquals("2025.03.04", map.get("ARG_NABPUM_ILJA"));
		assertEquals("", map.get("OUT_PARAM"));
	}

	@Test
	public void bothHaveSameParameterNames() {
		assertEquals(SampleRequestParams.forInsert(vo).keySet(), SampleRequestParams.forUpdate(vo).keySet());
		// ARG_FLAG, OUT_PARAM 을 포함해 35개 (기존 컨트롤러 코드와 같은 수)
		assertEquals(35, SampleRequestParams.forInsert(vo).size());
		assertTrue(SampleRequestParams.forInsert(vo).containsKey("ARG_GYEOLGWA_GIHAN"));
	}

	/** SampleRequestVO 생성자가 현재 요청의 세션을 읽으므로 빈 세션을 가진 가짜 요청을 바인딩한다. */
	private static void bindEmptyRequest() {
		final HttpSession session = stub(HttpSession.class, null);
		HttpServletRequest request = stub(HttpServletRequest.class, session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@SuppressWarnings("unchecked")
	private static <T> T stub(Class<T> type, final Object sessionToReturn) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) {
				if (method.getName().equals("getSession")) {
					return sessionToReturn;
				}
				return null;
			}
		});
	}
}
