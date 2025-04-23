<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 사용자관리 - 알림 관리 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function () {

	});
	function changeButton(obj) {
		var li_alarm = $("input[name='alarm']");
		var set_alarm = "";

		if($(obj).val() == "켜짐") {
			$(obj).removeClass();
			$(obj).addClass("setting_btn_off");
			$(obj).val("꺼짐");
		} else {
			$(obj).removeClass();
			$(obj).addClass("setting_btn");
			$(obj).val("켜짐");
		}
		
		for(var i=0; i < li_alarm.length; i++) {
			if($(li_alarm[i]).val() == "켜짐") {
				set_alarm += $(li_alarm[i]).attr("id");
				set_alarm += ",";
			}
		}

		set_alarm = set_alarm.substring(0, set_alarm.length - 1);
		
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxSetAlarm.do",
			data : {
				set_alarm : set_alarm
			},
			datatype : "JSON",
			success : function(data) {
				
			},
			error : function(xhr, status, error) {
				alert("error");	
			}
		});
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
						<h3 class="sub_tit">사용자 관리</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>마이페이지</li>
								<li>사용자관리</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->
					<div class="sub_cont">
						<div class="mypage_tab_wrap">
							<ul>
								<li><a href="sdpf004001u.do">검색조건관리</a></li>
								<li class="on"><a href="#">알림 관리</a></li>
								<li><a href="sdpf004003u.do">비밀번호 변경</a></li>
							</ul>
						</div>
						
						<div class="mypage_setting">
							<p>※ 설정된 조건은 알림 전송시 사용됩니다.</p>
								<div class="setting_area">
									<table class="tbl_input" summary="사용자관리 - 알림 관리">
										<caption>사용자관리 - 알림 관리</caption>
										<colgroup>
											<col style="width:35%" />
											<col style="width:55%" />
											<col style="width:10%" />
										</colgroup>
										<tbody>
											<tr class="first">
												<th scope="row" rowspan="3">배달과정 메시지 수신 옵션</th>
												<td>창고 출발</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'A') >= 0}">
															<input type="button" name="alarm" id="A" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="A" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>													
											</tr>					
											<tr>
												<td>중간기착지 통과</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'B') >= 0}">
															<input type="button" name="alarm" id="B" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="B" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>													
											</tr>
											<tr>
												<td>예정시간 변경</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'C') >= 0}">
															<input type="button" name="alarm" id="C" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="C" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>													
											</tr>
											<tr>
												<th scope="row" rowspan="4">인터넷 주문 시스템 메시지 수신 옵션</th>
												<td>인터넷 주문</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'D') >= 0}">
															<input type="button" name="alarm" id="D" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="D" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>													
											</tr>
											<tr>
												<td>영업부 발행</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'E') >= 0}">
															<input type="button" name="alarm" id="E" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="E" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											<tr>
												<td>영업부 처리</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'F') >= 0}">
															<input type="button" name="alarm" id="F" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="F" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>													
											</tr>
											<tr>
												<td>배차 대기</td>
												<td>
													<c:choose>
														<c:when test="${fn:indexOf(customerVO.set_alarm, 'G') >= 0}">
															<input type="button" name="alarm" id="G" value="켜짐" class="setting_btn" onclick="changeButton(this);"/>
														</c:when>
														<c:otherwise>
															<input type="button" name="alarm" id="G" value="꺼짐" class="setting_btn_off" onclick="changeButton(this);"/>
														</c:otherwise>
													</c:choose>
												</td>													
											</tr>
										</tbody>
									</table>										
								</div>
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