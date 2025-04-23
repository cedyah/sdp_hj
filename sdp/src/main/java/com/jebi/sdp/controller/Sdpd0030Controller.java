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
public class Sdpd0030Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpd0030Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpd003001l.do")
	public String selectList(@ModelAttribute("purchaseVO")PurchaseVO purchaseVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
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
		
		map.put("OUT_PARAM", null);

		//지급어음명세서 조회 프로시저 호출
		dao.update("sdpd0030.procedure_selectPurchaseInfo", map);
		
		model.addAttribute("purchaseInfoList", map.get("out_param"));
		return "sdpd0030/sdpd003001l";
	}
}
