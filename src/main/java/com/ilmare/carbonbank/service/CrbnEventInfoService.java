package com.ilmare.carbonbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilmare.carbonbank.mapper.content.CrbnEventInfoMapper;
import com.ilmare.carbonbank.model.content.NewsCommonModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CrbnEventInfoService {
	@Autowired
	public CrbnEventInfoMapper mapper;
	

	public NewsCommonModel selectAdmDesc(NewsCommonModel param){
		return mapper.selectAdmDesc(param);
	}

	public NewsCommonModel selectBeforeAfter(NewsCommonModel param){
		return mapper.selectBeforeAfter(param);
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

	
	public List<NewsCommonModel>  selectList(NewsCommonModel param){
		return mapper.selectList(param);
	}

	public String  selectListCount(NewsCommonModel param){
		return mapper.selectListCount(param);
	}

	public NewsCommonModel  selectDesc(NewsCommonModel param){
		return mapper.selectDesc(param);
	}

	/*
	 * 등록
	 */
	public int insert(NewsCommonModel nModel)
	{
		return mapper.insert(nModel);
		
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

	public int  updateDelStat(NewsCommonModel param){
		return mapper.updateDelStat(param);
	}

	public int  addReadCnt(NewsCommonModel param){
		return mapper.addReadCnt(param);
	}

	public int  addRcmndCnt(NewsCommonModel param){
		return mapper.addRcmndCnt(param);
	}

	public int  addLikeCnt(NewsCommonModel param){
		return mapper.addLikeCnt(param);
	}

	public int  addSadCnt(NewsCommonModel param){
		return mapper.addSadCnt(param);
	}

	public int  addAngryCnt(NewsCommonModel param){
		return mapper.addAngryCnt(param);
	}

	public List<NewsCommonModel>  selectLatestList(){
		return mapper.selectLatestList();
	}

	
	
}
