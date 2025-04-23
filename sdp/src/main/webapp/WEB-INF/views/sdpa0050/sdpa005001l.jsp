<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 견본요청서 목록 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
	});
	//상세정보 페이지 호출
	function callDetailForm(jeonpyo_no, ilja) {
		$("#jeonpyo_no").val(jeonpyo_no);
		$("#ilja").val(ilja);
		c_submit("frm", "sdpa005001d.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value=""/>
<input type="hidden" id="ilja" name="ilja" value=""/>

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">				
			            <h3 class="sub_tit">견본요청</h3>
	       				<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>주문관리</li>
								<li>견본요청</li>
							</ul>
	                   </div><!--local_nav-->                  
	       			</div>

					<div class="sub_cont">
					<div class="search_area_line2">
						<div>
							<span>요청기간</span>
							<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${smplReqVO.searchDate_from }" /> ~
							<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${smplReqVO.searchDate_to }" />
							<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpa005001l.do');" />
							<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpa005001l.do');" />
							<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpa005001l.do');" />
							<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpa005001l.do');" />
							<input class="btn_search_normal" value="검색" type="button" onclick="c_submit('frm', 'sdpa005001l.do');">
						</div>
					</div>
					
					<!--  gray_wrap -->
					<div class="search_btn_wrap">
						<p class="result_num" id="p_listCnt">견본요청 : ${fn:length(smplRequestList)}건</p>
						<div class="search_btn_area">
							<input class="btn_newmake" id="" type="button" value="견본요청" onclick="c_submit('frm','sdpa005001u.insert.do');">
						</div>
					</div>

					<div class="searchlist_wrap_tit">
						<table class="table_common" summary="시험성적서 목록">
							<colgroup>
								<col style="width: 40px;" />
								<col style="width: 100px;" />
								<col style="width: 60px;" />
								<col style="width: 400px;" />
								<col style="width: 200px;" />

								<col style="width: 100px;" />
								<col style="" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">NO</th>
									<th scope="col">요청일</th>
									<th scope="col">전표번호<br>(수주번호)</th>
									<th scope="col">품명</th>
									<th scope="col">제출처</th>

									<th scope="col">요구납기일</th>
									<th scope="col">처리일</th>
								</tr>
							</thead>
						</table>
					</div>

					<div class="searchlist_wrap">
						<table class="table_common focus_tr">
							<colgroup>
								<col style="width: 40px;" />
								<col style="width: 100px;" />
								<col style="width: 60px;" />
								<col style="width: 400px;" />
								<col style="width: 200px;" />

								<col style="width: 100px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(smplRequestList) > 0}">
										<c:set var="no" value="${fn:length(smplRequestList)}" />
										<c:forEach items="${smplRequestList}" varStatus="status" var="row">
											<tr onclick="callDetailForm('${row.jeonpyo_no}', '${row.ilja}');" style="cursor: pointer;">
												<td class="txt_center">${status.count}</td>
												<td class="txt_center">${row.ilja}</td>
												<td class="txt_center">${row.jeonpyo_no}</td>
												<td class="txt_lef">${row.pummyeong}</td>
												<td class="pro_lef">${row.jechulcheo}</td>

												<td class="txt_center">${row.himang_wanryoil}</td>
												<td class="txt_center">${row.cheoliil}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr id="tr_empty" >
											<td class="txt_center" colspan="6">검색된 견본요청서가 없습니다</td>
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