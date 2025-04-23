<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 문자수신 조회  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		
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
						 		<h3 class="sub_tit">문자수신조회</h3>		       				
				       				<div class="local_nav">
				                         <ul>
					                           <li class="home">홈</li>
					                           <li>마이페이지</li>
					                           <li>문자수신조회</li>
				                         </ul>
				                   </div>
				                   <!--local_nav-->		                   
		       				</div>
		       				<!--local_nav_wrap-->
		       				
		         			<div class="sub_cont">
								<div class="gray_wrap">
									<span>기간</span>
									<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${SmsreceiveVO.searchDate_from }" /> ~ 
									<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${SmsreceiveVO.searchDate_to }"/>
									<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdpf006001l.do');"/>
									<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdpf006001l.do');" />
									<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdpf006001l.do');" />
									<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdpf006001l.do');" />
									<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdpf006001l.do');">
								</div>
	
								<div class="left_num_count">
									수신문자 : ${fn:length(SmsreceiveList)}건
								</div>
								<!-- left_num_count -->
	
								<!-- board_list_wrap (게시물 리스트) -->
								<div class="searchlist_wrap_tit">
									<table summary="문자수신조회" class="table_common">
										<caption>문자수신조회</caption>
										<colgroup>
											<col style="width: 40px;" /><!-- NO -->
											<col style="width: 180px;" /><!-- 수신일시 -->
											<col style="width: 470px;" /><!-- 수신내용 -->
											<col style="width: 105px;" /><!-- 수신번호 -->
											<col style="width: 80px;" /><!-- 수신자 -->
											<col style="width: 105px;" /><!-- 회신번호 -->
											<col style="" /><!-- 회신자 -->
<!-- 											<col style="width: 50px;" />전송상태 -->
<!-- 											<col style="" />전송결과 -->
										</colgroup>
										<thead>
											<tr>
												<th scope="col">NO</th>
												<th scope="col">수신일시</th>
												<th scope="col">수신내용</th>
												<th scope="col">수신번호</th>
												<th scope="col">수신자</th>
												<th scope="col">회신번호</th>
												<th scope="col">회신자</th>
<!-- 												<th scope="col">전송상태</th> -->
<!-- 												<th scope="col">전송결과</th> -->
											</tr>
										</thead>
									</table>
								</div>
	
								<div class="searchlist_wrap">
									<table summary="문자수신조회" class="table_common">
										<caption>문자수신조회</caption>
										<colgroup>
											<col style="width: 40px;" /><!-- NO -->
											<col style="width: 180px;" /><!-- 수신일시 -->
											<col style="width: 470px;" /><!-- 수신내용 -->
											<col style="width: 105px;" /><!-- 수신번호 -->
											<col style="width: 80px;" /><!-- 수신자 -->
											<col style="width: 105px;" /><!-- 회신번호 -->
											<col style="" /><!-- 회신자 -->
<!-- 											<col style="width: 50px;" />전송상태 -->
<!-- 											<col style="" />전송결과 -->
										</colgroup>
										<tbody>
											<c:choose>
												<c:when test="${fn:length(SmsreceiveList) > 0 }">
													<c:set var="no" value="${fn:length(SmsreceiveList)}" />
													<c:forEach items="${SmsreceiveList}" var="row" varStatus="status">
														<tr>
	<%-- 														<td>${status.getCount()}</td> --%>
															<td class="txt_center">${no}</td>
															<td class="txt_center">${row.receive_time }</td>
															<td class="">${row.receive_contents }</td>
															<td class="">${row.phone }</td>
															<td class="txt_center">${row.receiver }</td>
															<td class="">${row.reply_phone}</td>
															<td class="txt_center">${row.reply_man}</td>
<%-- 															<td class="">${row.sms_state}</td> --%>
<%-- 															<td class="">${row.sms_result}</td> --%>
														</tr>
														<c:set var="no" value="${no - 1}" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td class="pro_code" colspan="5">문자수신내역이 없습니다</td>
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