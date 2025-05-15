package com.ilmare.carbonbank.cmn.util;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommUtil {

	/**
	 * 시스템 점검 체크
	 * @param request
	 * @return 시스템 점검 중이면 true, 정상 이면, false
	 * @throws Exception
	 */
	public static boolean systemInspection(final HttpServletRequest request) throws Exception {

		// 시스템 점검 내용 DB 조회

		// DB 조회 후 점검 내용 or 시간관련 request.setAttribute() 전달

		return false;
	}

	/**
	 * xss 보안 - 특수문자 변경
	 * @param src
	 * @return
	 */
	public static String escapeXss(final String src) {
		int length = src.length();
		String comp = "";
		StringBuffer buffer = new StringBuffer("");
		for ( int i = 0; i < length; i++ ) {
			comp = src.substring(i, i+1);
			if ( "\"".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&quot;");
			} else if ( "\\".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("￦");
			} else if ( "<".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&lt;");
			} else if ( ">".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&gt;");
			} else if ( "(".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&#40;");
			} else if ( ")".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&#41;");
			} else if ( "#".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&#35;");
			} else if ( "&".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&#38;");
			} else if ( "'".compareTo(comp) == 0 ) {
				comp = src.substring(i, i);
				buffer.append("&#39;");
			}
			buffer.append(comp);
		}
		return buffer.toString();
	}



	/**
	 * xss 보안 - 특수문자 변경 된것을 다시 원복
	 * @param src
	 * @return
	 */
	public static String unEscapeXss(final String src) {
		String tempSrc = null;
		tempSrc = src.replaceAll("&quot;", "\"");
		tempSrc = tempSrc.replaceAll("￦", "\\");
		tempSrc = tempSrc.replaceAll("&lt;", "<");
		tempSrc = tempSrc.replaceAll("&gt;", ">");

		tempSrc = tempSrc.replaceAll("&#40;", "(");
		tempSrc = tempSrc.replaceAll("&#41;", ")");
		tempSrc = tempSrc.replaceAll("&#35;", "#");
		tempSrc = tempSrc.replaceAll("&#38;", "&");
		tempSrc = tempSrc.replaceAll("&#39;", "'");

		return tempSrc;
	}


	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
	
	public static String getBrowser(HttpServletRequest request) {
		// 에이전트
		String agent = request.getHeader("User-Agent");
		// 브라우져 구분
		String browser = null;
		if (agent != null) {
			if (agent.indexOf("Trident") > -1) {
				browser = "MSIE";
			} else if (agent.indexOf("Chrome") > -1) {
				browser = "Chrome";
			} else if (agent.indexOf("Opera") > -1) {
				browser = "Opera";
			} else if (agent.indexOf("iPhone") > -1 && agent.indexOf("Mobile") > -1) {
				browser = "iPhone";
			} else if (agent.indexOf("Android") > -1 && agent.indexOf("Mobile") > -1) {
				browser = "Android";
			}
		}
		return browser;
	}
	
	public static String getOs(HttpServletRequest request) {
		// 에이전트
		String agent = request.getHeader("User-Agent");
		String os = null;
		if(agent.indexOf("NT 6.0") != -1) os = "Windows Vista/Server 2008";
		else if(agent.indexOf("NT 5.2") != -1) os = "Windows Server 2003";
		else if(agent.indexOf("NT 5.1") != -1) os = "Windows XP";
		else if(agent.indexOf("NT 5.0") != -1) os = "Windows 2000";
		else if(agent.indexOf("NT") != -1) os = "Windows NT";
		else if(agent.indexOf("9x 4.90") != -1) os = "Windows Me";
		else if(agent.indexOf("98") != -1) os = "Windows 98";
		else if(agent.indexOf("95") != -1) os = "Windows 95";
		else if(agent.indexOf("Win16") != -1) os = "Windows 3.x";
		else if(agent.indexOf("Windows") != -1) os = "Windows";
		else if(agent.indexOf("Linux") != -1) os = "Linux";
		else if(agent.indexOf("Macintosh") != -1) os = "Macintosh";
		else os = ""; 
		return os;
	}
	
	public static String getWebType(HttpServletRequest request) {
		String filter = "iphone|ipod|android|windows ce|blackberry|symbian|windows phone|webos|opera mini|opera mobi|polaris|iemobile|lgtelecom|nokia|sonyericsson|lg|samsung";
		String filters[] = filter.split("\\|");
		String webType = "";
		
		for(String tmp : filters){
			if ( request.getHeader("User-Agent").toLowerCase().indexOf(tmp) != -1) {
				webType = "MOBILE";
				break;
			} else {
				webType = "PC";
			}
		}
		return webType;
		
	}

	/**
	 * 목록 페이징 정보 세팅
	 * @param list (HahsMap List)
	 * @return HashMap
	 */
	public static HashMap setPageInfo (final List list, final PageUtils pages, final int totalCnt) {

		int totalcnt = 0;
		int listSize = 0;

		if (totalCnt > 0) {
			listSize = (list!=null)?list.size():0;
			totalcnt = totalCnt;
		} else {
			if (!list.isEmpty()) {
				listSize = list.size();
				totalcnt = Integer.parseInt((((HashMap) list.get(listSize-1)).get("totalcnt")).toString());
			}
		}

		// @Page 처리 (pages 와 pageVo를 구분할 것.)
		PageUtils pageVo = pages.getCalcPageNavi(totalcnt);

		log.info("#################  구해온 페이지 Start ################");
		log.info("총 갯수               totalCnt : " + totalcnt);
		log.info("현재 페이지            pageIdx : " + pages.getPageIdx());
		log.info("페이지당 출력 수    rowPerPage : " + pages.getRowPerPage());
		log.info("페이지당 블럭 수     pageBlock : " + pages.getPageBlock());

		log.info("페이지 그룹 갯수 totalGroupCnt : " + pageVo.getTotalGroupCnt());
		log.info("페이지 그룹 번호  numPageGroup : " + pageVo.getNumPageGroup());
		log.info("시작 페이지          startPage : " + pageVo.getStartPage());
		log.info("종료 페이지            endPage : " + pageVo.getEndPage());
		log.info("전체 페이지 수       pageCount : " + pageVo.getPageCount());
		log.info("이전 블럭             prevNavi : " + pageVo.getPrevNavi());
		log.info("다음 블럭             nextNavi : " + pageVo.getNextNavi());
		log.info("다음 블럭             listSize : " + listSize);
		log.info("#################  구해온 페이지 End ################");

		HashMap map = new HashMap();
		map.put("rows"     , list);
		map.put("page"     , pages.getPageIdx() + "");
		map.put("lastpage" , pageVo.getPageCount() + "");
		map.put("records"  , (totalcnt == 0) ? listSize + "" : totalcnt + "");
		map.put("startpage", pageVo.getStartPage() + "");
		map.put("endpage"  , pageVo.getEndPage() + "");
		map.put("prevnavi" , pageVo.getPrevNavi() + "");
		map.put("nextnavi" , pageVo.getNextNavi() + "");
		map.put("listSize" , listSize + "");

		return map;
	}

	
}

