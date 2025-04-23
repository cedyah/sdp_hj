<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 배송지 조회 등록 팝업  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//전화번호 검증
		$("#telex_num").on("focusout", function(event) {
			validate_phone(event.target);
		});	
	});
	
	//목록으로 이동
	function callListForm(){
		$("#ship_num").val("");
		c_submit("frm", "sdpz000301p.do");
	}
	
	//주소지 등록 & 수정
	function confirmShipment(flag) {
		//null 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		//데이터 검증
		if(!dataFmtCheck(document.frm)){
			return;
		}
		
		var msg = "";
		if($("#flag").val() == "insert") {
			msg = "저장 하시겠습니까?";
			
		} else {
			msg = "수정 하시겠습니까?";
		}
		
		c_confirm(msg).then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				if(flag == "insert") {
					c_submit("frm", "sdpz000301pu_insert.do");
					
				} else {
					c_submit("frm", "sdpz000301pu_update.do");
				} 
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
<input type="hidden" id="ship_num" name="ship_num" value="${rs_shipmentVO.ship_num}" />
<input type="hidden" id="flag" name="flag" value="${flag}" />
<input type="hidden" id="searchDiv" name="searchDiv" value="${shipmentVO.searchDiv}" />
<input type="hidden" id="searchText" name="searchText" value="${shipmentVO.searchText}" />

	<div class="pop_wrap" style="width:710px;height:600px;">
		<div class="pop_sub_cont">
			<h2 class="pop_tit">배송지 조회</h2>

			<div class="search_btn_wrap">
				<div class="search_btn_area">
					<input class="btn_sample" id="" type="button" value="목록" onclick="callListForm();"/>
				</div>
			</div>
			<!--search_btn_wrap-->

			<div class="tbl_wrap" style="height:480px;">
				<table class="tbl_basic" summary="배송지 등록 입력폼">
					<caption>배송지 등록 입력폼</caption>
					<colgroup>
						<col style="width:15%" />
						<col style="width:85%" />
					</colgroup>
					<tbody>
						<tr class="first">
							<th scope="row">인수자</th>
							<td class="last">
								<input type="text" class="" id="man" name="man" value="${rs_shipmentVO.man}" maxlength="10" title="인수자" req/>
							</td>
						</tr>
						<tr>
							<th scope="row">전화번호</th>
							<td class="last">
								<input type="text" class="tel_no" id="phone" name="phone" placeholder="예시 : 01012345678" value="${rs_shipmentVO.phone}" title="전화번호" req/>
							</td>
						</tr>
						<tr>
							<th scope="row">배달장소</th>
							<td class="last">
								<input type="button" class="order_zipnum" value="" 
									onclick="javascript:popup_searchAddress(); return false;"/>
								<input type="text" class="order_zip readonly" id="zip" name="zip" value="${rs_shipmentVO.zip}"
									onfocus="this.blur();" title="주소" req readonly/>
								<input type="text" class="order_add readonly" id="addr1"  name="addr1" value="${rs_shipmentVO.addr1}"
									onfocus="this.blur();" readonly/>
								<input type="text" class="order_add2" id="addr2"  name="addr2" value="${rs_shipmentVO.addr2}" maxlength="100"/>
							</td>
						</tr>
					</tbody>
				</table>

				<div class="btn_center_wrap">
					<c:choose>
						<c:when test="${flag == 'insert'}">
							<input type="button" class="btn_big_order" value="등록하기" onclick="confirmShipment('insert');">
						</c:when>
						<c:otherwise>
							<input type="button" class="btn_big_order" value="수정하기" onclick="confirmShipment('update');">
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<div class="bottom_btn_wrap">
				<div class="right_btn_area">
					<input class="btn_order" id="" type="button" value="닫기" onclick="self.close();">
				</div>
			</div>
			<!--bottom_btn_wrap-->
		</div>
		<!--pop_sub_cont-->
	</div>
	<jsp:directive.include file="/WEB-INF/views/templates/dialog_common.jsp" />
</form>
</body>

</html>