package com.jebi.sdp.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;

@Controller
public class Sdpy0010Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpy0010Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpy001001l.do")		//공지사항 목록 호출
	public String selectList(@ModelAttribute("noticeVO")NoticeVO noticeVO,
		HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		//공지사항 목록
		noticeVO.setNotice_num("");
		noticeVO.setPage_countPer("10");
		List<NoticeVO> noticeList = (List<NoticeVO>) dao.selectList("sdpy0010.select_notice", noticeVO);
		
		//전체 갯수
		int totalCnt = (Integer) dao.selectCnt("sdpy0010.selectCnt_notice", noticeVO);
		noticeVO.setPage_totalCnt(Integer.toString(totalCnt));
		
		model.addAttribute("noticeList", noticeList);
		
		return "sdpy0010/sdpy001001l";
	}
	
	@RequestMapping(value = "sdpy001001u.{flag}.do")		//작성 & 수정 화면 호출
	public String sdpy001001u(@ModelAttribute("noticeVO")NoticeVO noticeVO,@PathVariable(value="flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		model.addAttribute("flag", flag);
		
		if("insert".equals(flag)) {
			
		} else if("update".equals(flag)) {
			//공지사항 내용 select
			NoticeVO conVO = new NoticeVO();
			conVO.setNotice_num(noticeVO.getNotice_num());
			conVO.setPage_current("");
			NoticeVO notice = (NoticeVO) dao.select("sdpy0010.select_notice", conVO);
			model.addAttribute("notice", notice);
			
			//첨부파일  select
			FileVO fileVO = new FileVO();
			fileVO.setDoc_num(noticeVO.getNotice_num());
			List<FileVO> fileList = (List<FileVO>) dao.selectList("common.select_file", fileVO);
			model.addAttribute("fileList", fileList);
		}
		
		return "sdpy0010/sdpy001001u";
	}
	
	
	@RequestMapping(value = "sdpy001001u_insert.do")		//db저장
	public String sdpy001001u_insert(@ModelAttribute("noticeVO")NoticeVO noticeVO,
			RedirectAttributes redirectAttr, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		try {
			dao.startTransaction();
			
			noticeVO.setInternet_yn("Y");
			noticeVO.setDel_yn("N");
			noticeVO.setView_cnt("0");
			String notice_num = dao.insert_return("sdpy0010.insert_notice", noticeVO);
			noticeVO.setNotice_num(notice_num);
			
			//첨부파일 신규입력 시작
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)request;
			Iterator<String> iterator = mRequest.getFileNames();
			MultipartFile mFile = null;
			FileVO fileVO = new FileVO();
			
			while(iterator.hasNext()){
				mFile = mRequest.getFile(iterator.next());
				
				if(mFile.isEmpty() == false){
					fileVO = new FileVO();
					fileVO.setDoc_num(notice_num);
					fileVO.setOriginal_nm(mFile.getOriginalFilename());
					fileVO.setFile_size(Long.toString(mFile.getSize()));
					
					fileVO.setFile_nm(dao.insert_return("common.insert_file", fileVO));		//insert와 동시에 fileVO에 생성된 파일 이름 저장
					
					uploadFile(mFile, fileVO);		//생성된 파일 이름으로 실제로 서버에 파일 저장
				}
			}
			//첨부파일 종료
			
			dao.commit();
			dao.endTransaction();
			
			//상세페이지에 넘길 변수 저장
			redirectAttr.addFlashAttribute("noticeVO", noticeVO);
			
			return "redirect:/sdpy001001d.do";
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	
	@RequestMapping(value = "sdpy001001u_update.do")		//db수정
	public String sdpy001001u_update(@ModelAttribute("noticeVO")NoticeVO noticeVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		try {
			dao.startTransaction();
			
			dao.update("sdpy0010.update_notice", noticeVO);
			
			//첨부파일 수정 시작
			String flag = "";
			String file_nm = "";
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)request;
			Iterator<String> iterator = mRequest.getFileNames();
			MultipartFile mFile = null;
			FileVO fileVO = new FileVO();
			
			while(iterator.hasNext()){
				mFile = mRequest.getFile(iterator.next());
				fileVO = new FileVO();
				
				//mFile에서 필드이름을 가져와 flag와 파일명으로 나눔 (ex:new_A17022110484777)
				flag = mFile.getName().substring(0, 4);		//new_
				file_nm = mFile.getName().substring(4);		//A17022110484777
				
				if("new_".equals(flag)) {		//신규 파일 등록
					if(mFile.isEmpty() == false){
						fileVO.setDoc_num(noticeVO.getNotice_num());
						fileVO.setOriginal_nm(mFile.getOriginalFilename());
						fileVO.setFile_size(Long.toString(mFile.getSize()));
						
						fileVO.setFile_nm(dao.insert_return("common.insert_file", fileVO));		//insert와 동시에 fileVO에 생성된 파일 이름 저장
						
						uploadFile(mFile, fileVO);		//생성된 파일 이름으로 실제로 서버에 파일 저장
					}
					
				} else if("old_".equals(flag)) {	//기존것을 변경하거나 혹은 변경이 없는경우
					if(mFile.isEmpty() == false){	//기존파일이 변경된 경우(지우고 다시 insert)
						
						fileVO = new FileVO();
						fileVO.setFile_nm(file_nm);
						fileVO.setOriginal_nm(mFile.getOriginalFilename());
						fileVO.setFile_size(Long.toString(mFile.getSize()));
						
						dao.update("common.update_file", fileVO);		//DB 업데이트
						uploadFile(mFile, fileVO);		//생성된 파일 이름으로 실제로 서버에 파일 저장
					}
					
				} else if("del_".equals(flag)) {	//기존것 삭제
					fileVO.setDoc_num(noticeVO.getNotice_num());
					fileVO.setFile_nm(file_nm);
					dao.delete("common.delete_file", fileVO);		//DB에서 파일정보 삭제
					deleteFile(fileVO);		//서버에서 파일 삭제
				}
			}
			//첨부파일 종료
			
			dao.commit();
			dao.endTransaction();
			
			//상세페이지에 넘길 변수 저장
			model.addAttribute("notice_num", noticeVO.getNotice_num());
			model.addAttribute("searchDiv", noticeVO.getSearchDiv());
			model.addAttribute("searchText", noticeVO.getSearchText());
			model.addAttribute("page_current", noticeVO.getPage_current());
			model.addAttribute("page_totalCnt", noticeVO.getPage_totalCnt());
			model.addAttribute("page_countPer", noticeVO.getPage_countPer());
			
			return "redirect:/sdpy001001d.do";
			
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	
	@RequestMapping(value = "sdpy001001d.do")		//공지사항 상세정보
	public String sdpy001001d(@ModelAttribute("noticeVO")NoticeVO noticeVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		dao.update("sdpy0010.update_viewCnt", noticeVO);	//조회수 증가
		
		
		//공지사항 select
		NoticeVO conVO = new NoticeVO();
		conVO.setNotice_num(noticeVO.getNotice_num());
		conVO.setPage_current("");
		NoticeVO notice = (NoticeVO) dao.select("sdpy0010.select_notice", conVO);
		model.addAttribute("notice", notice);
		
		//첨부파일  select
		FileVO fileVO = new FileVO();
		fileVO.setDoc_num(noticeVO.getNotice_num());
		List<FileVO> fileList = (List<FileVO>) dao.selectList("common.select_file", fileVO);
		model.addAttribute("fileList", fileList);
		
		return "sdpy0010/sdpy001001d";
	}
	
	@RequestMapping(value = "sdpy001001d_delete.do")		//공지사항 삭제
	public String sdpy001001d_delete(@ModelAttribute("noticeVO")NoticeVO noticeVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		try {
			dao.startTransaction();
			
			FileVO fileVO = new FileVO();
			fileVO.setDoc_num(noticeVO.getNotice_num());
			
			//파일 서버에서 삭제
			List<FileVO> fileList = (List<FileVO>) dao.selectList("common.select_file", fileVO);
			for(int i=0; i < fileList.size(); i++) {
				fileVO = (FileVO) fileList.get(i);
				deleteFile(fileVO);		//서버에서 파일 삭제
			}
			
			//DB에서 파일정보 삭제
			dao.delete("common.delete_file", fileVO);
			
			//DB에서 공지사항 정보 삭제
			dao.delete("sdpy0010.delete_notice", noticeVO);
			
			dao.commit();
			dao.endTransaction();
			
			//목록 페이지에 넘길 변수 저장
			model.addAttribute("searchDiv", noticeVO.getSearchDiv());
			model.addAttribute("searchText", noticeVO.getSearchText());
			model.addAttribute("page_current", noticeVO.getPage_current());
			model.addAttribute("page_totalCnt", noticeVO.getPage_totalCnt());
			model.addAttribute("page_countPer", noticeVO.getPage_countPer());
			
			return "redirect:/sdpy001001l.do";
			
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}
	
	
	@RequestMapping(value = "ajaxNoticeDuplCheck.do")		//공지사항 상품권 기간 등록시 기간 중복 체크
	public ModelAndView ajaxNoticeDuplCheck(@ModelAttribute("noticeVO")NoticeVO noticeVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		
		noticeVO.setPage_current("");
		
		List<NoticeVO> noticeList = (List<NoticeVO>) dao.selectList("sdpy0010.select_notice", noticeVO);
		
		if(noticeList != null && noticeList.size() > 0) {	//중복이 없으면 true 리턴
			jsonView.addStaticAttribute("result", "false");
		} else {
			jsonView.addStaticAttribute("result", "true");
		}
		
		mv.setView(jsonView);
		return mv;
	}
}
