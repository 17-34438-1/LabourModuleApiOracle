package com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories.DesignationCategoryDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignationCategoryDeleteService {
    @Autowired
    private DesignationCategoryDeleteRepository desig_cat_dlt_repo;

    ResponseMessage jsonMsg;

    public Integer deleteAllCategory(){
        return desig_cat_dlt_repo.deleteAllDesignationCategory();
    }

    public Integer chkLaborInfo(Long id){
        return desig_cat_dlt_repo.chkLaborInfo(id);
    }

    public Integer deleteCategoryById(Long id){
        return desig_cat_dlt_repo.deleteDesignationCategoryById(id);
    }
}
