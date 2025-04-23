<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 생산진도조회 목록  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#div_list').bind("scroll", function(){
		    $('#div_title').scrollTop($(this).scrollTop());
		    $('#div_title').scrollLeft($(this).scrollLeft());
		});
	});
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">생산진도조회</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>주문관리</li>
								<li>생산진도조회</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
						<div class="gray_wrap">
							<span>의뢰일</span>
							<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${productionVO.searchDate_from }" /> ~
							<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${productionVO.searchDate_to }" />
							<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpa006001l.do');" />
							<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpa006001l.do');" />
							<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpa006001l.do');" />
							<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpa006001l.do');" />
							<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpa006001l.do');">
						</div>
						<!--  gray_wrap -->

						<div class="left_num_count">
							확인상품 : ${fn:length(productionList)}건
						</div>
						<!-- left_num_count -->

						<div class="searchlist_wrap_tit" id="div_title" style="overflow: hidden; width: 100%;">
							<div style="width:1820px;">
								<table summary="주문조회" class="table_common"  style="width:100%;">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 80px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 110px;" />
										
										<col style="width: 300px;" />
										<col style="width: 60px;" />
										<col style="width: 60px;" />
										<col style="width: 60px;" />
										<col style="width: 100px;" />
										
										<col style="width: 100px;" />
										<col style="width: 110px;" />
										<col style="width: 80px;" />
										<col style="width: 140px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">NO</th>
											<th scope="col">의뢰일자</th>
											<th scope="col">번호</th>
											<th scope="col">순번</th>
											<th scope="col">제품코드</th>
											
											<th scope="col">품명</th>
											<th scope="col">포장단위</th>
											<th scope="col">의뢰수량</th>
											<th scope="col">입고수량</th>
											<th scope="col">생산요청일</th>
											
											<th scope="col">완료예정일</th>
											<th scope="col">로트번호</th>
											<th scope="col">진도상황</th>
											<th scope="col">진도시각</th>
											<th scope="col">비고</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>

						<div class="searchlist_wrap" style="overflow: scroll;" id="div_list">
							<div style="width:1800px;">
								<table summary="주문조회" class="table_common" style="width:100%;">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 80px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 110px;" />
										
										<col style="width: 300px;" />
										<col style="width: 60px;" />
										<col style="width: 60px;" />
										<col style="width: 60px;" />
										<col style="width: 100px;" />
										
										<col style="width: 100px;" />
										<col style="width: 110px;" />
										<col style="width: 80px;" />
										<col style="width: 140px;" />
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(productionList) > 0 }" >
												<c:forEach items="${productionList}" var="row" varStatus="status">
													<tr>
														<td class="txt_center">${status.count}</td>
														<td class="txt_center">${row.ilja}</td>
														<td class="txt_center">${row.jeonpyo_no}</td>
														<td class="txt_center">${row.sunbeon}</td>
														<td class="txt_center">${row.jepum_code}</td>
														
														<td class="txt_lef">${row.pummyeong}</td>
														<td class="txt_center">${row.pojang_danwi}</td>
														<td class="txt_rig">${row.pojang_sulyang}</td>
														<td class="txt_rig">${row.ibgo_sulyang}</td>
														<td class="txt_center">${row.euiloiil}</td>
	
														<td class="txt_center">${row.wanlyo_yejeongil}</td>
														<td class="txt_center">${row.lot_no}</td>
														<td class="txt_center">${row.wanlyo_yn}</td>
														<td class="txt_center">${row.jindo_date}</td>
														<td class="txt_left">${row.bigo}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="pro_code txt_center" colspan="15">검색된 자료가 없습니다</td>
												</tr>				
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
						<!-- //searchlist_wrap (게시물 리스트) -->
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