package com.ilmare.carbonbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ilmare.carbonbank.model.CrbnAdmInfoModel;
import com.ilmare.carbonbank.model.CrbnAdmLgnHstModel;


@Repository
@Mapper
public interface AdmInfoMapper {
	List<CrbnAdmInfoModel> getAdmList();
	CrbnAdmInfoModel getAdmInfo(String crbnAdmId);
	int updateLgnDtm(String CrbnAdmId) throws Exception;
	int insertLoginHst(CrbnAdmLgnHstModel crbnAdmLgnHstModel) throws Exception;
	
}
