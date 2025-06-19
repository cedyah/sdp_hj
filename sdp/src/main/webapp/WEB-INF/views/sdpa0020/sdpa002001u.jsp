<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 주문서 작성 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />

<style type="text/css"></style>
<script type="text/javascript">
	var targetInput;

	$(document).ready(function() {
		ajaxRefreshQty_all();		//재고조회
		
		//입력시 현재일자 셋팅
		if("${flag}" == "insert") {
			settingDate($(".datepicker_aftToday"));
		}
		
		//주문번호가 있으면 완료 이후 분기 function 호출
		if("${jeonpyo_no}" != "") {
			c_confirm("주문이 완료되었습니다.<br>계속 주문하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					location.href = "sdpa001001l.standard.do";
				} else { //no Click
					c_submit("frm", "sdpa002001d.do");
				}
			});
		}
		
		//모든 체크박스 체크하고 화면 불러옴
// 		$("input[name='chkBox']").attr("checked", true);
// 		$("input[id='parentChkBox']").attr("checked", true);
		
		//input 클릭시 textarea가 표시되고 커서와 값이 옮겨감 (상세 textarea)
		$("#tbody_list").on("click", "input[name='input_bigo']" , function(event) {
// 			console.log($(event.target).offset().left);
// 			console.log($("#div_detailText").width());
// 			console.log($(event.target).width());
			$("#div_detailText").css("top", $(event.target).offset().top);
			$("#div_detailText").css("left", $(event.target).offset().left - ($("#div_detailText").width() - $(event.target).width()) + 15);

			targetInput = event.target;
			$("#div_detailText").show();

			$("#div_detailText input").val($(event.target).val());
			$("#div_detailText input").focus();
		});

		//textarea 에서 커서가 나가면 사라짐
		$("#div_detailText input").on("blur", function(event) {
			$("#div_detailText").hide();
			$(targetInput).val($("#div_detailText input").val());
			$(targetInput).attr("title", $("#div_detailText input").val());
		});
		
		//비고 텍스트 활성화
		$("#div_detailText input").on("keyup", function(event) {
			if(event.keyCode == '13') {	//엔터입력시 나가짐
				$("#div_detailText").hide();
				$(targetInput).val($("#div_detailText input").val());
				$(targetInput).attr("title", $("#div_detailText input").val());
				
			}
		});
		
		//보관품 출고 수량 제한
		$("#table_item").on("focusout","#input_qty2", function(event) {
			var targetNum = Number($(event.target).val().replace(/\,/g, ""));
			var maxNum = Number($(event.target).parent().parent().find("#td_keepOnHand").html().replace(/\,/g, ""));
			
			if(targetNum > maxNum) {
				c_alert("보관품재고 보다 많은 수량을 주문 할 수 없습니다");
				$(event.target).val(maxNum);
			}
		});
	});
	
	//주문서 등록 & 수정
	function confirmOrder() {
		var li_chkBox = $("#table_item input[name='chkBox']");		//주재 수량 체크를 위한 배열. 체크박스 배열
		var li_hidItem = $("#table_item input[name='hid_item']");		//전체 item을 배열에 담음. controller로 보내기 위함
		var jsonList = [];
		var item;
		var checkQty = 0;
		
		//주문 품목이 없는 경우 return
		if(li_chkBox.length < 1) {
			c_alert("품목을 추가해 주십시오");
			return;
		}
		
		//데이터 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		//인수자와 전화번호 체크
		if(!addrCheck()) {
			c_alert("주소가 입력된 경우 인수자와 전화번호를 반드시 입력해야 합니다");
			return;
		}
		
		//주재 수량 검사
		for(var i=0; i < li_chkBox.length; i++) {
			checkQty = Number($(li_chkBox[i]).parent().parent().find("#input_qty1").val()) + Number($(li_chkBox[i]).parent().parent().find("#input_qty2").val());
			if(checkQty < 1) {
				$(li_chkBox[i]).parent().parent().find("#input_qty1").val("");
				$(li_chkBox[i]).parent().parent().find("#input_qty1").focus();
				c_alert("수량을 입력해 주십시오<br>품명 : " + $(li_chkBox[i]).parent().parent().find("#td_description").html());
				return;
			}
		}
		
		//주재 및 부재를 모두 배열에 담음
		for(var i=0; i < li_hidItem.length; i++) {
// 			console.log($(li_hidItem[i]).val());
			item = {
				item : $(li_hidItem[i]).val()
				, qty_allocjob : $(li_hidItem[i]).parent().parent().find("#hid_qty_allocjob").val() 
				, u_m : $(li_hidItem[i]).parent().parent().find("#hid_u_m").val()
				, description : $(li_hidItem[i]).parent().parent().find("#td_description").html()
				, qty_input1 : $(li_hidItem[i]).parent().parent().find("#input_qty1").val()
				, qty_input2 : $(li_hidItem[i]).parent().parent().find("#input_qty2").val()
				, bigo : $(li_hidItem[i]).parent().parent().find("#input_bigo").val()
			}
// 			console.log(item);
			jsonList.push(item);
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		
		//주소 미입력시 알림
		if(($("#addr1").val().length + $("#addr2").val().length) < 1) {
			c_confirm("주소가 입력되지 않았습니다. 계속 하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					if("${flag}" == "insert") {
						c_submit("frm", "sdpa002001u_insert.do");
					
					} else {
						c_submit("frm", "sdpa002001u_update.do");
					}
				} else { //no Click
					return;
				}
			});
			
		} else {
			if("${flag}" == "insert") {
				c_confirm("주문 하시겠습니까?").then(function(result) { //커스텀 confirm
					if (result) { //yes Click
						c_submit("frm", "sdpa002001u_insert.do");
					} else { //no Click
						return;
					}
				});

			} else {
				c_confirm("수정 하시겠습니까?").then(function(result) { //커스텀 confirm
					if (result) { //yes Click
						c_submit("frm", "sdpa002001u_update.do");
					} else { //no Click
						return;
					}
				});
				
			}
		}
	}
	
	//주문서에 등록된 품목 제거
	function removeItem() {
		var li_chkBox = $("#table_item input[name='chkBox']:checked");
		
		if (li_chkBox.length < 1) {
			c_alert("삭제할 품목을 선택해 주십시오");
			return;
		}

		c_confirm("해당 품목을 목록에서 삭제하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				for (var i = 0; i < li_chkBox.length; i++) {
					$(li_chkBox[i]).parent().parent("tr").remove();		//주재 tr 삭제
				}

				if ($("#tbody_list tr").length < 1) {
					$("#tbody_list").append("<tr id='tr_empty'><td class='pro_code txt_center' colspan='10'>추가된 제품이 없습니다</td></tr>");
					$("#p_listCnt").html("주문상품 : <strong>0</strong>건");
				} else {
					$("#p_listCnt").html("주문상품 : <strong>" + $("#tbody_list tr").length + "</strong>건");
				}
			} else { //no Click
				return;
			}
		});
	}

	
	//팝업창에서 추가한 아이템들을 주문서 목록에 추가
	function addItem(text) {
		
		var jsonList = text;
		var obj;
		var li_mappingCode = $("#table_item input[name='hid_mappingCode']");	//화면에 mapping 코드를 object로 불러와 추가할 아이템 목록과 비교하여 중복을 방지하기 위함
		var li_mappingCodeVal = [];		//mapping의 value값을 저장하기 위한 배열
		var li_duplChk = [];		//기존에 이미 추가 되어 있는 아이템들 목록을 alert으로 뿌리기 위해 배열로 저장
		
		for(var i=0; i < li_mappingCode.length; i++) {
			li_mappingCodeVal.push($(li_mappingCode[i]).val());
		}
		
		for(var i=0; i < jsonList.length; i++) {
		
			//기존에 추가 되어 있는 아이템인지 검색 if:행추가X else:행추가O
			if(li_mappingCodeVal.indexOf(jsonList[i].item + jsonList[i].qty_allocjob + jsonList[i].u_m) >= 0) {
				li_duplChk.push(jsonList[i].description);

			} else {
				$("#hid_table1").find("input[name='chkBox']").attr("id", "chkBox_" + jsonList[i].item + jsonList[i].qty_allocjob + jsonList[i].u_m);
				$("#hid_table1").find("input[name='hid_mappingCode']").attr("id", "hid_" + jsonList[i].item + jsonList[i].qty_allocjob + jsonList[i].u_m);
				$("#hid_table1").find("input[name='hid_mappingCode']").val(jsonList[i].item + jsonList[i].qty_allocjob + jsonList[i].u_m);
				$("#hid_table1").find("label").attr("for", "chkBox_" + jsonList[i].item + jsonList[i].qty_allocjob + jsonList[i].u_m);
				$("#hid_table1").find("input[name='hid_item']").val(jsonList[i].item );
				$("#hid_table1").find("input[name='hid_qty_allocjob']").val(jsonList[i].qty_allocjob);
				$("#hid_table1").find("input[name='hid_u_m']").val(jsonList[i].u_m);
				$("#hid_table1").find("#td_item").html(jsonList[i].item);
				$("#hid_table1").find("#td_description").html(jsonList[i].description);
				$("#hid_table1").find("#td_u_m").html(jsonList[i].qty_allocjob + jsonList[i].u_m);
				
				$("#tbody_list").append($("#hid_table1 tbody").html());
				$("#tr_empty").remove();			//빈칸용 tr 삭제
			}
		}
		
		var trList = $("#table_item tbody tr");
		$("#p_listCnt").html("주문상품 : <strong>" + $("#tbody_list tr").length + "</strong>건");		//행 갯수 갱신
		
		ajaxRefreshQty_all();
		
		return li_duplChk;
	}
	
	//주문취소
	function cancelOrder(){
		if("${flag}" == "insert") {
			c_confirm("주문서 작성을 취소하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					history.back(); 
				} else { //no Click
					return;
				}
			});
			
		} else {
			c_confirm("주문서 수정을 취소하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					history.back(); 
				} else { //no Click
					return;
				}
			});
		}
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
	<input type="hidden" id="paramList" name="paramList" value="">
	<input type="hidden" id="jsonList" name="jsonList" value="${jsonList}">
	<input type="hidden" id="ord_num" name="ord_num" value="${ord_num}">
	<input type="hidden" id="pageCheck" name="pageCheck" value="${pageCheck}">
	
	<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${co.jeonpyo_no}">
	<input type="hidden" id="ilja" name="ilja" value="${co.ilja}">
	

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">주문서 등록</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>  
								<li>주문관리</li>
								<li>주문서 등록</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--<h3 class="sub_tit">장바구니</h3> -->
					<div class="sub_cont">

						<div class="search_btn_wrap">
							<p class="left_num_count" id="p_listCnt">주문상품 : <strong>${fn:length(coItemList)}</strong>건</p>
						</div>

						<!-- board_list_wrap (게시물 리스트) -->
						<div class="searchlist_wrap_tit">
							<table class="table_common" summary="주문서작성">
								<caption>주문서작성</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 300px;" />
									<col style="width: 80px;" />
									<col style="width: 70px;" />
									
									<col style="width: 70px;" />
									<col style="width: 60px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col" rowspan="2">
<!-- 											<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');" /> -->
<!-- 											<label class="blue_label" for="parentChkBox"></label> -->
										</th>
										<th scope="col" rowspan="2">제품코드</th>
										<th scope="col" rowspan="2">품명</th>
										<th scope="col" rowspan="2">판매단위</th>
										<th scope="col" colspan="2" class="tbl_col_tit">재고수량</th>
										
										<th scope="col" rowspan="2">출고량</th>
										<th scope="col" rowspan="2">제품별 비고</th>
									</tr>
									<tr>
										<jsp:directive.include file="/WEB-INF/views/templates/include_itemTitle.jsp" />
									</tr>
								</thead>
							</table>
						</div>

						<div class="searchlist_wrap">
							<table class="table_common" id="table_item" summary="주문 리스트">
								<caption>주문 리스트</caption>
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 300px;" />
									<col style="width: 80px;" />
									<col style="width: 70px;" />
									
									<col style="width: 60px;" />
									<col style="" />
								</colgroup>
								<tbody id="tbody_list">
									<c:choose>
										<c:when test="${fn:length(coItemList) > 0 }">
											<c:forEach items="${coItemList}" var="row" varStatus="status">
												<tr>
													<td class="txt_center">
														<input type="hidden" id="hid_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}"
															name="hid_mappingCode" value="${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}" >
														<input type="hidden" id="hid_item" name="hid_item" value="${row.jepum_code}" >
														<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.panmae_danwi_a}" >
														<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.panmae_danwi_b}" >
													
														<input class="blue_checkbox" type="checkbox" id="chkBox_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}" name="chkBox" />
														<label class="blue_label" for="chkBox_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}"></label>
													</td>
													<td class="pro_code" id="td_item">${row.jepum_code}</td>
													<td class="pro_name" id="td_description">${row.pummyeong}</td>
													<td class="txt_rig" id="td_u_m">${row.panmae_danwi_a}${row.panmae_danwi_b}</td>
													<td class="txt_rig ft_grey" id="td_qtyOnHand01">0</td>
													
													<td class="txt_rig ft_grey" id="td_qtyOnHand02">0</td>
													<!--<td class="txt_rig ft_grey" id="td_keepOnHand">0</td> -->
													<td class="txt_center">
														<input type="text" class="entry" id="input_qty1" name="input_qty1" value="${row.panmae_sulyang}" title="주문수량"/>
													</td>
													<!-- <td class="txt_center">
														<input type="text" class="entry" id="input_qty2" name="input_qty2"  value="${row.bo_sulyang}" title="보관품출고">
													</td>-->
													<td class="txt_center">
														<input class="order_key" type="text" id="input_bigo" name="input_bigo" placeholder="텍스트 입력..."
															value="${row.bigo}" style="width:100%;" readonly="readonly"/>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" ><td class="pro_code txt_center" colspan="10">추가된 제품이 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
								<input class="btn_add" type="button" id="" value="품목 추가" onclick="popup_itemList('multiple');" />
								<input class="btn_del2" type="button" id="" value="품목 삭제" onclick="removeItem();" />
							</div>
						</div>
						<!--bottom_btn_wrap-->

						<div class="order_subtit">주문자 정보</div>

						<!-- //searchlist_wrap (게시물 리스트) -->
						<div class="tbl_wrap">
							<table class="tbl_basic" summary="주문서 등록 입력폼">
								<colgroup>
									<col style="width:15%" />
									<col style="width:45%" />
									<col style="width:15%" />
									<col style="width:45%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">배달구분</th>
										<td class="last">
											<select class="select" title="배달구분" id="baedal_gubun" name="baedal_gubun">
												<c:if test="${fn:length(code10) > 0}">
													<c:forEach items="${code10}" var="row" varStatus="status">
														<option value="${row.code}"
															<c:if test="${fn:trim(co.baedal_gubun) == row.code}"> selected</c:if> >${row.name}</option>
													</c:forEach>
												</c:if>
											</select>
										</td>
										<th scope="row">배달요청일</th>
										<td class="last">
											<input type="text" class="ico_cal datepicker_aftToday" id="yocheongil" name="yocheongil" readonly="readonly" 
												value="${co.yocheongil}" title="배달요청일" req/>
											<input type="text" id="yocheong_hh" name="yocheong_hh"  style="width:40px"
												value="${co.yocheong_hh}" title="배달요청시" req/>시
											<input type="text" id="yocheong_mm" name="yocheong_hh"   style="width:40px"
												value="${co.yocheong_mm}" title="배달요청분" req/>분
										</td>
									</tr>
									<tr>
										<th scope="row">판매구분</th>
										<td class="last">
											<select class="select" title="판매구분" id="panmae_gubun" name="panmae_gubun">
												<c:if test="${fn:length(code11) > 0}">
													<c:forEach items="${code11}" var="row" varStatus="status">
														<option value="${row.code}"
															<c:if test="${fn:trim(co.panmae_gubun) == row.code}"> selected</c:if> >${row.name}</option>
													</c:forEach>
												</c:if>
											</select>
										</td>
										<th scope="row">화폐코드</th>
										<td class="last">
											<select class="select" title="화폐코드" id="hwapye_code" name="hwapye_code">
												<c:if test="${fn:length(code4900) > 0}">
													<c:forEach items="${code4900}" var="row" varStatus="status">
														<option value="${row.code}"
															<c:if test="${fn:trim(co.hwapye_code) == row.code}"> selected</c:if> >${row.name}</option>
													</c:forEach>
												</c:if>
											</select>
										</td>
									</tr>
									<tr>
										<th scope="row">배달장소</th>
										<td class="last" colspan="3">
											<input class="btn_del2" type="button" id="" value="주소 초기화" onclick="removeAddr();" />
											<input type="button" class="order_zipnum" onclick="javascript:popup_searchAddress(); return false;"/>
											<input type="text" class="order_zip readonly" id="zip" name="zip" onfocus="this.blur();" value="${co.zip}" title="주소" readonly/>
											<input type="button" class="order_addlist" value="주소록" onclick="popup_manageAddr();"/>&nbsp;※ 사업장(점포) 소재지에서 인수할 경우 입력하지 마세요
											<input type="text" class="order_add readonly" id="addr1"  name="addr1" onfocus="this.blur();" value="${co.addr1}" readonly/>
											<input type="text" class="order_add2" id="addr2"  name="addr2" value="${co.addr2}" maxlength="50"/>
										</td>
									</tr>
									<tr>
										<th scope="row">인수자</th>
										<td class="last"><input type="text" id="insuja" name="insuja" maxlength="10" value="${co.insuja}" title="인수자"/></td>
										<th scope="row">전화번호</th>
										<td class="last">
											<input type="text" class="phone" id="tel_no" name="tel_no" placeholder="예시 : 01012345678" value="${co.tel_no}" title="전화번호"/>
										</td>
									</tr>
									<tr>
										<th scope="row">비고</th>
										<td class="last" colspan="3">
											<textarea id="bigo" name="bigo" maxlength="30" style="height:140px; width:900px;" placeholder="비고는 30자까지 입력 가능합니다">${co.bigo}</textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="btn_center_wrap">
							<c:choose>
								<c:when test="${fn:trim(flag) == 'insert'}">
									<input type="button" class="btn_big_order" value="주문등록" onclick="javascript:confirmOrder();"/>&nbsp;
									<input type="button" class="btn_big_cancel" value="취소" onclick="javascript:cancelOrder();"/>
								</c:when>
								<c:otherwise>
									<input type="button" class="btn_big_order" value="수정" onclick="javascript:confirmOrder();"/>&nbsp;
									<input type="button" class="btn_big_cancel" value="취소" onclick="javascript:cancelOrder();"/>
								</c:otherwise>
							</c:choose>
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
<div id="div_detailText" style="display:none; width:470px; height:30px; position:absolute;">
	<input type="text" style="width:100%;height: 100%; overflow: hidden; background: #F6F6F6;" maxlength="30" placeholder="품목별 비고는 30자까지 입력 가능합니다">
</div>

<!-- 시판품 추가용 임시 테이블 -->
<table id="hid_table1" class="table_common" style="display: none;">
	<colgroup>
		<col style="width: 40px;" />
		<col style="width: 100px;" />
		<col style="width: 300px;" />
		<col style="width: 60px;" />
		<col style="width: 80px;" />
		
		<col style="width: 80px;" />
		<col style="width: 80px;" />
		<col style="width: 60px;" />
		<col style="width: 60px;" />
		<col style="" />
	</colgroup>
	<tbody>
		<tr>
			<td class="txt_center">
				<input type="hidden" id="hid_mappingCode" name="hid_mappingCode" value="" >
				<input type="hidden" id="hid_item" name="hid_item" value="" >
				<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="" >
				<input type="hidden" id="hid_u_m" name="hid_u_m" value="" >
				
				<input class="blue_checkbox" type="checkbox" id="" name="chkBox" />
				<label class="blue_label" for=""></label>
			</td>
			<td class="pro_code" id="td_item"></td>
			<td class="pro_name" id="td_description"></td>
			<td class="txt_rig" id="td_u_m"></td>
			<td class="txt_rig ft_grey" id="td_qtyOnHand01">0</td>
			
			<td class="txt_rig ft_grey" id="td_qtyOnHand02">0</td>
			<!-- <td class="txt_rig ft_grey" id="td_keepOnHand">0</td> -->
			<td class="txt_center">
				<input type="text" class="entry" id="input_qty1" name="input_qty1" value="0" title="주문수량"/>
			</td>
			<!--<td class="txt_center">
				<input class="entry" id="input_qty2" name="input_qty2" type="text" value="0" title="보관품출고">
			</td>-->
			<td class="txt_center">
				<input class="order_key" type="text" id="input_bigo" name="input_bigo" placeholder="텍스트 입력..."
					style="width:100%;" readonly="readonly"/>
			</td>
		</tr>
	</tbody>
</table>

</html>