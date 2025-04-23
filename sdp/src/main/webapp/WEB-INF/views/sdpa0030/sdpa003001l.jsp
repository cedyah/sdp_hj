<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 출하정보조회  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#div_list').bind("scroll", function(){
	        $('#div_title').scrollTop($(this).scrollTop());
	        $('#div_title').scrollLeft($(this).scrollLeft());
	    });
	});
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
						 		<h3 class="sub_tit">출하정보</h3>		       				
				       				<div class="local_nav">
				                         <ul>
					                           <li class="home">홈</li>
					                           <li>주문관리</li>
					                           <li>출하정보조회</li>
				                         </ul>
				                   </div>
				                   <!--local_nav-->		                   
		       				</div>
		       				<!--local_nav_wrap-->
		       				
		         			<div class="sub_cont">
								<div class="gray_wrap">
									<span>매입일</span>
									<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${shipmentInfoVO.searchDate_from }" /> ~ 
									<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${shipmentInfoVO.searchDate_to }"/>
									<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpa003001l.do');"/>
									<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpa003001l.do');" />
									<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpa003001l.do');" />
									<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpa003001l.do');" />
									<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpa003001l.do');">
								</div>
	
								<div class="left_num_count">
									출하정보 : ${fn:length(shipmentInfoList)}건
								</div>
								<!-- left_num_count -->
	
								<!-- board_list_wrap (게시물 리스트) -->
								<div class="searchlist_wrap_tit" id="div_title" style="overflow: hidden; width: 100%;">
									<div style="width:1920px;">
										<table summary="주문조회" class="table_common"  style="width:100%;">
											<caption>주문조회</caption>
											<colgroup>
												<col style="width: 50px;" />
												<col style="width: 100px;" />
												<col style="width: 80px;" />
												<col style="width: 110px;" />
												<col style="width: 300px;" />
												
												<col style="width: 70px;" />
												<col style="width: 60px;" />
												<col style="width: 80px;" />
												<col style="width: 80px;" />
												<col style="width: 350px;" />
												
												<col style="width: 80px;" />
												<col style="width: 80px;" />
												<col style="width: 110px;" />
												<col style="width: 80px;" />
												<col style="width: 80px;" />
												
												<col style="width: 80px;" />
												<col style="" />
											</colgroup>
											<thead>
												<tr>
													<th scope="col">NO</th>
													<th scope="col">판매일자</th>
													<th scope="col">전표번호</th>
													<th scope="col">제품코드</th>
													<th scope="col">품명</th>
													
													<th scope="col">단위</th>
													<th scope="col">수량</th>
													<th scope="col">상태</th>
													<th scope="col">배달구분</th>
													<th scope="col">비고</th>
	
													<th scope="col">차량번호</th>
													<th scope="col">기사성명</th>
													<th scope="col">전화번호</th>
													<th scope="col">주문일자</th>
													<th scope="col">주문번호</th>
													
													<th scope="col">순번</th>
													<th scope="col">도착예정시간</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
	
								<div class="searchlist_wrap" style="overflow: scroll;" id="div_list">
									<div style="width:1900px;">
										<table summary="주문조회" class="table_common" style="width:100%;">
											<caption>주문조회</caption>
											<colgroup>
												<col style="width: 50px;" />
												<col style="width: 100px;" />
												<col style="width: 80px;" />
												<col style="width: 110px;" />
												<col style="width: 300px;" />
												
												<col style="width: 70px;" />
												<col style="width: 60px;" />
												<col style="width: 80px;" />
												<col style="width: 80px;" />
												<col style="width: 350px;" />
												
												<col style="width: 80px;" />
												<col style="width: 80px;" />
												<col style="width: 110px;" />
												<col style="width: 80px;" />
												<col style="width: 80px;" />
												
												<col style="width: 80px;" />
												<col style="" />
											</colgroup>
											<tbody>
												<c:choose>
													<c:when test="${fn:length(shipmentInfoList) > 0 }">
														<c:set var="preCoNum" value="" />
														<c:forEach items="${shipmentInfoList}" var="row" varStatus="status">
															<tr <c:if test="${row.gubun == '2'}" >style="background:#f5f5f5;"</c:if>>
																<td class="txt_center">${status.count}</td>
																<td class="txt_center">${row.panmae_ilja}</td>
																<td class="txt_center">${row.jeonpyo_no}</td>
																<td class="txt_lef">${row.jepum_code}</td>
																<td class="txt_lef">${row.pummyeong}</td>
																<td class="txt_rig">${row.panmae_danwi}</td>
																
																<td class="txt_rig">${row.panmae_sulyang}</td>
																<td class="txt_center">${row.prog_stat}</td>
																<td class="txt_center">${row.dely_type}</td>
																<td class="txt_lef">${row.rmk}</td>
																<td class="txt_center">${row.car_no}</td>
																
																<td class="txt_center">${row.deriver_nm}</td>
																<td class="txt_center">${row.tel_no}</td>
																<td class="txt_center">${row.ord_dt}</td>
																<td class="txt_center">${row.ord_no}</td>
																<td class="txt_center">${row.sunbeon}</td>
																
																<td class="txt_center">${row.arrivale_time}</td>
	<%-- 															<td class="txt_rig"><fmt:formatNumber value="${row.keep_on_hand}" groupingUsed="true"/></td> --%>
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td class="pro_code txt_center" colspan="12">검색된 출하정보가 없습니다</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
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