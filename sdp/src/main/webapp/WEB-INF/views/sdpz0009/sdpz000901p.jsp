<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 로그인  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<title>한진화학 주문관리 시스템</title>
</head>

<body class="wrap_loginbg">
		<div class="login_wrap">
			<div class="login_logo">
				<h1 class="logo"></h1>
				<p>한진화학 주문관리 포털시스템 입니다.</p>
			</div>
			<div class="login_box_wrap">
				<select>						
					<option>대리점</option>
					<option>직영점</option>
					<option>영업부</option>
					<option>관제시스템</option>
				</select>
				<input type="text" class="loginbox" placeholder="로그인" />
				<input type="password" class="loginpw" placeholder="비밀번호"/>
				<input type="button" value="로그인" class="loginbtn" />
			</div>				
			<p class="lostpw"><a href="#">비밀번호를 잃어버리셨습니까? 비밀번호 찾기</a></p>
		</div>
</body>
</html>