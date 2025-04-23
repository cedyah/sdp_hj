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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpa0060Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0060Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpa006001l.do")		//생산진도 조회
	public String selectList(@ModelAttribute("productionVO")ProductionProgressVO productionVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_cust_cd", productionVO.getCust_num());
		map.put("arg_frdt", getExpDateString(productionVO.getSearchDate_from()));
		map.put("arg_todt", getExpDateString(productionVO.getSearchDate_to()));
	    //map.put("arg_item_cd", productionVO.getSearchText());
		map.put("arg_item_nm", productionVO.getSearchText());
		//<!--	map.put("arg_jindo_gubun", "T"); 
		map.put("out_param", null);
		
		dao.update("sdpa0060.procedure_selectProductionProgress",map);
		
		model.addAttribute("productionList", map.get("out_param"));
		return "sdpa0060/sdpa006001l";
	}
}
