<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 관심그룹 관리 팝업 -->
<div class="popup_layer_area" id="dialog_manageFavGroup" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_search_area">
			<input type="text" class="pop_search" id="" title="추가할 그룹명을 적어주세요" placeholder="추가할 그룹명을 적어주세요 (15자 이내)" maxlength="15"/>
			<input type="button" class="popbtn_add" value="추가" onclick="javascript:addFavGroup(this);" />
		</div>
		<ul id="ul_favGroup" style="height: 300px; overflow: auto;">
			<c:forEach items="${favGroupList}" var="row" varStatus="status">
				<li id="li_${row.group_cd}">
					<input type="hidden" id="hid_group_cdnm" value="${row.group_cdnm}" />
					<!--  일반 목록  -->
					<div id="div_select" style="">
						<span id="span_group_cdnm">${row.group_cdnm}</span>
						<c:if test="${row.group_seq != '0'}" >
							<span class="popup_group_btnarea">
								<input type="button" class="popbtn_modi" value="수정" title="수정" onclick="javascript:divChange(this, true);"/>
								<input type="button" class="popbtn_del" value="삭제" title="삭제" id="btn_delFavGroup"
									onclick="javascript:dialog_delFavGroup('${row.group_cd}');"/>
							</span>
						</c:if>
					</div>

					<!--  수정 아이콘 클릭시  -->
					<div id="div_update" style="display:none;">
						<input type="text" class="pop_grouplist" id="ipt_group_cdnm" name="" onkeydown="" />
						<span class="popup_group_btnarea">
							<input type="button" class="popbtn_save" value="저장" onclick="javascript:updateFavGroup(this);"/>
							<input type="button" class="popbtn_cancel2" value="취소" onclick="javascript:divChange(this, false);" />
						</span>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_cancel" value="닫기" onclick="javascript:closeManageFavGroupDialog();"/>
	</div>
</div>
<!-- 관심그룹 팝업 -->


<!-- 관심그룹 팝업 행추가용 Div -->
<div id="div_appendRow" style="display: none;">
	<li id="">
		<input type="hidden" id="hid_group_cdnm" value="" />
		<!--  일반 목록  -->
		<div id="div_select" style="">
			<span id="span_group_cdnm"></span>
			<span class="popup_group_btnarea">
				<input type="button" class="popbtn_modi" value="수정" title="수정" onclick="javascript:divChange(this, true);"/>
				<input type="button" class="popbtn_del" value="삭제" title="삭제" id="btn_delFavGroup"
					onclick=""/>
			</span>
		</div>

		<!--  수정 아이콘 클릭시  -->
		<div id="div_update" style="display:none;">
			<input type="text" class="pop_grouplist" id="ipt_group_cdnm" name="" onkeydown="" />
			<span class="popup_group_btnarea">
				<input type="button" class="popbtn_save" value="저장" onclick="javascript:updateFavGroup(this);"/>
				<input type="button" class="popbtn_cancel2" value="취소" onclick="javascript:divChange(this, false);" />
			</span>
		</div>
	</li>
</div>
<!-- 관심그룹 팝업 행추가용 Div -->



<!-- 관심그룹 관리 팝업 // 이동알림 -->
<div class="popup_layer_area" id="dialog_moveFavItem" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area">
			<select id="target_group_cd" name="target_group_cd">
				<c:choose>
					<c:when test="${favGroupVO.select_group_cd == ''}">
						<c:forEach items="${favGroupList}" var="row" varStatus="status" begin="1">
							<c:if test="${favGroupVO.select_group_cd != row.group_cd}" >
			               		<option value="${row.group_cd}">${row.group_cdnm}</option>
							</c:if>
		               	</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${favGroupList}" var="row" varStatus="status">
							<c:if test="${favGroupVO.select_group_cd != row.group_cd}" >
			               		<option value="${row.group_cd}">${row.group_cdnm}</option>
							</c:if>
		               	</c:forEach>
					</c:otherwise>
				</c:choose>
			</select> 으로 이동하시겠습니까?
		</div>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_ok" value="확인"  onclick="javascript:alterFavItem(this, 'move');" />
		<input type="button" class="popbtn_cancel" value="취소" onclick="javascript:closeDialog('dialog_moveFavItem');"/>
	</div>
</div>
<!-- 관심그룹 팝업 // 이동알림 -->

<!-- 관심그룹 관리 팝업 // 복사알림 -->
<div class="popup_layer_area" id="dialog_copyFavItem" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area">
			<select id="target_group_cd" name="target_group_cd" >
				<c:choose>
					<c:when test="${favGroupVO.select_group_cd == ''}">
						<c:forEach items="${favGroupList}" var="row" varStatus="status" begin="1">
							<c:if test="${favGroupVO.select_group_cd != row.group_cd}" >
			               		<option value="${row.group_cd}">${row.group_cdnm}</option>
							</c:if>
		               	</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${favGroupList}" var="row" varStatus="status">
							<c:if test="${favGroupVO.select_group_cd != row.group_cd}" >
			               		<option value="${row.group_cd}">${row.group_cdnm}</option>
							</c:if>
		               	</c:forEach>
					</c:otherwise>
				</c:choose>
			</select> 으로 복사하시겠습니까?
		</div>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_ok" value="확인" onclick="javascript:alterFavItem(this, 'copy');" />
		<input type="button" class="popbtn_cancel" value="취소" onclick="javascript:closeDialog('dialog_copyFavItem');" />
	</div>
</div>
<!-- 관심그룹 팝업 // 복사알림 -->

<!-- 관심그룹 관리 팝업 // 삭제알림 -->
<div class="popup_layer_area" id="dialog_delFavGroup" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area">해당 관심그룹을 삭제하시겠습니까?<br>삭제된 그룹의 품목들은 기본 그룹으로 이동 됩니다.</div>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_ok" id="btn_confirmDel" value="확인" onclick=""/>
		<input type="button" class="popbtn_cancel" value="취소" onclick="closeDialog('dialog_delFavGroup');" />
	</div>
</div>