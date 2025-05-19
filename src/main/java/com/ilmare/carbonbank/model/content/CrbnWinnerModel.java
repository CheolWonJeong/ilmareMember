package com.ilmare.carbonbank.model.content;


import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Data;

/*
 * 당첨자
 */
@Builder
@Data
public class CrbnWinnerModel {

	private String docSeq;		//문서순번
	private String mbrId;		//회원ID
	private String creDtm;		//생성일시

}
