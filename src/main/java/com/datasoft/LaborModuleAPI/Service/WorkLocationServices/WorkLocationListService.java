package com.datasoft.LaborModuleAPI.Service.WorkLocationServices;

import com.datasoft.LaborModuleAPI.Model.WorkLocation;
import com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository.WorkLocationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLocationListService {
    @Autowired
    private WorkLocationListRepository workLocationListRepository;

    public List<WorkLocation> workLocationList(){ return workLocationListRepository.workLocationList(); }

    public WorkLocation workLocationById(Long id){ return workLocationListRepository.workLocationById(id);}

    public Long getWorkCategoryByGangId(Long gang_id){
        return workLocationListRepository.getWorkCategorybyGangId(gang_id);
    }

    public List<WorkLocation> workLocationListByWorkCategory(Long work_category_id){
        return workLocationListRepository.workLocationListByWorkCategory(work_category_id);
    }
}
