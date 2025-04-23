<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 외상정보조회 목록  -->
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.td_line {
	border-left:1px solid #e6e8ed;
}
.col_1 {
	background-color:#EDEDED; 
	
}
</style>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//연도 변경시 새로고침
		$("#searchYear").on("change", function(event) {
			changeYear($(event.target).val());
		});
	});
	
	//연도변경 버튼
	function changeYear(type) {
		var date = new Date();
		
		if(type == "this") {
			$("#searchYear").val(date.getFullYear()).prop("selected", true);
		} else if(type == "last") {
			date.setYear(date.getFullYear() -1);
			$("#searchYear").val(date.getFullYear()).prop("selected", true);
		} else {
			
		}
		c_submit("frm", "sdpd001001l.do");
	}
	
	//클릭시 상세 팝업
	function detailPopup(obj, type) {
		var ym = $(obj).parent().find("#hid_ym").val();
		
		if(ym.substr(4, 2) == "이월"){
			c_alert("이월된 외상 상세정보는 전년도 외상정보를 조회하여 확인하시길 바랍니다.");
			return;
		} else if(ym.substr(4, 2) == "합계") {
			return;
		}
			
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxGetCreditSub.do",
			data : {
				arg_yyyymm : ym
				, arg_type : type
			},
			datatype : "JSON",
			success : function(data) {
				var subList = data.creditSubList;
				var addHtml = ""; 
				
				if(type == "1") {
					$("#div_type1").show();
					$("#div_type2").hide();
					$("#div_type3").hide();
					$("#div_type1").find("#tbody_contents").children().remove();
					if(subList.length > 0) {
						for(var i=0; i < subList.length; i ++) {
							addHtml += "<tr>";
							addHtml += "<td>" + subList[i]["ord_dt"] + "</td>";
							addHtml += "<td>" + subList[i]["sale_no"] + "</td>";
							addHtml += "<td>" + subList[i]["seq"] + "</td>";
							addHtml += "<td class='txt_lef'>" + subList[i]["item_cd"] + "</td>";
							addHtml += "<td class='txt_lef'>" + subList[i]["pummyeong"] + "</td>";
							addHtml += "<td>" + subList[i]["sale_inv_unit"] + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["sale_inv_qty"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["price"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["amt"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["vat"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["tot_amt"]) + "</td>";
							addHtml += "</tr>";
						}
					} else {
						addHtml +='<tr><td class="pro_code txt_center" colspan="11">상세정보가 없습니다</td></tr>';
					}
					
					$("#div_type1").find("#tbody_contents").append(addHtml);
					
				} else if(type == "2") {
					$("#div_type1").hide();
					$("#div_type2").show();
					$("#div_type3").hide();
					$("#div_type2").find("#tbody_contents").children().remove();
					
					if(subList.length > 0) {
						for(var i=0; i < subList.length; i ++) {
							addHtml += "<tr>";
							addHtml += "<td>" + subList[i]["ilja"] + "</td>";
							addHtml += "<td class='txt_lef'>" + subList[i]["jeogyo"] + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["geumaeg"]) + "</td>";
							addHtml += "</tr>";
						}
					} else {
						addHtml +='<tr><td class="pro_code txt_center" colspan="3">상세정보가 없습니다</td></tr>';
					}
					
					$("#div_type2").find("#tbody_contents").append(addHtml);
					
				} else {
					$("#div_type1").hide();
					$("#div_type2").hide();
					$("#div_type3").show();
					$("#div_type3").find("#tbody_contents").children().remove();

					if(subList.length > 0) {
						for(var i=0; i < subList.length; i ++) {
							addHtml += "<tr>";
							addHtml += "<td>" + subList[i]["month"] + "</td>";
							addHtml += "<td class='txt_lef'>" + subList[i]["pummyeong"] + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["amt_etc"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["vat_etc"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["colet_amt_etc"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["jango_etc"]) + "</td>";
							addHtml += "</tr>";
						}
					} else {
						addHtml +='<tr><td class="pro_code txt_center" colspan="6">상세정보가 없습니다</td></tr>';
					}
					
					$("#div_type3").find("#tbody_contents").append(addHtml);
				}
			},
			error : function(xhr, status, error) {
				alert("error");	
				closeDialog("dialog_addFavorite");
			}
		});
		
		$("#dialog_detailView").dialog({
			title : "상세정보"
			,resizable : false
			,height : "auto"
			,width : "auto"
			,modal : true
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
						<h3 class="sub_tit">외상정보 조회</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>집계조회</li>
								<li>외상정보조회</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
						<div class="gray_wrap">
							<span>연도</span>
							<select id="searchYear" name="searchYear" style="margin-right:20px;">
								<c:set var="setYear" value="${creditHVO.searchYear - 5}" />
								<fmt:formatDate var="nowYear" value="${creditHVO.searchCurrent_date}" pattern="yyyy" />
								<c:forEach begin="0" end="10">
									<c:if test="${setYear <= nowYear}" >
										<option value="${setYear}" <c:if test="${creditHVO.searchYear == setYear}">selected</c:if>>${setYear}</option>
										<c:set var="setYear" value="${setYear + 1}" />
									</c:if>
								</c:forEach>
							</select>
							<input type="button" value="현재" class="gray_inbtn" onclick="javascript:changeYear('this');" />
							<input type="button" value="전년도" class="gray_inbtn" onclick="javascript:changeYear('last');" />
						</div>
						<!--  gray_wrap -->

						<div class="searchlist_wrap_tit">
							<table summary="외상조회" class="table_common">
								<colgroup>
									<col style="width: 60px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />

									<col style="width: 105px;" />
									<col style="width: 60px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />

									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">월</th>
										<th scope="col">매입</th>
										<th scope="col">부가세</th>
										<th scope="col">매입합계</th>
										<th scope="col">결제</th>

										<th scope="col" style="border-right: 4px solid #9ea5a8;">외상잔고</th>
										<th scope="col">월</th>
										<th scope="col">매입</th>
										<th scope="col">부가세</th>
										<th scope="col">결제</th>

										<th scope="col">기타잔고</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="notice_wrap">
							<table summary="주문조회" class="table_common focus_td">
								<caption>주문조회</caption>
								<colgroup>
									<col style="width: 60px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />

									<col style="width: 105px;" />
									<col style="width: 60px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />
									<col style="width: 105px;" />

									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(creditHeaderList) > 1}"><!-- 합계때문에 기준을 1로 설정 -->
											<c:forEach items="${creditHeaderList}" var="row" varStatus="status">
												<c:if test="${row.month != null && row.month != ''}" >
													<tr>
														<input type="hidden" id="hid_ym" name="hid_ym" value="${creditHVO.searchYear}${row.month}">
														<td class="txt_center" onclick="javascript:detailPopup(this,'1');">${row.month}</td>
														<td class="txt_rig" onclick="javascript:detailPopup(this, '1');"><fmt:formatNumber value="${row.amt}" groupingUsed="true"/></td>
														<td class="txt_rig" onclick="javascript:detailPopup(this,'1');"><fmt:formatNumber value="${row.vat}" groupingUsed="true"/></td>
														<td class="txt_rig" onclick="javascript:detailPopup(this,'1');"><fmt:formatNumber value="${row.amt_vat}" groupingUsed="true"/></td>
														<td class="txt_rig col_1" onclick="javascript:detailPopup(this,'2');"><fmt:formatNumber value="${row.colet_amt}" groupingUsed="true"/></td>
				
														<td class="txt_rig col_1" onclick="javascript:detailPopup(this,'2');"  style="border-right: 4px solid #9ea5a8;">
															<fmt:formatNumber value="${row.jango}" groupingUsed="true"/>
														</td>
														<td class="txt_center td_line" onclick="javascript:detailPopup(this,'3');">${row.month_etc}</td>
														<td class="txt_rig" onclick="javascript:detailPopup(this,'3');"><fmt:formatNumber value="${row.amt_etc}" groupingUsed="true"/></td>
														<td class="txt_rig" onclick="javascript:detailPopup(this,'3');"><fmt:formatNumber value="${row.vat_etc}" groupingUsed="true"/></td>
														<td class="txt_rig" onclick="javascript:detailPopup(this,'3');"><fmt:formatNumber value="${row.colet_amt_etc}" groupingUsed="true"/></td>
				
														<td class="txt_rig" onclick="javascript:detailPopup(this,'3');"><fmt:formatNumber value="${row.jango_etc}" groupingUsed="true"/></td>
													</tr>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" ><td class="pro_code txt_center" colspan="11">검색데이터가 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<!-- //searchlist_wrap (게시물 리스트) -->
					</div>
					<!--sub_cont-->

				</div>
				<!--// sub_contents-->
			</div>
			<!--// sub_wrap-->
		</div>
		<!--// sub_wrap_area-->
		<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		<jsp:directive.include file="/WEB-INF/views/templates/dialog_sdpd001001l.jsp" />
	</div>
	<!--wrap end-->
</form>
</body>

</html>