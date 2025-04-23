<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 포인트 목록 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	//사은품 신청 화면 호출
	function callGiftForm() {
		if("${pointVO.gift_check}" == 'insert') {
			c_submit("frm", "sdpf003005u.insert.do")
		} else if("${pointVO.gift_check}" == 'update') {
			c_submit("frm", "sdpf003005u.update.do")
		} else {
			c_alert("현재는 상품권 신청 가능 기간이 아닙니다");
		}
	}
	
	//연도 변경
	function changeYear(num) {
		var date = new Date();
		$("#searchYear").val(date.getFullYear() + num);
		search();
	}
	
	//조회
	function search() {
		c_submit("frm", "sdpf003002l.do");
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
						<h3 class="sub_tit">포인트 관리</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>마이페이지</li>
								<li>포인트 관리</li>
								<li>주문 포인트</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->
					
					<div class="sub_cont">
						<!-- 상단 텝메뉴  -->
						<jsp:directive.include file="/WEB-INF/views/sdpf0030/sdpf0030_include.jsp" />
						
						<div class="gray_wrap">					
							<span>연도</span>
							<input type="text" class="entry" id="searchYear" name="searchYear" value="${pointVO.searchYear}" maxlength="4" style="width:55px;text-align:center;" />
							<input type="button" value="금년" class="gray_inbtn" onclick="javascript:changeYear(0);"/>
							<input type="button" value="작년" class="gray_inbtn" onclick="javascript:changeYear(-1);"/>
							<input type="button" value="제작년" class="gray_inbtn" onclick="javascript:changeYear(-2);"/>								
				            <input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:search();">
						</div>				            
	         				<div class="searchlist_wrap_tit">
								<table class="table_notice" summary="포인트 조회">
									<caption>포인트 조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 110px;" />
										<col style="width: 110px;" />						
										<col style="width: 110px;" />
										<col style="width: 100px;" />
																	
										<col style="width: 110px;" />																										
										<col style="" />
									</colgroup>
									<thead>
										<tr>											
											<th scope="col">No</th>
											<th scope="col">기준일자</th>																
											<th scope="col">발생</th>											
											<th scope="col">사용</th>											
											<th scope="col">잔고</th>
											
											<th scope="col">발생일</th>										
											<th scope="col">발생사유</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="searchlist_wrap">
								<table class="table_notice" summary="포인트 조회">
									<caption>포인트 조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 110px;" />
										<col style="width: 110px;" />						
										<col style="width: 110px;" />
										<col style="width: 100px;" />
																	
										<col style="width: 110px;" />																										
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(pointList) > 0}">
												<c:forEach items="${pointList}" var="row" varStatus="status">
													<tr>													
														<td>${status.count}</td>	
														<td>
															<c:choose>
																<c:when test="${fn:trim(row.dt) == '0'}">${pointVO.searchYear - 1}년 이월</c:when>
																<c:otherwise>${row.dt}</c:otherwise>
															</c:choose>
														</td>									
														<td class="blue_B txt_rig"><fmt:formatNumber value="${row.up}" groupingUsed="true"/></td>																															
														<td class="blue_B txt_rig"><fmt:formatNumber value="${row.down}" groupingUsed="true"/></td>																						
														<td class="blue_B txt_rig"><fmt:formatNumber value="${row.bal}" groupingUsed="true"/></td>
															
														<td>${row.inst_dt}</td>
														<td class="txt_lef">${row.reason}</td>	
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
							
							<div class="bottom_btn_wrap">					
								<div class="right_btn_area">
									<input class="btn_add" type="button" id="" value="사은품 신청" onclick="callGiftForm();" />										
								</div>
							</div>
							<!--right_btn_area-->		
					
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