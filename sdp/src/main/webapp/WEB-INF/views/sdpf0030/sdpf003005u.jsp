<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 사은품 신청 상세 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//플래그 값에 따른 날짜 셋팅
		if("${flag}" == "insert") {
			setting_date();
			
		} else {
			$("#qty_ordered").val(commaSplit(${gift.amt != 0 && gift.amt != null ? gift.amt : 0} / 100000));
			$("#money_total").val(commaSplit("${gift.amt + customerVO.cust_point}"));
			$("#money_remainder").val(commaSplit("${customerVO.cust_point}"));
		}
		
		$("#repay_type").on("change", "", function() {
			var num1 = Number($("#repay_type option:selected").attr("title"));
			var num2 = Number($("#qty_ordered").val());
			if(num2 < 1) {
				$("#qty_ordered").val("1");
				num2 = 1;
			}
			
			var numX = num1 * num2;
			var numR = returnNum($("#money_total").val());
			
			if(numX > 0) {
				$("#amt").val(commaSplit(numX));
				$("#money_remainder").val(commaSplit(numR - numX));
			} else {
				$("#amt").val(0);
				$("#money_remainder").val(0);
			}
		});
		$("#qty_ordered").on("blur", "", function() {
			var num1 = Number($("#repay_type option:selected").attr("title"));
			var num2 = $("#qty_ordered").val();
			var numX = num1 * num2;
			var numR = returnNum($("#money_total").val());
			
			if(numX > 0) {
				$("#amt").val(commaSplit(numX));
				$("#money_remainder").val(commaSplit(numR - numX));
			} else {
				$("#amt").val(0);
				$("#money_remainder").val(0);
			}
		});
	});
	
	//날짜 셋팅
	function setting_date() {
		var today = new Date();
		$("#dt").val(dateToString(today, "."));
	}
	
	//사은품 신청서 작성 완료
	function confirm() {
		//한도 금액 초과 체크
		if(Number($("#money_remainder").val().replace(/,/gi, "")) < 0) {
			c_alert("신청 금액이 보유 포인트량을 초과 하였습니다");
			return;
		}
		
		if($("#amt").val() == null || $("#amt").val() == '' || $("#amt").val() == '0') {
			c_alert("신청 내역이 없습니다");
			return;
		}
		
		var text = "";
		
		if("${flag}" == "insert") {
			c_confirm("사은품을 신청하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					c_submit("frm", "sdpf003005u_insert.do");
				} else { //no Click
					return;
				}
			});

		} else {
			c_confirm("사은품 신청내역을 수정하시겠습니까?").then(function(result) { //커스텀 confirm
				if (result) { //yes Click
					c_submit("frm", "sdpf003005u_update.do");
				} else { //no Click
					return;
				}
			});
		}
		
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">사은품 신청</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>마이페이지</li>
								<li>사은품 신청</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->
					<div class="sub_cont">
						<!-- 상단 텝메뉴  -->
						<jsp:directive.include file="/WEB-INF/views/sdpf0030/sdpf0030_include.jsp" />
						
						<div class="order_subtit">사은품 신청</div>						
							<div class="tbl_wrap">
								<table class="tbl_basic" summary="주문서 등록 입력폼">
									<caption>주문서 등록</caption>
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
											<th scope="row">신청일자</th>
											<td><input type="text" class="ico_cal datepicker_aftToday readonly" id="dt" name="dt" value="${gift.dt}" readonly/></td>
											<th scope="row">신청가능금액</th>
											<td>
												<input type="text" class="readonly" style="text-align:right;" id="money_total" name="money_total"
													value="<fmt:formatNumber value="${customerVO.cust_point}" groupingUsed="true"/>" readonly/>
											</td>
											<th scope="row">사은품종류</th>
											<td class="last">
												<div class="selectDiv">
													<select id="repay_type" name="repay_type">
														<option value="">선택</option>
														<c:forEach items="${code01}" var="row" varStatus="status">
															<option value="${row.code}" title="100000" <c:if test="${row.code == gift.repay_type}">selected</c:if>>${row.name}</option>
														</c:forEach>
													</select>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row">신청수량</th>
											<td>
												<input type="text" class="entry" id="qty_ordered" name="qty_ordered" style="text-align:right;" value="0"/>
											</td>
											<th scope="row">신청금액</th>
											<td>
												<input type="text" class="readonly" id="amt" name="amt" style="text-align:right;" 
													value="<fmt:formatNumber value="${gift.amt}" groupingUsed="true"/>" readonly/>
											</td>
											<th scope="row">잔여금액</th>
											<td class="last">
												<input type="text" class="readonly" id="money_remainder" name="money_remainder" style="text-align:right;"
													value="0" readonly/>
											</td>
										</tr>
<!-- 										<tr> -->
<!-- 											<th scope="row">지급확정일</th> -->
<!-- 											<td><input type="text" class="readonly" id="fix_date" name="fix_date" readonly/></td> -->
<!-- 											<th scope="row">지급일</th> -->
<!-- 											<td class="last" colspan="3"><input type="text" class="readonly" id="pay_date" name="pay_date" readonly/></td> -->
<!-- 										</tr> -->
										<tr>
											<td colspan="6" class="tblin_comment">
												<p>1. 공지된 기간 중에 1회만 신청할 수 있습니다.</p>													
												<p>2. 10만원이상 10만원 단위로 신청할 수 있습니다.</p>
												<p>3. 잔여금액은 다음에 사용할 수 있습니다.</p>
												<p>4. 지급이 확정되면 수정이나 삭제할 수 없습니다.</p>																										
											</td>
										</tr>
										
									</tbody>
								</table>
							</div>
							
							<div class="btn_center_wrap">
								<c:choose>
									<c:when test="${flag == 'insert'}">
										<input type="button" class="btn_big_order" value="신청" onclick="confirm();" />
									</c:when>
									<c:when test="${flag == 'update'}">
										<input type="button" class="btn_big_order" value="수정" onclick="confirm();" />
									</c:when>
								</c:choose>
								<input type="button" class="btn_big_cancel" value="취소하기" onclick="c_submit('frm', 'sdpf003002l.do');"/>
							</div>
					
					</div>
					<!--// sub_cont-->
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