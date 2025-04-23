<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 장바구니 - 장바구니 목록  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	var refreshCheck = false;
	
	$(document).ready(function() {
		//재고조회
		ajaxRefreshQty_all();
		
		//모든 체크박스 체크하고 화면 불러옴
		$("input[name='chkBox']").attr("checked", true);
		$("input[id='parentChkBox']").attr("checked", true);
		
		//장바구니 수량이 변경되면 true가 되어 저장 가능 상태로 변경
		$("[name*='input_qty']").change(function() {
			refreshCheck = true;
		});
	});
	
	//아이템 삭제
	function removeItem() {
       	var li_chkBox = $("input[name='chkBox']:checked");
       	var paramList = [];
       	
       	for(var i=0; i < li_chkBox.length; i++) {
       		if(li_chkBox[i].checked) {
       			paramList.push(li_chkBox[i].id);
       		}
       	}

       	if(paramList.length < 1) {
       		c_alert("삭제할 품목을 선택해 주십시오"); return;
       	}
       	
		c_confirm("해당 품목을 장바구니에서 삭제하시겠습니까?").then(function (result) {		//커스텀 confirm
	        if(result){		//yes Click
	        	$.ajaxSettings.traditional = true;
	        	jQuery.ajax({
	        		type : "POST",
	        		url : "ajaxDeleteBasket.do",
	        		data : {
	        			paramList : paramList
	        		},
	        		datatype : "JSON",
	        		success : function(data) {
	        			for(var i=0; i < li_chkBox.length; i++) {
	        				$(li_chkBox).parent().parent("tr").remove();
	        			}
	        			
	        			if($("#tbody_list tr").length < 1) {
	           				$("#tbody_list").append("<tr><td class='txt_center' colspan='9'>추가된 제품이 없습니다</td></tr>");
	           				$("#p_listCnt").html("담긴상품 : 0건");
	           			} else {
	    	       			$("#p_listCnt").html("담긴상품 : "+$("#tbody_list tr").length+"건");
	           			}
	        		},
	        		error : function(xhr, status, error) {
	        			alert(error);
	        		}
	        	});
	        } else {		//no Click
	        	
	        }
	    });
	}
	
	//장바구니 저장
	function saveBasket() {
		if(!refreshCheck) {
			c_alert("변경된 내용이 없습니다"); return;
		}
		
		c_confirm("변경된 수량을 저장 하시겠습니까?").then(function (result) {		//커스텀 confirm
	        if(result){		//yes Click
	        	c_submit("frm", "sdpf002001l_u.do");
	        } else {		//no Click
	        	
	        }
	    });
	}
	
	//주문서 작성으로
	function createOrder() {
		var li_chkBox = $("input[name='chkBox']:checked");
		var jsonList = [];
		var obj;
		
		if(li_chkBox.length < 1) {
			c_alert("선택된 제품이 없습니다"); return;
		}
		
		for(var i=0; i < li_chkBox.length; i++) {
			obj = {
				item : $(li_chkBox[i]).parent().find("#hid_item").val()
				, qty_allocjob : $(li_chkBox[i]).parent().find("#hid_qty_allocjob").val()
				, u_m : $(li_chkBox[i]).parent().find("#hid_u_m").val()
				, description : $(li_chkBox[i]).parent().find("#hid_description").val()
				, qty_on_hand01 : $(li_chkBox[i]).parent().parent().find("#input_qty").val()
			}
			jsonList.push(obj);
			console.log(obj);
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		c_submit("frm", "sdpa002001u.insert.do");
	}
	
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body onload="">
	<form id="frm" name="frm">
		<input type="hidden" id="paramList" name="paramList">
		<input type="hidden" id="jsonList" name="jsonList">
		<input type="hidden" id="pageCheck" name="pageCheck" value="Y">
		
		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

			<div class="sub_wrap_area">
	           <div class="sub_wrap">
					<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">장바구니</h3>
							<div class="local_nav">
								<ul>
									<li style="background:none;">
										<img src="img/navi_home.gif" alt="HOME" title="HOME"></li>
									<li>주문관리</li>
									<li>장바구니</li>
								</ul>
							</div>
							<!--local_nav-->
						</div>
						<div class="sub_cont">
							<div class="search_btn_wrap">
								<p class="result_num" id="p_listCnt">담긴상품 : ${fn:length(basketList)}건</p>
								<div class="search_btn_area">
									<input class="btn_reset" id="" type="button" value="전체 재고조회" onclick="ajaxRefreshQty_all();">
									<input class="btn_newmake" id="" type="button" value="저장" onclick="javascript:saveBasket();">
								</div>
							</div>
		
							<!-- board_list_wrap (게시물 리스트) -->
							<div class="searchlist_wrap_tit">
								<table summary="제품검색">
									<caption>제품검색</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 40px;" />
										<col style="width: 170px;" />
										<col style="width: 410px;" />
										<col style="width: 80px;" />
										
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col" rowspan="2">
												<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox"
													onclick="checkAll(this, 'chkBox');" />
												<label class="blue_label" for="parentChkBox"></label>
											</th>
											<th scope="col" rowspan="2">관심<br>품목</th>
											<th scope="col" rowspan="2">제품코드</th>
											<th scope="col" rowspan="2">품명</th>
											<th scope="col" rowspan="2">판매단위</th>
											
											<th scope="col" colspan="3" class="tbl_col_tit">재고</th>
											<th scope="col" rowspan="2">주문수량</th>
										</tr>
										<tr>
											<jsp:directive.include file="/WEB-INF/views/templates/include_itemTitle.jsp" />
										</tr>
									</thead>
								</table>
							</div>
		
							<div class="searchlist_wrap">
								<table class="table_common" summary="제품검색" id="table_item">
									<caption>제품검색</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 40px;" />
										<col style="width: 170px;" />
										<col style="width: 410px;" />
										<col style="width: 80px;" />
										
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="" />
									</colgroup>
									<tbody id="tbody_list">
										<c:choose>
											<c:when test="${fn:length(basketList) > 0 }">
												<c:forEach items="${basketList}" var="row" varStatus="status">
													<tr id="tr_${row.item}">
														<td class="txt_center">
															<input type="hidden" id="hid_${row.item}${fn:substring(row.qty_allocjob, 0, 1) == '.' ? '0'+row.qty_allocjob:row.qty_allocjob}${row.u_m}"
																name="hid_mappingCode" value="${row.item}${fn:substring(row.qty_allocjob, 0, 1) == '.' ? '0'+row.qty_allocjob:row.qty_allocjob}${row.u_m}" >
															<input type="hidden" id="hid_item" name="hid_item" value="${fn:trim(row.item)}" >
															<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${fn:trim(row.qty_allocjob)}" >
															<input type="hidden" id="hid_u_m" name="hid_u_m" value="${fn:trim(row.u_m)}" >
															<input type="hidden" id="hid_description" name="hid_description" value="${fn:trim(row.description)}" >
															
															<input class="blue_checkbox" type="checkbox" id="${fn:trim(row.item)}${fn:trim(row.qty_allocjob)}${fn:trim(row.u_m)}" name="chkBox" />
															<label class="blue_label" for="${fn:trim(row.item)}${fn:trim(row.qty_allocjob)}${fn:trim(row.u_m)}"></label>
														</td>
														<td class="txt_center">
															<input type="button" id="btn_addFavorite" onclick="javascript:dialog_addFavItem(this);"
																<c:choose> 
																	<c:when test="${row.fav_check != null && row.fav_check != ''}"> class="ico_wish_fixed"</c:when>
																	<c:otherwise> class="ico_wish"</c:otherwise>
																</c:choose>/>
														</td>
														<td class="pro_code">${row.item}</td>
														<td class="pro_name" id="td_description">
															<c:choose>
																<c:when test="${row.description == ''}"><a style="font-weight: bold; color:#FF0000;">주문이 불가한 품목입니다. 삭제해주십시오.</a></c:when>
																<c:otherwise>${row.description}</c:otherwise>
															</c:choose>
														</td>
														<td class="txt_rig">
															<c:choose>
																<c:when test="${fn:substring(row.qty_allocjob, 0, 1) == '.'}">
																	0${row.qty_allocjob}${row.u_m}
																</c:when>
																<c:otherwise>
																	${row.qty_allocjob}${row.u_m}
																</c:otherwise>
															</c:choose>
														</td>
														
														<td class="txt_rig ft_grey" id="td_qtyOnHand01">0</td>
														<td class="txt_rig ft_grey" id="td_qtyOnHand02">0</td>
														<td class="txt_rig ft_grey" id="td_keepOnHand">0</td>
														<td class="txt_center">
															<input class="entry" id="input_qty" name="input_qty" type="text" value="${row.qty_basket}">
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td class="txt_center" colspan="9">추가된 제품이 없습니다</td></tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<!-- //searchlist_wrap (게시물 리스트) -->
		
							<div class="bottom_btn_wrap">
								<div class="right_btn_area">
									<input class="btn_del2" type="button" id="" value="선택 삭제" onclick="javascript:removeItem();">
									<input class="btn_order" id="" type="button" value="주문서 작성" onclick="javascript:createOrder();">
								</div>
							</div>
							<!--bottom_btn_wrap-->
						</div>
						<!--sub_cont-->
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