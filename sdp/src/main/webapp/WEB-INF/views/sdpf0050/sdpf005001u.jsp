<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 배송지 등록&수정  -->
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
		c_submit("frm", "sdpf005001l.do");
	}
	
	//주소지 등록 & 수정
	function confirmShipment(flag) {
		var msg = "";
		
		//null 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		//데이터 검증
		if(!dataFmtCheck(document.frm)){
			return;
		}
		
		if($("#flag").val() == "insert") {
			msg = "저장 하시겠습니까?";
			
		} else {
			msg = "수정 하시겠습니까?";
		}
		
		c_confirm(msg).then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				if(flag == "insert") {
					c_submit("frm", "sdpf005001u_insert.do");
					
				} else {
					c_submit("frm", "sdpf005001u_update.do");
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

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">					
						<div class="local_nav_wrap">
							<c:choose>
								<c:when test="${flag == 'insert' }">
							 		<h3 class="sub_tit">배송지 작성</h3>		       				
								</c:when>
								<c:otherwise>
							 		<h3 class="sub_tit">배송지 수정</h3>		       				
								</c:otherwise>
							</c:choose>
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
	         				
							<!-- board_list_wrap (게시물 리스트) -->
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
									<input class="btn_big_cancel" type="button" value="취소하기" onclick="callListForm();">
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
	</div>
	<!--wrap end-->
	<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
	
</form>
</body>
</html>