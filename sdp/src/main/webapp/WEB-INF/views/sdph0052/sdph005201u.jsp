<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 샘플결과 등록&수정  -->
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
		  console.log("=== addItem 함수 시작 ===");
		    console.log("전달받은 데이터:", text);
		    console.log("데이터 타입:", typeof text);
		    console.log("배열 길이:", text.length);
		    
		    if (text.length > 0) {
		        console.log("첫 번째 아이템:", text[0]);
		        console.log("첫 번째 아이템의 item 값:", text[0].item);
		    }
		
		
	    var jsonList = text;
	    var obj;
	    var li_mappingCode = $("#table_item input[name='hid_mappingCode']");
	    var li_mappingCodeVal = [];
	    var li_duplChk = [];
	    
	    console.log("▶ parent.addItem called1:", jsonList);
	    
	    // 기존 매핑 코드 수집
	    for(var i=0; i < li_mappingCode.length; i++) {
	        li_mappingCodeVal.push($(li_mappingCode[i]).val());
	    }
	    
	    for(var i=0; i < jsonList.length; i++) {
	        console.log("▶ parent.addItem called:2", i.toString());
	        
	        var mappingKey = jsonList[i].item + jsonList[i].qty_allocjob + jsonList[i].u_m;
	        
	        // 중복 체크
	        if(li_mappingCodeVal.indexOf(mappingKey) >= 0) {
	            li_duplChk.push(jsonList[i].description);
	            console.log("▶ 중복 품목:", jsonList[i].description);
	        } else {
	            // 템플릿 복사 (clone 사용으로 개선)
	            var newRow = $("#hid_table1 tbody tr").clone();
	            
	            // 고유 ID 생성을 위한 타임스탬프 추가
	            var uniqueId = "item_" + Date.now() + "_" + i;
	            
	            // hidden 필드들 설정
	            newRow.find("input[name='hid_mappingCode']")
	                .attr("id", "hid_" + mappingKey)
	                .val(mappingKey);
	            
	            newRow.find("input[name='hid_item']")
	                .attr("id", "hid_item_" + uniqueId)
	                .val(jsonList[i].item);
	            newRow.find("input[name='hid_qty_allocjob']")
	                .attr("id", "hid_qty_allocjob_" + uniqueId)
	                .val(jsonList[i].qty_allocjob);
	            newRow.find("input[name='hid_u_m']")
	                .attr("id", "hid_u_m_" + uniqueId)
	                .val(jsonList[i].u_m);
            
	            newRow.find("input[name='hid_description']")
	                .attr("id", "hid_description_" + uniqueId)
	                .val(jsonList[i].description);
	            
	            // 체크박스 설정
	            newRow.find("input[name='chkBox']")
	                .attr("id", "chkBox_" + mappingKey);
	            
	            newRow.find("label")
	                .attr("for", "chkBox_" + mappingKey);
	            
	            // 화면에 표시되는 데이터 설정
	            newRow.find(".pro_code").text(jsonList[i].item);
	            newRow.find(".pro_name").text(jsonList[i].description);
	            
	            // td 요소에 직접 값 설정하는 부분들 수정
	            newRow.find("td").each(function() {
	                var $td = $(this);
	                if ($td.hasClass("pro_code")) {
	                    $td.text(jsonList[i].item);
	                } else if ($td.hasClass("pro_name")) {
	                    $td.text(jsonList[i].description);
	                }
	            });
	            
	            newRow.find("td#td_u_m").text(jsonList[i].qty_allocjob + jsonList[i].u_m);

	            // 다른 필드들도 기본값 설정
	            newRow.find("input[name='td_model_1']").val("9");
	            newRow.find("input[name='td_model_2']").val("9");
	            newRow.find("input[name='td_model_3']").val("9");
	            newRow.find("input[name='td_model_4']").val("9");
	            newRow.find("input[name='td_model_5']").val("9");
	            
	            console.log("▶ 추가되는 품목코드:", jsonList[i].item);
	            console.log("▶ 추가되는 품목명:", jsonList[i].description);

	            // 테이블에 행 추가
	            $("#tbody_list").append(newRow);
	            $("#tr_empty").remove(); // 빈칸용 tr 삭제
	        }
	    }
	    
	    console.log("loop end");
	    
	    // 행 갯수 갱신
	    var trCount = $("#tbody_list tr").length;
	    $("#p_listCnt").html("주문상품 : <strong>" + trCount + "</strong>건");
	    
	    // 중복 품목이 있다면 알림
	    if(li_duplChk.length > 0) {
	        c_alert("이미 추가된 품목입니다:<br>" + li_duplChk.join("<br>"));
	    }
	    
	    console.log("▶ ajaxRefreshQty_all 호출 전");
	    
	    // 수량 새로고침 함수가 있다면 호출
	    if(typeof ajaxRefreshQty_all === 'function') {
	        ajaxRefreshQty_all();
	    }
	    
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
		console.log("▶ confirmSampleRequest1");
	
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
 			console.log($(li_hidItem[i]).val());
 			item = {
 					  item : $(li_hidItem[i]).val(),
 					  qty_allocjob : $(li_hidItem[i]).closest('tr').find("input[name='hid_qty_allocjob']").val(),
 					  u_m : $(li_hidItem[i]).closest('tr').find("input[name='hid_u_m']").val(),
 					  description : $(li_hidItem[i]).closest('tr').find("input[name='hid_description']").val(),
 					  qty_input1 : $(li_hidItem[i]).closest('tr').find("input[name='po_su']").val()
 					}
 			console.log(item);
			jsonList.push(item);
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		
		//주소 미입력시 알림
// 		if(($("#addr1").val().length + $("#addr2").val().length) < -1) {
// 			c_confirm("주소가 입력되지 않았습니다. 계속 하시겠습니까?").then(
// 		   function(result) { //커스텀 confirm
// 				if (result) { //yes Click
// 					if("${flag}" == "insert") {
// 						c_submit("frm", "sdpa004101u_insert.do");
					
// 					} else {
// 						c_submit("frm", "sdpa004101u_update.do");
// 					}
// 				} else { //no Click
// 					return;
// 				}
// 			});
			
//  		} else {
 			if("${flag}" == "insert") {
 				c_confirm("샘플출고의뢰를 등록 하시겠습니까?").then(function(result) { //커스텀 confirm
 					if (result) { //yes Click
 						c_submit("frm", "sdph005201u_insert.do");
 					} else { //no Click
 						return;
 					}
 				});

 			} else {
 				c_confirm("샘플출고의뢰를 수정 하시겠습니까?").then(function(result) { //커스텀 confirm
 					if (result) { //yes Click
 						c_submit("frm", "sdpa005201u_update.do");
 					} else { //no Click
 						return; 
 					}
 				});
 			}
 		//}
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
// 				c_submit("frm", "sdph005201u_update.do");
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

    <input type="hidden" id="paramList" name="paramList" value="">
	<input type="hidden" id="jsonList" name="jsonList" value="${jsonList}">
	<input type="hidden" id="pageCheck" name="pageCheck" value="${pageCheck}">
	
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
										<col style="width: 5%;" /><!--  V  -->
										<col style="width: 10%;" />
										<col style="width: 10%;" />
										<col style="width: 30%;" />
										<col style="width: 15%;" />
										
										<col style="width: 10%;" /><!--  수량-->
										<col style="width: 10%;" />
										<col style="width: 10%;" />
										<col style="" /> <!--  적용모델1-->
									</colgroup>
									<thead>
										<tr>
											<th scope="col">V</th>																
											<th scope="col">견본구분</th>		
											<th scope="col">품목코드</th>
											<th scope="col">품명</th>
											<th scope="col">포장단위</th>

											<th scope="col">포장수량</th>
											<th scope="col">유무상</th>
											<th scope="col">도편</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="orderlist_wrap">
								<table class="table_common" id="table_item">
									<caption>주문 리스트</caption>
									<colgroup>
									    <col style="width: 5%;" /><!--  V  -->
										<col style="width: 10%;" />
										<col style="width: 10%;" />
										<col style="width: 30%;" />
										<col style="width: 15%;" />
										
										<col style="width: 10%;" /><!--  수량-->
										<col style="width: 10%;" />
										<col style="width: 10%;" />
									</colgroup>
									<tbody id="tbody_list">
							          <c:forEach var="sampleRequestItem" items="${sampleRequestItemList}" varStatus="status">									
										<tr id="tr_1">													
                                            <td class="txt_center"   >
												<input type="hidden" id="hid_${sampleRequestItem.pummog_code}${sampleRequestItem.po_danwi_a}${sampleRequestItem.po_danwi_b}"
														name="hid_mappingCode" value="${sampleRequestItem.pummog_code}${sampleRequestItem.po_danwi_a}${sampleRequestItem.po_danwi_b}" >
												<input  id="hid_item" name="hid_item" value="${sampleRequestItem.pummog_code}" >
												<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${sampleRequestItem.po_danwi_a}" >
												<input type="hidden" id="hid_u_m" name="hid_u_m" value="${sampleRequestItem.po_danwi_b}" >
												<input type="hidden" id="hid_description" name="hid_description" value="${sampleRequestItem.pummyeong}" >
													
												<input class="blue_checkbox" type="checkbox"   id="chkBox_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}" name="chkBox" />
												<label class="blue_label" for="chkBox_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}"></label>
											</td>		 
											<td class="last">
                                                <select class="select" style="width:100%;" title="견본구분" id="gyeonbon_gubun" name="gyeonbon_gubun">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(code4007) > 0}">
                                                        <c:forEach items="${code4007}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestItem.gyeonbon_gubun) == row.code}"> selected</c:if> >${row.name}</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
                                            <td class="pro_code"    id="td_item" >${sampleRequestItem.pummog_code}</td>
                                            <td class="pro_name"    id="td_description">${sampleRequestItem.pummyeong}</td>
											<td class="txt_center" colspan="1"    >
											
         									<td class="txt_rig" id="td_u_m">${sampleRequestItem.po_danwi_a}${sampleRequestItem.po_danwi_b}</td>
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="po_su" name="po_su" title="주문수량"  style="width:100%;" 
													value="${sampleRequestItem.po_su != null ? sampleRequestItem.po_su : 0}" req/>
											</td>								
										    <td class="last">
                                                <select class="select" title="유상여부" id="price_yn" name="price_yn"  style="width:100%;">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(code4030) > 0}">
                                                        <c:forEach items="${code4030}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestItem.price_yn) == row.code}"> selected</c:if> >${row.name}</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>					
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="dopyeon_yn" name="dopyeon_yn" title="도편여부"  style="width:100%;" 
													value="${sampleRequestItem.dopyeon_yn}" req/>
											</td>									
											
																					  </tr>
										</c:forEach>
									</tbody>
								</table>	
							</div>
							<div class="bottom_btn_wrap">
							    <div class="right_btn_area">
    							<!-- 	<input class="btn_add" type="button" id="" value="품목 추가" onclick="popup_itemList_01('multiple');" /> -->
    							    <input class="btn_add" type="button" id="" value="품목 추가" onclick="popup_itemList('multiple');" /> 
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
											<td class="last">
												<select class="select" style="width:100%;" title="사업장명" id="saeobjang_nm" name="saeobjang_nm">
                                                    <c:if test="${fn:length(code0031) > 0}">
                                                        <c:forEach items="${code0031}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestVO.saeobjang) == row.code}"> selected</c:if> >${row.name}</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
											</td class="last">
											<th scope="row">일자</th>
											<td class="last">
												<input type="text" class="ico_cal datepicker_aftToday" id="ilja" name="ilja" value="${sampleRequestVO.ilja}"
													         title="일자" req/>
											</td>
										</tr>
										<tr>
											<th scope="row">전표번호</th>
											<td class="last" colspan="1">
											  <input type="text" id="jeonpyo_no" name="jeonpyo_no" value="${sampleRequestVO.jeonpyo_no}"
											           title="전표번호" />
										  </td>
										  <th scope="row">품목분류</th>
											<td class="last">
                                                <select class="select" title="품목분류" style="width:150px;" id="pummog_bunryu" name="pummog_bunryu">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(code4901) > 0}">
                                                        <c:forEach items="${code4901}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestVO.pummog_bunryu) == row.code}"> selected</c:if> >${row.name}        </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
										</tr>
										<tr>
											<th scope="row">핸드폰분류</th>
											<td class="last">
                                                <select class="select" style="width:150px;" title="핸드폰분류" id="hp_bunryu" name="hp_bunryu">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(code4902) > 0}">
                                                        <c:forEach items="${code4902}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestVO.gyeonbon_gubun) == row.code}"> selected</c:if> >${row.name}        </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
										  
											<!--<th scope="row">거래처코드</th>
											<td class="last">
  										  <input type="text" id="geolaecheo_code" name="geolaecheo_code" value="${sampleRequestVO.geolaecheo_code}"
	    								         title="거래처코드" />
                      </td>-->
										</tr>

										<tr>
											<th scope="row">1차거래처코드</th>
											<td class="last" colspan="1">
											  <input type="text" id="geolaecheo_code" name="geolaecheo_code" value="${sampleRequestVO.geolaecheo_code}"
											           title="1차거래처코드" />
										  </td>
											<th scope="row">1차거래처상호</th>
											<td class="last">
  										  <input type="text" id="sangho" name="sangho" value="${sampleRequestVO.sangho}"
	    								         title="1차거래처상호" />
                      </td>
										</tr>
										<tr>
											<th scope="row">2차거래처코드</th>
											<td class="last" colspan="1">
											  <input type="text" id="geolaecheo_code_2" name="geolaecheo_code_2" value="${sampleRequestVO.geolaecheo_code_2}"
											           title="2차거래처코드" />
										  </td>
											<th scope="row">2차거래처상호</th>
											<td class="last">
  										  <input type="text" id="sangho_2" name="sangho_2" value="${sampleRequestVO.sangho_2}"
	    								         title="2차거래처상호" />
                      </td>
										</tr>
										<tr>
											<th scope="row">거래처실무자</th>
											<td class="last" colspan="1">
											  <input type="text" id="gogaeg_myeong" name="gogaeg_myeong" value="${sampleRequestVO.gogaeg_myeong}"
											           title="거래처실무자" />
										  </td>
											<th scope="row">영업담당자</th>
											<td class="last">
                                                <select class="select" title="영업담당자" id="balsinja" name="balsinja">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(codeBalsinja) > 0}">
                                                        <c:forEach items="${codeBalsinja}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestVO.balsinja) == row.code}"> selected</c:if> >${row.name}      </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
										</tr>
										<tr>
											<th scope="row">수신부서</th>
											<td class="last">
                                                <select class="select" title="수신부서" id="susin_buseo" name="susin_buseo">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(codeSusinBuseo) > 0}">
                                                        <c:forEach items="${codeSusinBuseo}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestVO.susin_buseo) == row.code}"> selected</c:if> >${row.name}      </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
											<th scope="row">수신자</th>
											<td class="last">
                                                <select class="select" title="수신자" id="susinja" name="susinja" width="200px">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(codeSusinja) > 0}">
                                                        <c:forEach items="${codeSusinja}" var="row" varStatus="status">
                                                             <option value="${row.code}">${row.name}      </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
										</tr>
										<tr>
											<th scope="row">입회자</th>
											<td class="last" colspan="1">
                                                <select class="select" title="입회자" id="ibhoija" name="ibhoija">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(codeIbhoija) > 0}">
                                                        <c:forEach items="${codeIbhoija}" var="row" varStatus="status">
                                                            <option value="${row.code}">${row.name}      </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                                
                                            </td>
											<th scope="row">납품일자</th>
											<td class="last">
                      	<input type="text" class="ico_cal datepicker_aftToday" style="width: 50%" id="nabpum_ilja" name="nabpum_ilja" value="${sampleRequestVO.nabpum_ilja}"
													         title="납품일자" req/>	    								         
                      </td>
										</tr>

										<tr>
											<th scope="row">예상판매금액(만원)</th>
											<td class="last" colspan="1">
											  <input type="text" id="yesang_geumaeg" name="yesang_geumaeg" value="${sampleRequestVO.yesang_geumaeg}"
											           title="예상판매금액(만원)" />
										  </td>
											<th scope="row">도료사용금액(만원)</th>
											<td class="last">
  										  <input type="text" id="sayong_geumaeg" name="sayong_geumaeg" value="${sampleRequestVO.sayong_geumaeg}"
	    								         title="도료사용금액(만원)" />
                      </td>
										</tr>
										<tr>
											<th scope="row">희망가격(원)</th>
											<td class="last" colspan="1">
											  <input type="text" id="himang_gagyeog" name="himang_gagyeog" value="${sampleRequestVO.himang_gagyeog}"
											           title="희망가격(원)" />
										  </td>
											<th scope="row">경쟁회사</th>
											<td class="last">
  										  <input type="text" id="ex_geolaecheo" name="ex_geolaecheo" value="${sampleRequestVO.ex_geolaecheo}"
	    								         title="경쟁회사" />
                      </td>
										</tr>
										<tr>
											<th scope="row">타사견본유무</th>
											<td class="last" colspan="1">
											  <input type="text" id="ex_gyeonbon_yn" name="ex_gyeonbon_yn" value="${sampleRequestVO.ex_gyeonbon_yn}"
											           title="타사견본유무" />
										  </td>
											<th scope="row">도장SYSTEM</th>
											<td class="last">
  										  <input type="text" id="dojang_bangbeob" name="dojang_bangbeob" value="${sampleRequestVO.dojang_bangbeob}"
	    								         title="도장SYSTEM" />
                      </td>
										</tr>

										<tr>
											<th scope="row">현장도장공정</th>
											<td class="last" colspan="1">
											  <input type="text" id="dojang_gongjeong" name="dojang_gongjeong" value="${sampleRequestVO.dojang_gongjeong}"
											           title="현장도장공정" />
										  </td>
											<th scope="row">건조조건</th>
											<td class="last">
  										  <input type="text" id="geonjo_bangbeob" name="geonjo_bangbeob" value="${sampleRequestVO.geonjo_bangbeob}"
	    								         title="건조조건" />
                      </td>
										</tr>

										<tr>
											<th scope="row">도료TYPE</th>
											<td class="last" colspan="1">
											  <input type="text" id="doryo_type" name="doryo_type" value="${sampleRequestVO.doryo_type}"
											           title="도료TYPE" />
										  </td>
											<th scope="row">소재의 종류</th>
											<td class="last">
  										  <input type="text" id="sojae_jonglyu" name="sojae_jonglyu" value="${sampleRequestVO.sojae_jonglyu}"
	    								         title="소재의 종류" />
                      </td>
										</tr>


										<tr>
											<th scope="row">기타요구사항(도료)</th>
											<td class="last" colspan="1">
											  <input type="text" id="gita_yogu6" name="gita_yogu6" value="${sampleRequestVO.gita_yogu6}"
											           title="기타요구사항(도료)" />
										  </td>
											<th scope="row">기타요구사항(기술자 출장3</th>
											<td class="last">
  										  <input type="text" id="gita_yogu3" name="gita_yogu3" value="${sampleRequestVO.gita_yogu3}"
	    								         title="기타요구사항(기술자 출장3" />
                      </td>
										</tr>


										<tr>
											<th scope="row">비고1</th>
											<td class="last" colspan="1">
											  <input type="text" id="bigo_1" name="bigo_1" value="${sampleRequestVO.bigo_1}"
											           title="비고1" />
										  </td>
											<th scope="row">비고2</th>
											<td class="last">
  										  <input type="text" id="bigo_2" name="bigo_2" value="${sampleRequestVO.bigo_2}"
	    								         title="비고2" />
                      </td>
										</tr>



										<tr>
											<th scope="row">비고3</th>
											<td class="last" colspan="1">
											  <input type="text" id="bigo_3" name="bigo_3" value="${sampleRequestVO.bigo_3}"
											           title="비고3" />
										  </td>
											<th scope="row">결과등록기한</th>
											<td class="last">
                                                <select class="select" title="결과등록기한" id="gyeolgwa_gihan" name="gyeolgwa_gihan">
                                                    <option value="">-- 선택하세요 --</option>
                                                    <c:if test="${fn:length(code4905) > 0}">
                                                        <c:forEach items="${code4905}" var="row" varStatus="status">
                                                            <option value="${row.code}"
                                                            <c:if test="${fn:trim(sampleRequestVO.gyeolgwa_gihan) == row.code}"> selected</c:if> >${row.name}</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>
										</tr>
									</tbody>
								</table>					
							</div>	
							
							<div class="btn_center_wrap">
								<c:choose>
									<c:when test="${flag == 'insert'}">
										<input type="button" class="btn_big_order" value="등록하기" id="" name="" onclick="confirmSampleRequest();"/>
										<input type="button" class="btn_big_cancel" value="취소하기" id="" name="" onclick="c_submit('frm', 'sdph005201l.do');" />
									</c:when>
									<c:otherwise>
										<input type="button" class="btn_big_order" value="수정하기" id="" name="" onclick="confirmSampleRequest();"/>
										<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdph005201d.do');" />
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

<table id="hid_table1" class="table_common" style="display: none;">
    <colgroup>
        <col style="width: 30px;" />
        <col style="width: 65px;" />
        <col style="width: 105px;" />
        <col style="width: 255px;" />
        <col style="width: 75px;" />
        
        <col style="width: 55px;" />
        <col style="width: 75px;" />
        <col style="width: 55px;" />
        <col style="" />
    </colgroup>
    <tbody>
        <tr>
            <td class="txt_center">
                <input type="hidden" name="hid_mappingCode" value="" />
                <input type="hidden" name="hid_item" value="" />
                <input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="" >
				<input type="hidden" id="hid_u_m" name="hid_u_m" value="" >
                
                <input type="hidden" name="hid_description" value="" />
                <input class="blue_checkbox" type="checkbox" name="chkBox" checked />
                <label class="blue_label"></label>
            </td>
            <td>
                <select class="select" style="width:50px;" title="견본구분" name="gyeonbon_gubun">
                    <c:if test="${fn:length(code4007) > 0}">
                        <c:forEach items="${code4007}" var="row" varStatus="status">
                            <option value="${row.code}">${row.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <td class="pro_code" style="width:105px;"></td>
            <td class="pro_name" style="width:255px;"></td>
			<td class="txt_rig" id="td_u_m"></td>
            <td class="txt_center" style="width:75px;">
                <input type="text" class="entry" name="po_su" title="주문수량" style="width:70px;" />
            </td>
            <td>
                <select class="select" title="유상여부" name="price_yn" style="width:50px;">
                    <c:if test="${fn:length(code4030) > 0}">
                        <c:forEach items="${code4030}" var="row" varStatus="status">
                            <option value="${row.code}">${row.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <td><input type="text" style="width:50px;" class="entry" name="dopyeon_yn" title="도편여부" /></td>
            
        </tr>
    </tbody>
</table>

</html>