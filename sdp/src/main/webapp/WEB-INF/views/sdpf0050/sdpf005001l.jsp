<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 배송지관리  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {

	});

	//검색
	function search() {
		c_submit("frm", "sdpf005001l.do");
	}

	//신규작성 화면 호출
	function callInsertForm() {
		$("#flag").val("insert");
		c_submit("frm", "sdpf005001u.do");
	}

	//수정 화면 호출
	function callUpdateForm(obj) {
		$("#flag").val("update");
		$("#ship_num").val($(obj).parent().find("#hid_ship_num").val());
		c_submit("frm", "sdpf005001u.do");
	}

	//주소 삭제
	function deleteShipment(obj) {
		$("#ship_num").val($(obj).parent().find("#hid_ship_num").val());

		c_confirm("삭제하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				c_submit("frm", "sdpf005001u_delete.do");
			} else { //no Click
				return;
			}
		});
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="flag" name="flag" value="insert" />
<input type="hidden" id="ship_num" name="ship_num" value="" />

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">배송지관리</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>마이페이지</li>
								<li>배송지관리</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
						<div class="search_area">
							<select id="searchDiv" name="searchDiv">
								<option value="man" <c:if test="${searchDiv == 'man'}" > selected</c:if>>인수자</option>
								<option value="addr" <c:if test="${searchDiv == 'addr'}" > selected</c:if>>주소</option>
							</select>
							<input class="txt" type="text" id="searchText" name="searchText" style="" value="${searchText}" onkeydown="if(event.keyCode==13){search(); return false;}" />
							<input class="btn_search" type="button" onclick="search();">
						</div>
						<!--search_area-->

						<div class="search_btn_wrap">
							<p class="result_num">검색결과 : ${fn:length(shipmentList)}건</p>
							<div class="search_btn_area">
								<input class="btn_sample" id="" type="button" value="신규등록" onclick="callInsertForm();">
							</div>
						</div>

						<!-- board_list_wrap (게시물 리스트) -->
						<div class="searchlist_wrap_tit">
							<table summary="주문조회" class="table_common">
								<caption>주문조회</caption>
								<colgroup>
									<col style="width: 70px;" />
									<col style="width: 120px;" />
									<col style="width: 600px;" />
									<col style="width: 120px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">NO</th>
										<th scope="col">인수자</th>
										<th scope="col">주소</th>
										<th scope="col">연락처</th>
										<th scope="col">관리</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="searchlist_wrap">
							<table summary="주문조회" class="table_common">
								<caption>주문조회</caption>
								<colgroup>
									<col style="width: 70px;" />
									<col style="width: 120px;" />
									<col style="width: 600px;" />
									<col style="width: 120px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(shipmentList) > 0 }">
											<c:set var="no" value="${fn:length(shipmentList)}" />
											<c:forEach items="${shipmentList}" var="row" varStatus="status">
												<tr>
													<td class="txt_center">${no}</td>
													<c:set var="no" value="${no - 1}" />
													<td class="pro_code" id="">${row.man}</td>
													<td class="pro_name" id="" onclick="javascript:selectShipment(); return false;">(${row.zip})&nbsp;${row.addr1} ${row.addr2}</td>
													<td class="" id="">${row.phone}</td>
													<td class="txt_center">
														<input type="button" class="order_addlist" value="수정" onclick="callUpdateForm(this);" />
														<input type="button" class="order_addlist" value="삭제" onclick="deleteShipment(this);" />

														<input type="hidden" id="hid_ship_num" name="hid_ship_num" value="${row.ship_num}" />
														<input type="hidden" id="hid_man" name="hid_man" value="${row.man}" />
														<input type="hidden" id="hid_zip" name="hid_zip" value="${row.zip}" />
														<input type="hidden" id="hid_phone" name="hid_phone" value="${row.phone}" />
														<input type="hidden" id="hid_addr1" name="hid_addr1" value="${row.addr1}" />
														<input type="hidden" id="hid_addr2" name="hid_addr2" value="${row.addr2}" />
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty">
												<td class="td_right center" colspan="4">등록된 배송지가 없습니다.</td>
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