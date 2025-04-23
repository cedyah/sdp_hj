<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 견본요청서 상세 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	//목록화면 호출
	function callList() {
		$("#jeonpyo_no").val("");
		c_submit("frm","sdpa005001l.do");
	}
	
	//주문서 수정 화면 호출
	function callUpdateForm() {
		if(checkCompleteDate()){return;}
		c_submit("frm","sdpa005001u.update.do");
	}
	
	//주문취소
	function cancelRequest() {
		if(checkCompleteDate()){return;}
		c_confirm("요청을 취소하시겠습니까?").then(function (result) {		
	        if(result){		//yes Click
				c_submit("frm", "sdpa005001d_delete.do");
	        } else {		//no Click
	        	return;
	        }
	    });
	}
	
	//수정, 취소가 가능한지 여부 체크 (처리된 데이터는 불가함)
	function checkCompleteDate() {
		if("${smplRequest.cheoliil}" != "" && "${smplRequest.cheoliil}".trim().length > 0) {
			c_alert("처리된 견본요청서는 수정, 취소가 불가 합니다");
			return true;
		}
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="jeonpyo_no" name="jeonpyo_no" value="${smplReqVO.jeonpyo_no}"/>
<input type="hidden" id="ilja" name="ilja" value="${smplReqVO.ilja}"/>
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${smplReqVO.searchDate_from}"/>
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${smplReqVO.searchDate_to}"/>

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">	
					<div class="local_nav_wrap">
					 	<h3 class="sub_tit">견본요청서</h3>		       				
	       				<div class="local_nav">
	                         <ul>
		                           <li class="home">홈</li>
		                           <li>주문관리</li>
		                           <li>견본요청서 상세</li>
	                         </ul>
	                   </div><!--local_nav-->		                   
	       			</div>
				<div class="sub_cont">
					<div class="search_btn_wrap" style="">
						<div class="search_btn_area" >
							<input class="btn_newmake" id="" type="button" value="목록" onclick="callList();">
						</div>
					</div>
										
					<div class="input_subtit">작성자정보</div>											
						<div class="tbl_input_wrap">				
							<table class="tbl_input" summary="견본요청서 상세 - 작성자정보">
								<caption>견본요청서 - 작성자정보</caption>
								<colgroup>
									<col style="width:10%" />
									<col style="width:23%" />
									<col style="width:10%" />
									<col style="width:23%">
									<col style="width:10%" />
									<col style="width:23%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">작성일자</th>
										<td>${smplRequest.ilja}</td>
										<th scope="row">전표번호</th>
										<td>${smplRequest.jeonpyo_no}</td>
										<th scope="row">처리일</th>
										<td class="last">${smplRequest.cheoliil}</td>
									</tr>
									<tr>
										<th scope="row">납기일</th>
										<td>${smplRequest.himang_wanryoil}</td>
										<th scope="row">성명</th>
										<td>${smplRequest.gogaeg_myeong}</td>
										<th scope="row">전화번호</th>
										<td>${smplRequest.tel_no}</td>								
									</tr>
									<tr>
										<th scope="row">제출처</th>
										<td colspan="5">${smplRequest.jechulcheo}</td>
										
									</tr>
									<tr>
										<th scope="row">배달구분</th>
										<td class="last" colspan="1">${smplRequest.baedal_gubun_nm}</td>
										<th scope="row">인수자</th>
										<td>${smplRequest.insuja}</td>
										<th scope="row">연락처</th>
										<td colspan="1">${smplRequest.insu_tel}</td>											
									</tr>
									<tr>
										<th scope="row" style="line-height:18px;">인수지점</th>
										<td class="last" colspan="5">${smplRequest.insu_juso}</td>
									</tr>						
									<tr>
										<th scope="row" style="line-height:18px;">주소</th>
										<td class="last" colspan="5">
											<c:choose>
												<c:when test="${fn:length(smplRequest.zip) > 0}">
													(${smplRequest.zip}) ${smplRequest.addr1} ${smplRequest.addr2}	
												</c:when>
												<c:otherwise>
													${smplRequest.addr2}
												</c:otherwise>
											</c:choose>
										</td>
									</tr>						
								</tbody>
							</table>					
						</div>

					<div class="input_subtit">제품정보</div>											
						<div class="tbl_input_wrap">				
							<table class="tbl_input" summary="견본요청서 상세 - 제품정보">
								<caption>견본요청서 - 제품정보</caption>
								<colgroup>
									<col style="width:10%" />
									<col style="width:23%" />
									<col style="width:10%" />
									<col style="width:23%">
									<col style="width:10%" />
									<col style="width:23%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">제품코드</th>
										<td class="last" colspan="5">${smplRequest.jepum_code}</td>
									</tr>
									<tr>
										<th scope="row">도료제품명</th>
										<td colspan="5">${smplRequest.pummyeong}</td>
									</tr>
									<tr>
										<th scope="row">도장물 완제품명</th>
										<td class="last" colspan="5">${smplRequest.wanjepum_myeong}</td>			
									</tr>
									<tr>
										<th scope="row">도장소재</th>
										<td>${smplRequest.dojang_soji_nm}</td>
										<th scope="row">설치장소</th>
										<td colspan="3">
											${smplRequest.seolchi_jangso_nm}
<%-- 											<c:choose> --%>
<%-- 												<c:when test="${smplRequest.seolchi_jangso eq '1'}">옥내용</c:when> --%>
<%-- 												<c:when test="${smplRequest.seolchi_jangso eq '2'}">옥외용</c:when> --%>
<%-- 												<c:otherwise>${smplRequest.seolchi_jangso}</c:otherwise> --%>
<%-- 											</c:choose> --%>
										</td>
									</tr>
									<tr>
										<th scope="row">월예상사용량</th>
										<td>${smplRequest.sayong_mm}</td>
										<th scope="row">견본량</th>
										<td>${smplRequest.sulyang} (단위 : ${smplRequest.pojang_danwi_a}${smplRequest.pojang_danwi_b})</td>
										<th scope="row">시판매수</th>
										<td class="last">${smplRequest.sipyeonmaesu}</td>												
									</tr>
									<tr>
										<th scope="row">경쟁사</th>
										<td>${smplRequest.kyeongjaengsa}</td>
										<th scope="row">중금속관리여부</th>
										<td>${smplRequest.junggeumsok_yn_nm}</td>
										<th scope="row">희석제필요</th>
										<td class="last">${smplRequest.hiseogje_nm}</td>
									</tr>
								</tbody>
							</table>					
						</div>
						
						<div class="input_subtit">표준시편</div>											
							<div class="tbl_input_wrap">				
								<table class="tbl_input" summary="견본요청서 상세 - 표준시편">
									<caption>견본요청서 - 표준시편</caption>
									<colgroup>
										<col style="width:10%" />
										<col style="width:40%" />
										<col style="width:10%" />
										<col style="width:40%">
									</colgroup>
									<tbody>
										<tr class="first">
											<th scope="row">표준시편</th>
											<td>${smplRequest.pyojun_sipyeon_nm}&nbsp;(${smplRequest.teugkisahang_2})</td>
											<th scope="row">광택</th>
											<td class="last">
												${smplRequest.kwangtaeg_nm}
												<c:if test="${smplRequest.gloss_a != '' or smplRequest.gloss_b != '' }">
													(${smplRequest.gloss_a} ~ ${smplRequest.gloss_b})
												</c:if>
											</td>
										</tr>
										<!-- 2017/1/24 이K 추가요청 -->
										<tr>
											<th scope="row">솔리드</th>
											<td colspan="3">${smplRequest.solid_nm}</td>
										</tr>											
									</tbody>
								</table>					
							</div>
						
						<div class="input_subtit">조건정보</div>											
						<div class="tbl_input_wrap">				
							<table class="tbl_input" summary="견본요청서 상세- 조건정보">
								<caption>견본요청서 - 조건정보</caption>
								<colgroup>
									<col style="width:10%" />
									<col style="width:23%" />
									<col style="width:10%" />
									<col style="width:23%">
									<col style="width:10%" />
									<col style="width:23%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">도장방법</th>
										<td>${smplRequest.dojang_bangbeob_nm}</td>
										<th scope="row">도료종류</th>
										<td class="last" colspan="3">
											${smplRequest.doryo_jonglyu_nm} [${smplRequest.doryo_jonglyu_m}]
										</td>											
									</tr>
									<tr>
										<th scope="row">전처리조건</th>
										<td>${smplRequest.jeoncheoli_bangbeob_nm}</td>
										<th scope="row">건조조건</th>
										<td>${smplRequest.geonjo_bangbeob_nm}</td>
										<th scope="row">하도도료</th>
										<td class="last">${smplRequest.hado_dolyo}</td>								
									</tr>
									<tr>
										<th scope="row">비고</th>											
										<td class="last" colspan="5">
<%-- 											<c:set var="nr" value="\r\n" /> --%>
											${fn:replace(smplRequest.teugkisahang_1, newLineChar, "<br>")}
										</td>											
									</tr>																			
								</tbody>
							</table>					
						</div>
						
						<div class="bottom_btn_wrap">					
							<div class="right_btn_area">
								<input class="btn_modify" type="button" id="" value="수정" onclick="callUpdateForm();" />
								<input class="btn_ord_cancel" type="button" id="" value="요청 취소" onclick="cancelRequest();" />
							</div>
						</div>											
	        	</div><!--sub_cont-->       
				
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