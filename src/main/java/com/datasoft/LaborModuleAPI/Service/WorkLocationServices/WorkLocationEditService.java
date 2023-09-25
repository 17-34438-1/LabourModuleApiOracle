package com.datasoft.LaborModuleAPI.Service.WorkLocationServices;

import com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository.WorkLocationEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkLocationEditService {
    @Autowired
    private WorkLocationEditRepository workLocEditRepository;

    public Boolean isUnique(Long id,String name){
        if(workLocEditRepository.isUnique(id,name) > 0) return true;
        else return false;
    }

    public Boolean WorkLocationUpdate(Long id,String name,Integer work_category_id){
        if(workLocEditRepository.WorkLocationUpdate(id,name,work_category_id) ==1) return true;
        else return false;
    }
}
