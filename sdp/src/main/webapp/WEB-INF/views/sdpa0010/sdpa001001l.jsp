<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		if("${flag}"== "standard") {
			ajaxRefreshQty_all();		//화면상 품목들 재고 조회
		} else {
			ajaxRefreshQty_detail();		//화면상 품목들 재고 조회
		}
// 		setView();					//화면 준비

		//특정 div에 페이징 추가('submit할 url', '현재 페이지', '페이지당 표시 갯수', '표시될 페이지 넘버 갯수', '전체 데이터 수')
		drawPagingDiv("sdpa001001l.${flag}.do", "${searchVO.page_current}", "${searchVO.page_countPer}", 10, "${searchVO.page_totalCnt}");
		
		$("#select_countPerPage").on("change", function(event) {
			$("#page_countPer").val($(event.target).val());
			$("#page_current").val("1");
			search();
		});
		
		//flag:detail 리스트의 스크롤 조정
		if("${flag}" == "detail" || "${flag}" == "keep" || "${flag}" == "order") {
			$('#div_list').bind("scroll", function(){
		        $('#div_title').scrollTop($(this).scrollTop());
		        $('#div_title').scrollLeft($(this).scrollLeft());
		    });
		}
		
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
	
	//화면 준비
	function setView() {
// 		$("#select_searchDiv").selectmenu();
	}

	//검색
	function search() {
		var li_chkBox = $("input[name='search_jungbun']:checked");
		var str_itemGroup = "";
		
		if(li_chkBox.length < 1) {
			//c_alert("제품 분류 검색조건은 최소 1가지 이상 선택하셔야 합니다.");
			//return;
		}
		
		$("#page_current").val("1");
		c_submit("frm", "sdpa001001l." + $("#flag").val() + ".do");
	}
	
	//장바구니 담기
	function addBasket(obj) {
		var item;
		var itemList = [];
		
		if(obj.id == "btn_addBasketCheckd") {		//이벤트를 호출한 obj의 id를 통해 다수 추가, 혹은 단일 추가를 결정 하는 분기
			var li_chkBox = $("#tbody_list input[name='chkBox']:checked");
			
			for(var i=0; i < li_chkBox.length; i++) {
				item = {
					item 			: $(li_chkBox[i]).parent().parent().find("input[name='hid_item']").val()
					, qty_allocjob	: $(li_chkBox[i]).parent().parent().find("#hid_qty_allocjob").val()
					, u_m			: $(li_chkBox[i]).parent().parent().find("#hid_u_m").val()
					, description 	: $(li_chkBox[i]).parent().parent().find("#td_description").html()
					, qty_basket 	: "0"
				};
				itemList.push(item);
			}
			
		} else {		//각 목록에서 단일 추가 버튼으로 장바구니에 추가하는 경우
			item = {
				item 			: $(obj).parent().parent().find("input[name='hid_item']").val()
				, qty_allocjob	: $(obj).parent().parent().find("#hid_qty_allocjob").val()
				, u_m			: $(obj).parent().parent().find("#hid_u_m").val()
				, description 	: $(obj).parent().parent().find("#td_description").html()
				, qty_basket 	: "0"
			};
			
			itemList.push(item);
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
		
		if(li_chkBox.length < 1) {
			c_alert("선택된 제품이 없습니다"); return;
		}
		
		for(var i=0; i < li_chkBox.length; i++) {
			item = {
				item : $(li_chkBox[i]).parent().find("#hid_item").val()
				, description : $(li_chkBox[i]).parent().find("#hid_description").val()
				, qty_allocjob : $(li_chkBox[i]).parent().find("#hid_qty_allocjob").val()
				, u_m : $(li_chkBox[i]).parent().find("#hid_u_m").val()
				, qty_on_hand01 : "0"
			}
			jsonList.push(item);
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		c_submit("frm", "sdpa002001u.insert.do");
	}
	
	//제품 판매타입 변경
	function changeSalesType(flag) {
		$("#flag").val(flag);
		
		var li_chkBox = $("input[name='search_jungbun']:checked");
		var str_itemGroup = "";
		
		if(li_chkBox.length < 1) {
			c_alert("제품 분류 검색조건은 최소 1가지 이상 선택하셔야 합니다.");
			return;
		}
		if(flag == "keep") {
			$("#search_pgmOption").val("B");
			
		} else if(flag == "order") {
			$("#search_pgmOption").val("JA");
			
		} else {
			$("#search_pgmOption").val("");
		}
		c_submit("frm", "sdpa001001l." + $("#flag").val() + ".do");
	}
	
	function detailModal(obj_id){
		$("#dialog_searchItemGroup").dialog({
			resizable : false
			,height : "auto"
			,width : "auto"
			,modal : true
			,draggable: false
			,position: { my: "left center", at: "right center", of: "#"+obj_id}
			,title : $("#"+obj_id).html().substring(0, $("#"+obj_id).html().indexOf("<"))
		});
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>
<body>
<form id="frm" name="frm" onsubmit="return setItemGroup()">
<!-- 페이징변수 -->
<input type="hidden" id="page_current" name="page_current" value="${searchVO.page_current}">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="${searchVO.page_totalCnt}">      
<input type="hidden" id="page_countPer" name="page_countPer" value="${searchVO.page_countPer}">

<!-- 기타변수 -->
<input type="hidden" id="flag" name="flag" value="${flag}" >
<input type="hidden" id="paramList" name="paramList">
<input type="hidden" id="jsonList" name="jsonList">
<input type="hidden" id="set_item_group" name="set_item_group" value="" >
<input type="hidden" id="search_pgmOption" name="search_pgmOption" value="${searchVO.search_pgmOption}" >
	
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">재고조회 (제품검색)</h3>
							<div class="local_nav">
								<ul>
									<li class="home">홈</li>
									<li>주문관리</li>
									<li>제품검색</li>
								</ul>
		            		</div><!--local_nav-->
						</div>
						<div class="sub_cont">
							<jsp:directive.include file="/WEB-INF/views/templates/include_searchItemGroup.jsp" />
							
<!-- 							<div class="search_check" id=""> -->
<!-- 								<div class="search_more_wrap search_more_on" id="opencheckBtn"> -->
<!-- 									<a href="#">더보기</a> -->
<!-- 								</div> -->
<!-- 								<input class="search_checkbox" id="parentChkBox_search" name="parentChkBox_search" type="checkbox" onclick="checkAll(this, 'uf_salegroup1');"/> -->
<!-- 								<label class="search_label" for="parentChkBox_search" style="font-weight: bold;">전체</label> -->
<%-- 								<c:if test="${fn:length(itemGroupCodeList) > 0}"> --%>
<%-- 									<c:forEach items="${itemGroupCodeList}" var="row" varStatus="status"> --%>
<%-- 										<input class="search_checkbox" id="${row.minor_cd}" name="uf_salegroup1" type="checkbox" value="${row.minor_cd}" --%>
<%-- 											<c:if test="${fn:contains(fn:trim(customerVO.set_item_group), fn:trim(row.minor_cd))}" > checked</c:if>/> --%>
<%-- 										<label class="search_label" for="${row.minor_cd}">${row.cd_nm}</label> --%>
<%-- 										<c:if test="${status.index == 4}"> --%>
<!-- 											</div> -->
<!-- 											<div class="search_check search_open" id="hiddenList"> -->
<%-- 										</c:if> --%>
<%-- 									</c:forEach> --%>
<%-- 								</c:if> --%>
<!-- 							</div> -->
			
							<div class="search_area">
								<span style="margin-right:10px;">단위</span>
								<select id="searchSelect_01" name="searchSelect_01" style="width:80px;">
									<option value="" <c:if test="${searchVO.searchSelect_01 == ''}"> selected</c:if>>전체</option>
									<option value="0.001~2.999"<c:if test="${searchVO.searchSelect_01 == '0.001~2.999'}"> selected</c:if>>LT(QT)</option>
									<option value="3.000~11.999"<c:if test="${searchVO.searchSelect_01 == '3.000~11.999'}"> selected</c:if>>G/L</option>
									<option value="12.000~25.999"<c:if test="${searchVO.searchSelect_01 == '12.000~25.999'}"> selected</c:if>>말</option>
									<option value="26.000~9999.999"<c:if test="${searchVO.searchSelect_01 == '26.000~9999.999'}"> selected</c:if>>DRUM</option>
								</select>
								<input class="txt txt_rig" style="width:50px; padding:5px;" id="searchInput_01" name="searchInput_01" type="hidden" 
									value="${searchVO.searchInput_01}" readonly/>
								<input class="txt txt_rig" style="width:50px; padding:5px;" id="searchInput_02" name="searchInput_02" type="hidden" 
									value="${searchVO.searchInput_02}" readonly/>
								
								<select id="select_searchDiv" name="searchDiv" style="margin-left:40px;">
									<option value="description" <c:if test="${searchVO.searchDiv eq 'description'}">selected</c:if>>제품명</option>
									<option value="item_code" <c:if test="${searchVO.searchDiv eq 'item_code'}">selected</c:if>>제품코드</option>
								</select>
								<input class="txt" id="input_searchText" name="searchText" type="text" value="${searchVO.searchText}"
									placeholder="검색할 키워드를 띄어쓰기 공간을 주고 입력하십시오. 예) 무광 백색"
									onkeydown="if(event.keyCode==13){search(); return false;}">
								<input class="btn_search" type="button" onclick="search();">
							</div>
							<!--search_area-->
							
							<div class="common_tab_wrap">
								<ul>
									<li <c:if test="${flag == 'standard'}">class="on"</c:if>><a href="javascript:changeSalesType('standard');">일반 조회</a></li>
									<!-- <li <c:if test="${flag == 'detail'}">class="on"</c:if>><a href="javascript:changeSalesType('detail');">상세 조회</a></li>
									<li <c:if test="${flag == 'order'}">class="on"</c:if>><a href="javascript:changeSalesType('order');">주문품 조회</a></li>
									<li <c:if test="${flag == 'keep'}">class="on"</c:if>><a href="javascript:changeSalesType('keep');">보관품 조회</a></li> -->
								</ul>
							</div>
							
							<div class="common_tab_contents">
								<div class="search_btn_wrap">
									<p class="result_num">검색결과 : ${searchVO.page_totalCnt}건</p>
									<div class="search_btn_area">
										<span>페이지당 표시 : </span>
										<select id="select_countPerPage" name="select_countPerPage" style="margin-right:20px;">
											<option value="10" <c:if test="${searchVO.page_countPer == '10'}">selected</c:if>>10</option>
											<option value="25" <c:if test="${searchVO.page_countPer == '25'}">selected</c:if>>25</option>
											<option value="50" <c:if test="${searchVO.page_countPer == '50'}">selected</c:if>>50</option>
										</select>
										<c:choose>
											<c:when test="${flag == 'standard'}">
												<input class="btn_reset" id="" type="button" value="전체 재고조회" onclick="ajaxRefreshQty_all();">
											</c:when>
											<c:otherwise>
												<input class="btn_reset" id="" type="button" value="전체 재고조회" onclick="ajaxRefreshQty_detail();">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<!--search_btn_wrap-->
								
								<c:choose>
									<c:when test="${flag == 'standard'}">
										<!-- board_list_wrap (게시물 리스트1) -->
										<div class="searchlist_wrap_tit">
											<table summary="제품검색">
												<caption>제품검색</caption>
												<colgroup>
													<col style="width: 40px;" />
													<col style="width: 40px;" />
													<col style="width: 110px;" />
													<col style="width: 300px;" />
													<col style="width: 90px;" />
													
													<col style="width: 80px;" />
													<!-- <col style="width: 90px;" /> -->
													<col style="width: 80px;" /> 
													<col style="width: 80px;" />
													<col style="width: 80px;" />

													<col style="" />
												</colgroup>
												<thead>
													<tr>
														<th scope="col" rowspan="2">
															<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
															<label class="blue_label" for="parentChkBox"></label>
														</th>
														<th scope="col" rowspan="2">관심</th>
														<th scope="col" rowspan="2">제품코드</th>
														<th scope="col" rowspan="2">품명</th>
														<th scope="col" rowspan="2">판매단위</th>
														
														<!--  <th scope="col" rowspan="2">구분</th>-->
														<!-- <th scope="col" rowspan="2">취급제한</th>-->
														<!--  <th scope="col" colspan="3" class="tbl_col_tit">재고량</th>-->
														<th scope="col" colspan="2" class="tbl_col_tit">재고량</th>
														<th scope="col" rowspan="2">장바<br>구니</th>
													</tr>
													<!--<tr>
														<jsp:directive.include file="/WEB-INF/views/templates/include_itemTitle.jsp" />
													</tr>-->
												</thead>
											</table>
										</div>
						
										<div class="searchlist_wrap">
											<table class="table_common focus_tr" id="table_item" summary="제품검색" >
												<caption>제품검색</caption>
												<colgroup>
													<col style="width: 40px;" />
													<col style="width: 40px;" />
													<col style="width: 110px;" />
													<col style="width: 300px;" />
													<col style="width: 90px;" />
													
													<col style="width: 80px;" />
													<!--  <col style="width: 90px;" />-->
													<col style="width: 80px;" />
													<col style="width: 80px;" />
													<col style="width: 80px;" />

													<col style="" />
												</colgroup>
												<tbody id="tbody_list">
													<c:choose>
														<c:when test="${fn:length(itemList) > 0 }" >
															<c:forEach items="${itemList }" var="row" varStatus="status">
																<tr>
																	<td class="txt_center">
																		<input type="hidden" id="hid_${row.item}${row.qty_allocjob}${row.u_m}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
																		<input type="hidden" id="hid_item" name="hid_item" value="${row.item}" >
																		<input type="hidden" id="hid_description" name="hid_description" value="${row.description}" >
																		<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.qty_allocjob}" >
																		<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.u_m}" >
																		
																		<input class="blue_checkbox" type="checkbox" id="${row.item}${row.qty_allocjob}${row.u_m}" name="chkBox" />
																		<label class="blue_label" for="${row.item}${row.qty_allocjob}${row.u_m}"></label>
																	</td>
																	<td class="txt_center">
																		<input type="button" id="btn_addFavorite" onclick="javascript:dialog_addFavItem(this);"
																		<c:choose> 
																			<c:when test="${row.fav_check != null && row.fav_check != ''}"> class="ico_wish_fixed"</c:when>
																			<c:otherwise> class="ico_wish"</c:otherwise>
																		</c:choose>/>
																	</td>
																	<td class="pro_name" id="td_item">${row.item}</td>
																	<td class="pro_name" id="td_description">${row.description}</td>
																	<td class="txt_rig" id="td_qty_allocjob">${row.qty_allocjob}${row.u_m}</td>
																	
																	<!-- <td class="txt_center">${row.pummog_gubun}</td> -->
																	<!--<td class="pro_code txt_center">${row.item_type}</td>-->
																	<td class="txt_rig ft_grey" id="td_qtyOnHand01">0</td>
																	<td class="txt_rig ft_grey" id="td_qtyOnHand02">0</td>
																	<!--<td class="txt_rig ft_grey" id="td_keepOnHand">0</td>-->
																	
																	<td class="txt_center">
																		<input type="button" class="ico_basket" onclick="javascript:addBasket(this);" value="담기">
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr id="tr_empty"><td class="pro_code txt_center" colspan="10">검색된 자료가 없습니다</td></tr>				
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
										<!-- //searchlist_wrap (게시물 리스트1) -->
									</c:when>
									
									<c:when test="${flag == 'detail'}">
										<!-- board_list_wrap (게시물 리스트2) -->
										<div class="searchlist_wrap_tit" style="overflow:hidden;" id="div_title">
											<div style="width:1690px;">
												<table summary="제품검색">
													<colgroup>
														<col style="width: 40px;" />
														<col style="width: 40px;" />
														<col style="width: 100px;" />
														<col style="width: 300px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="" />
													</colgroup>
													<thead>
														<tr>
															<th scope="col" rowspan="2">
																<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
																<label class="blue_label" for="parentChkBox"></label>
															</th>
															<th scope="col" rowspan="2">관심</th>
															<th scope="col" rowspan="2">제품코드</th>
															<th scope="col" rowspan="2">품명</th>
															
															<th scope="col" rowspan="2">판매단위</th>
															<th scope="col" rowspan="2">구분</th>
															<th scope="col" rowspan="2">장바<br>구니</th>
															<th scope="col" rowspan="1" colspan="3" class="tbl_col_tit">상시품재고</th>
															<th scope="col" rowspan="1" colspan="6" class="tbl_col_tit">주문품재고</th>
															<th scope="col" rowspan="1" colspan="6" class="tbl_col_tit">보관품재고</th>
														</tr>
														<tr>
															<jsp:directive.include file="/WEB-INF/views/templates/include_itemTitle.jsp" />
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">부산</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">안양</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">호남</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">중부</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">함안</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">부산</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">안양</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">호남</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">중부</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">함안</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
										<div class="searchlist_wrap"  style="overflow: auto;" id="div_list">
											<div style="width:1670px;">
												<table class="table_common focus_tr" id="table_item" summary="제품검색" >
													<colgroup>
														<col style="width: 40px;" />
														<col style="width: 40px;" />
														<col style="width: 100px;" />
														<col style="width: 300px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="" />
													</colgroup>
													<tbody id="tbody_list">
														<c:choose>
															<c:when test="${fn:length(itemList) > 0 }" >
																<c:forEach items="${itemList }" var="row" varStatus="status">
																	<tr>
																		<td class="txt_center">
																			<input type="hidden" id="hid_${row.item}${row.qty_allocjob}${row.u_m}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
																			<input type="hidden" id="hid_item" name="hid_item" value="${row.item}" >
																			<input type="hidden" id="hid_description" name="hid_description" value="${row.description}" >
																			<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.qty_allocjob}" >
																			<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.u_m}" >
																			
																			<input class="blue_checkbox" type="checkbox" id="${row.item}${row.qty_allocjob}${row.u_m}" name="chkBox" />
																		<label class="blue_label" for="${row.item}${row.qty_allocjob}${row.u_m}"></label>
																		</td>
																		<td class="txt_center">
																			<input type="button" id="btn_addFavorite" onclick="javascript:dialog_addFavItem(this);"
																			<c:choose> 
																				<c:when test="${row.fav_check != null && row.fav_check != ''}"> class="ico_wish_fixed"</c:when>
																				<c:otherwise> class="ico_wish"</c:otherwise>
																			</c:choose>/>
																		</td>
																		<td class="pro_name" id="td_item">${row.item}</td>
																		<td class="pro_name" id="td_description">${row.description}</td>
																		<td class="txt_rig" id="td_qty_allocjob">${row.qty_allocjob}${row.u_m}</td>
																		
																		<td class="txt_center" id="">${row.pummog_gubun}</td>
																		<td class="txt_center">
																			<input type="button" class="ico_basket" onclick="javascript:addBasket(this);" value="담기">
																		</td>
																		<td class="txt_rig ft_grey" id="td_s_jaego">0</td>
																		<td class="txt_rig ft_grey" id="td_s_ta_jaego">0</td>
																		
																		<td class="txt_rig ft_grey" id="td_j_jaego_1">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_3">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_4">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_5">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_6">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_7">0</td>
																		
																		<td class="txt_rig ft_grey" id="td_b_jaego_1">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_3">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_4">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_5">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_6">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_7">0</td>
																	</tr>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<tr id="tr_empty"><td class="pro_code txt_center" colspan="11">검색된 자료가 없습니다</td><td colspan="10"></td></tr>				
															</c:otherwise>
														</c:choose>
													</tbody>
												</table>
											</div>
										</div>
										<!-- //searchlist_wrap (게시물 리스트2) -->
									</c:when>
									
									
									<c:when test="${flag == 'order'}">
										<!-- board_list_wrap (게시물 리스트2) -->
										<div class="searchlist_wrap_tit" style="overflow:hidden;" id="div_title">
											<div style="">
												<table summary="제품검색">
													<colgroup>
														<col style="width: 40px;" />
														<col style="width: 40px;" />
														<col style="width: 100px;" />
														<col style="width: 270px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 50px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="" />
													</colgroup>
													<thead>
														<tr>
															<th scope="col" rowspan="2">
																<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
																<label class="blue_label" for="parentChkBox"></label>
															</th>
															<th scope="col" rowspan="2">관심</th>
															<th scope="col" rowspan="2">제품코드</th>
															<th scope="col" rowspan="2">품명</th>
															
															<th scope="col" rowspan="2">판매단위</th>
															<th scope="col" rowspan="2">구분</th>
															<th scope="col" rowspan="2">장바<br>구니</th>
															<th scope="col" rowspan="1" colspan="6" class="tbl_col_tit">주문품재고</th>
														</tr>
														<tr>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">부산</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">안양</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">호남</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">중부</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">함안</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
										<div class="searchlist_wrap"  style="overflow: auto;" id="div_list">
											<div style="">
												<table class="table_common focus_tr" id="table_item" summary="제품검색" >
													<colgroup>
														<col style="width: 40px;" />
														<col style="width: 40px;" />
														<col style="width: 100px;" />
														<col style="width: 270px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 50px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														
														<col style="width: 70px;" />
														<col style="width: 70px;" />
														<col style="" />
													</colgroup>
													<tbody id="tbody_list">
														<c:choose>
															<c:when test="${fn:length(itemList) > 0 }" >
																<c:forEach items="${itemList }" var="row" varStatus="status">
																	<tr>
																		<td class="txt_center">
																			<input type="hidden" id="hid_${row.item}${row.qty_allocjob}${row.u_m}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
																			<input type="hidden" id="hid_item" name="hid_item" value="${row.item}" >
																			<input type="hidden" id="hid_description" name="hid_description" value="${row.description}" >
																			<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.qty_allocjob}" >
																			<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.u_m}" >
																			
																			<input class="blue_checkbox" type="checkbox" id="${row.item}${row.qty_allocjob}${row.u_m}" name="chkBox" />
																		<label class="blue_label" for="${row.item}${row.qty_allocjob}${row.u_m}"></label>
																		</td>
																		<td class="txt_center">
																			<input type="button" id="btn_addFavorite" onclick="javascript:dialog_addFavItem(this);"
																			<c:choose> 
																				<c:when test="${row.fav_check != null && row.fav_check != ''}"> class="ico_wish_fixed"</c:when>
																				<c:otherwise> class="ico_wish"</c:otherwise>
																			</c:choose>/>
																		</td>
																		<td class="pro_name" id="td_item">${row.item}</td>
																		<td class="pro_name" id="td_description">${row.description}</td>
																		<td class="txt_rig" id="td_qty_allocjob">${row.qty_allocjob}${row.u_m}</td>
																		
																		<td class="txt_center" id="">${row.pummog_gubun}</td>
																		<td class="txt_center">
																			<input type="button" class="ico_basket" onclick="javascript:addBasket(this);" value="담기">
																		</td>
																		
																		<td class="txt_rig ft_grey" id="td_j_jaego_1">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_3">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_4">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_5">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_6">0</td>
																		<td class="txt_rig ft_grey" id="td_j_jaego_7">0</td>
																	</tr>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<tr id="tr_empty"><td class="pro_code txt_center" colspan="11">검색된 자료가 없습니다</td><td colspan="10"></td></tr>				
															</c:otherwise>
														</c:choose>
													</tbody>
												</table>
											</div>
										</div>
										<!-- //searchlist_wrap (게시물 리스트2) -->
									</c:when>
									
									<c:when test="${flag == 'keep'}">
										<!-- board_list_wrap (게시물 리스트3) -->
										<div class="searchlist_wrap_tit" style="overflow:hidden;" id="div_title">
											<div style="">
												<table summary="제품검색">
													<colgroup>
														<col style="width: 40px;" />
														<col style="width: 40px;" />
														<col style="width: 100px;" />
														<col style="width: 300px;" />
														<col style="width: 70px;" />
														
														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="width: 62px;" />

														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="" />
													</colgroup>
													<thead>
														<tr>
															<th scope="col" rowspan="2">
																<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');"/>
																<label class="blue_label" for="parentChkBox"></label>
															</th>
															<th scope="col" rowspan="2">관심</th>
															<th scope="col" rowspan="2">제품코드</th>
															<th scope="col" rowspan="2">품명</th>
															
															<th scope="col" rowspan="2">판매단위</th>
															<th scope="col" rowspan="2">구분</th>
															<th scope="col" rowspan="2">장바<br>구니</th>
															<th scope="col" rowspan="1" colspan="6" class="tbl_col_tit">보관품재고</th>
														</tr>
														<tr>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">부산</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">안양</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">호남</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">중부</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">함안</th>
															<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
										<div class="searchlist_wrap"  style="overflow: auto;" id="div_list">
											<div style="">
												<table class="table_common focus_tr" id="table_item" summary="제품검색" >
													<colgroup>
														<col style="width: 40px;" />
														<col style="width: 40px;" />
														<col style="width: 100px;" />
														<col style="width: 300px;" />
														<col style="width: 70px;" />
														
														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="width: 62px;" />

														<col style="width: 62px;" />
														<col style="width: 62px;" />
														<col style="" />
													</colgroup>
													<tbody id="tbody_list">
														<c:choose>
															<c:when test="${fn:length(itemList) > 0 }" >
																<c:forEach items="${itemList }" var="row" varStatus="status">
																	<tr>
																		<td class="txt_center">
																			<input type="hidden" id="hid_${row.item}${row.qty_allocjob}${row.u_m}" name="hid_mappingCode" value="${row.item}${row.qty_allocjob}${row.u_m}" >
																			<input type="hidden" id="hid_item" name="hid_item" value="${row.item}" >
																			<input type="hidden" id="hid_description" name="hid_description" value="${row.description}" >
																			<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.qty_allocjob}" >
																			<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.u_m}" >
																			
																			<input class="blue_checkbox" type="checkbox" id="${row.item}${row.qty_allocjob}${row.u_m}" name="chkBox" />
																			<label class="blue_label" for="${row.item}${row.qty_allocjob}${row.u_m}"></label>
																		</td>
																		<td class="txt_center">
																			<input type="button" id="btn_addFavorite" onclick="javascript:dialog_addFavItem(this);"
																			<c:choose> 
																				<c:when test="${row.fav_check != null && row.fav_check != ''}"> class="ico_wish_fixed"</c:when>
																				<c:otherwise> class="ico_wish"</c:otherwise>
																			</c:choose>/>
																		</td>
																		<td class="pro_name" id="td_item">${row.item}</td>
																		<td class="pro_name" id="td_description">${row.description}</td>
																		<td class="txt_rig" id="td_qty_allocjob">${row.qty_allocjob}${row.u_m}</td>
																		
																		<td class="txt_center">${row.pummog_gubun}</td>																		
																		<td class="txt_center">
																			<input type="button" class="ico_basket" onclick="javascript:addBasket(this);" value="담기">
																		</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_1">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_3">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_4">0</td>
																		
																		<td class="txt_rig ft_grey" id="td_b_jaego_5">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_6">0</td>
																		<td class="txt_rig ft_grey" id="td_b_jaego_7">0</td>
																	</tr>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<tr id="tr_empty"><td class="pro_code txt_center" colspan="13">검색된 자료가 없습니다</td><td colspan="9"></td></tr>				
															</c:otherwise>
														</c:choose>
													</tbody>
												</table>
											</div>
										</div>
										<!-- //searchlist_wrap (게시물 리스트3) -->
									</c:when>
								</c:choose>
				
								<div id="div_paging" class="pagingWrap"></div>
								<!-- // pagingWrap -->
								
								<div class="bottom_btn_wrap">
									<div class="right_btn_area">
										<input class="btn_basket" type="button" id="btn_addBasketCheckd" value="장바구니담기" onclick="javascript:addBasket(this);">
										<input class="btn_order" id="" type="button" value="주문서 작성" onclick="javascript:createOrder();">
									</div>
								</div>
								<!--bottom_btn_wrap-->
							</div>
						</div>
						<!--sub_body-->
				
					</div>
					<!--sub_contents-->
				</div>
				<!--sub_wrap-->
			</div>
			<!--sub_wrap_area-->

		<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		<jsp:directive.include file="/WEB-INF/views/templates/dialog_sdpa001001l.jsp" />
	</div>
	<!--wrap end-->
</form>
</body>
</html>