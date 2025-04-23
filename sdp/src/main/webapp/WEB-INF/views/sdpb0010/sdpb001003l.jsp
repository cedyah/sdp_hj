<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 연간위험물 유통량현황 목록  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	//화학물질 판매대장 출력
	function print() {
		c_alert("개발중...");
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
					 		<h3 class="sub_tit">연간위험물 유통량현황</h3>		       				
			       				<div class="local_nav">
			                         <ul>
				                           <li class="home">홈</li>
				                           <li>취급제한물질관리</li>
				                           <li>연간위험물 유통량현황</li>
			                         </ul>
			                   </div>
			                   <!--local_nav-->		                   
	       				</div>
	       				<!--local_nav_wrap-->
	       				
	         			<div class="sub_cont">
							<div class="gray_wrap">
								<span>기간</span>
								<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${conVO.searchDate_from}" /> ~
								<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${conVO.searchDate_to}" />
								<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpb001003l.do');" />
								<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpb001003l.do');" />
								<input type="button" value="올해" class="gray_inbtn" onclick="javascript:setDate('now_year', 'sdpb001003l.do');" />
								<input type="button" value="작년" class="gray_inbtn" onclick="javascript:setDate('last_year1', 'sdpb001003l.do');" />
								<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpb001003l.do');">
							</div>

							<div class="left_num_count">
								출력자료 : ${fn:length(list)}건
							</div>
							<!-- left_num_count -->

							<!-- board_list_wrap (게시물 리스트) -->
							<div class="searchlist_wrap_tit">
								<table class="">
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 160px;" />
										<col style="width: 230px;" />

										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col" rowspan="2">NO</th>
											<th scope="col" rowspan="2">유별</th>
											<th scope="col" rowspan="2">품명</th>
											<th scope="col" colspan="3">반입업체</th>
											<th scope="col" rowspan="2">연간반입량(L)</th>
											<th scope="col" rowspan="2">반입경로</th>
											<th scope="col" rowspan="2">비고</th>
										</tr>
										<tr>
											<th class="tbl_row_tit" style="border-right:1px solid #606c79;">업체명</th>
											<th class="tbl_row_tit" style="border-right:1px solid #606c79;">소재지(주소)</th>
											<th class="tbl_row_tit" style="">사업자등록번호</th>
										</tr>
									</thead>
								</table>
							</div>

							<div class="searchlist_wrap">
								<table class="table_common focus_tr">
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 160px;" />
										<col style="width: 230px;" />

										<col style="width: 120px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(list) > 0 }">
												<c:forEach items="${list}" var="row" varStatus="status">
													<tr>
														<td class="txt_center">${status.count}</td>
														<td class="txt_center">${row.youbyul}</td>
														<td class="txt_center">${row.jepum}</td>
														<td class="txt_lef">${row.sangho}</td>
														<td class="txt_lef">${row.juso}</td>
														
														<td class="txt_lef">${row.saeobja}</td>
														<td class="txt_rig">${row.panmaerayng}</td>
														<td class="txt_center">${row.banip}</td>
														<td class="txt_lef">${row.bigo}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td class="pro_code txt_center" colspan="9">검색된 자료가 없습니다</td></tr>
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
	</div>
	<!--wrap end-->
</form>
</body>
</html>