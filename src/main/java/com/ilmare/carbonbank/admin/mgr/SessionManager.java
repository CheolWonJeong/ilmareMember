package com.ilmare.carbonbank.admin.mgr;

import org.springframework.stereotype.Component;

import com.ilmare.carbonbank.cmn.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionManager
{
    private SessInfo sess = null;
 
    private HttpSession httpSession;
    protected final static String ILMARE_SESS_INFO = "SESS_INFO";
 
    public boolean isFindSession( HttpServletRequest req, String sessName )
    {
        if (httpSession != null)
        {
            if (isSession(sessName)) return true;
        }
        return false;
    }
 
 
    public void deleteSession( HttpServletRequest req )
    {
        createSession( req, false );
        if ( httpSession != null )
            httpSession.invalidate();
    }
 
    public void createSessionInfo( HttpServletRequest req, SessInfo sessinfo )
    {
        sessionInit( req, sessinfo );
    }
 
 
    private HttpSession getHttpSession()
    {
        return httpSession;
    }
 
 
    public boolean isSession( String sessName )
    {
        if ( getHttpSession() != null && getHttpSession().getAttribute(sessName) != null ) {
            return true;
        } else return false;
    }
 
 
    public boolean isSession()
    {
        if ( isSession(ILMARE_SESS_INFO) ) {
            if( StringUtil.isEmpty( getSessInfo().getCrbnAdmId()) ) return false;
            return true;
        }
        return false;
    }
 
 
    public SessInfo getSessInfo() {
        return getSessInfo( false );
    }
 
 
    private SessInfo getSessInfo( boolean isNew )
    {
        if (sess == null) {
            if ( getHttpSession() == null ) return sess;
            sess = (SessInfo) getHttpSession().getAttribute(ILMARE_SESS_INFO);
            if ( sess == null & isNew ) {
                sess = new SessInfo();
                setSessInfo(sess);
            }
        }
        return sess;
    }
    
    public Object getEtcSession(String sessionName) {
        if ( isSession(sessionName) ) {
            return getHttpSession().getAttribute(sessionName);
        }
        return null;
    }
 
 
    protected void setSessInfo( SessInfo SessInfo ) {
        getHttpSession().setAttribute(ILMARE_SESS_INFO, SessInfo);
    }
    
    public void setEtcSession( String sessionName, Object sessionVal ) {
        getHttpSession().setAttribute(sessionName, sessionVal);
    }
 
    public void removeEtcSession( String sessionName) {
        getHttpSession().removeAttribute(sessionName);
    }

    public void sessionInit( HttpServletRequest req, SessInfo sessinfo )
    {
        createSession(req, true );
        sess = getSessInfo( true );
 
        sess.setCrbnAdmId(StringUtil.defaultString(sessinfo.getCrbnAdmId()));       // 관리자아이디
        sess.setCrbnAdmNm(StringUtil.defaultString(sessinfo.getCrbnAdmNm()));       // 이름
        sess.setPartyGrp(StringUtil.defaultString(sessinfo.getPartyGrp()));     // 소속그룹
        sess.setPartyCd(StringUtil.defaultString(sessinfo.getPartyCd()));     // 소속코드
        sess.setPartyGrade(StringUtil.defaultString(sessinfo.getPartyGrade()));    // 소속 등급
        sess.setCrbnDept(StringUtil.defaultString(sessinfo.getCrbnDept()));    //담당부서
        sess.setCrbnPstn(StringUtil.defaultString(sessinfo.getCrbnPstn()));    //직급
        sess.setLoginDtm(StringUtil.defaultString(sessinfo.getLoginDtm()));	//로그인 일시

    }
 
    public void createSession( HttpServletRequest req, boolean create )
    {
        httpSession = req.getSession( create );
    }

    
    public void createSession( HttpServletRequest req, SessInfo sessinfo )
    {
    	httpSession  = req.getSession();
 
    	httpSession.setAttribute("admId", (StringUtil.defaultString(sessinfo.getCrbnAdmId())));       // 관리자아이디
    	httpSession.setAttribute("admNm", (StringUtil.defaultString(sessinfo.getCrbnAdmNm())));       // 이름
    	httpSession.setAttribute("partyGrp",  (StringUtil.defaultString(sessinfo.getPartyGrp())));     // 소속그룹   
    	httpSession.setAttribute("partyCd",  (StringUtil.defaultString(sessinfo.getPartyCd())));     // 소속코드    
    	httpSession.setAttribute("partyGrade", (StringUtil.defaultString(sessinfo.getPartyGrade())));    // 소속 등급  
    	httpSession.setAttribute("crbnDept", (StringUtil.defaultString(sessinfo.getCrbnDept())));    //담당부서      
    	httpSession.setAttribute("crbnPatn",  (StringUtil.defaultString(sessinfo.getCrbnPstn())));    //직급         
    	httpSession.setAttribute("loginDtm",  (StringUtil.defaultString(sessinfo.getLoginDtm())));	//로그인 일시     

    }
    

    public SessInfo getSession( HttpServletRequest req )
    {
    	httpSession  = req.getSession();
    	SessInfo sessinfo = new SessInfo(); 
    	if ( httpSession == null)  return sessinfo;
 
    	sessinfo.setCrbnAdmId(StringUtil.defaultString((String)httpSession.getAttribute("admId")));       // 관리자아이디
    	sessinfo.setCrbnAdmNm(StringUtil.defaultString((String)httpSession.getAttribute("admNm")));       // 이름
    	sessinfo.setPartyGrp(StringUtil.defaultString((String)httpSession.getAttribute("partyGrp")));     // 소속그룹   
    	sessinfo.setPartyCd(StringUtil.defaultString((String)httpSession.getAttribute("partyCd")));     // 소속코드    
    	sessinfo.setPartyGrade(StringUtil.defaultString((String)httpSession.getAttribute("partyGrade")));    // 소속 등급  
    	sessinfo.setCrbnDept(StringUtil.defaultString((String)httpSession.getAttribute("crbnDept")));    //담당부서      
    	sessinfo.setCrbnPstn(StringUtil.defaultString((String)httpSession.getAttribute("crbnPatn")));    //직급         
    	sessinfo.setLoginDtm(StringUtil.defaultString((String)httpSession.getAttribute("loginDtm")));	//로그인 일시
    	
    	return sessinfo;

    }
    
    public boolean isSession(HttpServletRequest req)
    {
    	httpSession  = req.getSession();
    	if ( httpSession == null)  return false;
        if ( StringUtil.defaultString((String)httpSession.getAttribute("admId")).length() < 2 ) {
            return false;
        }
        return true;
    }
 
    
}
