<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- MSDS 등록 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
		//입력시 현재일자 셋팅
		if("${flag}" == "insert") {
			settingDate($("#submit_dt"));
			$("#req_dt").val(dateToString(new Date(), "."));
		}
	});
	
	
	//시험성적서 요청에 등록된 품목 제거
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
					$("#tbody_list").append("<tr id='tr_empty' ><td class='txt_center' colspan='5'>추가된 제품이 없습니다</td></tr>");
					$("#p_listCnt").html("제품 : <strong>0</strong>건");
				} else {
					$("#p_listCnt").html("제품 : <strong>" + $("#tbody_list tr").length + "</strong>건");
				}
			} else { //no Click
	
			}
		});
	}
	
	//팝업창에서 추가한 아이템들을 목록에 추가
	function addItem(text) {
		var jsonList = text;
		var obj;
		var li_duplChk = [];		//기존에 이미 추가 되어 있는 아이템들 목록을 alert으로 뿌리기 위해 배열로 저장

		//중복체크
		var li_mappingCode = $("#table_item input[name='hid_mappingCode']");	//화면에 mapping 코드를 object로 불러와 추가할 아이템 목록과 비교하여 중복을 방지하기 위함
		var li_mappingCodeVal = [];		//mapping의 value값을 저장하기 위한 배열
		for(var i=0; i < li_mappingCode.length; i++) {
			li_mappingCodeVal.push($(li_mappingCode[i]).val());
		}
		
		
		for(var i=0; i < jsonList.length; i++) {
			obj = jsonList[i];
			
			
			//기존에 추가 되어 있는 아이템인지 검색 if:행추가X else:행추가O
			if(li_mappingCodeVal.indexOf(obj.item) >= 0) {
				li_duplChk.push(obj.description);

			} else {
				li_mappingCodeVal.push(obj.item);
				$("#hid_table").find("input[name='chkBox']").attr("id", obj.item);
				$("#hid_table").find("label").attr("for", obj.item);
				$("#hid_table").find("#hid_mappingCode").val(obj.item);
				$("#hid_table").find("#hid_item").val(obj.item);
				$("#hid_table").find("#hid_description").val(obj.description);
				
				$("#hid_table").find("#td_item").html(obj.item);
				$("#hid_table").find("#td_description").html(obj.description);
				
				$("#tbody_list").append($("#hid_table tbody").html());
				$("#tr_empty").remove();			//빈칸용 tr 삭제
			}
		}
		
		var trList = $("#table_item tbody tr");
		$("#p_listCnt").html("품목 : <strong>" + $("#tbody_list tr").length + "</strong>건");		//행 갯수 갱신
		
		return li_duplChk;
	}
	
	//msds 등록
	function confirmMsds() {
		var li_chkBox = $("#table_item input[name='chkBox']");
		var jsonList = [];
		var item;
		
		if(!doFormValidate(document.frm)){
			return;
		}
		
		if(li_chkBox.length < 1){
			c_alert("추가된 품목이 없습니다");
			return;
		}
		
		for(var i=0; i < li_chkBox.length; i++) {
			item = {
				item : $(li_chkBox[i]).parent().parent().find("#hid_item").val()
				, description : $(li_chkBox[i]).parent().parent().find("#td_description").html()
				, due_date : $(li_chkBox[i]).parent().parent().find("#td_due_date").html()
				, dept_cd : $(li_chkBox[i]).parent().parent().find("#dept_cd").val()
				, dept_nm : $(li_chkBox[i]).parent().parent().find("#td_dept_nm").html()
				
				, emp_no : $(li_chkBox[i]).parent().parent().find("#emp_no").val()
				, emp_nm : $(li_chkBox[i]).parent().parent().find("#emp_nm").val()
			}
			jsonList.push(item);
		}
		
		if(jsonList.length < 1) {
			c_alert("제품을 목록에 추가해주십시오");
			return;
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		
		c_confirm("MSDS 요청서를 등록 하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				c_submit("frm", "sdpe002001u_insert.do");
			} else { //no Click
				return;
			}
		});
	}
	
	//시험성적서 수정
	function updateMsds() {
		var li_chkBox = $("#table_item input[name='chkBox']");
		var jsonList = [];
		var item;
		
		if(!doFormValidate(document.frm)){
			return;
		}
		
		if(li_chkBox.length < 1){
			c_alert("추가된 제품이 없습니다");
			return;
		}
		
		for(var i=0; i < li_chkBox.length; i++) {
			if(Number($(li_chkBox[i]).parent().parent().find("#qty_ordered").val()) < 1) {
				c_alert("수량을 입력해 주십시오<br>품명 : " + $(li_chkBox[i]).parent().parent().find("#td_description").html());
				return;
			}
			item = {
				item : li_chkBox[i].id
				, description : $(li_chkBox[i]).parent().parent().find("#td_description").html()
				, due_date : $(li_chkBox[i]).parent().parent().find("#td_due_date").html()
				, dept_cd : $(li_chkBox[i]).parent().parent().find("#dept_cd").val()
				, dept_nm : $(li_chkBox[i]).parent().parent().find("#td_dept_nm").html()
				
				, emp_no : $(li_chkBox[i]).parent().parent().find("#emp_no").val()
				, emp_nm : $(li_chkBox[i]).parent().parent().find("#emp_nm").val()
			}
			jsonList.push(item);
		}

		$("#jsonList").val(JSON.stringify(jsonList));
		
		c_confirm("수정 하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				c_submit("frm", "sdpe002001u_update.do");
			} else { //no Click
				return;
			}
		});
	}
	
	//품목 추가 팝업
	function popup_itemListTtestRpt() {
		var pop_itemList = window.open("sdpz000102p.msds.do", "pop_itemList", "width=760,height=805, scrollbars=yes, resizable=no");
		pop_itemList.focus();
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="jsonList" name="jsonList" value="" />

<input type="hidden" id="searchDate_from" name="searchDate_from" value="${msdsHVO.searchDate_from}"/>
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${msdsHVO.searchDate_to}"/>
<input type="hidden" id="searchText" name="searchText" value="${msdsHVO.searchText}"/>

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">MSDS 요청</h3>

						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>자료실</li>
								<li>MSDS</li>
							</ul>
						</div>
						<!--local_nav-->

					</div>
					<div class="sub_cont">
						<c:choose>
							<c:when test="${fn:length(subList) > 0}">
								<p class="left_num_count" id="p_listCnt">품목 : <strong>${fn:length(subList)}</strong>건</p>
							</c:when>
							<c:otherwise>
								<p class="left_num_count" id="p_listCnt">품목 : <strong>0</strong>건</p>
							</c:otherwise>
						</c:choose>
						
						<div class="orderlist_wrap_tit">
							<table class="table_common" summary="시험성적서 등록 타이틀">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 180px;" />
									<col style="width: 550px;" />
									<col style="width: 100px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">
											<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');" checked/>
											<label class="blue_label" for="parentChkBox"></label>
										</th>
										<th scope="col">제품코드</th>
										<th scope="col">품명</th>
										<th scope="col">완료예정일</th>
										<th scope="col">담당부서</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="orderlist_wrap">
							<table class="table_common" id="table_item">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 180px;" />
									<col style="width: 550px;" />
									<col style="width: 100px;" />
									<col style="" />
								</colgroup>
								<tbody id="tbody_list">
									<c:choose>
										<c:when test="${fn:length(subList) > 0}">
											<c:forEach items="${subList}" var="row" varStatus="status">
												<tr>
													<td class="txt_center">
														<input type="hidden" id="hid_mappingCode" name="hid_mappingCode" value="${row.item_cd}" >
														<input type="hidden" id="hid_item" name="hid_item" value="${row.item_cd}" >
														<input type="hidden" id="hid_description" name="hid_description" value="${row.pummyeong}" >
													
														<input class="blue_checkbox" type="checkbox" id="${row.item_cd}" name="chkBox" checked/>
														<label class="blue_label" for="${row.item_cd}"></label>
													</td>
													<td class="pro_code" id="td_item">${row.item_cd}</td>
													<td class="pro_name" id="td_description">${row.pummyeong}</td>
													<td class="txt_center" id="td_due_date"></td>
													<td class="txt_center" id="td_dept_nm">${row.buseomyeong}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" ><td class="txt_center" colspan="5">추가된 제품이 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
								<input class="btn_add" type="button" id="" value="품목 추가" onclick="popup_itemListTtestRpt();" />
								<input class="btn_del2" type="button" id="" value="품목 삭제" onclick="removeItem();" />
							</div>
						</div>
						<!--bottom_btn_wrap-->

						<!--div class="input_subtit">주문자 정보</div-->

						<div class="tbl_input_wrap">
							<table class="tbl_input" summary="MSDS요청 입력폼">
								<caption>MSDS요청 입력폼</caption>
								<colgroup>
									<col style="width:10%" />
									<col style="width:23%" />
									<col style="width:10%" />
									<col style="width:23%">
									<col style="width:10%" />
									<col style="width:23%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">요청일</th>
										<td class="last">
											<input type="text" class="readonly txt_center" id="req_dt" name="req_dt" value="${headerVO.req_dt}" readonly title="요청일" req/>
										</td>
										<th scope="row">요청번호</th>
										<td class="last">
											<c:choose>
												<c:when test="${flag == 'insert'}">
													<input type="text" class="txt_center readonly" style="width:100%;" value="요청 등록시 자동생성" readonly/>
												</c:when>
												<c:otherwise>
													<input type="text" class="txt_center readonly" style="width:100%;" id="req_no" name="req_no" value="${headerVO.req_no}" readonly/>
												</c:otherwise>
											</c:choose>
										</td>
										<th scope="row">제출일</th>
										<td class="last">
											<input type="text" class="ico_cal datepicker_aftToday" id="submit_dt" name="submit_dt" title="제출일" value="${headerVO.submit_dt}"  readonly title="제출일" re/>
										</td>
									</tr>
									<tr>
										<th scope="row">제출처명</th>
										<td class="last" colspan="5">
											<input type="text" style="width:100%;" id="submit_nm" name="submit_nm" maxlength="25" value="${headerVO.submit_nm}"/>
										</td>
									</tr>
									<tr>
										<th scope="row">특기사항</th>
										<td class="last" colspan="5">
											<textarea id="rmk" name="rmk" style="width: 100%;" rows="10" maxlength="50"
												placeholder="특기사항은 최대 50자까지 입력 가능합니다">${headerVO.rmk}</textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="btn_center_wrap">
							<c:choose>
								<c:when test="${flag == 'insert'}">
									<input type="button" class="btn_big_order" value="요청하기" onclick="confirmMsds();" />
									<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpe002001l.do');"/>
								</c:when>
								<c:otherwise>
									<input type="button" class="btn_big_order" value="수정하기" onclick="updateMsds();" />
									<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpe002001d.do');"/>
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


<table class="table_common" id="hid_table" style="display:none;">
	<colgroup>
		<col style="width: 40px;" />
		<col style="width: 180px;" />
		<col style="width: 550px;" />
		<col style="width: 100px;" />
		<col style="" />
	</colgroup>
	<tbody>
		<tr>
			<td class="txt_center">
				<input type="hidden" id="hid_mappingCode" name="hid_mappingCode" value="" >
				<input type="hidden" id="hid_item" name="hid_item" value="" >
				<input type="hidden" id="hid_description" name="hid_description" value="" >
				
				<input class="blue_checkbox" type="checkbox" id="" name="chkBox" checked/>
				<label class="blue_label" for=""></label>
			</td>
			<td class="pro_code" id="td_item"></td>
			<td class="pro_name" id="td_description"></td>
			<td class="txt_center" id="td_due_date"></td>
			<td class="txt_center" id="td_dept_nm"></td>
		</tr>
	</tbody>
</table>
</html>