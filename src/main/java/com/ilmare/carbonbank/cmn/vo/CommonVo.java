package com.ilmare.carbonbank.cmn.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonVo {
	private String docSeq;		//문서순번
	private String partyCd;		//소속코드
	private String docTitle;		//제목
	private String docInfo;		//문서내용
	private String docStat;		//문서상태(N:등록, V:게시, C:게시취소)
	private String docRead ;		//읽은건수
	private String auditId ;		//등록자ID
	private String creDtm;		//생성일시	
	private String showId ;		//게시 아이디
	private String showDtm ;		//게시일시
	private String cancelId ;		//게시취소 아이디
	private String cancelDtm ;		//게시취소일시
	private String delYn ;		//삭제여부
	private String delId ;		//삭제 아이디
	private String delDtm ;		//삭제일시

	private String docStatText;		//문서상태(N:등록, V:게시, C:게시취소)
	private String upDate;		//변경일자

	//페이지 처리위한 코드
	private Integer   pageNo;		//
	private Integer   listSize;		//
	private Integer   totalCount;		//
	private String   searchType;		//
	private String   searchValue;		//
	
	private String qnaSort;   // FAQ 중분류
	private String qnContent; // FAQ 질문
	private String qaContent; // FAQ 답변
	
	
	private String storeId;
	//private String partyCd;
	private String bisNum;
	private String storePwd;
	private String storeCellNum;
	private String storeCeoNm;
	private String storeNm;
	  
	private String storeLatitude;
	private String storeLongitude;
	  
	private String storeNoImg;
	private String storeImg;
	private String storeEmail;
	private String storeAddr;
	private String storeEvent;
	
	private String ansContent;	

}
