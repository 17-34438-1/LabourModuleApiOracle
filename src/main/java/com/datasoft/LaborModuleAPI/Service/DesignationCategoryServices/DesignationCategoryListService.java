package com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories.DesignationCategoryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationCategoryListService {
    @Autowired
    private DesignationCategoryListRepository desig_cat_list_repo;

    ResponseMessage jsonMsg;

    public List<DesignationCategory> listDesignationCategory(){
        return desig_cat_list_repo.DesignationCategoryListQuery();
    }

    public DesignationCategory getDesigCategoryById(Long id){
        return desig_cat_list_repo.getDesignationCategoryById(id);
    }

    public List<DesignationCategory> designationCategoryListByLaborTypeId(Long labor_type_id){
        return desig_cat_list_repo.designationCategoryListByLaborTypeId(labor_type_id);
    }

    public List<DesignationCategory> laborTypeByDesignationId(Long id){
        return desig_cat_list_repo.laborTypeByDesignationId(id);
    }
}
