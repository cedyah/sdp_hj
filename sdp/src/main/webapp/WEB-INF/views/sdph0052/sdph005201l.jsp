<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 샘플 진도 조회  -->
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
	
	//상세정보 화면으로 이동
	function detailForm(obj) {
		//var product_type = $(obj).find("#hid_product_type").val();
		var ilja = $(obj).data("ilja");
		var jeonpyo_no = $(obj).data("jeonpyo_no");

			$("#jeonpyo_no").val(jeonpyo_no);
			$("#ilja").val(ilja);
			c_submit("frm", "sdph005201d.do");
			 
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
	<form id="frm" name="frm">
	    <input type="hidden" id="jeonpyo_no" name="jeonpyo_no" />
        <input type="hidden" id="ilja" name="ilja" />
		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
				<div class="sub_wrap_area">
					<div class="sub_wrap">
						<div class="sub_contents">					
							<div class="local_nav_wrap">
						 		<h3 class="sub_tit">주문관리</h3>		       				
				       				<div class="local_nav">
				                         <ul>
					                           <li class="home">홈</li>
					                           <li>주문관리</li>
					                           <li>샘플결과 조회</li>
				                         </ul>
				                   </div>
				                   <!--local_nav-->		                   
		       				</div>
		       				<!--local_nav_wrap-->
		       				
		         			<div class="sub_cont">
								<div class="gray_wrap">
								
						 
			
									<span>요청일</span>
									<input type="text" class="ico_cal datepicker" id="searchDate_from" name="searchDate_from" value="${sampleRequestItemStatVO.searchDate_from }" /> ~ 
									<input type="text" class="ico_cal datepicker" id="searchDate_to" name="searchDate_to" value="${sampleRequestItemStatVO.searchDate_to }"/>
									<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdph005201l.do');"/>
									<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdph005201l.do');" />
									<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdph005201l.do');" />
									<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdph005201l.do');" />
									<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdph005201l.do');">
																	<!-- 기존 요청일~ 조회 버튼들 아래에 추가 -->
									<br/><br/>
									<span>조회구분</span>
									<label><input type="radio" name="searchGubun" value=""    
									<c:if test="${empty sampleRequestItemStatVO.searchGubun}">checked</c:if> /> 전체</label>
									<label><input type="radio" name="searchGubun" value="INTERNET"    <c:if test="${sampleRequestItemStatVO.searchGubun == 'INTERNET'}">checked</c:if> /> 인터넷</label>
									<label><input type="radio" name="searchGubun" value="NOT_INTERNET"    <c:if test="${sampleRequestItemStatVO.searchGubun == 'NOT_INTERNET'}">checked</c:if> /> 인터넷제외</label>
									
								</div>
	
								<div class="left_num_count">
									샘플진도  : ${fn:length(sampleRequestItemStat)}건
								</div>
								<!-- left_num_count -->
	                    <div class="search_btn_wrap">
							<p class="result_num">샘플결과 : ${fn:length(sampleRequestItemStat)}건</p>
							<div class="search_btn_area">
								<input class="btn_sample" id="" type="button" value="샘플출고 결과등록" onclick="c_submit('frm','sdph005201u.insert.do');">
							</div>
						</div>
								<!-- board_list_wrap (게시물 리스트) -->
								<div class="searchlist_wrap_tit" id="div_title" style="overflow: hidden; width: 100%;">
									<div style="width:1920px;">
										<table summary="결과조회" class="table_common"  style="width:100%;">
											<caption>주문조회</caption>
											<colgroup>
												<col style="width: 100px;" />
												<col style="width: 120px;" />
												<col style="width: 120px;" />
												<col style="width: 110px;" />
												<col style="" />
											</colgroup>
											<thead>
												<tr>
													<th scope="col">NO</th>
													<th scope="col">구분</th>
													<th scope="col">요청일자</th>
													<th scope="col">전표번호</th>
													<th scope="col">결과</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
	
								<div class="searchlist_wrap" style="overflow: scroll;" id="div_list">
									<div style="width:1900px;">
										<table summary="샘플결과조회" class="table_common" style="width:100%;">
											<caption>샘플결과조회</caption>
											<colgroup>
												<col style="width: 100px;" />
												<col style="width: 120px;" />
												<col style="width: 120px;" />
												<col style="width: 110px;" />
												<col style="" /> 
											</colgroup>
											<tbody>
												<c:choose>
													<c:when test="${fn:length(sampleRequestItemStat) > 0 }">
														<c:set var="preCoNum" value="" />
														<c:forEach items="${sampleRequestItemStat}" var="row" varStatus="status">
															<tr onclick="detailForm(this)" style="cursor: pointer;" data-gubun="${row.gubun}" data-ilja="${row.ilja}" data-jeonpyo_no="${row.jeonpyo_no}">
															    <td class="txt_center">${status.count}</td>
																<td class="txt_center">${row.gubun}</td>
																<td class="txt_center">${row.ilja}</td>
																<td class="txt_center">${row.jeonpyo_no}</td>
																<td class="txt_center">${row.gyeolgwa_1}</td>
															</tr>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td class="pro_code txt_center" colspan="12">검색된 샘플정보가  없습니다</td>
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