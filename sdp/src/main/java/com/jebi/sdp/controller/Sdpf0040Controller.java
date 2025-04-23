package com.jebi.sdp.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpf0040Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpf0040Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpf004001u.do")		//검색조건 관리 화면
	public String sdpf004001u(@ModelAttribute("customerVO") CustomerVO customerVO
		, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		//제품군 코드 목록 불러오기
		CodeVO codeVO = new CodeVO();
		List<CodeVO> itemGroupCodeList = (List<CodeVO>) dao.selectList("common.select_itemType", codeVO);
		model.addAttribute("itemGroupCodeList", itemGroupCodeList);
		
		//사용자 정보 불러오기
		customerVO.setSet_item_group(((CustomerVO) dao.select("sdpf0040.select_user", customerVO)).getSet_item_group()); ;
		model.addAttribute("customerVO", customerVO);
		
		return "sdpf0040/sdpf004001u";
	}
	
	@RequestMapping(value = "ajaxSetItemGroup.do")		//검색조건 저장
	public ModelAndView ajaxSetItemGroup(@ModelAttribute("customerVO") CustomerVO customerVO
			, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();

		dao.update("sdpf0040.update_itemGroup", customerVO);
		HttpSession session = request.getSession();
		session.setAttribute("set_item_group" , customerVO.getSet_item_group().trim());

		mv.setView(jsonView);
		return mv;
	}
	
	@RequestMapping(value = "sdpf004002u.do")		//알림 관리 화면
	public String sdpf004002u(@ModelAttribute("customerVO") CustomerVO customerVO, 
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//사용자 정보 불러오기
		customerVO.setSet_alarm(((CustomerVO) dao.select("sdpf0040.select_user", customerVO)).getSet_alarm()); ;
		model.addAttribute("customerVO", customerVO);
				
		return "sdpf0040/sdpf004002u";
	}
	
	@RequestMapping(value = "ajaxSetAlarm.do")		//알림관리 저장
	public ModelAndView ajaxSetAlarm(@ModelAttribute("customerVO") CustomerVO customerVO
			, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		dao.update("sdpf0040.update_alarm", customerVO);

		mv.setView(jsonView);
		return mv;
	}
	
	@RequestMapping(value = "sdpf004003u.do")		//사용자 정보 관리 화면
	public String sdpf004003u(@ModelAttribute("customerVO") CustomerVO customerVO
			, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		return "sdpf0040/sdpf004003u";
	}
	
	@RequestMapping(value = "ajaxUpdatePassword.do")		//비밀번호 변경
	public ModelAndView ajaxUpdatePassword(@ModelAttribute("customerVO") CustomerVO customerVO,
			@RequestParam(value="newPassword", required=false)String newPassword,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		CustomerVO cvo = (CustomerVO) dao.select("sdpf0040.select_user", customerVO);
		
		if(cvo != null) {
			customerVO.setPassword(newPassword);
			dao.update("sdpf0040.update_password", customerVO);
			
			jsonView.addStaticAttribute("result", "1");

		} else {
			jsonView.addStaticAttribute("result", "2");
		}
		
		mv.setView(jsonView);
		return mv;
	}
}
