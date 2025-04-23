.<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 재고조회 팝업  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//특정 div에 페이징 추가('submit할 url', '현재 페이지', '페이지당 표시 갯수', '표시될 페이지 넘버 갯수', '전체 데이터 수')
		drawPagingDiv("sdpz000102p.${flag}.do", "${searchVO.page_current}", "${searchVO.page_countPer}", 10, "${searchVO.page_totalCnt}");
		
		if("${flag}" == "test") {
			
			$("#searchCheckBox_01").on("change", function(event) {
				if($("#searchCheckBox_01").prop("checked")) {
					$("#item_type").val("PUR6MONLL");
					$("#page_current").val("1");
					search();
				} else {
					$("#item_type").val("PUR6MON");
	
					if($("#input_searchText").val().length >= 2) {
						$("#page_current").val("1");
						search();
					} else {
						c_alert("전체 로트번호로 검색시 검색어를 2자 이상 입력해 주십시오");
						return;
					}
				}
				
			});
		} else if("${flag}" == "msds") {
			$("input[name='searchRadio_01']").on("click", function(event) {
				$("#item_type").val($(event.target).val());
			});
		}
	});
	
	//검색
	function search() {
		if("${flag}" == "test") {
			if($("#searchCheckBox_01").prop("checked")) {
				$("#item_type").val("PUR6MONLL");
				c_submit('frm', 'sdpz000102p.${flag}.do');
			} else {
				$("#item_type").val("PUR6MON");
	
				if($("#input_searchText").val().length >= 2) {
					$("#page_current").val("1");
					c_submit('frm', 'sdpz000102p.${flag}.do');
				} else {
					$("#searchCheckBox_01").attr("checked", true);
					c_alert("전체 로트번호로 검색시 검색어를 2자 이상 입력해 주십시오");
					return;
				}
			}
		} else if("${flag}" == "msds") {
			c_submit('frm', 'sdpz000102p.${flag}.do');
		}
	}
	
	//팝업창의 부모페이지에 선택된 아이템들을 추가
	function addItem() {
		var li_chkBox = $("input[name='chkBox']:checked")
		var item;
		var jsonList = [];
		
		if(null != li_chkBox && li_chkBox.length < 1) {
			c_alert("선택된 제품이 없습니다");
			return false;
		}
		
		//시험성적서와 msds 분기하여 서로 다른 값을 부모창으로 보냄
		if("${flag}" == "test") {
			for(var i=0;i <li_chkBox.length; i++) {
				item = {
					item : $(li_chkBox[i]).parent().find("input[name='hid_item']").val()
					, description : $(li_chkBox[i]).parent().find("input[name='hid_description']").val()
					, qty_allocjob : $(li_chkBox[i]).parent().find("input[name='hid_qty_allocjob']").val()
					, u_m : $(li_chkBox[i]).parent().find("input[name='hid_u_m']").val()
					, lot_no : $(li_chkBox[i]).parent().find("input[name='hid_lot_no']").val()
					, ibgo_ilja : $(li_chkBox[i]).parent().find("input[name='hid_ibgo_ilja']").val()
				}
// 				console.log(item);
				jsonList.push(item);
			}
			
		} else if("${flag}" == "msds") {
			for(var i=0;i <li_chkBox.length; i++) {
				item = {
					item : $(li_chkBox[i]).parent().find("input[name='hid_item']").val()
					, description : $(li_chkBox[i]).parent().find("input[name='hid_description']").val()
					, qty_allocjob : $(li_chkBox[i]).parent().find("input[name='hid_qty_allocjob']").val()
					, u_m : $(li_chkBox[i]).parent().find("input[name='hid_u_m']").val()
				}
// 				console.log(item);
				jsonList.push(item);
			}
		}

		var li_duplChk = opener.addItem(jsonList);
		if(li_duplChk.length > 1) {		//이미 추가된 품목 배열 크기가 0보다 크면 알림창 띄움
			$.toast("아래 품목들은 이미 주문서에 존재합니다.<br>" + li_duplChk[0] + " 외 " + (li_duplChk.length - 1) + "건", {
				duration: 2000,
				type: 'danger'
			});
		} else if(li_duplChk.length == 1) {
			$.toast("아래 품목은 이미 주문서에 존재합니다.<br>" + li_duplChk[0], {
				duration: 2000,
				type: 'danger'
			});
		} else {
			$.toast(jsonList.length + "건의 품목이 주문서에 추가 되었습니다", {
				duration: 2000,
				type: 'success'
			});
		}
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body style="background:#fff;">
<form id="frm" name="frm">
<input type="hidden" id=item_type name="item_type" value="${searchVO.item_type}">

<!-- 페이징변수 -->
<input type="hidden" id="page_current" name="page_current" value="${searchVO.page_current}">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="${searchVO.page_totalCnt}">      
<input type="hidden" id="page_countPer" name="page_countPer" value="${searchVO.page_countPer}">

<div class="pop_wrap" style="width:750px; height:790px;">			
	<div class="pop_sub_cont">
	<h2 class="pop_tit">제품검색</h2>
		<div class="gray_wrap">
			<c:choose>
				<c:when test="${flag == 'test'}">
					<input class="search_checkbox" id="searchCheckBox_01" name="searchCheckBox_01" type="checkbox" value="PUR6MONLL"
						<c:if test="${searchVO.item_type == 'PUR6MONLL'}">checked</c:if>/>
					<label class="search_label" for="searchCheckBox_01">최종LOT</label>
				</c:when>
				<c:otherwise>
					<input class="search_checkbox" id="searchRadio_ALL" name="searchRadio_01" type="radio" value="ALL"
						<c:if test="${searchVO.item_type == 'ALL'}"> checked</c:if>/>
					전체
					<input class="search_checkbox" id="searchRadio_FUR" name="searchRadio_01" type="radio" value="PUR"
						<c:if test="${searchVO.item_type == 'PUR'}"> checked</c:if>/>
					매입실적
				</c:otherwise>
			</c:choose>
			<select id="select_searchDiv" name="searchDiv" style="margin-left:10px;">
				<option value="description" <c:if test="${searchVO.searchDiv eq 'description'}">selected</c:if>>제품명</option>
				<option value="item_code" <c:if test="${searchVO.searchDiv eq 'item_code'}">selected</c:if>>제품코드</option>
			</select>
			<input class="txt" id="input_searchText" name="searchText" type="text" value="${searchVO.searchText}"
				onkeydown="if(event.keyCode==13){search(); return false;}" style="width:300px;">
			<input class="gray_inbtn_view" type="button" onclick="search();" value="조회">
		</div>

		<div class="search_btn_wrap">
			<p class="result_num">검색결과 : ${searchVO.page_totalCnt}건</p>
			<div class="search_btn_area">
				<input class="btn_newmake" id="" type="button" value="목록에 추가" onclick="javascript:addItem();">
			</div>
		</div>
		<!--search_area-->

		<!-- board_list_wrap (게시물 리스트) -->
		<div class="searchlist_wrap_tit">
			<table summary="제품검색">
				<caption>제품검색</caption>
				<colgroup>
					<c:choose>
						<c:when test="${flag == 'test'}">
							<col style="width:35px;" />
							<col style="width:100px;" />
							<col style="width:280px;" />
							<col style="width:100px;" />
							<col style="width:90px;" />
							
							<col style="" />
						</c:when>
						<c:otherwise>
							<col style="width:35px;" />
							<col style="width:100px;" />
							<col style="width:380px;" />
							<col style="width:100px;" />
							<col style="" />
						</c:otherwise>
					</c:choose>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" rowspan="1">
							<input class="blue_checkbox" id="bluecheck1" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
							<label class="blue_label" for="bluecheck1"></label>
						</th>
						<th scope="col" rowspan="1">제품코드</th>
						<th scope="col" rowspan="1">품명</th>
						<th scope="col" rowspan="1">판매단위</th>
						<c:choose>
							<c:when test="${flag == 'test'}">
								<th scope="col" rowspan="1">로트번호</th>
								<th scope="col" rowspan="1">입고일자</th>
							</c:when>
							<c:otherwise>
								<th scope="col" rowspan="1">취급제한</th>
							</c:otherwise>
						</c:choose>
					</tr>
				</thead>
			</table>
		</div>

		<div class="searchlist_wrap">
			<table class="table_common" summary="제품검색" id="table_item">
				<c:choose>
					<c:when test="${fn:length(itemList) > 0}">
						<c:choose>
							<c:when test="${flag == 'test'}">
							<!-- 시험성적서 목록 -->
								<colgroup>
									<col style="width:35px;" />
									<col style="width:100px;" />
									<col style="width:280px;" />
									<col style="width:100px;" />
									<col style="width:90px;" />
									
									<col style="" />
								</colgroup>
								<tbody id="tbody_list">
									<c:forEach items="${itemList}" var="row" varStatus="status">
										<tr>
											<td class="txt_center">
												<input type="hidden" id="hid_${row.jepum_code}${row.pojang_danwi_a}${row.pojang_danwi_b}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
												<input type="hidden" id="hid_item" name="hid_item" value="${row.jepum_code}" >
												<input type="hidden" id="hid_description" name="hid_description" value="${row.pummyeong}" >
												<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="
													<c:choose>
														<c:when test="${fn:substring(row.pojang_danwi_a, 0, 1) == '.'}">0${row.pojang_danwi_a}</c:when>
														<c:otherwise>${row.pojang_danwi_a}</c:otherwise>
													</c:choose>">
												<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.pojang_danwi_b}" >
												<input type="hidden" id="hid_lot_no" name="hid_lot_no" value="${row.lot_no}" > 
												<input type="hidden" id="hid_ibgo_ilja" name="hid_ibgo_ilja" value="${fn:substring(row.ibgo_ilja, 0, 10)}" > 
		
												<input class="blue_checkbox" type="checkbox" id="${row.jepum_code}${row.pojang_danwi_a}${row.pojang_danwi_b}${row.lot_no}" name="chkBox" />
												<label class="blue_label" for="${row.jepum_code}${row.pojang_danwi_a}${row.pojang_danwi_b}${row.lot_no}"></label>
											</td>
											<td class="pro_name" id="td_jepum_code">${row.jepum_code}</td>
											<td class="pro_name" id="td_pummyeong">${row.pummyeong}</td>
											<td class="txt_rig">
												<c:choose>
													<c:when test="${fn:substring(row.pojang_danwi_a, 0, 1) == '.'}">0${row.pojang_danwi_a}</c:when>
													<c:otherwise>${row.pojang_danwi_a}</c:otherwise>
												</c:choose>
												${row.pojang_danwi_b}
											</td>
											<td class="txt_center" id="">${row.lot_no}</td>
											<td class="txt_center" id="">${fn:substring(row.ibgo_ilja, 0, 10)}</td>
										</tr>
									</c:forEach>
								</tbody>
							</c:when>
							
							<c:otherwise>
							<!-- msds 목록 -->
								<colgroup>
									<col style="width:35px;" />
									<col style="width:100px;" />
									<col style="width:380px;" />
									<col style="width:100px;" />
									<col style="" />
								</colgroup>
								<tbody id="tbody_list">
									<c:forEach items="${itemList}" var="row" varStatus="status">
										<tr>
											<td class="txt_center">
												<input type="hidden" id="hid_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
												<input type="hidden" id="hid_item" name="hid_item" value="${row.jepum_code}" >
												<input type="hidden" id="hid_description" name="hid_description" value="${row.pummyeong}" >
												<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="
													<c:choose>
														<c:when test="${fn:substring(row.panmae_danwi_a, 0, 1) == '.'}">0${row.panmae_danwi_a}</c:when>
														<c:otherwise>${row.panmae_danwi_a}</c:otherwise>
													</c:choose>">
												<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.panmae_danwi_b}" >
												<input type="hidden" id="hid_lot_no" name="hid_lot_no" value="${row.lot_no}" > 
												<input type="hidden" id="hid_ibgo_ilja" name="hid_ibgo_ilja" value="${fn:substring(row.ibgo_ilja, 0, 10)}" > 
		
												<input class="blue_checkbox" type="checkbox" id="${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}${row.lot_no}" name="chkBox" />
												<label class="blue_label" for="${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}${row.lot_no}"></label>
											</td>
											<td class="pro_name" id="td_jepum_code">${row.jepum_code}</td>
											<td class="pro_name" id="td_pummyeong">${row.pummyeong}</td>
											<td class="txt_rig">
												<c:choose>
													<c:when test="${fn:substring(row.panmae_danwi_a, 0, 1) == '.'}">0${row.panmae_danwi_a}</c:when>
													<c:otherwise>${row.panmae_danwi_a}</c:otherwise>
												</c:choose>
												${row.panmae_danwi_b}
											</td>
											<td class="txt_center" id="">${row.restriction}</td>
										</tr>
									</c:forEach>
								</tbody>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<tr>
							<c:choose>
								<c:when test="${flag == 'test'}">
									<td class="txt_center" colspan="6">검색된 제품이 없습니다</td>
								</c:when>
								<c:otherwise>
									<td class="txt_center" colspan="5">검색된 제품이 없습니다</td>
								</c:otherwise>
							</c:choose>
						</tr>				
					</c:otherwise>
				</c:choose>
			</table>
		</div>				

		<div id="div_paging" class="pagingWrap"></div>
		<!-- // pagingWrap -->

		<div class="bottom_btn_wrap">					
			<div class="right_btn_area">						
				<input class="btn_order" id="" type="button" value="닫기" onclick="javascript:opener.focus(); self.close();">
			</div>
		</div>
		<!--bottom_btn_wrap-->
		
		
	</div>
	<!--sub_cont-->
	<jsp:directive.include file="/WEB-INF/views/templates/dialog_common.jsp" />
</div>
</form>
</body>
</html>