<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 지급어음명세서조회 목록  -->
<!DOCTYPE html>
<html>

<head>
	<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
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
							<h3 class="sub_tit">지급어음명세서 조회</h3>
							<div class="local_nav">
								<ul>
									<li class="home">홈</li>
									<li>집계조회</li>
									<li>지급어음명세서조회</li>
								</ul>
							</div>
							<!--local_nav-->
						</div>
						<!--local_nav_wrap-->

						<div class="sub_cont">
							<div class="gray_wrap">
								<span>구분</span>
								<select id="sarg_bill_type" name="arg_bill_type" style="margin-right: 20px;">
									<option value="" <c:if test="${notePayVO.arg_bill_type eq ''}">selected</c:if>>전체</option>
									<option value="1" <c:if test="${notePayVO.arg_bill_type eq '1'}">selected</c:if>>자기어음</option>
									<option value="2" <c:if test="${notePayVO.arg_bill_type eq '2'}">selected</c:if>>배서어음</option>
								</select>
								<span>만기일</span>
								<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${notePayVO.searchDate_from }" /> ~
								<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${notePayVO.searchDate_to }" />
								<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpd002001l.do');" />
								<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpd002001l.do');" />
								<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpd002001l.do');" />
								<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpd002001l.do');" />
								<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpd002001l.do');">
							</div>
							<!--  gray_wrap -->

							<div class="left_num_count">
								지급어음명세서 : ${fn:length(notePayList)}건
							</div>
							<!-- left_num_count -->

							<div class="searchlist_wrap_tit">
								<table summary="주문조회" class="table_common">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 35px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />

										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">NO</th>
											<th scope="col">어음번호</th>
											<th scope="col">만기일</th>
											<th scope="col">입금일</th>
											<th scope="col">결재일</th>

											<th scope="col">금액</th>
											<th scope="col">발행인</th>
											<th scope="col">세목</th>
										</tr>
									</thead>
								</table>
							</div>

							<div class="searchlist_wrap">
								<table summary="주문조회" class="table_common">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 35px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />

										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(notePayList) > 0}">
												<c:forEach items="${notePayList}" var="row" varStatus="status">
													<tr>
														<td class="txt_center">${status.count}</td>
														<td class="txt_center">${row.eoeum_no}</td>
														<td class="txt_center">${row.mangiil}</td>
														<td class="txt_center">${row.ibgeumil}</td>
														<td class="txt_center">${row.gyeoljeil}</td>
			
														<td class="txt_rig"><fmt:formatNumber value="${row.geumaeg}" groupingUsed="true"/></td>
														<td class="txt_center">${row.balhaengja}</td>
														<td class="">${row.semog}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr id="tr_empty" ><td class="pro_code txt_center" colspan="8">검색데이터가 없습니다</td></tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
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