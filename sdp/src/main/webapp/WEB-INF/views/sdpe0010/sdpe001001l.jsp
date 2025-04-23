<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 시험성적서 조회 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {

	});
	
	//상세정보 페이지 호출
	function callDetailForm(req_dt, req_no) {
		$("#req_dt").val(req_dt);
		$("#req_no").val(req_no);
		c_submit("frm", "sdpe001001d.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="req_dt" name="req_dt" value="" />
<input type="hidden" id="req_no" name="req_no" value="" />
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">시험성적서</h3>

						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>자료실</li>
								<li>시험성적서</li>
							</ul>
						</div>
						<!--local_nav-->

					</div>
					<div class="sub_cont">
						<div class="search_area_line2">
							<div>
								<span>요청 기간</span>
								<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${testReportVO.searchDate_from }" /> ~
								<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${testReportVO.searchDate_to }" />
								<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpe001001l.do');" />
								<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpe001001l.do');" />
								<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpe001001l.do');" />
								<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpe001001l.do');" />
								
								<span style="padding-left:50px;">제출처</span>
								<input class="txt" id="input_searchText" name="searchText" type="text" value="${testReportVO.searchText}"
									style="width:300px;" onkeydown="if(event.keyCode==13){c_submit('frm', 'sdpe001001l.do');}">
								<input class="btn_search_normal" value="검색" type="button" onclick="c_submit('frm', 'sdpe001001l.do');">
							</div>
						</div>
						
						<!--  gray_wrap -->
						<div class="search_btn_wrap">
							<p class="result_num" id="p_listCnt">시험성적서 : ${fn:length(testReportList)}건</p>
							<div class="search_btn_area">
								<input class="btn_newmake" id="" type="button" value="시험성적서 요청" 
									onclick="c_submit('frm','sdpe001001u.insert.do');">
							</div>
						</div>

						<div class="orderlist_wrap_tit">
							<table class="table_common" summary="시험성적서 목록">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 120px;" />
									<col style="width: 440px;" />
									<col style="width: 120px;" />

<!-- 									<col style="width:100px;" /> -->
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">NO</th>
										<th scope="col">요청일자</th>
										<th scope="col">요청번호</th>
										<th scope="col">제출처</th>
										<th scope="col">제출일</th>

<!-- 										<th scope="col">상태(진도)</th> -->
										<th scope="col">작성언어</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="searchlist_wrap">
							<table class="table_common">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 120px;" />
									<col style="width: 440px;" />
									<col style="width: 120px;" />

<!-- 									<col style="width:100px;" /> -->
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(testReportList) > 0}">
											<c:set var="no" value="${fn:length(testReportList)}" />
											<c:forEach items="${testReportList}" varStatus="status" var="row">
												<tr onclick="callDetailForm('${row.req_dt}','${row.req_no}');" style="cursor: pointer;">
													<td class="txt_center">${status.count}<c:set var="no" value="${no-1}" /></td>
													<td class="txt_center">${row.req_dt}</td>
													<td class="txt_center">${row.req_no}</td>
													<td class="txt_lef">
														<c:choose>
															<c:when test="${row.lang == 'K'}">
																${row.submit_nm_k}
															</c:when>
															<c:when test="${row.lang == 'E'}">
																${row.submit_nm_e}
															</c:when>
															<c:otherwise>
																${row.submit_nm_k} (${row.submit_nm_e})
															</c:otherwise>
														</c:choose>
													</td>
													<td class="txt_center">${row.submit_dt}</td>

<!-- 													<td class="txt_center">관리여부?</td> -->
													<td class="txt_center">
														<c:choose>
															<c:when test="${fn:trim(row.lang) == 'K'}">한글</c:when>
															<c:when test="${fn:trim(row.lang) == 'E'}">영문</c:when>
															<c:when test="${fn:trim(row.lang) == 'M'}">한글/영문</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" >
												<td class="txt_center" colspan="7">검색된 시험성적서가 없습니다</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

					</div>
					<!--sub_cont-->
				</div>
				<!--sub_contents-->
			</div>
			<!--sub_wrap-->
		</div>
		<!--sub_wrap_area-->
		<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		
	</div>
	<!--wrap end-->
</form>
</body>
</html>