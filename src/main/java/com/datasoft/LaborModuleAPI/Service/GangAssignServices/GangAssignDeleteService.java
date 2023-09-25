package com.datasoft.LaborModuleAPI.Service.GangAssignServices;

import com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories.GangAssignDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GangAssignDeleteService {
    @Autowired
    private GangAssignDeleteRepository gangAssignDeleteService;

    public Integer deleteAllGangAssignment(){ return gangAssignDeleteService.deleteAllGangAssignment(); }

    public Boolean deleteGangAssignmentById(Long id){
        if(gangAssignDeleteService.deleteGangAssignmentById(id) == 1) return true;
        else return false;
    }
}
