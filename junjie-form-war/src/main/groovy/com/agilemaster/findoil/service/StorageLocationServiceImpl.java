package com.agilemaster.findoil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.StorageLocation;
import com.agilemaster.findoil.repository.StorageLocationRepository;

@Service
public class StorageLocationServiceImpl implements StorageLocationService{
	
	@Autowired
	StorageLocationRepository storageLocationRepository;

	@Override
	public List<StorageLocation> getStorageLocationList() {
		return storageLocationRepository.findAll();
	}

}
