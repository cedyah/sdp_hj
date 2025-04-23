<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 장려금 목록 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {

	});

	//연도 변경
	function changeYear(num) {
		var date = new Date();
		$("#searchYear").val(date.getFullYear() + num);
		search();
	}

	//조회
	function search() {
		c_submit("frm", "sdpf003003l.do");
	}
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
						<h3 class="sub_tit">포인트 관리</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>마이페이지</li>
								<li>포인트 관리</li>
								<li>장려금</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
						<!-- 상단 텝메뉴  -->
						<jsp:directive.include file="/WEB-INF/views/sdpf0030/sdpf0030_include.jsp" />

						<div class="gray_wrap">
							<span>연도</span>
							<input type="text" class="entry" id="searchYear" name="searchYear" value="${subsidyVO.searchYear}" maxlength="4" style="width:55px;text-align:center;" />
							<input type="button" value="금년" class="gray_inbtn" onclick="javascript:changeYear(0);"/>
							<input type="button" value="작년" class="gray_inbtn" onclick="javascript:changeYear(-1);"/>
							<input type="button" value="제작년" class="gray_inbtn" onclick="javascript:changeYear(-2);"/>								
				            <input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:search();">
						</div>

						<div class="searchlist_wrap_tit">
							<table class="table_notice" summary="장려금 조회">
								<caption>장려금 조회</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 121px;" />
									<col style="width: 192px;" />
									<col style="width: 192px;" />
									<col style="width: 192px;" />

									<col style="width: 192px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">No</th>
										<th scope="col">입금년월</th>
										<th scope="col">결제할인액</th>
										<th scope="col">결제할총액</th>
										<th scope="col">판매장려금</th>

										<th scope="col">합계</th>
										<th scope="col">매입년월</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="searchlist_wrap">
							<table class="table_notice" summary="장려금 조회">
								<caption>장려금 조회</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 121px;" />
									<col style="width: 192px;" />
									<col style="width: 192px;" />
									<col style="width: 192px;" />

									<col style="width: 192px;" />
									<col style="" />
								</colgroup>
								<tbody>	
									<c:choose>
										<c:when test="${fn:length(subsidyList) > 0}">
											<c:forEach items="${subsidyList}" var="row" varStatus="status">
												<tr>
													<td class="txt_center"><c:if test="${fn:length(subsidyList) > status.count}">${status.count}</c:if></td>
													<td class="txt_center">${row.pay_yyyymm}</td>
													<td class="blue_B txt_rig"><fmt:formatNumber value="${row.div_amt1}" groupingUsed="true"/></td>
													<td class="blue_B txt_rig"><fmt:formatNumber value="${row.div_amt2}" groupingUsed="true"/></td>
													<td class="blue_B txt_rig"><fmt:formatNumber value="${row.div_amt3}" groupingUsed="true"/></td>
			
													<td class="blue_B txt_rig"><fmt:formatNumber value="${row.w_hab}" groupingUsed="true"/></td>
													<td class="txt_center">${row.sale_yyyymm}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty"><td class="pro_code txt_center" colspan="7">검색된 자료가 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
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