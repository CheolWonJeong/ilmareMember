package com.ilmare.carbonbank.model.content;



/*
 * 뉴스 공통
 */
public class CrbnStoreInfoModel {

	private String STORE_ID;          // 가맹점아이디
	private String PARTY_CD;          // 소속코드
	private String BIS_NUM;           // 사업자 등록번호
	private String STORE_PWD;         // 비밀번호
	private String STORE_CELL_NUM;    // 휴대폰번호
	private String STORE_CEO_NM;      // 대표자 성명
	private String STORE_NM; 		  // 상호
	private String STORE_LTTD_LNGTD;  //  위도경도(위도,경도)
	private String STORE_NO_IMG;      // 사업자등록증 이미지 파일 경로
	private String STORE_IMG;         // 사업장 사진 이미지 파일 경로
	private String STORE_EMAIL;       // 메일주소
	private String STORE_ADDR;        // 사업장 주소
	private String STORE_EVENT;       // 할인행사
	private String CHG_PWD_DTM;       // 패스워드 변경일
	private String LST_LGN_DTM;       // 마지막 로그인 일시
  
	private String creDtm; // 생성일시
	private String chgDtm; // 변경일시

		
	//조회
	private int pageNo  = 1;	//조회시작 위치 
	private int listSize  ;		//조회 건수  
	private int totalCount  ;	//총 건수  

	private String searchType;
	private String searchValue;
	public String getSTORE_ID() {
		return STORE_ID;
	}
	public void setSTORE_ID(String sTORE_ID) {
		STORE_ID = sTORE_ID;
	}
	public String getPARTY_CD() {
		return PARTY_CD;
	}
	public void setPARTY_CD(String pARTY_CD) {
		PARTY_CD = pARTY_CD;
	}
	public String getBIS_NUM() {
		return BIS_NUM;
	}
	public void setBIS_NUM(String bIS_NUM) {
		BIS_NUM = bIS_NUM;
	}
	public String getSTORE_PWD() {
		return STORE_PWD;
	}
	public void setSTORE_PWD(String sTORE_PWD) {
		STORE_PWD = sTORE_PWD;
	}
	public String getSTORE_CELL_NUM() {
		return STORE_CELL_NUM;
	}
	public void setSTORE_CELL_NUM(String sTORE_CELL_NUM) {
		STORE_CELL_NUM = sTORE_CELL_NUM;
	}
	public String getSTORE_CEO_NM() {
		return STORE_CEO_NM;
	}
	public void setSTORE_CEO_NM(String sTORE_CEO_NM) {
		STORE_CEO_NM = sTORE_CEO_NM;
	}
	public String getSTORE_NM() {
		return STORE_NM;
	}
	public void setSTORE_NM(String sTORE_NM) {
		STORE_NM = sTORE_NM;
	}
	public String getSTORE_LTTD_LNGTD() {
		return STORE_LTTD_LNGTD;
	}
	public void setSTORE_LTTD_LNGTD(String sTORE_LTTD_LNGTD) {
		STORE_LTTD_LNGTD = sTORE_LTTD_LNGTD;
	}
	public String getSTORE_NO_IMG() {
		return STORE_NO_IMG;
	}
	public void setSTORE_NO_IMG(String sTORE_NO_IMG) {
		STORE_NO_IMG = sTORE_NO_IMG;
	}
	public String getSTORE_IMG() {
		return STORE_IMG;
	}
	public void setSTORE_IMG(String sTORE_IMG) {
		STORE_IMG = sTORE_IMG;
	}
	public String getSTORE_EMAIL() {
		return STORE_EMAIL;
	}
	public void setSTORE_EMAIL(String sTORE_EMAIL) {
		STORE_EMAIL = sTORE_EMAIL;
	}
	public String getSTORE_ADDR() {
		return STORE_ADDR;
	}
	public void setSTORE_ADDR(String sTORE_ADDR) {
		STORE_ADDR = sTORE_ADDR;
	}
	public String getSTORE_EVENT() {
		return STORE_EVENT;
	}
	public void setSTORE_EVENT(String sTORE_EVENT) {
		STORE_EVENT = sTORE_EVENT;
	}
	public String getCHG_PWD_DTM() {
		return CHG_PWD_DTM;
	}
	public void setCHG_PWD_DTM(String cHG_PWD_DTM) {
		CHG_PWD_DTM = cHG_PWD_DTM;
	}
	public String getLST_LGN_DTM() {
		return LST_LGN_DTM;
	}
	public void setLST_LGN_DTM(String lST_LGN_DTM) {
		LST_LGN_DTM = lST_LGN_DTM;
	}
	public String getCreDtm() {
		return creDtm;
	}
	public void setCreDtm(String creDtm) {
		this.creDtm = creDtm;
	}
	public String getChgDtm() {
		return chgDtm;
	}
	public void setChgDtm(String chgDtm) {
		this.chgDtm = chgDtm;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}		
		
}
