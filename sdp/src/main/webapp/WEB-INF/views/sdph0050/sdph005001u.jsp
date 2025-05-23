<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 샘플요청 등록&수정  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//입력시 현재일자 셋팅
		if("${flag}" == "insert") {
			settingDate($(".datepicker_aftToday"));
		}
		
		//체크박스가 변경될때마다 hidden 값 변경
		$("#chkBox_prod_after_yn").on("change", "", function(event) {
			if($("#chkBox_prod_after_yn").prop("checked")){
				$("#prod_after_yn").val("Y");
			} else {
				$("#prod_after_yn").val("N");
				
			}
		});
		
		//모든 체크박스 체크하고 화면 불러옴
		$("input[name='chkBox']").attr("checked", true);
		$("input[id='parentChkBox']").attr("checked", true);
		
	});
	
	var rowNum = 1;			//행추가용 Row의 번호
	var target_tr = "";		//팝업 호출시 버튼이 있는 tr id를 저장함 (addItem 펑션 참조)
 
	/* 
	 * 제품 검색 팝업창 호출
	 */
	function popup_itemList_01(selectType){
		var pop_itemList = window.open("sdpz000101p." + selectType + ".do?search_pgmOption=J", "pop_itemList", "width=760,height=805, scrollbars=yes, resizable=no");
		pop_itemList.focus();
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
					$(li_chkBox).parent().parent("tr").remove();
				}

				if ($("#tbody_list tr").length < 1) {
					$("#tbody_list").append("<tr id='tr_empty'><td class='pro_code txt_center' colspan='9'>추가된 제품이 없습니다</td></tr>");
					$("#p_listCnt").html("제품 : <strong>0</strong>건");
				} else {
					$("#p_listCnt").html("제품 : <strong>" + $("#tbody_list tr").length + "</strong>건");
				}
			} else { //no Click

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
		console.log("▶ parent.addItem called:", "aaa");
		console.log("▶ parent.addItem called:", jsonList);
		debugger;
		
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
	
	//취소하기
	function cancel() {
		if($("#jeonpyo_no").val() != null && $("#jeonpyo_no").val() != "") {
			c_submit('frm','sdpa004101d.do');
		} else {
			c_submit('frm','sdpa004001l.do');
		}
	}
	
	//제조의뢰 등록 & 수정
	function confirmSampleRequest() {
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
		
		//인수자와 전화번호 체크
		//if(!addrCheck()) {
		//	c_alert("주소가 입력된 경우 인수자와 전화번호를 반드시 입력해야 합니다");
		//	return;
		//}
		
		//데이터 검증
// 		if(!doFormValidate(document.frm)){
// 			return;
// 		}
		
		//주재 수량 검사
		for(var i=0; i < li_chkBox.length; i++) {
			checkQty = Number($(li_chkBox[i]).parent().parent().find("#input_qty1").val());
			if(checkQty < 1) {
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
						c_submit("frm", "sdpa004101u_insert.do");
					
					} else {
						c_submit("frm", "sdpa004101u_update.do");
					}
				} else { //no Click
					return;
				}
			});
			
		} else {
			if("${flag}" == "insert") {
				c_confirm("제조의뢰를 등록 하시겠습니까?").then(function(result) { //커스텀 confirm
					if (result) { //yes Click
						c_submit("frm", "sdpa004101u_insert.do");
					} else { //no Click
						return;
					}
				});

			} else {
				c_confirm("제조의뢰를 수정 하시겠습니까?").then(function(result) { //커스텀 confirm
					if (result) { //yes Click
						c_submit("frm", "sdpa004101u_update.do");
					} else { //no Click
						return;
					}
				});
			}
		}
	}
	//샘플의뢰등록 수정(db update)
// 	function updateSmplRequest() {
// 		//데이터 검증
// 		if(!doFormValidate(document.frm)){
// 			return;
// 		}
		
// 		//인수자와 전화번호 체크
// 		if(!addrCheck()) {
// 			c_alert("주소가 입력된 경우 인수자와 전화번호를 반드시 입력해야 합니다");
// 			return;
// 		}
		
// 		c_confirm("신규제조를 수정하시겠습니까?").then(function(result) { //커스텀 confirm
// 			if (result) { //yes Click
// 				c_submit("frm", "sdph005001u_update.do");
// 			} else { //no Click
// 				return;
// 			}
// 		});
// 	}
	 
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${sampleRequestVO.jeonpyo_no}" />
<input type="hidden" id="ilja" name="ilja" value="${sampleRequestVO.ilja}" />

<input type="hidden" id="searchDiv" name="searchDiv" value="${sampleRequestVO.searchDiv}" />
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${sampleRequestVO.searchDate_from}" />
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${sampleRequestVO.searchDate_to}" />

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">					
						<div class="local_nav_wrap">
					 		<h3 class="sub_tit">샘플 의뢰 등록</h3>		       				
			       				<div class="local_nav">
			                         <ul>
				                           <li class="home">홈</li>
				                           <li>주문관리</li>
				                           <li>샘플 의뢰 등록</li>
			                         </ul>
			                   </div>
			                   <!--local_nav-->		                   
	       				</div>
	       				<!--local_nav_wrap-->	
	         			<div class="sub_cont">		            
			
							<div class="orderlist_wrap_tit">
								<table class="table_common" summary="샘플의뢰서등록">
									<caption>샘플의뢰서등록</caption>
									<colgroup>
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 250px;" />
										<col style="width: 100px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>																
											<th scope="col">견본구분</th>		
											<th scope="col">품목코드</th>
											<th scope="col">품명</th>
											<th scope="col">포장단위</th>
											<th scope="col">포장수량</th>
											<th scope="col">유무상</th>
											<th scope="col">도편</th>
											<th scope="col">진도상황</th>
											<th scope="col">적용모델1</th>
											<th scope="col">적용모델2</th>
											<th scope="col">적용모델3</th>
											<th scope="col">적용모델4</th>
											<th scope="col">적용모델5</th>
											<th scope="col">적용모델6</th>
										</tr>
										<tr>
											<th class="tbl_row_tit" scope="col" rowspan="" style="border-right:1px solid #606c79;">작성일</th>
											<th class="tbl_row_tit" scope="col" colspan="" style="border-right:1px solid #606c79;">전표번호</th>
											<th class="tbl_row_tit" scope="col" colspan="">사업장</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="orderlist_wrap">
								<table class="table_common" id="table_item">
									<caption>주문 리스트</caption>
									<colgroup>
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 250px;" />
										<col style="width: 100px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="" />
									</colgroup>
									<tbody id="tbody_list">
							          <c:forEach var="sampleRequestItem" items="${sampleRequestItemList}" varStatus="status">									
										<tr id="tr_1">													
											<td class="txt_center"  colspan="1">
												<input type="text" id="sunbeon" name="sunbeon" 
													value="${sampleRequestItem.sunbeon}" title="순번" req/>
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" id="gyeonbon_gubun" name="gyeonbon_gubun" title="견본구분" 
													value="${sampleRequestItem.gyeonbon_gubun}"  req/>
											</td>									
 											<td class="txt_center"  colspan="1">
 												<input type="text" id="pummog_code" name="pummog_code" title="품목코드" 
 													value="${sampleRequestItem.pummog_code}"  req/>
 											</td>									
											<td class="pro_name" colspan="1">
												<input type="text" style="width:100%" id="pummyeong" name="pummyeong" value="${sampleRequestItem.pummyeong}" 
													maxlength="30" title="제품명" />
											</td>	
											<td class="txt_center" colspan="1">
												<input type="text" class="entry_f" id="po_danwi_a" name="po_danwi_a"
													value="${sampleRequestItem.po_danwi_a != null ? sampleRequestItem.po_danwi_a:0}" title="판매단위(수량)" req/>
												<select class="select" title="포장단위" id="po_danwi_b" name="pojang_danwi_b">
													<option value="LT" <c:if test="${'LT' == sampleRequestItem.po_danwi_b}"> selected</c:if>>LT</option>
													<option value="KG" <c:if test="${'KG' == sampleRequestItem.po_danwi_b}"> selected</c:if>>KG</option>
													<option value="EA" <c:if test="${'EA' == sampleRequestItem.po_danwi_b}"> selected</c:if>>EA</option>
												</select>
											</td>																															
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="po_su" name="po_su" title="주문수량" 
													value="${sampleRequestItem.po_su != null ? sampleRequestItem.po_su : 0}" req/>
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="price_yn" name="price_yn" title="유상여부" 
													value="${sampleRequestItem.price_yn}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="dopyeon_yn" name="dopyeon_yn" title="도편여부" 
													value="${sampleRequestItem.dopyeon_yn}" req/>
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="stat_nm" name="stat_nm" title="상태" 
													value="${sampleRequestItem.stat_nm}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="model_1" name="model_1" title="모델1" 
													value="${sampleRequestItem.model_1}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="model_2" name="model_2" title="모델2" 
													value="${sampleRequestItem.model_2}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="model_3" name="model_3" title="모델3" 
													value="${sampleRequestItem.model_3}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="model_4" name="model_4" title="모델4" 
													value="${sampleRequestItem.model_4}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="model_5" name="model_5" title="모델5" 
													value="${sampleRequestItem.model_5}" />
											</td>									
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="model_6" name="model_6" title="모델6" 
													value="${sampleRequestItem.model_6}" />
											</td>									
										  </tr>
										</c:forEach>
									</tbody>
								</table>	
							</div>
							<div class="bottom_btn_wrap">
							    <div class="right_btn_area">
    								<input class="btn_add" type="button" id="" value="품목 추가" onclick="popup_itemList_01('multiple');" />
								    <input class="btn_del2" type="button" id="" value="품목 삭제" onclick="removeItem();" />
     							</div>
						    </div>
   							<!--bottom_btn_wrap-->				
									
							<div class="order_subtit">주문자 정보</div>
												
							<div class="tbl_wrap">				
								<table class="tbl_input" summary="샘플의뢰 등록 입력폼">
									<caption>샘플의뢰서 등록</caption>
									<colgroup>
										<col style="width:15%" />
										<col style="width:25%" />
										<col style="width:15%" />
										<col style="width:45%">
									</colgroup>
									<tbody>
										<tr class="first">
											<th scope="row">사업장명</th>
																						<td class="last">${sampleRequest.saeobjang_nm}</td>
											<th scope="row">일자</th>
											<td class="last">
												<input type="text" class="ico_cal datepicker_aftToday" id="ilja" name="ilja" value="${sampleRequest.ilja}"
													         title="일자" req/>
											</td>
										</tr>
										<tr>
											<th scope="row">전표번호</th>
											<td class="last" colspan="1">
											  <input type="text" id="jeonpyo_no" name="jeonpyo_no" value="${sampleRequest.jeonpyo_no}"
											           title="전표번호" />
										  </td>
											<th scope="row">품목분류</th>
											<td class="last">
  										  <input type="text" id="pummog_bunryu" name="pummog_bunryu" value="${sampleRequest.pummog_bunryu}"
	    								         title="품목분류" />
                      </td>
										</tr>
										<tr>
											<th scope="row">핸드폰분류</th>
											<td class="last" colspan="1">
											  <input type="text" id="hp_bunryu" name="hp_bunryu" value="${sampleRequest.hp_bunryu}"
											           title="핸드폰분류" />
										  </td>
											<!--<th scope="row">거래처코드</th>
											<td class="last">
  										  <input type="text" id="geolaecheo_code" name="geolaecheo_code" value="${sampleRequest.geolaecheo_code}"
	    								         title="거래처코드" />
                      </td>-->
										</tr>

										<tr>
											<th scope="row">1차거래처코드</th>
											<td class="last" colspan="1">
											  <input type="text" id="geolaecheo_code" name="geolaecheo_code" value="${sampleRequest.geolaecheo_code}"
											           title="1차거래처코드" />
										  </td>
											<th scope="row">1차거래처상호</th>
											<td class="last">
  										  <input type="text" id="sangho" name="sangho" value="${sampleRequest.sangho}"
	    								         title="1차거래처상호" />
                      </td>
										</tr>
										<tr>
											<th scope="row">2차거래처코드</th>
											<td class="last" colspan="1">
											  <input type="text" id="geolaecheo_code_2" name="geolaecheo_code_2" value="${sampleRequest.geolaecheo_code_2}"
											           title="2차거래처코드" />
										  </td>
											<th scope="row">2차거래처상호</th>
											<td class="last">
  										  <input type="text" id="sangho_2" name="sangho_2" value="${sampleRequest.sangho_2}"
	    								         title="2차거래처상호" />
                      </td>
										</tr>
										<tr>
											<th scope="row">거래처실무자</th>
											<td class="last" colspan="1">
											  <input type="text" id="gogaeg_myeong" name="gogaeg_myeong" value="${sampleRequest.gogaeg_myeong}"
											           title="거래처실무자" />
										  </td>
											<th scope="row">영업담당자</th>
											<td class="last">
  										  <input type="text" id="balsinja" name="balsinja" value="${sampleRequest.balsinja}"
	    								         title="영업담당자" />
                      </td>
										</tr>
										<tr>
											<th scope="row">수신부서</th>
											<td class="last" colspan="1">
											  <input type="text" id="susin_buseo" name="susin_buseo" value="${sampleRequest.susin_buseo}"
											           title="수신부서" />
										  </td>
											<th scope="row">수신자</th>
											<td class="last">
  										  <input type="text" id="susinja" name="susinja" value="${sampleRequest.susinja}"
	    								         title="수신자" />
                      </td>
										</tr>
										<tr>
											<th scope="row">입회자</th>
											<td class="last" colspan="1">
											  <input type="text" id="ibhoija" name="ibhoija" value="${sampleRequest.ibhoija}"
											           title="입회자" />
										  </td>
											<th scope="row">납품일자</th>
											<td class="last">
  										  <input type="text" id="nabpum_ilja" name="nabpum_ilja" value="${sampleRequest.nabpum_ilja}"
	    								         title="납품일자" />
                      	<input type="text" class="ico_cal datepicker_aftToday" id="nabpum_ilja" name="nabpum_ilja" value="${sampleRequest.nabpum_ilja}"
													         title="납품일자" req/>	    								         
                      </td>
										</tr>

										<tr>
											<th scope="row">예상판매금액(만원)</th>
											<td class="last" colspan="1">
											  <input type="text" id="yesang_geumaeg" name="yesang_geumaeg" value="${sampleRequest.yesang_geumaeg}"
											           title="예상판매금액(만원)" />
										  </td>
											<th scope="row">도료사용금액(만원)</th>
											<td class="last">
  										  <input type="text" id="sayong_geumaeg" name="sayong_geumaeg" value="${sampleRequest.sayong_geumaeg}"
	    								         title="도료사용금액(만원)" />
                      </td>
										</tr>
										<tr>
											<th scope="row">희망가격(원)</th>
											<td class="last" colspan="1">
											  <input type="text" id="himang_gagyeog" name="himang_gagyeog" value="${sampleRequest.himang_gagyeog}"
											           title="희망가격(원)" />
										  </td>
											<th scope="row">경쟁회사</th>
											<td class="last">
  										  <input type="text" id="ex_geolaecheo" name="ex_geolaecheo" value="${sampleRequest.ex_geolaecheo}"
	    								         title="경쟁회사" />
                      </td>
										</tr>
										<tr>
											<th scope="row">타사견본유무</th>
											<td class="last" colspan="1">
											  <input type="text" id="ex_gyeonbon_yn" name="ex_gyeonbon_yn" value="${sampleRequest.ex_gyeonbon_yn}"
											           title="타사견본유무" />
										  </td>
											<th scope="row">도장SYSTEM</th>
											<td class="last">
  										  <input type="text" id="dojang_bangbeob" name="dojang_bangbeob" value="${sampleRequest.dojang_bangbeob}"
	    								         title="도장SYSTEM" />
                      </td>
										</tr>

										<tr>
											<th scope="row">현장도장공정</th>
											<td class="last" colspan="1">
											  <input type="text" id="dojang_gongjeong" name="dojang_gongjeong" value="${sampleRequest.dojang_gongjeong}"
											           title="현장도장공정" />
										  </td>
											<th scope="row">건조조건</th>
											<td class="last">
  										  <input type="text" id="geonjo_bangbeob" name="geonjo_bangbeob" value="${sampleRequest.geonjo_bangbeob}"
	    								         title="건조조건" />
                      </td>
										</tr>

										<tr>
											<th scope="row">도료TYPE</th>
											<td class="last" colspan="1">
											  <input type="text" id="doryo_type" name="doryo_type" value="${sampleRequest.doryo_type}"
											           title="도료TYPE" />
										  </td>
											<th scope="row">소재의 종류</th>
											<td class="last">
  										  <input type="text" id="sojae_jonglyu" name="sojae_jonglyu" value="${sampleRequest.sojae_jonglyu}"
	    								         title="소재의 종류" />
                      </td>
										</tr>


										<tr>
											<th scope="row">기타요구사항(도료)</th>
											<td class="last" colspan="1">
											  <input type="text" id="gita_yogu6" name="gita_yogu6" value="${sampleRequest.gita_yogu6}"
											           title="기타요구사항(도료)" />
										  </td>
											<th scope="row">기타요구사항(기술자 출장3</th>
											<td class="last">
  										  <input type="text" id="gita_yogu3" name="gita_yogu3" value="${sampleRequest.gita_yogu3}"
	    								         title="기타요구사항(기술자 출장3" />
                      </td>
										</tr>


										<tr>
											<th scope="row">비고1</th>
											<td class="last" colspan="1">
											  <input type="text" id="bigo_1" name="bigo_1" value="${sampleRequest.bigo_1}"
											           title="비고1" />
										  </td>
											<th scope="row">비고2</th>
											<td class="last">
  										  <input type="text" id="bigo_2" name="bigo_2" value="${sampleRequest.bigo_2}"
	    								         title="비고2" />
                      </td>
										</tr>



										<tr>
											<th scope="row">비고3</th>
											<td class="last" colspan="1">
											  <input type="text" id="bigo_3" name="bigo_3" value="${sampleRequest.bigo_3}"
											           title="비고3" />
										  </td>
											<th scope="row">결과등록기한</th>
											<td class="last">
  										  <input type="text" id="gyeolgwa_gihan" name="gyeolgwa_gihan" value="${sampleRequest.gyeolgwa_gihan}"
	    								         title="결과등록기한" />
                      </td>
										</tr>
									</tbody>
								</table>					
							</div>	
							
							<div class="btn_center_wrap">
								<c:choose>
									<c:when test="${flag == 'insert'}">
										<input type="button" class="btn_big_order" value="등록하기" id="" name="" onclick="confirmSampleRequest();"/>
										<input type="button" class="btn_big_cancel" value="취소하기" id="" name="" onclick="c_submit('frm', 'sdph005001l.do');" />
									</c:when>
									<c:otherwise>
										<input type="button" class="btn_big_order" value="수정하기" id="" name="" onclick="confirmSampleRequest();"/>
										<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdph005001d.do');" />
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

<!-- 시판품 추가용 임시 테이블 -->
<table id="hid_table1" class="table_common" style="display: none;">
	<colgroup>
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 250px;" />
										<col style="width: 100px;" />
										
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="" />
	</colgroup>
	<tbody>
		<tr>
			<td class="txt_center">
				<input type="hidden" id="hid_" name="hid_mappingCode" value="" >
				<input type="hidden" id="hid_item" name="hid_item" value="" >
				<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="" >
				<input type="hidden" id="hid_u_m" name="hid_u_m" value="" >
			
			 	<input class="blue_checkbox" type="checkbox" id="" name="chkBox" checked/>
				<label class="blue_label" for=""></label>  
			</td>
			<td class="txt_center" colspan="1">
				<input type="text" id="gyeonbon_gubun_1" name="gyeonbon_gubun" title="견본구분"  value=""  />
			</td>									
			<td class="pro_code" >
				<input type="text" id="td_item" name="pummog_code" title="품목코드" value=""  />
			</td>									
			<td class="pro_name" >
				<input type="text" id="td_description" name="pummyeong" value=""  title="제품명" />
			</td>	
			<td class="txt_center" colspan="1">
				<input type="text" class="entry_f" id="td_u_m_a" name="po_danwi_a"
					value="" title="판매단위(수량)" />
			</td>																															
			<td class="txt_center" colspan="1">
				<select class="select" title="포장단위" id="td_um_b" name="po_danwi_b">
					<option value="LT" <c:if test="${'LT' == sampleRequestItem.po_danwi_b}"> selected</c:if>>LT</option>
					<option value="KG" <c:if test="${'KG' == sampleRequestItem.po_danwi_b}"> selected</c:if>>KG</option>
					<option value="EA" <c:if test="${'EA' == sampleRequestItem.po_danwi_b}"> selected</c:if>>EA</option>
				</select>
			</td>																															
			<td class="txt_center"  >
				<input type="text" class="entry" id="td_po_su" name="po_su" title="주문수량" 
					value=0 />
			</td>									
			<td class="txt_center">
				<input type="text" class="entry" id="td_price_yn" name="price_yn" title="유상여부" 
					value="" />
			</td>									
			<td class="txt_center" >
				<input type="text" class="entry" id="td_dopyeon_yn" name="dopyeon_yn" title="도편여부" 
					value="" />
			</td>									
			<td class="txt_center" >
				<input type="text" class="entry" id="td_stat_nm" name="stat_nm" title="상태" 
					value="" />
			</td>									
			<td class="txt_center" ><input type="text" class="entry" id="td_model_1" name="model_1" title="모델1" value="" /></td>									
			<td class="txt_center" ><input type="text" class="entry" id="td_model_2" name="model_2" title="모델2" value="" /></td>									
			<td class="txt_center" ><input type="text" class="entry" id="td_model_3" name="model_3" title="모델3" value="" /></td>									
			<td class="txt_center" ><input type="text" class="entry" id="td_model_4" name="model_4" title="모델4" value="" /></td>									
			<td class="txt_center" ><input type="text" class="entry" id="td_model_5" name="model_5" title="모델5" value="" /></td>									
			<td class="txt_center" ><input type="text" class="entry" id="td_model_6" name="model_6" title="모델6" value="" /></td>									

		</tr>
	</tbody>
</table>
</html>