package com.jebi.sdp.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;

@Controller
public class Sdpf0020Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpf0020Controller.class);

	@Autowired
	private CmmnDao dao;

	@RequestMapping(value = "sdpf002001l.do") // 장바구니 목록 조회
	public String selectList(@ModelAttribute("basketVO") BasketItemVO basketVO, HttpServletRequest request,
			ModelMap model, Locale locale) throws Exception {

		// 장바구니 목록
		List basketList = dao.selectList("sdpf0020.select_basketItemList", basketVO);
		// 관심품목 그룹
		List<FavoriteGroupVO> favGroupList = (List<FavoriteGroupVO>) dao.selectList("sdpf0010.select_favoriteGroupList",
				new FavoriteGroupVO());

		model.addAttribute("favGroupList", favGroupList);
		model.addAttribute("basketList", basketList);

		return "sdpf0020/sdpf002001l";
	}

	@RequestMapping(value = "sdpf002001l_u.do") // 장바구니 저장
	public String updateBasket(@RequestParam(value = "hid_item", required = false) List hid_item,
			@RequestParam(value = "input_qty", required = false) List input_qty, HttpServletRequest request,
			ModelMap model, Locale locale) throws Exception {

		BasketItemVO vo = new BasketItemVO();

		try {
			dao.startTransaction();
			dao.startBatch();
			
			for (int i = 0; i < hid_item.size(); i++) {
				vo.setItem((String) hid_item.get(i));
				vo.setQty_basket((String) input_qty.get(i));
				
				dao.update("sdpf0020.update_basket", vo);
			}
			
			dao.executeBatch();
			dao.commit();
			dao.endTransaction();
			
			return "redirect:/sdpf002001l.do";
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}

	@RequestMapping(value = "ajaxAddBasket.do") // 장바구니 추가
	public ModelAndView addBasket(@RequestParam JSONArray itemList, HttpServletRequest request, ModelMap model)
			throws Exception {

		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();

		try {
			ItemVO item = new ItemVO();
			
			dao.startTransaction();
			dao.startBatch();
			for (int i = 0; i < itemList.length(); i++) {
				JSONObject obj = itemList.getJSONObject(i);
				item.setItem(nvl(obj.getString("item")));
				item.setDescription(nvl(obj.getString("description")));
				item.setQty_allocjob(nvl(obj.getString("qty_allocjob")));
				item.setU_m(nvl(obj.getString("u_m")));
				item.setQty_input(nvl(obj.getString("qty_basket")));
				
				dao.insert("sdpf0020.merge_addBasket", item);
			}
			dao.executeBatch();
			dao.commit();
			dao.endTransaction();
			
			mv.setView(jsonView);
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			dao.endTransaction();
			return mv;
		}
	}

	@RequestMapping(value = "ajaxDeleteBasket.do") // 장바구니 삭제
	public ModelAndView deleteBasket(@RequestParam("paramList") List paramList, HttpServletRequest request,
			ModelMap model) throws Exception {

		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		BasketItemVO vo = new BasketItemVO();
		vo.setParamList(paramList);

		dao.delete("sdpf0020.delete_delBasket", vo);

		mv.setView(jsonView);
		return mv;
	}
}
