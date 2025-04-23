<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 가격조회 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//특정 div에 페이징 추가('submit할 url', '현재 페이지', '페이지당 표시 갯수', '표시될 페이지 넘버 갯수', '전체 데이터 수')
		drawPagingDiv("sdpa007001l.do", "${searchVO.page_current}", "${searchVO.page_countPer}", 10, "${searchVO.page_totalCnt}");
		
		$("#select_countPerPage").on("change", function(event) {
			$("#page_countPer").val($(event.target).val());
			$("#page_current").val("1");
			search();
		});
		
		$("input[name='searchRadio_01']").on("click", function(event) {
			search();
		});
	});

	//검색
	function search() {
		var li_chkBox = $("input[name='search_jungbun']:checked");
		var str_itemGroup = "";
		
		if(li_chkBox.length < 1) {
			c_alert("제품 분류 검색조건은 최소 1가지 이상 선택하셔야 합니다.");
			return;
		}
		
		$("#page_current").val("1");
		c_submit("frm", "sdpa007001l.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm" onsubmit="">
<!-- 페이징변수 -->
<input type="hidden" id="page_current" name="page_current" value="${searchVO.page_current}">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="${searchVO.page_totalCnt}">      
<input type="hidden" id="page_countPer" name="page_countPer" value="${searchVO.page_countPer}">

<!-- 기타변수 -->
<input type="hidden" id="paramList" name="paramList">
<input type="hidden" id="jsonList" name="jsonList">
<input type="hidden" id="set_item_group" name="set_item_group" value="" >

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">가격조회</h3>
							<div class="local_nav">
								<ul>
									<li class="home">홈</li>  
									<li>주문관리</li>
									<li>가격조회</li>
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
			
							<div class="search_area" style="height:50px;">
								<select id="select_searchDiv" name="searchDiv">
									<option value="description" <c:if test="${searchVO.searchDiv eq 'description'}">selected</c:if>>제품명</option>
									<option value="item_code" <c:if test="${searchVO.searchDiv eq 'item_code'}">selected</c:if>>제품코드</option>
								</select>
								<input class="txt" id="input_searchText" name="searchText" type="text" value="${searchVO.searchText}"
									placeholder="검색할 키워드를 띄어쓰기 공간을 주고 입력하십시오. 예) 무광 백색" onkeydown="if(event.keyCode==13){search(); return false;}">
								<input class="btn_search" type="button" onclick="search();">
							</div>
							<!--search_area-->
							
							<div class="">
								<div class="search_btn_wrap">
									<p class="result_num">검색결과 : ${searchVO.page_totalCnt}건</p>
									<div class="search_btn_area">
										※부가세 별도&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span>페이지당 표시 : </span>
										<select id="select_countPerPage" name="select_countPerPage" style="">
											<option value="10" <c:if test="${searchVO.page_countPer == '10'}">selected</c:if>>10</option>
											<option value="25" <c:if test="${searchVO.page_countPer == '25'}">selected</c:if>>25</option>
											<option value="50" <c:if test="${searchVO.page_countPer == '50'}">selected</c:if>>50</option>
										</select>
									</div>
								</div>
								<!--search_btn_wrap-->
								<div class="searchlist_wrap_tit">
									<table>
										<colgroup>
											<col style="width: 60px;" />
											<col style="width: 100px;" />
											<col style="width: 400px;" />
											<col style="width: 100px;" />
											<col style="width: 120px;" />
											
											<col style="width: 120px;" />
											<col style="" />
										</colgroup>
										<thead>
											<tr>
												<th scope="col" rowspan="1">순번</th>
												<th scope="col" rowspan="1">제품코드</th>
												<th scope="col" rowspan="1">품명</th>
												<th scope="col" rowspan="1">판매단위</th>
												<th scope="col" rowspan="1">공장도</th>
												
												<th scope="col" rowspan="1">매입가</th>
												<th scope="col" rowspan="1">적용종료일</th>
											</tr>
										</thead>
									</table>
								</div>
				
								<div class="searchlist_wrap">
									<table class="table_common focus_tr" id="table_item" summary="제품검색" >
										<caption>제품검색</caption>
										<colgroup>
											<col style="width: 60px;" />
											<col style="width: 100px;" />
											<col style="width: 400px;" />
											<col style="width: 100px;" />
											<col style="width: 120px;" />
											
											<col style="width: 120px;" />
											<col style="" />
										</colgroup>
										<tbody id="tbody_list">
											<c:choose>
												<c:when test="${fn:length(priceList) > 0 }" >
													<c:forEach items="${priceList }" var="row" varStatus="status">
														<tr>
															<td class="txt_center">
																<input type="hidden" id="hid_${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}" name="hid_mappingCode" value="${row.jepum_code}${row.panmae_danwi_a}${row.panmae_danwi_b}" >
																<input type="hidden" id="hid_item" name="hid_item" value="${row.jepum_code}" >
																<input type="hidden" id="hid_description" name="hid_description" value="${row.pummyeong}" >
																<input type="hidden" id="hid_qty_allocjob" name="hid_qty_allocjob" value="${row.panmae_danwi_a}" >
																<input type="hidden" id="hid_u_m" name="hid_u_m" value="${row.panmae_danwi_b}" >
																${row.rnum}
															</td>
															<td class="pro_name" id="">${row.jepum_code}</td>
															<td class="pro_name" id="">${row.pummyeong}</td>
															<td class="txt_rig" id="">${row.panmae_danwi_a}${row.panmae_danwi_b}</td>
															<td class="txt_rig blue_B" id=""><fmt:formatNumber value="${row.gongjangdoga}" groupingUsed="true"/></td>
															
															<td class="txt_rig blue_B" id=""><fmt:formatNumber value="${row.maeibga}" groupingUsed="true"/></td>
															<td class="txt_center id="">${row.endil}</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr id="tr_empty"><td class="pro_code txt_center" colspan="7">검색된 자료가 없습니다</td></tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
								<!-- //searchlist_wrap (게시물 리스트1) -->
								<div id="div_paging" class="pagingWrap"></div>
								<!-- // pagingWrap -->
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