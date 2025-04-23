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
public class Sdpa0020Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0020Controller.class);

	@Autowired
	private CmmnDao dao;

	@RequestMapping(value = "sdpa002001l.do")		//주문서 목록 조회
	public String selectCo(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", 		coVO.getCust_num());
		map.put("ARG_FRDT", 		getExpDateString(coVO.getSearchDate_from()));
		map.put("ARG_TODT", 		getExpDateString(coVO.getSearchDate_to()));
		map.put("ARG_INCLUDE_YN", 	coVO.getSearchCheckBox_01().equals("on") ? "Y" : "N");	// 출하완료 포함이면 Y, 미포함 N
		map.put("OUT_PARAM", 		null);
		dao.update("sdpa0020.procedure_selectOrder", map);
		
		model.addAttribute("coList", map.get("OUT_PARAM"));
		
		return "sdpa0020/sdpa002001l";
	}
	
	@RequestMapping(value = "sdpa002001d.do")		//주문서 상세 조회
	public String detailCo(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//header 정보
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", 		coVO.getCust_num());
		map.put("ARG_ORD_DT", 		getExpDateString(coVO.getIlja()));
		map.put("ARG_ORD_NO", 		coVO.getJeonpyo_no());
		map.put("OUT_PARAM", 		null);
		dao.update("sdpa0020.procedure_selectOrderHeader", map);
		
		CoVO co = ((List<CoVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("co", co);
		
		//sub 정보
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", 		coVO.getCust_num());
		map.put("ARG_ORD_DT", 		getExpDateString(coVO.getIlja()));
		map.put("ARG_ORD_NO", 		coVO.getJeonpyo_no());
		map.put("OUT_PARAM", 		null);
		dao.update("sdpa0020.procedure_selectOrderSub", map);
		List<CoItemVO> coItemList = (List<CoItemVO>)map.get("OUT_PARAM");
		model.addAttribute("coItemList", map.get("OUT_PARAM"));
		
		return "sdpa0020/sdpa002001d";
	}
	
	@RequestMapping(value = "sdpa002001u.{flag}.do")		//주문서 작성화면으로
	public String insertForm(@ModelAttribute("coVO")CoVO coVO,
			@PathVariable(value="flag")String flag,
			@RequestParam(value="pageCheck", required=false)String pageCheck,
			@RequestParam(value="jsonList", required=false)JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//화면으로 flag값을 바로 넘김
		model.addAttribute("flag", flag);
		HashMap<String, Object> map;
		
		//종합 포인트 정보
		CustomerVO customerVO = new CustomerVO();
		customerVO = (CustomerVO) dao.select("sdpf0030.select_pointInfo", coVO);
		model.addAttribute("customerVO", customerVO);
		
		//장바구니에서 넘어 왔는지 여부를 다음화면으로 넘김
		model.addAttribute("pageCheck", pageCheck);
		
		//배달구분 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "425");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code10", map.get("OUT_PARAM"));
		
		if("insert".equals(flag)) {
			//화면에서 넘겨준 아이템들이 있을경우 JSONarray를 ArryaList로 변환하여 전달
			if(jsonList != null && jsonList.length() > 0) {
				List<ItemVO> coItemList = new ArrayList<ItemVO>();
				JSONObject obj = new JSONObject();
				CoItemVO item;
				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);
					item = new CoItemVO();
					item.setJepum_code(obj.getString("item"));
					item.setPummyeong(obj.getString("description"));
					item.setPanmae_danwi_a(obj.getString("qty_allocjob"));
					item.setPanmae_danwi_b(obj.getString("u_m"));
					item.setPanmae_sulyang(obj.getString("qty_on_hand01"));
					item.setBo_sulyang("0");
					coItemList.add(item);
				}
				
				model.addAttribute("coItemList", coItemList);
			}
			
		} else if("update".equals(flag)) {
			//header 정보
			map = new HashMap<String, Object>();
			map.put("ARG_CUST_CD", 		coVO.getCust_num());
			map.put("ARG_ORD_DT", 		getExpDateString(coVO.getIlja()));
			map.put("ARG_ORD_NO", 		coVO.getJeonpyo_no());
			map.put("OUT_PARAM", 		null);
			dao.update("sdpa0020.procedure_selectOrderHeader", map);
			
			CoVO co = ((List<CoVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("co", co);
			
			//sub 정보
			map = new HashMap<String, Object>();
			map.put("ARG_CUST_CD", 		coVO.getCust_num());
			map.put("ARG_ORD_DT", 		getExpDateString(coVO.getIlja()));
			map.put("ARG_ORD_NO", 		coVO.getJeonpyo_no());
			map.put("OUT_PARAM", 		null);
			dao.update("sdpa0020.procedure_selectOrderSub", map);
			
			List<CoItemVO> coItemList = (List<CoItemVO>)map.get("OUT_PARAM");
			model.addAttribute("coItemList", map.get("OUT_PARAM"));
		}
		
		return "sdpa0020/sdpa002001u";
	}
	
	@RequestMapping(value = "sdpa002001u_insert.do")		//주문서 등록 (db insert)
	public String sdpa002001u_insert(@ModelAttribute("coVO")CoVO coVO, RedirectAttributes redirectAttr,
			@RequestParam(value="pageCheck", required=false) String pageCheck,
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		List<String> li_delBasket = new ArrayList<String>();		//장바구니 삭제용 array
		
		//작성일 현재일자 셋팅
		Date dt = new Date();
		SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");
		coVO.setIlja(smt.format(dt));
		
		try {
			//transaction 시작
			dao.startTransaction();
			
			//전표번호 가져오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
			map.put("ARG_SLIP_TYPE", "01");		//주문서 전표 번호는 01
			map.put("ARG_DT", getExpDateString(coVO.getIlja()));
			map.put("OUT_PARAM", null);
			
			dao.select("sdpa0020.procedure_selectJeonpyoNo", map);
			List<CoVO> list = (List<CoVO>)map.get("OUT_PARAM");
			coVO.setJeonpyo_no(((CoVO) list.get(0)).getJeonpyo_no());
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "insert");
			map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
			map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
			map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
			map.put("ARG_CUST_CD", coVO.getCust_num());
			
			map.put("ARG_DELY_TYPE", coVO.getBaedal_gubun());
			map.put("ARG_DELY_DT", getExpDateString(coVO.getYocheongil()));
			map.put("ARG_DELY_PLACE", "");
			map.put("ARG_RECVER", coVO.getInsuja());
			map.put("ARG_TEL_NO", coVO.getTel_no());
			
			map.put("ARG_RMK", coVO.getBigo());
			map.put("ARG_TAKSONG_POINT_YN", coVO.getTaksong_point_yn());
			map.put("ARG_ZIP", coVO.getZip());
			map.put("ARG_ADDR1", coVO.getAddr1());
			map.put("ARG_ADDR2", coVO.getAddr2());
			
			map.put("OUT_PARAM", "");
			
			dao.select("sdpa0020.procedure_insertOrderHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			//sub 입력
			if(jsonList.length() > 0) {
				JSONObject obj = new JSONObject();
				
				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);

					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
					map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
					map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
					map.put("ARG_SEQ", Integer.toString(i + 1));
					map.put("ARG_CUST_CD", coVO.getCust_num());
					map.put("ARG_DELY_TYPE", coVO.getBaedal_gubun());
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_SALE_UNIT_A", obj.getString("qty_allocjob"));
					map.put("ARG_SALE_UNIT_B", obj.getString("u_m"));
					map.put("ARG_QTY", obj.getString("qty_input1"));
					map.put("ARG_QTY_BO", obj.getString("qty_input2"));
					map.put("ARG_QTY_BO_C", "0");
					map.put("ARG_RMK", obj.getString("bigo"));
					map.put("OUT_PARAM", "");
					
					dao.select("sdpa0020.procedure_insertOrderSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
					//장바구니에서 주문서작성의 경우 array에 추가해서 마지막에 한번에 장바구니 품목들을 삭제
					if(pageCheck != null && pageCheck.equals("Y")) {
						li_delBasket.add(obj.getString("item") + obj.getString("qty_allocjob") + obj.getString("u_m"));
					}
				}
			}
			
			//장바구니에서 주문작성으로 넘어온 경우 장바구니에서 저장된 제품 삭제
			if(pageCheck != null && pageCheck.equals("Y")) {
				BasketItemVO basketVO = new BasketItemVO();
				basketVO.setParamList(li_delBasket);
				dao.delete("sdpf0020.delete_delBasket", basketVO);				
			}
			
			dao.commit();
			dao.endTransaction();
			
			redirectAttr.addFlashAttribute("coVO", coVO);
			
			return "redirect:/sdpa002001d.do";

		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
		
	}
	
	@RequestMapping(value = "sdpa002001u_update.do")		//주문서 수정 (db update)
	public String sdpa002001u_update(@ModelAttribute("coVO")CoVO coVO, RedirectAttributes redirectAttr,
			@RequestParam(value="pageCheck", required=false) String pageCheck,
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		try {
			HashMap<String, Object> map;
			
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
			map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
			map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
			map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
			map.put("ARG_CUST_CD", coVO.getCust_num());
			
			map.put("ARG_DELY_TYPE", coVO.getBaedal_gubun());
			map.put("ARG_DELY_DT", getExpDateString(coVO.getYocheongil()));
			map.put("ARG_DELY_PLACE", "(" + coVO.getZip() + ")"+ coVO.getAddr1() + " " + coVO.getAddr2());
			map.put("ARG_RECVER", coVO.getInsuja());
			map.put("ARG_TEL_NO", coVO.getTel_no());
			
			map.put("ARG_RMK", coVO.getBigo());
			map.put("ARG_TAKSONG_POINT_YN", coVO.getTaksong_point_yn());
			map.put("ARG_ZIP", coVO.getZip());
			map.put("ARG_ADDR1", coVO.getAddr1());
			map.put("ARG_ADDR2", coVO.getAddr2());
			
			map.put("OUT_PARAM", "");
			
			dao.select("sdpa0020.procedure_insertOrderHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			//sub 입력
			if(jsonList.length() > 0) {
				//기존 주문서의 품목 전체 삭제
				map = new HashMap<String, Object>();
				map.put("ARG_FLAG", "delete");
				map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
				map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
				map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
				map.put("ARG_CUST_CD", coVO.getCust_num());
				dao.select("sdpa0020.procedure_insertOrderSub", map);
				
				if(!map.get("OUT_PARAM").equals("OK")) {
					//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
					System.out.println((String) map.get("OUT_PARAM"));
					dao.endTransaction();
					return "templates/error";
				}
				
				//수정페이지에서 넘어온 품목들 다시 insert
				JSONObject obj = new JSONObject();
				
				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);
					
					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
					map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
					map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
					map.put("ARG_SEQ", Integer.toString(i + 1));
					map.put("ARG_CUST_CD", coVO.getCust_num());
					map.put("ARG_DELY_TYPE", coVO.getBaedal_gubun());
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_SALE_UNIT_A", obj.getString("qty_allocjob"));
					map.put("ARG_SALE_UNIT_B", obj.getString("u_m"));
					map.put("ARG_QTY", obj.getString("qty_input1"));
					map.put("ARG_QTY_BO", obj.getString("qty_input2"));
					map.put("ARG_QTY_BO_C", "0");
					map.put("ARG_RMK", obj.getString("bigo"));
					map.put("OUT_PARAM", "");
					
					dao.select("sdpa0020.procedure_insertOrderSub", map);
					
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
			dao.endTransaction();
			
			redirectAttr.addFlashAttribute("coVO", coVO);
			
			return "redirect:/sdpa002001d.do";
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	@RequestMapping(value = "sdpa002001d_delete.do")		//주문서 삭제
	public String cancelCo(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//header 입력
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", coVO.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(coVO.getIlja()));
		map.put("ARG_ORD_NO", coVO.getJeonpyo_no());
		map.put("ARG_CUST_CD", coVO.getCust_num());
		
		map.put("ARG_DELY_TYPE", coVO.getBaedal_gubun());
		map.put("ARG_DELY_DT", getExpDateString(coVO.getYocheongil()));
		map.put("ARG_DELY_PLACE", coVO.getBaedal_jangso());
		map.put("ARG_RECVER", coVO.getInsuja());
		map.put("ARG_TEL_NO", coVO.getTel_no());
		
		map.put("ARG_RMK", coVO.getBigo());
		map.put("ARG_TAKSONG_POINT_YN", coVO.getTaksong_point_yn());
		map.put("ARG_ZIP", coVO.getZip());
		map.put("ARG_ADDR1", coVO.getAddr1());
		map.put("ARG_ADDR2", coVO.getAddr2());
		
		map.put("OUT_PARAM", "");
		
		dao.select("sdpa0020.procedure_insertOrderHeader", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		return "redirect:/sdpa002001l.do";
	}
}

