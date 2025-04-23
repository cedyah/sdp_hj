<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 비밀번호 찾기 팝업 -->
<div class="popup_login_area" id="dialog_findPw" style="display:none;">
	<div class="popup_login_top">
		<ul>
			<li><span>고객 ID</span> <input type="text" class="" id="find_cust_num" name="find_cust_num" style="width:200px"/></li>
			<li><span>휴대전화</span> <input type="text" class="" id="find_phone" name="find_phone" style="width:200px"/></li>							
		</ul>
		<p>고객님의 ID에 등록된 대표 휴대전화로<br/>초기화된 비밀번호가 전송됩니다.</p>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_ok" value="비밀번호 찾기" onclick="findPassword();"/>
		<input type="button" class="popbtn_cancel" value="닫기" onclick="closeDialog('dialog_findPw');"/>
	</div>
</div>
<!-- 비밀번호 찾기 팝업 -->