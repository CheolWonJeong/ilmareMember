package com.ilmare.carbonbank.admin.mgr;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessInfo 
{
	private String CrbnAdmId;        // 관리자아이디
	private String CrbnAdmNm;        // 이름
	private String partyGrp;     // 소속그룹
	private String partyCd;     // 소속코드
	private String partyGrade;    // 소속 등급
	private String CrbnDept;    //담당부서
	private String CrbnPstn;    //직급
    private String LoginDtm;	//로그인 일시
	
}
