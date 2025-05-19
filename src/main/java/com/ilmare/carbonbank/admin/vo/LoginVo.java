package com.ilmare.carbonbank.admin.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginVo
{
	private String CrbnAdmId;        // 관리자아이디
	private String CrbnAdmNm;        // 이름
	private String CrbnAttachCd;     // 소속코드
	private String CrbnattachGrade;    // 소속 등급
	private String CrbnDept;    //담당부서
	private String CrbnPstn;    //직급
    private String LoginDtm;	//로그인 일시
	private String lgnIp      ;    //아이피
	private String lgnOs      ;    //OS
	private String lgnBrowser ;    //브라우져
	
}
