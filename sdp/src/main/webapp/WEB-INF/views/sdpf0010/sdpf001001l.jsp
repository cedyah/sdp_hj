<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />

<style type="text/css">
</style>

<script type="text/javascript">
	var refreshCheck = false;

	$("document").ready(function() {
		ajaxRefreshQty_all(); //화면상 품목들 재고 조회
		// 		setView();					//jquery select 등 화면 준비

		$(".wish_warp label").html(
				$(".wish_warp select option:selected").html()); //관심품목 선택값을 label에 반영        	

		$("#select_group_cd").change(function() { //관심품목 그룹이 변경되면 화면 새로고침
			c_submit("frm", "sdpf001001l.do");
		});
	});

	//화면 준비
	function setView() {
		$("#select_group_cd").selectmenu({
			change : function(event, data) {
				c_submit("frm", "sdpf001001l.do");
			}
		});
	}

	//장바구니 담기
	function addBasket(obj) {
		var item;
		var itemList = [];
		
		if(obj.id == "btn_addBasketCheckd") {		//체크박스로 선택해 추가하는 경우 다수를 장바구니에 추가하는 경우.
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
				, qty_allocjob : $(li_chkBox[i]).parent().find("#hid_qty_allocjob").val()
				, u_m : $(li_chkBox[i]).parent().find("#hid_u_m").val()
				, description : $(li_chkBox[i]).parent().find("#hid_description").val()
				, qty_on_hand01 : "0"
			}
			jsonList.push(item);
		}
		
		$("#jsonList").val(JSON.stringify(jsonList));
		c_submit("frm", "sdpa002001u.insert.do");
	}

	//관심품목 관리 팝업 호출
	function dialog_manageFavGroup() {
		$("#dialog_manageFavGroup").dialog({
			title : "관심품목 그룹 관리",
			height : "auto",
			width : "auto",
			resizable : false,
			modal : true,
			close : function() {
				closeManageFavGroupDialog();
			}
		});
		return;
	}

	//관심품목 그룹 관리 다이얼로그 닫기 전에 수정 됐는지 여부 체크. 수정 사항 있으면 화면 갱신
	function closeManageFavGroupDialog() {
		if (refreshCheck) {
			c_submit("frm", "sdpf001001l.do");
		} else {
			closeDialog('dialog_manageFavGroup');
		}
	}

	//관심품목 그룹 추가
	function addFavGroup(obj) {
		var group_cdnm = $(obj).parent().find("input[type='text']").val();

		if (group_cdnm.length < 1) {
			c_alert("관심품목 그룹명을 입력해 주십시오");
			return;
		}

		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxAddFavGroup.do",
			data : {
				group_cdnm : group_cdnm
			},
			datatype : "JSON",
			success : function(data) {
				refreshCheck = true;

				$("#div_appendRow").find("li").attr("id", "li_" + data.group_cd);
				$("#div_appendRow").find("#hid_group_cdnm").val(group_cdnm);
				$("#div_appendRow").find("#span_group_cdnm").html(group_cdnm);
				$("#div_appendRow").find("#btn_delFavGroup").attr("onclick","javascript:dialog_delFavGroup('" + data.group_cd+ "');");

				$("#ul_favGroup").append($("#div_appendRow").html());

				//LI ID값을 초기화 시켜 주지 않으면 li를 Remove할때 같은 아이디 값이 2개 되기 때문에 문제가됨
				$("#div_appendRow").find("li").attr("id", "");

			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});

	}

	//관심품목 그룹 삭제 확인 팝업
	function dialog_delFavGroup(deleteGroupCd) {
		$("#btn_confirmDel").attr("onClick", "javascript:delFavGroup('" + deleteGroupCd + "');");

		$("#dialog_delFavGroup").dialog({
			title : "관심품목 그룹 삭제",
			resizable : true,
			height : "auto",
			width : "auto",
			modal : true
		});
		return;
	}

	//실제로 관심품목 그룹을 DB에서 삭제
	function delFavGroup(group_cd) {
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxDeleteFavGroup.do",
			data : {
				group_cd : group_cd
			},
			datatype : "JSON",
			success : function(data) {
				refreshCheck = true;
				$("#li_" + group_cd).remove();
				closeDialog('dialog_delFavGroup');
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	}

	//관심품목 그룹 관리 다이얼로그. 수정 버튼 클릭시  show 되는 Div 교체
	function divChange(obj, displayCheck) {
		if (displayCheck) {
			var text = $(obj).parent().parent().parent().find("#span_group_cdnm").html();

			$(obj).parent().parent().parent().find("#div_select").hide();
			$(obj).parent().parent().parent().find("#div_update").show();
			$(obj).parent().parent().parent().find("#div_update").find("#ipt_group_cdnm").val(text);
			$(obj).parent().parent().parent().find("#div_update").find("#ipt_group_cdnm").focus();

		} else {
			$(obj).parent().parent().parent().find("#div_select").show();
			$(obj).parent().parent().parent().find("#div_update").hide();
		}
	}

	//관심그룹 이름수정
	function updateFavGroup(obj) {
		var updateGroupCd = $(obj).parent().parent().parent("li").attr("id").replace("li_", "");
		var updateGroupCdnm = $(obj).parent().parent().find("#ipt_group_cdnm").val();
		var targetSpan = $(obj).parent().parent().parent().find("#span_group_cdnm");

		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxUpdateFavGroup.do",
			data : {
				group_cd : updateGroupCd,
				group_cdnm : updateGroupCdnm
			},
			datatype : "JSON",
			success : function(data) {
				refreshCheck = true;
				$(targetSpan).html(updateGroupCdnm);
				$(targetSpan).parent().parent().find("#ipt_group_cdnm").val(updateGroupCdnm);
				$(targetSpan).parent().parent().find("#div_select").show();
				$(targetSpan).parent().parent().find("#div_update").hide();
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	}

	//관심품목 아이템 이동&복사 다이얼로그 호출
	function dialog_alterFavItem(flag) {
		if ($("input[name='chkBox']:checked").length < 1) {
			c_alert("품목을 선택해 주십시오");
			return;
		}

		if (flag == "move") {
			$("#dialog_moveFavItem").dialog({
				title : "관심품목 이동",
				resizable : true,
				modal : true
			});
		} else if (flag == "copy") {
			$("#dialog_copyFavItem").dialog({
				title : "관심품목 복사",
				resizable : true,
				modal : true
			});
		}

	}

	//관심품목 아이템 이동&복사
	function alterFavItem(obj, flag) {
		var group_cd = $("#select_group_cd option:selected").val();
		var target_group_cd = $(obj).parent().parent().find("#target_group_cd option:selected").val();
		var jsonList = [];
		var jsonObj;
		var li_chkBox = $("input[name='chkBox']:checked");

		for (var i = 0; i < li_chkBox.length; i++) {
			jsonObj = {
				item			: $(li_chkBox[i]).parent().find("input[name='hid_item']").val(),
				qty_allocjob	: $(li_chkBox[i]).parent().find("input[name='hid_qty_allocjob']").val(),
				u_m				: $(li_chkBox[i]).parent().find("input[name='hid_u_m']").val()
			};
			
			jsonList.push(jsonObj);
		}

		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxAlterFavItem.do",
			data : {
				flag : flag,
				group_cd : group_cd,
				target_group_cd : target_group_cd,
				jsonList : JSON.stringify(jsonList)
			},
			datatype : "JSON",
			success : function(data) {
				c_submit("frm", "sdpf001001l.do");
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	}

	//관심품목 아이템 삭제
	function deleteFavItem() {
		var li_chkBox = $("#table_item input[name='chkBox']:checked");
		var paramList = [];
		var group_cd = $("#select_group_cd option:selected").val();

		if (li_chkBox.length < 1) {
			c_alert("품목을 선택해주십시오");
			return;
		}
		//커스텀 confirm
		c_confirm("선택된 제품들을 삭제하시겠습니까?").then(function(result) {
			if (result) { //yes Click
				for (var i = 0; i < li_chkBox.length; i++) {
					paramList.push(li_chkBox[i].id);
				}
				$.ajaxSettings.traditional = true;
				jQuery.ajax({
					type : "POST",
					url : "ajaxDeleteFavItem.do",
					data : {
						group_cd : group_cd,
						paramList : paramList
					},
					datatype : "JSON",
					success : function(data) {
						c_submit("frm", "sdpf001001l.do");
					},
					error : function(xhr, status, error) {
						alert(error);
					}
				});
			} else { //no Click
				return;
			}
		});
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
	<form id="frm" name="frm" action="">
		<input type="hidden" id="paramList" name="paramList"> <input
			type="hidden" id="jsonList" name="jsonList">

		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">관심품목관리</h3>
							<div class="local_nav">
								<ul>
									<li class="home">홈</li>
									<li>주문관리</li>
									<li>제품검색</li>
								</ul>
							</div>
							<!--local_nav-->
						</div>
						<div class="sub_cont">
							<div class="search_btn_wrap">
								<div class="selectDiv" style="width: 150px;">
									<select id="select_group_cd" name="select_group_cd">										
										<c:forEach items="${favGroupList}" var="row"
											varStatus="status">
											<option value="${row.group_cd}"
												<c:if test="${favGroupVO.select_group_cd eq row.group_cd}">selected</c:if>>${row.group_cdnm}</option>
										</c:forEach>
									</select>
								</div>
								<span class="group_num">의 관심상품 :<strong>${fn:length(favItemList) }건</strong></span>
								<input type="image" src="img/btn_groupsetting.gif" alt="그룹관리"
									title="그룹관리"
									onclick="javascript:dialog_manageFavGroup(); return false;" />

								<div class="search_btn_area">
									<input class="btn_reset" id="" type="button" value="전체 재고조회"
										onclick="ajaxRefreshQty_all();">
								</div>
							</div>
							<!--search_btn_wrap-->

							<!-- board_list_wrap (게시물 리스트) -->
							<div class="searchlist_wrap_tit">
								<table summary="제품검색">
									<caption>제품검색</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 100px;" />
										<col style="width: 500px;" />
										<col style="width: 90px;" />

										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col" rowspan="2">
												<input class="blue_checkbox" id="parentChkBox" name="parentChkBox" type="checkbox" onclick="checkAll(this, 'chkBox');" />
												<label class="blue_label" for="parentChkBox"></label>
											</th>
											<th scope="col" rowspan="2">제품코드</th>
											<th scope="col" rowspan="2">품명</th>

											<th scope="col" rowspan="2">판매단위</th>

											<th scope="col" colspan="3" class="tbl_col_tit">재고</th>
											<th scope="col" rowspan="2">장바구니</th>
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
										<col style="width: 100px;" />
										<col style="width: 500px;" />
										<col style="width: 90px;" />

										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="" />
									</colgroup>
									<tbody id="tbody_list">
										<c:choose>
											<c:when test="${fn:length(favItemList) > 0 }">
												<c:forEach items="${favItemList}" var="row"
													varStatus="status">
													<tr>
														<td class="txt_center">
															<input type="hidden" id="hid_${row.item}${fn:substring(row.qty_allocjob, 0, 1) == '.' ? '0'+row.qty_allocjob:row.qty_allocjob}${row.u_m}"
																name="hid_mappingCode" value="${row.item}${fn:substring(row.qty_allocjob, 0, 1) == '.' ? '0'+row.qty_allocjob:row.qty_allocjob}${row.u_m}" >
															<input type="hidden" id="hid_item" name="hid_item" value="${row.item}" >
															<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.qty_allocjob}" >
															<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.u_m}" >
															<input type="hidden" id="hid_description" name="hid_description" value="${row.description}" >
															
															<input class="blue_checkbox" id="${row.item}" type="checkbox" name="chkBox" />
															<label class="blue_label" for="${row.item}"></label>
														</td>
														<td class="pro_name" id="td_item">${row.item}</td>
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
															<input type="button" class="ico_basket" onclick="javascript:addBasket(this);" value="담기">
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="pro_code txt_center" colspan="8">추가된 제품이
														없습니다</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<!-- //searchlist_wrap (게시물 리스트) -->

							<div class="bottom_btn_wrap">
								<div class="left_btn_area">
									<input class="btn_move" type="button" value="이동" onclick="dialog_alterFavItem('move');">
									<input class="btn_copy" type="button" value="복사" onclick="dialog_alterFavItem('copy');">
									<input class="btn_del" type="button" value="삭제" onclick="deleteFavItem();">
								</div>
								<div class="right_btn_area">
									<input class="btn_basket" type="button" id="btn_addBasketCheckd" value="장바구니담기" onclick="javascript:addBasket(this);">
									<input class="btn_order" id="" type="button" value="주문서 작성" onclick="javascript:createOrder();">
								</div>
							</div>
							<!--bottom_btn_wrap-->
						</div>
						<!--sub_body-->
					</div>
					<!--sub_contents-->
				</div>
				<!--sub_wrap-->
			</div>
			<!--sub_wrap_area-->
			<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		</div>
		<!--wrap end-->

		<!-- custom dialog -->
		<jsp:directive.include file="/WEB-INF/views/templates/dialog_sdpf001001l.jsp" />
		
	</form>
</body>
</html>