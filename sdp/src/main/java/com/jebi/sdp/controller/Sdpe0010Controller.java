package com.jebi.sdp.controller;

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
public class Sdpe0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpe0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpe001001l.do")		//시험성적서 조회
	public String sdpe001001l(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ARG_CUST_CD", testReportVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(testReportVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(testReportVO.getSearchDate_to()));
		map.put("ARG_SUBMIT_NM", testReportVO.getSearchText());
		map.put("OUT_PARAM", null);
		dao.update("sdpe0010.procedure_selectTestReportList", map);
		
		List<TestReportVO> list = (List<TestReportVO>) map.get("OUT_PARAM");
		
		model.addAttribute("testReportList", list);
		
		return "sdpe0010/sdpe001001l";
	}
	
	@RequestMapping(value = "sdpe001001d.do")		//시험성적서 상세조회
	public String sdpe001001d(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map;
		
		//헤더 불러오기
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", testReportVO.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
		map.put("ARG_REQ_NO", testReportVO.getReq_no());
		map.put("OUT_PARAM", null);
		dao.update("sdpe0010.procedure_selectTestReportHeader", map);
		
		TestReportVO testReportHeader = ((List<TestReportVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("testReportHeader", testReportHeader);
		
		//서브 불러오기
		map = new HashMap<String, Object>();
		map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
		map.put("ARG_REQ_NO", testReportVO.getReq_no());
		map.put("OUT_PARAM", null);
		dao.update("sdpe0010.procedure_selectTestReportSub", map);
		
		List<TestReportItemVO> testReportSub = (List<TestReportItemVO>) map.get("OUT_PARAM");
		model.addAttribute("testReportSub", testReportSub);
		
		return "sdpe0010/sdpe001001d";
	}
	
	@RequestMapping(value = "sdpe001001u.{flag}.do")		//시험성적서 등록&수정화면 호출
	public String sdpe001001u(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			@PathVariable String flag, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);		//flag 값을 다음 화면으로 넘김
		
		if("insert".equals(flag)) {				//신규등록화면
			
		} else if("update".equals(flag)) {		//기존 시험요청서 수정화면
			Map<String, Object> map;
			
			//헤더 불러오기
			map = new HashMap<String, Object>();
			map.put("ARG_CUST_CD", testReportVO.getCust_num());
			map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
			map.put("ARG_REQ_NO", testReportVO.getReq_no());
			map.put("OUT_PARAM", null);
			dao.update("sdpe0010.procedure_selectTestReportHeader", map);
			
			TestReportVO testReportHeader = ((List<TestReportVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("testReportHeader", testReportHeader);
			
			//서브 불러오기
			map = new HashMap<String, Object>();
			map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
			map.put("ARG_REQ_NO", testReportVO.getReq_no());
			map.put("OUT_PARAM", null);
			dao.update("sdpe0010.procedure_selectTestReportSub", map);
			
			List<TestReportItemVO> testReportSub = (List<TestReportItemVO>) map.get("OUT_PARAM");
			model.addAttribute("testReportSub", testReportSub);
		}
		
		return "sdpe0010/sdpe001001u";
	}
	
	@RequestMapping(value = "sdpe001001u_insert.do")		//시험성적서 신규 작성 (db insert)
	public String sdpe001001u_insert(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;

		//메일관련 변수
		List li_sendMail = new ArrayList<EmailVO>();
		EmailVO emailVO = new EmailVO();
		
		
		try {
			dao.startTransaction();
			
			//전표번호 가져오기
			map = new HashMap<String, Object>();
			map.put("ARG_TYPE", "TEST_SHEET");
			map.put("ARG_DT", getExpDateString(testReportVO.getReq_dt()));
			map.put("OUT_PARAM", "");
			
			dao.select("sdpe0010.procedure_selectReqNo", map);
			List<TestReportVO> list = (List<TestReportVO>)map.get("OUT_PARAM");
			testReportVO.setReq_no(((TestReportVO) list.get(0)).getReq_no());
			
			//header 저장하기
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "insert");
			map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
			map.put("ARG_REQ_NO", testReportVO.getReq_no());
			map.put("ARG_CUST_CD", testReportVO.getCust_num());
			map.put("ARG_SUBMIT_NM_K", testReportVO.getSubmit_nm_k());
			
			map.put("ARG_SUBMIT_NM_E", testReportVO.getSubmit_nm_e());
			map.put("ARG_SUBMIT_DT", getExpDateString(testReportVO.getSubmit_dt()));
			map.put("ARG_LANG", testReportVO.getLang());
			map.put("ARG_RMK", testReportVO.getRmk());
			map.put("OUT_PARAM", "");
			
			dao.update("sdpe0010.procedure_updateTestReportHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			if(jsonList != null && jsonList.length() > 0) {
				JSONObject obj = new JSONObject();
				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);
					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
					map.put("ARG_REQ_NO", testReportVO.getReq_no());
					map.put("ARG_SEQ",Integer.toString(i));
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_LOT_NO", obj.getString("lot_no"));
					map.put("ARG_SU", obj.getString("su"));
					map.put("OUT_PARAM", "");
					
					dao.update("sdpe0010.procedure_updateTestReportSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
					//메일 발송 정보를 ArrayList에 저장
					map = new HashMap<String, Object>();
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_LOT_NO", obj.getString("lot_no"));
					map.put("OUT_PARAM", null);
					dao.update("sdpe0010.procedure_selectRcptTo", map);
					List<TestReportVO> li = (List<TestReportVO>) map.get("OUT_PARAM");
					
					if(li != null || li.size() > 0) {
						ArrayList li_to = new ArrayList<String>();
						
						for(int x = 0; x < li.size(); x++) {
							li_to.add(((TestReportVO) li.get(x)).getEmail());
						}
						
						emailVO = new EmailVO();
						emailVO.setFrom(testReportVO.getEmail());
						emailVO.setLi_to(li_to);
						emailVO.setSubject("[인터넷주문시스템] 시험성적서 작성 요청 ");
						
						String contents = "";
						contents += "\n" + "본 메일은 '대리점 주문시스템'에서 자동으로 발송되는 메일입니다.";
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "대리점 " + testReportVO.getCust_nm() + " 입니다.";
						contents += "\n" + "";
						contents += "\n" + "다음과 같이 시험성적서를 요청하오니 신속히 처리하여 주시기 바랍니다.";
						contents += "\n" + "";
						contents += "\n" + "요 청 일 : " + testReportVO.getReq_dt();
						contents += "\n" + "요청번호 : " + testReportVO.getReq_no();
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "제품코드 : " + obj.getString("item");
						contents += "\n" + "품 명 : " + obj.getString("description");
						contents += "\n" + "로트번호 : " + obj.getString("lot_no");
						contents += "\n" + "수 량 : " + obj.getString("su");
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "제 출 일 : " + testReportVO.getSubmit_dt();
						contents += "\n" + "작성언어 : ";
						if(testReportVO.getLang().equals("K")) {
							contents += "한글";
						} else if(testReportVO.getLang().equals("E")) {
							contents += "영문";
						} else {
							contents += "한글 + 영문";
						}
						contents += "\n" + "제출처(한글) : " + testReportVO.getSubmit_nm_k();
						contents += "\n" + "제출처(영문) : " + testReportVO.getSubmit_nm_e();
						contents += "\n" + "특기사항 : " + testReportVO.getRmk();
						contents += "\n" + "대리점 e-mail : " + testReportVO.getEmail();
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
			
			redirectAttr.addFlashAttribute("testReportVO", testReportVO);
			
			return "redirect:/sdpe001001d.do";
			
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
		
	}
	
	@RequestMapping(value = "sdpe001001u_update.do")		//시험성적서 수정 (db insert)
	public String sdpe001001u_update(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			@RequestParam(value="jsonList", required=false)JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//메일관련 변수
		List li_sendMail = new ArrayList<EmailVO>();
		EmailVO emailVO = new EmailVO();
		
		try {
			dao.startTransaction();
			
			//header 저장하기
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
			map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
			map.put("ARG_REQ_NO", testReportVO.getReq_no());
			map.put("ARG_CUST_CD", testReportVO.getCust_num());
			map.put("ARG_SUBMIT_NM_K", testReportVO.getSubmit_nm_k());
			
			map.put("ARG_SUBMIT_NM_E", testReportVO.getSubmit_nm_e());
			map.put("ARG_SUBMIT_DT", getExpDateString(testReportVO.getSubmit_dt()));
			map.put("ARG_LANG", testReportVO.getLang());
			map.put("ARG_RMK", testReportVO.getRmk());
			map.put("OUT_PARAM", null);
			
			dao.update("sdpe0010.procedure_updateTestReportHeader", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			//서브 목록 전체 delete 후 insert
			
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "delete");
			map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
			map.put("ARG_REQ_NO", testReportVO.getReq_no());
			map.put("OUT_PARAM", null);
			dao.update("sdpe0010.procedure_updateTestReportSub", map);
			
			if(!map.get("OUT_PARAM").equals("OK")) {
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			
			if(jsonList != null && jsonList.length() > 0) {
				JSONObject obj = new JSONObject();
				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);
					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
					map.put("ARG_REQ_NO", testReportVO.getReq_no());
					map.put("ARG_SEQ",Integer.toString(i));
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_LOT_NO", obj.getString("lot_no"));
					map.put("ARG_SU", obj.getString("su"));
					map.put("OUT_PARAM", null);
					
					dao.update("sdpe0010.procedure_updateTestReportSub", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
					//메일 발송 정보를 ArrayList에 저장
					map = new HashMap<String, Object>();
					map.put("ARG_ITEM_CD", obj.getString("item"));
					map.put("ARG_LOT_NO", obj.getString("lot_no"));
					map.put("OUT_PARAM", null);
					dao.update("sdpe0010.procedure_selectRcptTo", map);
					List<TestReportVO> li = (List<TestReportVO>) map.get("OUT_PARAM");
					
					if(li != null || li.size() > 0) {
						ArrayList li_to = new ArrayList<String>();
						
						for(int x = 0; x < li.size(); x++) {
							li_to.add(((TestReportVO) li.get(x)).getEmail());
						}
						
						emailVO = new EmailVO();
						emailVO.setFrom(testReportVO.getEmail());
						emailVO.setLi_to(li_to);
						emailVO.setSubject("[인터넷주문시스템] 시험성적서 작성 요청 ");
						
						String contents = "";
						contents += "\n" + "본 메일은 '대리점 주문시스템'에서 자동으로 발송되는 메일입니다.";
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "대리점 " + testReportVO.getCust_nm() + " 입니다.";
						contents += "\n" + "";
						contents += "\n" + "다음과 같이 시험성적서를 요청하오니 신속히 처리하여 주시기 바랍니다.";
						contents += "\n" + "";
						contents += "\n" + "요 청 일 : " + testReportVO.getReq_dt();
						contents += "\n" + "요청번호 : " + testReportVO.getReq_no();
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "제품코드 : " + obj.getString("item");
						contents += "\n" + "품 명 : " + obj.getString("description");
						contents += "\n" + "로트번호 : " + obj.getString("lot_no");
						contents += "\n" + "수 량 : " + obj.getString("su");
						contents += "\n" + "";
						contents += "\n" + "";
						contents += "\n" + "제 출 일 : " + testReportVO.getSubmit_dt();
						contents += "\n" + "작성언어 : ";
						if(testReportVO.getLang().equals("K")) {
							contents += "한글";
						} else if(testReportVO.getLang().equals("E")) {
							contents += "영문";
						} else {
							contents += "한글 + 영문";
						}
						contents += "\n" + "제출처(한글) : " + testReportVO.getSubmit_nm_k();
						contents += "\n" + "제출처(영문) : " + testReportVO.getSubmit_nm_e();
						contents += "\n" + "특기사항 : " + testReportVO.getRmk();
						contents += "\n" + "대리점 e-mail : " + testReportVO.getEmail();
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
			
			redirectAttr.addFlashAttribute("testReportVO", testReportVO);
			
			return "redirect:/sdpe001001d.do";
			
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	@RequestMapping(value = "sdpe001001d_delete.do")		//시험성적서 요청 취소(delete flag 처리)
	public String sdpe001001d_delete(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//header 삭제
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_REQ_DT", getExpDateString(testReportVO.getReq_dt()));
		map.put("ARG_REQ_NO", testReportVO.getReq_no());
		map.put("ARG_CUST_CD", testReportVO.getCust_num());
		map.put("OUT_PARAM", null);
		
		dao.update("sdpe0010.procedure_updateTestReportHeader", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		return "redirect:/sdpe001001l.do";
	}
}
