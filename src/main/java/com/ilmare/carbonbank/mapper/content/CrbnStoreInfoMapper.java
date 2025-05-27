package com.ilmare.carbonbank.mapper.content;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ilmare.carbonbank.model.content.CrbnStoreInfoModel;

@Repository
@Mapper
public interface CrbnStoreInfoMapper {
	CrbnStoreInfoModel selectAdmDesc(CrbnStoreInfoModel commModel);
	//CrbnStoreInfoModel selectBeforeAfter(CrbnStoreInfoModel commModel);
	List<CrbnStoreInfoModel> selectAdmList(CrbnStoreInfoModel commModel);
	String selectAdmListCount(CrbnStoreInfoModel commModel);
	
	int countCellNum(CrbnStoreInfoModel commModel);	
	int countBisNum(CrbnStoreInfoModel commModel);	
	
	//List<CrbnStoreInfoModel> selectList(CrbnStoreInfoModel commModel);
	//String selectListCount(CrbnStoreInfoModel commModel);
	//CrbnStoreInfoModel selectDesc(CrbnStoreInfoModel commModel);
	int insert(CrbnStoreInfoModel commModel);
	int update(CrbnStoreInfoModel commModel);

}
