<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 샘플요청 상세  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	//목록으로 되돌아감
	function goList() {
		c_submit("frm", "sdph005001l.do");
	}
	
	//제조의뢰 화면으로 이동함
	function alterRequest() {
		c_submit("frm", "sdph005001u.update.do");
	}
	
	//제조의뢰 취소
	function cancelRequest() {
		c_confirm("샘플 요청을 취소하시겠습니까?<br>포함된 모든 품목들이 요청 취소됩니다.").then(function (result) {		
	        if(result){		//yes Click
				c_submit("frm", "sdph005001d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
	
	//제조의뢰 수정, 삭제 차단
	function ignoreAlert() {
		c_alert("처리된 샘플요청서는 수정, 삭제가 불가합니다");
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${sampleRequestVO.jeonpyo_no}" />
<input type="hidden" id="ilja" name="ilja" value="${sampleRequestVO.ilja}" />

<!-- <input type="hidden" id="searchDiv" name="searchDiv" value="${nprVO.searchDiv}" />
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${nprVO.searchDate_from}" />
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${nprVO.searchDate_to}" /> -->

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">					
						<div class="local_nav_wrap">
					 		<h3 class="sub_tit">샘플의뢰서등록</h3>
			       				<div class="local_nav">
			                         <ul>
				                           <li class="home">홈</li>
				                           <li>주문관리</li>
				                           <li>샘플의뢰서등록</li>
			                         </ul>
			                   </div>
			                   <!--local_nav-->		                   
	       				</div>
	       				<!--local_nav_wrap-->	
	         			<div class="sub_cont">		            
							<div class="search_btn_wrap" style="">
<%-- 								<p class="result_num">제조의뢰품목 : ${fn:length(coItemList)} 건</p> --%>
								<div class="search_btn_area">
									<input class="btn_newmake" id="" type="button" value="목록" onclick="javascript:goList();">
								</div>
							</div>
							
							<div class="orderlist_wrap_tit">
								<table class="table_common" summary="샘플의뢰서등록">
									<caption>샘플의뢰서등록</caption>
									<colgroup>
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 250px;" />
										<col style="width: 100px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>																
											<th scope="col">견본구분</th>		
											<th scope="col">품목코드</th>
											<th scope="col">품명</th>
											<th scope="col">포장단위</th>
											<th scope="col">포장수량</th>
											<th scope="col">유무상</th>
											<th scope="col">도편</th>
											<th scope="col">진도상황</th>
											<th scope="col">적용모델1</th>
											<th scope="col">적용모델2</th>
											<th scope="col">적용모델3</th>
											<th scope="col">적용모델4</th>
											<th scope="col">적용모델5</th>
											<th scope="col">적용모델6</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="orderdetail_wrap">
								<table class="table_common" summary="주문 리스트">
									<caption>주문 리스트</caption>
									<colgroup>
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 100px;" />
										<col style="width: 250px;" />
										<col style="width: 100px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="width: 50px;" />
										<col style="" />
									</colgroup>
									<tbody>
									    <c:forEach var="sampleRequestItem" items="${sampleRequestItemList}" varStatus="status">
										<tr>													
											<td class="txt_center">${sampleRequestItem.sunbeon}</td>
											<td class="txt_center">${sampleRequestItem.gyeonbon_gubun}</td>
											<td class="txt_center">${sampleRequestItem.pummog_code}</td>
											<td class="pro_name">${sampleRequestItem.pummyeong}</td>	
											<td class="txt_rig blue_B">${sampleRequestItem.po_danwi_a} ${sampleRequestItem.po_danwi_b}</td>									
											<td class="txt_rig blue_B">${sampleRequestItem.po_su}</td>
											<td class="txt_center">${sampleRequestItem.price_yn}</td>
											<td class="txt_center">${sampleRequestItem.dopyeon_yn}</td>
											<td class="txt_center">${sampleRequestItem.stat_nm}</td>	
											<td class="txt_center">${sampleRequestItem.model_1}</td>	
											<td class="txt_center">${sampleRequestItem.model_2}</td>	
											<td class="txt_center">${sampleRequestItem.model_3}</td>	
											<td class="txt_center">${sampleRequestItem.model_4}</td>	
											<td class="txt_center">${sampleRequestItem.model_5}</td>	
											<td class="txt_center">${sampleRequestItem.model_6}</td>	
											                       																			
										</tr>	
     								 </c:forEach>																											
									</tbody>
								</table>	
							</div>
									
							<div class="input_subtitle">주문자 정보</div>
							<div class="tbl_wrap">				
								<table class="tbl_input" summary="샘플의뢰서 등록 입력폼">
									<caption>샘플의뢰서 등록</caption>
									<colgroup>
										<col style="width:15%" />
										<col style="width:25%" />
										<col style="width:15%" />
										<col style="width:45%">
									</colgroup>
									<tbody>
										<tr class="first">
											<th scope="row">사업장명</th>
											<td class="last">${sampleRequest.saeobjang_nm}</td>
											<th scope="row">일자</th>
											<td class="last">${sampleRequest.ilja}</td>
										</tr>
										<tr>
											<th scope="row">전표번호</th>
											<td class="last" colspan="1">${sampleRequest.jeonpyo_no}</td>
											<th scope="row">품목분류</th>
											<td class="last" colspan="1">${sampleRequest.pummog_bunryu}</td>
										</tr>
										<tr>
											<th scope="row">핸드폰분류</th>
											<td class="last">${sampleRequest.hp_bunryu}</td>
										<!-- 	<th scope="row">거래처코드</th>
											<td class="last">${sampleRequest.geolaecheo_code}</td> -->
										</tr>
		
										<tr>
											<th scope="row">1차거래처코드</th>
											<td class="last">${sampleRequest.geolaecheo_code}</td>
										 	<th scope="row">1차거래처상호</th>
											<td class="last">${sampleRequest.sangho}</td>
										</tr>
										<tr>
											<th scope="row">2차거래처코드</th>
											<td class="last">${sampleRequest.geolaecheo_code_2}</td>
										 	<th scope="row">2차거래처상호</th>
											<td class="last">${sampleRequest.sangho_2}</td>
										</tr>
										<tr>
											<th scope="row">거래처실무자</th>
											<td class="last">${sampleRequest.gogaeg_myeong}</td>
										 	<th scope="row">영업담당자</th>
											<td class="last">${sampleRequest.balsinja}</td>
										</tr>
										<tr>
											<th scope="row">수신부서</th>
											<td class="last">${sampleRequest.susin_buseo}</td>
										 	<th scope="row">수신자</th>
											<td class="last">${sampleRequest.susinja}</td>
										</tr>
										<tr>
											<th scope="row">입회자</th>
											<td class="last">${sampleRequest.ibhoija}</td>
										 	<th scope="row">납품일자</th>
											<td class="last">${sampleRequest.nabpum_ilja}</td>
										</tr>
										<tr>
											<th scope="row">예상판매금액(만원)</th>
											<td class="last">${sampleRequest.yesang_geumaeg}</td>
										 	<th scope="row">도료사용금액(만원)</th>
											<td class="last">${sampleRequest.sayong_geumaeg}</td>
										</tr>
										<tr>
											<th scope="row">희망가격(원)</th>
											<td class="last">${sampleRequest.himang_gagyeog}</td>
										 	<th scope="row">경쟁회사</th>
											<td class="last">${sampleRequest.ex_geolaecheo}</td>
										</tr>
										<tr>
											<th scope="row">타사견본유무</th>
											<td class="last">${sampleRequest.ex_gyeonbon_yn}</td>
										 	<th scope="row">도장SYSTEM</th>
											<td class="last">${sampleRequest.dojang_bangbeob}</td>
										</tr>
										
										<tr>
											<th scope="row">현장도장공정</th>
											<td class="last">${sampleRequest.dojang_gongjeong}</td>
										 	<th scope="row">건조조건</th>
											<td class="last">${sampleRequest.geonjo_bangbeob}</td>
										</tr>
										<tr>
											<th scope="row">도료TYPE</th>
											<td class="last">${sampleRequest.doryo_type}</td>
										 	<th scope="row">소재의 종류</th>
											<td class="last">${sampleRequest.sojae_jonglyu}</td>
										</tr>
										<tr>
											<th scope="row">기타요구사항(도료)</th>
											<td class="last">${sampleRequest.gita_yogu6}</td>
										 	<th scope="row">기타요구사항(기술자 출장3</th>
											<td class="last">${sampleRequest.gita_yogu3}</td>
										</tr>
										<tr>
											<th scope="row">비고1</th>
											<td class="last">${sampleRequest.bigo_1}</td>
										 	<th scope="row">비고2</th>
											<td class="last">${sampleRequest.bigo_2}</td>
										</tr>
										
										<tr>
											<th scope="row">비고3</th>
											<td class="last">${sampleRequest.bigo_3}</td>
										 	<th scope="row">결과등록기한</th>
											<td class="last">${sampleRequest.gyeolgwa_gihan}</td>
										</tr>
										 		
									</tbody>
								</table>					
							</div>	
							
							<div class="bottom_btn_wrap">
								<div class="right_btn_area">
									<c:choose>
										<c:when test="${nprodReqSub.jindo == 'S'}">
											<input class="btn_modify" type="button" id="" value="수정" onclick="alterRequest();" />
											<input class="btn_ord_cancel" type="button" id="" value="요청취소" onclick="cancelRequest();" />
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