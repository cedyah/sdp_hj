<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="quickmenu" id="quickmenu">
	<div class="toggle" id="toggle">
		<input type="button" value=">" onclick="toggleQuickMenu(this);"/>
	</div>
	<div class="menu" id="menu">
		<ul>
			<!-- li class=""><a href="javascript:login();">Login</a></li-->
			<li style="background-color: #525c60;"><a href="javascript:logout();" style="font: bold;">로그아웃</a></li>
			<li><a href="sdpa001001l.standard.do">재고조회</a></li>
			<!-- <li><a href="sdpa002001u.insert.do">주문서등록</a></li>
			<li><a href="sdpf002001l.do">장바구니</a></li>
			<li><a href="sdpf001001l.do">관심품목</a></li> -->
			<li><a href="sdpf004003u.do">비밀번호 변경</a></li>
			<li class="movetop"><a href="#top">TOP</a></li>
			<li class="movedown"><a href="#down">DOWN</a></li>
		</ul>
	</div>
</div>

<footer>
	<div class="foot_inner">
		<ul class="foot_util">
			<li><a href="javascript:fncNewWindow('http://www.HANJINcoatings.com');">한진화학 홈페이지</a></li>
			<!-- <li><a href="javascript:fncNewWindow('http://s.jebi.co.kr/main');">한진화학 이메일</a></li>
			<li><a href="javascript:fncNewWindow('http://ok.jevisco.com:8080/index.do');">한진화학 컬러시스템</a></li> -->
		</ul>
		<p>Copyright &copy; 1995-2024 HANJIN Coatings All Rights reserved.</p>
		<div class="family_site" style="height:44px;">
			<a href="#">Family site</a>
			<ul>
				<li><a href="javascript:fncNewWindow('http://www.hjci.co.kr');">한진화학</a></li>
				<!-- <li><a href="javascript:fncNewWindow('http://www.kangnamchem.com');">강남화성</a></li>
				<li><a href="javascript:fncNewWindow('http://www.kangnamship.co.kr');">(주)강남</a></li>
				<li><a href="javascript:fncNewWindow('http://www.jevisco.com');">강남제비스코</a></li>
				<li><a href="javascript:fncNewWindow('http://www.kangnamkpi.com/kor/ppage/main/index.do');">강남KPI</a></li>
				<li><a href="javascript:fncNewWindow('http://www.munhwapen.co.kr/kor/page/main/index.do');">문화연필</a></li>
				<li><a href="javascript:fncNewWindow('http://www.kangnamcon.com/kor/ppage/main/index.do');">강남건영</a></li>
				<li><a href="javascript:fncNewWindow('http://www.knitec.co.kr/kor/ppage/main/index.do');">강남아이텍</a></li> -->
			</ul>
		</div>
		<!--family_site-->
	</div>
	<!--foot_inner-->
</footer>

<jsp:directive.include file="/WEB-INF/views/templates/dialog_common.jsp" />