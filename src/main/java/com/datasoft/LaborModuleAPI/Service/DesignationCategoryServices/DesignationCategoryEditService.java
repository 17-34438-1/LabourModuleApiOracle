package com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices;

import com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories.DesignationCategoryEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignationCategoryEditService {
    @Autowired
    private DesignationCategoryEditRepository desig_cat_list_repo;

    public Boolean chkCategoryforEdit(Long id,Long labor_type_id,String name){
        if(desig_cat_list_repo.chkCategoryforEdit(id,labor_type_id,name) > 0) return true;
        else return false;
    }

    public Boolean updateDesignationCategory(Long id,Long labor_type_id,String name){
        if(desig_cat_list_repo.updateDesignationCategory(id,labor_type_id,name)==1) return true;
        else return false;
    }
}
