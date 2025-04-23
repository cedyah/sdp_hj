package com.jebi.sdp.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpd0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpd0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	//외상정보 조회
	@RequestMapping(value = "sdpd001001l.do")
	public String selectList(@ModelAttribute("creditHVO")CreditHeaderVO creditHVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		if(creditHVO.getSearchYear() == "") {
			creditHVO.setSearchYear(getYYYY());
		}
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_cust_cd", creditHVO.getCust_num());
		map.put("arg_yyyy", creditHVO.getSearchYear());
		map.put("out_param", null);

		//지급어음명세서 조회 프로시저 호출
		dao.update("sdpd0010.procedure_selectCreditHeader", map);
		
//		model.addAttribute("creditHVO", creditHVO);
		model.addAttribute("creditHeaderList", map.get("out_param"));
		return "sdpd0010/sdpd001001l";
	}
	
	//외상정보 상세
	@RequestMapping(value = "ajaxGetCreditSub.do")
	public ModelAndView ajaxGetCreditSub(@ModelAttribute("creditSVO")CreditSubVO creditSVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_cust_cd", creditSVO.getCust_num());
		map.put("arg_yyyymm", creditSVO.getArg_yyyymm());
		map.put("arg_type", creditSVO.getArg_type());
		map.put("out_param", null);
		
		//지급어음명세서 조회 프로시저 호출
		dao.update("sdpd0010.procedure_selectCreditSub", map);
		
//		model.addAttribute("creditHVO", creditHVO);
		jsonView.addStaticAttribute("creditSubList", map.get("out_param"));

		mv.setView(jsonView);
		return mv;
	}
}
