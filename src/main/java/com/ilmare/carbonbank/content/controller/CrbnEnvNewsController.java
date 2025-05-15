package com.ilmare.carbonbank.content.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ilmare.carbonbank.admin.mgr.SessInfo;
import com.ilmare.carbonbank.admin.mgr.SessionManager;
import com.ilmare.carbonbank.cmn.controller.ConfigConstants;
import com.ilmare.carbonbank.cmn.service.CommonService;
import com.ilmare.carbonbank.cmn.util.DateUtil;
import com.ilmare.carbonbank.cmn.util.FileUtil;
import com.ilmare.carbonbank.model.content.CrbnEnvNewsModel;
import com.ilmare.carbonbank.service.CrbnEnvNewsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 환경 뉴스
 */
@Slf4j
@Controller
@RequestMapping("/adm/content")
public class CrbnEnvNewsController {
	@Autowired(required=true)
	private SessionManager sessMgr;
	
	@Autowired
	private CrbnEnvNewsService svc;

	@Autowired
	private CommonService commSvc;
	
	@Autowired
	private ConfigConstants conConst;
	
	/*
	 *  리스트 조회
	 */
	@RequestMapping("/EnvNewsMainList.do")
	public String EnvNewsMainList(HttpServletRequest request, final CrbnEnvNewsModel paramVo, Model model) throws Exception {
		
		log.info("EnvNewsMainList Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
//		if ( !sessMgr.isSession(request) ) {
			log.info("EnvNewsMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
//		SessInfo sessInfo = sessMgr.getSession(request);
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EnvNewsMainList 로그인 상태");
		log.info("EnvNewsMainList sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EnvNewsMainList PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("EnvNewsMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		//메뉴 조회
		//List menuList = iUserInfoService.getMenu(userInfoVO);
		
		//공지사항 리스트 조회
        int pageSize = conConst.pageSize;    //페이지당 row 건수
        int pageNo = paramVo.getPageNo(); //조회할 페이지 번호
        int sRowNum = ((pageNo - 1) * pageSize) ;    //조회할 row의 시작값
		log.info("EnvNewsMainList {} ~ {}", sRowNum, pageSize);
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		
		List<CrbnEnvNewsModel> ntsList = svc.selectAdmList(paramVo);
		log.info("EnvNewsMainList ntsList.size()" + ntsList.toString());
		

		model.addAttribute("sessInfo", sessInfo);
		model.addAttribute("ntsList", ntsList);
		//model.addAttribute("menuList", menuList);
		log.info("EnvNewsMainList End");

		return "adm/content/envnews/list";
	}

	/*
	 * 버튼 클릭조회
	 */
	@RequestMapping("/EnvNewsQueryList")
	public  @ResponseBody HashMap EnvNewsQueryList(HttpServletRequest request, final CrbnEnvNewsModel paramVo, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("NoticeQueryList Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("NoticeQueryList 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("NoticeQueryList 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("NoticeQueryList sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("NoticeQueryList PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("NoticeQueryList 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//공지사항 리스트 조회
        int pageSize = conConst.pageSize;    //페이지당 row 건수
        int pageNo = paramVo.getPageNo(); //조회할 페이지 번호
        int sRowNum = ((pageNo - 1) * pageSize) ;    //조회할 row의 시작값
		log.info("EnvNewsMainList {} ~ {}", sRowNum, pageSize);
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setListSize(ConfigConstants.pageSize);
		List<CrbnEnvNewsModel> ntsList = svc.selectAdmList(paramVo);

		result.put("ntsList", ntsList);
		log.info("NoticeQueryList End");

		return result;
	}

	/*
	 * 신규등록
	 */
	@RequestMapping("/EnvNewsIns.do")
	public String EnvNewsIns(HttpServletRequest request, Model model) throws Exception {
		log.info("EnvNewsIns Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("AdmEnvNewsIns 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		log.info("EnvNewsIns 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EnvNewsIns sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EnvNewsIns PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("EnvNewsIns 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);
		return "adm/content/envnews/insert";
	}

	/*
	 * 저장
	 */
	@PostMapping("/EnvNewsInsProc")
	public  @ResponseBody HashMap EnvNewsInsProc(HttpServletRequest request, @RequestPart("imgFile") MultipartFile imgFile, final CrbnEnvNewsModel paramModel, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("EnvNewsInsProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("EnvNewsInsProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("EnvNewsInsProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EnvNewsInsProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EnvNewsInsProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("AdmNoticeList 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//파일 관련
		String fileSavePath = FileUtil.getSaveFilePath("EnvNews", DateUtil.getCurrDate());
		log.info("파일 이름: " + imgFile.getOriginalFilename());
		log.info("파라머터: {} " + FileUtil.updTemp);

        String fileExt = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
		String originalFilename = imgFile.getOriginalFilename();
		String imgNailNm = fileSavePath + "640"+DateUtil.getCurrDateTime()+"." + fileExt;
		//String tmpFileNm = FileUtil.uploadTemp + originalFilename;
		String tmpFileNm =  "/app/data/" + originalFilename;

		log.info("파라머터: {}| {} | {} | {} | {}  " + originalFilename, fileExt, imgNailNm, FileUtil.updTemp, tmpFileNm);

		//FileUtil.createDirectory(fileSavePath);
		File savedFile = new File(tmpFileNm);
		imgFile.transferTo(savedFile); // 업로드된 파일 저장

        // 썸네일 생성
        File thumbnailFile = new File(imgNailNm);
        Thumbnails.of(savedFile)
                  .size(700, 400)
                  .toFile(thumbnailFile);		
		//file upload
		
		paramModel.setImgSrcNm(imgFile.getOriginalFilename());
		paramModel.setImgNailNm(fileSavePath + DateUtil.getCurrDateTime() + fileExt);

		//저장
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.insert(paramModel);

		
		

		result.put("procInd", "S");  // 정상
		log.info("EnvNewsInsProc End");

		return result;
	}

	

}
