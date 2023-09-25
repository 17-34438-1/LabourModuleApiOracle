package com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories.DesignationCategoryInsertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignationCategoryInsertService {
    @Autowired
    private DesignationCategoryInsertRepository desigCatInsertRepository;

    public Boolean isLaborTypeExists(Long labor_type_id){
        if(desigCatInsertRepository.isLaborTypeExists(labor_type_id) == 1) return true;
        else return false;
    }

    public Boolean isExists(String desig_cat_name,Long labor_type_id){
        if(desigCatInsertRepository.isExists(desig_cat_name,labor_type_id) > 0) return true;
        else return false;
    }

    public Boolean DesignationCategoryAdd(DesignationCategory designationCategory){
        if(desigCatInsertRepository.insertDesigCatQuery(designationCategory.getName(),designationCategory.getLabor_type_id()) == 1) return true;
        else return false;
    }
}
