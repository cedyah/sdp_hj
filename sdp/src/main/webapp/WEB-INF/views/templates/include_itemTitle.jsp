<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${sessionScope.user['workplace'] == '1'}"><!-- 부산 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">함안</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '2'}"><!-- 서울 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">-</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '3'}"><!-- 안양 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">-</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '4'}"><!-- 호남 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">안양</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '5'}"><!-- 중부 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">안양</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '6'}"><!-- 함안 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">-</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '7'}"><!-- 대구 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">함안</th>
	</c:when>
	<c:otherwise><!-- 기타 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">-</th>
	</c:otherwise>
</c:choose>
<c:if test="${flag != 'detail'}">
	<th class="tbl_row_tit">보관품</th>
</c:if>