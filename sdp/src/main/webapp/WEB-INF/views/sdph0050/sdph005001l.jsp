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
			c_submit("frm", "sdph005001d.do");
			 
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
					                           <li>샘플진도 조회</li>
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
									<input type="button" value="오늘" class="gray_inbtn" onclick="javascript:setDate('today', 'sdph005001l.do');"/>
									<input type="button" value="어제" class="gray_inbtn" onclick="javascript:setDate('yesterday', 'sdph005001l.do');" />
									<input type="button" value="1주일" class="gray_inbtn" onclick="javascript:setDate('week', 'sdph005001l.do');" />
									<input type="button" value="1개월" class="gray_inbtn" onclick="javascript:setDate('month', 'sdph005001l.do');" />
									<input type="button" class="gray_inbtn_view" value="조회" onclick="javascript:c_submit('frm', 'sdph005001l.do');">
								</div>
	
								<div class="left_num_count">
									샘플진도  : ${fn:length(sampleRequestItemStat)}건
								</div>
								<!-- left_num_count -->
	                    <div class="search_btn_wrap">
							<div class="search_btn_area">
								<input class="btn_sample" id="" type="button" value="샘플출고 요청" onclick="c_submit('frm','sdph005001u.insert.do');">
							</div>
						</div>
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
												<col style="" />
											</colgroup>
											<thead>
												<tr>
													<th scope="col">NO</th>
													<th scope="col">요청일자</th>
													<th scope="col">전표번호</th>
													<th scope="col">제품코드</th>
													<th scope="col">품명</th>
													
													<th scope="col">단위</th>
													<th scope="col">수량</th>
													<th scope="col">납기일</th>
													<th scope="col">상태</th>
													<th scope="col">비고1</th>
	
													<th scope="col">비고2</th>
													<th scope="col">비고3</th>
													<th scope="col">순번</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
	
								<div class="searchlist_wrap" style="overflow: scroll;" id="div_list">
									<div style="width:1900px;">
										<table summary="샘플진도조회" class="table_common" style="width:100%;">
											<caption>샘플진도조회</caption>
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
											</colgroup>
											<tbody>
												<c:choose>
													<c:when test="${fn:length(sampleRequestItemStat) > 0 }">
														<c:set var="preCoNum" value="" />
														<c:forEach items="${sampleRequestItemStat}" var="row" varStatus="status">
															<tr onclick="detailForm(this)" style="cursor: pointer;" data-ilja="${row.prod_req_dt}" data-jeonpyo_no="${row.prod_req_no}">
															    <!-- <td class="txt_center">
															        <input type="hidden" id="hid_saeobjang" name="hid_saeobjang" value="${row.saeobjang}"/> 
														            <input type="hidden" id="hid_ilja" name="hid_ilja" value="${row.prod_req_dt}"/>
												                 	<input type="hidden" id="hid_jeonpyo_no" name="hid_jeonpyo_no" value="${row.prod_req_no}"/>
                 												</td>-->
															
																<td class="txt_center">${status.count}</td>
																<td class="txt_center">${row.prod_req_dt}</td>
																<td class="txt_center">${row.prod_req_no}</td>
																<td class="txt_lef">${row.item_cd}</td>
																<td class="txt_lef">${row.pummyeong}</td>

																<td class="txt_rig">${row.po_danwi}</td>
																<td class="txt_rig">${row.qty}</td>
																<td class="txt_center">${row.dely_date}</td>
																<td class="txt_center">${row.prgs_status}</td>
																<td class="txt_lef">${row.rmk_1}</td>

																<td class="txt_lef">${row.rmk_2}</td>
																<td class="txt_lef">${row.rmk_3}</td>
																<td class="txt_center">${row.seq}</td>
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