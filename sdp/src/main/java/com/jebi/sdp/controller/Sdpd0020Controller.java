package com.jebi.sdp.controller;

import java.sql.ResultSet;
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
public class Sdpd0020Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpd0020Controller.class);

	@Autowired
	private CmmnDao dao;
	
	//지급어음 명세 조회
	@RequestMapping(value = "sdpd002001l.do")
	public String selectList(@ModelAttribute("notePayVO")NotePayableVO notePayVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_cust_cd", notePayVO.getCust_num());
		map.put("arg_frdt", getExpDateString(notePayVO.getSearchDate_from()));
		map.put("arg_todt", getExpDateString(notePayVO.getSearchDate_to()));
		map.put("arg_bill_type", notePayVO.getArg_bill_type());
//		map.put("arg_cust_cd", "419840");
//		map.put("arg_frdt", "20000301");
//		map.put("arg_todt", "20170331");
//		map.put("arg_bill_type", "1");
		map.put("out_param", null);
		
		//지급어음명세서 조회 프로시저 호출
		dao.update("sdpd0020.procedure_selectNotePayable",map);
		
		model.addAttribute("notePayList", map.get("out_param"));
		
		return "sdpd0020/sdpd002001l";
	}
}
