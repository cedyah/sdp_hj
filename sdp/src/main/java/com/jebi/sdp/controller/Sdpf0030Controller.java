package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpf0030Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpf0030Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpf003002l.do")		//주문포인트 관리 조회
	public String sdpf003002l(@ModelAttribute("pointVO") PointVO pointVO
		, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("menu_num", "1");		//선택메뉴 번호값 저장
		
		//포인트 관리 화면 상단 종합 포인트 출력
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", customerVO);
		model.addAttribute("customerVO", customerVO);
		
		if(pointVO.getSearchYear() == null || pointVO.getSearchYear().equals("")) {
			Date dt = new Date();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy");
			pointVO.setSearchYear(smt.format(dt));
		}
		
		//포인트목록 조회 프로시저
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", pointVO.getCust_num());
		map.put("ARG_YYYY", pointVO.getSearchYear() == "" ? "2016" : pointVO.getSearchYear());
		map.put("OUT_PARAM", null);
		dao.update("sdpf0030.procedure_selectPointList", map);
		model.addAttribute("pointList", map.get("OUT_PARAM"));
		
		//상품권 신규 등록 가능 여부
		Date dt = new Date();
		SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");
		NoticeVO conVO = new NoticeVO();
		conVO.setNotice_type("G");
		conVO.setNotice_from((String) smt.format(dt));
		NoticeVO noVO = ((List<NoticeVO>)dao.selectList("sdpy0010.select_noticeTop", conVO)).get(0);
		
		int target_dt = Integer.parseInt(getExpDateString(smt.format(dt)));
		int from_dt = Integer.parseInt(getExpDateString(noVO.getNotice_from()));
		int to_dt = Integer.parseInt(getExpDateString(noVO.getNotice_to()));

		//null이 아니며 마지막 상품권 등록 게시물의 기간이 현재 일자를 포함하는 경우
		if(noVO != null &&  target_dt >= from_dt && target_dt <= to_dt) {
			map = new HashMap<String, Object>();
			
			map.put("ARG_CUST_CD", pointVO.getCust_num());
			map.put("ARG_DT", "");
			map.put("OUT_PARAM", null);
			dao.select("sdpf0030.procedure_selectGift", map);
			if(((List<GiftVO>)map.get("OUT_PARAM")) != null && ((List<GiftVO>)map.get("OUT_PARAM")).size() > 0) {
				GiftVO giftVO = ((List<GiftVO>)map.get("OUT_PARAM")).get(0);
				
				//기간내에 등록한 상품권이 있는지 검사
				target_dt = Integer.parseInt(getExpDateString(giftVO.getDt()));
				
				if(noVO != null &&  target_dt >= from_dt && target_dt <= to_dt) {
					pointVO.setGift_check("update");
				} else {
					pointVO.setGift_check("insert");
				}
			} else {
				pointVO.setGift_check("insert");
			}
		}
		
		model.addAttribute("pointVO", pointVO);
		return "sdpf0030/sdpf003002l";
	}
	
	@RequestMapping(value = "sdpf003003l.do")		//장려금 조회
	public String sdpf003003l(@ModelAttribute("subsidyVO") SubsidyVO subsidyVO
			, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("menu_num", "2");		//선택메뉴 번호값 저장
		
		//포인트 관리 화면 상단 종합 포인트 출력
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", customerVO);
		model.addAttribute("customerVO", customerVO);
		
		if(subsidyVO.getSearchYear() == null || subsidyVO.getSearchYear().equals("")) {
			Date dt = new Date();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy");
			subsidyVO.setSearchYear(smt.format(dt));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", subsidyVO.getCust_num());
		map.put("ARG_YYYY", subsidyVO.getSearchYear() == "" ? "2016" : subsidyVO.getSearchYear());
		map.put("OUT_PARAM", null);
		
		//제품목록  조회 프로시저 호출
		dao.update("sdpf0030.procedure_selectSubsidyList", map);
				
		model.addAttribute("subsidyList", map.get("OUT_PARAM"));
		model.addAttribute("subsidyVO", subsidyVO);
		
		return "sdpf0030/sdpf003003l";
	}
	
	@RequestMapping(value = "sdpf003004l.do")		//화물탁송 포인트 조회
	public String sdpf003004l(@ModelAttribute("consignPtVO") ConsignmentPointVO consignPtVO
			, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("menu_num", "3");		//선택메뉴 번호값 저장
		
		//포인트 관리 화면 상단 종합 포인트 출력
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", customerVO);
		model.addAttribute("customerVO", customerVO);
		
		if(consignPtVO.getSearchYear() == null || consignPtVO.getSearchYear().equals("")) {
			Date dt = new Date();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy");
			consignPtVO.setSearchYear(smt.format(dt));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", consignPtVO.getCust_num());
		map.put("ARG_YYYY", consignPtVO.getSearchYear() == "" ? "2016" : consignPtVO.getSearchYear());
		map.put("OUT_PARAM", null);
		
		//제품목록  조회 프로시저 호출
		dao.update("sdpf0030.procedure_selectConsignPtList", map);
				
		model.addAttribute("consignPtList", map.get("OUT_PARAM"));
		model.addAttribute("consignPtVO", consignPtVO);
		
		return "sdpf0030/sdpf003004l";
	}
	
	@RequestMapping(value = "sdpf003005u.{flag}.do")		//사은품 신청 & 수정 화면
	public String sdpf003002l(@ModelAttribute("giftVO") GiftVO giftVO, @PathVariable("flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("menu_num", "1");		//선택메뉴 번호값 저장
		model.addAttribute("flag", flag);
		
		HashMap<String, Object> map;
		
		//사은품 신청내역 확인
		
		//포인트 관리 화면 상단 종합 포인트 출력
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", customerVO);
		model.addAttribute("customerVO", customerVO);
		
		//배달구분 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "0081");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code01", map.get("OUT_PARAM"));
				
		if("update".equals(flag)) {
			map = new HashMap<String, Object>();
			
			map.put("ARG_CUST_CD", giftVO.getCust_num());
			map.put("ARG_DT", "");
			map.put("OUT_PARAM", null);
			dao.select("sdpf0030.procedure_selectGift", map);
			model.addAttribute("gift", ((List<GiftVO>)map.get("OUT_PARAM")).get(0));
		}
		
		return "sdpf0030/sdpf003005u";
	}
	
	@RequestMapping(value = "sdpf003005u_insert.do")		//사은품 등록
	public String sdpf003005u_insert(@ModelAttribute("giftVO") GiftVO giftVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		redirectAttr.addFlashAttribute("menu_num", "1");		//선택메뉴 번호값 저장
		HashMap<String, Object> map;
		map = new HashMap<String, Object>();
		
		map.put("ARG_FLAG", "insert");
		map.put("ARG_CUST_CD", giftVO.getCust_num());
		map.put("ARG_DT", getExpDateString(giftVO.getDt()));
		map.put("ARG_REPAY_TYPE", giftVO.getRepay_type());
		map.put("ARG_AMT", getExpNumString(giftVO.getAmt()));
		map.put("OUT_PARAM", "");
		
		dao.select("sdpf0030.procedure_insertGift", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		return "redirect:/sdpf003002l.do";
	}
	
	@RequestMapping(value = "sdpf003005u_update.do")		//사은품 수정
	public String sdpf003005u_update(@ModelAttribute("giftVO") GiftVO giftVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		redirectAttr.addFlashAttribute("menu_num", "1");		//선택메뉴 번호값 저장
		HashMap<String, Object> map;
		map = new HashMap<String, Object>();
		
		map.put("ARG_FLAG", "update");
		map.put("ARG_CUST_CD", giftVO.getCust_num());
		map.put("ARG_DT", getExpDateString(giftVO.getDt()));
		map.put("ARG_REPAY_TYPE", giftVO.getRepay_type());
		map.put("ARG_AMT", getExpNumString(giftVO.getAmt()));
		map.put("OUT_PARAM", "");
		
		dao.select("sdpf0030.procedure_insertGift", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		return "redirect:/sdpf003002l.do";
	}
	
	@RequestMapping(value = "sdpf003005d.do")		//사은품 신청 상세
	public String sdpf003005d(@ModelAttribute("giftVO") GiftVO giftVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("menu_num", "1");		//선택메뉴 번호값 저장
		
		//포인트 관리 화면 상단 종합 포인트 출력
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", customerVO);
		model.addAttribute("customerVO", customerVO);
		
		return "redirect:/sdpf003002l.do";
	}
}
