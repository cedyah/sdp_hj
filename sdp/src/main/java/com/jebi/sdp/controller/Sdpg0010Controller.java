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
public class Sdpg0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpg0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	//경화제/신나대비표 조회
	@RequestMapping(value = "sdpg001001l.do")
	public String selectList(@ModelAttribute("contrastHVO")ContrastHeaderVO contrastHVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();

		if(contrastHVO.getSearchDiv().equals("item_code")) {
			map.put("ARG_ITEM_CD", contrastHVO.getSearchText().trim());
			map.put("ARG_ITEM_NM", "");
			
		} else if(contrastHVO.getSearchDiv().equals("description")) {
			map.put("ARG_ITEM_CD", "");
			map.put("ARG_ITEM_NM", contrastHVO.getSearchText().trim());
			
		} else {
			map.put("ARG_ITEM_CD", "");
			map.put("ARG_ITEM_NM", "");
		}
		
		map.put("out_param", null);

		//경화제/신나대비표 조회  프로시저 호출
		dao.update("sdpg0010.procedure_selectContrastHeader", map);
		
//		model.addAttribute("contrastHVO", contrastHVO);
		model.addAttribute("contrastHeaderList", map.get("out_param"));
		return "sdpg0010/sdpg001001l";
	}
	
	//경화제/신나대비표 상세
	@RequestMapping(value = "ajaxGetcontrastSub.do")
	public ModelAndView ajaxGetcontrastSub(@ModelAttribute("contrastSVO")ContrastSubVO contrastSVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_pummog", contrastSVO.getarg_pummog());
		map.put("out_param", null);
		
		//경화제/신나대비표 조회 프로시저 호출
		dao.update("sdpg0010.procedure_selectContrastSub", map);
		
//		model.addAttribute("contrastHVO", contrastHVO);
		jsonView.addStaticAttribute("contrastSubList", map.get("out_param"));

		mv.setView(jsonView);
		return mv;
	}
}
