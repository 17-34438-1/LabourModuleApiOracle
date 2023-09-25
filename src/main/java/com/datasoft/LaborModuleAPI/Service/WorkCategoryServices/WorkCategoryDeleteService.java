package com.datasoft.LaborModuleAPI.Service.WorkCategoryServices;

import com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories.WorkCategoryDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkCategoryDeleteService {
    @Autowired
    private WorkCategoryDeleteRepository work_cat_dlt_repo;

    public Boolean deleteAllWorkCategory(){
        if(work_cat_dlt_repo.deleteAllWorkCategory() == 1) return true;
        else return false;
    }

    public Boolean chkWorkLocation(Long id){
        if(work_cat_dlt_repo.chkWorkLocation(id) > 0) return true;
        else return false;
    }

    public Boolean isWorkCategoryExistInGang(Long id){
        if(work_cat_dlt_repo.chkWorkCategoryInGang(id) > 0) return true;
        else return false;
    }

    public Boolean deleteWorkCategoryById(Long id){
        if(work_cat_dlt_repo.deleteWorkCategoryById(id) == 1) return true;
        else return false;
    }
}
