package com.jebi.sdp.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
//import org.apache.tomcat.websocket.CaseInsensitiveKeyMap;
import org.codehaus.jackson.map.annotate.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpz0009Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpz0009Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@RequestMapping(value = "sdpz000901u.do")		//로그인 페이지 화면 호출
	public String selectList(@ModelAttribute("SearchItemVO")SearchItemVO searchVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		return "/sdpz0009/sdpz000901u";
	}
	
	@RequestMapping(value = "ajaxLogin.do")		//로그인
	public ModelAndView sdpz000901u_login(@ModelAttribute("customerVO")CustomerVO customerVO
			,HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("iCustNum", customerVO.getCust_num());
		map.put("iPassWd", customerVO.getPassword());
		map.put("outParam", null);
		
		dao.select("sdpz0009.procedure_login",map);
		
		CustomerVO resultVO = null;
		
		if(((List)map.get("outParam")).size() > 0) {
			resultVO = ((List<CustomerVO>) map.get("outParam")).get(0);
			
		}
		
		if(resultVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user" , resultVO);
			
			//로그인 정보 DB에 저장
			LogVO logVO = new LogVO();
			logVO.setCust_num(resultVO.getCust_num());
			logVO.setCust_nm(resultVO.getCust_nm());
			logVO.setAuth(resultVO.getAuth());
			logVO.setType("login");
			logVO.setContents("사용자 로그인");
			dao.insert("common.insert_log", logVO);
			
			jsonView.addStaticAttribute("login_result", "1");		//로그인 성공

		} else {
			jsonView.addStaticAttribute("login_result", "2");		//로그인 실패
			
		}
		
		mv.setView(jsonView);
		return mv;
	}
	
	
//	@RequestMapping(value = "ajaxLogin.do")		//로그인
//	public ModelAndView sdpz000901u_login(@ModelAttribute("customerVO")CustomerVO customerVO
//			,HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
//		
//		ModelAndView mv = new ModelAndView();
//		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
//		
//		String loginResult = "2";		//0:아이디 없음(프로시저로 대체하며 없어짐), 1:로그인성공, 2:비밀번호실패
//		customerVO.setCust_seq("0");
//
//		CustomerVO resultVO = (CustomerVO) dao.select("sdpz0009.select_loginPocess", customerVO);
//		
//		if(resultVO != null) {
//			HttpSession session = request.getSession();
//			session.setAttribute("cust_num" , resultVO.getCust_num().trim());
//			session.setAttribute("cust_seq" , resultVO.getCust_seq().trim());
//			session.setAttribute("site_ref" , resultVO.getSite_ref().trim());
//			session.setAttribute("set_item_group" , resultVO.getSet_item_group().trim());
//			session.setAttribute("set_alarm" , resultVO.getSet_alarm().trim());
//			
//			List li_qty_site = new ArrayList();			//타겟이 될 재고 사업장을 저장
//			li_qty_site.add("KENDEV");					//개발중에는 뭔가 보여주기 위해 필수로 개발 사업장을 포함
//			
//			session.setAttribute("li_qty_site", li_qty_site);
//			
//			loginResult = "1";
//			
//		} else {
//			loginResult = "2";
//		}
// 		
//		jsonView.addStaticAttribute("login_result", loginResult);
//  		mv.setView(jsonView);
//		return mv;
//	}
	
//	//2017-03-09 프로시저로 로그인 프로세스를 대체하기 위해 벅업
//	@RequestMapping(value = "ajaxLogin.do")		//로그인
//	public ModelAndView sdpz000901u_login(@ModelAttribute("customerVO")CustomerVO customerVO
//			,HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
//		
//		ModelAndView mv = new ModelAndView();
//		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
//		
//		String loginResult = "0";		//0:아이디 없음, 1:로그인성공, 2:비밀번호실패
//		
//		CustomerVO conVO = new CustomerVO();
//		CustomerVO customer_mst = new CustomerVO();
//		CustomerVO customer_sdp = new CustomerVO();
//		
//		String url = "";
//		conVO.setCust_num(customerVO.getCust_num());
//		conVO.setCust_seq("0");
//		customer_mst = (CustomerVO) dao.select("sdpz0009.select_customerERP", conVO);
//		
//		if(customer_mst != null) {
//			customer_sdp = (CustomerVO) dao.select("sdpz0009.select_customerSDP",  conVO);
//			
//			if(customer_sdp == null) {
//				conVO.setCust_num(customer_mst.getCust_num());
//				conVO.setCust_seq(customer_mst.getCust_seq());
//				conVO.setSite_ref(customer_mst.getSite_ref());
//				conVO.setPassword("1");
//				
//				dao.insert("sdpz0009.insert_customer", conVO);
//			}
//			conVO = new CustomerVO();
//			conVO.setCust_num(customerVO.getCust_num());
//			conVO.setPassword(customerVO.getPassword());
//			
//			customer_sdp = (CustomerVO) dao.select("sdpz0009.select_customerSDP",  conVO);
//			
//			if(customer_sdp != null) {
//				HttpSession session = request.getSession();
//				session.setAttribute("cust_num" , customer_sdp.getCust_num().trim());
//				session.setAttribute("cust_seq" , customer_sdp.getCust_seq().trim());
//				session.setAttribute("site_ref" , customer_sdp.getSite_ref().trim());
//				session.setAttribute("set_item_group" , customer_sdp.getSet_item_group().trim());
//				session.setAttribute("set_alarm" , customer_sdp.getSet_alarm().trim());
//				
//				List li_qty_site = new ArrayList();		//타겟이 될 재고 사업장을 저장
//				li_qty_site.add("KENDEV");					//개발중에는 뭔가 보여주기 위해 필수로 개발 사업장을 포함
//				
//				//사업장 별로 재고를 불러올 Site_ref를 로그인 할때 session에 저장
//				if("12".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("12");
//					
//				} else if("13".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("13");
//					
//				} else if("14".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("14");
//					li_qty_site.add("13");
//					
//				} else if("15".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("15");
//					li_qty_site.add("12");
//					
//				} else if("16".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("16");
//					li_qty_site.add("12");
//					
//				} else if("17".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("13");
//					
//				} else if("18".equals(customer_sdp.getSite_ref().trim())) {
//					li_qty_site.add("18");
//					li_qty_site.add("13");
//					
//				} else {
//					
//				}
//				
//				session.setAttribute("li_qty_site", li_qty_site);
//				
//				loginResult = "1";
//			} else {
//				loginResult = "2";
//			}
//		}
//		
//		jsonView.addStaticAttribute("login_result", loginResult);
//		mv.setView(jsonView);
//		return mv;
//	}
	
	@RequestMapping(value = "sdpz000901u_logout.do")		//로그아웃
	public String sdpz000901u_logout(HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/sdpz000901u.do";
	}
	
	
	@RequestMapping(value = "ajaxFindPassword.do")		//비밀번호 찾기
	public ModelAndView ajaxFindPassword(@ModelAttribute(value="customerVO")CustomerVO customerVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
 		
		CustomerVO resultVO = (CustomerVO) dao.select("sdpz0009.select_userInfo", customerVO);

		//입력된 아이디, 전화번호와 DB에서 읽어온 아이디, 전화번호가 일치하면 새로운 비밀번호를 생성하여 발송
		if(resultVO != null && resultVO.getHp_no().equals(customerVO.getHp_no())) {
			String password = "";
			int tempNum = 0;
			String addText = "";
			for(int i=0; i < 8; i++) {
				tempNum = (new Random()).nextInt(35);
				switch(tempNum) {
					case 10 : addText = "a"; break;
					case 11 : addText = "b"; break;
					case 12 : addText = "c"; break;
					case 13 : addText = "d"; break;
					case 14 : addText = "e"; break;
					case 15 : addText = "f"; break;
					case 16 : addText = "g"; break;
					case 17 : addText = "h"; break;
					case 18 : addText = "i"; break;
					case 19 : addText = "j"; break;
					case 20 : addText = "k"; break;
					case 21 : addText = "l"; break;
					case 22 : addText = "m"; break;
					case 23 : addText = "n"; break;
					case 24 : addText = "o"; break;
					case 25 : addText = "p"; break;
					case 26 : addText = "q"; break;
					case 27 : addText = "r"; break;
					case 28 : addText = "s"; break;
					case 29 : addText = "t"; break;
					case 30 : addText = "u"; break;
					case 31 : addText = "v"; break;
					case 32 : addText = "w"; break;
					case 33 : addText = "x"; break;
					case 34 : addText = "y"; break;
					case 35 : addText = "z"; break;
					default : addText = Integer.toString(tempNum); break;
				}
				password += addText;
			}
			customerVO.setPassword(password);
			//생성된 비밀번호로 변경
			dao.update("sdpf0040.update_password", customerVO);
			
			//변경된 비밀번호 SMS 발송
			SMSVO smsVO = new SMSVO();
			smsVO.setTr_id(customerVO.getCust_num());
			smsVO.setTr_sendstat("0");		//0:발송준비
			smsVO.setTr_rsltstat("00");
			smsVO.setTr_msgtype("0");
			smsVO.setTr_phone(resultVO.getHp_no());	//실제 소스
//			smsVO.setTr_phone("01049051659");		//테스트용
			smsVO.setTr_callback("0234732021");
			smsVO.setTr_msg("[한진화학 인터넷주문 시스템]\n비밀번호:" + customerVO.getPassword());
			dao.insert("sdpz0009.insert_scTran", smsVO);
			
			jsonView.addStaticAttribute("findpw_result", "success");
			
		} else {
			jsonView.addStaticAttribute("findpw_result", "fail");
		}
  		mv.setView(jsonView);
		return mv;
	}
}
