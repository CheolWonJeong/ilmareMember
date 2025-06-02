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
import com.ilmare.carbonbank.service.CrbnQnaService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 이벤트 관리
 */
@Slf4j
@Controller
@RequestMapping("/adm/content")
public class CrbnQnaController {
	@Autowired(required=true)
	private SessionManager sessMgr;
	
	@Autowired
	private CrbnQnaService svc;

	@Autowired
	private CommonService commSvc;
	
	@Autowired
	private ConfigConstants conConst;
	

    @Value("${comm.pcUploadTemp}")
    private static String pcTmp;		//이벤트
	
	/*
	 *  이벤트 리스트 조회
	 *  
	 */
	@RequestMapping("/QnaMainList.do")
	public String EventMainList(HttpServletRequest request, final NewsCommonModel paramVo, Model model) throws Exception {
		
		log.info("QnaMainList Start");
		
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("QnaMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("QnaMainList 로그인 상태");
		log.info("QnaMainList sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("QnaMainList PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("QnaMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}		
		
		model.addAttribute("sessInfo", sessInfo);	
		
		log.info("EventMainList End");

		return "adm/content/qna/list";
		
	}

	/*
	 * 버튼 클릭조회
	 */
	@RequestMapping("/QnaQueryList")
	public  @ResponseBody HashMap EventQueryList(
			HttpServletRequest request, 
			final NewsCommonModel paramVo, 
			Model model,			
		    @RequestParam(defaultValue = "1") int page
			) throws Exception {
		
		HashMap result = new HashMap();
		log.info("QnaQueryList Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("QnaQueryList 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("QnaQueryList 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("QnaQueryList sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("QnaQueryList PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("QnaQueryList 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//이벤트 리스트 조회
        int pageSize = conConst.pageSize;    //페이지당 row 건수
        int pageNo = paramVo.getPageNo(); //조회할 페이지 번호
        int sRowNum = ((pageNo - 1) * pageSize) ;    //조회할 row의 시작값
		log.info("QnaQueryList {} ~ {}", sRowNum, pageSize);
		
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
		
		log.info("QnaQueryList End");

		return result;
	}

	
	/*
	 * 이벤트 상세 조회
	 */
	@RequestMapping("/QnaDesc.do")
	public String EventDesc(HttpServletRequest request, final NewsCommonModel paramVo, Model model) throws Exception {
		
		log.info("EventDesc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("AdmNoticeView 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		log.info("EventDesc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EventDesc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EventDesc PartyGrp=" + sessInfo.getPartyGrp());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("EventDesc 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		
		//메뉴 조회
		//List menuList = iUserInfoService.getMenu(userInfoVO);
		
		//이벤트 한건 조회
		
		NewsCommonModel rtnModel = svc.selectAdmDesc(paramVo);
		log.info("EventDesc {}  {}",rtnModel.getImgSrcNm(),rtnModel.getImgNailNm());

		model.addAttribute("hsDocStat", commSvc.hsDocStat);
		model.addAttribute("sessInfo", sessInfo);
		
		model.addAttribute("docview", rtnModel);
		//model.addAttribute("menuList", menuList);
		log.info("EventDesc End");

		return "adm/content/qna/update";
	}

	/*
	 * 이벤트 저장
	 */
	@PostMapping("/QnaUptProc")
	public  @ResponseBody HashMap EventUptProc(
			HttpServletRequest request, 
			//@RequestPart("imgFile") MultipartFile imgFile, 
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile,			
			final NewsCommonModel paramModel, 
			Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("EventUptProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("EventUptProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("EventUptProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EventUptProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EventUptProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("EventUptProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//파일 관련 삭제
		
		log.info("EventUptProc , paramModel.getDocSeq() : " + paramModel.getDocSeq());
		log.info("EventUptProc , paramModel.getAnsContent() : " + paramModel.getAnsContent());
		
		//이벤트 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		int rtn = svc.update(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("EventUptProc End");

		return result;
	}

	
	/*
	 * 이벤트 게시
	 */
	@RequestMapping("/QnaViewProc")
	public  @ResponseBody HashMap EventViewProc(HttpServletRequest request, final NewsCommonModel paramModel, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("EventViewProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("EventViewProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("EventViewProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EventViewProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EventViewProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("EventViewProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//paramModel.setDocStat("V");	//상태 'V
		//paramModel.setRegId(sessInfo.getCrbnAdmId());
		
		//이벤트 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		
		int rtn = svc.updateShowStat(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("EventViewProc End");

		return result;
	}

	/*
	 * 이벤트 게시 취소
	 */
	@RequestMapping("/QnaCancelProc")
	public  @ResponseBody HashMap EventCancelProc(HttpServletRequest request, final NewsCommonModel paramModel, Model model) throws Exception {
		
		HashMap result = new HashMap();
		log.info("EventCancelProc Start");
		sessMgr.createSession(request, false);
		if ( !sessMgr.isSession() ) {
			log.info("EventCancelProc 세션 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotLogin");  // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요");  // 오류 메시지
			return result;
		}
		
		log.info("EventCancelProc 로그인 상태");
		SessInfo sessInfo = sessMgr.getSessInfo();
		log.info("EventCancelProc sessInfo=" + sessInfo.toString());

		//권한 검사
		log.info("EventCancelProc getPartyCd=" + sessInfo.getPartyCd());
		if ( !commSvc.checkContentUse(sessInfo.getPartyGrp()) ) {
			log.info("EventCancelProc 권한 없음 상태");
			result.put("procInd", "E");  // 오류
			result.put("errorId", "NotGrade");  // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다.");  // 오류 메시지
			return result;
		}
		
		//paramModel.setDocStat("C");	//취소 C
		//paramModel.setRegId(sessInfo.getCrbnAdmId());
		
		//이벤트 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setAuditId(sessInfo.getCrbnAdmId());

		
		int rtn = svc.updateCancelStat(paramModel);

		result.put("procInd", "S");  // 정상
		log.info("EventCancelProc End");

		return result;
	}


}
