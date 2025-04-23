<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 공지사항 상세  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	
	//목록으로
	function goList() {
		c_submit("frm", "sdpy001001l.do");
	}
	
	//수정화면으로
	function callUpdateForm() {
		c_submit("frm", "sdpy001001u.update.do");
	}
	
	//공지사항 삭제
	function delNotice() {
		c_confirm("삭제 하시겠습니까?").then(function (result) {		
	        if(result){		//yes Click
	        	c_submit("frm", "sdpy001001d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="notice_num" name="notice_num" value="${noticeVO.notice_num}">

<!-- 검색조건 -->
<input type="hidden" id="searchDiv" name="searchDiv" value="${noticeVO.searchDiv}"/>
<input type="hidden" id="searchText" name="searchText" value="${noticeVO.searchText}"/>

<!-- 페이징 변수 -->
<input type="hidden" id="page_current" name="page_current" value="${noticeVO.page_current}">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="${noticeVO.page_totalCnt}">      
<input type="hidden" id="page_countPer" name="page_countPer" value="${noticeVO.page_countPer}">

<!-- 첨부파일 변수 -->
<input type="hidden" id="file_nm" name="file_nm" />

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">					
						<div class="local_nav_wrap">
					 		<h3 class="sub_tit">공지사항</h3>		       				
			       				<div class="local_nav">
			                         <ul>
				                           <li class="home">홈</li>
				                           <li>공지사항</li>
			                         </ul>
			                   </div>
			                   <!--local_nav-->		                   
	       				</div>
	       				<!--local_nav_wrap-->	
	         			<div class="sub_cont">
	         				<div class="search_btn_wrap" style="">
								<div class="search_btn_area">
									<input class="btn_newmake" id="" type="button" value="목록" onclick="javascript:goList();">
								</div>
							</div>
							<div class="tbl_wrap">
								<table class="tbl_basic" summary="공지사항 보기">
									<caption>공지사항 보기</caption>
									<colgroup>
										<col style="width:15%" />
										<col style="width:85%" />
									</colgroup>
									<tbody>
										<tr class="first">
											<th scope="row">구분</th>
											<td class="last">
												<c:choose>
													<c:when test="${fn:trim(notice.notice_type) == 'A'}">공지사항</c:when>
													<c:when test="${fn:trim(notice.notice_type) == 'P'}">신제품</c:when>
													<c:when test="${fn:trim(notice.notice_type) == 'G'}">상품권</c:when>
													<c:when test="${fn:trim(notice.notice_type) == 'L'}">대리점소식</c:when>
													<c:otherwise>${notice.notice_type}</c:otherwise>
												</c:choose>
											</td>												
										</tr>
										<c:if test="${fn:trim(notice.notice_type) == 'G'}">
											<tr>
												<th scope="row">기간</th>
												<td class="last">${notice.notice_from} ~ ${notice.notice_to}</td>
											</tr>
										</c:if>
										<tr>
											<th scope="row">제목</th>
											<td class="last">
												${notice.title}
											</td>												
										</tr>
										<tr>
											<th scope="row">내용</th>
											<td class="last notice_body">
												<img src="" alt="" />
												<c:set var="nr" value="\r\n" />
												${fn:replace(notice.contents, newLineChar, "<br>")}
											</td>
										</tr>
										<tr>
											<th scope="row">파일첨부</th>
											<td class="last filetxt">
												<c:if test="${fn:length(fileList) > 0}">
													<c:forEach items="${fileList}" var="row" varStatus="status">
														<a href="javascript:fileDownload('${row.file_nm}');">
															${row.original_nm}&nbsp;(<fmt:formatNumber groupingUsed="true" value="${row.file_size}"/> Byte)
														</a>
													</c:forEach>
												</c:if>
											</td>
										</tr>
										<c:if test="${fn:trim(notice.notice_type) == 'L'}">
											<tr>
												<th scope="row">Link</th>
												<td class="last filetxt">
													<a href="${notice.link}" target="_blank">${notice.link}</a>
												</td>
											</tr>
										</c:if>
										<!--tr>
											<th scope="row">이미지</th>
											<td class="last">attach_img.jpg</td>
										</tr-->
									</tbody>
								</table>
							</div>
							<div class="bottom_btn_wrap">
								<div class="right_btn_area">
									<c:if test="${auth == 'M'}">
										<input class="btn_save" type="button" id="" value="수정" onclick="callUpdateForm();" />
										<input class="btn_cancel" type="button" id="" value="삭제" onclick="delNotice();" />
									</c:if>
								</div>
							</div>
							<!--bottom_btn_wrap-->	
						</div>
						<!--sub_cont-->							
					</div>
					<!--// sub_contents-->
				</div>
				<!--// sub_wrap-->
			</div>
			<!--// sub_wrap_area-->
		<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		
	</div>
	<!--wrap end-->
</form>
</body>
</html>