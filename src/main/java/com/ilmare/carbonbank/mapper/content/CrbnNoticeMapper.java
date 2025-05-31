package com.ilmare.carbonbank.mapper.content;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ilmare.carbonbank.cmn.vo.CommonVo;
import com.ilmare.carbonbank.model.content.CrbnNoticeModel;
import com.ilmare.carbonbank.model.content.NewsCommonModel;

@Repository
@Mapper
public interface CrbnNoticeMapper {
	CrbnNoticeModel selectAdmDesc(CommonVo commVo);
	CrbnNoticeModel selectBeforeAfter(CommonVo commVo);
	List<CrbnNoticeModel> selectAdmList(CommonVo commVo);
	String selectAdmListCount(CommonVo commVo);
	List<CrbnNoticeModel> selectList(CommonVo commVo);
	String selectListCount(CommonVo commVo);
	CrbnNoticeModel selectDesc(CommonVo commVo);
	int insert(CrbnNoticeModel crbnNotice);
	int update(CrbnNoticeModel crbnNotice);
	int updateShowStat(CrbnNoticeModel crbnNotice);
	int updateCancelStat(CrbnNoticeModel crbnNotice);
	int updateDelStat(CrbnNoticeModel crbnNotice);
	int addReadCnt(CrbnNoticeModel crbnNotice);
	List<CrbnNoticeModel> selectLatestList();

	
	
	
}
