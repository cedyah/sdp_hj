package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Session;
import org.bouncycastle.util.test.SimpleTestResult;
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
public class Sdpb0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpb0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpb001001l.do")	//화학물질 판매대장
	public String sdpb001001l(@ModelAttribute("sLedgerVO")ChemicalSalesLedgerVO sLedgerVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", sLedgerVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(sLedgerVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(sLedgerVO.getSearchDate_to()));
		map.put("ARG_GWANLI_GIJUN", "01");		//고정값
		map.put("OUT_PARAM", null);
		dao.select("sdpb0010.procedure_selectSalesLedger", map);
		
		List<ChemicalSalesLedgerVO> list = (List<ChemicalSalesLedgerVO>) map.get("OUT_PARAM");
		
		model.addAttribute("sLedgerList", list);
		
		return "sdpb0010/sdpb001001l";
	}
	
	@RequestMapping(value = "sdpb001002l.do")	//화학물질 관리대장
	public String sdpb001002l(@ModelAttribute("mLedgerVO")ChemicalManagementLedgerVO mLedgerVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		if(mLedgerVO.getSearchYear().equals("")) {
			mLedgerVO.setSearchYear(getYYYY());
		}
		
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", mLedgerVO.getCust_num());
		map.put("ARG_YYYY", mLedgerVO.getSearchYear());
		map.put("ARG_GWANLI_GIJUN", "01");		//고정값
		map.put("OUT_PARAM", null);
		dao.select("sdpb0010.procedure_selectManageLedger", map);
		
		model.addAttribute("mLedgerList", map.get("OUT_PARAM"));
		
		return "sdpb0010/sdpb001002l";
	}
	
	@RequestMapping(value = "sdpb001003l.do")	//연간위험물 유통량현황 PKG_ORDER_RC.Q_SZB550_S
	public String sdpb001003l(@ModelAttribute("conVO")Sdpb001003VO conVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", conVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(conVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(conVO.getSearchDate_to()));
		map.put("OUT_PARAM", null);
		dao.select("sdpb0010.procedure_sdpb001003l", map);
		
		model.addAttribute("list", map.get("OUT_PARAM"));
		
		return "sdpb0010/sdpb001003l";
	}
	
	@RequestMapping(value = "sdpb001004l.do")	//연간위험물 판매제품 현황 PKG_ORDER_RC.Q_SZB560_S
	public String sdpb001004l(@ModelAttribute("conVO")Sdpb001004VO conVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", conVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(conVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(conVO.getSearchDate_to()));
		map.put("OUT_PARAM", null);
		dao.select("sdpb0010.procedure_sdpb001004l", map);
		
		model.addAttribute("list", map.get("OUT_PARAM"));
		
		return "sdpb0010/sdpb001004l";
	}
}
