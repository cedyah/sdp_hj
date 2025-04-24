package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonArray;
import com.jebi.sdp.common.*;
import com.jebi.sdp.dao.*;
import com.jebi.sdp.model.*;
import com.sun.mail.imap.protocol.Item;

@Controller
public class Sdph0050Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdph0050Controller.class);

	@Autowired
	private CmmnDao dao;
 
	@RequestMapping(value = "sdph005001l.do")		//샘플진도조회
	public String detailSampleRequestItem(@ModelAttribute("SampleRequestItemStatVO")SampleRequestItemStatVO sampleRequestItemStatVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//header 정보
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", 		sampleRequestItemStatVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(sampleRequestItemStatVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(sampleRequestItemStatVO.getSearchDate_to()));
		map.put("ARG_PUMMYEONG",  sampleRequestItemStatVO.getPummyeong());
		map.put("OUT_PARAM", null);

		

		//출하정보 목록 프로시저
		dao.update("sdph0050.procedure_selectSampleRequestItemStat", map);
		
		model.addAttribute("sampleRequestItemStat", map.get("OUT_PARAM"));
		
		return "sdph0050/sdph005001l";
		 
	}
	 
}

