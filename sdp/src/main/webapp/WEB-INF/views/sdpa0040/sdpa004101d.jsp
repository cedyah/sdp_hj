<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 주문 상세보기 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />

<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {

	});
	
	//목록으로 되돌아감
	function goList() {
		c_submit("frm", "sdpa004001l.do");
	}
	
	//제조의뢰 수정 화면으로 이동함
	function alertNprodReq() {
		c_submit("frm", "sdpa004101u.update.do");
	}
	
	//제조의뢰 취소
	function cancelCo() {
		c_confirm("제조의뢰 요청을 취소하시겠습니까?<br>포함된 모든 품목들이 요청 취소됩니다.").then(function (result) {		
	        if(result){		//yes Click
				c_submit("frm", "sdpa004101d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
	
	//제조의뢰 수정, 삭제 차단
	function ignoreAlert() {
		c_alert("처리된 제조의뢰서는 수정, 삭제가 불가합니다");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
	<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${prVO.jeonpyo_no}" />
	<input type="hidden" id="ilja" name="ilja" value="${prVO.ilja}" />
	
	<input type="hidden" id="searchDiv" name="searchDiv" value="${prVO.searchDiv}" />
	<input type="hidden" id="searchDate_from" name="searchDate_from" value="${prVO.searchDate_from}" />
	<input type="hidden" id="searchDate_to" name="searchDate_to" value="${prVO.searchDate_to}" />
	
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">제조의뢰 상세보기</h3>

						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>주문관리</li>
								<li>제조의뢰 상세보기</li>
							</ul>
						</div>
						<!--local_nav-->

					</div>
					<div class="sub_cont">

						<div class="search_btn_wrap" style="">
							<p class="result_num">제조의뢰품목 : ${fn:length(prodReqSubList)} 건</p>
							<div class="search_btn_area">
								<input class="btn_newmake" id="" type="button" value="목록" onclick="javascript:goList();">
							</div>
						</div>

						<!-- board_list_wrap (게시물 리스트) -->
						<div class="orderdetail_wrap_tit">
							<table class="table_common" summary="주문 리스트 타이틀">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 500px;" />
									<col style="width: 80px;" />
									<col style="width: 80px;" />
									
									<col style="width: 80px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr>
										<th scope="col">No</th>
										<th scope="col">제품코드</th>
										<th scope="col">품명</th>
										<th scope="col">단위</th>
										<th scope="col">요청수량</th>
										
										<th scope="col">상태</th>
										<th scope="col">갱신일</th>
									</tr>
								</thead>
							</table>
						</div>

						<div class="orderdetail_wrap">
							<table class="table_common" summary="주문 리스트">
								<colgroup>
									<col style="width: 40px;" />
									<col style="width: 100px;" />
									<col style="width: 500px;" />
									<col style="width: 80px;" />
									<col style="width: 80px;" />
									
									<col style="width: 80px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(prodReqSubList) > 0 }">
											<c:forEach items="${prodReqSubList}" var="row" varStatus="status">
												<tr style="border-bottom: 0px;">
													<td class="txt_center">${status.count}</td>
													<td class="pro_code">${row.jepum_code}</td>
													<td class="pro_name">${row.pummyeong}</td>
													<td class="txt_rig">${row.pojang_danwi_a} ${row.pojang_danwi_b}</td>
													<td class="txt_rig blue_B last">${row.pojang_sulyang}</td>
													
													<td class="txt_center">${row.wanlyo_yn_nm}</td>
													<td class="txt_center">${row.jindo_date}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td class="pro_code" colspan="8">제조의뢰 품목이 없습니다</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

						<div class="order_subtit">제조의뢰자 정보</div>

						<!-- //searchlist_wrap (게시물 리스트) -->
						<div class="tbl_wrap">
							<table class="tbl_basic" summary="제조의뢰 등록 입력폼">
								<colgroup>
									<col style="width:15%" />
									<col style="width:45%" />
									<col style="width:15%" />
									<col style="width:45%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">의뢰일자</th>
										<td class="last">${prodReqHeader.ilja}</td>
										<th scope="row" alt="">의뢰번호</th>
										<td class="last" >${prodReqHeader.jeonpyo_no}</td>
									</tr>
									<tr>
										<th scope="row" alt="">제조번호</th>
										<td class="last" >${prodReqHeader.jejo_jeonpyo_no}</td>
										<th scope="row">배달구분</th>
										<td class="last">${prodReqHeader.baedal_gubun_nm}</td>
									</tr>
									<tr>
										<th scope="row">완료요청일</th>
										<td class="last">${prodReqHeader.euiloiil}</td>
										<th scope="row">전화번호</th>
										<td class="last">${prodReqHeader.tel_no}</td>
									</tr>
									<tr>
										<th scope="row">인수자</th>
										<td class="last" colspan="3">${prodReqHeader.insuja}</td>
									</tr>
									<tr>
										<th scope="row">배달장소</th>
										<td class="last" colspan="3">
											<c:choose>
												<c:when test="${fn:length(prodReqHeader.zip) > 0}">
													<span class="zipnum">(${prodReqHeader.zip})&nbsp;${prodReqHeader.addr1}&nbsp;${prodReqHeader.addr2}</span>
												</c:when>
												<c:when test="${fn:length(prodReqHeader.zip) == 0 && fn:length(prodReqHeader.addr2) > 0}">
													${prodReqHeader.addr2}
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<th scope="row">비고</th>
										<td class="last" colspan="3">${fn:replace(prodReqHeader.bigo, newLineChar, "<br>")}</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
								<c:choose>
									<c:when test="${prodReqHeader.wanlyo_yn == 'A'}">
										<input class="btn_modify" type="button" id="" value="수정" onclick="javascript:alertNprodReq();" />
										<input class="btn_ord_cancel" type="button" id="" value="제조의뢰 취소" onclick="javascript:cancelCo();" />
									</c:when>
									<c:otherwise>
										<input class="btn_modify" type="button" id="" value="수정" onclick="javascript:ignoreAlert();" />
										<input class="btn_ord_cancel" type="button" id="" value="제조의뢰 취소" onclick="javascript:ignoreAlert();" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<!--bottom_btn_wrap-->
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