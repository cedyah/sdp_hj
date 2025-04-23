<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 신규제조의뢰서 상세  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	//목록으로 되돌아감
	function goList() {
		c_submit("frm", "sdpa004001l.do");
	}
	
	//제조의뢰 화면으로 이동함
	function alterRequest() {
		c_submit("frm", "sdpa004001u.update.do");
	}
	
	//제조의뢰 취소
	function cancelRequest() {
		c_confirm("신규제조 요청을 취소하시겠습니까?<br>포함된 모든 품목들이 요청 취소됩니다.").then(function (result) {		
	        if(result){		//yes Click
				c_submit("frm", "sdpa004001d_delete.do");
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
<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${nprVO.jeonpyo_no}" />
<input type="hidden" id="ilja" name="ilja" value="${nprVO.ilja}" />

<input type="hidden" id="searchDiv" name="searchDiv" value="${nprVO.searchDiv}" />
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${nprVO.searchDate_from}" />
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${nprVO.searchDate_to}" />

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
			<div class="sub_wrap_area">
				<div class="sub_wrap">
					<div class="sub_contents">					
						<div class="local_nav_wrap">
					 		<h3 class="sub_tit">신규제조의뢰서</h3>
			       				<div class="local_nav">
			                         <ul>
				                           <li class="home">홈</li>
				                           <li>주문관리</li>
				                           <li>신규제조의뢰서</li>
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
								<table class="table_common" summary="신규제조의뢰서">
									<caption>신규제조의뢰서</caption>
									<colgroup>
										<col style="width: 450px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 120px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col">품명</th>																
											<th scope="col">판매단위</th>		
											<th scope="col">주문수량</th>
											<th scope="col">견본일자</th>
											<th scope="col">견본번호</th>
											<th scope="col">견본사업장</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="orderdetail_wrap">
								<table class="table_common" summary="주문 리스트">
									<caption>주문 리스트</caption>
									<colgroup>
										<col style="width: 450px;" />
										<col style="width: 100px;" />
										<col style="width: 100px;" />
										<col style="width: 120px;" />
										<col style="width: 100px;" />
										<col style="" />
									</colgroup>
									<tbody>
										<tr>													
											<td class="pro_name">${nprodReqSub.pummyeong}</td>	
											<td class="txt_rig blue_B">${nprodReqSub.pojang_danwi_a} ${nprodReqSub.pojang_danwi_b}</td>									
											<td class="txt_rig blue_B">${nprodReqSub.pojang_sulyang}</td>																															
											<td class="txt_center">${nprodReqSub.gyeon_ilja}</td>
											<td class="txt_center">${nprodReqSub.gyeon_jeonpyo_no}</td>
											<td class="txt_center">${nprodReqSub.gyeon_saeobjang_nm}</td>
										</tr>																												
									</tbody>
								</table>	
							</div>
									
							<div class="input_subtit">주문자 정보</div>
							<div class="tbl_wrap">				
								<table class="tbl_input" summary="신규제조의뢰서 등록 입력폼">
									<caption>신규제조의뢰서 등록</caption>
									<colgroup>
										<col style="width:15%" />
										<col style="width:45%" />
										<col style="width:15%" />
										<col style="width:45%">
									</colgroup>
									<tbody>
										<tr class="first">
											<th scope="row">의뢰일자</th>
											<td class="last">${nprodReqHeader.ilja}</td>
											<th scope="row">전표번호</th>
											<td class="last">${nprodReqHeader.jeonpyo_no}</td>
										</tr>
										<tr>
											<th scope="row">완료요청일</th>
											<td class="last" colspan="1">${nprodReqHeader.euiloiil}</td>
											<th scope="row">배달구분</th>
											<td class="last" colspan="1">${nprodReqHeader.baedal_gubun_nm}</td>
										</tr>
										<tr>
											<th scope="row">인수자</th>
											<td class="last">${nprodReqHeader.insuja}</td>
											<th scope="row">전화번호</th>
											<td class="last">${nprodReqHeader.tel_no}</td>
										</tr>
										<tr>
											<th scope="row">배달장소</th>
											<td class="last" colspan="3">
												<c:if test="${fn:length(nprodReqHeader.zip) > 0}">
													<span class="zipnum">(${nprodReqHeader.zip})&nbsp;</span>
												</c:if>
												<c:if test="${fn:length(nprodReqHeader.addr1) > 0}">
													${nprodReqHeader.addr1}&nbsp;
												</c:if>
												<c:if test="${fn:length(nprodReqHeader.addr2) > 0}">
													${nprodReqHeader.addr2}
												</c:if>
												
											</td>
										</tr>
										<tr>
											<th scope="row">비고</th>
											<td class="last" colspan="3">${nprodReqHeader.bigo}</td>
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