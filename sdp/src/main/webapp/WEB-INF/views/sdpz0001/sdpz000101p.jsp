<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 재고조회 팝업  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		ajaxRefreshQty_all();		//재고조회
		
		//특정 div에 페이징 추가('submit할 url', '현재 페이지', '페이지당 표시 갯수', '표시될 페이지 넘버 갯수', '전체 데이터 수')
		drawPagingDiv("sdpz000101p.${selectType}.do", "${searchVO.page_current}", "${searchVO.page_countPer}", 10, "${searchVO.page_totalCnt}");
		
		//단위 지정
		$("#searchSelect_01").on("change", function(event) {
			var text = $(event.target).val();
			var text1 = text.substring(0, text.indexOf("~"));
			var text2 = text.substring(text.indexOf("~") + 1, text.length);

			$("#searchInput_01").val(text1);
			$("#searchInput_02").val(text2);
			;
		});
	});
	
	//검색
	function search() {
		var li_chkBox = $("input[name='search_jungbun']:checked");
		var str_itemGroup = "";

		//if(li_chkBox.length < 1) {
		//	c_alert("제품 분류 검색조건은 최소 1가지 이상 선택하셔야 합니다.");
		//	return;
		//}
		for(var i=0; i < li_chkBox.length; i++) {
			str_itemGroup += $(li_chkBox[i]).attr("id") + ","; 
		}
		str_itemGroup = str_itemGroup.substring(0, str_itemGroup.length -1);
		$("#set_item_group").val(str_itemGroup);
		
		c_submit('frm', 'sdpz000101p.${selectType}.do');
	}
	
	//팝업창의 부모페이지에 선택된 아이템들을 추가
	function addItem() {
		console.log("체크된 박스 개수:", $("input[name='chkBox']:checked").length);
		var li_chkBox = $("input[name='chkBox']:checked")
		var item;
		var jsonList = [];
		
		if(null != li_chkBox && li_chkBox.length < 1) {
			c_alert("선택된 제품이 없습니다");
			return false;
		}
		
		for(var i=0;i <li_chkBox.length; i++) {
			item = {
				item : $(li_chkBox[i]).parent().find("input[name='hid_item']").val()
				, description : $(li_chkBox[i]).parent().find("input[name='hid_description']").val()
				, qty_allocjob : $(li_chkBox[i]).parent().find("input[name='hid_qty_allocjob']").val()
				, u_m : $(li_chkBox[i]).parent().find("input[name='hid_u_m']").val()
			}
			jsonList.push(item);
		}
// 		self.close();
// 		opener.focus();
// 		opener.addItem(JSON.stringify(jsonList));

		var li_duplChk = opener.addItem(jsonList);
		console.log("▶ child.addItem called:", jsonList);
		debugger;

		if(li_duplChk.length > 1) {		//이미 추가된 품목 배열 크기가 0보다 크면 알림창 띄움
// 			c_alert("아래 품목들은 이미 주문서에 존재합니다.<br>" + li_duplChk[0] + " 외 " + (li_duplChk.length - 1) + "건");
			$.toast("아래 품목들은 이미 주문서에 존재합니다.<br>" + li_duplChk[0] + " 외 " + (li_duplChk.length - 1) + "건", {
				duration: 2000,
				type: 'danger'
			});
		} else if(li_duplChk.length == 1) {
// 			c_alert("아래 품목은 이미 주문서에 존재합니다.<br>" + li_duplChk[0]);
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
	
	//팝업창의 부모 페이지에 선택한 단일 아이템 추가
	function selectItem(obj) {
// 		console.log($(obj).html());
		var item = $(obj).find("#td_item").html();
		var description = $(obj).find("#td_description").html();
		self.close();
		opener.focus();
		opener.addItem(item, description);
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body style="background:#fff;">
<form id="frm" name="frm">
<input type="hidden" id="set_item_group" name="set_item_group" value="" >
<input type="hidden" id="search_pgmOption" name="search_pgmOption" value="${searchVO.search_pgmOption}" >

<!-- 페이징변수 -->
<input type="hidden" id="page_current" name="page_current" value="${searchVO.page_current}">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="${searchVO.page_totalCnt}">      
<input type="hidden" id="page_countPer" name="page_countPer" value="${searchVO.page_countPer}">

	<div class="pop_wrap" style="width:750px; height:790px;">			
		<div class="pop_sub_cont">
		<h2 class="pop_tit">제품검색</h2>
			<jsp:directive.include file="/WEB-INF/views/templates/include_searchItemGroup.jsp" />

			<div class="search_area">
				<span style="margin-right:10px;">단위</span>
				<select id="searchSelect_01" name="searchSelect_01" style="width:80px; margin-right:10px;">
					<option value="" <c:if test="${searchVO.searchSelect_01 == ''}"> selected</c:if>>전체</option>
					<option value="0.001~2.999"<c:if test="${searchVO.searchSelect_01 == '0.001~2.999'}"> selected</c:if>>LT(QT)</option>
					<option value="3.000~11.999"<c:if test="${searchVO.searchSelect_01 == '3.000~11.999'}"> selected</c:if>>G/L</option>
					<option value="12.000~25.999"<c:if test="${searchVO.searchSelect_01 == '12.000~25.999'}"> selected</c:if>>말</option>
					<option value="26.000~9999.999"<c:if test="${searchVO.searchSelect_01 == '26.000~9999.999'}"> selected</c:if>>DRUM</option>
				</select>
				<input type="hidden" class="txt txt_rig" style="width:50px; padding:5px;" id="searchInput_01" name="searchInput_01" 
					value="${searchVO.searchInput_01}" readonly/>
				<input type="hidden" class="txt txt_rig" style="width:50px; padding:5px;" id="searchInput_02" name="searchInput_02" 
					value="${searchVO.searchInput_02}" readonly/>
				<select id="select_searchDiv" name="searchDiv">
					<option value="description" <c:if test="${searchVO.searchDiv eq 'description'}">selected</c:if>>제품명</option>
					<option value="item_code" <c:if test="${searchVO.searchDiv eq 'item_code'}">selected</c:if>>제품코드</option>
				</select>
				<input class="txt" id="input_searchText" name="searchText" type="text" value="${searchVO.searchText}" style="width:400px;"
					onkeydown="if(event.keyCode==13){search(); return false;}">
				<input class="btn_search" type="button" onclick="search();">
			</div>

			<div class="search_btn_wrap">
				<p class="result_num">검색결과 : ${searchVO.page_totalCnt}건</p>
				<div class="search_btn_area">
					<input class="btn_reset" id="" type="button" value="전체 재고조회" onclick="javascript:ajaxRefreshQty_all();">
					<c:if test="${selectType != null and selectType == 'multiple'}">
						<input class="btn_newmake" id="" type="button" value="목록에 추가" onclick="javascript:addItem();">
					</c:if>
				</div>
			</div>
			<!--search_area-->

			<!-- board_list_wrap (게시물 리스트) -->
			<div class="searchlist_wrap_tit">
				<table summary="제품검색">
					<caption>제품검색</caption>
					<colgroup>
						<col style="width:35px;" />
						<col style="width:100px;" />
						<col style="width:280px;" />
						<col style="width:60px;" />
						<col style="width:90px;" />
						
						<col style="width:80px;" />
						<col style="" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col" rowspan="2">
								<c:if test="${selectType != null and selectType == 'multiple'}">
									<input class="blue_checkbox" id="bluecheck1" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
									<label class="blue_label" for="bluecheck1"></label>
								</c:if>
							</th>
							<th scope="col" rowspan="2">제품코드</th>
							<th scope="col" rowspan="2">품명</th>
							<th scope="col" rowspan="2">판매단위</th>
							
							<th scope="col" colspan="3" class="tbl_col_tit">재고</th>
						</tr>
						<tr>
							<jsp:directive.include file="/WEB-INF/views/templates/include_itemTitle.jsp" />
						</tr>
					</thead>
				</table>
			</div>

			<div class="searchlist_wrap">
				<table class="table_common" summary="제품검색" id="table_item">
					<colgroup>
						<col style="width:35px;" />
						<col style="width:100px;" />
						<col style="width:280px;" />
						<col style="width:60px;" />
						<col style="width:90px;" />
						
						<col style="width:80px;" />
						<col style="" />
					</colgroup>
					<tbody id="tbody_list">
						<c:choose>
							<c:when test="${fn:length(itemList) > 0 }" >
								<c:forEach items="${itemList}" var="row" varStatus="status">
									<tr <c:if test="${selectType != null and selectType == 'single'}"> onclick="selectItem(this);"</c:if>>
										<td class="txt_center">
											<input type="hidden" id="hid_${row.item}${row.qty_allocjob}${row.u_m}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
											<input type="hidden" id="hid_item" name="hid_item" value="${row.item}" >
											<input type="hidden" id="hid_description" name="hid_description" value="${row.description}" >
											<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.qty_allocjob}" >
											<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.u_m}" >

											<c:if test="${selectType != null and selectType == 'multiple'}">
												<input class="blue_checkbox" type="checkbox" id="${row.item}${row.qty_allocjob}${row.u_m}" name="chkBox" />
												<label class="blue_label" for="${row.item}${row.qty_allocjob}${row.u_m}"></label>
											</c:if>
										</td>
										<td class="pro_name" id="td_item">${row.item}</td>
										<td class="pro_name" id="td_description">${row.description}[${row.pummog_gubun}]</td>
										<td class="txt_rig">${row.qty_allocjob}${row.u_m}</td>
										<td class="txt_rig ft_grey" id="td_qtyOnHand01">0</td>
										
										<td class="txt_rig ft_grey" id="td_qtyOnHand02">0</td>
										<td class="txt_rig ft_grey" id="td_keepOnHand">0</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="txt_center" colspan="7">검색된 제품이 없습니다</td>
								</tr>				
							</c:otherwise>
						</c:choose>
					</tbody>
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