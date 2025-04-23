<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 공지사항 목록  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//특정 div에 페이징 추가('submit할 url', '현재 페이지', '페이지당 표시 갯수', '표시될 페이지 넘버 갯수', '전체 데이터 수')
		drawPagingDiv("sdpy001001l.do", "${noticeVO.page_current}", "${noticeVO.page_countPer}", 10, "${noticeVO.page_totalCnt}");
	});
	
	//검색
	function search() {
		$("#page_current").val("1");
		c_submit("frm", "sdpy001001l.do");
	}
	
	//상세페이지로 이동
	function callDetailForm(obj) {
		$("#notice_num").val($(obj).find("#row_notice_num").val())
		c_submit("frm", "sdpy001001d.do");
	}
	
	//작성페이지
	function callInsertForm() {
		c_submit("frm", "sdpy001001u.insert.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>
<body>
<form id="frm" name="frm">
<input type="hidden" id="notice_num" name="notice_num" value=""/>

<!-- 페이징 변수 -->
<input type="hidden" id="page_current" name="page_current" value="">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="">      
<input type="hidden" id="page_countPer" name="page_countPer" value="">

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
	         				<div class="notice_search_wrap">
	         					<div class="selectDiv" style="width:100px;">
		         					<select id="searchDiv" name="searchDiv" style="">
		         						<option value="title" <c:if test="${noticeVO.searchDiv == 'title'}"> selected</c:if>>제목</option>
		         						<option value="contents" <c:if test="${noticeVO.searchDiv == 'contents'}"> selected</c:if>>내용</option>
		         					</select>
	         					</div>
	         					<input type="text" id="searchText" name="searchText" style="width:300px;" value="${noticeVO.searchText}">
	         					<input type="button" class="btn_notice_search" value="검색" onclick="search();">
	         				</div>    
	         				<div class="notice_wrap_tit">
								<table class="table_notice" summary="공지사항">
									<caption>공지사항</caption>
									<colgroup>
										<col style="width: 160px;" />
										<col style="width: 100px;" />
										<col style="width: 550px;" />							
										<col style="width: 200px;" />																
										<col style="" />
												
									</colgroup>
									<thead>
										<tr>											
											<th scope="col">번호</th>
											<th scope="col">구분</th>																
											<th scope="col">제목</th>												
											<th scope="col">기간</th>												
											<th scope="col">조회</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="notice_wrap">
								<table class="table_notice" summary="공지사항">
									<caption>공지사항</caption>
									<colgroup>
										<col style="width: 160px;" /> <!-- 번호 -->
										<col style="width: 100px;" /> <!-- 구분 -->
										<col style="width: 550px;" /> <!-- 제목 -->													
										<col style="width: 200px" /> <!-- 기간 -->																															
										<col style="" /><!-- 조회 -->							
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(noticeList) > 0 }" >
												<c:forEach items="${noticeList}" var="row" varStatus="status">
													<tr onclick="callDetailForm(this);" style="cursor: pointer;">
														<input type="hidden" id="row_notice_num" name="row_notice_num" value="${row.notice_num}">
														<td>${row.notice_num}</td>
														<td>
															<c:choose>
																<c:when test="${fn:trim(row.notice_type) == 'A'}">공지사항</c:when>
																<c:when test="${fn:trim(row.notice_type) == 'P'}">신제품</c:when>
																<c:when test="${fn:trim(row.notice_type) == 'G'}">상품권</c:when>
																<c:when test="${fn:trim(row.notice_type) == 'L'}">대리점소식</c:when>
																<c:otherwise>${row.notice_type}</c:otherwise>
															</c:choose>
														</td>
														<td class="subject">${row.title}</td>
														<td>
															<c:if test="${fn:trim(row.notice_type) == 'G'}">
																${row.notice_from} ~ ${row.notice_to}
															</c:if>
														</td>
														<td>${row.view_cnt}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="pro_code txt_center" colspan="7">검색된 자료가 없습니다</td>
												</tr>				
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>	
							</div>
							
							<div class="bottom_btn_wrap">
								<div class="right_btn_area">
									<c:if test="${auth == 'M'}">
										<input class="btn_add" type="button" id="" value="신규작성" onclick="callInsertForm();">
									</c:if>
								</div>
							</div>

							<div id="div_paging" class="pagingWrap">
							</div>
							<!-- // pagingWrap -->
							<!--right_btn_area-->	
	         			</div>
	         			<!--// sub_cont-->							
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