package com.example.service;

import java.util.List;

import com.example.vo.CoindeskApiVo;
import com.example.vo.CoindeskVo;

public interface CoindeskService {

	public CoindeskApiVo findCoindeskApiV1();
	public List<CoindeskVo> findCoindeskApiV2();
}
