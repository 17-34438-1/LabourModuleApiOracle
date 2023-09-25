package com.datasoft.LaborModuleAPI.Service.GangServices;

import com.datasoft.LaborModuleAPI.Repository.GangRepositories.GangEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GangEditService {
    @Autowired
    private GangEditRepository gangEditRepo;

    public Boolean isExist(Long id,String name){
        if(gangEditRepo.isExist(id,name) > 0) return true;
        else return false;
    }

    public Boolean gangEdit(Long id,String name,Long work_category_id,Long berth_operator_id){
        if(gangEditRepo.gangEdit(id,name,work_category_id,berth_operator_id) == 1) return true;
        else return false;
    }
}
