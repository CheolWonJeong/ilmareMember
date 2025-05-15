package com.ilmare.carbonbank.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilmare.carbonbank.mapper.AdmInfoMapper;
import com.ilmare.carbonbank.model.CrbnAdmInfoModel;
import com.ilmare.carbonbank.model.CrbnAdmLgnHstModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AdmUserService {
	@Autowired(required=true)
	public AdmInfoMapper mapper;
	
	public List<CrbnAdmInfoModel> getAdmList(){
		return mapper.getAdmList();
	}
	
	public CrbnAdmInfoModel getAdmInfo(String crbnAdmId){
		return mapper.getAdmInfo(crbnAdmId);
	}
	
	
	public int loginHistSave(CrbnAdmLgnHstModel admLgnHst) throws Exception
	{
		//로그인 일시 update
		mapper.updateLgnDtm(admLgnHst.getCrbnAdmId());
		
		//로그인 이력 저장
		return mapper.insertLoginHst(admLgnHst);
	}
	
}
