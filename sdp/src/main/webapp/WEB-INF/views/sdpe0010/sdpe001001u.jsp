<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 시험성적서 등록 -->
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
					$("#p_listCnt").html("품목 : <strong>0</strong>건");
				} else {
					$("#p_listCnt").html("품목 : <strong>" + $("#tbody_list tr").length + "</strong>건");
				}
			} else { //no Click

			}
		});
	}
	
	//팝업창에서 추가한 아이템들을 목록에 추가
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
			obj = jsonList[i];
		
			//기존에 추가 되어 있는 아이템인지 검색 if:행추가X else:행추가O
			if(li_mappingCodeVal.indexOf(obj.item + obj.lot_no) >= 0) {
				li_duplChk.push(obj.description);

			} else {
				$("#hid_table").find("checkbox").attr("id", obj.item + obj.lot_no);
				$("#hid_table").find("label").attr("for", obj.item + obj.lot_no);
				$("#hid_table").find("#hid_mappingCode").val(obj.item + obj.lot_no);
				$("#hid_table").find("#hid_item").val(obj.item);
				$("#hid_table").find("#hid_description").val(obj.description);
				$("#hid_table").find("#hid_lot_no").val(obj.lot_no);
				$("#hid_table").find("#hid_ibgo_ilja").val(obj.ibgo_ilja);
				
				$("#hid_table").find("#td_item").html(obj.item);
				$("#hid_table").find("#td_description").html(obj.description);
				$("#hid_table").find("#td_u_m").html(obj.qty_allocjob + obj.u_m);
				$("#hid_table").find("#td_lot_no").html(obj.lot_no);
				
				$("#tbody_list").append($("#hid_table tbody").html());
				$("#tr_empty").remove();			//빈칸용 tr 삭제
			}
		}
		
		var trList = $("#table_item tbody tr");
		$("#p_listCnt").html("품목 : <strong>" + $("#tbody_list tr").length + "</strong>건");		//행 갯수 갱신
		
		return li_duplChk;
	}
	
	//시험성적서 등록
	function confirmTestReport() {
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
			if(Number($(li_chkBox[i]).parent().parent().find("#su").val()) < 1) {
				c_alert("수량을 입력해 주십시오<br>품명 : " + $(li_chkBox[i]).parent().parent().find("#td_description").html());
				return;
			}
			item = {
				item : $(li_chkBox[i]).parent().parent().find("#hid_item").val()
				, description : $(li_chkBox[i]).parent().parent().find("#td_description").html()
				, lot_no : $(li_chkBox[i]).parent().parent().find("#hid_lot_no").val()
				, su : $(li_chkBox[i]).parent().parent().find("#su").val()
			}
			jsonList.push(item);
		}
		$("#jsonList").val(JSON.stringify(jsonList));
		
		c_confirm("시험성적서를 요청 하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				c_submit("frm", "sdpe001001u_insert.do");
			} else { //no Click
				return;
			}
		});
	}
	
	//시험성적서 수정
	function updateTestReport() {
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
			if(Number($(li_chkBox[i]).parent().parent().find("#su").val()) < 1) {
				c_alert("수량을 입력해 주십시오<br>품명 : " + $(li_chkBox[i]).parent().parent().find("#td_description").html());
				return;
			}
			item = {
				item : $(li_chkBox[i]).parent().parent().find("#hid_item").val()
				, lot_no : $(li_chkBox[i]).parent().parent().find("#hid_lot_no").val()
				, su : $(li_chkBox[i]).parent().parent().find("#su").val()
			}
			jsonList.push(item);
		}
		$("#jsonList").val(JSON.stringify(jsonList));
		
		c_confirm("수정 하시겠습니까?").then(function(result) { //커스텀 confirm
			if (result) { //yes Click
				c_submit("frm", "sdpe001001u_update.do");
			} else { //no Click
				return;
			}
		});
	}
	
	//품목 추가 팝업
	function popup_itemListTtestRpt() {
		var pop_itemList = window.open("sdpz000102p.test.do", "pop_itemList", "width=760,height=805, scrollbars=yes, resizable=no");
		pop_itemList.focus();
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="jsonList" name="jsonList" value="" />
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${testReportVO.searchDate_from}"/>
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${testReportVO.searchDate_to}"/>
<input type="hidden" id="searchDiv" name="searchDiv" value="${testReportVO.searchDiv}"/>
<input type="hidden" id="searchText" name="searchText" value="${testReportVO.searchText}"/>
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">시험성적서 요청</h3>

						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>자료실</li>
								<li>시험성적서</li>
							</ul>
						</div>
						<!--local_nav-->

					</div>
					<div class="sub_cont">

						<div class="search_btn_wrap">
							<c:choose>
								<c:when test="${fn:length(testReportSub) > 0}">
									<p class="left_num_count" id="p_listCnt">품목 : <strong>${fn:length(testReportSub)}</strong>건</p>
								</c:when>
								<c:otherwise>
									<p class="left_num_count" id="p_listCnt">품목 : <strong>0</strong>건</p>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="orderlist_wrap_tit">
							<table class="table_common" summary="시험성적서 등록 타이틀">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 600px;" />
									<col style="width: 210px;" />
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
										<th scope="col">로드번호</th>
										<th scope="col">수량</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="orderlist_wrap">
							<table class="table_common" id="table_item">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 120px;" />
									<col style="width: 600px;" />
									<col style="width: 210px;" />
									<col style="" />
								</colgroup>
								<tbody id="tbody_list">
									<c:choose>
										<c:when test="${fn:length(testReportSub) > 0}">
											<c:forEach items="${testReportSub}" var="row" varStatus="status">
												<tr>
													<td class="txt_center">
														<input class="blue_checkbox" type="checkbox" id="${row.item_cd}${row.lot_no}" name="chkBox" checked/>
														<label class="blue_label" for="${row.item_cd}${row.lot_no}"></label>
														<input type="hidden" id="hid_mappingCode" name="hid_mappingCode" value="${row.item_cd}${row.lot_no}">
														<input type="hidden" id="hid_item" name="hid_item" value="${row.item_cd}">
														<input type="hidden" id="hid_lot_no" name="hid_lot_no" value="${row.lot_no}">
													</td>
													<td class="pro_code" id="td_item">${row.item_cd}</td>
													<td class="pro_name" id="td_description">${row.pummyeong}</td>
													<td class="txt_center" id="td_lot_no">${row.lot_no}</td>
													<td class="txt_center">
														<input type="text" class="entry" id="su" name="su" value="${row.su}"/>
													</td>
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
							<table class="tbl_input" summary="시험성적서요청 입력폼">
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
											<input type="text" class="readonly txt_center" id="req_dt" name="req_dt" value="${testReportHeader.req_dt}" readonly title="요청일" req/>
										</td>
										<th scope="row">요청번호</th>
										<td class="last">
											<c:choose>
												<c:when test="${flag == 'insert'}">
													<input type="text" class="readonly txt_center" style="width:100%;" value="요청 등록시 자동생성" readonly/>
												</c:when>
												<c:otherwise>
													<input type="text" class="readonly txt_center" style="width:100%;" id="req_no" name="req_no" value="${testReportHeader.req_no}" readonly/>
												</c:otherwise>
											</c:choose>
										</td>
										<th scope="row">제출일</th>
										<td class="last">
											<input type="text" class="ico_cal datepicker_aftToday"  id="submit_dt" name="submit_dt" value="${testReportHeader.submit_dt}" readonly title="제출일" req/>
										</td>
									</tr>
									<tr>
										<th scope="row">작성언어</th>
										<td class="last" colspan="5">
											<c:choose>
												<c:when test="${flag == 'insert'}">
													<input type="radio" id="lang1" name="lang" value="K" checked="checked">&nbsp;한글
													<input type="radio" id="lang2" name="lang" value="E" style="margin-left: 100px;">&nbsp;영문
													<input type="radio" id="lang3" name="lang" value="M"  style="margin-left: 100px;">&nbsp;한글+영문
												</c:when>
												<c:otherwise>
													<input type="radio" id="lang1" name="lang" value="K"
														<c:if test="${fn:trim(testReportHeader.lang) == 'K'}" > checked="checked"</c:if>>&nbsp;한글
													<input type="radio" id="lang2" name="lang" value="E" style="margin-left: 100px;"
														<c:if test="${fn:trim(testReportHeader.lang) == 'E'}" > checked="checked"</c:if>>&nbsp;영문
													<input type="radio" id="lang3" name="lang" value="M"  style="margin-left: 100px;"
														<c:if test="${fn:trim(testReportHeader.lang) == 'M'}" > checked="checked"</c:if>>&nbsp;한글+영문
												</c:otherwise>
											</c:choose>
										</td>	
									</tr>
									<tr>
										<th scope="row">제출처명(한글)</th>
										<td class="last" colspan="5">
											<input type="text" style="width:100%;" id="submit_nm_k" name="submit_nm_k" maxlength="25" value="${testReportHeader.submit_nm_k}"/>
										</td>
									</tr>
									<tr>
										<th scope="row">제출처명(영문)</th>
										<td class="last" colspan="5">
											<input type="text" style="width:100%;" id="submit_nm_e" name="submit_nm_e" maxlength="25" value="${testReportHeader.submit_nm_e}"/>
										</td>
									</tr>
									<tr>
										<th scope="row">특기사항</th>
										<td class="last" colspan="5">
											<textarea id="rmk" name="rmk" style="width: 100%;" maxlength="50" rows="10"
												placeholder="특기사항은 최대 50자까지 입력 가능합니다">${testReportHeader.rmk}</textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="comment_wrap">
							<ul>
								<li><span class="blit">·</span> 중금속 시험성적서는 영업부서 담당자에게 직접 요청하십시오.</li>
								<li><span class="blit">·</span> 파크멜(분체)는 대리점(<span>${testReportVO.cust_num}@s.jebi.co.kr</span>) e-mail로 회신됩니다.</li>
							</ul>
						</div>

						<div class="btn_center_wrap">
							<c:choose>
								<c:when test="${flag == 'insert'}">
									<input type="button" class="btn_big_order" value="요청하기" onclick="confirmTestReport();" />
									<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpe001001l.do');"/>
								</c:when>
								<c:otherwise>
									<input type="button" class="btn_big_order" value="수정하기" onclick="updateTestReport();" />
									<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpe001001d.do');"/>
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
		<col style="width: 120px;" />
		<col style="width: 600px;" />
		<col style="width: 210px;" />
		
		<col style="" />
	</colgroup>
	<tbody id="">
		<tr>
			<td class="txt_center">
				<input class="blue_checkbox" type="checkbox" id="" name="chkBox" checked/>
				<label class="blue_label" for=""></label>
				<input type="hidden" id="hid_mappingCode" name="hid_mappingCode" value="">
				<input type="hidden" id="hid_item" name="hid_item" value="">
				<input type="hidden" id="hid_lot_no" name="hid_lot_no" value="">
			</td>
			<td class="pro_code" id="td_item"></td>
			<td class="pro_name" id="td_description"></td>
			<td class="pro_code" id="td_lot_no"></td>
			<td class="txt_center">
				<input type="text" class="entry" id="su" name="su" value="0"/>
			</td>
		</tr>
	</tbody>
</table>
</html>