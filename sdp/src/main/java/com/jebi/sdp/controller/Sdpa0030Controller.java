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
public class Sdpa0030Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0030Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpa003001l.do")
	public String selectList(@ModelAttribute("shipmentInfoVO")ShipmentInformationVO shipmentInfoVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", shipmentInfoVO.getWorkplace());
		map.put("ARG_CUST_CD", shipmentInfoVO.getCust_num().trim());
		map.put("ARG_FRDT", getExpDateString(shipmentInfoVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(shipmentInfoVO.getSearchDate_to()));
		map.put("OUT_PARAM", null);

		//출하정보 목록 프로시저
		dao.update("sdpa0030.procedure_selectShipmentInfo", map);
		
		model.addAttribute("shipmentInfoList", map.get("OUT_PARAM"));
		
		return "sdpa0030/sdpa003001l";
	}
}
