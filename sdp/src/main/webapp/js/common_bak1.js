/**
/*
 * 왼쪽부터 해당 문자열 채우기 @s:객체 @c:채울문자 @n:자릿수
 */
function LPAD(s, c, n) {
	if (!s || !c || s.length >= n) {
		return s;
	}

	var max = (n - s.length) / c.length;
	for (var i = 0; i < max; i++) {
		s = c + s;
	}

	return s;
}

/*
 * 오른쪽부터 해당 문자열 채우기 @s:객체 @c:채울문자 @n:자릿수
 */
function RPAD(s, c, n) {
	if (!s || !c || s.length >= n) {
		return s;
	}

	var max = (n - s.length) / c.length;
	for (var i = 0; i < max; i++) {
		s += c;
	}

	return s;
}

/**
 * @param obj : 객체
 * @param type : int, float, string, num
 * @param pn : int, float(정수부 자리수), string(byte)
 * @param sn : int(사용안함), flaot(소수점 이하 자리수) , string( Y : 글자수 초과시 ""로 치환 / else :
 *            글자수 초과시 초과된 글자 자르기 )
 */
function inputCheck(obj, type, pn, sn) {
	var str = obj.value;

	if (type == "string") {
		var comp = "<`>";

		for (i = 0; i < str.length; i++) {
			if (comp.indexOf(str.substring(i, i + 1)) > -1) {
				alert("특수문자는 쓰실수 없습니다.[`,<,>]");
				str = str.replace("`", "");
				str = str.replace("<", "");
				str = str.replace(">", "");
				obj.value = str;
				return;
			}
		}

		var bytee = getByte(str);
		if (bytee > pn) {
			alert(pn + "Byte 이상 입력하실 수 없습니다.");
			str = fStr;

			if (sn == 'Y') {
				obj.value = '';
			} else {
				obj.value = cutStr(obj, pn);
			}

			return;
		}

	} else {
		if (event.keyCode == 32) {
			obj.value = str.replace(/\ /g, "");// 공백 제거
			return;
		} else {
			str = numberCheck(str, type, pn, sn);
			if (type == "num") {
				obj.value = str;
			} else {
				var reg = /(^[+-]?\d+)(\d{3})/;
				str += '';
				while (reg.test(str))
					str = str.replace(reg, '$1' + ',' + '$2');
				obj.value = str;
			}
		}
	}
	fStr = str;
}

/**
 * 숫자 체크
 * 
 * @param objVal
 * @param type
 * @param pn
 * @param sn
 * @returns {String}
 */
function numberCheck(objVal, type, pn, sn) {
	var str = objVal;
	var minus = "";
	if (str.charAt(0) == "-") {
		str = objVal.substring(1);
		minus = "-";
	}

	// 콤마 제거
	str = str.replace(/\,/g, "");

	if (str != "") {
		if (isNaN(str)) {
			alert("숫자만 입력하실수 있습니다.");
			if (isNaN(fStr)) {
				return "";
			}
			return fStr;
		}
	}

	if (type == "num") {
		// 자리수 체크
		if (str.length > pn) {
			alert(pn + "자리까지만 입력 가능합니다.");
			str = str.substring(0, pn);
		}
	}

	if (type == "int") {
		// 첫자리가 0체크
		if (str.length > 1) {
			if (str.charAt(0) == "0") {
				alert("첫번째 자리는 0으로 시작할 수 없습니다.");
				str = str.substring(1);
			}
		}
		// 소수점 체크
		if (str.indexOf(".") > -1) {
			alert("정수만 입력가능합니다.");
			str = str.replace(/\./g, "");
		}
		// 자리수 체크
		if (str.length > pn) {
			alert(pn + "자리까지만 입력 가능합니다.");
			str = str.substring(0, pn);
		}
	}

	if (type == "float") {
		var intNumber = str;
		var floatNumber = "";
		var dotYn = "";

		// 소수점이 있는지 체크
		if (str.indexOf(".") > -1) {
			dotYn = "Y";// 소수점이 존재
			intNumber = str.substring(0, str.indexOf("."));
			floatNumber = str.substring(str.indexOf(".") + 1, str.length);
		}

		// 정수자리수 체크
		if (intNumber.length > pn) {
			alert("정수는" + pn + "자리까지만 입력 가능합니다.");
			intNumber = intNumber.substring(0, pn);
		}

		// 소수점이하 자리수 체크
		if (floatNumber.length > sn) {
			alert("소수점 이하는 " + sn + "자리까지만 입력 가능합니다");
			floatNumber = floatNumber.substring(0, sn);
		}

		if (dotYn == "") {
			str = intNumber;
		} else {
			str = intNumber + "." + floatNumber;
		}
	}
	return minus + str;
}

/**
 * 입력받은 문자열의 byte 리턴
 * 
 * @param str
 * @returns {Number}
 */
function getByte(str) {
	var l = 0;
	for (var i = 0; i < str.length; i++)
		l += (str.charCodeAt(i) > 128) ? 2 : 1;
	return l;
}

/**
 * infocus시 inputbox css 변경
 */
function inFocus(i) {
	var id = document.getElementById(i.id);
	id.style.border = '1px solid #ff3d33';
	id.style.fontWeight = "bold";
}

/**
 * outFocus시 inputbox css 변경
 */
function outFocus(i) {
	var id = document.getElementById(i.id);
	id.style.border = '1px solid #dcdde3';
	id.style.fontWeight = "";
}

/**
 * rowspan tr color
 * 
 * @param tblId
 * @param obj
 * @param cnt
 * @param color
 */
function mouseAll(tblId, obj, cnt, color) {
	var idx = obj.rowIndex;
	// document.all['list_table'].rows[parseInt(idx/2)*2].style.background=color;

	// if (!obj.contains(event.toElement)) {
	// obj.style.cursor = "hand";
	for (var i = 0; i < Number(cnt); i++) {
		document.all[tblId].rows[parseInt(idx / Number(cnt)) * Number(cnt)
				+ Number(i)].style.background = color;
	}
	// }
}


// 소숫점 표시 val 값, precision 자릿수
function round(val, precision) {
	val = val * Math.pow(10, precision);
	val = Math.round(val);
	return val / Math.pow(10, precision);
}


// 절삭
function getCutNumber(num, place) {
	var returnNum;
	var str = "1";

	return Math.floor(num * Math.pow(10, parseInt(place, 10)))
			/ Math.pow(10, parseInt(place, 10));
}


function twoNumCheck(sObj, eObj, gb) {
	// alert(sObj + "//" + eObj + "//" + gb);
	if (gb == "S") {
		// alert(sObj.value.replace(/\./g,""));
		if (Number(sObj.value.replace(/\./g, "")) > Number(eObj.value.replace(
				/\./g, ""))) {
			eObj.value = sObj.value;
		}
	} else {
		if (Number(sObj.value.replace(/\./g, "")) > Number(eObj.value.replace(
				/\./g, ""))) {
			sObj.value = eObj.value;
		}
	}
}


// email체크
function fnEmailCheck(obj) {
	invalidchars = "/:,;";
	var email = obj.value;
	for (var i = 0; i < invalidchars.length; i++) {
		badchar = invalidchars.charAt(i);
		if (email.indexOf(badchar, 0) > -1) {
			// alert("사용할 수 없는 문자가 입력되었습니다. [" + badchar + "]")
			alert("올바른 이메일 형식이 아닙니다.");
			obj.value = "";
			obj.focus();
			return false;
		}
	}
	if (email != "") {
		atpos = email.indexOf("@", 1);
		if (atpos == -1) {
			alert("올바른 이메일 형식이 아닙니다.");
			obj.value = "";
			obj.focus();
			return false;
		}
		if (email.indexOf("@", atpos + 1) > -1) {
			alert("올바른 이메일 형식이 아닙니다.");
			obj.value = "";
			obj.focus();
			return false;
		}
		periodpos = email.indexOf(".", atpos);
		if (periodpos == -1) {
			alert("올바른 이메일 형식이 아닙니다.");
			obj.value = "";
			obj.focus();
			return false;
		}
		if (periodpos + 3 > email.length) {
			alert("올바른 이메일 형식이 아닙니다.");
			obj.value = "";
			obj.focus();
			return false;
		}
	}
	return true;
}


/*
 * 숫자체크
 */
function chkNum(obj) {
	if (isNum2(obj.value) == false) {
		alert('숫자만 입력하실수 있습니다.');
		obj.value = "";
		return;
	}
}


/*
 * 숫자체크
 */
function isNum2(str) {
	// if ( str.substring(0,1) == '-' ) {
	// str = str.substring(1,str.length);
	// }
	var len = str.length;
	var comp = "0123456789";

	for (var i = 0; i < len; i++) {
		if (comp.indexOf(str.substring(i, i + 1)) < 0) {
			return false;
		}
	}
	return true;
}


function fnScroll(no) {
	var x = document.all["div_list_" + no].scrollLeft;
	document.all["div_intitle_" + no].style.left = 0 - x;
}


/**
 * 선택된 체크박스 카운트 0인경우 에러 메시지 출력
 */
function checkYn(obj) {
	var checkCnt = 0;

	if (obj.length > 0) {
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				checkCnt += 1;
			}
		}
	} else {
		if (obj.checked) {
			checkCnt = 1;
		}
	}

	return checkCnt;
}


function MM_swapImgRestore() { // v3.0
	var i, x, a = document.MM_sr;
	for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
		x.src = x.oSrc;
}


function MM_preloadImages() { // v3.0
	var d = document;
	if (d.images) {
		if (!d.MM_p)
			d.MM_p = new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for (i = 0; i < a.length; i++)
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
	}
}

function MM_findObj(n, d) { // v4.01
	var p, i, x;
	if (!d)
		d = document;
	if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if (!(x = d[n]) && d.all)
		x = d.all[n];
	for (i = 0; !x && i < d.forms.length; i++)
		x = d.forms[i][n];
	for (i = 0; !x && d.layers && i < d.layers.length; i++)
		x = MM_findObj(n, d.layers[i].document);
	if (!x && d.getElementById)
		x = d.getElementById(n);
	return x;
}

function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for (i = 0; i < (a.length - 2); i += 3)
		if ((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if (!x.oSrc)
				x.oSrc = x.src;
			x.src = a[i + 2];
		}
}

/**
 * 첨부파일이 있는지 체크
 * @param mf
 * @returns {Boolean}
 */
function checkFile(mf) {
	for (var i = 0; i < mf.length; i++) {
		if (mf[i].value != "") {
			return true;
		}
	}
	return false;
}

/**
 * 체크박스 선택시 tr색상을 변경
 */
function checkSelected(obj) {
	if (typeof (obj) == "undefined") {
		return;
	} else {
		if (obj.length > 0) {
			for (var i = 0; i < obj.length; i++) {
				tr = obj[i].parentNode.parentNode;
				tr.style.backgroundColor = (obj[i].checked) ? "#ddffee" : "";
			}
		} else {
			tr = obj.parentNode.parentNode;
			tr.style.backgroundColor = (obj.checked) ? "#ddffee" : "";
		}
	}
}


/**
 * 특수문자 사용금지 스크립트 사용법 : onkeyup="special_check(this.name);
 */
function special_check(strElementName) {
	var ob = eval("document.form." + strElementName);
	var special = "'\"!,@#$%^&*_+<>;:";
	if (specialChars(ob, special)) {
		c_alert("특수문자는 사용하실수 없습니다");
		ob.value = "";
		return false;
	}

	return true;
}
function specialChars(str, chars) {
	for (var i = 0; i < str.value.length; i++) {
		if (chars.indexOf(str.value.charAt(i)) != -1) {
			return true;
		}
	}
	return false;
}


/*
 * 날짜에 '-'제거
 */
function delhypon(obj) {
	var str = obj.value;
	while (str.indexOf("-") > -1)
		str = str.replace("-", "");
	obj.value = str;
}

function addhypon(obj) {
	var str = returnNum(obj.value);
	// var returnValue = goDateChk(str);
	if (str.length > 0) {
		var tmpYear = str.substring(0, 4);
		var tmpMonth = str.substring(4, 6);
		var tmpDay = str.substring(6, 8);

		if (str.length == 8) {
			str = tmpYear + "-" + tmpMonth + "-" + tmpDay;
		} else if (str.length == 6) {
			str = tmpYear + "-" + tmpMonth;
		}

		obj.value = str;
	}
}

function addhyponVal(str) {
	// var returnValue = goDateChk(str);
	if (str.length > 0) {
		var tmpYear = str.substring(0, 4);
		var tmpMonth = str.substring(4, 6);
		var tmpDay = str.substring(6, 8);

		if (str.length == 8) {
			str = tmpYear + "-" + tmpMonth + "-" + tmpDay;
		} else if (str.length == 6) {
			str = tmpYear + "-" + tmpMonth;
		}

		return str;
	}
}

/*
 * 문자, 기호등을 제거한 숫자만 반환
 */
function returnNum(str) {
	var len = str.length;
	var comp = "0123456789";
	var temp = "";
	for (i = 0; i < len; i++) {
		if (comp.indexOf(str.substring(i, i + 1)) >= 0) {
			temp = temp + str.substring(i, i + 1);
		}
	}
	return temp;
}

/*
 * 3자리마다 콤마 (,)붙이기
 */
function commaSplit(srcNumber) {
	var txtNumber = '' + srcNumber;

	var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
	var arrNumber = txtNumber.split('.');
	arrNumber[0] += '.';
	do {
		arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
	} while (rxSplit.test(arrNumber[0]));

	if (arrNumber.length > 1) {
		return arrNumber.join('');
	} else {
		return arrNumber[0].split('.')[0];
	}
}


/*
 * ~원 단위 붙이기
 */
function addWon(fee) {
	if (fee == null || fee == "") {
		fee = 0;
	}
	return fee + "원";
}


/*
 * obj의 글자수를 limit 만큼 제한.
 */
function cutStr(obj, limit) {
	var strVal = obj.value;
	var tmpStr = strVal;
	var byte_count = 0;
	var len = tmpStr.length;
	var dot = '';

	for (var i = 0; i < len; i++) {
		byte_count += chr_byte(strVal.charAt(i));

		if (byte_count == limit - 1) {
			if (chr_byte(strVal.charAt(i + 1)) == 2) {
				tmpStr = strVal.substring(0, i + 1);
			} else {
				tmpStr = strVal.substring(0, i + 2);
			}
			break;

		} else if (byte_count == limit) {
			tmpStr = strVal.substring(0, i + 1);
			break;
		}

	}

	return tmpStr;
}


/*
 * 글자수,라인수제한
 */
function CheckStrLength(fr_nm, contents, remain, remain1, strlength) {
	// "CheckStrLength('form이름','textarea name','입력글자수','제한글자수',500);"

	var temp;
	var i = 0;
	var f = eval("document." + fr_nm + "." + contents);
	var r = eval("document." + fr_nm + "." + remain);
	var r1 = eval("document." + fr_nm + "." + remain1);
	var msglen = strlength; // 최대 길이
	var tmpstr = "";
	var enter = 0;
	var strlen;

	var eventkeycode = event.keyCode;

	// alert(eventkeycode);

	if (eventkeycode != 16 && eventkeycode != 17 && eventkeycode != 18
			&& eventkeycode != 33 && eventkeycode != 34 && eventkeycode != 37
			&& eventkeycode != 38 && eventkeycode != 39 && eventkeycode != 40
			&& eventkeycode != 35 && eventkeycode != 36) {
		// 초기 최대길이를 텍스트 박스에 뿌려준다.
		if (f.value.length == 0) {
			document.getElementById(remain1).innerHTML = msglen;
			document.getElementById(remain).innerHTML = 0;
		} else {
			for (k = 0; k <= f.value.length; k++) {
				temp = f.value.charAt(k);

				if (temp == '\n') {// 입력 라인 제한을 위해 엔터키 횟수 증가
					enter++;
				}

				// 한글이면 2를더하고, 기타는 1을 더한다.
				if (escape(temp).length > 4) { // 한글자씩 검사를 한다
					msglen -= 2; // 제한글자에서 입력한 단어수 빼기 작업(한글)
					r = i += 2;

					if (msglen < 0) {
						r = i -= 2;
					}
				} else {
					msglen--; // 제한글자에서 입력한 단어수 빼기 작업(기타)
					r = i++;
					if (msglen < 0) {
						r = --i;
					}
				}

				document.getElementById(remain).innerHTML = r;
				if (msglen < 0) {
					alert("총 영문 1000자 한글 500자 까지 쓰실 수 있습니다. \n 초과된 내용은 자동으로 삭제 됩니다.");
					f.value = tmpstr;
					break;
				} else if (enter > 4) {
					alert("라인수 5라인을 넘을수 없습니다.");
					enter = 0;
					strlen = tmpstr.length - 1;
					f.value = tmpstr.substring(0, strlen + 1);
					break;
				} else {
					tmpstr += temp; // 입력한 글자를 합친다.(나중에 입력한곳까지만 보여주기위한것)
				}
			}
		}
	}
}

/*
 * 글자수카운트
 */
function CheckStrLength_count(fr_nm, contents, remain) {
	// "CheckStrLength('form이름','textarea name','입력글자수','제한글자수',500);"
	var temp;
	var i = 0;
	var f = eval("document." + fr_nm + "." + contents);
	var r = eval("document." + fr_nm + "." + remain);
	var enter = 0;

	for (k = 0; k <= f.value.length; k++) {
		temp = f.value.charAt(k);

		if (temp == '\n') {// 입력 라인 제한을 위해 엔터키 횟수 증가
			enter++;
		}

		// 한글이면 2를더하고, 기타는 1을 더한다.
		if (escape(temp).length > 4) { //한글자씩 검사를 한다
			r = i += 2;
		} else {
			r = i++;
		}
		document.getElementById(remain).innerHTML = r;

	}

}