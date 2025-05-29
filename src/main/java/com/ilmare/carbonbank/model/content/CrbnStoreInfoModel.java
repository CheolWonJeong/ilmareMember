package com.ilmare.carbonbank.model.content;



/*
 * 뉴스 공통
 */
public class CrbnStoreInfoModel {

	private String storeId;          // 가맹점아이디
	private String partyCd;          // 소속코드
	private String bisNum;           // 사업자 등록번호
	private String storePwd;         // 비밀번호
	private String storeCellNum;    // 휴대폰번호
	private String storeCeoNm;      // 대표자 성명
	private String storeNm; 		  // 상호
	//private String STORE_LTTD_LNGTD;  //  위도경도(위도,경도)
	private String storeLatitude;  //  위도경도(위도,경도)
	private String storeLongitude;  //  위도경도(위도,경도)
	
	private String storeNoImg;      // 사업자등록증 이미지 파일 경로
	private String storeImg;         // 사업장 사진 이미지 파일 경로
	private String befStoreNoImg;      // 사업자등록증 이미지 파일 경로
	private String befStoreImg;         // 사업장 사진 이미지 파일 경로
	private String storeEmail;       // 메일주소
	private String storeAddr;        // 사업장 주소
	private String storeEvent;       // 할인행사
	private String chgPwdDtm;       // 패스워드 변경일
	private String lstLgnDtm;       // 마지막 로그인 일시
  
	private String creDtm; // 생성일시
	private String chgDtm; // 변경일시
		
	//조회
	private int pageNo  = 1;	//조회시작 위치 
	private int listSize  ;		//조회 건수  
	private int totalCount  ;	//총 건수  

	private String searchType;
	private String searchValue;
	
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getPartyCd() {
		return partyCd;
	}
	public void setPartyCd(String partyCd) {
		this.partyCd = partyCd;
	}
	public String getBisNum() {
		return bisNum;
	}
	public void setBisNum(String bisNum) {
		this.bisNum = bisNum;
	}
	public String getStorePwd() {
		return storePwd;
	}
	public void setStorePwd(String storePwd) {
		this.storePwd = storePwd;
	}
	public String getStoreCellNum() {
		return storeCellNum;
	}
	public void setStoreCellNum(String storeCellNum) {
		this.storeCellNum = storeCellNum;
	}
	public String getStoreCeoNm() {
		return storeCeoNm;
	}
	public void setStoreCeoNm(String storeCeoNm) {
		this.storeCeoNm = storeCeoNm;
	}
	public String getStoreNm() {
		return storeNm;
	}
	public void setStoreNm(String storeNm) {
		this.storeNm = storeNm;
	}
	public String getStoreLatitude() {
		return storeLatitude;
	}
	public void setStoreLatitude(String storeLatitude) {
		this.storeLatitude = storeLatitude;
	}
	public String getStoreLongitude() {
		return storeLongitude;
	}
	public void setStoreLongitude(String storeLongitude) {
		this.storeLongitude = storeLongitude;
	}
	public String getStoreNoImg() {
		return storeNoImg;
	}
	public void setStoreNoImg(String storeNoImg) {
		this.storeNoImg = storeNoImg;
	}
	public String getStoreImg() {
		return storeImg;
	}
	public void setStoreImg(String storeImg) {
		this.storeImg = storeImg;
	}
	public String getStoreEmail() {
		return storeEmail;
	}
	public void setStoreEmail(String storeEmail) {
		this.storeEmail = storeEmail;
	}
	public String getStoreAddr() {
		return storeAddr;
	}
	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}
	public String getStoreEvent() {
		return storeEvent;
	}
	public void setStoreEvent(String storeEvent) {
		this.storeEvent = storeEvent;
	}
	public String getChgPwdDtm() {
		return chgPwdDtm;
	}
	public void setChgPwdDtm(String chgPwdDtm) {
		this.chgPwdDtm = chgPwdDtm;
	}
	public String getLstLgnDtm() {
		return lstLgnDtm;
	}
	public void setLstLgnDtm(String lstLgnDtm) {
		this.lstLgnDtm = lstLgnDtm;
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
	public String getBefStoreNoImg() {
		return befStoreNoImg;
	}
	public void setBefStoreNoImg(String befStoreNoImg) {
		this.befStoreNoImg = befStoreNoImg;
	}
	public String getBefStoreImg() {
		return befStoreImg;
	}
	public void setBefStoreImg(String befStoreImg) {
		this.befStoreImg = befStoreImg;
	}
	
}
