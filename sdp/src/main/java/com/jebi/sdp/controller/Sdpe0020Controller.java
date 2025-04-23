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
public class Sdpe0020Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpe0020Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpe002001l.do")		//msds 목록 조회
	public String selectList(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ARG_CUST_CD", msdsHVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(msdsHVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(msdsHVO.getSearchDate_to()));
		map.put("ARG_SUBMIT_NM", msdsHVO.getSearchText());
		map.put("OUT_PARAM", null);
		dao.update("sdpe0020.procedure_selectMsdsList", map);
		
		List<MsdsHeaderVO> list = (List<MsdsHeaderVO>) map.get("OUT_PARAM");
		
		model.addAttribute("msdsList", list);
		
		return "sdpe0020/sdpe002001l";
	}
	
	
	@RequestMapping(value = "sdpe002001d.do")		//msds 상세조회
	public String sdpe002001d(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("ARG_CUST_CD", msdsHVO.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
		map.put("ARG_REQ_NO", msdsHVO.getReq_no());
		map.put("OUT_PARAM", null);
		dao.update("sdpe0020.procedure_selectMsdsHeader", map);
		
		MsdsHeaderVO headerVO = ((List<MsdsHeaderVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("headerVO", headerVO);

		map = new HashMap<String, Object>();
		map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
		map.put("ARG_REQ_NO", msdsHVO.getReq_no());
		map.put("OUT_PARAM", null);
		dao.update("sdpe0020.procedure_selectMsdsSub", map);
		
		List<MsdsSubVO> subList = (List<MsdsSubVO>) map.get("OUT_PARAM");
		model.addAttribute("subList", subList);
		
		return "sdpe0020/sdpe002001d";
	}
	
	
	@RequestMapping(value = "sdpe002001u.{flag}.do")		//MSDS 등록&수정화면 호출
	public String sdpe001001u(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO, @PathVariable String flag
			, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);		//flag 값을 다음 화면으로 넘김

		if("insert".equals(flag)) {				//신규등록화면
			
		} else if("update".equals(flag)) {		//기존 MSDS 수정화면
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("ARG_CUST_CD", msdsHVO.getCust_num());
			map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
			map.put("ARG_REQ_NO", msdsHVO.getReq_no());
			map.put("OUT_PARAM", null);
			dao.update("sdpe0020.procedure_selectMsdsHeader", map);
			
			MsdsHeaderVO headerVO = ((List<MsdsHeaderVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("headerVO", headerVO);

			map = new HashMap<String, Object>();
			map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
			map.put("ARG_REQ_NO", msdsHVO.getReq_no());
			map.put("OUT_PARAM", null);
			dao.update("sdpe0020.procedure_selectMsdsSub", map);
			
			List<MsdsSubVO> subList = (List<MsdsSubVO>) map.get("OUT_PARAM");
			model.addAttribute("subList", subList);
		}
		
		return "sdpe0020/sdpe002001u";
	}
	
	@RequestMapping(value = "sdpe002001u_insert.do")		//msds 신규 작성 (db insert)
	public String sdpe002001u_insert(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		HashMap<String, Object> map;
		
		//메일관련 변수
		List li_sendMail = new ArrayList<EmailVO>();
		EmailVO emailVO = new EmailVO();
		
		//작성일 현재일자 셋팅
		Date dt = new Date();
		SimpleDateFormat smt = new SimpleDateFormat("yyyyMMdd");
		msdsHVO.setReq_dt(smt.format(dt));
		
		try {
			//transaction 시작
			dao.startTransaction();
			
			//전표번호 가져오기
			map = new HashMap<String, Object>();
			map.put("ARG_TYPE", "MSDS");		//주문서 전표 번호는 01
			map.put("ARG_DT", getExpDateString(msdsHVO.getReq_dt()));
			map.put("OUT_PARAM", null);
			
			dao.select("sdpe0020.procedure_selectJeonpyoNo", map);
			List<MsdsHeaderVO> list = (List<MsdsHeaderVO>) map.get("OUT_PARAM");
			msdsHVO.setReq_no(((MsdsHeaderVO) list.get(0)).getReq_no());
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "insert");
			map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
			map.put("ARG_REQ_NO", msdsHVO.getReq_no());
			map.put("ARG_CUST_CD", msdsHVO.getCust_num());
			map.put("ARG_SUBMIT_NM", msdsHVO.getSubmit_nm());
			map.put("ARG_SUBMIT_DT", getExpDateString(msdsHVO.getSubmit_dt()));
			map.put("ARG_RMK", msdsHVO.getRmk());
			map.put("OUT_PARAM", "");
			
			dao.select("sdpe0020.procedure_updateMsdsHeader", map);
			
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
					map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
					map.put("ARG_REQ_NO", msdsHVO.getReq_no());
					map.put("ARG_SEQ", Integer.toString(i));
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_DEPT_CD", "");
					map.put("ARG_EMP_NO", "");
					map.put("OUT_PARAM", "");
					
					dao.select("sdpe0020.procedure_updateMsdsSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
					//메일 발송 정보를 ArrayList에 저장
					map = new HashMap<String, Object>();
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("OUT_PARAM", null);
					dao.select("sdpe0020.procedure_selectEmail", map);
					List<MsdsSubVO> li = (List<MsdsSubVO>) map.get("OUT_PARAM");
					
					if(li != null || li.size() > 0) {
						ArrayList arLi = new ArrayList<String>();
						
						for(int x = 0; x < li.size(); x++) {
							arLi.add(((MsdsSubVO) li.get(x)).getEmail());
						}
						
						emailVO = new EmailVO();
						emailVO.setFrom(msdsHVO.getEmail());
						emailVO.setLi_to(arLi);
						emailVO.setSubject("[인터넷주문시스템] MSDS 작성 요청");
						
						String contents = "";
						contents += "\n" + "본 메일은 '대리점 주문시스템'에서 자동으로 발송되는 메일입니다.";
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "수신부서 : ";
						for(int y = 0; y < arLi.size(); y++) {
							contents += arLi.get(y);
							if(y < (arLi.size() - 1) ) {
								contents += ", ";
							}
						}
						contents += "\n" + "";
						contents += "\n" + "대리점 " + msdsHVO.getCust_nm() + " 입니다.";
						contents += "\n" + "";
						contents += "\n" + "다음과 같이 MSDS를 요청하오니 신속히 처리하여 주시기 바랍니다.";
						contents += "\n" + "";
						contents += "\n" + "요 청 일 : " + msdsHVO.getReq_dt();
						contents += "\n" + "요청번호 : " + msdsHVO.getReq_no();
						contents += "\n" + "제품코드 : " + obj.getString("item");
						contents += "\n" + "품 명 : " + obj.getString("description");
						contents += "\n" + "제 출 일 : " + msdsHVO.getSubmit_dt();
						contents += "\n" + "제 출 처 : " + msdsHVO.getSubmit_nm();
						contents += "\n" + "특기사항 : " + msdsHVO.getRmk();
						contents += "\n" + "대리점 e-mail : " + msdsHVO.getEmail();
						contents += "\n" + "처리시 유의할 점 : SAYONG_YN과 OPEN_YN이 'Y'로 되어야만 주문시스템에서 조회가 가능합니다.";
						emailVO.setContents(contents);
						li_sendMail.add(emailVO);
					}
				}
			}
			
			//li에 저장해 놓은 이메일 객체들을 일괄 발송후 커밋
			for(int i=0; i < li_sendMail.size(); i ++) {
				sendMail( (EmailVO) li_sendMail.get(i));
			}
			
			dao.commit();
			dao.endTransaction();
			
			redirectAttr.addFlashAttribute("msdsHVO", msdsHVO);
			
			return "redirect:/sdpe002001d.do";

		} catch(Exception e) {
			e.printStackTrace();
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	@RequestMapping(value = "sdpe002001u_update.do")		//시험성적서 수정 (db insert)
	public String sdpe001001u_update(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			RedirectAttributes redirectAttr, @RequestParam(value="jsonList", required=false)JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		HashMap<String, Object> map;
		
		//메일관련 변수
		List li_sendMail = new ArrayList<EmailVO>();
		EmailVO emailVO = new EmailVO();
		
		//작성일 현재일자 셋팅
		Date dt = new Date();
		SimpleDateFormat smt = new SimpleDateFormat("yyyyMMdd");
		msdsHVO.setReq_dt(smt.format(dt));
		
		try {
			//transaction 시작
			dao.startTransaction();
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
			map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
			map.put("ARG_REQ_NO", msdsHVO.getReq_no());
			map.put("ARG_CUST_CD", msdsHVO.getCust_num());
			map.put("ARG_SUBMIT_NM", msdsHVO.getSubmit_nm());
			map.put("ARG_SUBMIT_DT", getExpDateString(msdsHVO.getSubmit_dt()));
			map.put("ARG_RMK", msdsHVO.getRmk());
			map.put("OUT_PARAM", "");
			
			dao.select("sdpe0020.procedure_updateMsdsHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			//sub 전체 삭제
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "delete");
			map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
			map.put("ARG_REQ_NO", msdsHVO.getReq_no());
			dao.select("sdpe0020.procedure_updateMsdsSub", map);
			
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
					map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
					map.put("ARG_REQ_NO", msdsHVO.getReq_no());
					map.put("ARG_SEQ", Integer.toString(i));
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_DEPT_CD", "");
					map.put("ARG_EMP_NO", "");
					map.put("OUT_PARAM", "");
					
					dao.select("sdpe0020.procedure_updateMsdsSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
					//메일 발송 정보를 ArrayList에 저장
					map = new HashMap<String, Object>();
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("OUT_PARAM", null);
					dao.select("sdpe0020.procedure_selectEmail", map);
					List<MsdsSubVO> li = (List<MsdsSubVO>) map.get("OUT_PARAM");
					
					if(li != null || li.size() > 0) {
						ArrayList arLi = new ArrayList<String>();
						
						for(int x = 0; x < li.size(); x++) {
							arLi.add(((MsdsSubVO) li.get(x)).getEmail());
						}
						
						emailVO = new EmailVO();
						emailVO.setFrom(msdsHVO.getEmail());
						emailVO.setLi_to(arLi);
						emailVO.setSubject("[인터넷주문시스템] MSDS 작성 요청");
						
						String contents = "";
						contents += "\n" + "본 메일은 '대리점 주문시스템'에서 자동으로 발송되는 메일입니다.";
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "수신부서 : ";
						for(int y = 0; y < arLi.size(); y++) {
							contents += arLi.get(y);
							if(y < (arLi.size() - 1) ) {
								contents += ", ";
							}
						}
						contents += "\n" + "";
						contents += "\n" + "대리점 " + msdsHVO.getCust_nm() + " 입니다.";
						contents += "\n" + "";
						contents += "\n" + "다음과 같이 MSDS를 요청하오니 신속히 처리하여 주시기 바랍니다.";
						contents += "\n" + "";
						contents += "\n" + "요 청 일 : " + msdsHVO.getReq_dt();
						contents += "\n" + "요청번호 : " + msdsHVO.getReq_no();
						contents += "\n" + "제품코드 : " + obj.getString("item");
						contents += "\n" + "품 명 : " + obj.getString("description");
						contents += "\n" + "제 출 일 : " + msdsHVO.getSubmit_dt();
						contents += "\n" + "제 출 처 : " + msdsHVO.getSubmit_nm();
						contents += "\n" + "특기사항 : " + msdsHVO.getRmk();
						contents += "\n" + "대리점 e-mail : " + msdsHVO.getEmail();
						contents += "\n" + "처리시 유의할 점 : SAYONG_YN과 OPEN_YN이 'Y'로 되어야만 주문시스템에서 조회가 가능합니다.";
						emailVO.setContents(contents);
						li_sendMail.add(emailVO);
					}
				}
			}
			
			//li에 저장해 놓은 이메일 객체들을 일괄 발송후 커밋
			for(int i=0; i < li_sendMail.size(); i ++) {
				sendMail( (EmailVO) li_sendMail.get(i));
			}
			
			dao.commit();
			dao.endTransaction();
			
			redirectAttr.addFlashAttribute("msdsHVO", msdsHVO);
			
			return "redirect:/sdpe002001d.do";

		} catch(Exception e) {
			e.printStackTrace();
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	@RequestMapping(value = "sdpe002001d_delete.do")		//시험성적서 요청 취소(delete flag 처리)
	public String sdpe001001d_delete(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap map;
		
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_REQ_DT", getExpDateString(msdsHVO.getReq_dt()));
		map.put("ARG_REQ_NO", msdsHVO.getReq_no());
		map.put("ARG_CUST_CD", msdsHVO.getCust_num());
		map.put("ARG_SUBMIT_NM", msdsHVO.getSubmit_nm());
		map.put("ARG_SUBMIT_DT", getExpDateString(msdsHVO.getSubmit_dt()));
		map.put("ARG_RMK", msdsHVO.getRmk());
		map.put("OUT_PARAM", "");
		
		dao.select("sdpe0020.procedure_updateMsdsHeader", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		redirectAttr.addFlashAttribute("msdsHVO", msdsHVO);
		
		return "redirect:/sdpe002001l.do";
	}
}
