package com.ilmare.carbonbank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 기업(대리점, 스폰서)
 */
@Data
@NoArgsConstructor
public class CrbnBisInfoModel {

	private String partyCd;        // 소속코드
	private String bisNum;       //사업자 등록번호 
	private String bisNm;   //상호 
	private String bisRepNum;        // 대표 번호
	private String agencyCeoNm;     // 대표자 성명
	private String creDtm;           // 생성일시
	private String chgDtm;           // 변경일시
	
}
