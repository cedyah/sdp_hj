package com.jebi.sdp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private CmmnService cmmnService;
	
	@RequestMapping(value = "sample.do")
	public String home(Locale locale, Model model) {
//		return "sample/sample";		sample		
		return "templates/error";
	}
} 
/* 자주사용하는 
sdpa0020/sdpa002001u

 * 주문관리  
sdpa0010/sdpa001001l - 제품검색 목록 
sdpa0020/sdpa002001u - 주문서등록
              /sdpa002001l - 주문조회 목록	
              /sdpa002001d - 주문서 상세

sdpa0030/sdpa003001l - 출하정보조회 목록 

sdpa0040/sdpa004001u_bak - 신규제조의뢰서등록
sdpa0040/sdpa004001d - 신규제조의뢰서상세
sdpa0040/sdpa004001l - 신규제조의뢰서목록

sdpa0050/sdpa005001u - 견본요청서등록
              /sdpa005001d - 견본요청서상세
              /sdpa005001l - 견본요청서목록

sdpa0060/sdpa006001l - 생산진도조회
sdpa0070/sdpa007001l - 가격조회

 * 집계조회
sdpd0010/sdpd001001l - 외상정보조회
sdpd0020/sdpd002001l - 지급어음명세조회
sdpd0030/sdpd003001l - 매입조회/인쇄

* 자료실
sdpe0010/sdpe001001u - 시험성적서 등록
sdpe0010/sdpe001001d -시험성적서 상세
sdpe0010/sdpe001001l - 시험성적서 조회 목록
sdpe0020/sdpe002001l - msds 조회 목록
sdpe0020/sdpe002001u - msds 등록
sdpe0020/sdpe002001d - msds 상세

* 취급제한물질관리
sdpb0010/sdpb001001l -화학물질 관리대장 목록
sdpb0020/sdpb001002l - 화학물질 판매대장 목록
 
* 마이페이지
sdpf0050/sdpf005001l - 배송지관리 목록
		sdpf005001u - 배송지관리 등록
sdpf0010/sdpf001001l - 관심품목관리 목록
sdpf0020/sdpf002001l - 장바구니조회 목록
sdpf0030/sdpf003002l - 포인트 목록
              /sdpf003003l - 장려금 목록
              /sdpf003004l - 화물탁송포인트 목록
              /sdpf003005u - 사은품 신청 등록
              /sdpf003005d - 사은품 신청 상세
sdpf0040/sdpf004001u - 개인정보관리 등록
sdpf0060/sdpf006001l - 문자수신조회목록

* 공지사항 
sdpy0010/sdpy001001l - 공지사항

* 공통 
sdpz0001/sdpz000101p - 재고조회 팝업
sdpz0002/sdpz000201p - 주소검색
sdpz0003/sdpz000301p - 배송지조회 팝업
 */		