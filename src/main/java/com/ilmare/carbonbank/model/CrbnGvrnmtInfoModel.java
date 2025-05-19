package com.ilmare.carbonbank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 지방자치단체
 */
@Data
@NoArgsConstructor
public class CrbnGvrnmtInfoModel {

	private String partyCd            ;        //소속코드                  
	private String gvrnmtRepNum       ;        //대표 번호                 
	private String gvrnmtNm           ;        //지방자치단체 명           
	private String gvrnmtCiImg        ;        //지방자치단체 CI 이미지경로
	private String gvrnmtAdmHome      ;        //지방자치단체 관리자홈피 명
	private String gvrnmtSvcInd       ;        //서비스 구분(Y:시작)       
	private String gvrnmtSvcStrtDtm   ;        //서비스 시작일시           
	private String gvrnmtSvcStopDtm   ;        //서비스 종료일시           
	private String creDtm;           // 생성일시
	private String chgDtm;           // 변경일시
	
}
