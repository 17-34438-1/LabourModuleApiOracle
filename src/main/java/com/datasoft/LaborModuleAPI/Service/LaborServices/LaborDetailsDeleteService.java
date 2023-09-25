package com.datasoft.LaborModuleAPI.Service.LaborServices;

import com.datasoft.LaborModuleAPI.Repository.LaborRepositories.LaborDetailsDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaborDetailsDeleteService {
    @Autowired
    private LaborDetailsDeleteRepository laborDeleteRepo;

    public Integer deleteAllLabor(){ return laborDeleteRepo.deleteAllLabor(); }

    public Boolean chkLaborAssignToGang(Long id){
        if(laborDeleteRepo.chkLaborAssignToGang(id) > 0) return true;
        else return false;
    }

    public Boolean chkLaborAssignToWorkplace(Long id){
        if(laborDeleteRepo.chkLaborAssignToWorkplace(id) > 0) return true;
        else return false;
    }

    public Boolean deleteLabor(Long id){
        if(laborDeleteRepo.deleteLabor(id) == 1) return true;
        else return false;
    }
}
