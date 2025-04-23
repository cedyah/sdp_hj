<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 관심그룹 추가 -->
<div class="popup_layer_area" id="dialog_addFavorite" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area">
			<select id="target_group_cd" name="target_group_cd" style="width: 100px;">
				<c:forEach items="${favGroupList}" var="row" varStatus="status">
               		<option value="${row.group_cd}">${row.group_cdnm}</option>
               	</c:forEach>
			</select> 그룹에 저장하시겠습니까?
		</div>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_ok" id="btn_confirm" value="확인"  onclick="javascript:addFavItem('');" />
		<input type="button" class="popbtn_cancel" value="취소" onclick="javascript:closeDialog('dialog_addFavorite');"/>
	</div>
</div>
<!-- 관심그룹 추가 -->