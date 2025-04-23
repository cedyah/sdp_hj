<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 사용자관리 - 사용자 정보 관리 수정 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	//비밀번호 변경
	function updatePassword() {
		//데이터 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		if($("#pw_new1").val() != $("#pw_new2").val()) {
			c_alert("신규 비밀번호와 비밀번호 확인을 동일하게 입력해 주십시오");
			return;
		}
		
		if($("#pw_old").val() == $("#pw_new1").val()) {
			c_alert("신규 비밀번호와 기존 비밀번호가 동일합니다.");
			return;
		}
		
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxUpdatePassword.do",
			data : {
				password : $("#pw_old").val()
				,newPassword : $("#pw_new1").val() 
			},
			datatype : "JSON",
			success : function(data) {
				if(data.result == "1") {
					c_alert("변경이 완료 되었습니다.");
					
					$("#pw_old").val("");
					$("#pw_new1").val("");
					$("#pw_new2").val("");
					
				} else if(data.result == "2") {
					$("#pw_old").val("");
					c_alert("비밀번호가 일치하지 않습니다.<br>비밀번호를 확인해 주십시오.");
					$("#pw_old").focus();
				}
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
						<h3 class="sub_tit">사용자관리</h3>
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
								<li><a href="sdpf004002u.do">알림 관리</a></li>
								<li class="on"><a href="#">비밀번호 변경</a></li>
							</ul>
						</div>
						
						<div class="mypage_setting">
								<p>※ 고객 정보</p>
									<div class="setting_area">
										<table class="tbl_input" summary="사용자관리 - 사용자 정보 관리 수정폼">
											<caption>사용자관리 - 사용자 정보 관리</caption>
											<colgroup>
												<col style="width:15%" />
												<col style="width:85%" />
											</colgroup>
											<tbody>
												<tr class="first">
													<th scope="row">고객ID</th>
													<td><c:out value="${sessionScope.user['cust_num']}"/></td>												
												</tr>					
												<tr>
													<th scope="row">고객명</th>
													<td>${sessionScope.user['cust_nm']}</td>												
												</tr>
												<tr>
													<th scope="row">E-mail</th>
													<td>${sessionScope.user['email']}</td>												
												</tr>
<!-- 												<tr> -->
<!-- 													<th scope="row">휴대전화</th> -->
<!-- 													<td><input type="text" /></td>											 -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 													<th scope="row">이메일</th> -->
<!-- 													<td><input type="text" /> @ <input type="text" /> -->
<!-- 														<select> -->
<!-- 															<option>s.jevi.co.kr</option> -->
<!-- 														</select> -->
<!-- 													</td>												 -->
<!-- 												</tr> -->
											</tbody>
										</table>										
									</div>
								
								<p>※ 비밀번호 변경</p>
									<div class="setting_area">
										<table class="tbl_input" summary="사용자관리 - 사용자 정보 관리 수정폼">
											<caption>사용자관리 - 사용자 정보 관리</caption>
											<colgroup>
												<col style="width:15%" />
												<col style="width:85%" />
											</colgroup>
											<tbody>
												<tr class="first">
													<th scope="row">기존비밀번호</th>
													<td><input type="password" id="pw_old" name="pw_old" placeholder="비밀번호를 입력해주세요" title="기존 비밀번호" req/></td>												
												</tr>					
												<tr>
													<th scope="row">신규비밀번호</th>
													<td><input type="password" id="pw_new1" name="pw_new1" placeholder="비밀번호를 입력해주세요" title="신규 비밀번호" req/></td>												
												</tr>
												<tr>
													<th scope="row">비밀번호확인</th>
													<td><input type="password" id="pw_new2" name="pw_new2" placeholder="비밀번호를 입력해주세요" title="비밀번호 확인" req/></td>											
												</tr>													
											</tbody>
										</table>										
									</div>
									
								<div class="bottom_btn_wrap">					
									<div class="btn_center_wrap">
										<input class="btn_big_order" type="button" id="" value="저장" onclick="updatePassword();" />
<!-- 											<input class="btn_big_cancel" type="button" id="" value="취소" onclick="" /> -->
									</div>
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