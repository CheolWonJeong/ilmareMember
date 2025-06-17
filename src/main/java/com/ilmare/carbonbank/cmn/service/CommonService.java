package com.ilmare.carbonbank.cmn.service;


import java.awt.datatransfer.StringSelection;
import java.util.Hashtable;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilmare.carbonbank.admin.mgr.SessionManager;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService {
	
	@Autowired
	private SessionManager sessMgr;
	

    //소속그룹
	public static Hashtable <String, Boolean>  hsPartyGrp = new Hashtable<String, Boolean>() {{
        put("system", true);
        put("carbonbank", true);
        put("gvrnmt", true);
    }};
	
    //문서상태
	public static Hashtable <String, String>  hsDocStat = new Hashtable<String, String>() {{
        put("N", "등록상태");
        put("V", "게시상태");
        put("C", "게시취소");
        put("D", "삭제");
    }};
	
	/*
	 * 관리자 로그인 여부 확인
	 */
	public boolean checkAdmLogin(HttpServletRequest request){
		
		sessMgr.createSession(request, false);
		//세션 존재여부 확인
		if ( !sessMgr.isSession() ) return false;
		
		return  true;
	}
	
	/*
	 * 관리자 화면 구너한 검사
	 */
	public boolean checkContentUse(String partyGrp){
		
		
		//세션 존재여부 확인
		if ( Strings.isBlank(partyGrp)  ) return false;

		return  hsPartyGrp.get(partyGrp);
	}
	

}
