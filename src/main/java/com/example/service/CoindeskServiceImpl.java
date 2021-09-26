package com.example.service;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.dao.CurrencyDao;
import com.example.entity.CurrencyEntity;
import com.example.vo.CoindeskApiVo;
import com.example.vo.CoindeskDateVo;
import com.example.vo.CoindeskVo;
import com.google.gson.Gson;

@Component
public class CoindeskServiceImpl implements CoindeskService {

	private static final String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	static final Logger log = LoggerFactory.getLogger(CoindeskServiceImpl.class);
	
	@Autowired
	private CurrencyDao currencyDao;
	
	@Override
	public CoindeskApiVo findCoindeskApiV1() {


		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> params = new HashMap<>();

		String coindeskJsonStr = restTemplate.getForObject(URL, String.class, params);

		Gson gson = new Gson();
		CoindeskApiVo coindeskApiVo = gson.fromJson(coindeskJsonStr, CoindeskApiVo.class);

		return coindeskApiVo;
	}
	
	@Override
	public List<CoindeskVo> findCoindeskApiV2() {
		

		log.info("findCoindeskDatas method");
		

		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> params = new HashMap<>();

		String coindeskJsonStr = restTemplate.getForObject(URL, String.class, params);
		Map<String,Object> bpiMap = new JSONObject(coindeskJsonStr).getJSONObject("bpi").toMap();

		//取得key
		List<String> mapKeyList = bpiMap.keySet().stream().map(key->key).collect(Collectors.toList());
		
		//取得時間
		String updateDateStr = 	new JSONObject(coindeskJsonStr).getJSONObject("time").getString("updated");

		log.info("findCoindeskDatas method mapKeyList = " + mapKeyList.toString());
		log.info("findCoindeskDatas method updateDateStr = " + updateDateStr);
		
		String updateDate = myFormatDate(updateDateStr);	//轉換

		List<CoindeskVo> coindeskVoList = mapKeyList.stream().map(key->{
			Map<String,Object> bpjDataMap =  (Map<String, Object>) bpiMap.get(key);
			String rate = (String) bpjDataMap.get("rate");
			
			CurrencyEntity currencyEntity = currencyDao.findByCurrencyCode(key);

			CoindeskVo coindeskVo = new CoindeskVo();
			
			if(currencyEntity != null) {
				BeanUtils.copyProperties(currencyEntity, coindeskVo);
			}else{
				coindeskVo.setCurrencyCode(key);
				coindeskVo.setCurrencyName("暫無資訊!");
			}

			coindeskVo.setExchangeRate(rate);
			coindeskVo.setUpdateDate(updateDate);
			return coindeskVo;

		}).collect(Collectors.toList());

		log.info("findCoindeskDatas method coindeskVoList = " + coindeskVoList.toString());
		return coindeskVoList;
		
	}

	private String myFormatDate(String dateStr) {
		
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("MMM dd, HH:mm:ss yyyy zzz", Locale.US);
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			date = sdf1.parse(dateStr);
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		return sdf2.format(date);
	}

	
}
