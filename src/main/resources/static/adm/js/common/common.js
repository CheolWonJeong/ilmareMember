function checkFileExt(file) {

 var file_path = file.value;
	var reg = /(.*?)\.(jpg|bmp|jpeg|png)$/;


        // 허용되지 않은 확장자일 경우

	if (file_path != "" && (file_path.match(reg) == null || reg.test(file_path) == false)) {

		if ($.browser.msie) { // ie 일때 

			file.parentNode.replaceChild(file.cloneNode(), file);

		} else {

			file.value = "";

		}

		

		alert("이미지 파일만 업로드 가능합니다.");

	}

}
