package com.ilmare.carbonbank.content.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ilmare.carbonbank.model.content.CrbnStoreInfoModel;
import com.ilmare.carbonbank.service.CrbnStoreInfoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 가맹점 정보 관리
 */
@Slf4j
@Controller
@RequestMapping("/adm/content")
public class CrbnStoreInfoController {
	@Autowired(required=true)
	private SessionManager sessMgr;
	
	@Autowired
	private CrbnStoreInfoService svc;

	@Autowired
	private CommonService commSvc;
	
	@Autowired
	private ConfigConstants conConst;
	

    @Value("${comm.pcUploadTemp}")
    private static String pcTmp;		//가맹점 정보
	
	/*
	 *  가맹점 정보 리스트 조회
	 */
	@RequestMapping("/StoreInfoMainList.do")
	public String StoreInfoMainList(HttpServletRequest request, final CrbnStoreInfoModel paramVo, Model model) throws Exception {
		
		log.info("StoreInfoMainList Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
//		if ( !sessMgr.isSession(request) ) {
			log.info("StoreInfoMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
//		SessInfo sessInfo = sessMgr.getSession(request);
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("StoreInfoMainList 로그인 상태");
		log.info("StoreInfoMainList sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("StoreInfoMainList PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("StoreInfoMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		//메뉴 조회
		//List menuList = iUserInfoService.getMenu(userInfoVO);
		
		//가맹점 정보 리스트 조회
        int pageSize = conConst.pageSize;    //페이지당 row 건수
        int pageNo = paramVo.getPageNo(); //조회할 페이지 번호
        int sRowNum = ((pageNo - 1) * pageSize) ;    //조회할 row의 시작값
		log.info("StoreInfoMainList {} ~ {}", sRowNum, pageSize);
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		
		List<CrbnStoreInfoModel> ntsList = svc.selectAdmList(paramVo);
		log.info("StoreInfoMainList ntsList.size()" + ntsList.toString());
		String totalCount = svc.selectAdmListCount(paramVo);

		model.addAttribute("sessInfo", sessInfo);
		model.addAttribute("ntsList", ntsList);
		model.addAttribute("totalCount", totalCount);
		//model.addAttribute("menuList", menuList);
		log.info("StoreInfoMainList End");

		return "adm/content/StoreInfo/list";
		
	}

	/*
	 * 버튼 클릭조회
	 */
	@RequestMapping("/StoreInfoQueryList")
	public  @ResponseBody HashMap StoreInfoQueryList(HttpServletRequest request, final CrbnStoreInfoModel paramVo, Model model) throws Exception {
		
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
		
		//가맹점 정보 리스트 조회
        int pageSize = conConst.pageSize;    //페이지당 row 건수
        int pageNo = paramVo.getPageNo(); //조회할 페이지 번호
        int sRowNum = ((pageNo - 1) * pageSize) ;    //조회할 row의 시작값
		log.info("StoreInfoMainList {} ~ {}", sRowNum, pageSize);
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setListSize(ConfigConstants.pageSize);
		List<CrbnStoreInfoModel> ntsList = svc.selectAdmList(paramVo);

		result.put("ntsList", ntsList);
		log.info("NoticeQueryList End");

		return result;
	}

	/*
	 * 신규등록
	 */
	@RequestMapping("/StoreInfoIns.do")
	public String StoreInfoIns(HttpServletRequest request, Model model) throws Exception {
		log.info("StoreInfoIns Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("AdmStoreInfoIns 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		log.info("StoreInfoIns 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("StoreInfoIns sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("StoreInfoIns PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("StoreInfoIns 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);
		return "adm/content/StoreInfo/insert";
	}

	/*
	 * 저장
	 */
	@PostMapping("/StoreInfoInsProc")
	public  @ResponseBody HashMap StoreInfoInsProc(
			HttpServletRequest request, 
			//@RequestPart("imgFile") MultipartFile imgFile,
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile,	
			final CrbnStoreInfoModel paramModel, 
			Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("StoreInfoInsProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("StoreInfoInsProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("StoreInfoInsProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("StoreInfoInsProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("StoreInfoInsProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("AdmNoticeList 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//파일 관련		
		if ( imgFile != null && !imgFile.isEmpty()) {		
			String fileSavePath = FileUtil.getSaveFilePath("pcTemp", DateUtil.getCurrDate());
			log.info("파일 이름: " + imgFile.getOriginalFilename());
			log.info("fileSavePath: {} " ,fileSavePath);
	
	        String fileExt = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = imgFile.getOriginalFilename();
			//String imgNailNm = fileSavePath +File.separator + "640"+DateUtil.getCurrDateTime()+"." + fileExt;
			String imgNailNm = fileSavePath +File.separator + "640"+DateUtil.getCurrDateTime()+fileExt; // 중간에 점 제거
			//String tmpFileNm = FileUtil.uploadTemp + originalFilename;
			String tmpFileNm =  fileSavePath+File.separator +originalFilename;
			//log.info("파라머터: {}| {} | {} |  {}  " ,originalFilename, fileExt, imgNailNm,  tmpFileNm);
	
			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			imgFile.transferTo(savedFile); // 업로드된 파일 저장
			//log.info("TEST {} | {} | {} | {}", paramModel.getDocStat(), paramModel.getDocFrom(),  paramModel.getDocTitle());
	
	        // 썸네일 생성
	        File thumbnailFile = new File(imgNailNm);
	        Thumbnails.of(savedFile)
	                  .size(700, 400)
	                  .toFile(thumbnailFile);		
			//file upload
			
			//paramModel.setImgSrcNm(imgFile.getOriginalFilename());
			//paramModel.setImgNailNm(fileSavePath + DateUtil.getCurrDateTime() + fileExt);
			
		} else {
			log.info("imgFile is null ");
			//paramModel.setImgSrcNm("");
			//paramModel.setImgNailNm("");
		}			
			

		//저장
		//paramModel.setPartyCd(sessInfo.getPartyCd());
		//paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.insert(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("StoreInfoInsProc End");

		return result;
	}

	
	/*
	 * 가맹점 정보 상세 조회
	 */
	@RequestMapping("/StoreInfoDesc.do")
	public String StoreInfoDesc(HttpServletRequest request, final CrbnStoreInfoModel paramVo, Model model) throws Exception {
		
		log.info("StoreInfoDesc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("AdmNoticeView 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		log.info("StoreInfoDesc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("StoreInfoDesc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("StoreInfoDesc PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("StoreInfoDesc 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		//메뉴 조회
		//List menuList = iUserInfoService.getMenu(userInfoVO);
		
		//가맹점 정보 한건 조회
		
		CrbnStoreInfoModel rtnModel = svc.selectAdmDesc(paramVo);
		//log.info("StoreInfoDesc {}  {}",rtnModel.getImgSrcNm(),rtnModel.getImgNailNm());

		model.addAttribute("hsDocStat", commSvc.hsDocStat);
		model.addAttribute("sessInfo", sessInfo);
		
		model.addAttribute("docview", rtnModel);
		//model.addAttribute("menuList", menuList);
		log.info("StoreInfoDesc End");

		return "adm/content/StoreInfo/update";
	}

	/*
	 * 가맹점 정보 저장
	 */
	@PostMapping("/StoreInfoUptProc")
	public  @ResponseBody HashMap StoreInfoUptProc(
			HttpServletRequest request, 
			//@RequestPart("imgFile") MultipartFile imgFile, 
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile,			
			final CrbnStoreInfoModel paramModel, 
			Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("StoreInfoUptProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("StoreInfoUptProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("StoreInfoUptProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("StoreInfoUptProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("StoreInfoUptProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("StoreInfoUptProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//파일 관련
		if ( imgFile != null && !imgFile.isEmpty()) {
			String fileSavePath = FileUtil.getSaveFilePath("pcTemp", DateUtil.getCurrDate());
			log.info("파일 이름: " + imgFile.getOriginalFilename());
			log.info("fileSavePath: {} " ,fileSavePath);

	        String fileExt = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = imgFile.getOriginalFilename();
			//String imgNailNm = fileSavePath +File.separator + "640"+DateUtil.getCurrDateTime()+"." + fileExt;
			String imgNailNm = fileSavePath +File.separator + "640"+DateUtil.getCurrDateTime() + fileExt;  // 중간에 점 제거
			//String tmpFileNm = FileUtil.uploadTemp + originalFilename;
			String tmpFileNm =  fileSavePath+File.separator +originalFilename;

			log.info("파라머터: {}| {} | {} |  {}  " ,originalFilename, fileExt, imgNailNm,  tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			imgFile.transferTo(savedFile); // 업로드된 파일 저장
			//log.info("TEST {} | {} | {} | {}", paramModel.getDocStat(), paramModel.getDocFrom(),  paramModel.getDocTitle());

	        // 썸네일 생성
	        File thumbnailFile = new File(imgNailNm);
	        Thumbnails.of(savedFile)
	                  .size(700, 400)
	                  .toFile(thumbnailFile);		
			//file upload
			
			//paramModel.setImgSrcNm(imgFile.getOriginalFilename());
			//paramModel.setImgNailNm(fileSavePath + DateUtil.getCurrDateTime() + fileExt);

		} else {
			log.info("imgFile is null ");
			//paramModel.setImgSrcNm(paramModel.getBefImgSrcNme());
			//paramModel.setImgNailNm(paramModel.getBefImgNailNme());
		}
		
		//log.info("StoreInfoUptProc , paramModel.getDocSeq() : " + paramModel.getDocSeq());
		//log.info("StoreInfoUptProc , paramModel.getDocTitle() : " + paramModel.getDocTitle());
		
		//가맹점 정보 변경처리
		//paramModel.setPartyCd(sessInfo.getPartyCd());
		//paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.update(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("StoreInfoUptProc End");

		return result;
	}



}
