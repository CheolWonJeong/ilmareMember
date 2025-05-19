package com.ilmare.carbonbank.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ilmare.carbonbank.admin.service.AdmUserService;
import com.ilmare.carbonbank.model.CrbnAdmInfoModel;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/adm")
public class HomeController {

	@Autowired
	AdmUserService admSvc;

	@RequestMapping(value = "/home", method=RequestMethod.GET)
	@ResponseBody
	public String goHome(HttpServletRequest request) {
		log.debug("goHome");
		log.trace("TRACE!!");
        log.debug("DEBUG!!");
        log.info("INFO!!");
        log.warn("WARN!!");
        log.error("ERROR!!");
		return "Hello Spring Boot";
	}

	@RequestMapping(value = "/htmlHome", method=RequestMethod.GET)
	public String htmlHome(HttpServletRequest request) {
		return "adm/home";
	}

	@RequestMapping(value = "/Viewhome", method=RequestMethod.GET)
	public ModelAndView Viewhome(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<String> resultList = new ArrayList<String>();
		
		resultList.add("AAA");
		resultList.add("BBB");
		resultList.add("CCC");
		resultList.add("DDD");
		resultList.add("EEE");
		resultList.add("FFF");
		
		mav.addObject("resultList",resultList);
		mav.setViewName("demo/Viewhome");
		
		return mav;
	}

	
/*	
	@RequestMapping(value = "/admDBList", method=RequestMethod.GET)
	public ModelAndView admDBList(HttpServletRequest request) {
        log.info("admDBList start");
		ModelAndView mav = new ModelAndView();
		
		List<CrbnAdmInfo> admList = admSvc.getAdmList();
        log.info("admList=" + admList.toString());
		
		mav.addObject("admList", admList);
		mav.setViewName("adm/homeDB");
		
		return mav;
	}
*/

	@RequestMapping(value = "/admDBInfo", method=RequestMethod.GET)
	public ModelAndView admDBInfo(HttpServletRequest request) {
        log.info("admDBInfo start");
		ModelAndView mav = new ModelAndView();
		
		CrbnAdmInfoModel admInfo = admSvc.getAdmInfo("AdmMaster");
		if (admInfo != null ) {
	        log.info("admInfo=" + admInfo.toString());
		}
		
		mav.addObject("admInfo", admInfo);
		mav.setViewName("adm/homeDB");
		return mav;
	}
}
