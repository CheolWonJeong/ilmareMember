package com.ilmare.carbonbank.model;


import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Data;

/*
 * 어드민 info
 */
@Builder @Data
public class CrbnAdmInfoModel {

	private String crbnAdmId;        // 관리자아이디
	private String CrbnAdmPwd;   // 비밀번호
	private String crbnAdmCellNum;   // 휴대폰번호
	private String crbnAdmNm;        // 이름
	private String partyGrp;     // 소속코드
	private String partyCd;     // 소속코드
	private String partyGrade;    // 소속 등급
	@NonNull @Builder.Default 	private String crbnEmail = "";    //이메일
	@NonNull @Builder.Default 	private String crbnDept = "";    //담당부서
	@NonNull @Builder.Default 	private String crbnPstn = "";    //직급
	@NonNull @Builder.Default 	private String chgPwdDtm = "";        // 패스워드 변경일
	@NonNull @Builder.Default 	private String lstLgnDtm = "";        // 마지막 로그인 일시
	@NonNull @Builder.Default 	private String creDtm = "";           // 생성일시
	@NonNull @Builder.Default 	private String chgDtm = "";           // 변경일시
}
