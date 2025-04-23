<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 외상정보 상세 -->
<div class="popup_layer_area" id="dialog_detailView" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area" style="width: 1000px; height:500px;">
			<div id="div_type1" style="display: none;">
				<div class="searchlist_wrap_tit">
					<table class="table_common">
						<colgroup>
							<col style="width: 80px;" />
							<col style="width: 60px;" />
							<col style="width: 50px;" />
							<col style="width: 120px;" />
							<col style="width: 240px;" />
							
							<col style="width: 50px;" />
							<col style="width: 50px;" />
							<col style="width: 80px;" />
							<col style="width: 80px;" />
							<col style="width: 80px;" />
							
							<col style="" />
						</colgroup>
						<thead>
							<tr>											
								<th scope="col">일자</th>																
								<th scope="col">전표번호</th>												
								<th scope="col">순번</th>												
								<th scope="col">제품코드</th>												
								<th scope="col">품명</th>
																				
								<th scope="col">단위</th>												
								<th scope="col">수량</th>												
								<th scope="col">단가</th>												
								<th scope="col">물품대</th>												
								<th scope="col">부가세</th>
																				
								<th scope="col">합계</th>												
							</tr>
						</thead>
					</table>
				</div>
	
				<div class="searchlist_wrap">
					<table class="table_common" summary="공지사항">
						<caption>공지사항</caption>
						<colgroup>
							<col style="width: 80px;" />
							<col style="width: 60px;" />
							<col style="width: 50px;" />
							<col style="width: 120px;" />
							<col style="width: 240px;" />
							
							<col style="width: 50px;" />
							<col style="width: 50px;" />
							<col style="width: 80px;" />
							<col style="width: 80px;" />
							<col style="width: 80px;" />
							
							<col style="" />
						</colgroup>
						<tbody id="tbody_contents">
							<tr><td class="pro_code txt_center" colspan="11">상세정보가 없습니다</td></tr>
						</tbody>
					</table>	
				</div>
			</div>
			
			<div id="div_type2" style="display: none;">
				<div class="searchlist_wrap_tit">
					<table class="table_common">
						<colgroup>
							<col style="width: 200px;" />
							<col style="width: 600px;" />
							<col style="" />
						</colgroup>
						<thead>
							<tr>											
								<th scope="col">일자</th>
								<th scope="col">적요</th>																
								<th scope="col">금액</th>												
							</tr>
						</thead>
					</table>
				</div>
	
				<div class="searchlist_wrap">
					<table class="table_common" summary="공지사항">
						<caption>공지사항</caption>
						<colgroup>
							<col style="width: 200px;" />
							<col style="width: 600px;" />
							<col style="" />
						</colgroup>
						<tbody id="tbody_contents">
							<tr><td class="pro_code txt_center" colspan="3">상세정보가 없습니다</td></tr>
						</tbody>
					</table>	
				</div>
			</div>
			
			<div id="div_type3" style="display: none;">
				<div class="searchlist_wrap_tit">
					<table class="table_common">
						<colgroup>
							<col style="width: 80px;" />
							<col style="width: 600px;" />
							<col style="width: 80px;" />							
							<col style="width: 80px;" />																
							<col style="width: 80px;" />

							<col style="" />
									
						</colgroup>
						<thead>
							<tr>											
								<th scope="col">월</th>
								<th scope="col">적요</th>																
								<th scope="col">물품대</th>												
								<th scope="col">부가세</th>												
								<th scope="col">수금</th>
								
								<th scope="col">기타잔고</th>
							</tr>
						</thead>
					</table>
				</div>
	
				<div class="searchlist_wrap">
					<table class="table_common" summary="공지사항">
						<caption>공지사항</caption>
						<colgroup>
							<col style="width: 80px;" />
							<col style="width: 600px;" />
							<col style="width: 80px;" />							
							<col style="width: 80px;" />																
							<col style="width: 80px;" />

							<col style="" />						
						</colgroup>
						<tbody id="tbody_contents">
							<tr><td class="pro_code txt_center" colspan="6">상세정보가 없습니다</td></tr>
						</tbody>
					</table>	
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 외상정보 상세 -->