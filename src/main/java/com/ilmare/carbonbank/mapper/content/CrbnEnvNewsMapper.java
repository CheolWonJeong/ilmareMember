package com.ilmare.carbonbank.mapper.content;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ilmare.carbonbank.cmn.vo.CommonVo;
import com.ilmare.carbonbank.model.content.CrbnEnvNewsModel;

@Repository
@Mapper
public interface CrbnEnvNewsMapper {
	List<CrbnEnvNewsModel> selectAdmList(CrbnEnvNewsModel envModel);

	int insert(CrbnEnvNewsModel envModel);
	
}
