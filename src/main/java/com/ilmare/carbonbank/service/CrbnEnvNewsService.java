package com.ilmare.carbonbank.service;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilmare.carbonbank.cmn.vo.CommonVo;
import com.ilmare.carbonbank.mapper.content.CrbnEnvNewsMapper;
import com.ilmare.carbonbank.model.content.CrbnEnvNewsModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CrbnEnvNewsService {
	@Autowired
	public CrbnEnvNewsMapper mapper;
	
	/*
	 * 관리자 리스트 조회
	 */
	public List<CrbnEnvNewsModel> selectAdmList(CrbnEnvNewsModel param){
		return mapper.selectAdmList(param);
	}
	
	/*
	 * 관리자 등록
	 */
	public int insert(CrbnEnvNewsModel nModel)
	{
		return mapper.insert(nModel);
		
	}
	
}
