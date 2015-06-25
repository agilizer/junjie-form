package com.agilemaster.findoil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.dao.ResourceDao;
import com.agilemaster.findoil.domain.Resource;


@Service

public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource createResource(Resource resource) {
        return resourceDao.createResource(resource);
    }

    @Override
    public Resource updateResource(Resource resource) {
        return resourceDao.updateResource(resource);
    }

    @Override
    public void deleteResource(Long resourceId) {
        resourceDao.deleteResource(resourceId);
    }

    @Override
    public Resource findOne(Long resourceId) {
        return resourceDao.findOne(resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    
}
