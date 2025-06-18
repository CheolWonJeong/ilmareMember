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
import com.ilmare.carbonbank.service.CrbnHotNewsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 기관 핫뉴스 관리
 */
@Slf4j
@Controller
@RequestMapping("/adm/content")
public class CrbnHotNewsController {
	@Autowired(required = true)
	private SessionManager sessMgr;

	@Autowired
	private CrbnHotNewsService svc;

	@Autowired
	private CommonService commSvc;

	@Autowired
	private ConfigConstants conConst;

	@Autowired
	private FileUtil fileUtil;

	/*
	 * 기관 핫뉴스 리스트 조회
	 */
	@RequestMapping("/HotNewsMainList.do")
	public String HotNewsMainList(HttpServletRequest request, final NewsCommonModel paramVo, Model model,
			@RequestParam(defaultValue = "1") int page) throws Exception {

		log.info("HotNewsMainList Start , page = " + page);

		if (!sessMgr.isSession(request)) {
			log.info("HotNewsMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsMainList PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		// 기관 핫뉴스 리스트 조회
		int pageSize = conConst.pageSize; // 페이지당 row 건수
		int pageNo = page; // 조회할 페이지 번호
		int sRowNum = ((pageNo - 1) * pageSize); // 조회할 row의 시작값

		log.info("HotNewsMainList {} ~ {}", sRowNum, pageSize);
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setPartyCd(sessInfo.getPartyCd());

		List<NewsCommonModel> ntsList = svc.selectAdmList(paramVo);
		log.info("HotNewsMainList ntsList.size()" + ntsList.toString());
		String totalCountStr = svc.selectAdmListCount(paramVo);

		model.addAttribute("sessInfo", sessInfo);
		model.addAttribute("ntsList", ntsList);
		model.addAttribute("totalCount", totalCountStr);
		model.addAttribute("currentPage", pageNo); // 타임리프에 돌려줄 페이지번호

		int totalCount = 0;
		try {
			totalCount = Integer.parseInt(totalCountStr);
		} catch (NumberFormatException e) {
			totalCount = 0;
		}

		int totalPages = (int) Math.ceil((double) totalCount / pageSize);
		model.addAttribute("totalPages", totalPages);

		log.info("HotNewsMainList End");

		return "adm/content/hotnews/list";

	}

	/*
	 * 버튼 클릭조회
	 */
	@RequestMapping("/HotNewsQueryList")
	public @ResponseBody HashMap HotNewsQueryList(HttpServletRequest request, final NewsCommonModel paramVo,
			Model model, @RequestParam(defaultValue = "1") int page) throws Exception {

		log.info("HotNewsQueryList Start , page = " + page);

		HashMap result = new HashMap();
		log.info("HotNewsQueryList Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeQueryList 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsQueryList PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsQueryList 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 기관 핫뉴스 리스트 조회
		int pageSize = conConst.pageSize; // 페이지당 row 건수
		int pageNo = paramVo.getPageNo(); // 조회할 페이지 번호
		int sRowNum = ((pageNo - 1) * pageSize); // 조회할 row의 시작값
		log.info("HotNewsMainList {} ~ {}", sRowNum, pageSize);

		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setListSize(ConfigConstants.pageSize);
		paramVo.setPartyCd(sessInfo.getPartyCd());

		List<NewsCommonModel> dataList = svc.selectAdmList(paramVo);

		///////////////////////////////

		String totalCountStr = svc.selectAdmListCount(paramVo);

		int totalCount = 0;
		try {
			totalCount = Integer.parseInt(totalCountStr);
		} catch (NumberFormatException e) {
			totalCount = 0;
		}

		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		result.put("currentPage", pageNo); // 타임리프에 돌려줄 페이지번호
		result.put("totalCount", totalCountStr);
		result.put("totalPages", totalPages);

		///////////////////////////////

		result.put("rows", dataList); // jquery는 이 데이터만 사용.

		log.info("HotNewsMainList End");

		return result;
	}

	/*
	 * 신규등록
	 */
	@RequestMapping("/HotNewsIns.do")
	public String HotNewsIns(HttpServletRequest request, Model model) throws Exception {
		log.info("HotNewsIns Start");
		if (!sessMgr.isSession(request)) {
			log.info("AdmHotNewsIns 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsIns PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsIns 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);
		return "adm/content/hotnews/insert";
	}

	/*
	 * 저장
	 */
	@PostMapping("/HotNewsInsProc")
	public @ResponseBody HashMap HotNewsInsProc(HttpServletRequest request,
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile, final NewsCommonModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("HotNewsInsProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("HotNewsInsProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsInsProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("AdmNoticeList 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 파일 관련
		if (imgFile != null && !imgFile.isEmpty()) {
			String fileSavePath = fileUtil.imgServerBasePath
					+ fileUtil.getSaveFilePath("HotNews", DateUtil.getCurrDate());
			String fileUriPath = fileUtil.imgUriBasePath + fileUtil.getSaveFilePath("HotNews", DateUtil.getCurrDate());
			log.info("파일 이름: " + imgFile.getOriginalFilename());
			log.info("fileSavePath: {} ", fileSavePath);

			String fileExt = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = imgFile.getOriginalFilename();
			String imgNailNm = fileSavePath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt; // 중간에 점 제거
			String urlNailNm = fileUriPath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt;
			String tmpFileNm = fileSavePath + File.separator + originalFilename;

			log.info("파라머터: {}| {} | {} |  {}  ", originalFilename, fileExt, imgNailNm, tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			imgFile.transferTo(savedFile); // 업로드된 파일 저장

			// 썸네일 생성
			File thumbnailFile = new File(imgNailNm);
			Thumbnails.of(savedFile).size(700, 400).toFile(thumbnailFile);
			// file upload

			paramModel.setImgSrcNm(tmpFileNm);
			paramModel.setImgNailNm(urlNailNm);

		} else {
			log.info("imgFile is null ");
			paramModel.setImgSrcNm("");
			paramModel.setImgNailNm("");
		}

		// 저장
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.insert(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("HotNewsInsProc End");

		return result;
	}

	/*
	 * 기관 핫뉴스 상세 조회
	 */
	@RequestMapping("/HotNewsDesc.do")
	public String HotNewsDesc(HttpServletRequest request, final NewsCommonModel paramVo, Model model) throws Exception {

		log.info("HotNewsDesc Start");
		if (!sessMgr.isSession(request)) {
			log.info("AdmNoticeView 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsDesc PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsDesc 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		// 기관 핫뉴스 한건 조회

		NewsCommonModel rtnModel = svc.selectAdmDesc(paramVo);
		log.info("HotNewsDesc {}  {}", rtnModel.getImgSrcNm(), rtnModel.getImgNailNm());

		model.addAttribute("hsDocStat", commSvc.hsDocStat);
		model.addAttribute("sessInfo", sessInfo);

		model.addAttribute("docview", rtnModel);
		// model.addAttribute("menuList", menuList);
		log.info("HotNewsDesc End");

		return "adm/content/hotnews/update";
	}

	/*
	 * 기관 핫뉴스 저장
	 */
	@PostMapping("/HotNewsUptProc")
	public @ResponseBody HashMap HotNewsUptProc(HttpServletRequest request,
			// @RequestPart("imgFile") MultipartFile imgFile,
			@RequestPart(value = "imgFile", required = false) MultipartFile imgFile, final NewsCommonModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("HotNewsUptProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("HotNewsUptProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsUptProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsUptProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 파일 관련
		if (imgFile != null && !imgFile.isEmpty()) {
			String fileSavePath = fileUtil.imgServerBasePath
					+ fileUtil.getSaveFilePath("HotNews", DateUtil.getCurrDate());
			String fileUriPath = fileUtil.imgUriBasePath + fileUtil.getSaveFilePath("HotNews", DateUtil.getCurrDate());
			log.info("파일 이름: " + imgFile.getOriginalFilename());
			log.info("fileSavePath: {} ", fileSavePath);

			String fileExt = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = imgFile.getOriginalFilename();
			String imgNailNm = fileSavePath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt; // 중간에 점 제거
			String urlNailNm = fileUriPath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt;
			String tmpFileNm = fileSavePath + File.separator + originalFilename;

			log.info("파라머터: {}| {} | {} |  {}  ", originalFilename, fileExt, imgNailNm, tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			imgFile.transferTo(savedFile); // 업로드된 파일 저장

			// 썸네일 생성
			File thumbnailFile = new File(imgNailNm);
			Thumbnails.of(savedFile).size(700, 400).toFile(thumbnailFile);
			// file upload

			paramModel.setImgSrcNm(tmpFileNm);
			paramModel.setImgNailNm(urlNailNm);

		} else {
			log.info("imgFile is null ");
			paramModel.setImgSrcNm(paramModel.getBefImgSrcNme());
			paramModel.setImgNailNm(paramModel.getBefImgNailNme());
		}

		log.info("HotNewsUptProc , paramModel.getDocSeq() : " + paramModel.getDocSeq());
		log.info("HotNewsUptProc , paramModel.getDocTitle() : " + paramModel.getDocTitle());

		// 기관 핫뉴스 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.update(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("HotNewsUptProc End");

		return result;
	}

	/*
	 * 기관 핫뉴스 게시
	 */
	@RequestMapping("/HotNewsViewProc")
	public @ResponseBody HashMap HotNewsViewProc(HttpServletRequest request, final NewsCommonModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("HotNewsViewProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("HotNewsViewProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsViewProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsViewProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		paramModel.setDocStat("V"); // 상태 'V
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateShowStat(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("HotNewsViewProc End");

		return result;
	}

	/*
	 * 기관 핫뉴스 게시 취소
	 */
	@RequestMapping("/HotNewsCancelProc")
	public @ResponseBody HashMap HotNewsCancelProc(HttpServletRequest request, final NewsCommonModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("HotNewsCancelProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("HotNewsCancelProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsCancelProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("HotNewsCancelProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		paramModel.setDocStat("C"); // 취소 C
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateCancelStat(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("HotNewsCancelProc End");

		return result;
	}

	/*
	 * 기관 핫뉴스 삭제
	 */
	@RequestMapping("/HotNewsDelProc")
	public @ResponseBody HashMap HotNewsDelProc(HttpServletRequest request, final NewsCommonModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("HotNewsDelProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("HotNewsDelProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("HotNewsDelProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeCancelProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		paramModel.setDocStat("D"); // 상태 D
		paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateDelStat(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("HotNewsDelProc End");

		return result;
	}

}
