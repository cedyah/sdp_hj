package com.jebi.sdp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jebi.sdp.model.*;

@Service
public class SessionInterceptor extends HandlerInterceptorAdapter{
//	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
		boolean result = false;
		String rootPath = request.getContextPath();
		
		try{
			HttpSession session = request.getSession(false);
			
			if(excludeURLCheck(request)){
				return true;
			}
			
			if(session == null){
				response.sendRedirect("sdpz000901u.do");
				return false;
			}else{
				if(session.getAttribute("user") != null) {
					String cust_num = ((CustomerVO)session.getAttribute("user")).getCust_num();

					if(cust_num == null || "".equals(cust_num)){
						response.sendRedirect(rootPath);
						return false;
					}
				} else {
					response.sendRedirect("sdpz000901u.do");
					return false;
				}
			}
			result = true;
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		
		return result;
	}
	
	// 제외할 페이지 확인.
	private boolean excludeURLCheck(HttpServletRequest request){
		boolean result = false;
		String requestURI = request.getRequestURI();
		
		if(requestURI.equals("/sdp/")
				|| requestURI.equals("/sdp/sdpz000901u.do") 
				|| requestURI.equals("/sdp/ajaxLogin.do")
				|| requestURI.equals("/sdp/ajaxFindPassword.do")
				){
			result = true;
		}
		
		return result;
	}
}
