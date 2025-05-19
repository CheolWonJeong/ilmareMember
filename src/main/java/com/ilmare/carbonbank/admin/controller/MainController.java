package com.ilmare.carbonbank.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ilmare.carbonbank.admin.mgr.SessInfo;
import com.ilmare.carbonbank.admin.mgr.SessionManager;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
 * 괒리자 메인
 */
@Slf4j
@Controller
@RequestMapping("/adm")
public class MainController {

	@Autowired(required=true)
	private SessionManager sessMgr;
	
	@RequestMapping("/main.do")
	public String main(HttpServletRequest request, Model model) throws Exception {
		
		log.info("main Start");
		if ( !sessMgr.isSession() ) {
			return "redirect:/adm/login.do";
		}
		
		log.info("main 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("main sessInfo=" + sessInfo.toString());

		log.info("main ID=" + sessInfo.getCrbnAdmId());
		//메뉴 조회
		//List menuList = iUserInfoService.getMenu(userInfoVO);
		
		model.addAttribute("sessInfo", sessInfo);
		//model.addAttribute("menuList", menuList);
		
		log.info("main ID=" + sessInfo.getCrbnAdmId());
		return "adm/main/admmain";
	}

	@RequestMapping("/admReg.do")
	public String admReg(HttpServletRequest request) {
		log.info("admReg Start");

		//세션 삭제
		sessMgr.deleteSession(request);
		return "adm/main/admRegister";
	}

	
}
