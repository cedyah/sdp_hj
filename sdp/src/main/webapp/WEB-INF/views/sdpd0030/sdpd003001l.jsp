<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 매입조회 목록  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		setTotalCnt();
// 		test();
	});
	
	//총건수 계산
	function setTotalCnt() {
		var li_chkBox = $("input[name='chkBox']");
		$(".left_num_count").html("매입 : " + li_chkBox.length + "건");
	}
	
	//장바구니 담기
	function addBasket(obj) {
		var item;
		var itemList = [];
		var li_duplChk =[];		//중복체크용 배열
		
		var li_chkBox = $("#tbody_list input[name='chkBox']:checked");
		for(var i=0; i < li_chkBox.length; i++) {
			item = {
				item 			: $(li_chkBox[i]).parent().parent().find("#hid_item").val()
				, qty_allocjob	: $(li_chkBox[i]).parent().parent().find("#hid_qty_allocjob").val()
				, u_m			: $(li_chkBox[i]).parent().parent().find("#hid_u_m").val()
				, description 	: $(li_chkBox[i]).parent().parent().find("#hid_description").val()
				, qty_basket 	: $(li_chkBox[i]).parent().parent().find("#hid_qty").val()
			};
			
			if(li_duplChk.indexOf(item["item"] + item["qty_allocjob"] + item["u_m"]) < 0) {
				itemList.push(item);
				li_duplChk.push(item["item"] + item["qty_allocjob"] + item["u_m"]);			
			}
		}

		if(itemList.length < 1) {
			c_alert("선택된 제품이 없습니다.");
			return;
		}
		
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxAddBasket.do",
			data : {
				itemList : JSON.stringify(itemList)
			},
			datatype : "JSON",
			success : function(data) {
				//커스텀 confirm
				c_confirmBasket(itemList.length + "건의 제품이 장바구니에 추가 되었습니다.<br>장바구니 화면으로 이동하시겠습니까?").then(function (result) {		
			        if(result){		//yes Click
			        	c_href("sdpf002001l.do");
			        } else {		//no Click
			        	return;
			        }
			    });
				
			},
			error : function(xhr, status, error) {
				alert("error");	
			}
		});
	}
	
	//주문서 작성 화면으로 감
	function createOrder() {
		var li_chkBox = $("input[name='chkBox']:checked");
		var jsonList = [];
		var item;
		var li_duplChk =[];		//중복체크용 배열
		
		if(li_chkBox.length < 1) {
			c_alert("선택된 제품이 없습니다"); return;
		}
		
		for(var i=0; i < li_chkBox.length; i++) {
			item = {
				item : $(li_chkBox[i]).parent().find("#hid_item").val()
				, qty_allocjob : $(li_chkBox[i]).parent().find("#hid_qty_allocjob").val()
				, u_m : $(li_chkBox[i]).parent().find("#hid_u_m").val()
				, description : $(li_chkBox[i]).parent().find("#hid_description").val()
				, qty_on_hand01 : $(li_chkBox[i]).parent().parent().find("#hid_qty").val() < 0 ? 0 : $(li_chkBox[i]).parent().parent().find("#hid_qty").val() 
			}
			if(li_duplChk.indexOf(item["item"] + item["qty_allocjob"] + item["u_m"]) < 0) {
				jsonList.push(item);
				li_duplChk.push(item["item"] + item["qty_allocjob"] + item["u_m"]);			
			}
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		c_submit("frm", "sdpa002001u.insert.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<!-- 기타변수 -->
<input type="hidden" id="paramList" name="paramList">
<input type="hidden" id="jsonList" name="jsonList">

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">매입조회/인쇄</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>집계조회</li>
								<li>매입조회/인쇄</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
						<div class="gray_wrap">
							<span>기간</span>
							<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${purchaseVO.searchDate_from }" /> ~
							<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${purchaseVO.searchDate_to }" />
							<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpd003001l.do');" />
							<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpd003001l.do');" />
							<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpd003001l.do');" />
							<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpd003001l.do');" />
							
							<select id="select_searchDiv" name="searchDiv" style="margin-left: 30px;">
								<option value="1" <c:if test="${purchaseVO.searchDiv eq '1'}">selected</c:if>>제품명</option>
								<option value="2" <c:if test="${purchaseVO.searchDiv eq '2'}">selected</c:if>>제품코드</option>
							</select>
							<input class="txt" id="input_searchText" name="searchText" type="text" value="${purchaseVO.searchText}"
								style="width:300px;" onkeydown="if(event.keyCode==13){c_submit('frm', 'sdpd003001l.do');}">
							<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpd003001l.do');">
						</div>
						<!--  gray_wrap -->
						<div>
							<div class="left_num_count" style="float:left; width:50%;"></div>
							<!-- left_num_count -->
							<input class="btn_move" style="float:right;" type="button" value="출력" onclick="javascript:popup_report('sdpd003001l_report.do');">
							<div style="float:right; margin-top:6px; margin-right:10px; height:100%; text-align: right;">※부가세 별도</div>
						</div>
						
						<div class="searchlist_wrap_tit">
							<table summary="주문조회" class="table_common">
								<caption>주문조회</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 80px;" />
									<col style="width: 60px;" />
									<col style="width: 40px;" />
									<col style="width: 110px;" />

									<col style="width: 200px;" />
									<col style="width: 50px;" />
									<col style="width: 50px;" />
									<col style="width: 80px;" />
									<col style="width: 80px;" />
									
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">
											<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
											<label class="blue_label" for="parentChkBox"></label>
										</th>
										<th scope="col">매입일자</th>
										<th scope="col">전표<br>번호</th>
										<th scope="col">순번</th>
										<th scope="col">제품코드</th>

										<th scope="col">품명</th>
										<th scope="col">단위</th>
										<th scope="col">수량</th>
										<th scope="col">단가</th>
										<th scope="col">금액</th>

										<th scope="col">비고</th>
										
									</tr>
								</thead>
							</table>
						</div>

						<div class="searchlist_wrap">
							<table summary="주문조회" class="table_common">
								<caption>주문조회</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 80px;" />
									<col style="width: 60px;" />
									<col style="width: 40px;" />
									<col style="width: 110px;" />

									<col style="width: 200px;" />
									<col style="width: 50px;" />
									<col style="width: 50px;" />
									<col style="width: 80px;" />
									<col style="width: 80px;" />
									
									<col style="" />
								</colgroup>
								<tbody id="tbody_list">
									<c:choose>
										<c:when test="${fn:length(purchaseInfoList) > 0}">
											<c:set var="sum" value="0"/>
											<c:forEach items="${purchaseInfoList}" var="row" varStatus="status">
												<c:choose>
													<c:when test="${fn:trim(row.item_cd) != null and fn:trim(row.item_cd) != ''}">
														<tr style="background-color:#f5f5f5;">
															<td class="txt_center">
																<input type="hidden" id="hid_item" name="hid_item" value="${row.item_cd}" />
																<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.ut_a}" />
																<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.ut_b}" />
																<input type="hidden" id="hid_description" name="hid_description" value="${row.item_nm}" />
																<input type="hidden" id="hid_qty" name="hid_qty" value="${row.qty}" />
															
																<input class="blue_checkbox" type="checkbox" id="chkBox_${status.count}" name="chkBox" />
																<label class="blue_label" for="chkBox_${status.count}"></label>
															</td>
															<td class="txt_center">${row.sale_dt}</td>
															<td class="txt_center">${row.sale_no}</td>
															<td class="txt_center">${row.seq}</td>
															<td class="txt_lef">${row.item_cd}</td>
															
															<td class="txt_lef">${row.item_nm}</td>
															<td class="txt_rig">${row.ut_a}${row.ut_b}</td>
															<td class="txt_rig">${row.qty}</td>
															<td class="txt_rig"><fmt:formatNumber value="${row.up}" groupingUsed="true"/></td>
															<td class="txt_rig"><fmt:formatNumber value="${row.amt}" groupingUsed="true"/></td>
															
															<td class="txt_lef">${row.dely_place}</td>
															<c:set var="sum" value="${sum + row.amt}" />
														</tr>
													</c:when>
													<c:otherwise>
														<c:if test="${purchaseVO.searchText == ''}">
															<tr>
																<td class="txt_center"></td>
																<td class="txt_center"></td>
																<td class="txt_center"></td>
																<td class="txt_center"></td>
																<td class="txt_lef"></td>
																
																<td class="txt_rig" style="font-weight: bold;">${row.item_nm}</td>
																<td class="txt_center"></td>
																<td class="txt_rig"></td>
																<td class="txt_rig"></td>
																<td class="txt_rig" style="font-weight: bold;">
																	<fmt:formatNumber value="${row.amt}" groupingUsed="true"/>
																</td>
																
																<td class="txt_lef">${row.dely_place}</td>
															</tr>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td class="pro_code txt_center" colspan="11">검색된 자료가 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<c:if test="${sum > 0 }">
							<table class="table_common">
								<colgroup>
									<col style="width:540px;"/>
									<col style="width:180px;"/>
									<col style=""/>
								</colgroup>
								<tbody>
									<tr style="background-color:#EAEAEA;">
										<td colspan="2" style="text-align:right; font-weight:bold;">합계 : </td>
										<td style="font-weight:bold;"><fmt:formatNumber value="${sum}" groupingUsed="true" /></td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<!-- //searchlist_wrap (게시물 리스트) -->
						
						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
<!-- 								<input class="btn_basket" type="button" id="btn_addBasketCheckd" value="오즈테스트" onclick="javascript:test();"> -->
								<input class="btn_basket" type="button" id="btn_addBasketCheckd" value="장바구니담기" onclick="javascript:addBasket(this);">
								<input class="btn_order" id="" type="button" value="주문서 작성" onclick="javascript:createOrder();">
							</div>
						</div>
						<!--bottom_btn_wrap-->
						
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