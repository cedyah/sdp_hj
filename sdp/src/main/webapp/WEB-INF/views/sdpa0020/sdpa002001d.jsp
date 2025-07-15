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
		c_submit("frm", "sdpa002001l.do");
	}
	
	//주문수정 화면으로 이동함
	function alterCo() {
		c_submit("frm", "sdpa002001u.update.do");
	}
	
	//해당주문 취소
	function cancelCo() {
		c_confirm("주문을 취소하시겠습니까?").then(function (result) {		
	        if(result){		//yes Click
				c_submit("frm", "sdpa002001d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
	
	//주문수정, 취소 막음
	function ignoreAlert() {
		c_alert("처리된 주문서는 수정, 취소가 불가합니다");
		//alterCo();
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
	<form id="frm" name="frm">
		<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${co.jeonpyo_no}" />
		<input type="hidden" id="ilja" name="ilja" value="${co.ilja}" />
		
		<input type="hidden" id="searchDate_from" name="searchDate_from" value="${coVO.searchDate_from}" />
		<input type="hidden" id="searchDate_to" name="searchDate_to" value="${coVO.searchDate_to}" />
		<input type="hidden" id="searchCheckBox_01" name="searchCheckBox_01" value="${coVO.searchCheckBox_01}" />
		
		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">
						<div class="local_nav_wrap">
							<h3 class="sub_tit">주문 상세보기</h3>

							<div class="local_nav">
								<ul>
									<li class="home">홈</li>
									<li>주문관리</li>
									<li>주문 상세보기</li>
								</ul>
							</div>
							<!--local_nav-->

						</div>
						<div class="sub_cont">

							<div class="search_btn_wrap" style="">
								<p class="result_num">주문상품 : ${fn:length(coItemList)} 건</p>
								<div class="search_btn_area">
									<input class="btn_newmake" id="" type="button" value="목록" onclick="javascript:goList();">
								</div>
							</div>

							<!-- board_list_wrap (게시물 리스트) -->
							<div class="orderdetail_wrap_tit">
								<table class="table_common" summary="주문 리스트 타이틀">
									<caption>주문서작성</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 100px;" />
										<col style="width: 280px;" />
										<col style="width: 70px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										
										<!-- <col style="width: 80px;" />
										<col style="width: 80px;" /> -->
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">No</th>
											<th scope="col">제품코드</th>
											<th scope="col">품명</th>
											<th scope="col"><!-- 판매<br>전표번호 --></th>
											<th scope="col">판매단위</th>
											
											<th scope="col">주문수량</th>
											<!-- <th scope="col">보관품출고</th>
											<th scope="col">상태</th> -->
											<th scope="col">품목별 비고</th>
										</tr>
									</thead>
								</table>
							</div>

							<div class="orderdetail_wrap">
								<table class="table_common" summary="주문 리스트">
									<caption>주문 리스트</caption>
									<colgroup>
										<col style="width: 40px;" />
										<col style="width: 100px;" />
										<col style="width: 280px;" />
										<col style="width: 70px;" />
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										
										<!--  <col style="width: 80px;" />
										<col style="width: 80px;" />-->
										<col style="" />
									</colgroup>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(coItemList) > 0 }">
												<c:forEach items="${coItemList}" var="row" varStatus="status">
													<tr style="border-bottom: 0px;">
														<td class="txt_center">${row.sunbeon}</td>
														<td class="pro_code">${row.jepum_code}</td>
														<td class="pro_name">${row.pummyeong}</td>
														<td class="txt_center"><!--  ${row.panmae_jeonpyo_no}--></td>
														<td class="txt_rig">${row.panmae_danwi}</td>
														<td class="txt_rig blue_B">${row.panmae_sulyang}</td>
														
														<!--<td class="txt_rig blue_B last">${row.bo_sulyang}</td>
														 <td class="txt_center">${row.stat}</td>-->
														<td class="txt_center">
															<input type="text" class="readonly" id="" name="" value="${row.bigo}" style="width:100%;" readonly/>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr id="tr_empty"><td class="pro_code" colspan="8">주문 품목이 없습니다</td></tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>

							<div class="order_subtit">주문자 정보</div>

							<!-- //searchlist_wrap (게시물 리스트) -->
							<div class="tbl_wrap">
								<table class="tbl_basic" summary="주문서 등록 입력폼">
									<caption>주문서 등록</caption>
									<colgroup>
										<col style="width:15%" />
										<col style="width:45%" />
										<col style="width:15%" />
										<col style="width:45%">
									</colgroup>
									<tbody>
										<tr class="first">
											<th scope="row" alt="">주문일자</th>
											<td class="last" colspan="1">${co.ilja}</td>
											<th scope="row" alt="">전표번호</th>
											<td class="last" colspan="1">${co.jeonpyo_no}</td>
										</tr>
										<tr>
											<th scope="row">배달구분</th>
											<td class="last">
												${co.baedal_gubun_nm}
											</td>
											<th scope="row">배달요청일</th>
											<td class="last">${co.yocheongil}</td>
										</tr>
										<tr>
											<th scope="row">인수자</th>
											<td class="last">${co.insuja}</td>
											<th scope="row">전화번호</th>
											<td class="last">${co.tel_no}</td>
										</tr>
										<tr>
											<th scope="row">배달장소</th>
											<td class="last" colspan="3">${co.baedal_jangso}</td>
										</tr>
										<tr>
											<th scope="row">비고</th>
											<td class="last" colspan="3">
												${fn:replace(co.bigo, newLineChar, "<br>")}
											</td>
										</tr>
									</tbody>
								</table>
							</div>

							<div class="bottom_btn_wrap">
								<div class="right_btn_area">
									<c:choose>
										<c:when test="${co.jindo_code == 'S'}">
											<input class="btn_modify" type="button" id="" value="수  정${co.jindo_code}" onclick="javascript:alterCo();" />
											<input class="btn_ord_cancel" type="button" id="" value="취소" onclick="javascript:cancelCo();" />
										</c:when>
										<c:otherwise>
											
											<input class="btn_modify" type="button" id="" value="수정${co.jindo_code}" onclick="javascript:ignoreAlert();" />
											<input class="btn_ord_cancel" type="button" id="" value="취  소" onclick="javascript:ignoreAlert();" />
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