package com.jebi.sdp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpa0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpa001001l.{flag}.do")		//제품검색
	public String sdpa001001l(@ModelAttribute("SearchItemVO")SearchItemVO searchVO,
			@PathVariable("flag") String flag, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);
		
		//관심품목 그룹
		List<FavoriteGroupVO> favGroupList = (List<FavoriteGroupVO>) dao.selectList("sdpf0010.select_favoriteGroupList", new FavoriteGroupVO());
		model.addAttribute("favGroupList", favGroupList);

		//제품군 코드 목록 불러오기
		CodeVO codeVO = new CodeVO();
		List<CodeVO> search_daebun = (List<CodeVO>) dao.selectList("common.select_itemType", codeVO);
		model.addAttribute("search_daebun", search_daebun);

		//제품군 코드 목록 불러오기
		codeVO = new CodeVO();
		List<CodeVO> search_jungbun = (List<CodeVO>) dao.selectList("common.select_itemTypeSub", codeVO);
		model.addAttribute("search_jungbun", search_jungbun);
		
		//대분류 값 셋팅
		String daebun = "";
		if(searchVO.getSearch_daebun().length() < 1) {
			CustomerVO customerVO = new CustomerVO();
			daebun += ((CustomerVO) dao.select("sdpf0040.select_user", customerVO)).getSet_item_group();
			
			//사용자 지정 검색 조건이 하나도 없는 경우 전체 선택을 기본으로 설정
			if(daebun.equals("")) {
				daebun = "11,99";
			}
			searchVO.setSearch_daebun(daebun);
		}
		
		//중분류 값 셋팅
		String jungbun = "";
		if(searchVO.getSearch_jungbun().length() < 1) {
//			CustomerVO customerVO = new CustomerVO();
//			jungbun += ((CustomerVO) dao.select("sdpf0040.select_user", customerVO)).getSet_item_group();
			
			//사용자 지정 검색 조건이 하나도 없는 경우 전체 선택을 기본으로 설정
			if(jungbun.equals("")) {
				jungbun = "11A1,11A2,11A3,11A9,99Z1";
				String[] li_jungbun = jungbun.split(",");
				jungbun = "";
				for(int i=0; i < li_jungbun.length; i++) {
					if(searchVO.getSearch_daebun().indexOf(li_jungbun[i].substring(0, 1)) >= 0) {
						jungbun += li_jungbun[i] + ",";
					}
				}
				
				jungbun = jungbun.substring(0, jungbun.length()-1);
			}
			searchVO.setSearch_jungbun(jungbun);
		}

		
		//화면에서 가져온 값들을 map에 setting
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", searchVO.getWorkplace());
		map.put("ARG_CUST_CD", searchVO.getCust_num().trim());
		
		if(searchVO.getSearchDiv().equals("item_code")) {
			map.put("ARG_ITEM_CD", searchVO.getSearchText().trim());
			map.put("ARG_ITEM_NM", null);
			
		} else if(searchVO.getSearchDiv().equals("description")) {
			map.put("ARG_ITEM_CD", null);
			map.put("ARG_ITEM_NM", searchVO.getSearchText().trim().replaceAll(" ", "%"));
			
		} else {
			map.put("ARG_ITEM_CD", null);
			map.put("ARG_ITEM_NM", null);
		}
		map.put("ARG_ITEM_OPTION", "ALL");
		
		map.put("ARG_CUST_OPTION", searchVO.getCust_type());
		map.put("ARG_DANWI_FROM", searchVO.getSearchInput_01().equals("") ? "0" : searchVO.getSearchInput_01());
		map.put("ARG_DANWI_TO", searchVO.getSearchInput_02().equals("") ? "9999" : searchVO.getSearchInput_02());
		map.put("ARG_DAEBUN",searchVO.getSearch_daebun());
		map.put("ARG_PGM_OPTION", searchVO.getSearch_pgmOption());
		map.put("ARG_JUNGBUN",searchVO.getSearch_jungbun());
		map.put("ARG_CURRENT_PAGE", searchVO.getPage_current());
		map.put("ARG_COUNTPER_PAGE", searchVO.getPage_countPer());
		map.put("ARG_TOTAL_CNT", null);
		
		map.put("OUT_PARAM", null);
		
		//제품목록  조회 프로시저 호출
		dao.update("sdpa0010.procedure_selectItemList_menu", map);
//		if(flag.equals("standard")) {
//			dao.update("sdpa0010.procedure_selectItemList", map);
//		} else if(flag.equals("detail")) {
//			dao.update("sdpa0010.procedure_selectItemList_detail", map);
//		}
		List<ItemVO> itemList = (List<ItemVO>) map.get("OUT_PARAM");
		model.addAttribute("itemList", itemList);
		
		searchVO.setPage_totalCnt((String) map.get("ARG_TOTAL_CNT"));
		model.addAttribute("searchVO", searchVO);
		return "sdpa0010/sdpa001001l";
	}
	
	@RequestMapping(value="ajaxRefreshQty.do")		//제품 목록에서 재고수량 갱신
	public ModelAndView ajaxRefreshQty(@RequestParam(value="paramList",required=false) List paramList
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		if(null != paramList && paramList.size() > 0) {
			List<ItemVO> qtyList = new ArrayList<ItemVO>();
			
			SearchItemVO searchVO = new SearchItemVO();
			searchVO.setParamList(paramList);

			qtyList = (List<ItemVO>)dao.selectList("sdpa0010.select_qty", searchVO);
			
			jsonView.addStaticAttribute("qtyList", qtyList);
			
		}
 		mv.setView(jsonView); 
		
		return mv;
	}
	
	@RequestMapping(value="ajaxRefreshQtyDetail.do")		//제품 목록에서 재고수량 갱신
	public ModelAndView ajaxRefreshQtyDetail(@RequestParam(value="paramList",required=false) List paramList
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		if(null != paramList && paramList.size() > 0) {
			List<ItemDetailVO> qtyList = new ArrayList<ItemDetailVO>();
			
			SearchItemVO searchVO = new SearchItemVO();
			searchVO.setParamList(paramList);
			
			qtyList = (List<ItemDetailVO>)dao.selectList("sdpa0010.select_qtyDetail", searchVO);
			
			jsonView.addStaticAttribute("qtyList", qtyList);
			
		}
		mv.setView(jsonView); 
		
		return mv;
	}
	
	@RequestMapping(value="sdpa001001p.do")		//시험성적서용 제품검색 팝업
	public ModelAndView sdpa001001p(@RequestParam(value="paramList",required=false) List paramList
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		if(null != paramList && paramList.size() > 0) {
			List<ItemDetailVO> qtyList = new ArrayList<ItemDetailVO>();
			
			SearchItemVO searchVO = new SearchItemVO();
			searchVO.setParamList(paramList);
			
			qtyList = (List<ItemDetailVO>)dao.selectList("sdpa0010.select_qtyDetail", searchVO);
			
			jsonView.addStaticAttribute("qtyList", qtyList);
			
		}
		mv.setView(jsonView); 
		
		return mv;
	}
	
	
}
