package com.datasoft.LaborModuleAPI.Service.GangServices;

import com.datasoft.LaborModuleAPI.Repository.GangRepositories.GangInsertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GangInsertService {
    @Autowired
    private GangInsertRepository gangRepository;

    public Integer isExist(String name){
        return gangRepository.isExist(name);
    }

    public Integer isWorkCategoryExist(Long work_category_id){
        return gangRepository.isWorkCategoryExist(work_category_id);
    }

    public Integer gangInsertQuery(String name, Long work_category_id ,Long berth_operator_id){
        return gangRepository.gangInsertQuery(name,work_category_id,berth_operator_id);
    }
}
