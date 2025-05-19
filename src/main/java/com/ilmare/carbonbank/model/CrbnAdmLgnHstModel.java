package com.ilmare.carbonbank.model;


import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 관리자 로그인 이력
 */
@Data
@NoArgsConstructor
public class CrbnAdmLgnHstModel {

	private String lgnSeq;        // 로그인순번
	private String crbnAdmId;        // 관리자아이디
	private String lgnIp      ;    //아이피
	private String lgnOs      ;    //OS
	private String lgnBrowser ;    //브라우져
	private String creDtm;           // 생성일시
	
}
