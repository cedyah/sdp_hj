<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 신규제조의뢰서 등록&수정  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//입력시 현재일자 셋팅
		if("${flag}" == "insert") {
			settingDate($(".datepicker_aftToday"));
		}
		
		//체크박스가 변경될때마다 hidden 값 변경
		$("#chkBox_prod_after_yn").on("change", "", function(event) {
			if($("#chkBox_prod_after_yn").prop("checked")){
				$("#prod_after_yn").val("Y");
			} else {
				$("#prod_after_yn").val("N");
				
			}
		});
		
		//모든 체크박스 체크하고 화면 불러옴
		$("input[name='chkBox']").attr("checked", true);
		$("input[id='parentChkBox']").attr("checked", true);
		
	});
	
	var rowNum = 1;			//행추가용 Row의 번호
	var target_tr = "";		//팝업 호출시 버튼이 있는 tr id를 저장함 (addItem 펑션 참조)

	//견본요청 팝업창 호출
	function changeDiv(obj) {
		//검색기간 1년 설정해서 팝업 호출
		var dt = new Date();
		var toDt = dateToString(dt, ".");
		dt.setYear(dt.getFullYear() -1);
		var fromDt = dateToString(dt, ".");
		
		target_tr = $(obj).parent().parent().parent("tr").attr("id");		//팝업을 호출한 Tr Id를 저장
		var pop = window.open("sdpa005001p.do?searchDate_from=" + fromDt + "&searchDate_to=" + toDt, "pop", "width=775, height=650, scrollbars=yes, resizable=yes");
		pop.focus();
		
	}
	
	//버튼 교체 및 견본요청 값 불러오기
	function addItem(ilja, jeonpyo_no, saeobjang, pummyeong) {
		//팝업창에서 가져온 값 대입
		//히든값 먼저 입력
		$("#"+target_tr).find("input[name='gyeon_ilja']").val(ilja);
		$("#"+target_tr).find("input[name='gyeon_jeonpyo_no']").val(jeonpyo_no);
		$("#"+target_tr).find("input[name='gyeon_saeobjang']").val(saeobjang);
		
		if($("#pummyeong").val().length > 0) {
			c_confirm("이미 품명이 입력되어 있습니다. 덮어 씌우시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					$("#"+target_tr).find("input[name='pummyeong']").val(pummyeong);
				} else { //no Click
					
				}
			});
		} else {
			$("#"+target_tr).find("input[name='pummyeong']").val(pummyeong);
		}
		if(saeobjang == "1") {saeobjang = "부산";}
		else if(saeobjang == "2") {saeobjang = "서울";}
		else if(saeobjang == "3") {saeobjang = "안양";}
		else if(saeobjang == "4") {saeobjang = "호남";}
		else if(saeobjang == "5") {saeobjang = "중부";}
		else if(saeobjang == "6") {saeobjang = "함안";}
		else if(saeobjang == "7") {saeobjang = "대구";}
		
		//사업장 변환후 화면 표시용 필드 입력
		$("#"+target_tr).find("input[name='ipt_gyeon_ilja']").val(ilja);
		$("#"+target_tr).find("input[name='ipt_gyeon_jeonpyo_no']").val(jeonpyo_no);
		$("#"+target_tr).find("input[name='ipt_gyeon_saeobjang']").val(saeobjang);
		
		//div 교체
		$("#"+target_tr).find("#div_smplReqButton").hide();
		$("#"+target_tr).find("#div_smplReqContents").show();
	}
	
	//신규제조의뢰 작성(db insert)
	function confirmSmplRequest() {
		//데이터 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		//인수자와 전화번호 체크
		if(!addrCheck()) {
			c_alert("주소가 입력된 경우 인수자와 전화번호를 반드시 입력해야 합니다");
			return;
		}
		
		if(Number($("#pojang_sulyang").val()) < 1) {
			c_alert("수량을 1이상 입력해 주십시오");
			return;
		}

		if(Number($("#pojang_danwi_a").val()) < 1) {
			c_alert("단위 수량을 1이상 입력해 주십시오");
			return;
		}
		
// 		c_confirm("신규제조를 등록하시겠습니까?").then(function(result) { //커스텀 confirm
// 			if (result) { //yes Click
// 				c_submit("frm", "sdpa004001u_insert.do");
// 			} else { //no Click
// 				return;
// 			}
// 		});
		
		//주소 미입력시 알림
		if(($("#addr1").val().length + $("#addr2").val().length) < 1) {
			c_confirm("주소가 입력되지 않았습니다. 계속 하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					if("${flag}" == "insert") {
						c_submit("frm", "sdpa004001u_insert.do");
					
					} else {
						c_submit("frm", "sdpa004001u_update.do");
					}
				} else { //no Click
					return;
				}
			});
			
		} else {
			if("${flag}" == "insert") {
				c_confirm("신규 제조의뢰를 등록 하시겠습니까?").then(function(result) { //커스텀 confirm
					if (result) { //yes Click
						c_submit("frm", "sdpa004001u_insert.do");
					} else { //no Click
						return;
					}
				});

			} else {
				c_confirm("신규 제조의뢰를 수정 하시겠습니까?").then(function(result) { //커스텀 confirm
					if (result) { //yes Click
						c_submit("frm", "sdpa004001u_update.do");
					} else { //no Click
						return;
					}
				});
			}
		}
	}
	
	//신규제조의뢰 수정(db update)
// 	function updateSmplRequest() {
// 		//데이터 검증
// 		if(!doFormValidate(document.frm)){
// 			return;
// 		}
		
// 		//인수자와 전화번호 체크
// 		if(!addrCheck()) {
// 			c_alert("주소가 입력된 경우 인수자와 전화번호를 반드시 입력해야 합니다");
// 			return;
// 		}
		
// 		c_confirm("신규제조를 수정하시겠습니까?").then(function(result) { //커스텀 confirm
// 			if (result) { //yes Click
// 				c_submit("frm", "sdpa004001u_update.do");
// 			} else { //no Click
// 				return;
// 			}
// 		});
// 	}
	
	//견본요청번호 초기화
	function cancelGyeon_info(obj) {
		//팝업창에서 가져온 값 대입
		$(obj).parent().parent().find("#gyeon_ilja").val("");
		$(obj).parent().parent().find("#gyeon_jeonpyo_no").val("");
		$(obj).parent().parent().find("#gyeon_saeobjang").val("");
		
		//div 교체
		$(obj).parent().parent().find("#div_smplReqButton").show();
		$(obj).parent().parent().find("#div_smplReqContents").hide();
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
			
							<div class="orderlist_wrap_tit">
								<table class="table_common" summary="신규제조의뢰서">
									<caption>신규제조의뢰서</caption>
									<colgroup>
										<col style="width: 480px;" />
										<col style="width: 120px;" />
										<col style="width: 120px;" />
										<col style="width: 115px;" />
										<col style="width: 110px;" />
										
										<col style="" />
									</colgroup>
									<thead>
										<tr>
											<th scope="col" rowspan="2">품명</th>																
											<th scope="col" rowspan="2">판매단위</th>		
											<th scope="col" rowspan="2">주문수량</th>
											<th class="tbl_col_tit" scope="col" colspan="3">참조견본</th>
										</tr>
										<tr>
											<th class="tbl_row_tit" scope="col" rowspan="" style="border-right:1px solid #606c79;">작성일</th>
											<th class="tbl_row_tit" scope="col" colspan="" style="border-right:1px solid #606c79;">전표번호</th>
											<th class="tbl_row_tit" scope="col" colspan="">사업장</th>
										</tr>
									</thead>
								</table>
							</div>
			
							<div class="orderlist_wrap">
								<table class="table_common" id="table_item">
									<caption>주문 리스트</caption>
									<colgroup>
										<col style="width: 480px;" />
										<col style="width: 120px;" />
										<col style="width: 120px;" />
<!-- 										<col style="width: 100px;" /> -->
<!-- 										<col style="width: 100px;" /> -->
										
										<col style="" />
									</colgroup>
									<tbody id="tbody_list">
										<tr id="tr_1">													
											<td class="pro_name" colspan="1">
												<input type="text" style="width:100%" id="pummyeong" name="pummyeong" value="${nprodReqSub.pummyeong}" 
													maxlength="30" title="제품명" req/>
											</td>	
											<td class="txt_center" colspan="1">
												<input type="text" class="entry_f" id="pojang_danwi_a" name="pojang_danwi_a"
													value="${nprodReqSub.pojang_danwi_a != null ? nprodReqSub.pojang_danwi_a:0}" title="판매단위(수량)" req/>
												<select class="select" title="판매단위" id="pojang_danwi_b" name="pojang_danwi_b">
													<option value="LT" <c:if test="${'LT' == nprodReqSub.pojang_danwi_b}"> selected</c:if>>LT</option>
													<option value="KG" <c:if test="${'KG' == nprodReqSub.pojang_danwi_b}"> selected</c:if>>KG</option>
													<option value="EA" <c:if test="${'EA' == nprodReqSub.pojang_danwi_b}"> selected</c:if>>EA</option>
												</select>
											</td>																															
											<td class="txt_center"  colspan="1">
												<input type="text" class="entry" id="pojang_sulyang" name="pojang_sulyang" title="주문수량" 
													value="${nprodReqSub.pojang_sulyang != null ? nprodReqSub.pojang_sulyang : 0}" title="주문수량" req/>
											</td>									
											<td class="txt_center" id="td_smplReq" colspan="">
												<div id="div_smplReqContents" name="" style="<c:out value="${fn:length(nprodReqSub.gyeon_jeonpyo_no) > 0 ? '' : 'display:none;'}"/>">
													<!-- 저장용 hidden값 -->
													<input type="hidden" id="gyeon_ilja" name="gyeon_ilja" value="${nprodReqSub.gyeon_ilja}"/>
													<input type="hidden" id="gyeon_jeonpyo_no" name="gyeon_jeonpyo_no" value="${nprodReqSub.gyeon_jeonpyo_no}"/>
													<input type="hidden" id="gyeon_saeobjang" name="gyeon_saeobjang" value="${nprodReqSub.gyeon_saeobjang}"/>
													
													<!-- 화면표시용 input -->
													<input type="text" class="readonly" id="ipt_gyeon_ilja" name="ipt_gyeon_ilja" style="width: 100px;"
														value="${nprodReqSub.gyeon_ilja}" maxlength="10" readonly/>
													<input type="text" class="readonly" id="ipt_gyeon_jeonpyo_no" name="ipt_gyeon_jeonpyo_no" style="width: 100px;"
														value="${nprodReqSub.gyeon_jeonpyo_no}" maxlength="5" readonly/>
													<input type="text" class="readonly" id="ipt_gyeon_saeobjang" name="ipt_gyeon_saeobjang" style="width: 50px;"
														value="${nprodReqSub.gyeon_saeobjang_nm}" maxlength="4" readonly/>
													<input class="order_addlist" id="" type="button" value="취소" onclick="cancelGyeon_info(this);">
												</div>
												
												<div id="div_smplReqButton" name="" style="<c:out value="${fn:length(nprodReqSub.gyeon_jeonpyo_no) > 0 ? 'display:none;' : ''}"/>">
													<input class="order_addlist" id="" type="button" value="견본요청 불러오기" onclick="changeDiv(this);">
												</div>
											</td>
										</tr>
									</tbody>
								</table>	
							</div>
							<!--bottom_btn_wrap-->				
									
							<div class="order_subtit">주문자 정보</div>
												
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
											<th scope="row">배달구분</th>
											<td class="last">
												<select class="select" title="배달구분" id="baedal_gubun" name="baedal_gubun">
													<c:if test="${fn:length(code10) > 0}">
														<c:forEach items="${code10}" var="row" varStatus="status">
															<option value="${row.code}"
																<c:if test="${fn:trim(nprodReqHeader.baedal_gubun) == row.code}"> selected</c:if> >${row.name}</option>
														</c:forEach>
														<option value="B" <c:if test="${fn:trim(nprodReqHeader.baedal_gubun) == 'B'}"> selected</c:if>>요청후출고 (11말 또는 44G/A 이상)</option>
													</c:if>
												</select>
											</td>
											<th scope="row">생산완료 요청일</th>
											<td class="last">
												<input type="text" class="ico_cal datepicker_aftToday" id="euiloiil" name="euiloiil" value="${nprodReqHeader.euiloiil}"
													readonly title="배달요청일" req/>
											</td>
										</tr>
										<tr>
											<th scope="row">인수자</th>
											<td class="last"><input type="text" id="insuja" name="insuja" value="${nprodReqHeader.insuja}" maxlength="10" /></td>
											<th scope="row">전화번호</th>
											<td class="last">
												<input type="text" class="phone" id="tel_no" name="tel_no" placeholder="예시 : 01012345678" value="${nprodReqHeader.tel_no}" title="전화번호"/>							
											</td>
										</tr>
										<tr>
											<th scope="row">배달장소</th>
											<td class="last" colspan="3">
												<input class="btn_del2" type="button" id="" value="주소 초기화" onclick="removeAddr();" />
												<input type="button" class="order_zipnum" value="" onclick="javascript:popup_searchAddress(); return false;"/>
												<input type="text" class="order_zip readonly" id="zip" name="zip" value="${nprodReqHeader.zip}" onfocus="this.blur();" readonly/>
												<input type="button" class="order_addlist" value="주소록" onclick="popup_manageAddr();"/> ※ 사업장(점포) 소재지에서 인수할 경우 입력하지 마세요
												<input type="text" class="order_add readonly" id="addr1"  name="addr1" onfocus="this.blur();"
													value="${nprodReqHeader.addr1}" readonly title="주소" />
												<input type="text" class="order_add2" id="addr2"  name="addr2" value="${nprodReqHeader.addr2}" maxlength="50"/>
											</td>
										</tr>
										<tr>
											<th scope="row">비고</th>
											<td class="last" colspan="3">
												<textarea id="bigo" name="bigo" maxlength="20" style="height:140px; width:900px;"
													placeholder="비고는 최대 20자까지 입력 가능합니다">${nprodReqHeader.bigo}</textarea>
											</td>
										</tr>							
									</tbody>
								</table>					
							</div>	
							
							<div class="btn_center_wrap">
								<c:choose>
									<c:when test="${flag == 'insert'}">
										<input type="button" class="btn_big_order" value="등록하기" id="" name="" onclick="confirmSmplRequest();"/>
										<input type="button" class="btn_big_cancel" value="취소하기" id="" name="" onclick="c_submit('frm', 'sdpa004001l.do');" />
									</c:when>
									<c:otherwise>
										<input type="button" class="btn_big_order" value="수정하기" id="" name="" onclick="confirmSmplRequest();"/>
										<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpa004001d.do');" />
									</c:otherwise>
								</c:choose>
							</div>											
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