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
public class Sdpf0060Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpf0060Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpf006001l.do") // 문자수신조회
	public String selectList(@ModelAttribute("SmsreceiveVO")SmsreceiveVO SmsreceiveVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arg_cust_cd", SmsreceiveVO.getCust_num());
		map.put("arg_frdt", getExpDateString(SmsreceiveVO.getSearchDate_from()));
		map.put("arg_todt", getExpDateString(SmsreceiveVO.getSearchDate_to()));
//		map.put("arg_bill_type", SmsreceiveVO.getArg_bill_type());
//		map.put("arg_cust_cd", "419840");
//		map.put("arg_frdt", "20000301");
//		map.put("arg_todt", "20170331");
//		map.put("arg_bill_type", "1");
		map.put("out_param", null);
		
		//문자수신조회 프로시저 호출
		dao.update("sdpf0060.procedure_selectSmsreceive",map);
		
		model.addAttribute("SmsreceiveList", map.get("out_param"));
		
		return "sdpf0060/sdpf006001l";
	}
}
