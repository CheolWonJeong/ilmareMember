package com.ilmare.carbonbank.mapper.content;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ilmare.carbonbank.model.content.NewsCommonModel;

@Repository
@Mapper
public interface CrbnFaqMapper {
	NewsCommonModel selectAdmDesc(NewsCommonModel commModel);
	NewsCommonModel selectBeforeAfter(NewsCommonModel commModel);
	List<NewsCommonModel> selectAdmList(NewsCommonModel commModel);
	String selectAdmListCount(NewsCommonModel commModel);
	List<NewsCommonModel> selectList(NewsCommonModel commModel);
	String selectListCount(NewsCommonModel commModel);
	NewsCommonModel selectDesc(NewsCommonModel commModel);
	int insert(NewsCommonModel commModel);
	int update(NewsCommonModel commModel);
	int updateShowStat(NewsCommonModel commModel);
	int updateCancelStat(NewsCommonModel commModel);
	int updateDelStat(NewsCommonModel commModel);
	int addReadCnt(NewsCommonModel commModel);
	int addRcmndCnt(NewsCommonModel commModel);
	int addLikeCnt(NewsCommonModel commModel);
	int addSadCnt(NewsCommonModel commModel);
	int addAngryCnt(NewsCommonModel commModel);
	List<NewsCommonModel> selectLatestList();
	
}
