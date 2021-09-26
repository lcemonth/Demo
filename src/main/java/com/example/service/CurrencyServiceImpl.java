package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CurrencyDao;
import com.example.entity.CurrencyEntity;
import com.example.vo.CurrencyVo;

@Component
@Transactional
public class CurrencyServiceImpl implements CurrencyService{

	
	static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
	
	@Autowired
	private CurrencyDao currencyDao;


	@Override
	public void addCurrency(CurrencyVo currencyVo) {
		
		log.info("addCurrency method: currencyVo = " + currencyVo.toString());
		
		CurrencyEntity currencyEntity = new CurrencyEntity();
		BeanUtils.copyProperties(currencyVo, currencyEntity);
		
		currencyEntity = currencyDao.save(currencyEntity);
	}

	@Override
	public CurrencyVo updCurrency(Integer currencyId, CurrencyVo currencyVo) {

		log.info("updCurrency method: currencyId = " + currencyId);
		log.info("updCurrency method: currencyVo = " + currencyVo.toString());
		
		CurrencyEntity findByCurrencyEntity = currencyDao.findById(currencyId).orElse(null);

		
		CurrencyEntity currencyEntity = null;
		
		if(findByCurrencyEntity != null) {

			log.info("updCurrency method: findByCurrencyEntity = " + findByCurrencyEntity.toString());
			
			BeanUtils.copyProperties(currencyVo, findByCurrencyEntity);
			currencyEntity = currencyDao.save(findByCurrencyEntity);
			BeanUtils.copyProperties(currencyEntity, currencyVo);

			return currencyVo;
		}else {
			return null;
		}
	}

	@Override
	public CurrencyVo delCurrency(Integer currencyId) {
		
		log.info("delCurrency method: currencyId = " + currencyId);
		
		
		CurrencyEntity findByCurrencyEntity = currencyDao.findById(currencyId).orElse(null);
		
		CurrencyVo currencyVo = null;
		
		if(findByCurrencyEntity != null) {

			log.info("delCurrency method: findByCurrencyEntity = " + findByCurrencyEntity.toString());
			
			currencyDao.deleteById(currencyId);
			
			currencyVo = new CurrencyVo();
			BeanUtils.copyProperties(findByCurrencyEntity, currencyVo);
			
			return currencyVo;
		}else {

			return null;
		}
	}
	
	@Override
	public CurrencyVo findByid(Integer currencyId) {

		log.info("findByid method: currencyId = " + currencyId);
		
		CurrencyEntity currencyEntity = currencyDao.findById(currencyId).orElse(null);
		
		if(currencyEntity != null) {
			
			CurrencyVo currencyVo = new CurrencyVo();
			BeanUtils.copyProperties(currencyEntity, currencyVo);
			
			return currencyVo;
		}else {
			return null;
		}
	}


	@Override
	public CurrencyVo findByCurrencyCode(String currencyCode) {
		
		log.info("findByid method: currencyCode = " + currencyCode);
		
		CurrencyEntity currencyEntity = currencyDao.findByCurrencyCode(currencyCode);
		CurrencyVo currencyVo = new CurrencyVo();
		BeanUtils.copyProperties(currencyEntity, currencyVo);
		
		return currencyVo;
	}
	
	@Override
	public List<CurrencyVo> findAllCurrency() {
		log.info("findAllCurrency method");
		
		List<CurrencyEntity> currencyEntityList = currencyDao.findAll();
		
		List<CurrencyVo> currencyVoList = currencyEntityList.stream().map(emt ->{
			CurrencyVo currencyVo = new CurrencyVo();
			BeanUtils.copyProperties(emt, currencyVo);
			return currencyVo;
		}).collect(Collectors.toList());
		
		log.info("findAllCurrency method: CurrencyVo = "+ currencyEntityList.toString());
		
		return currencyVoList;
	}


}
