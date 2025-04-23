<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- MSDS 목록 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
	});
	
	//상세정보 페이지 호출
	function callDetailForm(obj) {
		$("#req_dt").val($(obj).parent().find("#hid_req_dt").val());
		$("#req_no").val($(obj).parent().find("#hid_req_no").val());
		
		c_submit("frm", "sdpe002001d.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="req_dt" name="req_dt" value=""/>
<input type="hidden" id="req_no" name="req_no" value=""/>

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">MSDS</h3>

						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>자료실</li>
								<li>MSDS</li>
							</ul>
						</div>
						<!--local_nav-->

					</div>
					<div class="sub_cont">
						<div class="search_area_line2">
							<div>
								<span>요청 기간</span>
								<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${msdsHVO.searchDate_from }" /> ~
								<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${msdsHVO.searchDate_to }" />
								<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpe002001l.do');" />
								<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpe002001l.do');" />
								<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpe002001l.do');" />
								<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpe002001l.do');" />
								
<!-- 								<select id="select_searchDiv" name="searchDiv" class="mL30"> -->
<%-- 									<option value="" <c:if test="${msdsHVO.searchDiv eq ''}">selected</c:if>>전체</option> --%>
<%-- 									<option value="item_code" <c:if test="${msdsHVO.searchDiv eq 'item_code'}">selected</c:if>>제품코드</option> --%>
<%-- 									<option value="item_codeOld" <c:if test="${msdsHVO.searchDiv eq 'item_codeOld'}">selected</c:if>>구제품코드</option> --%>
<%-- 									<option value="description" <c:if test="${msdsHVO.searchDiv eq 'description'}">selected</c:if>>제품명</option> --%>
<%-- 									<option value="submit_nm" <c:if test="${msdsHVO.searchDiv eq 'submit_nm'}">selected</c:if>>제출처</option> --%>
<!-- 								</select> -->
								<span style="margin-left:20px;">제출처</span>
								<input class="txt" id="input_searchText" name="searchText" type="text" value="${msdsHVO.searchText}" 
									style="width:300px;" onkeydown="if(event.keyCode==13){c_submit('frm', 'sdpe002001l.do');}">
								<input class="btn_search_normal" value="검색" type="button" onclick="c_submit('frm', 'sdpe002001l.do');">
							</div>
						</div>
						
						<!--  gray_wrap -->
						<div class="search_btn_wrap">
							<p class="result_num" id="p_listCnt">요청 MSDS : ${fn:length(msdsList)}건</p>
							<div class="search_btn_area">
								<input class="btn_newmake" id="" type="button" value="MSDS 요청" onclick="c_submit('frm','sdpe002001u.insert.do');">
							</div>
						</div>

						<div class="orderlist_wrap_tit">
							<table class="table_common" summary="MSDS 목록">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 120px;" />
									<col style="width: 540px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">NO</th>
										<th scope="col">요청일자</th>
										<th scope="col">요청번호</th>
										<th scope="col">제출처</th>
										<th scope="col">제출일</th>
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
									<col style="width: 540px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(msdsList) > 0}">
											<c:set var="no" value="${fn:length(msdsList)}" />
											<c:forEach items="${msdsList}" varStatus="status" var="row">
												<tr>
													<input type="hidden" id="hid_req_dt" name="hid_req_dt" value="${row.req_dt}"/>
													<input type="hidden" id="hid_req_no" name="hid_req_no" value="${row.req_no}"/>
													<td class="txt_center">${status.count}<c:set var="no" value="${no-1}" /></td>
													<td class="txt_center">${row.req_dt}</td>
													<td class="txt_center">${row.req_no}</td>
													<td class="pro_name" onclick="callDetailForm(this);">${row.submit_nm}</td>
													<td class="txt_center">${row.submit_dt}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" >
												<td class="txt_center" colspan="5">검색된 MSDS가 없습니다</td>
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