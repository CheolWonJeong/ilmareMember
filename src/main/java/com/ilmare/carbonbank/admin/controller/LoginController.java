package com.ilmare.carbonbank.admin.controller;


import java.util.Enumeration;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilmare.carbonbank.admin.mgr.SessInfo;
import com.ilmare.carbonbank.admin.mgr.SessionManager;
import com.ilmare.carbonbank.admin.service.AdmUserService;
import com.ilmare.carbonbank.cmn.util.AES256Util;
import com.ilmare.carbonbank.cmn.util.CommUtil;
import com.ilmare.carbonbank.cmn.util.DateUtil;
import com.ilmare.carbonbank.model.CrbnAdmInfoModel;
import com.ilmare.carbonbank.model.CrbnAdmLgnHstModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/adm")
public class LoginController {
	
	@Autowired
	private SessionManager sessMgr;

	@Autowired
	private AdmUserService svc;

	@Autowired
	private CommUtil util;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private AES256Util aceUtil;

    @Value("${spring.saveRootForDev:defaultValue}")
	private String saveBase; //서버의 파일저장 기본 경로


	@RequestMapping("/login.do")
	public String loginView(HttpServletRequest request) {
		log.info("loginView Start");

		//세션 삭제
		sessMgr.deleteSession(request);
		
		return "adm/main/admlogin";
	}

	@RequestMapping("/loginProc")
	public @ResponseBody HashMap loginProc(final HttpServletRequest request, final CrbnAdmInfoModel crbnAdmInfo, final Model model) throws Exception {

		CrbnAdmInfoModel info; 
		
		HashMap result = new HashMap();
		
		log.info("loginProc Start");
		//세션 삭제
		sessMgr.deleteSession(request);
		
		//id 존재 검사
		log.info("loginProc id {} | pwd {}", crbnAdmInfo.getCrbnAdmId(), crbnAdmInfo.getCrbnAdmPwd());
		info = svc.getAdmInfo(crbnAdmInfo.getCrbnAdmId());
		if (info == null) {
			//id 미존재
			log.info("회원이 아닙니다. " );
			result.put("procInd", "E");  // 오류
			result.put("errorId", "idpw");  // 오류 종류
			result.put("errorMsg", "회원이 아니거나 비밀번호가 틀립니다.");  // 오류 메시지
			return result;
		}
		log.info("loginProc info=" + info.toString());
		//패스워드 비교
		String encParamPasswd = aceUtil.encrypt(crbnAdmInfo.getCrbnAdmPwd());
		log.info("loginProc pwd  {} | pwd {}", encParamPasswd, info.getCrbnAdmPwd());
		if ( !encParamPasswd.equalsIgnoreCase(info.getCrbnAdmPwd())) {
			//id 미존재
			log.info("패스워드가 다릅니다. " );
			result.put("procInd", "E");  // 오류
			result.put("errorId", "idpw");  // 오류 종류
			result.put("errorMsg", "회원이 아니거나 비밀번호가 틀립니다.");  // 오류 메시지
			return result;
		}

		log.info("정보일치 세션생성" );
		//PC 정보 얻음
		CrbnAdmLgnHstModel admLgnHst = new CrbnAdmLgnHstModel();
		admLgnHst.setCrbnAdmId(crbnAdmInfo.getCrbnAdmId());
		admLgnHst.setLgnIp(util.getIp(request));
		admLgnHst.setLgnOs(util.getOs(request));
		admLgnHst.setLgnBrowser(util.getBrowser(request));
		int rtn = svc.loginHistSave(admLgnHst);
		log.info("rtn=" + rtn );
		//세션 생성
		SessInfo sessInfo = new SessInfo();
		sessInfo.setCrbnAdmId(info.getCrbnAdmId());
		sessInfo.setCrbnAdmNm(info.getCrbnAdmNm());
		sessInfo.setPartyGrp(info.getPartyGrp());
		sessInfo.setPartyCd(info.getPartyCd());
		sessInfo.setPartyGrade(info.getPartyGrade());
		sessInfo.setCrbnDept(info.getCrbnDept());
		sessInfo.setCrbnPstn(info.getCrbnPstn());
		sessInfo.setLoginDtm(dateUtil.getCurrentTime());
		sessMgr.createSession(request, sessInfo);

		sessInfo = sessMgr.getSession(request);
		log.info("loginProc SessInfo=" + sessInfo.toString());

		log.info("loginProc End");
		result.put("procInd", "S");  // 정상
		return result;

	}


	@RequestMapping("/sessionView")
	public @ResponseBody SessInfo sessionView(final HttpServletRequest request) throws Exception {

		log.info("sessionView Start");
		SessInfo sessInfo = sessMgr.getSession(request);
		if ( sessInfo != null)
			log.info("loginProc SessInfo=" + sessInfo.toString());
		else 
			log.info("sessionView 세션 미존재");
		log.info("sessionView End {}", saveBase);
		return sessInfo;
	}

}
