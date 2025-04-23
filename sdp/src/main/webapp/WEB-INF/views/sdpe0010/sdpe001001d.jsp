<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 시험성적서 상세 -->
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
		c_submit("frm", "sdpe001001u.update.do");
	}
	
	//목록화면 호출
	function callList() {
		$("#req_no").val("");
		$("#req_dt").val("");
		c_submit("frm","sdpe001001l.do");
	}
	
	//시험성적서 삭제
	function cancelTestReport() {
		c_confirm("시험성적서 요청을 취소 하시겠습니까?").then(function (result) {		
	        if(result){		//yes Click
	        	c_submit("frm", "sdpe001001d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
	
	//시험성적서 출력
	function popup_testReport(language, item_cd, lot_no) {
		$("#lang").val(language);
		$("#item_cd").val(item_cd);
		$("#lot_no").val(lot_no);
		
		popup_report("sdpe001001d_report.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="req_dt" name="req_dt" value="${testReportVO.req_dt}" />
<input type="hidden" id="req_no" name="req_no" value="${testReportVO.req_no}" />
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${testReportVO.searchDate_from}"/>
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${testReportVO.searchDate_to}"/>
<input type="hidden" id="searchText" name="searchText" value="${testReportVO.searchText}"/>

<!-- 출력물 관련 레포트 -->
<input type="hidden" id="lang" name="lang" value="" />
<input type="hidden" id="item_cd" name="item_cd" value="" />
<input type="hidden" id="lot_no" name="lot_no" value="" />

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">시험성적서 상세</h3>

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

						<div class="search_btn_wrap">
							<p class="result_num">품목 : <strong>${fn:length(testReportSub)}</strong>건</p>
							<div class="search_btn_area">
								<input class="btn_newmake" id="" type="button" value="목록" onclick="callList();">
							</div>
						</div>

						<div class="orderlist_wrap_tit">
							<table class="table_common" summary="시험성적서 리스트 타이틀">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 360px;" />
									<col style="width: 100px;" />
									<col style="width: 200px;" />
									
									<col style="width: 75px;" />
									<col style="width: 65px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">No</th>
										<th scope="col">제품코드</th>
										<th scope="col">품명</th>
										<th scope="col">로트번호</th>
										<th scope="col">담당부서</th>
										
										<th scope="col">담당자</th>
										<th scope="col">수량</th>
										<th scope="col">출력</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="orderlist_wrap">
							<table class="table_common" summary="시험성적서 리스트">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 360px;" />
									<col style="width: 100px;" />
									<col style="width: 200px;" />
									
									<col style="width: 75px;" />
									<col style="width: 65px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(testReportSub) > 0}">
											<c:forEach items="${testReportSub}" var="row" varStatus="status">
												<tr>
													<td>${row.seq}</td>
													<td class="txt_lef">${row.item_cd}</td>
													<td class="txt_lef">${row.pummyeong}</td>
													<td class="txt_center">${row.lot_no }</td>
													<td class="txt_lef">${row.dept}</td>
													
													<td class="txt_center">${row.emp_no}</td>
													<td class="txt_rig blue_B">
														<fmt:formatNumber groupingUsed="true" value="${row.su}"/>
													</td>
													<td class="txt_center" title="${row.print_yn}">
														<c:choose>
															<c:when test="${row.print_yn == 'YY'}">
																<input class="btn_move" type="button" style="float:left;" value="한글" onclick="javascript:popup_testReport('kor', '${row.item_cd}', '${row.lot_no}');">
																<input class="btn_move" type="button" style="float:right;" value="영문" onclick="javascript:popup_testReport('eng', '${row.item_cd}', '${row.lot_no}');">
															</c:when>
															<c:when test="${row.print_yn == 'YN'}">
																<input class="btn_move" type="button" style="" value="한글" onclick="javascript:popup_testReport('kor', '${row.item_cd}', '${row.lot_no}');">
															</c:when>
															<c:when test="${row.print_yn == 'NY'}">
																<input class="btn_move" type="button" style="" value="영문" onclick="javascript:popup_testReport('eng', '${row.item_cd}', '${row.lot_no}');">
															</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" ><td class="txt_center" colspan="8">추가된 제품이 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

						<div class="input_subtit">요청자 정보</div>
						<div class="tbl_input_wrap">
							<table class="tbl_input" summary="시험성적서 요청">
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
										<td class="last">${testReportHeader.req_dt}</td>
										<th scope="row">요청번호</th>
										<td class="last">${testReportHeader.req_no}</td>
										<th scope="row">제출일</th>
										<td class="last">${testReportHeader.submit_dt}</td>
									</tr>
									<tr>
										<th scope="row">작성언어</th>
										<td class="last" colspan="5">
											<c:choose>
												<c:when test="${fn:trim(testReportHeader.lang) == 'K'}">한글</c:when>
												<c:when test="${fn:trim(testReportHeader.lang) == 'E'}">영문</c:when>
												<c:when test="${fn:trim(testReportHeader.lang) == 'M'}">한글/영문</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<th scope="row">제출처명(한글)</th>
										<td class="last" colspan="5">${testReportHeader.submit_nm_k}</td>
									</tr>
									<tr>
										<th scope="row">제출처명(영문)</th>
										<td class="last" colspan="5">${testReportHeader.submit_nm_e}</td>
									</tr>
									<tr>
										<th scope="row">특기사항</th>
										<td class="last" colspan="5">${testReportHeader.rmk}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="comment_wrap">
							<ul>
								<li><span class="blit">·</span> 중금속 시험성적서는 영업부서 담당자에게 직접 요청하십시오.</li>
								<li><span class="blit">·</span> 파크멜(분체)는 대리점(<span>129710@s.jebi.co.kr</span>) e-mail로 회신됩니다.</li>
							</ul>
						</div>

						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
								<input class="btn_modify" type="button" id="" value="수정" onclick="callUpdateForm();" />
								<input class="btn_ord_cancel" type="button" id="" value="요청 취소" onclick="cancelTestReport();" />
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