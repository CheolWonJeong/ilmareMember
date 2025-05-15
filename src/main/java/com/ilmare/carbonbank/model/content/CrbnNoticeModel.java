package com.ilmare.carbonbank.model.content;

import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Data;

/*
 * 공지사항
 */
@Builder
@Data
public class CrbnNoticeModel {

	private String docSeq;		//문서순번
	private String partyCd;		//소속코드
	private String docTitle;		//제목
	@NonNull @Builder.Default 	private String docInfo = "";		//문서내용
	private String docStat;		//문서상태(N:등록, V:게시, C:게시취소)
	@NonNull @Builder.Default 	private String docRead = "";		//읽은건수
	@NonNull @Builder.Default 	private String auditId = "";		//등록자ID
	private String creDtm;		//생성일시	
	@NonNull @Builder.Default 	private String showId = "";		//게시 아이디
	@NonNull @Builder.Default 	private String showDtm = "";		//게시일시
	@NonNull @Builder.Default 	private String cancelId = "";		//게시취소 아이디
	@NonNull @Builder.Default 	private String cancelDtm = "";		//게시취소일시
	@NonNull @Builder.Default 	private String delYn = "";		//삭제여부
	@NonNull @Builder.Default 	private String delId = "";		//삭제 아이디
	@NonNull @Builder.Default 	private String delDtm = "";		//삭제일시

}
