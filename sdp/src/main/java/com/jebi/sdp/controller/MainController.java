package com.jebi.sdp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class MainController extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "main.do")
	public String home(HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//검색조건용 VO 생성
		NoticeVO conVO = new NoticeVO();
		FileVO fileConVO = new FileVO();
		
		//신제품
		conVO = new NoticeVO();
		conVO.setPage_current("");
		conVO.setNotice_type("P");
		conVO.setTop("1");
		NoticeVO newProduct = (NoticeVO) dao.select("sdpy0010.select_noticeTop", conVO);
		model.addAttribute("newProduct", newProduct);
		
		//대리점 소식에 사용될 첨부파일 이미지
		if(newProduct != null) {
			fileConVO = new FileVO();
			fileConVO.setDoc_num(newProduct.getNotice_num());
			FileVO newProduct_file = (FileVO) dao.select("common.select_file", fileConVO);
			model.addAttribute("newProduct_file", newProduct_file);
		}
		
		//공지사항
		conVO = new NoticeVO();
		conVO.setPage_current("");
		conVO.setNotice_type("A,G");
		conVO.setTop("4");
		List<NoticeVO> noticeList = (List<NoticeVO>) dao.selectList("sdpy0010.select_noticeTop", conVO);
		model.addAttribute("noticeList", noticeList);
		
		//대리점 소식
		conVO = new NoticeVO();
		conVO.setPage_current("");
		conVO.setNotice_type("L");
		conVO.setTop("1");
		NoticeVO franchiseNews = (NoticeVO) dao.select("sdpy0010.select_noticeTop", conVO);
		model.addAttribute("franchiseNews", franchiseNews);
		
		//대리점 소식에 사용될 첨부파일 이미지
		if(franchiseNews != null) {
			fileConVO = new FileVO();
			fileConVO.setDoc_num(franchiseNews.getNotice_num());
			FileVO franchiseNews_file = (FileVO) dao.select("common.select_file", fileConVO);
			model.addAttribute("franchiseNews_file", franchiseNews_file);
		}
		
		//최근 출하정보 가져오기
		ShipmentInformationVO shipmentInfoVO = new ShipmentInformationVO();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", shipmentInfoVO.getWorkplace());
		map.put("ARG_CUST_CD", shipmentInfoVO.getCust_num().trim());
		map.put("OUT_PARAM", null);

		//출하정보 목록 프로시저
		dao.update("sdpa0030.procedure_selectShipmentInfoMain", map);
		List<ShipmentInformationVO> list = (List<ShipmentInformationVO>) map.get("OUT_PARAM");
		
		model.addAttribute("shipmentInfoList", map.get("OUT_PARAM"));
		
		//포인트 관리 화면 상단 종합 포인트 출력
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", customerVO);
		model.addAttribute("customerVO", customerVO);
		
		return "main";
	}
}
