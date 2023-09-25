package com.datasoft.LaborModuleAPI.Service.GangServices;

import com.datasoft.LaborModuleAPI.Repository.GangRepositories.GangDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GangDeleteService {
    @Autowired
    private GangDeleteRepository gang_dlt_repo;

    public Integer deleteAllGang(){ return gang_dlt_repo.deleteAllGang(); }

    public Integer chkLaborAssignToGangCnt(Long id){
        return gang_dlt_repo.chkLaborAssignToGang(id);
    }

    public Integer chkGangAssignToWorkplace(Long id){
        return gang_dlt_repo.chkGangAssignToWorkplace(id);
    }

    public Integer deleteGangById(Long id){
        return gang_dlt_repo.deleteGangById(id);
    }
}
