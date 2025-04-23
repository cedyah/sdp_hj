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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpf0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpf0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpf001001l.do")	//관심품목그룹 목록조회
	public String selectList(@ModelAttribute("favGroupVO") FavoriteGroupVO favGroupVO
		, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		boolean selectFavGroupCheck = true;
		
		List<FavoriteGroupVO> favGroupList = (List<FavoriteGroupVO>) dao.selectList("sdpf0010.select_favoriteGroupList", favGroupVO);
		
		if(favGroupList.size() < 1) {		//최초 관심품목관리 화면 접속 시 기본그룹 생성
			favGroupVO.setGroup_seq("0");
			favGroupVO.setGroup_cdnm("기본그룹");
			dao.insert("sdpf0010.insert_favGroup", favGroupVO);
			favGroupList = (List<FavoriteGroupVO>) dao.selectList("sdpf0010.select_favoriteGroupList", favGroupVO);
		}
		
		//화면에서 선택된 그룹이 삭제로 없어졌을 경우 선택 그룹을 기본그룹으로 변경해주기 위해서 적용.
		for(int i=0; i< favGroupList.size(); i++) {
			FavoriteGroupVO vo = (FavoriteGroupVO) favGroupList.get(i);
			
			if(vo.getGroup_cd().equals(favGroupVO.getSelect_group_cd())) {
				selectFavGroupCheck = false;
			}
		}
		
		if(selectFavGroupCheck) {
			favGroupVO.setSelect_group_cd("");		//선택된 관심그룹을을 비어있는 값으로 지정하여 기본그룹이 보이도록 지정
		}
		FavoriteItemVO favItemVO = new FavoriteItemVO();
		favItemVO.setGroup_cd(favGroupVO.getSelect_group_cd());
		List<FavoriteItemVO> favItemList = (List<FavoriteItemVO>) dao.selectList("sdpf0010.select_favoriteItemList", favItemVO);
		
		model.addAttribute("favGroupList", favGroupList);
		model.addAttribute("favItemList", favItemList);
		
		return "sdpf0010/sdpf001001l";
	}
	
	
	@RequestMapping(value="ajaxAddFavGroup.do")		//관심품목 그룹 추가
	public ModelAndView ajaxAddFavGroup(@ModelAttribute("favGroupVO") FavoriteGroupVO favGroupVO
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		String group_cd = (String) dao.insert_return("sdpf0010.insert_favGroup", favGroupVO);
		
		jsonView.addStaticAttribute("group_cd", group_cd);
		mv.setView(jsonView);

		return mv;
	}
	
	@RequestMapping(value="ajaxUpdateFavGroup.do")		//관심품목 그룹 수정
	public ModelAndView ajaxUpdateFavGroup(@ModelAttribute("favGroupVO") FavoriteGroupVO favGroupVO
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		dao.update("sdpf0010.update_favGroup", favGroupVO);
		
		mv.setView(jsonView);
		return mv;
	}
	
	
	@RequestMapping(value="ajaxDeleteFavGroup.do")		//관심품목 그룹 삭제
	public ModelAndView ajaxDeleteFavGroup(@RequestParam(value="group_cd",required=false) String group_cd
			, HttpServletRequest request, ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		try {
			dao.startTransaction();
			dao.startBatch();
			FavoriteGroupVO favGroupVO = new FavoriteGroupVO();
			favGroupVO.setGroup_seq("0");
			String target_group_cd = (
					(FavoriteGroupVO)dao.select("sdpf0010.select_favoriteGroupList", favGroupVO)
					).getGroup_cd();
			
			FavoriteItemVO favItemVO = new FavoriteItemVO();
			favItemVO.setGroup_cd(group_cd);
			List<FavoriteItemVO> favItemList = (List<FavoriteItemVO>)dao.selectList("sdpf0010.select_favoriteItemList", favItemVO);
			
			for(int i=0;i < favItemList.size(); i++) {		//삭제할 그룹의 품목들을 기본그룹으로 복사
				favItemVO = new FavoriteItemVO();
				favItemVO = favItemList.get(i);
				favItemVO.setTarget_group_cd(target_group_cd);
				dao.update("sdpf0010.merge_copyFavItem", favItemVO);
			}
			
			favItemVO.setItem("");
			dao.delete("sdpf0010.delete_favItem", favItemVO);		//삭제할 그룹의 품목들을 삭제
			
			favGroupVO = new FavoriteGroupVO();
			favGroupVO.setGroup_cd(group_cd);
			
			dao.delete("sdpf0010.delete_favGroup", favGroupVO);		//그룹 삭제
			
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
	
	@RequestMapping(value="ajaxAlterFavItem.do")		//관심품목 아이템 이동&복사
	public ModelAndView ajaxAlterFavItem(@RequestParam(value="flag")String flag
			, @RequestParam(value="jsonList")JSONArray jsonList
			, @RequestParam(value="group_cd")String group_cd
			, @RequestParam(value="target_group_cd")String target_group_cd
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		FavoriteItemVO favItemVO = new FavoriteItemVO();
		favItemVO.setGroup_cd(group_cd);
		favItemVO.setTarget_group_cd(target_group_cd);
		
		try {
			dao.startTransaction();
			dao.startBatch();
			
			JSONObject obj; 
			
			for(int i=0; i < jsonList.length(); i++) {
				obj = new JSONObject();
				obj = (JSONObject) jsonList.get(i);
				favItemVO.setItem(obj.getString("item"));
				favItemVO.setQty_allocjob(obj.getString("qty_allocjob"));
				favItemVO.setU_m(obj.getString("u_m"));
				
				if("move".equals(flag)) {						//이동시 태우는 sql
					dao.update("sdpf0010.merge_copyFavItem", favItemVO);
					dao.delete("sdpf0010.delete_favItem", favItemVO);
					
				} else if("copy".equals(flag)) {				//복사시 태우는 sql
					dao.update("sdpf0010.merge_copyFavItem", favItemVO);
				}
			}
			
			dao.executeBatch();
			dao.commit();
			dao.endTransaction();
			
			mv.setView(jsonView);
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			dao.endTransaction();
		}
		return mv;
	}
	
	@RequestMapping(value="ajaxDeleteFavItem.do")		//관심품목 아이템 삭제
	public ModelAndView ajaxDeleteFavItem(@RequestParam(value="group_cd",required=false)String group_cd
			, @RequestParam(value="paramList",required=false)List paramList
			, HttpServletRequest request, ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		try {
			FavoriteItemVO favItemVO = new FavoriteItemVO();
			favItemVO.setGroup_cd(group_cd);
			
			dao.startTransaction();
			dao.startBatch();
			
			for(int i=0; i < paramList.size(); i++) {
				favItemVO.setItem(paramList.get(i).toString());
				dao.delete("sdpf0010.delete_favItem", favItemVO);
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
	
	
	@RequestMapping(value="ajaxAddFavItem.do")		//관심품목 아이템 추가
	public ModelAndView ajaxAddFavItem(@ModelAttribute("favItemVO") FavoriteItemVO favItemVO
			, HttpServletRequest request, ModelMap model) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		//이미 저장되어 있는지 확인하기 위한 select List
		List chkList = (List<FavoriteItemVO>)dao.selectList("sdpf0010.select_favoriteItemList", favItemVO);
		
		if(chkList.size() < 1) {
			dao.insert("sdpf0010.insert_favItem", favItemVO);
			jsonView.addStaticAttribute("resultStr", true);
		} else {
			jsonView.addStaticAttribute("resultStr", false);
		}
		mv.setView(jsonView);

		return mv;
	}
	
}
