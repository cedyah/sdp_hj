<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 배송지 조회 pop 팝업  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	//검색
	function search() {
		c_submit("frm", "sdpz000301p.do");
	}
	
	//신규작성 화면 호출
	function callInsertForm(){
		$("#flag").val("insert");
		c_submit("frm", "sdpz000301pu.do");
	}
	
	//수정 화면 호출
	function callUpdateForm(obj){
		$("#flag").val("update");
		$("#ship_num").val($(obj).parent().find("#hid_ship_num").val());
		c_submit("frm", "sdpz000301pu.do");
	}
	
	//주소 선택
	function selectShipment(obj) {
		showLoadingDiv();
		var man = $(obj).parent().find("#hid_man").val(); 
		var zip = $(obj).parent().find("#hid_zip").val();
		var phone = $(obj).parent().find("#hid_phone").val();
		var addr1 = $(obj).parent().find("#hid_addr1").val();
		var addr2 = $(obj).parent().find("#hid_addr2").val();
		
		$("#insuja",opener.document).val(man);
		$("#zip",opener.document).val(zip);
		$("#tel_no",opener.document).val(phone);
		$("#addr1",opener.document).val(addr1);
		$("#addr2",opener.document).val(addr2);
		
		self.close();
	}
	
	//주소 삭제
	function deleteShipment(obj) {
		$("#ship_num").val($(obj).parent().find("#hid_ship_num").val());
		
		c_confirm("삭제하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				c_submit("frm", "sdpz000301p_delete.do");
			} else { //no Click
				return;
			}
		});
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body style="background: white;">
<form id="frm" name="frm">
<input type="hidden" id="flag" name="flag" value="insert" />
<input type="hidden" id="ship_num" name="ship_num" value="" />

	<div class="pop_wrap" style="width:710px;height:600px;">
	<div class="pop_sub_cont">
		<h2 class="pop_tit">배송지 조회</h2>

		<div class="search_btn_wrap">
			<div class="search_pop_area">
				<select id="searchDiv" name="searchDiv">
					<option value="man" <c:if test="${searchDiv == 'man'}" > selected</c:if>>인수자</option>
					<option value="addr" <c:if test="${searchDiv == 'addr'}" > selected</c:if>>주소</option>
				</select>
				<input type="text" id="searchText" name="searchText" style="width:200px;" value="${searchText}"
					onkeydown="if(event.keyCode==13){search(); return false;}"/>
				<input type="button" class="btn_newmake" value="검색" onclick="search();"/>
			</div>
			<div class="search_btn_area">
				<input class="btn_sample" id="" type="button" value="신규등록"
					onclick="callInsertForm();">
			</div>
		</div>
		<!--search_btn_wrap-->

		<div class="searchlist_wrap_tit">
			<table summary="배송지 조회">
				<caption>배송지 조회</caption>
				<colgroup>
					<col style="width: 80px;" />
					<col style="width: 360px;" />
					<col style="width: 100px;" />
					<col style="" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">인수자</th>
						<th scope="col">주소</th>
						<th scope="col">연락처</th>
						<th scope="col">관리</th>
					</tr>
				</thead>
			</table>
		</div>

		<div class="searchlist_wrap" style="height:400px;">
			<table class="table_common" summary="배송지 조회">
				<caption>배송지 조회</caption>
				<colgroup>
					<col style="width: 80px;" />
					<col style="width: 360px;" />
					<col style="width: 100px;" />
					<col style="" />
				</colgroup>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(shipmentList) > 0 }">
							<c:forEach items="${shipmentList}" var="row" varStatus="status">
								<tr>
									<td class="pro_code" id="">${row.man}</td>
									<td class="" id="" onclick="javascript:selectShipment(this); return false;"
										style="cursor:pointer;">${row.addr1} ${row.addr2}</td>
									<td class="" id="">${row.phone}</td>
									<td>
										<input type="button" class="order_addlist" value="수정" onclick="callUpdateForm(this);"/>
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
							<tr id="tr_empty"><td class="td_right center" colspan="4">등록된 배송지가 없습니다.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>

		<div class="bottom_btn_wrap">
			<div class="right_btn_area">
				<input class="btn_order" id="" type="button" value="닫기" onclick="self.close();">
			</div>
		</div>
		<!--bottom_btn_wrap-->

	</div>
	<!--sub_cont-->
	</div>
	<jsp:directive.include file="/WEB-INF/views/templates/dialog_common.jsp" />
</form>
</body>
</html>