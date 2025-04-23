<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 견본요청서 등록 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
		//입력시 현재일자 셋팅
		if("${flag}" == "insert") {
			settingDate($(".datepicker_aftToday"));
			$("#ilja").val(dateToString(new Date(),"."));
		}
		
		//불러올때 제품 타입을 체크해서 제품코드 검색 활성화 및 비활성화
		if ("${smplRequest.gyeonbon_gubun}" == "2") {	//신규품
			$("#chkBox_gyeonbon_gubun").attr("checked", true);
			$("#jepum_code").attr("disabled", true);
			$("#tr_1").hide();
			$("#tr_2").removeClass();
			$("#tr_2").addClass("first");
			$("#btn_addItem").attr("disabled", true);
			$("#btn_addItem").hide();
			$("#pummyeong").attr("readonly", false);
			$("#pummyeong").removeClass();
		}
		
		//제품 타입에 따라 제품코드 검색 활성화 및 비활성화
		$("#chkBox_gyeonbon_gubun").on("change", "", function() {
			if($("#chkBox_gyeonbon_gubun").prop("checked")){		//신규품
				$("#pummyeong").removeClass();
				$("#jepum_code").attr("disabled", true);
				$("#tr_1").hide();
				$("#tr_2").removeClass();
				$("#tr_2").addClass("first");
				$("#btn_addItem").attr("disabled", true);
				$("#btn_addItem").hide();
				$("#pummyeong").attr("readonly", false);
				$("#pummyeong").removeClass();
				$("#jepum_code").val("");
				$("#pummyeong").val("");
				
			} else {		//기존품
				$("#pummyeong").addClass("readonly");
				$("#jepum_code").attr("disabled", false);
				$("#tr_1").show();
				$("#tr_2").removeClass();
				$("#btn_addItem").attr("disabled", false);
				$("#btn_addItem").show();
				$("#pummyeong").attr("readonly", true);
				$("#pummyeong").addClass("readonly");
				$("#jepum_code").val("");
				$("#pummyeong").val("");
			}
		});
		
		//불러올때 도료 종류를 체크해서 text 활성화
		if("${smplRequest.doryo_jonglyu}" != "5") {
			$("#doryo_jonglyu_m").hide();
			$("#doryo_jonglyu_m").attr("disabled", true);
		}
		
		//도료 종류가 기타 이면 text 박스 활성화
		$("#doryo_jonglyu").on("change", "", function() {
			if($("#doryo_jonglyu option:selected").val() == '5') {
				$("#doryo_jonglyu_m").show();
				$("#doryo_jonglyu_m").attr("disabled", false);
			} else {
				$("#doryo_jonglyu_m").hide();
				$("#doryo_jonglyu_m").attr("disabled", true);
			}
		});
	});
	
	//팝업에서 선택한 아이템 정보 불러오기
	function addItem(item, description) {
		$("#jepum_code").val(item);
		$("#pummyeong").val(description);
	}
	
	//견본요청서 작성
	function confirmSmplRequest() {
		if($("#chkBox_gyeonbon_gubun").prop("checked")) {
			$("#gyeonbon_gubun").val("2");	//신규품
			
		} else {
			$("#gyeonbon_gubun").val("1");	//기존품
		}
		
		//null 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		//데이터 검증
		if(!dataCheck()) {
			return;
		}
		
		c_submit("frm", "sdpa005001u_insert.do");
	}
	
	//견본요청서 수정
	function updateSmplRequest() {
		if($("#chkBox_gyeonbon_gubun").prop("checked")) {
			$("#gyeonbon_gubun").val("2");	//신규품
			
		} else {
			$("#gyeonbon_gubun").val("1");	//기존품
		}
		
		//null 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		//데이터 검증
		if(!dataCheck()) {
			return;
		}
		
		c_submit("frm", "sdpa005001u_update.do");
	}
	
	//데이터 필수값 검증
	function dataCheck() {
		//라디오버튼 필수값 검증
		var len_seolchi_janso = $("input[name='seolchi_jangso']:checked").length;
		var len_junggeumsok_yn = $("input[name='junggeumsok_yn']:checked").length;
		var len_solid = $("input[name='solid']:checked").length;
		
		if(len_seolchi_janso < 1) {
			c_alert("설치장소를 선택해 주십시오");
			return false;
			
		} else if(len_junggeumsok_yn < 1) {
			c_alert("중금속 관리 여부를 선택해 주십시오");
			return false;
		} else if(len_solid < 1) {
			c_alert("솔리드를 선택해 주십시오");
			return false;
		}
		
		//배달구분이 화물착불, 택배착불 경우 인수자, 인수자 전화번호, 주소, 도착지점 필수 입력 해야함
		if($("#baedal_gubun").val() == "3" || $("#baedal_gubun").val() == "4") {
			if($("#insuja").val().trim() == '') {
				c_alert("착불의 경우 인수자를 반드시 입력해야 합니다");
				$("#insuja").focus();
				return false;
			
			} else if($("#tel_no").val().trim() == '') {
				c_alert("착불의 경우 연락처를 반드시 입력해야 합니다");
				$("#tel_no").focus();
				return false;
			} else if($("#target_place").val().trim() == '') {
				c_alert("착불의 경우 인수지점을 반드시 입력해야 합니다");
				$("#target_place").focus();
				return false;
			}
		}
		
		//인수자와 전화번호 체크
		if(!addrCheck()) {
			c_alert("주소가 입력된 경우 인수자와 전화번호를 반드시 입력해야 합니다");
			return;
		}
		
		return true;
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
<input type="hidden" id="searchDate_from" name="searchDate_from" value="${smplReqVO.searchDate_from}"/>
<input type="hidden" id="searchDate_to" name="searchDate_to" value="${smplReqVO.searchDate_to}"/>
<input type="hidden" id="gyeonbon_gubun" name="gyeonbon_gubun" value="${smplReqVO.gyeonbon_gubun}"/>

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">	
					<div class="local_nav_wrap">
					 	<h3 class="sub_tit">견본요청서 작성</h3>		       				
	       				<div class="local_nav">
	                         <ul>
		                           <li class="home">홈</li>
		                           <li>주문관리</li>
		                           <li>견본요청서</li>
	                         </ul>
	                   </div><!--local_nav-->		                   
	       			</div>
				<div class="sub_cont">
					<div class="input_subtit">작성자정보</div>											
						<div class="tbl_input_wrap">				
							<table class="tbl_input" summary="견본요청서 입력폼 - 작성자정보">
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
										<td >
											<input type="text" class="readonly" id="ilja" name="ilja" value="${smplRequest.ilja}" readonly/>
										</td>
										<th scope="row">요청번호</th>
										<td>
											<input class="readonly" type="text" id="jeonpyo_no" name="jeonpyo_no" value="${smplRequest.jeonpyo_no}" readonly/>
										</td>
										<th scope="row">처리일</th>
										<td class="last">
											<input type="text" class="readonly" id="cheoliil" name="cheoliil" value="${smplRequest.cheoliil}" readonly/>
										</td>
									</tr>
									<tr>
										<th scope="row">납기일</th>
										<td>
											<input type="text" class="ico_cal datepicker_aftToday" id="himang_wanryoil" name="himang_wanryoil"
												value="${smplRequest.himang_wanryoil}" title="납기일" req readonly/>
										</td>
										<th scope="row">성명</th>
										<td>
											<input type="text" id="gogaeg_myeong" name="gogaeg_myeong" maxlength="5" value="${smplRequest.gogaeg_myeong}"/>											
										</td>
										<th scope="row">전화번호</th>
										<td>
											<input type="text" class="phone" title="휴대폰번호 입력" placeholder="예시 : 01012345678" id="tel_no" name="tel_no" value="${smplRequest.tel_no}"/>								
										</td>								
									</tr>
									<tr>
										<th scope="row">제출처</th>
										<td colspan="5">
											<input type="text" style="width:848px;" id="jechulcheo" name="jechulcheo" value="${smplRequest.jechulcheo}" maxlength="15" title="제출처" req/>
										</td>
									</tr>
									<tr>
										<th scope="row">배달구분</th>
										<td class="last" colspan="1">
											<select class="select" title="배달구분" id="baedal_gubun" name="baedal_gubun">
												<c:forEach items="${code10}" var="row" varStatus="status">
													<option value="${row.code}">${row.name}</option>
												</c:forEach>
											</select>							
										</td>
										<th scope="row">인수자</th>
										<td>
											<input type="text" id="insuja" name="insuja" value="${smplRequest.insuja}" maxlength="6"/>
										</td>
										<th scope="row">연락처</th>
										<td colspan="1">
											<input type="text" class="phone" id="tel_no" name="insu_tel" placeholder="예시 : 01012345678" value="${smplRequest.insu_tel}"/>
										</td>											
									</tr>
									<tr>
										<th scope="row" style="line-height:18px;">인수지점</th>
										<td class="last" colspan="5">
											<input type="text" class="order_add" id="insu_juso" name="insu_juso"  style="width:300px;" maxlength="40" value="${smplRequest.insu_juso}"/>
										</td>
									</tr>						
									<tr>
										<th scope="row" style="line-height:18px;">주소</th>
										<td class="last" colspan="5">
											<input class="btn_del2" type="button" id="" value="주소 초기화" onclick="removeAddr();" />
											<input type="button" class="order_zipnum" onclick="javascript:popup_searchAddress(); return false;"/>
											<input type="text" class="order_zip readonly" id="zip" name="zip"  value="${smplRequest.zip}" readonly/>
											<input type="button" class="order_addlist" value="주소록" onclick="popup_manageAddr();"/> ※ 사업장(점포) 소재지에서 인수할 경우 입력하지 마세요
											<input type="text" class="order_add readonly" id="addr1" name="addr1"  value="${smplRequest.addr1}" readonly/>
											<input type="text" class="order_add2" id="addr2" name="addr2"  value="${smplRequest.addr2}" maxlength="50"/>
										</td>
									</tr>						
								</tbody>
							</table>					
						</div>

					<div class="input_subtit">
						<p style="float: left; margin-right:20px;">제품정보</p>
						<p style="float: left; font: normal 10px; font-size:12px; color:#626262;">
							<input type="checkbox" id="chkBox_gyeonbon_gubun" name="chkBox_gyeonbon_gubun"  value="">&nbsp;신규품의 경우 체크해주세요
						</p>
					</div>
						<div class="tbl_input_wrap">				
							<table class="tbl_input" summary="견본요청서 입력폼 - 제품정보">
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
									<tr class="first" id="tr_1">
										<th scope="row">제품코드</th>
										<td class="last" colspan="5">
											<input class="readonly" type="text" id="jepum_code" name="jepum_code"  value="${smplRequest.jepum_code}" style="width:200px;" readonly/>
											<input type="button" class="order_addlist" id="btn_addItem" name="btn_addItem" onclick="popup_itemList('single');"
												value="제품검색" />
										</td>
									</tr>
									<tr class="" id="tr_2">
										<th scope="row">도료제품명</th>
										<td colspan="5">
											<input type="text" class="readonly" id="pummyeong" name="pummyeong" style="width:500px;" maxlength="25" value="${smplRequest.pummyeong}" title="도료제품명" req readonly/> ※ 신제품의 경우 색상을 포함하여 입력하십시오
										</td>
									</tr>
									<tr >
										<th scope="row">도장물 완제품명</th>
										<td colspan="5">
											<input type="text" id="wanjepum_myeong" name="wanjepum_myeong" style="width:500px;" maxlength="25" value="${smplRequest.wanjepum_myeong}" title="도장물완제품명" req/>
										</td>			
									</tr>
									<tr>
										<th scope="row">도장소재</th>
										<td>
											<select class="select" id="dojang_soji" name="dojang_soji" title="도장소재" req>
												<option value=""></option>
												<c:forEach items="${code02}" var="row" varStatus="status">
													<option value="${row.code}" <c:if test="${fn:trim(smplRequest.dojang_soji) == fn:trim(row.code)}">selected</c:if>>${row.name}</option>
												</c:forEach>
											</select>
										</td>
										<th scope="row">설치장소</th>
										<td colspan="3">
											<input type="radio" class="first" id="seolchi_jangso" name="seolchi_jangso" value="2" <c:if test="${fn:trim(smplRequest.seolchi_jangso) == '2'}">checked</c:if> title="설치장소" req/>옥내용			
											<input type="radio" class="" id="seolchi_jangso" name="seolchi_jangso" value="1" <c:if test="${fn:trim(smplRequest.seolchi_jangso) == '1'}">checked</c:if> title="설치장소" req/>옥외용
										</td>
									</tr>
									<tr>
										<th scope="row">월예상사용량</th>
										<td>
											<input type="text" class="" id="sayong_mm" name="sayong_mm" maxlength="6" value="${smplRequest.sayong_mm}" title="월예상사용량" req/>
										</td>
										<th scope="row">견본량</th>
										<td>(단위 
											<input type="text" class="entry_f" style="width:45px;" id="pojang_danwi_a" name="pojang_danwi_a" value="${smplRequest.pojang_danwi_a}"/>
											<select class="select" id="pojang_danwi_b" name="pojang_danwi_b">
												<c:forEach items="${code09}" var="row" varStatus="status">
													<c:if test="${row.code eq 'LT' or row.code eq 'KG'}" >
														<option value="${row.code}" <c:if test="${fn:trim(smplRequest.pojang_danwi_b) eq fn:trim(code)}">selected</c:if>>${row.code}</option>
													</c:if>
												</c:forEach>
											</select>
											수량 <input type="text" class="entry" style="width:45px;" id="sulyang" name="sulyang" value="${smplRequest.sulyang}" title="수량" req/> 개)			
										</td>
										<th scope="row">시편매수</th>
										<td class="last">
											<input type="text" class="entry" style="" id="sipyeonmaesu" name="sipyeonmaesu" maxlength="3" value="${smplRequest.sipyeonmaesu}"/> 매							
										</td>												
									</tr>
									<tr>
										<th scope="row">경쟁사</th>
										<td>
											<input type="text" id="kyeongjaengsa" name="kyeongjaengsa" maxlength="10" value="${smplRequest.kyeongjaengsa}"/>												
										</td>
										<th scope="row">중금속관리여부</th>
										<td>
											<input type="radio" class="first" id="junggeumsok_yn" name="junggeumsok_yn" value="Y" <c:if test="${fn:trim(smplRequest.junggeumsok_yn) eq 'Y'}">checked</c:if>>관리
											<input type="radio" class="" id="junggeumsok_yn" name="junggeumsok_yn" value="N" <c:if test="${fn:trim(smplRequest.junggeumsok_yn) eq 'N'}">checked</c:if>>비관리
										</td>
										<th scope="row">희석제필요</th>
										<td class="last">
											<select class="select" id="hiseogje" name="hiseogje" title="희석제 유무" req>
												<option value="N" <c:if test="${fn:trim(smplRequest.hiseogje) eq 'N'}">selected</c:if>>무</option>
												<option value="Y" <c:if test="${fn:trim(smplRequest.hiseogje) eq 'Y'}">selected</c:if>>유</option>
											</select>
										</td>
									</tr>
								</tbody>
							</table>					
						</div>
						
						<div class="input_subtit">표준시편</div>											
							<div class="tbl_input_wrap">				
								<table class="tbl_input" summary="견본요청서 입력폼 - 표준시편">
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
											<td>
												<select class="select" id="pyojun_sipyeon" name="pyojun_sipyeon" title="표준시편" req>
													<option value=""></option>
													<c:forEach items="${code03}" var="row" varStatus="status">
														<option value="${row.code}" <c:if test="${fn:trim(smplRequest.pyojun_sipyeon) eq fn:trim(row.code)}">selected</c:if>>${row.name}</option>
													</c:forEach>
												</select>
												<input type="text" id="teugkisahang_2" name="teugkisahang_2" style="width:250px;" maxlength="35" value="${smplRequest.teugkisahang_2}"/>
											</td>
											<th scope="row">광택</th>
											<td class="last">
												<select class="select" id="kwangtaeg" name="kwangtaeg" title="광택" req>
													<option value=""></option>
													<c:forEach items="${code04}" var="row" varStatus="status">
														<option value="${row.code}" <c:if test="${fn:trim(smplRequest.kwangtaeg) eq fn:trim(row.code)}">selected</c:if>>${row.name}</option>
													</c:forEach>
												</select>
												(GLOSS: <input type="text" class="entry" style="width:30px" id="gloss_a" name="gloss_a" value="${smplRequest.gloss_a}"/> % - 
												<input type="text" class="entry" style="width:30px" id="gloss_b" name="gloss_b" value="${smplRequest.gloss_b}"/> %)
											</td>
										</tr>
										<!-- 2017/1/24 이K 추가요청 -->
										<tr>
											<th scope="row">솔리드</th>													
											<td colspan="3">
												<input type="radio" class="first" id="solid" name="solid" value="3"<c:if test="${smplRequest.solid == '3'}"> checked</c:if>>솔리드
												<input type="radio"  id="solid" name="solid" value="4"<c:if test="${smplRequest.solid == '4'}"> checked</c:if>>메탈릭/펄
											</td>												
										</tr>											
									</tbody>
								</table>					
							</div>
						
						<div class="input_subtit">조건정보</div>											
						<div class="tbl_input_wrap">				
							<table class="tbl_input" summary="견본요청서 입력폼 - 조건정보">
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
										<td>
											<select class="select" id="dojang_bangbeob" name="dojang_bangbeob" title="도장방법" req>
												<option value=""></option>
												<c:forEach items="${code06}" var="row" varStatus="status">	
													<option value="${row.code}" <c:if test="${fn:trim(smplRequest.dojang_bangbeob) eq fn:trim(row.code)}">selected</c:if>>${row.name}</option>
												</c:forEach>
											</select>
										</td>
										<th scope="row">도료종류</th>
										<td colspan="3">
											<select class="select" id="doryo_jonglyu" name="doryo_jonglyu" title="도료종류" req>
												<option value=""></option>
												<c:forEach items="${code05}" var="row" varStatus="status">
													<option value="${row.code}" <c:if test="${fn:trim(smplRequest.doryo_jonglyu) eq fn:trim(row.code)}">selected</c:if>>${row.name}</option>
												</c:forEach>
											</select>
											<input type="text" placeholder="내용을 기입해주십시오" id="doryo_jonglyu_m" name="doryo_jonglyu_m" value="${smplRequest.doryo_jonglyu_m}"
												maxlength="15" style="width:250px;" />
										</td>											
									</tr>
									<tr>
										<th scope="row">전처리조건</th>
										<td>
											<select class="select" id="jeoncheoli_bangbeob" name="jeoncheoli_bangbeob" title="전처리조건" req>
												<option value=""></option>
												<c:forEach items="${code08}" var="row" varStatus="status">
													<option value="${row.code}" <c:if test="${row.code eq smplRequest.jeoncheoli_bangbeob}">selected</c:if>>${row.name}</option>
												</c:forEach>
											</select>
										</td>
										<th scope="row">건조조건</th>
										<td>
											<select class="select" id="geonjo_bangbeob" name="geonjo_bangbeob" title="건조조건" req>
												<option value=""></option>
												<c:forEach items="${code07}" var="row" varStatus="status">
													<option value="${row.code}" <c:if test="${row.code eq smplRequest.geonjo_bangbeob}">selected</c:if>>${row.name}</option>
												</c:forEach>
											</select>
										</td>
										<th scope="row">하도도료</th>
										<td class="last">
											<input type="text" id="hado_dolyo" name="hado_dolyo" style="width:200px;" maxlength="15" value="${smplRequest.hado_dolyo}"/>
										</td>								
									</tr>
									<tr>
										<th scope="row">비고</th>											
										<td class="last" colspan="5">
											<textarea rows="5" style="width:100%;" id="teugkisahang_1" name="teugkisahang_1" maxlength="35">${smplRequest.teugkisahang_1}</textarea>
										</td>											
									</tr>
								</tbody>
							</table>					
						</div>
						
						<div class="btn_center_wrap">
							<c:choose>
								<c:when test="${flag == 'insert'}">
									<input type="button" class="btn_big_order" value="등록하기" id="" name="" onclick="confirmSmplRequest();"/>
									<input type="button" class="btn_big_cancel" value="취소하기" id="" name="" onclick="c_submit('frm', 'sdpa005001l.do');" />
								</c:when>
								<c:otherwise>
									<input type="button" class="btn_big_order" value="수정하기" id="" name="" onclick="updateSmplRequest();"/>
									<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpa005001d.do');" />
								</c:otherwise>
							</c:choose>
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