package com.ilmare.carbonbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilmare.carbonbank.mapper.content.CrbnQnaMapper;
import com.ilmare.carbonbank.model.content.NewsCommonModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CrbnQnaService {
	@Autowired
	public CrbnQnaMapper mapper;
	

	public NewsCommonModel selectAdmDesc(NewsCommonModel param){
		return mapper.selectAdmDesc(param);
	}

	/*
	 * 리스트 조회
	 */
	public List<NewsCommonModel> selectAdmList(NewsCommonModel param){
		return mapper.selectAdmList(param);
	}
	
	public String  selectAdmListCount(NewsCommonModel param){
		return mapper.selectAdmListCount(param);
	}



	public int  update(NewsCommonModel param){
		return mapper.update(param);
	}

	public int  updateShowStat(NewsCommonModel param){
		return mapper.updateShowStat(param);
	}

	public int  updateCancelStat(NewsCommonModel param){
		return mapper.updateCancelStat(param);
	}
	
}
