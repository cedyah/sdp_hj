<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="point_wrap">
	<div class="point_bg">									
		<div class="<c:out value="${menu_num == '1' ? 'order_point_on' : 'order_point'}"/>">
			주문 포인트
			<p class="<c:out value="${menu_num == '1' ? 'point_price_on' : 'point_price'}"/>">
				<a href="javascript:c_submit('frm','sdpf003002l.do');">
					<fmt:formatNumber value="${customerVO.cust_point}" groupingUsed="true"/> P
				</a>
			</p>									
		</div>
		<div class="<c:out value="${menu_num == '2' ? 'order_point_on' : 'order_point'}"/>">
			장려금 
			<p class="<c:out value="${menu_num == '2' ? 'point_price_on' : 'point_price'}"/>">
				<a href="javascript:c_submit('frm','sdpf003003l.do');">
					<fmt:formatNumber value="${customerVO.cust_subsidy}" groupingUsed="true"/> 원
				</a>
			</p>
		</div>
		<div class="<c:out value="${menu_num == '3' ? 'order_point_on' : 'order_point'}"/>" style="border:0;">
			화물탁송포인트
			<p class="<c:out value="${menu_num == '3' ? 'point_price_on' : 'point_price'}"/>">
				<a href="javascript:c_submit('frm','sdpf003004l.do');">
					<fmt:formatNumber value="${customerVO.cust_consignment_point}" groupingUsed="true"/> P
				</a>
			</p>
		</div>
	</div>								
</div>