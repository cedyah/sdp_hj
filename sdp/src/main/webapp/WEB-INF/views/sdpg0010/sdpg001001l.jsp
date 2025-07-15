<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 경화제/신나대비표 품목 목록  -->
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
	//클릭시 상세 팝업
	function detailPopup(obj, type) {
		var pummog = $(obj).parent().find("#hid_pummog").val();
		
    	//c_alert("이월된 외상 상세정보는 전년도 외상정보를 조회하여 확인하시길 바랍니다.");
			
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxGetcontrastSub.do",
			data : {
				arg_pummog : pummog
				, arg_type : type
			},
			datatype : "JSON",
			success : function(data) {
				var subList = data.contrastSubList;
				var addHtml = ""; 
				
				if(type == "1") {
					$("#div_type1").show();
					$("#div_type1").find("#tbody_contents").children().remove();
					if(subList.length > 0) {
						for(var i=0; i < subList.length; i ++) {
							addHtml += "<tr>";
							addHtml += "<td class='txt_lef'>" + subList[i]["pummyeong"] + "</td>";
							addHtml += "<td>" + subList[i]["pa_danwi"] + "</td>";
							addHtml += "<td>" + subList[i]["po_danwi"] + "</td>";
							addHtml += "<td class='txt_lef'>" + subList[i]["aeg1_myeong"] + "</td>";
							addHtml += "<td>" + subList[i]["aeg1_danwi"] + "</td>";
							addHtml += "<td class='txt_lef'>" + subList[i]["aeg2_myeong"] + "</td>";
							addHtml += "<td>" + commaSplit(subList[i]["aeg2_danwi"]) + "</td>";
							addHtml += "<td class='txt_rig'>" + commaSplit(subList[i]["sinna"]) + "</td>";
							addHtml += "</tr>";
						}
					} else {
						addHtml +='<tr><td class="pro_code txt_center" colspan="8">상세정보가 없습니다</td></tr>';
					}
					
					$("#div_type1").find("#tbody_contents").append(addHtml);
					
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

	//검색
	function search() {
		$("#page_current").val("1");
		c_submit("frm", "sdpg001001l.do");
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
						<h3 class="sub_tit">경화제/신나대비표 조회</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>기준정보관리</li>
								<li>경화제/신나대비표조회</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->

					<div class="sub_cont">
                        <div class="search_area" style="height:50px; display: flex; justify-content: center; align-items: center; position: relative;">
                            <!-- 가운데 정렬할 부분 -->
                            <div style="display: flex; gap: 10px;">
                                <select id="select_searchDiv" name="searchDiv">
                                    <option value="description" <c:if test="${contrastHVO.searchDiv eq 'description'}">selected</c:if>>제품명</option>
                                    <option value="item_code" <c:if test="${contrastHVO.searchDiv eq 'item_code'}">selected</c:if>>제품코드</option>
                                </select>
                                <input class="txt" id="input_searchText" name="searchText" type="text" value="${contrastHVO.searchText}"
                                       placeholder="검색할 키워드를 띄어쓰기 공간을 주고 입력하십시오. 예) 슈퍼크린"
                                       onkeydown="if(event.keyCode==13){search(); return false;}">
                                <input class="btn_search" type="button" onclick="search();">
                            </div>
                            
                            <!-- 오른쪽 끝에 고정할 부분 -->
                            <span style="position: absolute; right: 10px; bottom: 0px; font-size: 12px; color: #999999; opacity: 0.7;">최대 100건이 조회됩니다.</span>
                        </div>
						<!--search_area-->

						<div class="searchlist_wrap_tit">
							<table summary="품목조회" class="table_common">
								<colgroup>
									<col style="width: 60px;" />
									<col style="width: 105px;" />
									<col style="width: 315px;" />
									<col style="width: 60px;" />
									<col style="width: 125px;" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">NO</th>
										<th scope="col">품목코드</th>
										<th scope="col">품      명</th>
										<th scope="col">액형</th>
										<th scope="col">A:B:C 비율</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="notice_wrap">
							<table summary="품목조회" class="table_common focus_td">
								<caption>품목조회</caption>
								<colgroup>
									<col style="width: 60px;" />
									<col style="width: 105px;" />
									<col style="width: 315px;" />
									<col style="width: 60px;" />
									<col style="width: 125px;" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(contrastHeaderList) > 1}"><!-- 합계때문에 기준을 1로 설정 -->
											<c:forEach items="${contrastHeaderList}" var="row" varStatus="status">
													<tr>
														<input type="hidden" id="hid_pummog" name="hid_pummog" value="${contrastHVO.pummog}${row.pummog}">
														<td class="txt_center" onclick="javascript:detailPopup(this,'1');">${row.no}</td>
														<td class="txt_center" onclick="javascript:detailPopup(this,'1');">${row.pummog}</td>
														<td class="txt_lef"    onclick="javascript:detailPopup(this,'1');">${row.pummyeong}</td>
														<td class="txt_center" onclick="javascript:detailPopup(this,'1');">${row.aeg}</td>
														<td class="txt_center" onclick="javascript:detailPopup(this,'1');">${row.biyul}</td>
													</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty" ><td class="pro_code txt_center" colspan="5">검색데이터가 없습니다</td></tr>
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
		<jsp:directive.include file="/WEB-INF/views/templates/dialog_sdpg001001l.jsp" />
	</div>
	<!--wrap end-->
</form>
</body>

</html>