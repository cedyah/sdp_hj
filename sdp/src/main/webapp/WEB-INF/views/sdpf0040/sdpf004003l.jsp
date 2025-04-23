<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 사용자관리 - 사용자 정보 관리 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
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
									<li class="on"><a href="#">검색조건관리</a></li>
									<li><a href="#">알림 관리</a></li>
									<li><a href="#">비밀번호 변경</a></li>
								</ul>
							</div>
							
							<div class="mypage_setting">
								<p>※ 고객님의 정보 확인 및 변경을 진행하실수 있습니다.</p>
									<div class="setting_area">
										<table class="tbl_input" summary="사용자관리 - 사용자 정보 관리">
											<caption>사용자관리 - 사용자 정보 관리</caption>
											<colgroup>
												<col style="width:15%" />
												<col style="width:85%" />
											</colgroup>
											<tbody>
												<tr class="first">
													<th scope="row">고객ID</th>
													<td>101011</td>												
												</tr>					
												<tr>
													<th scope="row">고객명</th>
													<td>홍길동페인트</td>												
												</tr>
												<tr>
													<th scope="row">전화번호</th>
													<td>02-1234-1234</td>											
												</tr>
												<tr>
													<th scope="row">휴대전화</th>
													<td>010-1234-1234</td>											
												</tr>
												<tr>
													<th scope="row">이메일</th>
													<td>hongkd@s.jevi.co.kr</td>												
												</tr>
											</tbody>
										</table>										
									</div>
									<div class="bottom_btn_wrap">					
										<div class="right_btn_area">
											<input class="btn_ord_cancel" type="button" id="" value="수정" onclick="" />
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