package com.ilmare.carbonbank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 회원정보
 */
@Data
@NoArgsConstructor
public class CrbnMbrInfoModel {
	private String mbrId;           //회원아이디        
	private String mbrPwd;        //  비밀번호          
	private String mbrCellNum;  //    휴대폰번호        
	private String mbrNm;        //   이름              
	private String gvrnmtNum;  //     시도 코드         
	private String bisId;            //사업자아이디
	private String qrUseYn;       //  QR 사용가능여부   
	private String dgtQrCd;       //  디지털 QR코드     
	private String pprQrCd;       //  종이 QR코드       
	private String dtoreNo;       //  사업자 등록번호   
	private String chgPwdDtm;  //     패스워드 변경일   
	private String lstLgnDtm;    //   마지막 로그인 일시
	private String creDtm;        //  생성일시          
	private String chgDtm;       //   변경일시          

}
