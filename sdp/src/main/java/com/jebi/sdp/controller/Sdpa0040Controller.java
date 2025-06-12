package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Session;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
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
public class Sdpa0040Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0040Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpa004001l.do")		//제조의뢰 목록 조회
	public String sdpa004001l(@ModelAttribute("prVO")ProdReqHeaderVO prVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		//제조의뢰 목록 불러오기
		map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
		map.put("ARG_CUST_CD", prVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(prVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(prVO.getSearchDate_to()));
		map.put("ARG_SEARCH_TYPE", prVO.getSearchDiv().equals("") ? "1" : prVO.getSearchDiv());
		map.put("OUT_PARAM", null);
		dao.select("sdpa0040.procedure_selectProdReqList", map);
		
		model.addAttribute("prodReqList", map.get("OUT_PARAM"));
		
		return "sdpa0040/sdpa004001l";
	}
	
	@RequestMapping(value = "sdpa004101d.do")		//제조의뢰 상세 조회
	public String sdpa004101d(@ModelAttribute("prVO")ProdReqHeaderVO prVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		
	    System.out.println(">>> [coVO] received: " + prVO);
	    System.out.println(">>> [cust_num]: " + prVO.getCust_num());
	    System.out.println(">>> [ord_dt]: " + prVO.getIlja());
	    System.out.println(">>> [ord_no]: " + prVO.getJeonpyo_no());
	    System.out.println(">>> [product_type()]: " + prVO.getProduct_type());

	    prVO.setProduct_type("인터넷");
	    
		//제조의뢰 헤더 불러오기
		map = new HashMap<String, Object>();
		//map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
		map.put("ARG_CUST_CD", prVO.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
		map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
		map.put("ARG_GUBUN", prVO.getProduct_type());
		map.put("OUT_PARAM", null);
		dao.select("sdpa0040.procedure_selectProdReqHeader", map);
		ProdReqHeaderVO prodReqHeader = ((List<ProdReqHeaderVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("prodReqHeader", prodReqHeader);
		
		
		//제조의뢰 서브 불러오기
		map = new HashMap<String, Object>();
//		map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
		map.put("ARG_CUST_CD", prVO.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
		map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
		map.put("ARG_GUBUN", prVO.getProduct_type());
		map.put("OUT_PARAM", null);
		dao.select("sdpa0040.procedure_selectProdReqSub", map);
		List<ProdReqSubVO> prodReqSubList = (List<ProdReqSubVO>) map.get("OUT_PARAM");
		model.addAttribute("prodReqSubList", prodReqSubList);
		
		return "sdpa0040/sdpa004101d";
	}
	
	@RequestMapping(value = "sdpa004101u.{flag}.do")		//제조의뢰 작성 & 수정 화면 호출
	public String sdpa004101u(@ModelAttribute("prVO") ProdReqHeaderVO prVO, @PathVariable("flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		model.addAttribute("flag", flag);
		
		//판매구분 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "4069");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code10", map.get("OUT_PARAM"));

		if(flag != null && flag.equals("update")) {
			//제조의뢰 헤더 불러오기
			map = new HashMap<String, Object>();
			//map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
			map.put("ARG_CUST_CD", prVO.getCust_num());
			map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
			map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
			map.put("ARG_GUBUN", prVO.getProduct_type());
			map.put("OUT_PARAM", null);
			dao.select("sdpa0040.procedure_selectProdReqHeader", map);
			ProdReqHeaderVO prodReqHeader = ((List<ProdReqHeaderVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("prodReqHeader", prodReqHeader);
			
			
			//제조의뢰 서브 불러오기
			map = new HashMap<String, Object>();
			//map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
			map.put("ARG_CUST_CD", prVO.getCust_num());
			map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
			map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
			map.put("ARG_GUBUN", prVO.getProduct_type());
			map.put("OUT_PARAM", null);
			dao.select("sdpa0040.procedure_selectProdReqSub", map);
			List<ProdReqSubVO> prodReqSubList = (List<ProdReqSubVO>) map.get("OUT_PARAM");
			model.addAttribute("prodReqSubList", prodReqSubList);
		}
				
		return "sdpa0040/sdpa004101u";
	}
	
	@RequestMapping(value = "sdpa004101u_insert.do")		//제조의뢰 작성 (db insert)
	public String sdpa004101u_insert(@ModelAttribute("prVO") ProdReqHeaderVO prVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		try {
			HashMap<String, Object> map;
			
			//작성일 현재일자 셋팅
			Date dt = new Date();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");
			prVO.setIlja(smt.format(dt));
			
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//전표번호 가져오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
			map.put("ARG_SLIP_TYPE", "W2");		//일반제조는 02
			map.put("ARG_DT", getExpDateString(prVO.getIlja()));
			map.put("OUT_PARAM", null);
			
			System.out.println(">>> sdpa0040.procedure_selectJeonpyoNoPre");
			dao.select("sdpa0040.procedure_selectJeonpyoNo", map);
			List<ProdReqHeaderVO> list = (List<ProdReqHeaderVO>) map.get("OUT_PARAM");
			prVO.setJeonpyo_no(((ProdReqHeaderVO) list.get(0)).getJeonpyo_no());
			
    	     System.out.println(">>> calling procedure_insertOrderSub");
			System.out.println((String) prVO.getJeonpyo_no());
			System.out.println(">>> sdpa0040.procedure_selectJeonpyoNoPost");

			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "insert");
			map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
			map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
			map.put("ARG_REQ_NO",prVO.getJeonpyo_no());
			map.put("ARG_CUST_CD", prVO.getCust_num());
			map.put("ARG_DELY_DT", getExpDateString(prVO.getEuiloiil()));
			map.put("ARG_DELY_TYPE", prVO.getBaedal_gubun());
			map.put("ARG_RMK", prVO.getBigo());
			map.put("ARG_RECVER", prVO.getInsuja());
			map.put("ARG_TEL_NO", prVO.getTel_no());
			map.put("ARG_ZIP", prVO.getZip());
			map.put("ARG_ADDR1", prVO.getAddr1());
			map.put("ARG_ADDR2", prVO.getAddr2());
			map.put("OUT_PARAM", "");
			
			dao.select("sdpa0040.procedure_updateProdReqHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			System.out.println(">>> jsonList.length = " + jsonList.length());

			//sub 입력
			if(jsonList.length() > 0) {
				JSONObject obj = new JSONObject();
				
				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);

					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
					map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
					map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
					map.put("ARG_SEQ", Integer.toString(i + 1));
					map.put("ARG_CUST_CD", prVO.getCust_num());
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_UNIT_A", obj.getString("qty_allocjob"));
					map.put("ARG_UNIT_B", obj.getString("u_m"));
					map.put("ARG_QTY", obj.getString("qty_input1"));
					map.put("OUT_PARAM", "");
					
					dao.select("sdpa0040.procedure_updateProdReqSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
				}
			}
			
			dao.executeBatch();
			dao.commit();
			
			redirectAttr.addFlashAttribute("prVO", prVO);
			
			return "redirect:/sdpa004101d.do";

		} catch (Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
		
	}
	
	@RequestMapping(value = "sdpa004101u_update.do")		//제조의뢰 수정 DB처리
	public String sdpa004101u_update(@ModelAttribute(value = "prVO")ProdReqHeaderVO prVO, 
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			RedirectAttributes redirectAttr, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		try {
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
			map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
			map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
			map.put("ARG_REQ_NO",prVO.getJeonpyo_no());
			map.put("ARG_CUST_CD", prVO.getCust_num());
			map.put("ARG_DELY_DT", getExpDateString(prVO.getEuiloiil()));
			map.put("ARG_DELY_TYPE", prVO.getBaedal_gubun());
			map.put("ARG_RMK", prVO.getBigo());
			map.put("ARG_RECVER", prVO.getInsuja());
			map.put("ARG_TEL_NO", prVO.getTel_no());
			map.put("ARG_ZIP", prVO.getZip());
			map.put("ARG_ADDR1", prVO.getAddr1());
			map.put("ARG_ADDR2", prVO.getAddr2());
			map.put("OUT_PARAM", "");
			
			dao.select("sdpa0040.procedure_updateProdReqHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			//sub 입력
			if(jsonList.length() > 0) {
				//기존 sub 품목 전체 삭제
				map = new HashMap<String, Object>();
				map.put("ARG_FLAG", "delete");
				map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
				map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
				map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
				map.put("ARG_CUST_CD", prVO.getCust_num());
				map.put("OUT_PARAM", "");
				dao.select("sdpa0040.procedure_updateProdReqSub", map);
				
				if(!map.get("OUT_PARAM").equals("OK")) {
					//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
					System.out.println((String) map.get("OUT_PARAM"));
					dao.endTransaction();
					return "templates/error";
				}
				
				
				JSONObject obj = new JSONObject();

				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);

					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
					map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
					map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
					map.put("ARG_SEQ", Integer.toString(i + 1));
					map.put("ARG_CUST_CD", prVO.getCust_num());
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_UNIT_A", obj.getString("qty_allocjob"));
					map.put("ARG_UNIT_B", obj.getString("u_m"));
					map.put("ARG_QTY", obj.getString("qty_input1"));
					map.put("OUT_PARAM", "");
					
					dao.select("sdpa0040.procedure_updateProdReqSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
				}
			}
			
			dao.executeBatch();
			dao.commit();
			
			redirectAttr.addFlashAttribute("prVO", prVO);
			return "redirect:/sdpa004101d.do";
			
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}

	@RequestMapping(value = "sdpa004101d_delete.do")		//제조의뢰 삭제
	public String sdpa004101d_delete(@ModelAttribute("prVO")ProdReqHeaderVO prVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//header 입력
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
		map.put("ARG_REQ_DT", getExpDateString(prVO.getIlja()));
		map.put("ARG_REQ_NO", prVO.getJeonpyo_no());
		map.put("ARG_CUST_CD", prVO.getCust_num());
		map.put("OUT_PARAM", "");
		
		dao.select("sdpa0040.procedure_updateProdReqHeader", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		redirectAttr.addFlashAttribute("prVO", prVO);
		
		return "redirect:/sdpa004001l.do";
	}
	
	@RequestMapping(value = "sdpa004001d.do")		//신규제조의뢰 상세 조회
	public String sdpa004001d(@ModelAttribute("nprVO")NprodReqHeaderVO nprVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//신규제조의뢰 헤더 불러오기
		map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
		map.put("ARG_CUST_CD", nprVO.getCust_num());
		map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
		map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
		map.put("OUT_PARAM", null);
		dao.select("sdpa0040.procedure_selectNprodReqHeader", map);
		NprodReqHeaderVO nprodReqHeader = ((List<NprodReqHeaderVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("nprodReqHeader", nprodReqHeader);
		
		
		//신규제조의뢰 서브 불러오기(신규제조는 단건으로 불러옴)
		map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
		map.put("ARG_CUST_CD", nprVO.getCust_num());
		map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
		map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
		map.put("OUT_PARAM", null);
		dao.select("sdpa0040.procedure_selectNprodReqSub", map);
		NprodReqSubVO nprodReqSub = ((List<NprodReqSubVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("nprodReqSub", nprodReqSub);
		
		return "sdpa0040/sdpa004001d";
	}
	
	@RequestMapping(value = "sdpa004001u.{flag}.do")		//신규 제조의뢰 작성 & 수정 화면 호출
	public String sdpa004001u(@ModelAttribute("nprVO") NprodReqHeaderVO nprVO, @PathVariable("flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		model.addAttribute("flag", flag);
		
		//배달구분 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "425");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code10", map.get("OUT_PARAM"));
		
		if(flag != null && flag.equals("update")) {
			//신규제조의뢰 헤더 불러오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
			map.put("ARG_CUST_CD", nprVO.getCust_num());
			map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
			map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
			map.put("OUT_PARAM", null);
			dao.select("sdpa0040.procedure_selectNprodReqHeader", map);
			NprodReqHeaderVO nprodReqHeader = ((List<NprodReqHeaderVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("nprodReqHeader", nprodReqHeader);
			
			
			//신규제조의뢰 서브 불러오기(신규제조는 단건으로 불러옴)
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
			map.put("ARG_CUST_CD", nprVO.getCust_num());
			map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
			map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
			map.put("OUT_PARAM", null);
			dao.select("sdpa0040.procedure_selectNprodReqSub", map);
			NprodReqSubVO nprodReqSub = ((List<NprodReqSubVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("nprodReqSub", nprodReqSub);
		}
		
		return "sdpa0040/sdpa004001u";
	}
	
	@RequestMapping(value="sdpa004001u_insert.do")		//신규 제조의뢰 작성(db insert)
	public String sdpa004001u_insert(@ModelAttribute("nprVO")NprodReqHeaderVO nprVO, @ModelAttribute("nprsVO")NprodReqSubVO nprsVO, 
			RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		try {
			HashMap<String, Object> map;
			
			//작성일 현재일자 셋팅
			Date dt = new Date();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");
			nprVO.setIlja(smt.format(dt));
			nprsVO.setIlja(smt.format(dt));
			
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//전표번호 가져오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
			map.put("ARG_SLIP_TYPE", "03");		//신규제조 03
			map.put("ARG_DT", getExpDateString(nprVO.getIlja()));
			map.put("OUT_PARAM", null);
			
			dao.select("sdpa0040.procedure_selectJeonpyoNo", map);
			List<ProdReqHeaderVO> list = (List<ProdReqHeaderVO>) map.get("OUT_PARAM");
			
			nprVO.setJeonpyo_no(((ProdReqHeaderVO) list.get(0)).getJeonpyo_no());
			nprsVO.setJeonpyo_no(((ProdReqHeaderVO) list.get(0)).getJeonpyo_no());
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "insert");
			map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
			map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
			map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
			map.put("ARG_CUST_CD", nprVO.getCust_num());
			map.put("ARG_DELY_DT", getExpDateString(nprVO.getEuiloiil()));
			map.put("ARG_DELY_TYPE", nprVO.getBaedal_gubun());
			map.put("ARG_DELY_PLACE", nprVO.getBaedal_jangso());
			map.put("ARG_RECVER", nprVO.getInsuja());
			map.put("ARG_TEL_NO", nprVO.getTel_no());
			map.put("ARG_RMK", nprVO.getBigo());
			map.put("ARG_ZIP", nprVO.getZip());
			map.put("ARG_ADDR1", nprVO.getAddr1());
			map.put("ARG_ADDR2", nprVO.getAddr2());
			map.put("OUT_PARAM", "");
			dao.select("sdpa0040.procedure_updateNprodReqHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			//서브 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "insert");
			map.put("ARG_BIZ_AREA_CD", nprsVO.getWorkplace());
			map.put("ARG_ORD_DT", getExpDateString(nprsVO.getIlja()));
			map.put("ARG_ORD_NO", nprsVO.getJeonpyo_no());
			map.put("ARG_SEQ", "1");
			map.put("ARG_CUST_CD", nprsVO.getCust_num());
			map.put("ARG_ITEM_NM", nprsVO.getPummyeong());
			map.put("ARG_UNIT_A", nprsVO.getPojang_danwi_a());
			map.put("ARG_UNIT_B", nprsVO.getPojang_danwi_b());
			map.put("ARG_QTY", nprsVO.getPojang_sulyang());
			map.put("ARG_RMK", nprsVO.getBigo());
			map.put("ARG_GYEON_SAEOBJANG", nprsVO.getGyeon_saeobjang());
			map.put("ARG_GYEON_ILJA", getExpDateString(nprsVO.getGyeon_ilja()));
			map.put("ARG_GYEON_JEONPYO_NO", nprsVO.getGyeon_jeonpyo_no());
			map.put("OUT_PARAM", "");
			dao.select("sdpa0040.procedure_updateNprodReqSub", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			dao.executeBatch();
			dao.commit();
			
			redirectAttr.addFlashAttribute("nprVO", nprVO);
			
			return "redirect:/sdpa004001d.do";
		} catch (Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
		
	}
	
	@RequestMapping(value="sdpa004001u_update.do")		//신규 제조의뢰 작성(db insert)
	public String sdpa004001u_update(@ModelAttribute("nprVO")NprodReqHeaderVO nprVO, @ModelAttribute("nprsVO")NprodReqSubVO nprsVO, 
			RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		try {
			HashMap<String, Object> map;
			
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
			map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
			map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
			map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
			map.put("ARG_CUST_CD", nprVO.getCust_num());
			map.put("ARG_DELY_DT", getExpDateString(nprVO.getEuiloiil()));
			map.put("ARG_DELY_TYPE", nprVO.getBaedal_gubun());
			map.put("ARG_DELY_PLACE", nprVO.getBaedal_jangso());
			map.put("ARG_RECVER", nprVO.getInsuja());
			map.put("ARG_TEL_NO", nprVO.getTel_no());
			map.put("ARG_RMK", nprVO.getBigo());
			map.put("ARG_ZIP", nprVO.getZip());
			map.put("ARG_ADDR1", nprVO.getAddr1());
			map.put("ARG_ADDR2", nprVO.getAddr2());
			map.put("OUT_PARAM", "");
			dao.select("sdpa0040.procedure_updateNprodReqHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			//서브 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
			map.put("ARG_BIZ_AREA_CD", nprsVO.getWorkplace());
			map.put("ARG_ORD_DT", getExpDateString(nprsVO.getIlja()));
			map.put("ARG_ORD_NO", nprsVO.getJeonpyo_no());
			map.put("ARG_SEQ", "1");
			map.put("ARG_CUST_CD", nprsVO.getCust_num());
			map.put("ARG_ITEM_NM", nprsVO.getPummyeong());
			map.put("ARG_UNIT_A", nprsVO.getPojang_danwi_a());
			map.put("ARG_UNIT_B", nprsVO.getPojang_danwi_b());
			map.put("ARG_QTY", nprsVO.getPojang_sulyang());
			map.put("ARG_RMK", nprsVO.getBigo());
			map.put("ARG_GYEON_SAEOBJANG", nprsVO.getGyeon_saeobjang());
			map.put("ARG_GYEON_ILJA", getExpDateString(nprsVO.getGyeon_ilja()));
			map.put("ARG_GYEON_JEONPYO_NO", nprsVO.getGyeon_jeonpyo_no());
			map.put("OUT_PARAM", "");
			dao.select("sdpa0040.procedure_updateNprodReqSub", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			dao.executeBatch();
			dao.commit();
			
			redirectAttr.addFlashAttribute("nprVO", nprVO);
			
			return "redirect:/sdpa004001d.do";
		} catch (Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	@RequestMapping(value = "sdpa004001d_delete.do")		//제조의뢰 삭제
	public String sdpa004001d_delete(@ModelAttribute("nprVO")ProdReqHeaderVO nprVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//header 입력
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", nprVO.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(nprVO.getIlja()));
		map.put("ARG_ORD_NO", nprVO.getJeonpyo_no());
		map.put("ARG_CUST_CD", nprVO.getCust_num());
		map.put("OUT_PARAM", "");
		
		dao.select("sdpa0040.procedure_updateNprodReqHeader", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		redirectAttr.addFlashAttribute("nprVO", nprVO);
		
		return "redirect:/sdpa004001l.do";
	}
}
