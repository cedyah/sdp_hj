package com.jebi.sdp.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 세션 소유 필드 바인딩 차단.
 *
 * CustomerVO(및 이를 상속한 모든 VO)는 생성자에서 세션의 로그인 사용자 정보로
 * cust_num, workplace 등을 채운다. 그러나 @ModelAttribute 바인딩이 그 뒤에 실행되므로
 * 요청 파라미터(?cust_num=...)가 세션 값을 덮어써 다른 거래처의 데이터에 접근할 수 있었다.
 *
 * 로그인 상태의 요청에서는 아래 필드를 바인딩 대상에서 제외해, 항상 세션 값이 유지되도록 한다.
 * (multipart 요청을 포함한 모든 @ModelAttribute 바인딩에 적용)
 */
@Component
public class SessionFieldBindingGuard implements BeanPostProcessor {

	/** 세션에서만 값을 가져와야 하는 필드 */
	private static final String[] SESSION_OWNED_FIELDS = {
		"cust_num", "cust_nm", "workplace", "auth", "cust_type"
	};

	/** 로그인 전 요청 — 사용자가 직접 cust_num 을 입력하는 화면 */
	private static final Set<String> BINDING_ALLOWED_URIS = new HashSet<String>(Arrays.asList(
		"/sdpz000901u.do",
		"/ajaxLogin.do",
		"/ajaxFindPassword.do"
	));

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof RequestMappingHandlerAdapter) {
			RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
			adapter.setWebBindingInitializer(new GuardedInitializer(adapter.getWebBindingInitializer()));
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	private static final class GuardedInitializer implements WebBindingInitializer {
		private final WebBindingInitializer delegate;

		GuardedInitializer(WebBindingInitializer delegate) {
			this.delegate = delegate;
		}

		@Override
		public void initBinder(WebDataBinder binder, WebRequest request) {
			if (delegate != null) {
				delegate.initBinder(binder, request);
			}
			if (isLoggedInRequest(request)) {
				binder.setDisallowedFields(SESSION_OWNED_FIELDS);
			}
		}

		private boolean isLoggedInRequest(WebRequest request) {
			if (!(request instanceof NativeWebRequest)) {
				return true;	// 판단 불가 시 안전한 쪽(차단)으로
			}
			HttpServletRequest httpRequest = ((NativeWebRequest) request).getNativeRequest(HttpServletRequest.class);
			if (httpRequest == null) {
				return true;
			}

			String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
			if (BINDING_ALLOWED_URIS.contains(path)) {
				return false;
			}

			HttpSession session = httpRequest.getSession(false);
			return session != null && session.getAttribute("user") != null;
		}
	}
}
