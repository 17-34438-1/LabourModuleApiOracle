package com.datasoft.LaborModuleAPI.Service.WorkCategoryServices;

import com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories.WorkCategoryInsertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkCategoryInsertService {
    @Autowired
    private WorkCategoryInsertRepository work_cat_insert_repo;

    public Boolean isExist(String name){
        if(work_cat_insert_repo.isExist(name) > 0) return true;
        else return false;
    }

    public Boolean workCategoryInsert(String name){
        if(work_cat_insert_repo.workCategoryInsertQuery(name) == 1) return true;
        else return false;
    }
}
