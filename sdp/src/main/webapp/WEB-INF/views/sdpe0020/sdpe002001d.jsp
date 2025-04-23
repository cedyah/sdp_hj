<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- MSDS 상세 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	//수정화면 호출
	function callUpdateForm() {
		c_submit("frm", "sdpe002001u.update.do");
	}
	
	//목록화면 호출
	function callList() {
		$("#msds_num").val("");
		c_submit("frm","sdpe002001l.do");
	}
	
	//시험성적서 삭제
	function cancelMsds() {
		c_confirm("MSDS 요청을 취소 하시겠습니까?").then(function (result) {		
	        if(result){		//yes Click
	        	c_submit("frm", "sdpe002001d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
	
	function print(item_cd, saeobjang) {
		$("#item_cd").val(item_cd);
		$("#saeobjang").val(saeobjang);
		popup_report("sdpe002001d_report.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="req_dt" name="req_dt" value="${msdsHVO.req_dt}" />
<input type="hidden" id="req_no" name="req_no" value="${msdsHVO.req_no}" />

<input type="hidden" id="searchDate_from" name="searchDate_from" value="${msdsHVO.searchDate_from}"/>
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${msdsHVO.searchDate_to}"/>
<input type="hidden" id="searchText" name="searchText" value="${msdsHVO.searchText}"/>

<input type="hidden" id="item_cd" name="item_cd" value=""/>
<input type="hidden" id="saeobjang" name="saeobjang" value=""/>

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">MSDS 요청</h3>

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
						<div class="search_btn_wrap">
							<p class="result_num">품목 : <strong>${fn:length(subList)}</strong>건</p>
							<div class="search_btn_area">
								<input class="btn_newmake" id="" type="button" value="목록" onclick="callList();">
							</div>
						</div>
						<div class="orderlist_wrap_tit">
							<table class="table_common" summary="MSDS 등록 타이틀">
								<caption>MSDS 등록 타이틀</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 300px;" />
									<col style="width: 300px;" />
									<col style="width: 200px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">NO</th>
										<th scope="col">제품코드</th>
										<th scope="col">품명</th>
										<th scope="col">담당부서</th>
										<th scope="col">완료일</th>
										<th scope="col">출력</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="orderlist_wrap">
							<table class="table_common" summary="MSDS 리스트">
								<caption>MSDS 리스트</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 300px;" />
									<col style="width: 300px;" />
									<col style="width: 200px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(subList) > 0}">
											<c:forEach items="${subList}" var="row" varStatus="status">
												<tr>
													<td class="txt_center">${row.seq}</td>
													<td class="txt_center">${row.item_cd}</td>
													<td class="txt_lef">${row.pummyeong}</td>
													<td class="txt_lef">${row.buseomyeong}</td>
													<td class="txt_center">${row.prog_dt}</td>
													<td class="txt_center" title="${row.msds_yn}">
														<c:choose>
															<c:when test="${row.msds_yn == 'Y'}">
																<input class="btn_move" type="button" value="출력" onclick="javascript:print('${row.item_cd}', '${row.saeobjang}');">
															</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty"><td class="txt_center" colspan="5">추가된 제품이 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

						<div class="input_subtit">요청자 정보</div>
						<div class="tbl_input_wrap">
							<table class="tbl_input" summary="MSDS요청 입력폼">
								<colgroup>
									<col style="width:10%" />
									<col style="width:23%" />
									<col style="width:10%" />
									<col style="width:23%">
									<col style="width:10%" />
									<col style="width:23%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">요청일</th>
										<td class="last">${headerVO.req_dt }</td>
										<th scope="row">요청번호</th>
										<td class="last">${headerVO.req_no}</td>
										<th scope="row">제출일</th>
										<td class="last">${headerVO.submit_dt}</td>
									</tr>
<!-- 									<tr> -->
<!-- 										<th scope="row">작성언어</th> -->
<!-- 										<td class="last" colspan="5"> -->
<%-- 											<c:choose> --%>
<%-- 												<c:when test="${fn:trim(msds.lang) == '0'}">한글</c:when> --%>
<%-- 												<c:when test="${fn:trim(msds.lang) == '1'}">영문</c:when> --%>
<%-- 												<c:when test="${fn:trim(msds.lang) == '2'}">한글/영문</c:when> --%>
<%-- 												<c:otherwise></c:otherwise> --%>
<%-- 											</c:choose> --%>
<!-- 										</td> -->
<!-- 									</tr> -->
									<tr>
										<th scope="row">제출처명</th>
										<td class="last" colspan="5">${headerVO.submit_nm}</td>
									</tr>
<!-- 									<tr> -->
<!-- 										<th scope="row">제출처명(영문)</th> -->
<%-- 										<td class="last" colspan="5">${headerVO.submit_nm_e}</td> --%>
<!-- 									</tr> -->
									<tr>
										<th scope="row">특기사항</th>
										<td class="last" colspan="5">${headerVO.rmk}</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
								<input class="btn_modify" type="button" id="" value="수정" onclick="callUpdateForm();" />
								<input class="btn_ord_cancel" type="button" id="" value="요청 취소" onclick="cancelMsds();" />
							</div>
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