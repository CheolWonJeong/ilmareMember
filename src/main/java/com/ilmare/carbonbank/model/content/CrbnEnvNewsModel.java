package com.ilmare.carbonbank.model.content;



/*
 * 환경뉴스
 */
public class CrbnEnvNewsModel {
	private long docSeq;		//문서순번	
	private String partyCd;		//소속코드	
	private String docTitle;		//제목	
	private String imgSrcNm;		//이미지 원래이름	
	private String imgNailNm;		//이미지 이름	
	private String docInfo;		//문서내용
	private String docFrom;		//문서 출처
	private String docUrl;		//문서 Url
	private int docRead;		//읽은건수
	private int docRcmnd  ;		//추천해요 건수
	private int docLike   ;		//좋아요 건수  
	private int docSad    ;		//슬퍼요 건수  
	private int docAngry  ;		//화나요 건수  
	private String docStat;		//문서상태(N:등록, V:게시, C:게시취소)	
	private String regId;		//등록자ID
	private String regDtm;		//생성일시	
	private String showId;		//게시 아이디
	private String showDtm;		//게시일시
	private String cancelId;		//게시취소 아이디
	private String cancelDtm;		//게시취소일시
	private String delId;		//삭제 아이디
	private String delDtm;		//삭제일시

	private String prodDtm;		//처리일시
	private String docStatNm;	//문서상태(N:등록, V:게시, C:게시취소)	

	//조회
	private int pageNo  = 1;		//조회시작 위치 
	private int listSize  ;		//조회 건수  
	
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

    public long getDocSeq() {
        return docSeq;
    }
    public void setDocSeq(long docSeq) {
        this.docSeq = docSeq;
    }
    public String getPartyCd() {
        return partyCd;
    }
    public void setPartyCd(String partyCd) {
        this.partyCd = partyCd;
    }
    public String getDocTitle() {
        return docTitle;
    }
    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }
    public String getImgSrcNm() {
        return imgSrcNm;
    }
    public void setImgSrcNm(String imgSrcNm) {
        this.imgSrcNm = imgSrcNm;
    }
    public String getimgNailNm() {
        return imgNailNm;
    }
    public void setImgNailNm(String imgNailNm) {
        this.imgNailNm = imgNailNm;
    }

    public String getDocInfo() {
        return docInfo;
    }
    public void setDocInfo(String docInfo) {
        this.docInfo = docInfo;
    }
    public String getDocFrom() {
        return docFrom;
    }
    public void setDocFrom(String docFrom) {
        this.docFrom = docFrom;
    }
    public String getDocUrl() {
        return docUrl;
    }
    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
    public int getDocRead() {
        return docRead;
    }
    public void setDocRead(int docRead) {
        this.docRead = docRead;
    }
    public int getDocRcmnd() {
        return docRcmnd;
    }
    public void setDocRcmnd(int docRcmnd) {
        this.docRcmnd = docRcmnd;
    }
    public int getDocLike() {
        return docLike;
    }
    public void setDocLike(int docLike) {
        this.docLike = docLike;
    }
    public int getDocSad() {
        return docSad;
    }
    public void setDocSad(int docSad) {
        this.docSad = docSad;
    }
    public int getDocAngry() {
        return docAngry;
    }
    public void setDocAngry(int docAngry) {
        this.docAngry = docAngry;
    }
    public String getDocStat() {
        return docStat;
    }
    public void setDocStat(String docStat) {
        this.docStat = docStat;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getRegDtm() {
        return regDtm;
    }
    public void setRegDtm(String regDtm) {
        this.regDtm = regDtm;
    }
    public String getShowId() {
        return showId;
    }
    public void setShowId(String showId) {
        this.showId = showId;
    }
    public String getShowDtm() {
        return showDtm;
    }
    public void setShowDtm(String showDtm) {
        this.showDtm = showDtm;
    }
    public String getCancelId() {
        return cancelId;
    }
    public void setCancelId(String cancelId) {
        this.cancelId = cancelId;
    }
    public String getCancelDtm() {
        return cancelDtm;
    }
    public void setCancelDtm(String cancelDtm) {
        this.cancelDtm = cancelDtm;
    }
    public String getDelId() {
        return delId;
    }
    public void setDelId(String delId) {
        this.delId = delId;
    }
 
    public String getDelDtm() {
        return delDtm;
    }
    public void setDelDtm(String delDtm) {
        this.delDtm = delDtm;
    }
    
    public String getProdDtm() {
        return prodDtm;
    }
    public void setProdDtm(String prodDtm) {
        this.prodDtm = prodDtm;
    }

    public String getDocStatNm() {
        return docStatNm;
    }
    public void setDocStatNm(String docStatNm) {
        this.docStatNm = docStatNm;
    }
	
}
