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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ilmare.carbonbank.admin.mgr.SessInfo;
import com.ilmare.carbonbank.admin.mgr.SessionManager;
import com.ilmare.carbonbank.cmn.controller.ConfigConstants;
import com.ilmare.carbonbank.cmn.service.CommonService;
import com.ilmare.carbonbank.cmn.util.DateUtil;
import com.ilmare.carbonbank.cmn.util.FileUtil;
import com.ilmare.carbonbank.model.content.NewsCommonModel;
import com.ilmare.carbonbank.service.CrbnPushMsgService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 알림 관리
 */
@Slf4j
@Controller
@RequestMapping("/adm/content")
public class PushMsgController {
	@Autowired(required=true)
	private SessionManager sessMgr;
	
	@Autowired
	private CrbnPushMsgService svc;

	@Autowired
	private CommonService commSvc;
	
	@Autowired
	private ConfigConstants conConst;
	
	@Autowired
	private FileUtil fileUtil;
	
    //@Value("${comm.pcUploadTemp}")
    //private static String pcTmp;		//알림
	
	/*
	 *  알림 리스트 조회
	 */
	@RequestMapping("/PushMsgMainList.do")
	public String PushMsgMainList(HttpServletRequest request, final NewsCommonModel paramVo, Model model) throws Exception {
		
		log.info("PushMsgMainList Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("PushMsgMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
				
		
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgMainList 로그인 상태");
		log.info("PushMsgMainList sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgMainList PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("PushMsgMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}		
		
		model.addAttribute("sessInfo", sessInfo);
		
		return "adm/content/pushmsg/list";
		
	}

	/*
	 * 버튼 클릭조회
	 */
	@RequestMapping("/PushMsgQueryList")
	public  @ResponseBody HashMap PushMsgQueryList(
			HttpServletRequest request, 
			final NewsCommonModel paramVo, 
			Model model,			
		    @RequestParam(defaultValue = "1") int page
			) throws Exception {
		
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
		
		//알림 리스트 조회
        int pageSize = conConst.pageSize;    //페이지당 row 건수
        int pageNo = paramVo.getPageNo(); //조회할 페이지 번호
        int sRowNum = ((pageNo - 1) * pageSize) ;    //조회할 row의 시작값
		log.info("PushMsgMainList {} ~ {}", sRowNum, pageSize);
		
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setListSize(ConfigConstants.pageSize);
		
		List<NewsCommonModel> dataList = svc.selectAdmList(paramVo);

		///////////////////////////////
		String totalCountStr = svc.selectAdmListCount(paramVo);

	    int totalCount = 0;
	    try { totalCount = Integer.parseInt(totalCountStr); } catch(NumberFormatException e) { totalCount = 0; }
	    
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);

	    result.put("currentPage", pageNo);  // 타임리프에 돌려줄 페이지번호
	    result.put("totalCount", totalCountStr);
	    result.put("totalPages", totalPages);
		///////////////////////////////
		result.put("rows", dataList); // jquery는 이 데이터만 사용.
		
		log.info("NoticeQueryList End");

		return result;
	}

	/*
	 * 신규등록
	 */
	@RequestMapping("/PushMsgIns.do")
	public String PushMsgIns(HttpServletRequest request, Model model) throws Exception {
		log.info("PushMsgIns Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("AdmPushMsgIns 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		log.info("PushMsgIns 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgIns sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgIns PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("PushMsgIns 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);
		return "adm/content/pushmsg/insert";
	}

	/*
	 * 저장
	 */
	@PostMapping("/PushMsgInsProc")
	public  @ResponseBody HashMap PushMsgInsProc(
			HttpServletRequest request, 
			//@RequestPart("imgFile") MultipartFile imgFile,
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile,	
			final NewsCommonModel paramModel, 
			Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("PushMsgInsProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("PushMsgInsProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("PushMsgInsProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgInsProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgInsProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("AdmNoticeList 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//파일 관련		
		if ( imgFile != null && !imgFile.isEmpty()) {		
			String fileSavePath = fileUtil.getSaveFilePath("pcTemp", DateUtil.getCurrDate());
			log.info("파일 이름: " + imgFile.getOriginalFilename());
			log.info("fileSavePath: {} " ,fileSavePath);
	
	        String fileExt = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = imgFile.getOriginalFilename();
			//String imgNailNm = fileSavePath +File.separator + "640"+DateUtil.getCurrDateTime()+"." + fileExt;
			String imgNailNm = fileSavePath +File.separator + "640"+DateUtil.getCurrDateTime()+fileExt; // 중간에 점 제거
			//String tmpFileNm = FileUtil.uploadTemp + originalFilename;
			String tmpFileNm =  fileSavePath+File.separator +originalFilename;
	
			log.info("파라머터: {}| {} | {} |  {}  " ,originalFilename, fileExt, imgNailNm,  tmpFileNm);
	
			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			imgFile.transferTo(savedFile); // 업로드된 파일 저장
			log.info("TEST {} | {} | {} | {}", paramModel.getDocStat(), paramModel.getDocFrom(),  paramModel.getDocTitle());
	
	        // 썸네일 생성
	        File thumbnailFile = new File(imgNailNm);
	        Thumbnails.of(savedFile)
	                  .size(700, 400)
	                  .toFile(thumbnailFile);		
			//file upload
			
			paramModel.setImgSrcNm(imgFile.getOriginalFilename());
			paramModel.setImgNailNm(fileSavePath + DateUtil.getCurrDateTime() + fileExt);
			
		} else {
			log.info("imgFile is null ");
			paramModel.setImgSrcNm("");
			paramModel.setImgNailNm("");
		}			
			

		//저장
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.insert(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("PushMsgInsProc End");

		return result;
	}

	
	/*
	 * 알림 상세 조회
	 */
	@RequestMapping("/PushMsgDesc.do")
	public String PushMsgDesc(HttpServletRequest request, final NewsCommonModel paramVo, Model model) throws Exception {
		
		log.info("PushMsgDesc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("AdmNoticeView 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		log.info("PushMsgDesc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgDesc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgDesc PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("PushMsgDesc 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		//메뉴 조회
		//List menuList = iUserInfoService.getMenu(userInfoVO);
		
		//알림 한건 조회
		
		NewsCommonModel rtnModel = svc.selectAdmDesc(paramVo);
		log.info("PushMsgDesc {}  {}",rtnModel.getImgSrcNm(),rtnModel.getImgNailNm());

		model.addAttribute("hsDocStat", commSvc.hsDocStat);
		model.addAttribute("sessInfo", sessInfo);
		
		model.addAttribute("docview", rtnModel);
		//model.addAttribute("menuList", menuList);
		log.info("PushMsgDesc End");

		return "adm/content/pushmsg/update";
	}

	/*
	 * 알림 저장
	 */
	@PostMapping("/PushMsgUptProc")
	public  @ResponseBody HashMap PushMsgUptProc(
			HttpServletRequest request, 
			//@RequestPart("imgFile") MultipartFile imgFile, 
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile,			
			final NewsCommonModel paramModel, 
			Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("PushMsgUptProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("PushMsgUptProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("PushMsgUptProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgUptProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgUptProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("PushMsgUptProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//파일 관련
		if ( imgFile != null && !imgFile.isEmpty()) {
			String fileSavePath = fileUtil.getSaveFilePath("pcTemp", DateUtil.getCurrDate());
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
			log.info("TEST {} | {} | {} | {}", paramModel.getDocStat(), paramModel.getDocFrom(),  paramModel.getDocTitle());

	        // 썸네일 생성
	        File thumbnailFile = new File(imgNailNm);
	        Thumbnails.of(savedFile)
	                  .size(700, 400)
	                  .toFile(thumbnailFile);		
			//file upload
			
			paramModel.setImgSrcNm(imgFile.getOriginalFilename());
			paramModel.setImgNailNm(fileSavePath + DateUtil.getCurrDateTime() + fileExt);

		} else {
			log.info("imgFile is null ");
			paramModel.setImgSrcNm(paramModel.getBefImgSrcNme());
			paramModel.setImgNailNm(paramModel.getBefImgNailNme());
		}
		
		log.info("PushMsgUptProc , paramModel.getDocSeq() : " + paramModel.getDocSeq());
		log.info("PushMsgUptProc , paramModel.getDocTitle() : " + paramModel.getDocTitle());
		
		//알림 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.update(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("PushMsgUptProc End");

		return result;
	}

	
	/*
	 * 알림 게시
	 */
	@RequestMapping("/PushMsgViewProc")
	public  @ResponseBody HashMap PushMsgViewProc(HttpServletRequest request, final NewsCommonModel paramModel, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("PushMsgViewProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("PushMsgViewProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("PushMsgViewProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgViewProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgViewProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("PushMsgViewProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		paramModel.setDocStat("V");	//상태 'V
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateShowStat(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("PushMsgViewProc End");

		return result;
	}

	/*
	 * 알림 게시 취소
	 */
	@RequestMapping("/PushMsgCancelProc")
	public  @ResponseBody HashMap PushMsgCancelProc(HttpServletRequest request, final NewsCommonModel paramModel, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("PushMsgCancelProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("PushMsgCancelProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("PushMsgCancelProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgCancelProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgCancelProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("PushMsgCancelProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		paramModel.setDocStat("C");	//취소 C
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateCancelStat(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("PushMsgCancelProc End");

		return result;
	}

	/*
	 * 알림 삭제
	 */
	@RequestMapping("/PushMsgDelProc")
	public  @ResponseBody HashMap PushMsgDelProc(HttpServletRequest request, final NewsCommonModel paramModel, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("PushMsgDelProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("PushMsgDelProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("PushMsgDelProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("PushMsgDelProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("PushMsgDelProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("NoticeCancelProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		paramModel.setDocStat("D");	//상태 D
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateDelStat(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("PushMsgDelProc End");

		return result;
	}


}
