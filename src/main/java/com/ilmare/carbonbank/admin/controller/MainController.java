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
		if ( !sessMgr.isSession(request) ) {
			return "redirect:/adm/login.do";
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		log.info("main ID=" + sessInfo.getCrbnAdmId());
		
		model.addAttribute("sessInfo", sessInfo);
		
		log.info("main ID=" + sessInfo.getPartyCd());
		return "adm/main/main_" + sessInfo.getPartyCd();
	}


	
}
