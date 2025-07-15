<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 사용자관리 - 검색조건관리 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$(document).on("change", "input[name='uf_salegroup1']", function(event) {
			saveUserSetting(event.target);
		});
		
		$(document).on("change", "input[name='parentChkBox_search']", function(event) {
			saveUserSetting(event.target);
		});

		//전체 체크박스 체크 여부
		var li_unchecked = $(".setting_area input[name='uf_salegroup1']:not(:checked)");
		if(li_unchecked.length == 0) {
			$("input[name='parentChkBox_search']").prop("checked", true);
		} else {
			$("input[name='parentChkBox_search']").prop("checked", false);
		}
		
		//체크 박스가 선택&해제 될때마다 전체 체크박스 체크할것인지 검사
		$("input[name='uf_salegroup1']").on("change", function(event) {
			if($(".setting_area input[name='uf_salegroup1']:not(:checked)").length == 0) {
				$("input[name='parentChkBox_search']").prop("checked", true);
			} else {
				$("input[name='parentChkBox_search']").prop("checked", false);
			}
		});		
	});
	
	//유저 셋팅 저장
	function saveUserSetting(obj) {
		var li_chkBox = $("input[name='uf_salegroup1']:checked");
		var str_itemGroup = "";
		
		for(var i=0; i < li_chkBox.length; i++) {
			str_itemGroup += $(li_chkBox[i]).attr("id") + ","; 
		}
		str_itemGroup = str_itemGroup.substring(0, str_itemGroup.length -1);

		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxSetItemGroup.do",
			data : {
				set_item_group : str_itemGroup
			},
			datatype : "JSON",
			success : function(data) {
				
			},
			error : function(xhr, status, error) {
				alert("error");	
			}
		});
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
	<form id="frm" name="frm">
		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">사용자관리</h3>
							<div class="local_nav">
								<ul>
									<li class="home">홈</li>
									<li>마이페이지</li>
									<li>사용자관리</li>
								</ul>
							</div>
							<!--local_nav-->
						</div>
						<!--local_nav_wrap-->
						<div class="sub_cont">
							<div class="mypage_tab_wrap">
								<ul>
									<li class="on"><a href="#">검색조건관리</a></li>
									<li><a href="sdpf004003u.do">비밀번호 변경</a></li>
								</ul>
							</div>
							
							<div class="mypage_setting">
								<p>※ 설정된 조건은 기본 검색 조건으로 사용됩니다.</p>
									<div class="setting_area">
										<table class="tbl_input" summary="사용자관리 - 검색조건">
											<caption>사용자관리 - 검색조건</caption>
											<colgroup>
												<col style="width:15%" />
												<col style="width:85%" />
											</colgroup>
											<tbody>
												<tr class="first">
													<th scope="row">검색조건</th>
													<td>		
														<div class="setting_input_area">
															<input class="search_checkbox" id="parentChkBox_search" name="parentChkBox_search" type="checkbox" onclick="checkAll(this, 'uf_salegroup1');"/>
															<label class="search_label" for="parentChkBox_search" style="font-weight: bold;">전체</label>
															<c:if test="${fn:length(itemGroupCodeList) > 0}">
																<c:forEach items="${itemGroupCodeList}" var="row" varStatus="status">
																	<input class="search_checkbox" id="${fn:trim(row.minor_cd)}" type="checkbox" name="uf_salegroup1" value="${row.minor_cd}"
																		<c:if test="${fn:contains(fn:trim(customerVO.set_item_group), fn:trim(row.minor_cd))}" > checked</c:if>/>
																	<label class="search_label" for="${fn:trim(row.minor_cd)}">${row.cd_nm}</label>
																</c:forEach>
															</c:if>
														</div>
													</td>													
												</tr>					
											</tbody>
										</table>										
									</div>
										
							</div>
							
							
						</div>
						<!--// sub_cont-->
					</div>
					<!--// sub_contents-->
				</div>
				<!--// sub_wrap-->
			</div>
			<!--// sub_wrap_area-->
			<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
			
		</div>
		<!--wrap end-->
	</form>
</body>
</html>