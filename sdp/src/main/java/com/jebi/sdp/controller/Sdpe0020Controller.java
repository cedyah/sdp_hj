package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.jebi.sdp.model.MsdsHeaderVO;
import com.jebi.sdp.model.MsdsSubVO;
import com.jebi.sdp.service.MsdsService;

/**
 * MSDS(sdpe0020) 화면.
 * 저장 프로시저 호출은 MsdsService 가 담당한다.
 */
@Controller
public class Sdpe0020Controller {
	private static final Logger logger = LoggerFactory.getLogger(Sdpe0020Controller.class);

	/** 프로시저가 정상 처리되었을 때 OUT_PARAM 값 */
	private static final String OK = "OK";

	/** 오류 발생 시 이동할 화면 */
	private static final String ERROR_VIEW = "templates/error";

	@Autowired
	private MailService mailService;

	@Autowired
	private MsdsService msdsService;

	@RequestMapping(value = "sdpe002001l.do")		//msds 목록 조회
	public String sdpe002001l(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("msdsList", msdsService.selectMsdsList(msdsHVO));

		return "sdpe0020/sdpe002001l";
	}

	@RequestMapping(value = "sdpe002001d.do")		//msds 상세조회
	public String sdpe002001d(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		addDetail(msdsHVO, model);

		return "sdpe0020/sdpe002001d";
	}

	@RequestMapping(value = "sdpe002001u.{flag}.do")		//MSDS 등록&수정화면 호출
	public String sdpe002001u(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			@PathVariable String flag, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);		//flag 값을 다음 화면으로 넘김

		if("update".equals(flag)) {				//기존 요청 수정화면 (신규등록화면은 불러올 내용이 없다)
			addDetail(msdsHVO, model);
		}

		return "sdpe0020/sdpe002001u";
	}

	/** 헤더와 서브 품목을 화면에 담는다. */
	private void addDetail(MsdsHeaderVO msdsHVO, ModelMap model) throws Exception {
		model.addAttribute("headerVO", msdsService.selectMsdsHeader(msdsHVO));
		model.addAttribute("subList", msdsService.selectMsdsSubList(msdsHVO));
	}

	@RequestMapping(value = "sdpe002001u_insert.do")		//msds 신규 작성 (db insert)
	public String sdpe002001u_insert(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		//작성일 현재일자 셋팅
		msdsHVO.setReq_dt(today());

		try {
			//transaction 시작
			msdsService.startTransaction();

			//전표번호 가져오기
			msdsHVO.setReq_no(msdsService.selectReqNo(msdsHVO));

			//header 입력
			String result = msdsService.saveHeader(msdsHVO, "insert");
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			return saveSubItemsAndSendMail(msdsHVO, jsonList, redirectAttr);

		} catch(Exception e) {
			logger.error("MSDS 작성 중 오류", e);
			return ERROR_VIEW;

		} finally {
			msdsService.endTransaction();
		}
	}

	@RequestMapping(value = "sdpe002001u_update.do")		//msds 수정 (db update)
	public String sdpe001001u_update(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			RedirectAttributes redirectAttr, @RequestParam(value="jsonList", required=false)JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		//작성일 현재일자 셋팅 (수정할 때도 요청일을 오늘로 다시 채운다)
		msdsHVO.setReq_dt(today());

		try {
			//transaction 시작
			msdsService.startTransaction();

			//header 입력
			String result = msdsService.saveHeader(msdsHVO, "update");
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			//sub 전체 삭제
			result = msdsService.deleteSubAll(msdsHVO);
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			return saveSubItemsAndSendMail(msdsHVO, jsonList, redirectAttr);

		} catch(Exception e) {
			logger.error("MSDS 수정 중 오류", e);
			return ERROR_VIEW;

		} finally {
			msdsService.endTransaction();
		}
	}

	/**
	 * 서브 품목을 저장하면서 품목마다 담당자에게 보낼 메일을 모았다가, 저장이 모두 끝나면 일괄 발송하고 커밋한다.
	 * 작성과 수정이 같은 흐름이다.
	 */
	private String saveSubItemsAndSendMail(MsdsHeaderVO msdsHVO, JSONArray jsonList,
			RedirectAttributes redirectAttr) throws Exception {
		List<EmailVO> li_sendMail = new ArrayList<EmailVO>();

		if(jsonList.length() > 0) {
			for(int i=0; i < jsonList.length(); i++) {
				JSONObject obj = (JSONObject) jsonList.get(i);

				String result = msdsService.insertSub(msdsHVO, i, obj);
				if(!OK.equals(result)) {
					return rollbackToError(result);
				}

				//메일 발송 정보를 목록에 저장
				List<MsdsSubVO> li = msdsService.selectEmail(obj.getString("item"));
				if(li != null) {
					li_sendMail.add(requestMail(msdsHVO, obj, li));
				}
			}
		}

		//li에 저장해 놓은 이메일 객체들을 일괄 발송후 커밋
		for(int i=0; i < li_sendMail.size(); i ++) {
			mailService.sendMail(li_sendMail.get(i));
		}

		msdsService.commit();
		msdsService.endTransaction();

		redirectAttr.addFlashAttribute("msdsHVO", msdsHVO);

		return "redirect:/sdpe002001d.do";
	}

	/** 담당자에게 보낼 MSDS 작성 요청 메일 */
	private static EmailVO requestMail(MsdsHeaderVO msdsHVO, JSONObject obj, List<MsdsSubVO> rcptList)
			throws Exception {
		List<String> arLi = new ArrayList<String>();
		for(int x = 0; x < rcptList.size(); x++) {
			arLi.add(rcptList.get(x).getEmail());
		}

		EmailVO emailVO = new EmailVO();
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

		return emailVO;
	}

	@RequestMapping(value = "sdpe002001d_delete.do")		//msds 요청 취소(delete flag 처리)
	public String sdpe001001d_delete(@ModelAttribute("msdsHVO")MsdsHeaderVO msdsHVO,
			RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		String result = msdsService.saveHeader(msdsHVO, "delete");
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		redirectAttr.addFlashAttribute("msdsHVO", msdsHVO);

		return "redirect:/sdpe002001l.do";
	}

	/** 프로시저가 오류를 돌려주면 트랜잭션을 닫고 오류 화면으로 보낸다. */
	private String rollbackToError(String outParam) throws Exception {
		logger.error("MSDS 프로시저 오류: {}", outParam);
		msdsService.endTransaction();
		return ERROR_VIEW;
	}

	/** 요청일에 넣는 현재 일자 (yyyyMMdd) */
	private static String today() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
}
