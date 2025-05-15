package com.ilmare.carbonbank.cmn.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 업무 그룹명 : com.common.util 서브 업무명 : PageUtils.java 작성자 : 이남희 작성일 : 2009. 06. 23
 * 설 명 : PageUtils
 */
@Component
public class PageUtils {

	/**
	 * 페이지
	 */
	private int pageIdx;

	/**
	 * 한 페이지당 출력할 갯수
	 */
	private int rowPerPage;

	/**
	 * 페이징 기본 블럭 사이즈
	 */
	private int pageBlock;

	/**
	 * 전체 그룹 수
	 */
	private int totalGroupCnt;

	/**
	 * 페이지 그룹 번호
	 */
	private int numPageGroup;

	/**
	 * 시작 페이지
	 */
	private int startPage;

	/**
	 * 종료 페이지
	 */
	private int endPage;

	/**
	 * 전체 페이지 수
	 */
	private int pageCount;

	/**
	 * 이전 그룹 페이지
	 */
	private int prevNavi;

	/**
	 * 다음 그룹 페이지
	 */
	private int nextNavi;

	/**시작 레코드 */
	private int startNum;
	/** 끝 레코드 */
	private int endNum;
	/**
	 * @return the pageIdx
	 */
	public int getPageIdx() {
		return pageIdx;
	}

	/**
	 * @param pageIdx
	 *            the pageIdx to set
	 */
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}

	/**
	 * @return the rowPerPage
	 */
	public int getRowPerPage() {
		return rowPerPage;
	}

	/**
	 * @param rowPerPage
	 *            the rowPerPage to set
	 */
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	/**
	 * @return the totalGroupCnt
	 */
	public int getTotalGroupCnt() {
		return totalGroupCnt;
	}

	/**
	 * @param totalGroupCnt
	 *            the totalGroupCnt to set
	 */
	public void setTotalGroupCnt(int totalGroupCnt) {
		this.totalGroupCnt = totalGroupCnt;
	}

	/**
	 * @return the numPageGroup
	 */
	public int getNumPageGroup() {
		return numPageGroup;
	}

	/**
	 * @param numPageGroup
	 *            the numPageGroup to set
	 */
	public void setNumPageGroup(int numPageGroup) {
		this.numPageGroup = numPageGroup;
	}

	/**
	 * @return the startPage
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * @param startPage
	 *            the startPage to set
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	/**
	 * @return the endPage
	 */
	public int getEndPage() {
		return endPage;
	}

	/**
	 * @param endPage
	 *            the endPage to set
	 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the prevNavi
	 */
	public int getPrevNavi() {
		return prevNavi;
	}

	/**
	 * @param prevNavi
	 *            the prevNavi to set
	 */
	public void setPrevNavi(int prevNavi) {
		this.prevNavi = prevNavi;
	}

	/**
	 * @return the nextNavi
	 */
	public int getNextNavi() {
		return nextNavi;
	}

	/**
	 * @param nextNavi
	 *            the nextNavi to set
	 */
	public void setNextNavi(int nextNavi) {
		this.nextNavi = nextNavi;
	}

	/**
	 * @return the pageBlock
	 */
	public int getPageBlock() {
		return pageBlock;
	}

	/**
	 * @param pageBlock
	 *            the pageBlock to set
	 */
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	
	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	/**
	 * 기본 값 세팅
	 * 
	 * @param request
	 */
	public void requestProcess(HttpServletRequest request) {
		int pageIdx = Integer.parseInt(StringUtil.strNull(request.getParameter("pageIdx"), "1"));
		int rowPerPage = Integer.parseInt(StringUtil.strNull(request.getParameter("rowPerPage"), "15"));
		int rows = Integer.parseInt(StringUtil.strNull(request.getParameter("rows"), "15"));
		int page = Integer.parseInt(StringUtil.strNull(request.getParameter("page"), "1"));
		
		this.pageIdx = (pageIdx == page) ? pageIdx : page;
		this.rowPerPage = (rowPerPage == rows) ? rowPerPage : rows;
		this.pageBlock = Integer.parseInt(StringUtil.strNull(request.getParameter("pageBlock"), "10"));
	}

	/**
	 * 페이지 처리 계산
	 * 
	 * @param totalCnt
	 * @return
	 */
	public PageUtils getCalcPageNavi(int totalCnt) {
		int totalGroupCnt = totalCnt / (rowPerPage * pageBlock) + (totalCnt % (rowPerPage * pageBlock) == 0 ? 0 : 1); // 전체 그룹 수
		int numPageGroup = (int) Math.ceil((double) pageIdx / pageBlock);

		int startPage = pageBlock * (numPageGroup - 1) + 1; // 시작 페이지
		int endPage = startPage + pageBlock - 1; // 끝 페이지
		int pageCount = totalCnt / rowPerPage + (totalCnt % rowPerPage == 0 ? 0 : 1);
		int prevNavi = (numPageGroup - 2) * pageBlock + 1; // 이전 블럭
		int nextNavi = numPageGroup * pageBlock + 1; // 다음 블럭

		this.setTotalGroupCnt(totalGroupCnt);
		this.setNumPageGroup(numPageGroup);
		this.setStartPage(startPage);
		this.setEndPage(endPage);
		this.setPageCount(pageCount);
		this.setPrevNavi(prevNavi);
		this.setNextNavi(nextNavi);
		this.setStartNum(rowPerPage * (pageIdx-1) + 1);
		this.setEndNum(this.startNum + rowPerPage - 1);
		return this;
	}

	/**
	 * 페이징 HTML
	 * @return
	 */
	public String getPageHtml() {
		StringBuffer sbHtml = new StringBuffer();

		int pageNO = this.pageIdx; 			// 현재페이지
		int startPage = this.startPage; 		// 페이지 블록 시작번호
		int endPage = this.endPage; 		// 페이지 블록 마지막번호
		int firstPage = 1; 						// 처음 페이지 번호
		int lastPage = this.pageCount; 		// 마지막 페이지 번호
		int prevNavi = this.prevNavi<firstPage ? firstPage : this.prevNavi; 	// 이전페이지 번호
		int nextNavi = this.nextNavi>lastPage ? lastPage : this.nextNavi; 	// 다음페이지 번호
		 
		sbHtml.append("<div class=\"pagenate\">");
		sbHtml.append("	<div class=\"page\">");
		sbHtml.append("		<a href=\"javascript:movePage('"+firstPage+"');\" class=\"page_btn first\">맨처음리스트</a>"); 
		sbHtml.append("		<a href=\"javascript:movePage('"+prevNavi+"');\" class=\"page_btn prev\">이전리스트</a>");
		sbHtml.append("		<span class=\"page_num\">");
		for( int p=startPage; p<=endPage; p++ ) {
			if( p > lastPage ) break;		
			sbHtml.append("			<a href=\"javascript:movePage('"+p+"');\" "+( p==pageNO ? "class=\"on\"" : "" ) +">"+p+"</a>");
		}
		sbHtml.append("		</span>");
		sbHtml.append("		<a href=\"javascript:movePage('"+nextNavi+"');\" class=\"page_btn next\">다음리스트</a>");
		sbHtml.append("		<a href=\"javascript:movePage('"+lastPage+"');\" class=\"page_btn end\">맨끝리스트</a>");
		sbHtml.append("	</div>");
		sbHtml.append("</div>");
		return sbHtml.toString();
	}
	
	
	/**
	 * 페이징 html 백오피스용
	 * @return
	 */
	public String getPageHtmlBO() {

		int prevNavi = this.prevNavi<1 ? 1 : this.prevNavi; 	// 이전페이지 번호
		int nextNavi = this.nextNavi>this.pageCount ? this.pageCount : this.nextNavi; 	// 다음페이지 번호
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"pageNav\">");
		sb.append("<a href=\"javascript:" + (this.pageIdx == 1 ? "void(0)" : "goPage('" + 1        + "')\"") + "\" class=\"first\">«</a>");
		sb.append("<a href=\"javascript:" + (this.pageIdx == 1 ? "void(0)" : "goPage('" + prevNavi + "')\"") + "\" class=\"prev\">&lt;</a>");
		for (int p = this.startPage; p <= this.endPage; p++) {
			if( p > this.pageCount ) break;		
			if( this.pageIdx == p ) {
				sb.append("<span class=\"current\">" + p + "</span>");
			} else {
				sb.append("<a href=\"javascript:goPage('" + p + "')\">" + p + "</a>");
			}
		}
		sb.append("<a href=\"javascript:").append(this.pageIdx >= this.pageCount ? "void(0)" : "goPage('" + nextNavi    + "')\"").append("\" class=\"next\">&gt;</a>");
		sb.append("<a href=\"javascript:").append(this.pageIdx >= this.pageCount ? "void(0)" : "goPage('" + this.pageCount + "')\"").append("\" class=\"last\">»</a>");
		sb.append("</div>");
		return sb.toString();
	}
}
