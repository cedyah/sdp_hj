/*******************************************************************************
 * 폼 검증
 ******************************************************************************/
function doFormValidate(form) {
	var IE=(document.all)?true:false;
	if (IE){
		for (var i = 0; i < form.all.length; i++) {
			
		//	alert( form.all(i) );
			
			switch (form.all(i).tagName.toLowerCase()) {
			case "textarea":
				if (!validate(form.all(i)))
					return false;
			case "select":
				if (!validate(form.all(i)))
					return false;
			case "input":
				if (form.all(i).name == "")
					continue;
				if (!validate(form.all(i)))
					return false;
			default:
				;
			}
		}		
	}else{
		for (var i = 0; i < form.length; i++) {
			switch (form.elements[i].tagName.toLowerCase()) {
			case "textarea":
				if (!validate(form.elements[i]))
					return false;
			case "select":
				if (!validate(form.elements[i]))
					return false;
			case "input":
				if (form.elements[i].name == "")
					continue;
				if (!validate(form.elements[i]))
					return false;
			default:
				;
			}
		}
	}

	return true;
}

/*******************************************************************************
 * 객체 검증
 ******************************************************************************/
function validate(obj) {
	var message = itemCheck(obj);
	if (message == "")
		return true;
	else {
		c_alert(message);
	}
}

/*******************************************************************************
 * 객체에대한 검증을 실행한다.(title)
 ******************************************************************************/
function itemCheck(obj) {
		var retMseeage = "";
		
		var title   = (obj.title == "" || typeof (obj.title) == "undefined" ? obj.name : obj.title);
		var value   = jfncTrim(obj.value);
		
		if ((obj.getAttribute("req") == "" ? true : false) && value == "") {
			retMseeage = title + "은(는) 필수입력 항목입니다.";
			obj.focus();
		}
		
		//파일첨부 확장자 검사
		if(obj.type == "file" && obj.value != ""){
			var imgArr = ['jpg', 'jpeg', 'bmp', 'png', 'gif'];
			var extArr = ['htm', 'html', 'asp', 'aspx', 'jsp', 'php', 'php3', 'java', 'class', 'xml', 'out', 'dll', 'exe', 'h', 'c', 'cpp', 'ocx', 'dat', 'js', 'sh'];
			var cmdArr = ['BS0620', 'BS0630', 'BS0720', 'BS0730'];
			var ext = obj.value.split('.').pop().toLowerCase();
			var cmd = window.location.search;
			cmd = cmd.substring(cmd.indexOf("=")+1);
			
			if($.inArray(cmd, cmdArr) == -1) {
				if($.inArray(ext, extArr) > -1) {
					retMseeage = "첨부할 수 없는 파일입니다.";
				}
			} else {
				if($.inArray(ext, imgArr) == -1) {
					retMseeage = "이미지 파일만 첨부 가능합니다.";
				}
			}
		}
		
		return retMseeage;
	}

/*******************************************************************************
 * 공백 제거
 ******************************************************************************/
function jfncTrim(str) {
	return str.replace(/^\str*/, '').replace(/\str*$/, '');
}

/*
 * 사업소검증
 */
function saupsoCheck(form) {
	if ( form.schSaupsoCd1.value == "9999" ) {
		alert("1차사업소은(는) 필수입력항목입니다.");
		form.schSaupsoCd1.focus();
		return false;
	} else {
		return true;
	}
}


function reqMessage2(_obj, item) {
	alert(item + "은(는) 필수입력 항목입니다.");
	_obj.focus();
}

function reqMessage1(item) {
	alert(item + "은(는) 필수입력 항목입니다.");
}
