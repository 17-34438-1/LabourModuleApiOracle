package com.datasoft.LaborModuleAPI.Service.WorkLocationServices;

import com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository.WorkLocationDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkLocationDeleteService {
    @Autowired
    private WorkLocationDeleteRepository workLocDeleteRepository;

    public Integer deleteAllWorkLocation(){ return workLocDeleteRepository.deleteAllWorkLocation(); }

    public Boolean chkGangAssign(Long id){
        if(workLocDeleteRepository.chkGangAssign(id) > 0) return true;
        else return false;
    }

    public Boolean deleteWorkLocationById(Long id){
        if(workLocDeleteRepository.deleteWorkLocationById(id)==1) return true;
        else return false;
    }
}
