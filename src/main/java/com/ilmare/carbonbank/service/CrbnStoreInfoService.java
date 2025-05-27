package com.ilmare.carbonbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilmare.carbonbank.mapper.content.CrbnStoreInfoMapper;
import com.ilmare.carbonbank.model.content.CrbnStoreInfoModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CrbnStoreInfoService {
	@Autowired
	public CrbnStoreInfoMapper mapper;
	

	public CrbnStoreInfoModel selectAdmDesc(CrbnStoreInfoModel param){
		return mapper.selectAdmDesc(param);
	}

	/*
	 * 리스트 조회
	 */
	public List<CrbnStoreInfoModel> selectAdmList(CrbnStoreInfoModel param){
		return mapper.selectAdmList(param);
	}
	
	public String  selectAdmListCount(CrbnStoreInfoModel param){
		return mapper.selectAdmListCount(param);
	}
	
	public int countCellNum(CrbnStoreInfoModel param){
		return mapper.countCellNum(param);
	}

	public int countBisNum(CrbnStoreInfoModel param){
		return mapper.countBisNum(param);
	}
	
	
	/*
	 * 등록
	 */
	public int insert(CrbnStoreInfoModel nModel)
	{
		return mapper.insert(nModel);
		
	}
	public int  update(CrbnStoreInfoModel param){
		return mapper.update(param);
	}

	
	
}
