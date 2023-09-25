package com.datasoft.LaborModuleAPI.Service.WorkCategoryServices;

import com.datasoft.LaborModuleAPI.Model.WorkCategory;
import com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories.WorkCategoryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkCategoryListService {
    @Autowired
    private WorkCategoryListRepository work_cat_list_repo;

    public List<WorkCategory> workCategoryList(){
        return work_cat_list_repo.workCategoryList();
    }

    public WorkCategory workCategoryListById(Long id){
        return work_cat_list_repo.workCategoryListById(id);
    }
}
