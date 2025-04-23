<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
		//쿠키를 체크해서 공지사항이 있을경우 팝업 띄움
		if(!cookieCheck("1")) {
			showNoticeView_1();
		}

		if(!cookieCheck("2")) {
// 			showNoticeView_2();
		}
	});

	//공지사항 상세페이지로 이동
	function moveNotice(notice_num) {
		$("#notice_num").val(notice_num);
		c_submit("frm", "sdpy001001d.do");
	}
	
	//팝업 공지사항 1
	function showNoticeView_1() {
		var popWidth = "520";
		var popHeight = "595";
		var winHeight = document.body.clientHeight; // 현재창의 높이
		var winWidth = document.body.clientWidth; // 현재창의 너비
		var winX = window.screenX || window.screenLeft || 0; // 현재창의 x좌표
		var winY = window.screenY || window.screenTop || 0; // 현재창의 y좌표

		var popX = (winX + (winWidth - popWidth)/2) - 270;
		var popY = winY + (winHeight - popHeight)/2;
		
		var pop1 = window.open("noticeMain_1.do" ,"popup_notice1", "width="+popWidth+", height="+popHeight+", top="+popY+", left="+popX+", toolbar=no, menubar=no, scrollbars=yes, resizable=no");
		pop1.focus();
	}
	
	//팝업 공지사항 2
	function showNoticeView_2() {
		var popWidth = "520";
		var popHeight = "595";
		var winHeight = document.body.clientHeight; // 현재창의 높이
		var winWidth = document.body.clientWidth; // 현재창의 너비
		var winX = window.screenX || window.screenLeft || 0; // 현재창의 x좌표
		var winY = window.screenY || window.screenTop || 0; // 현재창의 y좌표

		var popX = (winX + (winWidth - popWidth)/2) + 270;
		var popY = winY + (winHeight - popHeight)/2;
		
		var pop2 = window.open("noticeMain_2.do" ,"popup_notice2", "width="+popWidth+", height="+popHeight+", top="+popY+", left="+popX+", toolbar=no, menubar=no, scrollbars=yes, resizable=no");
		pop2.focus();
	}
	
	//저장된 쿠키가 있는지 검사
	function cookieCheck(type) {
		// userid 쿠키에서 id 값을 가져온다.
		var cook = document.cookie + ";";
		var idx = "";
		
		if(type == '1') {
			idx = cook.indexOf("main_noticeCheck1", 0)
		} else if(type == '2') {
			idx = cook.indexOf("main_noticeCheck2", 0)
		}
		
		var val = "";

		if (idx != -1) {
			cook = cook.substring(idx, cook.length);
			begin = cook.indexOf("=", 0) + 1;
			end = cook.indexOf(";", begin);
			val = unescape(cook.substring(begin, end));
		}

		// 가져온 쿠키값이 있으면
		if (val != "") {
			return true;
		} else {
			return false;
		}
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
	<input type="hidden" id="notice_num" name="notice_num" value="">
	<div id="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="main_wrap_area">
			<div class="main_myinfo_area">
				<div class="main_info hover">
					<a href="#">${sessionScope.user['cust_nm']}<img src="img/ico_setting.png" onclick="javascript:c_submit('frm', 'sdpf003002l.do'); return;"/></a>
					<div class="appear">
						<ul>
							<!-- <li><span class="tit">마일리지 포인트</span> <fmt:formatNumber value="${customerVO.cust_point}" groupingUsed="true"/><span class="won">P</span></li>
							<li><span class="tit">탁송 포인트</span> <fmt:formatNumber value="${customerVO.cust_consignment_point}" groupingUsed="true"/><span class="won">P</span></li>
							 -->
						</ul>
					</div>
				</div>
			</div>

			<div class="main_wrap">
				<div class="main_contents">

					<div class="main_top_area">
						<div class="newproduct_area">
							<h3>한진화학 신제품</h3>
							<c:choose>
								<c:when test="${newProduct_file != null}">
									<span>
									<img src="/file/<c:out value='${newProduct_file.file_nm}'/>" style="width: 160px; height: 185px;"/>
								</span>
								</c:when>
								<c:otherwise>
									<span>
									<img src="img/new_products.gif" style="width: 160px; height: 185px;"/>
								</span>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newProduct != null }">
									<p class="subtit" style="height:auto; max-height: 48px;">
										<c:choose>
											<c:when test="${fn:length(newProduct.title) > 32}">
												${fn:substring(newProduct.title, 0, 32)}..
											</c:when>
											<c:otherwise>
												${newProduct.title}
											</c:otherwise>
										</c:choose>
										
									</p>
									<p style="height:auto; max-height:83px; overflow:hidden;">${fn:replace(newProduct.contents, newLineChar, "<br>")}</p>
								</c:when>
								<c:otherwise>
									<p class="subtit" style="height:48px;"></p>
									<p style="height:83px; overflow:hidden;">등록된 신제품 소식이 없습니다</p>
								</c:otherwise>
							</c:choose>
						</div>
                        <!-- 
						<div class="orderbtn_area">
							<a href="sdpa002001u.insert.do">주문하기 btn</a>
						</div>
						<div class="shipment_area">
							<a href="sdpa003001l.do">출하조회 btn</a>
						</div>
                        -->
						<div class="main_search_area">
							<h3>재고조회</h3>
							<div class="mainsel">
								<select style="" id="select_searchDiv" name="searchDiv">
									<option value="description">제품명</option>
									<option value="item_code">제품코드</option>
								</select>
							</div>
							<input class="txt" id="input_searchText" name="searchText" type="text" value="" placeholder="예) 무광 백색"
								onkeydown="if(event.keyCode==13){c_submit('frm', 'sdpa001001l.standard.do'); return false;}">
							<input type="button" class="btn_main_search" onclick="c_submit('frm', 'sdpa001001l.standard.do');" />
						</div>

					</div>

					<div class="orderflowArea">
						<a href="#">
							<h2>주문처리현황</h2>
						</a>
<!-- 						<div class="overflow_icon"> -->
<!-- 					    	<a href="#" class="step1">접수</a> -->
<!-- 					    	<a href="#" class="step2">제품준비</a> -->
<!-- 					    	<a href="#" class="step3">출고요청</a> -->
<!-- 					    	<a href="#" class="step4">출고준비</a> -->
<!-- 					    	<a href="#" class="step5">출고완료</a> -->
<!-- 				    	</div> -->
						<div class="orderflow">
							<table class="table_common" summary="메인 주문처리현황 요약표입니다.">
								<caption>주문조회</caption>
								<colgroup>
									<col style="width: 55px;" />
									<col style="width: 100px;" />
									<col style="width: 100px;" />
									<col style="width: 400px;" />
									<col style="width: 80px;" />

									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th rowspan="2">NO</th>
										<th rowspan="2">주문일</th>
										<th rowspan="2">주문번호</th>
										<th rowspan="2">품명</th>
										<th rowspan="2">수량</th>

										<th colspan="5">출하상태</th>
									</tr>
									<tr>
										<th class="flow_box1">대리점주문</th>
										<th class="flow_box2">영업부접수</th>
										<th class="flow_box3">배차계획</th>
										<th class="flow_box4">상차중</th>
										<th class="flow_box5">출고완료</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(shipmentInfoList) > 0 }">
											<c:forEach items="${shipmentInfoList}" var="row" varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${row.ord_dt}</td>
													<td class="number">${row.ord_no}</td>
													<td class="pro_code txt_lef">${row.pummyeong}</td>
													<td>${row.panmae_sulyang}</td>
													<td colspan="5" class="flow_bg2">
														<c:choose>
															<c:when test="${row.prog_stat_code == 'S'}">
																<span class="step1"></span>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when test="${row.prog_stat_code == 'T'}">
																<span class="step2"></span>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when test="${row.prog_stat_code == 'B'}">
																<span class="step3"></span>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when test="${row.prog_stat_code == 'C'}">
																<span class="step4"></span>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when test="${row.prog_stat_code == 'E'}">
																<span class="step5"></span>
															</c:when>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr id="tr_empty"><td class="pro_code txt_center" colspan="10">검색된 자료가 없습니다</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<!--orderflow-->
					</div>
					<!--orderflowArea-->

					<div class="boardArea">
						<div class="noticeArea">
							<h2>공지사항</h2>
							<a href="sdpy001001l.do" class="more"><img src="img/notice_blit.png"></a>
							<ul>
								<c:choose>
									<c:when test="${noticeList != null and fn:length(noticeList) > 0}">
										<c:forEach items="${noticeList}" var="row" varStatus="status">
											<li>
												<a href="javascript:moveNotice('${row.notice_num}');">
													<c:choose>
														<c:when test="${fn:length(row.title) > 30}">${fn:substring(row.title,0 ,30)}...</c:when>
														<c:otherwise>${row.title}</c:otherwise>
													</c:choose>
												</a>
												<span>${row.createDate}</span>
											</li>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<li><a href="#">등록된 공지사항이 없습니다</a><span></span></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
						<!--noticeArea-->

						<div class="communityArea">
							<c:choose>
								<c:when test="${franchiseNews_file != null}">
									<img src="/file/<c:out value='${franchiseNews_file.file_nm}'/>" style="width:210px; height:180px;" />
								</c:when>
								<c:otherwise>
									<img src="img/main_community.gif" style="width:210px; height:180px;" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${franchiseNews != null }">
									<a onclick="javascript:fncNewWindow('${franchiseNews.link}');" style="cursor:pointer;">
										<h2>대리점 소식</h2>
										<p class="new_tit">${franchiseNews.title}</p>
										<p style="height:95px; overflow: hidden;">${fn:replace(franchiseNews.contents, newLineChar, "<br>")}</p>
									</a>
								</c:when>
								<c:otherwise>
									<a href="">
										<h2>대리점 소식</h2>
										<p class="new_tit"></p>
										<p style="height:95px; overflow: hidden;">등록된 대리점 소식이 없습니다.</p>
									</a>
								</c:otherwise>
							</c:choose>
						</div>
						<!--newArea-->
					</div>
					<!--boardArea-->

				</div>
			</div>

		</div>

		<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />

	</div>
	<!--wrap end-->
</form>
</body>

</html>