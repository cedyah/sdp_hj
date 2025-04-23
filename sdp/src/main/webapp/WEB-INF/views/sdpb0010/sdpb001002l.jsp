<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 화학물질 관리인쇄(년도별) 목록  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function () {
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
		c_submit("frm", "sdpb001002l.do");
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
						 		<h3 class="sub_tit">화학물질 관리대장</h3>		       				
				       				<div class="local_nav">
				                         <ul>
					                           <li class="home">홈</li>
					                           <li>취급제한물질관리</li>
					                           <li>화학물질 관리대장</li>
				                         </ul>
				                   </div>
				                   <!--local_nav-->		                   
		       				</div>
		       				<!--local_nav_wrap-->
		       					
		         			<div class="sub_cont">
								<div class="gray_wrap">
									<span>연도</span>
									<select id="searchYear" name="searchYear" style="margin-right:20px;">
										<c:set var="setYear" value="${mLedgerVO.searchYear - 5}" />
										<fmt:formatDate var="nowYear" value="${mLedgerVO.searchCurrent_date}" pattern="yyyy" />
										<c:forEach begin="0" end="10">
											<c:if test="${setYear <= nowYear}" >
												<option value="${setYear}" <c:if test="${mLedgerVO.searchYear == setYear}">selected</c:if>>${setYear}</option>
												<c:set var="setYear" value="${setYear + 1}" />
											</c:if>
										</c:forEach>
									</select>
									<input type="button" value="현재" class="gray_inbtn" onclick="javascript:changeYear('this');" />
									<input type="button" value="전년도" class="gray_inbtn" onclick="javascript:changeYear('last');" />
								</div>
	
								<div class="left_num_count">
									출력자료 : ${fn:length(mLedgerList)}건
								</div>
								<!-- left_num_count -->
	
								<!-- board_list_wrap (게시물 리스트) -->
								<div class="searchlist_wrap_tit">
									<table summary="주문조회" class="table_common">
										<caption>주문조회</caption>
										<colgroup>
											<col style="width: 40px;" />
											<col style="width: 300px;" />
											<col style="width: 250px;" />
											<col style="width: 70px;" />
											<col style="width: 70px;" />
	
											<col style="width: 80px;" />
											<col style="width: 90px;" />
											<col style="width: 50px;" />
											<col style="width: 50px;" />
											<col style="" />
										</colgroup>
										<thead>
											<tr>
												<th scope="col" rowspan="2">NO</th>
												<th scope="col" rowspan="2">제품명</th>
												<th scope="col" rowspan="2">물질명</th>
												<th scope="col" rowspan="2">함유량<br>(%)</th>
												<th scope="col" rowspan="2">취급량<br>(톤/년)</th>
												
												<th scope="col" rowspan="2">주요용도</th>
												<th scope="col" rowspan="2">고유번호<br>(CAS_NO)</th>
												<th scope="col" rowspan="1" colspan="3">상온·상압에서의 상태</th>
											</tr>
											<tr>
												<th class="tbl_row_tit" style="border-right:1px solid #606c79;">고체</th>
												<th class="tbl_row_tit" style="border-right:1px solid #606c79;">액체</th>
												<th class="tbl_row_tit" style="border-right:1px solid #606c79;">기체</th>
											</tr>
										</thead>
									</table>
								</div>
	
								<div class="searchlist_wrap">
									<table summary="주문조회" class="table_common">
										<caption>주문조회</caption>
										<colgroup>
											<col style="width: 40px;" />
											<col style="width: 300px;" />
											<col style="width: 250px;" />
											<col style="width: 70px;" />
											<col style="width: 70px;" />
	
											<col style="width: 80px;" />
											<col style="width: 90px;" />
											<col style="width: 50px;" />
											<col style="width: 50px;" />
											<col style="" />
										</colgroup>
										<tbody>
											<c:choose>
												<c:when test="${fn:length(mLedgerList) > 0 }">
													<c:forEach items="${mLedgerList}" var="row" varStatus="status">
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
																	
																	<td class="txt_center" colspan="5"></td>
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
																	<td class="txt_center">${row.goche }</td>
																	<td class="txt_center">${row.aegche }</td>
																	<td class="txt_center last">${row.giche }</td>
																</tr>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr><td class="pro_code txt_center" colspan="10">검색된 자료가 없습니다</td></tr>
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