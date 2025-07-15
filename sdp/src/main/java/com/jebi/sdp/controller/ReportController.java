package com.jebi.sdp.controller;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.directory.SearchControls;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.catalina.Session;
import org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReportJmxMBean;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.ibatis.common.util.PaginatedList;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.execution.BatchException;
import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.daoImpl.CmmnDaoImpl;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;
import com.jebi.sdp.serviceImpl.CmmnServiceImpl;
import com.sun.javafx.collections.SetAdapterChange;

@Controller
public class ReportController extends CommonUtil {
	@Autowired
	private CmmnDao dao;
	//private String url = "http://biz.jevisco.com";		//오즈서버 호출 URL
	
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	//매입조회 인쇄
	@RequestMapping(value = "sdpd003001l_report.do")
	public String sdpd003001l_report(@ModelAttribute("purchaseVO")PurchaseVO purchaseVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
	//	map.put("url", url);
		map.put("ARG_CUST_NUM", purchaseVO.getCust_num());
		map.put("ARG_CUST_NM", purchaseVO.getCust_nm());
		map.put("ARG_BIZ_AREA_CD", purchaseVO.getWorkplace());
		map.put("ARG_CUST_CD", purchaseVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(purchaseVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(purchaseVO.getSearchDate_to()));
		
		if("1".equals(purchaseVO.getSearchDiv())) {
			map.put("ARG_ITEM_CD", "");
			map.put("ARG_ITEM_NM", purchaseVO.getSearchText());
			
		} else if("2".equals(purchaseVO.getSearchDiv())) {
			map.put("ARG_ITEM_CD", purchaseVO.getSearchText());
			map.put("ARG_ITEM_NM", "");
			
		} else {
			map.put("ARG_ITEM_CD", "");
			map.put("ARG_ITEM_NM", "");
		}
		model.addAttribute("map", map);
		return "report/report_sdpd003001l";
	}
	
	//주문상세정보 인쇄
	@RequestMapping(value = "sdpa002001d_report.do")
	public String sdpa002001d_report(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		//	map.put("url", url);
		map.put("ARG_CUST_NM",coVO.getCust_nm());
		
		map.put("ARG_CUST_CD", coVO.getCust_num());
		map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
		map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
		model.addAttribute("map", map);
		
		return "report/report_sdpa002001d";
	}
	
	//시험성적서 상세 페이지 - 품목별 인쇄
	@RequestMapping(value = "sdpe001001d_report.do")
	public String sdpe001001d_report(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		//	map.put("url", url);
		map.put("ARG_CUST_NM",testReportVO.getCust_nm());
		
		map.put("ARG_BIZ_AREA_CD", "");		//현재는 사업장 코드를 보내주지 않아도 결과 나옴
		map.put("ARG_ITEM_CD", testReportVO.getItem_cd());
		map.put("ARG_LOT_NO", testReportVO.getLot_no());
		model.addAttribute("map", map);
		
		if("kor".equals(testReportVO.getLang())) {
			return "report/report_sdpe001001d_kor";
		} else {
			return "report/report_sdpe001001d_eng";
		}
	}
	
	//MSDS 상세 페이지 - 품목별 인쇄
	@RequestMapping(value = "sdpe002001d_report.do")
	public String sdpe002001d_report(@ModelAttribute("msdsSVO")MsdsSubVO msdsSVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("url", url);
		map.put("ARG_BIZ_AREA_CD", msdsSVO.getSaeobjang());
		map.put("ARG_ITEM_CD", msdsSVO.getItem_cd());
		model.addAttribute("map", map);
		
		return "report/report_sdpe002001d";
	}
}
