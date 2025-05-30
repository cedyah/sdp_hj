
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 신규제조의뢰서 리스트  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#searchDiv").on("change", function() {
			c_submit("frm", "sdpa004001l.do");
		});
		
		
	});
	
	//상세정보 화면으로 이동
	function detailForm(obj) {
		var product_type = $(obj).find("#hid_product_type").val();
		var ilja = $(obj).find("#hid_ilja").val();
		var jeonpyo_no = $(obj).find("#hid_jeonpyo_no").val();

		//if(product_type == '제조') {				//일반 제조의뢰 상세
			$("#product_type").val(product_type);
			$("#jeonpyo_no").val(jeonpyo_no);
			$("#ilja").val(ilja);
			c_submit("frm", "sdpa004101d.do");
			
		//} else if(product_type == '신규') {		//신규 제조의뢰 상세
		//	$("#jeonpyo_no").val(jeonpyo_no);
		//	$("#ilja").val(ilja);
		//	c_submit("frm", "sdpa004001d.do");
		//}
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="product_type" name="product_type" value="" />
<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="" />
<input type="hidden" id="ilja" name="ilja" value="" />

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">제조요청</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>주문관리</li>
								<li>제조요청</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
						<div class="gray_wrap">
							<span>구분</span>
							<select id="searchDiv" name="searchDiv" style="margin-right: 20px;">
								<option value="1" <c:if test="${prVO.searchDiv eq '1'}">selected</c:if>>전체</option>
								<option value="2" <c:if test="${prVO.searchDiv eq '2'}">selected</c:if>>인터넷</option>
								<option value="3" <c:if test="${prVO.searchDiv eq '3'}">selected</c:if>>일반</option>
							</select>
							
							<span>요청기간</span>
							<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${prVO.searchDate_from }" /> ~
							<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${prVO.searchDate_to }" />
							<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpa004001l.do');" />
							<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpa004001l.do');" />
							<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpa004001l.do');" />
							<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpa004001l.do');" />
							<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpa004001l.do');">
						</div>

						<div class="search_btn_wrap">
							<p class="result_num">제조의뢰요청 : ${fn:length(prodReqList)}건</p>
							<div class="search_btn_area">
								<input class="btn_sample" id="" type="button" value="제조의뢰 작성" onclick="c_submit('frm','sdpa004101u.insert.do');">
								<!-- <input class="btn_red" id="" type="button" value="신규제조 작성" onclick="c_submit('frm','sdpa004001u.insert.do');"> -->
							</div>
						</div>
						<!--search_btn_wrap-->

						<!-- board_list_wrap (게시물 리스트) -->
						<div class="searchlist_wrap_tit">
							<table summary="제조의뢰" class="table_common">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 60px;" />
									<col style="width: 60px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">NO</th>
										<th scope="col">일자</th>
										<th scope="col">의뢰번호</th>
										<th scope="col">유형</th>
										<th scope="col">의뢰품목</th>
										
									</tr>
								</thead>
							</table>
						</div>

						<div class="searchlist_wrap">
							<table summary="제조의뢰" class="table_common">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 60px;" />
									<col style="width: 60px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(prodReqList) > 0 }">
											<c:set var="no" value="${fn:length(prodReqList)}" />
											<c:forEach items="${prodReqList}" var="row" varStatus="status">
												<tr onclick="javascript:detailForm(this); return false;" style="cursor:pointer;">
													<td class="txt_center">
														<input type="hidden" id="hid_product_type" name="hid_product_type" value="${row.product_type}"/>
														<input type="hidden" id="hid_ilja" name="hid_ilja" value="${row.ilja}"/>
														<input type="hidden" id="hid_jeonpyo_no" name="hid_jeonpyo_no" value="${row.jeonpyo_no}"/>
														${status.count}
													</td>
													<td class="txt_center">${row.ilja}</td>
													<td class="txt_center">${row.jeonpyo_no}</td>
													<td class="txt_center">
														<c:choose>
															<c:when test="${row.product_type == '인터넷'}"><span class="make_normal">${row.product_type}</span></c:when>
															<c:when test="${row.product_type == '일반'}"><span class="make_new">${row.product_type}</span></c:when>
															<c:otherwise>${row.product_type}</c:otherwise>
														</c:choose>
													</td>
													<td>${row.pummyeong}</td>
												</tr>
												<c:set var="no" value="${no - 1}" />
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td id="tr_empty" class="txt_center" colspan="7">제조의뢰 내역이 없습니다</td>
											</tr>
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