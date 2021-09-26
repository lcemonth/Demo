package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.CurrencyEntity;

public interface CurrencyDao extends JpaRepository<CurrencyEntity,Integer>{

	CurrencyEntity findByCurrencyCode(String currencyCode);

	
}
