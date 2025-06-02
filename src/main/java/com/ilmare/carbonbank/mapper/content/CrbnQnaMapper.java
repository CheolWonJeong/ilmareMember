package com.ilmare.carbonbank.mapper.content;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ilmare.carbonbank.model.content.NewsCommonModel;

@Repository
@Mapper
public interface CrbnQnaMapper {
	
	NewsCommonModel selectAdmDesc(NewsCommonModel commModel);

	List<NewsCommonModel> selectAdmList(NewsCommonModel commModel);
	String selectAdmListCount(NewsCommonModel commModel);

	int update(NewsCommonModel commModel);
	int updateShowStat(NewsCommonModel commModel);
	int updateCancelStat(NewsCommonModel commModel);
	
}
