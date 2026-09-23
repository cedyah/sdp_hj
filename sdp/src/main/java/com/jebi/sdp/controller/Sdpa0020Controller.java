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

import com.jebi.sdp.model.CoItemVO;
import com.jebi.sdp.model.CoVO;
import com.jebi.sdp.model.ItemVO;
import com.jebi.sdp.service.CodeService;
import com.jebi.sdp.service.OrderService;

/**
 * 주문서(sdpa0020) 화면.
 * 저장 프로시저 호출은 OrderService 가 담당한다.
 */
@Controller
public class Sdpa0020Controller {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0020Controller.class);

	/** 프로시저가 정상 처리되었을 때 OUT_PARAM 값 */
	private static final String OK = "OK";

	/** 오류 발생 시 이동할 화면 */
	private static final String ERROR_VIEW = "templates/error";

	@Autowired
	private OrderService orderService;

	@Autowired
	private CodeService codeService;

	@RequestMapping(value = "sdpa002001l.do")		//주문서 목록 조회
	public String selectCo(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("coList", orderService.selectOrderList(coVO));

		return "sdpa0020/sdpa002001l";
	}

	@RequestMapping(value = "sdpa002001d.do")		//주문서 상세 조회
	public String detailCo(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//header 정보
		List<CoVO> coList = orderService.selectOrderHeader(coVO);

		if (coList == null || coList.isEmpty()) {
			model.addAttribute("message", "해당 정보가 없습니다.");
			return "templates/errorPage";
		}
		model.addAttribute("co", coList.get(0));

		//sub 정보
		model.addAttribute("coItemList", orderService.selectOrderSubList(coVO));

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

		//종합 포인트 정보
		model.addAttribute("customerVO", orderService.selectPointInfo(coVO));

		//장바구니에서 넘어 왔는지 여부를 다음화면으로 넘김
		model.addAttribute("pageCheck", pageCheck);

		model.addAttribute("code10", codeService.selectCodeList("4020"));		//배달구분
		model.addAttribute("code11", codeService.selectCodeList("4069"));		//판매구분
		model.addAttribute("code4900", codeService.selectCodeList("4900"));		//화폐코드

		if("insert".equals(flag)) {
			//화면에서 넘겨준 아이템들이 있을경우 JSONarray를 ArrayList로 변환하여 전달
			if(jsonList != null && jsonList.length() > 0) {
				model.addAttribute("coItemList", toItemList(jsonList));
			}

		} else if("update".equals(flag)) {
			//header 정보
			model.addAttribute("co", orderService.selectOrderHeader(coVO).get(0));

			//sub 정보
			model.addAttribute("coItemList", orderService.selectOrderSubList(coVO));
		}

		return "sdpa0020/sdpa002001u";
	}

	/** 장바구니 등에서 넘어온 품목 JSON 을 화면에 뿌릴 목록으로 바꾼다. */
	private static List<ItemVO> toItemList(JSONArray jsonList) throws Exception {
		List<ItemVO> coItemList = new ArrayList<ItemVO>();

		for(int i=0; i < jsonList.length(); i++) {
			JSONObject obj = (JSONObject) jsonList.get(i);

			CoItemVO item = new CoItemVO();
			item.setJepum_code(obj.getString("item"));
			item.setPummyeong(obj.getString("description"));
			item.setPanmae_danwi_a(obj.getString("qty_allocjob"));
			item.setPanmae_danwi_b(obj.getString("u_m"));
			item.setPanmae_sulyang(obj.getString("qty_on_hand01"));
			item.setBo_sulyang("0");
			coItemList.add(item);
		}

		return coItemList;
	}

	@RequestMapping(value = "sdpa002001u_insert.do")		//주문서 등록 (db insert)
	public String sdpa002001u_insert(@ModelAttribute("coVO")CoVO coVO, RedirectAttributes redirectAttr,
			@RequestParam(value="pageCheck", required=false) String pageCheck,
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		List<String> li_delBasket = new ArrayList<String>();		//장바구니 삭제용 array

		//작성일 현재일자 셋팅
		coVO.setIlja(today());

		try {
			//transaction 시작
			orderService.startTransaction();

			//전표번호 가져오기
			coVO.setJeonpyo_no(orderService.selectJeonpyoNo(coVO.getWorkplace(), coVO.getIlja()));

			//header 입력
			String result = orderService.insertOrderHeader(coVO);
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			//sub 입력
			for(int i=0; i < jsonList.length(); i++) {
				JSONObject obj = (JSONObject) jsonList.get(i);

				result = orderService.insertOrderSub(coVO, i + 1, obj);
				if(!OK.equals(result)) {
					return rollbackToError(result);
				}

				//장바구니에서 주문서작성의 경우 array에 추가해서 마지막에 한번에 장바구니 품목들을 삭제
				if(pageCheck != null && pageCheck.equals("Y")) {
					li_delBasket.add(obj.getString("item") + obj.getString("qty_allocjob") + obj.getString("u_m"));
				}
			}

			//장바구니에서 주문작성으로 넘어온 경우 장바구니에서 저장된 제품 삭제
			if(pageCheck != null && pageCheck.equals("Y")) {
				orderService.deleteBasketItems(li_delBasket);
			}

			orderService.commit();
			orderService.endTransaction();

			redirectAttr.addFlashAttribute("coVO", coVO);

			return "redirect:/sdpa002001d.do";

		} catch(Exception e) {
			logger.error("주문서 작성 중 오류", e);
			return ERROR_VIEW;

		} finally {
			orderService.endTransaction();
		}
	}

	@RequestMapping(value = "sdpa002001u_update.do")		//주문서 수정 (db update)
	public String sdpa002001u_update(@ModelAttribute("coVO")CoVO coVO, RedirectAttributes redirectAttr,
			@RequestParam(value="pageCheck", required=false) String pageCheck,
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		try {
			//transaction 시작
			orderService.startTransaction();
			orderService.startBatch();

			//header 입력
			String result = orderService.updateOrderHeader(coVO);
			if(!OK.equals(result)) {
				return rollbackToError(result);
			}

			//sub 입력
			if(jsonList.length() > 0) {
				//기존 주문서의 품목 전체 삭제
				result = orderService.deleteOrderSubAll(coVO);
				if(!OK.equals(result)) {
					return rollbackToError(result);
				}

				//수정페이지에서 넘어온 품목들 다시 insert
				for(int i=0; i < jsonList.length(); i++) {
					JSONObject obj = (JSONObject) jsonList.get(i);

					result = orderService.insertOrderSubOnUpdate(coVO, i + 1, obj);
					if(!OK.equals(result)) {
						return rollbackToError(result);
					}
				}
			}

			orderService.executeBatch();
			orderService.commit();
			orderService.endTransaction();

			redirectAttr.addFlashAttribute("coVO", coVO);

			return "redirect:/sdpa002001d.do";

		} catch(Exception e) {
			logger.error("주문서 수정 중 오류", e);
			return ERROR_VIEW;

		} finally {
			orderService.endTransaction();
		}
	}

	@RequestMapping(value = "sdpa002001d_delete.do")		//주문서 삭제
	public String cancelCo(@ModelAttribute("coVO")CoVO coVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		String result = orderService.deleteOrderHeader(coVO);
		if(!OK.equals(result)) {
			return rollbackToError(result);
		}

		return "redirect:/sdpa002001l.do";
	}

	/** 프로시저가 오류를 돌려주면 트랜잭션을 닫고 오류 화면으로 보낸다. */
	private String rollbackToError(String outParam) throws Exception {
		logger.error("주문서 프로시저 오류: {}", outParam);
		orderService.endTransaction();
		return ERROR_VIEW;
	}

	/** 작성일에 넣는 현재 일자 (yyyy.MM.dd) */
	private static String today() {
		return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
	}
}
