package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
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

import com.jebi.sdp.model.NprodReqHeaderVO;
import com.jebi.sdp.model.NprodReqSubVO;
import com.jebi.sdp.model.ProdReqHeaderVO;
import com.jebi.sdp.model.ProdReqSubVO;
import com.jebi.sdp.service.ProdReqService;

/**
 * 제조의뢰(sdpa004101*)와 신규제조의뢰(sdpa004001*) 화면.
 * 저장 프로시저 호출은 ProdReqService 가 담당한다.
 */
@Controller
public class Sdpa0040Controller {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0040Controller.class);

	/** 프로시저가 정상 처리되었을 때 OUT_PARAM 값 */
	private static final String OK = "OK";

	/** 오류 발생 시 이동할 화면 */
	private static final String ERROR_VIEW = "templates/error";

	@Autowired
	private ProdReqService prodReqService;

	// ---- 제조의뢰 ----

	@RequestMapping(value = "sdpa004001l.do")		//제조의뢰 목록 조회
	public String sdpa004001l(@ModelAttribute("prVO")ProdReqHeaderVO prVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("prodReqList", prodReqService.selectProdReqList(prVO));

		return "sdpa0040/sdpa004001l";
	}

	@RequestMapping(value = "sdpa004101d.do")		//제조의뢰 상세 조회
	public String sdpa004101d(@ModelAttribute("prVO")ProdReqHeaderVO prVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		prVO.setProduct_type("인터넷");

		addProdReqDetail(prVO, model);

		return "sdpa0040/sdpa004101d";
	}

	@RequestMapping(value = "sdpa004101u.{flag}.do")		//제조의뢰 작성 & 수정 화면 호출
	public String sdpa004101u(@ModelAttribute("prVO") ProdReqHeaderVO prVO, @PathVariable("flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);

		//판매구분 코드목록
		model.addAttribute("code10", prodReqService.selectCodeList("4069"));

		if(flag != null && flag.equals("update")) {
			prVO.setProduct_type("인터넷");
			addProdReqDetail(prVO, model);
		}

		return "sdpa0040/sdpa004101u";
	}

	/** 제조의뢰 헤더와 서브 품목을 화면에 담는다. */
	private void addProdReqDetail(ProdReqHeaderVO prVO, ModelMap model) throws Exception {
		ProdReqHeaderVO prodReqHeader = prodReqService.selectProdReqHeader(prVO);
		model.addAttribute("prodReqHeader", prodReqHeader);

		List<ProdReqSubVO> prodReqSubList = prodReqService.selectProdReqSubList(prVO);
		model.addAttribute("prodReqSubList", prodReqSubList);
	}

	@RequestMapping(value = "sdpa004101u_insert.do")		//제조의뢰 작성 (db insert)
	public String sdpa004101u_insert(@ModelAttribute("prVO") ProdReqHeaderVO prVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		try {
			//작성일 현재일자 셋팅
			prVO.setIlja(today());

			//transaction 시작
			prodReqService.startTransaction();
			prodReqService.startBatch();

			//전표번호 가져오기 (일반제조는 W2)
			prVO.setJeonpyo_no(prodReqService.selectJeonpyoNo(prVO.getWorkplace(), "W2", prVO.getIlja()));

			//header 입력
			String result = prodReqService.insertProdReqHeader(prVO);
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			//sub 입력
			for(int i=0; i < jsonList.length(); i++) {
				JSONObject obj = (JSONObject) jsonList.get(i);

				result = prodReqService.insertProdReqSub(prVO, i + 1, obj);
				if(!OK.equals(result)) {
					return rollbackToError(result);
				}
			}

			prodReqService.executeBatch();
			prodReqService.commit();

			redirectAttr.addFlashAttribute("prVO", prVO);

			return "redirect:/sdpa004101d.do";

		} catch (Exception e) {
			logger.error("제조의뢰 작성 중 오류", e);
			return ERROR_VIEW;

		} finally {
			prodReqService.endTransaction();
		}
	}

	@RequestMapping(value = "sdpa004101u_update.do")		//제조의뢰 수정 DB처리
	public String sdpa004101u_update(@ModelAttribute(value = "prVO")ProdReqHeaderVO prVO,
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			RedirectAttributes redirectAttr, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		try {
			//transaction 시작
			prodReqService.startTransaction();
			prodReqService.startBatch();

			//header 입력
			String result = prodReqService.updateProdReqHeader(prVO);
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			//sub 입력
			if(jsonList.length() > 0) {
				//기존 sub 품목 전체 삭제
				result = prodReqService.deleteProdReqSubAll(prVO);
				if(!OK.equals(result)) {
					return rollbackToError(result);
				}

				for(int i=0; i < jsonList.length(); i++) {
					JSONObject obj = (JSONObject) jsonList.get(i);

					result = prodReqService.insertProdReqSubOnUpdate(prVO, i + 1, obj);
					if(!OK.equals(result)) {
						return rollbackToError(result);
					}
				}
			}

			prodReqService.executeBatch();
			prodReqService.commit();

			redirectAttr.addFlashAttribute("prVO", prVO);
			return "redirect:/sdpa004101d.do";

		} catch(Exception e) {
			logger.error("제조의뢰 수정 중 오류", e);
			return ERROR_VIEW;

		} finally {
			prodReqService.endTransaction();
		}
	}

	@RequestMapping(value = "sdpa004101d_delete.do")		//제조의뢰 삭제
	public String sdpa004101d_delete(@ModelAttribute("prVO")ProdReqHeaderVO prVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		String result = prodReqService.deleteProdReqHeader(prVO);
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		redirectAttr.addFlashAttribute("prVO", prVO);

		return "redirect:/sdpa004001l.do";
	}

	// ---- 신규제조의뢰 ----

	@RequestMapping(value = "sdpa004001d.do")		//신규제조의뢰 상세 조회
	public String sdpa004001d(@ModelAttribute("nprVO")NprodReqHeaderVO nprVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		addNprodReqDetail(nprVO, model);

		return "sdpa0040/sdpa004001d";
	}

	@RequestMapping(value = "sdpa004001u.{flag}.do")		//신규 제조의뢰 작성 & 수정 화면 호출
	public String sdpa004001u(@ModelAttribute("nprVO") NprodReqHeaderVO nprVO, @PathVariable("flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);

		//배달구분 코드목록
		model.addAttribute("code10", prodReqService.selectCodeList("425"));

		if(flag != null && flag.equals("update")) {
			addNprodReqDetail(nprVO, model);
		}

		return "sdpa0040/sdpa004001u";
	}

	/** 신규제조의뢰 헤더와 서브(단건)를 화면에 담는다. */
	private void addNprodReqDetail(NprodReqHeaderVO nprVO, ModelMap model) throws Exception {
		NprodReqHeaderVO nprodReqHeader = prodReqService.selectNprodReqHeader(nprVO);
		model.addAttribute("nprodReqHeader", nprodReqHeader);

		NprodReqSubVO nprodReqSub = prodReqService.selectNprodReqSub(nprVO);
		model.addAttribute("nprodReqSub", nprodReqSub);
	}

	@RequestMapping(value="sdpa004001u_insert.do")		//신규 제조의뢰 작성(db insert)
	public String sdpa004001u_insert(@ModelAttribute("nprVO")NprodReqHeaderVO nprVO, @ModelAttribute("nprsVO")NprodReqSubVO nprsVO,
			RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		try {
			//작성일 현재일자 셋팅
			String today = today();
			nprVO.setIlja(today);
			nprsVO.setIlja(today);

			//transaction 시작
			prodReqService.startTransaction();
			prodReqService.startBatch();

			//전표번호 가져오기 (신규제조는 03)
			String jeonpyoNo = prodReqService.selectJeonpyoNo(nprVO.getWorkplace(), "03", nprVO.getIlja());
			nprVO.setJeonpyo_no(jeonpyoNo);
			nprsVO.setJeonpyo_no(jeonpyoNo);

			return saveNprodReq(nprVO, nprsVO, "insert", redirectAttr);

		} catch (Exception e) {
			logger.error("신규제조의뢰 작성 중 오류", e);
			return ERROR_VIEW;

		} finally {
			prodReqService.endTransaction();
		}
	}

	@RequestMapping(value="sdpa004001u_update.do")		//신규 제조의뢰 수정 DB처리
	public String sdpa004001u_update(@ModelAttribute("nprVO")NprodReqHeaderVO nprVO, @ModelAttribute("nprsVO")NprodReqSubVO nprsVO,
			RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		try {
			//transaction 시작
			prodReqService.startTransaction();
			prodReqService.startBatch();

			return saveNprodReq(nprVO, nprsVO, "update", redirectAttr);

		} catch (Exception e) {
			logger.error("신규제조의뢰 수정 중 오류", e);
			return ERROR_VIEW;

		} finally {
			prodReqService.endTransaction();
		}
	}

	/** 신규제조의뢰 헤더·서브 저장. flag 는 "insert" 또는 "update". */
	private String saveNprodReq(NprodReqHeaderVO nprVO, NprodReqSubVO nprsVO, String flag,
			RedirectAttributes redirectAttr) throws Exception {
		//header 입력
		String result = prodReqService.saveNprodReqHeader(nprVO, flag);
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		//서브 입력
		result = prodReqService.saveNprodReqSub(nprsVO, flag);
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		prodReqService.executeBatch();
		prodReqService.commit();

		redirectAttr.addFlashAttribute("nprVO", nprVO);

		return "redirect:/sdpa004001d.do";
	}

	@RequestMapping(value = "sdpa004001d_delete.do")		//신규제조의뢰 삭제
	public String sdpa004001d_delete(@ModelAttribute("nprVO")ProdReqHeaderVO nprVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		String result = prodReqService.deleteNprodReqHeader(nprVO);
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		redirectAttr.addFlashAttribute("nprVO", nprVO);

		return "redirect:/sdpa004001l.do";
	}

	// ---- 공통 ----

	/** 프로시저가 오류를 돌려주면 트랜잭션을 닫고 오류 화면으로 보낸다. */
	private String rollbackToError(String outParam) throws Exception {
		logger.error("제조의뢰 프로시저 오류: {}", outParam);
		prodReqService.endTransaction();
		return ERROR_VIEW;
	}

	/** 작성일에 넣는 현재 일자 (yyyy.MM.dd) */
	private static String today() {
		return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
	}
}
