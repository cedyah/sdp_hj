<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 주문조회 - 주문조회 목록  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />

<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#searchCheckBox_01").on("change", "", function() {
			c_submit('frm', 'sdpa002001l.do');
		});
	});
	
	//상세정보 화면으로 이동
	function detailCo(ilja, jeonpyo_no) {
		$("#ilja").val(ilja);
		$("#jeonpyo_no").val(jeonpyo_no);
		c_submit("frm", "sdpa002001d.do");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
	<form id="frm" name="frm">
		<input type="hidden" id="ilja" name="ilja" value=""/>
		<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value=""/>

		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">주문조회</h3>
							<div class="local_nav">
								<ul>
									<li class="home">홈</li>  
									<li>주문관리</li>
									<li>주문조회</li>
								</ul>
							</div>
							<!--local_nav-->
						</div>
						
						<div class="sub_cont">
							<div class="gray_wrap">

								<span>주문일</span>
								<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${coVO.searchDate_from }" /> ~ 
								<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${coVO.searchDate_to }"/>
								<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpa002001l.do');"/>
								<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpa002001l.do');" />
								<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpa002001l.do');" />
								<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpa002001l.do');" style="margin-right:40px;"/>
								<input type="checkbox" class="search_checkbox" id="searchCheckBox_01" name="searchCheckBox_01"
									<c:if test="${coVO.searchCheckBox_01 == 'on'}">checked</c:if>/>
								<label class="search_label" for="searchCheckBox_01" style="padding-left:0px;">출하완료포함</label>
								<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpa002001l.do');">
							</div>

							<div class="left_num_count">
								주문서 : ${fn:length(coList)}건
							</div>
							<!-- left_num_count -->

							<!-- board_list_wrap (게시물 리스트) -->
							<div class="searchlist_wrap_tit">
								<table summary="주문조회" class="table_common">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 70px;" />

										<col style="width: 100px;" />
										<col style="width: 300px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">NO</th>
											<th scope="col">주문번호</th>
											<th scope="col">주문일자</th>
											<th scope="col">현재상태</th>
											<th scope="col">배달구분</th>
											
											<th scope="col">배달요청일</th>
											<th scope="col">배달장소</th>
											<th scope="col">인수자</th>
											<th scope="col">판매일자</th>
											<th scope="col">판매전표<br>번호</th>
										</tr>
									</thead>
								</table>
							</div>

							<div class="searchlist_wrap">
								<table summary="주문조회" class="table_common">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 70px;" />

										<col style="width: 100px;" />
										<col style="width: 300px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(coList) > 0 }">
<%-- 												<c:set var="no" value="${fn:length(coList)}" /> --%>
												<c:forEach items="${coList}" var="row" varStatus="status">
													<tr onclick="javascript:detailCo('${row.ilja}','${row.jeonpyo_no}'); return false;" style="cursor: pointer;">
														<td class="txt_center">${status.count}</td>
														<td class="txt_center">${row.jeonpyo_no}</td>
														<td class="txt_center">${row.ilja}</td>
														<td class="txt_center">${row.jindo_code_nm}</td>
														<td class="txt_center">${row.baedal_gubun}</td>
														
														<td class="txt_center">${row.yocheongil}</td>
														<td class="txt_lef">${row.addr1} ${row.addr2}</td>
														<td class="txt_center">${row.insuja}</td>
														<td class="txt_center">${row.panmae_ilja}</td>
														<td class="txt_center">${row.panmae_jeonpyo_no}</td>
													</tr>
<%-- 													<c:set var="no" value="${no - 1}" /> --%>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="pro_code txt_center" colspan="9">주문 내역이 없습니다</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<!-- //searchlist_wrap (게시물 리스트) -->
						</div>
						<!--sub_cont-->

					</div>
					<!--sub_contents-->
				</div>
				<!--sub_wrap-->
			</div>
			<!--sub_wrap_area-->
			<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
			
		</div>
		<!--wrap end-->
	</form>
</body>

</html>