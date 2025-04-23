<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 메세지 dialog -->
<div class="popup_layer_area" id="dialog_alert" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area" id="div_msg"></div>
	</div>
	<div class="popup_btn_wrap">
		<input type="button" class="popbtn_ok" id="" value="확인" onclick="closeDialog('dialog_alert');"/>
	</div>
</div>
<!-- 메세지 dialog -->

<!-- confirm dialog -->
<div class="popup_layer_area" id="dialog_confirm" style="display:none;">
	<div class="popup_layer_top">
		<div class="popup_text_area" id="div_msg"></div>
	</div>
	<div class="popup_btn_wrap">
<!-- 		<input type="button" class="popbtn_ok" id="btn_confirmTrue" value="확인" onclick=""/> -->
<!-- 		<input type="button" class="popbtn_cancel" id="btn_confirmFalse" value="취소" onclick="javascript:closeDialog('dialog_confirm');"/> -->
	</div>
</div>
<!-- confirm dialog -->


<!-- search_itemGroup dialog -->
<div class="popup_layer_area" id="dialog_searchItemGroup" style="display:none;">
	<ul>
		<li>
			<input class="search_checkbox" id="sChkBox01" name="uf_salegroup1" type="checkbox"/>
			<label class="search_label" for="sChkBox01" style="font-weight: bold;">중분류1</label>
		</li>
		<li>
			<input class="search_checkbox" id="sChkBox01" name="uf_salegroup1" type="checkbox"/>
			<label class="search_label" for="sChkBox01" style="font-weight: bold;">중분류2</label>
		</li>
		<li>
			<input class="search_checkbox" id="sChkBox01" name="uf_salegroup1" type="checkbox"/>
			<label class="search_label" for="sChkBox01" style="font-weight: bold;">중분류3</label>
		</li>
		<li>
			<input class="search_checkbox" id="sChkBox01" name="uf_salegroup1" type="checkbox"/>
			<label class="search_label" for="sChkBox01" style="font-weight: bold;">중분류4</label>
		</li>
		<li>
			<input class="search_checkbox" id="sChkBox01" name="uf_salegroup1" type="checkbox"/>
			<label class="search_label" for="sChkBox01" style="font-weight: bold;">중분류5</label>
		</li>
	</ul>
	
</div>
<!-- search_itemGroup dialog -->