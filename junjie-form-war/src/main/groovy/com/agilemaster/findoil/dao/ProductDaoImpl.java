package com.agilemaster.findoil.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.agilemaster.findoil.repository.ResourceRepository;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
    private ResourceRepository resourceRepository;
	
}
