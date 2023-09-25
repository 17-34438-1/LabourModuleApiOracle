package com.datasoft.LaborModuleAPI.Service.WorkCategoryServices;

import com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories.WorkCategoryEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkCategoryEditService {
    @Autowired
    private WorkCategoryEditRepository workCatEditRepository;

    public Boolean isUnique(Long id,String name){
        if(workCatEditRepository.isUnique(id,name) > 0) return true;
        else return false;
    }

    public Boolean WorkCategoryUpdate(Long id,String name){
        if(workCatEditRepository.WorkCategoryUpdate(id,name) == 1) return true;
        else return false;
    }
}
