package com.jebi.sdp.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpa0070Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0070Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpa007001l.do")
	public String selectList(@ModelAttribute("searchVO") ItemPriceVO searchVO
		, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
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
				daebun = "A,D,C,F,G,J,K,L,M,R,Z,B,E,S,H,N,P,U,T,I";
			}
			searchVO.setSearch_daebun(daebun);
		}
		
		//중분류 값 셋팅
		String jungbun = "";
		if(searchVO.getSearch_jungbun().length() < 1) {
//					CustomerVO customerVO = new CustomerVO();
//					jungbun += ((CustomerVO) dao.select("sdpf0040.select_user", customerVO)).getSet_item_group();
			
			//사용자 지정 검색 조건이 하나도 없는 경우 전체 선택을 기본으로 설정
			if(jungbun.equals("")) {
				jungbun = "A01,A02,A03,A04,A05,A06,A07,A08,A09,A10,A11,A12,A13,A14,A15,A16,A17,A99,B01,B02,B03,B04,B05,B06,B07"
						+ ",B08,B09,B10,B11,B12,B13,B14,B15,B16,B17,B18,B19,B20,B21,B22,B99,C01,C02,C03,C04,C05,C06,C07,C09,C10"
						+ ",C11,C12,C13,C14,C15,C16,C17,C99,D01,D02,D03,D04,D05,D06,D07,D08,D09,D10,D11,D12,D13,D14,D15,D16,D17"
						+ ",D18,D21,D31,D99,E01,E02,E03,E04,E05,E06,E07,E08,E09,E10,E11,E99,F01,F02,F03,F04,F05,F06,F07,F99,G01"
						+ ",G02,G03,G04,G05,G06,G07,G08,G09,G10,G11,G99,H01,H02,H03,H04,H05,H06,H99,I01,I02,I03,I04,I05,I06,I07"
						+ ",I08,I09,I10,I21,I22,I23,I24,I25,I99,J01,J02,J03,J04,J05,J06,J07,J08,J99,K01,K02,K03,K04,K05,K06,K99"
						+ ",L01,L02,L03,L04,L05,L06,L07,L08,L09,L10,L11,L12,L13,L99,M01,M02,M03,M04,M05,M06,M07,M08,M09,M13,M99"
						+ ",N01,N02,N08,N99,P01,P99,R01,R02,R03,R04,R05,R06,R07,R08,R09,R10,R99,S01,S03,S04,S05,S06,S07,S08,S09"
						+ ",S10,S99,T01,T02,T99,U01,U02,U03,U04,U05,U99,Z99";
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
		map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", searchVO.getCust_num());
		
		if(searchVO.getSearchDiv().equals("item_code")) {
			map.put("ARG_ITEM_CD", searchVO.getSearchText().trim());
			map.put("ARG_ITEM_NM", null);
			
		} else if(searchVO.getSearchDiv().equals("description")) {
			map.put("ARG_ITEM_CD", null);
			map.put("ARG_ITEM_NM", searchVO.getSearchText().trim());
			
		} else {
			map.put("ARG_ITEM_CD", null);
			map.put("ARG_ITEM_NM", null);
		}
		
		map.put("ARG_BIZ_AREA_CD", searchVO.getWorkplace());
//		map.put("ARG_OPT", itemPriceVO.getSearchRadio_01() == "" ? "1" : itemPriceVO.getSearchRadio_01());
		map.put("ARG_DAEBUN", searchVO.getSearch_daebun());
		map.put("ARG_JUNGBUN", searchVO.getSearch_jungbun());
		map.put("ARG_CURRENT_PAGE", searchVO.getPage_current());
		map.put("ARG_COUNTPER_PAGE", searchVO.getPage_countPer());
		map.put("ARG_TOTAL_CNT", null);
		map.put("OUT_PARAM", null);
		
//		dao.update("sdpa0070.procedure_selectItemPrice", map);
		dao.update("sdpa0070.procedure_selectItemPrice_all", map);
		model.addAttribute("priceList", map.get("OUT_PARAM"));
		
		searchVO.setPage_totalCnt((String) map.get("ARG_TOTAL_CNT")); 
		
		model.addAttribute("searchVO", searchVO);
		return "sdpa0070/sdpa007001l";
	}
}
