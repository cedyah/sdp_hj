package com.jebi.sdp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jebi.sdp.common.MailService;
import com.jebi.sdp.model.EmailVO;
import com.jebi.sdp.model.TestReportVO;
import com.jebi.sdp.service.TestReportService;

/**
 * 시험성적서(sdpe0010) 화면.
 * 저장 프로시저 호출은 TestReportService 가 담당한다.
 */
@Controller
public class Sdpe0010Controller {
	private static final Logger logger = LoggerFactory.getLogger(Sdpe0010Controller.class);

	/** 프로시저가 정상 처리되었을 때 OUT_PARAM 값 */
	private static final String OK = "OK";

	/** 오류 발생 시 이동할 화면 */
	private static final String ERROR_VIEW = "templates/error";

	@Autowired
	private MailService mailService;

	@Autowired
	private TestReportService testReportService;

	@RequestMapping(value = "sdpe001001l.do")		//시험성적서 조회
	public String sdpe001001l(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("testReportList", testReportService.selectTestReportList(testReportVO));

		return "sdpe0010/sdpe001001l";
	}

	@RequestMapping(value = "sdpe001001d.do")		//시험성적서 상세조회
	public String sdpe001001d(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		addDetail(testReportVO, model);

		return "sdpe0010/sdpe001001d";
	}

	@RequestMapping(value = "sdpe001001u.{flag}.do")		//시험성적서 등록&수정화면 호출
	public String sdpe001001u(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			@PathVariable String flag, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);		//flag 값을 다음 화면으로 넘김

		if("update".equals(flag)) {				//기존 시험요청서 수정화면 (신규등록화면은 불러올 내용이 없다)
			addDetail(testReportVO, model);
		}

		return "sdpe0010/sdpe001001u";
	}

	/** 헤더와 서브 품목을 화면에 담는다. */
	private void addDetail(TestReportVO testReportVO, ModelMap model) throws Exception {
		model.addAttribute("testReportHeader", testReportService.selectTestReportHeader(testReportVO));
		model.addAttribute("testReportSub", testReportService.selectTestReportSubList(testReportVO));
	}

	@RequestMapping(value = "sdpe001001u_insert.do")		//시험성적서 신규 작성 (db insert)
	public String sdpe001001u_insert(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		try {
			testReportService.startTransaction();

			//전표번호 가져오기
			testReportVO.setReq_no(testReportService.selectReqNo(testReportVO));

			//header 저장하기
			String result = testReportService.saveHeader(testReportVO, "insert");
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			return saveSubItemsAndSendMail(testReportVO, jsonList, redirectAttr);

		} catch(Exception e) {
			logger.error("시험성적서 작성 중 오류", e);
			return ERROR_VIEW;

		} finally {
			testReportService.endTransaction();
		}
	}

	@RequestMapping(value = "sdpe001001u_update.do")		//시험성적서 수정 (db update)
	public String sdpe001001u_update(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			@RequestParam(value="jsonList", required=false)JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		try {
			testReportService.startTransaction();

			//header 저장하기
			String result = testReportService.saveHeader(testReportVO, "update");
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			//서브 목록 전체 delete 후 insert
			result = testReportService.deleteSubAll(testReportVO);
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			return saveSubItemsAndSendMail(testReportVO, jsonList, redirectAttr);

		} catch(Exception e) {
			logger.error("시험성적서 수정 중 오류", e);
			return ERROR_VIEW;

		} finally {
			testReportService.endTransaction();
		}
	}

	/**
	 * 서브 품목을 저장하면서 품목마다 담당자에게 보낼 메일을 모았다가, 저장이 모두 끝나면 일괄 발송하고 커밋한다.
	 * 작성과 수정이 같은 흐름이다.
	 */
	private String saveSubItemsAndSendMail(TestReportVO testReportVO, JSONArray jsonList,
			RedirectAttributes redirectAttr) throws Exception {
		List<EmailVO> li_sendMail = new ArrayList<EmailVO>();

		if(jsonList != null && jsonList.length() > 0) {
			for(int i=0; i < jsonList.length(); i++) {
				JSONObject obj = (JSONObject) jsonList.get(i);

				String result = testReportService.insertSub(testReportVO, i, obj);
				if(!OK.equals(result)) {
					return rollbackToError(result);
				}

				//메일 발송 정보를 목록에 저장
				List<TestReportVO> li = testReportService.selectRcptTo(obj.getString("item"), obj.getString("lot_no"));
				if(li != null) {
					li_sendMail.add(requestMail(testReportVO, obj, li));
				}
			}
		}

		//li에 저장해 놓은 이메일 객체들을 일괄 발송후 커밋
		for(int i=0; i < li_sendMail.size(); i ++) {
			mailService.sendMail(li_sendMail.get(i));
		}

		testReportService.commit();
		testReportService.endTransaction();

		redirectAttr.addFlashAttribute("testReportVO", testReportVO);

		return "redirect:/sdpe001001d.do";
	}

	/** 담당자에게 보낼 시험성적서 작성 요청 메일 */
	private static EmailVO requestMail(TestReportVO testReportVO, JSONObject obj, List<TestReportVO> rcptList)
			throws Exception {
		List<String> li_to = new ArrayList<String>();
		for(int x = 0; x < rcptList.size(); x++) {
			li_to.add(rcptList.get(x).getEmail());
		}

		EmailVO emailVO = new EmailVO();
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

		return emailVO;
	}

	@RequestMapping(value = "sdpe001001d_delete.do")		//시험성적서 요청 취소(delete flag 처리)
	public String sdpe001001d_delete(@ModelAttribute("testReportVO")TestReportVO testReportVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		String result = testReportService.deleteHeader(testReportVO);
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		return "redirect:/sdpe001001l.do";
	}

	/** 프로시저가 오류를 돌려주면 트랜잭션을 닫고 오류 화면으로 보낸다. */
	private String rollbackToError(String outParam) throws Exception {
		logger.error("시험성적서 프로시저 오류: {}", outParam);
		testReportService.endTransaction();
		return ERROR_VIEW;
	}
}
