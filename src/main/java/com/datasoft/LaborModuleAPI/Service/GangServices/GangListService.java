package com.datasoft.LaborModuleAPI.Service.GangServices;

import com.datasoft.LaborModuleAPI.Model.Gang;
import com.datasoft.LaborModuleAPI.Repository.GangRepositories.GangListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GangListService {
    @Autowired
    private GangListRepository gang_repo;

    public List<Gang> gangList(){
        return gang_repo.gangList();
    }

    public List<Gang> gangListByOrgType(Integer user_role_id, Integer org_id){
        if(user_role_id==1 || org_id.equals(1) || org_id.equals("1")){
            return gang_repo.gangList();
        } else {
            return gang_repo.gangListByBerthOperator(org_id);
        }

    }

    public Gang gangById(Long id){
        return gang_repo.gangById(id);
    }

    public List<Gang> gangListByOrg(Long berth_operator_id){
        return gang_repo.gangListByOrg(berth_operator_id);
    }

    public List<Gang> gangListByOrgAndWorkCategory(Long berth_operator_id, Long work_category_id){
        return gang_repo.gangListByOrgAndWorkCategory(berth_operator_id,work_category_id);
    }

}
