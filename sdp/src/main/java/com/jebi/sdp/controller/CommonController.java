package com.jebi.sdp.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.annotate.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class CommonController extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "fileDownload.do")		//파일 다운로드
	public void fileDownload(@RequestParam(value="file_nm", required=false)String file_nm,
			HttpServletRequest request, ModelMap model, Locale locale, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		FileVO fileVO = new FileVO();
		fileVO.setFile_nm(file_nm);
		fileVO = (FileVO) dao.select("common.select_file", fileVO);
	    
		byte fileByte[] = FileUtils.readFileToByteArray(new File(fileVO.getFile_path() + fileVO.getFile_nm()));
		response.setContentType("application/octet-stream");
	    response.setContentLength(fileByte.length);
	    String download_nm = fileVO.getOriginal_nm();
	    
	    //파이어폭스 일경우 한글이 깨져서 다운로드 되기에 처리한 if문
	    if(request.getHeader("User-Agent").contains("Firefox")) {
	    	download_nm = new String(download_nm.getBytes("UTF-8"), "ISO-8859-1");
	    } else {
	    	download_nm = URLEncoder.encode(download_nm,"UTF-8");
	    }
//	    System.out.println("download_file_nm :: " + download_nm);
	    
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + download_nm + "\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");
	    response.getOutputStream().write(fileByte);

	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}
	
	@RequestMapping(value = "ozReport.do")		//오즈레포트
	public String ozReport(HttpServletRequest request, ModelMap model, Locale locale, HttpServletResponse response) throws Exception {
		//String url = "http://biz.jevisco.com:8080";
		
		
		return "templates/ozh_sample";
	}
	
	@RequestMapping(value = "noticeMain_1.do")		//메인화면 새창 공지사항
	public String noticeMain_1(HttpServletRequest request, ModelMap model, Locale locale, HttpServletResponse response) throws Exception {
		
		return "templates/notice_main_1";
	}
	
	@RequestMapping(value = "noticeMain_2.do")		//메인화면 새창 공지사항
	public String noticeMain_2(HttpServletRequest request, ModelMap model, Locale locale, HttpServletResponse response) throws Exception {
		
		return "templates/notice_main_2";
	}
	
}
