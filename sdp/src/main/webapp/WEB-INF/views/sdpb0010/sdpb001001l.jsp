<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 화학물질 판매대장 목록  -->
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
					 		<h3 class="sub_tit">화학물질 판매대장</h3>		       				
			       				<div class="local_nav">
			                         <ul>
				                           <li class="home">홈</li>
				                           <li>취급제한물질관리</li>
				                           <li>화학물질 판매대장</li>
			                         </ul>
			                   </div>
			                   <!--local_nav-->		                   
	       				</div>
	       				<!--local_nav_wrap-->
	       				
	         			<div class="sub_cont">
							<div class="gray_wrap">
								<span>기간</span>
								<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${sLedgerVO.searchDate_from}" /> ~
								<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${sLedgerVO.searchDate_to}" />
								<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpb001001l.do');" />
								<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpb001001l.do');" />
								<input type="button" value="올해" class="gray_inbtn" onclick="javascript:setDate('now_year', 'sdpb001001l.do');" />
								<input type="button" value="작년" class="gray_inbtn" onclick="javascript:setDate('last_year1', 'sdpb001001l.do');" />
								<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpb001001l.do');">
							</div>

							<div class="left_num_count">
								출력자료 : ${fn:length(sLedgerList)}건
							</div>
							<!-- left_num_count -->

							<!-- board_list_wrap (게시물 리스트) -->
							<div class="searchlist_wrap_tit">
								<table summary="주문조회" class="table_common">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 330px;" />
										<col style="width: 280px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />

										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">NO</th>
											<th scope="col">제품명</th>
											<th scope="col">물질명</th>
											<th scope="col">함유량<br>(%)</th>
											<th scope="col">취급량<br>(톤/년)</th>
											
											<th scope="col">주요용도</th>
											<th scope="col">고유번호<br>(CAS_NO)</th>
											<th scope="col">인쇄</th>
										</tr>
									</thead>
								</table>
							</div>

							<div class="searchlist_wrap">
								<table summary="주문조회" class="table_common">
									<caption>주문조회</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 330px;" />
										<col style="width: 280px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />

										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(sLedgerList) > 0 }">
												<c:forEach items="${sLedgerList}" var="row" varStatus="status">
													<c:choose>
														<c:when test="${row.pummyeong == '합계'}">
															<tr style="background-color: #EDEDED;">
																<td class="txt_rig" colspan="4">${row.pummyeong}</td>
																<td class="txt_rig">
																	<c:choose>
																		<c:when test="${fn:substring(row.hwansan_su, 0, 1) == '.'}">0${row.hwansan_su}</c:when>
																		<c:otherwise>${row.hwansan_su}</c:otherwise>
																	</c:choose>
																</td>
																
																<td class="txt_center" colspan="3"></td>
															</tr>
														</c:when>
														<c:otherwise>
															<tr>
																<td class="txt_center">${status.getCount()}</td>
																<td class="txt_lef" >${row.pummyeong}</td>
																<td class="txt_lef">${row.cas_myeong}</td>
																<td class="txt_rig">
																	<c:choose>
																		<c:when test="${fn:substring(row.baehab_10, 0, 1) == '.'}">0${row.baehab_10}</c:when>
																		<c:otherwise>${row.baehab_10}</c:otherwise>
																	</c:choose>
																</td>
																<td class="txt_rig">
																	<c:choose>
																		<c:when test="${fn:substring(row.hwansan_su, 0, 1) == '.'}">0${row.hwansan_su}</c:when>
																		<c:otherwise>${row.hwansan_su}</c:otherwise>
																	</c:choose>
																</td>
																
																<td class="txt_center">${row.yongdo }</td>
																<td class="txt_center">${row.cas_no}</td>
																<td class="txt_center">
																	<input type="button" class="btn_add" id="" name="" value="출력" onclick="print();"/>
																</td>
															</tr>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td class="pro_code txt_center" colspan="8">검색된 자료가 없습니다</td></tr>
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