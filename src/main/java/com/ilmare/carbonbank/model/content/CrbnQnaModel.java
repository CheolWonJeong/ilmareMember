package com.ilmare.carbonbank.model.content;


import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Data;

/*
 * 일대일문의하기
 */
@Builder
@Data
public class CrbnQnaModel {

	private String docSeq;		//문서순번
	private String partyCd;		//소속코드
	@NonNull @Builder.Default 	private String mbrId = "";		//회원ID
	@NonNull @Builder.Default 	private String qnaPwd = "";		//비밀번호
	@NonNull @Builder.Default 	private String qnaSort = "";		//중분류
	@NonNull @Builder.Default 	private String qnaTitle = "";		//제목
	@NonNull @Builder.Default 	private String qnaContent = "";		//내용
	private String qnaInd;		//공개/비공개 여부(1:공개, 2:비공개)
	@NonNull @Builder.Default 	private String docRead = "";		//읽은건수
	private String creDtm;		//생성일시	
	@NonNull @Builder.Default 	private String chgDtm = "";		//수정일시
	@NonNull @Builder.Default 	private String delId = "";		//삭제 아이디
	@NonNull @Builder.Default 	private String ansContent = "";		//답변
	@NonNull @Builder.Default 	private String ansDtm = "";		//답변일시

}
