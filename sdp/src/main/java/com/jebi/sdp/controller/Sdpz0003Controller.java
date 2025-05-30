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
public class Sdpz0003Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpz0003Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpz000301p.do")		//배송지 조회 팝업
	public String sdpz000301p(@ModelAttribute(value="shipmentVO")ShipmentVO shipmentVO,
			@RequestParam(value="searchDiv", required=false)String searchDiv,
			@RequestParam(value="searchText", required=false)String searchText,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		if(searchDiv != null && searchText != null) {
			shipmentVO.setSearchDiv(searchDiv);
			shipmentVO.setSearchText("%" + searchText.replaceAll(" ", "%") + "%");
			model.addAttribute("searchDiv", searchDiv);
			model.addAttribute("searchText", searchText);
			
		}
		List<ShipmentVO> shipmentList = (List<ShipmentVO>) dao.selectList("sdpz0003.select_shipment", shipmentVO);		//주소 목록
		
		
		model.addAttribute("shipmentList", shipmentList);
		return "/sdpz0003/sdpz000301p";
	}
	
	@RequestMapping(value = "sdpz000301pu.do")		//배송지 추가&수정 화면 호출
	public String sdpz000301pu(@ModelAttribute(value="shipmentVO")ShipmentVO shipmentVO,
			@RequestParam(value="flag", required=false)String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		String url = "";
		model.addAttribute("flag", flag);		//flag 값을 그대로 다음화면으로 넘김
		
		ShipmentVO searchVO = new ShipmentVO();
		searchVO.setShip_num(shipmentVO.getShip_num());
		
		if(null != flag && !"".equals(flag)) {
			if("update".equals(flag)) {
				ShipmentVO rs_shipmentVO = (ShipmentVO) dao.select("sdpz0003.select_shipment", searchVO);		//주소 목록
				model.addAttribute("rs_shipmentVO", rs_shipmentVO);
			} 
			url = "/sdpz0003/sdpz000301pu";

		} else {
			url = "redirect:/sdpz000301p.do";
		}
		
		return url;
	}
	
	@RequestMapping(value = "sdpz000301pu_insert.do")		//배송지 추가 db INSERT
	public String sdpz000301pu_insert(@ModelAttribute(value="shipmentVO")ShipmentVO shipmentVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		dao.insert("sdpz0003.insert_shipment", shipmentVO);
		
		return "redirect:/sdpz000301p.do";
	}
	
	@RequestMapping(value = "sdpz000301pu_update.do")		//배송지 추가 db update
	public String sdpz000301pu_update(@ModelAttribute(value="shipmentVO")ShipmentVO shipmentVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		dao.update("sdpz0003.update_shipment", shipmentVO);
		
		return "redirect:/sdpz000301p.do";
	}
	
	@RequestMapping(value = "sdpz000301p_delete.do")		//배송지 삭제 db
	public String sdpz000301p_delete(@ModelAttribute(value="shipmentVO")ShipmentVO shipmentVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		dao.update("sdpz0003.delete_shipment", shipmentVO);
		
		return "redirect:/sdpz000301p.do";
	}
}
