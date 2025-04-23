<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 견본요청서 조회 팝업  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	//팝업창의 부모 페이지에 선택한 단일 아이템 추가
	function selectRow(obj) {
		var ilja = $(obj).find("#hid_ilja").val();
		var jeonpyo_no = $(obj).find("#hid_jeonpyo_no").val();
		var saeobjang = $(obj).find("#hid_saeobjang").val();
		var pummyeong = $(obj).find("#hid_pummyeong").val();
			
		self.close();
		opener.focus();
		opener.addItem(ilja, jeonpyo_no, saeobjang, pummyeong);
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body style="background-color: white;" >
<form id="frm" name="frm">
	<div class="pop_wrap" style="width:750px;">			
		<div class="pop_sub_cont">
		<h2 class="pop_tit">견본요청서 조회</h2>
<!-- 			<div class="search_btn_wrap"> -->
<%-- 				<p class="result_num">검색결과 : ${fn:length(smplRequestList)}건</p> --%>
<!-- 			</div> -->
			<!--search_area-->
			<div class="gray_wrap">
				
				<span>기간</span>
				<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${smplReqVO.searchDate_from}" /> ~
				<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${smplReqVO.searchDate_to}" />
				<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpa005001p.do');" />
				<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpa005001p.do');" />
				<input type="button" value="1년" class="gray_inbtn" onclick="javascript:setDate('year1', 'sdpa005001p.do');" />
				<input type="button" value="2년" class="gray_inbtn" onclick="javascript:setDate('year2', 'sdpa005001p.do');" />
				<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpa005001p.do');">
			</div>
			
			<!-- board_list_wrap (게시물 리스트) -->
			<div class="searchlist_wrap_tit">
				<table>
					<colgroup>
						<col style="width:30px;" />
						<col style="width:100px;" />
						<col style="width:60px;" />
						<col style="width:100px;" />
						<col style="width:100px;" />
						
						<col style="" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">NO</th>
							<th scope="col">작성일</th>
							<th scope="col">견본번호</th>
							<th scope="col">사업장</th>
							<th scope="col">제출처</th>
							
							<th scope="col">제품명</th>
						</tr>
					</thead>
				</table>
			</div>

			<div class="searchlist_wrap">
				<table class="table_common" id="table_item">
					<colgroup>
						<col style="width:30px;" />
						<col style="width:100px;" />
						<col style="width:60px;" />
						<col style="width:100px;" />
						<col style="width:150px;" />
						
						<col style="" />
					</colgroup>
					<tbody id="tbody_list">
						<c:choose>
							<c:when test="${fn:length(smplRequestList) > 0 }" >
								<c:set var="no" value="1"/>
								<c:forEach items="${smplRequestList}" var="row" varStatus="status">
									<tr onclick="selectRow(this);">
										<c:if test="${row.cheoliil != null && row.cheoliil != ''}">
											<td class="txt_center pro_name">
												${no}
												<c:set var="no" value="${no + 1}" />
												<input type="hidden" id="hid_ilja" name="hid_ilja" value="${row.ilja}" />
												<input type="hidden" id="hid_jeonpyo_no" name="hid_jeonpyo_no" value="${row.jeonpyo_no}" />
												<input type="hidden" id="hid_saeobjang" name="hid_saeobjang" value="${row.saeobjang}" />
												<input type="hidden" id="hid_pummyeong" name="hid_pummyeong" value="${row.pummyeong}" />
											</td>
											<td class="txt_center pro_name" id="td_ilja">${row.ilja}</td>
											<td class="txt_center pro_name" id="td_jeonpyo_no">${row.jeonpyo_no}</td>
											<td class="txt_center pro_name" id="td_saeobjang">${row.saeobjang_nm}</td>
											<td class="txt_left pro_name">${row.jechulcheo}</td>
											<td class="pro_name" id="td_description">${row.pummyeong}</td>
										</c:if>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="txt_center" colspan="6">검색된 견본요청이 없습니다</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>				

			<div class="bottom_btn_wrap">					
				<div class="right_btn_area">						
					<input class="btn_order" id="" type="button" value="닫기" onclick="javascript:opener.focus(); self.close();">
				</div>
			</div>
			<!--bottom_btn_wrap-->
		</div>
		<!--sub_cont-->
		
		
	</div>
</form>
</body>
</html>