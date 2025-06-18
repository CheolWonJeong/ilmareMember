package com.ilmare.carbonbank.content.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ilmare.carbonbank.model.content.CrbnStoreInfoModel;
import com.ilmare.carbonbank.model.content.NewsCommonModel;
import com.ilmare.carbonbank.service.CrbnStoreInfoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/*
 * 가맹점 정보 관리
 */
@Slf4j
@Controller
@RequestMapping("/adm/store")
public class CrbnStoreInfoController {
	@Autowired(required = true)
	private SessionManager sessMgr;

	@Autowired
	private CrbnStoreInfoService svc;

	@Autowired
	private CommonService commSvc;

	@Autowired
	private ConfigConstants conConst;

	@Autowired
	private FileUtil fileUtil;

	/*
	 * 가맹점 정보 리스트 조회
	 */
	@RequestMapping("/StoreInfoMainList.do")
	public String StoreInfoMainList(HttpServletRequest request, final CrbnStoreInfoModel paramVo, Model model,
			@RequestParam(defaultValue = "1") int page) throws Exception {

		log.info("StoreInfoMainList Start , page = " + page);

		if (!sessMgr.isSession(request)) {
			log.info("StoreInfoMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("StoreInfoMainList PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("StoreInfoMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		// 가맹점 정보 리스트 조회
		int pageSize = conConst.pageSize; // 페이지당 row 건수
		int pageNo = page; // 조회할 페이지 번호
		int sRowNum = ((pageNo - 1) * pageSize); // 조회할 row의 시작값

		log.info("StoreInfoMainList {} ~ {}", sRowNum, pageSize);
		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setPartyCd(sessInfo.getPartyCd());

		List<CrbnStoreInfoModel> ntsList = svc.selectAdmList(paramVo);
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

		log.info("StoreInfoMainList End");

		return "adm/content/store/list";

	}

	/*
	 * 버튼 클릭조회
	 */
	@RequestMapping("/StoreInfoQueryList")
	public @ResponseBody HashMap StoreInfoQueryList(HttpServletRequest request, final CrbnStoreInfoModel paramVo,
			Model model, @RequestParam(defaultValue = "1") int page) throws Exception {

		HashMap result = new HashMap();
		log.info("StoreInfoQueryList Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeQueryList 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("StoreInfoQueryList PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("StoreInfoQueryList 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 가맹점 정보 리스트 조회
		int pageSize = conConst.pageSize; // 페이지당 row 건수
		int pageNo = paramVo.getPageNo(); // 조회할 페이지 번호
		int sRowNum = ((pageNo - 1) * pageSize); // 조회할 row의 시작값
		log.info("StoreInfoMainList {} ~ {}", sRowNum, pageSize);

		paramVo.setPageNo(sRowNum);
		paramVo.setListSize(pageSize);
		paramVo.setListSize(ConfigConstants.pageSize);
		paramVo.setPartyCd(sessInfo.getPartyCd());

		List<CrbnStoreInfoModel> dataList = svc.selectAdmList(paramVo);

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

		log.info("StoreInfoQueryList End");

		return result;
	}

	/*
	 * 신규등록
	 */
	@RequestMapping("/StoreInfoIns.do")
	public String StoreInfoIns(HttpServletRequest request, Model model) throws Exception {
		log.info("StoreInfoIns Start");
		if (!sessMgr.isSession(request)) {
			log.info("AdmStoreInfoIns 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("StoreInfoIns PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("StoreInfoIns 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);
		return "adm/content/store/insert";
	}

	/*
	 * 저장
	 */
	@PostMapping("/StoreInfoInsProc")
	public @ResponseBody HashMap StoreInfoInsProc(HttpServletRequest request,
			@RequestPart(value = "storeNoImgFile", required = false) MultipartFile storeNoImgFile,
			@RequestPart(value = "storeImgFile", required = false) MultipartFile storeImgFile,
			final CrbnStoreInfoModel paramModel, Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("StoreInfoInsProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("StoreInfoInsProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("StoreInfoInsProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("AdmNoticeList 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		log.info("StoreInfoInsProc countCellNum=" + svc.countCellNum(paramModel));
		log.info("StoreInfoInsProc countBisNum=" + svc.countBisNum(paramModel));

		// 휴대폰 & 사업자번호
		if (svc.countCellNum(paramModel) > 0) {
			result.put("procInd", "E");
			result.put("errorId", "dupCellNum");
			result.put("errorMsg", "휴대폰 번호가 중복되었습니다.");
			return result;
		}
		if (svc.countBisNum(paramModel) > 0) {
			result.put("procInd", "E");
			result.put("errorId", "dupBisNum");
			result.put("errorMsg", "사업자 번호가 중복되었습니다.");
			return result;
		}

		// 파일 관련
		if (storeNoImgFile != null && !storeNoImgFile.isEmpty()) {
			String fileSavePath = fileUtil.imgServerBasePath
					+ fileUtil.getSaveFilePath("StoreBis", DateUtil.getCurrDate());

			log.info("파일 이름: " + storeNoImgFile.getOriginalFilename());
			log.info("fileSavePath: {} ", fileSavePath);

			String fileExt = storeNoImgFile.getOriginalFilename()
					.substring(storeNoImgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = storeNoImgFile.getOriginalFilename();
			String tmpFileNm = fileSavePath + File.separator + originalFilename;

			log.info("파라미터: {}| {} | {}   ", originalFilename, fileExt, tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			storeNoImgFile.transferTo(savedFile); // 업로드된 파일 저장

			paramModel.setStoreNoImg(tmpFileNm);

		} else {
			log.info("storeNoImgFile is null ");
			paramModel.setStoreNoImg("");
		}

		// 파일 관련
		if (storeImgFile != null && !storeImgFile.isEmpty()) {
			String fileSavePath = fileUtil.imgServerBasePath
					+ fileUtil.getSaveFilePath("Store", DateUtil.getCurrDate());
			String fileUriPath = fileUtil.imgUriBasePath + fileUtil.getSaveFilePath("Store", DateUtil.getCurrDate());

			log.info("파일 이름: " + storeImgFile.getOriginalFilename());
			log.info("fileSavePath: {} ", fileSavePath);

			String fileExt = storeImgFile.getOriginalFilename()
					.substring(storeImgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = storeImgFile.getOriginalFilename();
			String imgNailNm = fileSavePath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt; // 중간에 점 제거
			String urlNailNm = fileUriPath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt;
			String tmpFileNm = fileSavePath + File.separator + originalFilename;

			log.info("파라미터: {}| {} | {} |  {}  ", originalFilename, fileExt, imgNailNm, tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			storeImgFile.transferTo(savedFile); // 업로드된 파일 저장

			// 썸네일 생성
			File thumbnailFile = new File(imgNailNm);
			Thumbnails.of(savedFile).size(700, 400).toFile(thumbnailFile);

			paramModel.setStoreImg(urlNailNm);

		} else {
			log.info("storeImgFile is null ");
			paramModel.setStoreImg("");
		}

		// 저장
		paramModel.setPartyCd(sessInfo.getPartyCd());
		// paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.insert(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("StoreInfoInsProc End");

		return result;
	}

	/*
	 * 가맹점 정보 상세 조회
	 */
	@RequestMapping("/StoreInfoDesc.do")
	public String StoreInfoDesc(HttpServletRequest request, final CrbnStoreInfoModel paramVo, Model model)
			throws Exception {

		log.info("StoreInfoDesc Start");
		if (!sessMgr.isSession(request)) {
			log.info("AdmNoticeView 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("StoreInfoDesc PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("StoreInfoDesc 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		// 가맹점 정보 한건 조회
		CrbnStoreInfoModel rtnModel = svc.selectAdmDesc(paramVo);

		model.addAttribute("hsDocStat", commSvc.hsDocStat);
		model.addAttribute("sessInfo", sessInfo);

		model.addAttribute("docview", rtnModel);
		// model.addAttribute("menuList", menuList);
		log.info("StoreInfoDesc End");

		return "adm/content/store/update";
	}

	/*
	 * 가맹점 정보 저장
	 */
	@PostMapping("/StoreInfoUptProc")
	public @ResponseBody HashMap StoreInfoUptProc(HttpServletRequest request,
			@RequestPart(value = "storeNoImgFile", required = false) MultipartFile storeNoImgFile,
			@RequestPart(value = "storeImgFile", required = false) MultipartFile storeImgFile,
			final CrbnStoreInfoModel paramModel, Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("StoreInfoUptProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("StoreInfoUptProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("StoreInfoUptProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("StoreInfoUptProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		log.info("StoreInfoInsProc countCellNum=" + svc.countCellNum(paramModel));
		log.info("StoreInfoInsProc countBisNum=" + svc.countBisNum(paramModel));

		// 휴대폰 & 사업자번호
		if (svc.countCellNum(paramModel) > 0) {
			result.put("procInd", "E");
			result.put("errorId", "dupCellNum");
			result.put("errorMsg", "휴대폰 번호가 중복되었습니다.");
			return result;
		}
		if (svc.countBisNum(paramModel) > 0) {
			result.put("procInd", "E");
			result.put("errorId", "dupBisNum");
			result.put("errorMsg", "사업자 번호가 중복되었습니다.");
			return result;
		}

		// 파일 관련
		if (storeNoImgFile != null && !storeNoImgFile.isEmpty()) {
			String fileSavePath = fileUtil.imgServerBasePath
					+ fileUtil.getSaveFilePath("StoreBis", DateUtil.getCurrDate());

			log.info("파일 이름: " + storeNoImgFile.getOriginalFilename());
			log.info("fileSavePath: {} ", fileSavePath);

			String fileExt = storeNoImgFile.getOriginalFilename()
					.substring(storeNoImgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = storeNoImgFile.getOriginalFilename();
			String tmpFileNm = fileSavePath + File.separator + originalFilename;

			log.info("파라미터: {}| {} | {}  ", originalFilename, fileExt, tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			storeNoImgFile.transferTo(savedFile); // 업로드된 파일 저장

			paramModel.setStoreNoImg(tmpFileNm);

		} else {
			log.info("storeNoImgFile is null ");
			paramModel.setStoreNoImg(paramModel.getBefStoreNoImg());
		}

		// 파일 관련
		if (storeImgFile != null && !storeImgFile.isEmpty()) {
			String fileSavePath = fileUtil.imgServerBasePath
					+ fileUtil.getSaveFilePath("Store", DateUtil.getCurrDate());
			String fileUriPath = fileUtil.imgUriBasePath + fileUtil.getSaveFilePath("Store", DateUtil.getCurrDate());

			log.info("파일 이름: " + storeImgFile.getOriginalFilename());
			log.info("fileSavePath: {} ", fileSavePath);

			String fileExt = storeImgFile.getOriginalFilename()
					.substring(storeImgFile.getOriginalFilename().lastIndexOf("."));
			String originalFilename = storeImgFile.getOriginalFilename();
			String imgNailNm = fileSavePath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt; // 중간에 점 제거
			String urlNailNm = fileUriPath + File.separator + "640" + DateUtil.getCurrDateTime() + fileExt;
			String tmpFileNm = fileSavePath + File.separator + originalFilename;

			log.info("파라미터: {}| {} | {} |  {}  ", originalFilename, fileExt, imgNailNm, tmpFileNm);

			FileUtil.createDirectory(fileSavePath);
			File savedFile = new File(tmpFileNm);
			storeImgFile.transferTo(savedFile); // 업로드된 파일 저장

			// 썸네일 생성
			File thumbnailFile = new File(imgNailNm);
			Thumbnails.of(savedFile).size(700, 400).toFile(thumbnailFile);
			paramModel.setStoreImg(urlNailNm);

		} else {
			log.info("storeImgFile is null ");
			paramModel.setStoreImg(paramModel.getBefStoreImg());
		}

		// 가맹점 정보 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		// paramModel.setRegId(sessInfo.getCrbnAdmId());
		int rtn = svc.update(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("StoreInfoUptProc End");

		return result;
	}

}
