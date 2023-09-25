package com.datasoft.LaborModuleAPI.Service.WorkLocationServices;

import com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository.WorkLocationInsertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkLocationInsertService {
    @Autowired
    private WorkLocationInsertRepository work_location_insert_repo;

    public Boolean isExists(String name){
        if(work_location_insert_repo.isExists(name) > 0) return true;
        else return false;
    }

    public Boolean workLocationInsert(String name, Integer work_category_id){
        if(work_location_insert_repo.workLocationInsert(name,work_category_id) == 1) return true;
        else return false;
    }
}
