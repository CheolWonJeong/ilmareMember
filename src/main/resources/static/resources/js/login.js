$(document).ready(function(){
	
	//조회버튼 클릭
    $('#loginBtn').on('click', function(event, page) {
    	
    	if ($('#crbnAdmId').val() == '' ) {
    		alert('아이디를 입력하세요.');
    		return;
    	} 
    	
		if ($('#crbnAdmPwd').val() == '' ) {
    		alert('패스워드를 입력하세요.');
    		return;
    	} 

        var param = {
        		 "crbnAdmId" : $("#crbnAdmId").val()
                 , "crbnAdmPwd" : $("#crbnAdmPwd").val()
            };        
        
        $.ajax({
            url : '/adm/loginProc'
            , type : 'POST'
            , dataType : 'JSON'
            , cache : false
            , data : param
            , beforeSend : function( xhr, settings ) { 
                //loadingbar_show(); 
            }
            , success : function(data, stauts, request) {
				console.log(data);
				if (data.procInd == "S") {
					alert("로그인 성공 메인 페이지 링크");
					location.href = "/adm/main.do";
				} else {
					alert(data.errorMsg);
				}
            }
            , error : function(xhr, status) {
				console.log(status);
                alert('서비스 장애발생 관리자에게 문의해 주십시요');
            }
            , complete : function(xhr, status) {
                //loadingbar_hide();
            }    
        });
    });
    
    $('#IdPwd').on('click',function(){
        location.href = "/adm/pwdsearch.do";
    });
    
	$('#admReg').on('click',function(){
	    location.href = "/adm/admReg.do";
	});

	$('#admLogOut').on('click',function(){
	    location.href = "/adm/login.do";
	});

    //엔터시 검색
    $('#search_text').on('keydown', function(event, page) {
    	if (event.keyCode != 13) {
    		return;
    	}
    	$("#loginBtn").trigger('click', 1);
    });
});
