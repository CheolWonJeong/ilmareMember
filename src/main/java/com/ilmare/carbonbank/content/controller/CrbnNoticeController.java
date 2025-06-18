package com.ilmare.carbonbank.content.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilmare.carbonbank.admin.mgr.SessInfo;
import com.ilmare.carbonbank.admin.mgr.SessionManager;
import com.ilmare.carbonbank.cmn.controller.ConfigConstants;
import com.ilmare.carbonbank.cmn.service.CommonService;
import com.ilmare.carbonbank.cmn.vo.CommonVo;
import com.ilmare.carbonbank.model.content.CrbnNoticeModel;
import com.ilmare.carbonbank.service.CrbnNoticeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
 * 공지사항 메인
 */
@Slf4j
@Controller
@RequestMapping("/adm/content")
public class CrbnNoticeController {

	@Autowired(required = true)
	private SessionManager sessMgr;

	@Autowired
	private CrbnNoticeService svc;

	@Autowired
	private CommonService commSvc;

	@Autowired
	private ConfigConstants conConst;

	/*
	 * 공지사항 관리자 리스트 조회
	 */
	@RequestMapping("/NoticeMainList.do")
	public String NoticeMainList(HttpServletRequest request, final CommonVo paramVo, Model model) throws Exception {

		log.info("NoticeMainList Start");

		if (!sessMgr.isSession(request)) {
			log.info("NoticeMainList 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeMainList PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeMainList 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);

		log.info("NoticeMainList End");

		return "adm/content/notice/list";
	}

	/*
	 * 공지사항버튼 클릭조회
	 */
	@RequestMapping("/NoticeQueryList")
	public @ResponseBody HashMap NoticeQueryList(HttpServletRequest request, final CommonVo CommonVo, Model model,
			@RequestParam(defaultValue = "1") int page) throws Exception {

		HashMap result = new HashMap();
		log.info("NoticeQueryList Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeQueryList 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeQueryList PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeQueryList 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 공지사항 리스트 조회
		int pageSize = conConst.pageSize; // 페이지당 row 건수
		int pageNo = CommonVo.getPageNo(); // 조회할 페이지 번호
		int sRowNum = ((pageNo - 1) * pageSize); // 조회할 row의 시작값
		log.info("EventQueryList {} ~ {}, partycd{}", sRowNum, pageSize, sessInfo.getPartyCd());

		CommonVo.setPageNo(sRowNum);
		CommonVo.setListSize(pageSize);
		CommonVo.setListSize(ConfigConstants.pageSize);
		CommonVo.setPartyCd(sessInfo.getPartyCd());

		List<CrbnNoticeModel> dataList = svc.selectAdmList(CommonVo);

		///////////////////////////////
		String totalCountStr = svc.selectAdmListCount(CommonVo);

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

		log.info("NoticeQueryList End");

		return result;
	}

	/*
	 * 신규등록
	 */
	@RequestMapping("/NoticeIns.do")
	public String NoticeIns(HttpServletRequest request, Model model) throws Exception {
		log.info("NoticeIns Start");
		if (!sessMgr.isSession(request)) {
			log.info("AdmNoticeIns 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeIns PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeIns 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		model.addAttribute("sessInfo", sessInfo);
		return "adm/content/notice/insert";
	}

	/*
	 * 공지사항 저장
	 */
	@RequestMapping("/NoticeInsProc")
	public @ResponseBody HashMap NoticeInsProc(HttpServletRequest request, final CrbnNoticeModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("NoticeInsProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeInsProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeInsProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("AdmNoticeList 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 공지사항 저장
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		int rtn = svc.insert(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("NoticeInsProc End");

		return result;
	}

	/*
	 * 공지사항 상세 조회
	 */
	@RequestMapping("/NoticeDesc.do")
	public String NoticeDesc(HttpServletRequest request, final CommonVo paramVo, Model model) throws Exception {

		log.info("NoticeDesc Start");
		if (!sessMgr.isSession(request)) {
			log.info("AdmNoticeView 세션 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeDesc PartyGrp=" + sessInfo.getPartyGrp());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeDesc 권한 없음 상태");
			return "redirect:" + conConst.lgnUrl;
		}

		CrbnNoticeModel rtnModel = svc.selectAdmDesc(paramVo);
		log.info("NoticeDesc " + rtnModel);

		model.addAttribute("hsDocStat", commSvc.hsDocStat);
		model.addAttribute("sessInfo", sessInfo);

		model.addAttribute("docview", rtnModel);
		// model.addAttribute("menuList", menuList);
		log.info("NoticeDesc End");

		return "adm/content/notice/update";
	}

	/*
	 * 공지사항 저장
	 */
	@RequestMapping("/NoticeUptProc")
	public @ResponseBody HashMap NoticeUptProc(HttpServletRequest request, final CrbnNoticeModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("NoticeUptProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeUptProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeUptProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeUptProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		// 공지사항 변경처리
		paramModel.setPartyCd(sessInfo.getPartyCd());
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		int rtn = svc.update(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("NoticeUptProc End");

		return result;
	}

	/*
	 * 공지사항 게시
	 */
	@RequestMapping("/NoticeViewProc")
	public @ResponseBody HashMap NoticeViewProc(HttpServletRequest request, final CrbnNoticeModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("NoticeViewProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeViewProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeViewProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeViewProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		paramModel.setDocStat("V"); // 상태 'V
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateShowStat(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("NoticeViewProc End");

		return result;
	}

	/*
	 * 공지사항 게시 취소
	 */
	@RequestMapping("/NoticeCancelProc")
	public @ResponseBody HashMap NoticeCancelProc(HttpServletRequest request, final CrbnNoticeModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("NoticeCancelProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeCancelProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeCancelProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeCancelProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		paramModel.setDocStat("C"); // 취소 C
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateCancelStat(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("NoticeCancelProc End");

		return result;
	}

	/*
	 * 공지사항 삭제
	 */
	@RequestMapping("/NoticeDelProc")
	public @ResponseBody HashMap NoticeDelProc(HttpServletRequest request, final CrbnNoticeModel paramModel,
			Model model) throws Exception {

		HashMap result = new HashMap();
		log.info("NoticeDelProc Start");
		if (!sessMgr.isSession(request)) {
			log.info("NoticeDelProc 세션 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotLogin"); // 오류 종류
			result.put("errorMsg", "로그인 후 이용 하세요"); // 오류 메시지
			return result;
		}
		SessInfo sessInfo = sessMgr.getSession(request);

		// 권한 검사
		log.info("NoticeDelProc getPartyCd=" + sessInfo.getPartyCd());
		if (!commSvc.checkContentUse(sessInfo.getPartyGrp())) {
			log.info("NoticeCancelProc 권한 없음 상태");
			result.put("procInd", "E"); // 오류
			result.put("errorId", "NotGrade"); // 오류 종류
			result.put("errorMsg", "조회 권한이 없습니다."); // 오류 메시지
			return result;
		}

		paramModel.setDocStat("D"); // 상태 D
		paramModel.setAuditId(sessInfo.getCrbnAdmId());
		int rtn = svc.updateDelStat(paramModel);

		result.put("procInd", "S"); // 정상
		log.info("NoticeDelProc End");

		return result;
	}

}
