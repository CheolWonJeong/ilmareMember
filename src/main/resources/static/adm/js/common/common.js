function checkExtension(fileName, fileSize){

	var regex = new RegExp("(.*?)\.(jpg|jpeg|png|bmp)$");
	var maxSize = 5242880; //5MB


	if(fileSize >= maxSize){
	    alert("파일 사이즈 초과");
	    return false;
	}
	if(!regex.test(fileName)){
	    alert("해당 종류의 파일은 업로드할 수 없습니다.");
	    return false;
	}
	return true;

}

// 기존 소스 유지 (EnvNews)
function checkExtenstion(fileName, fileSize){

	var regex = new RegExp("(.*?)\.(jpg|jpeg|png|bmp)$");
	var maxSize = 5242880; //5MB


	if(fileSize >= maxSize){
	    alert("파일 사이즈 초과");
	    return false;
	}
	if(!regex.test(fileName)){
	    alert("해당 종류의 파일은 업로드할 수 없습니다.");
	    return false;
	}
	return true;

}
