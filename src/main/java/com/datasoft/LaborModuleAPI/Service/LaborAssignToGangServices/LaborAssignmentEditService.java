package com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices;

import com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository.LaborAssignmentEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LaborAssignmentEditService {
    @Autowired
    private LaborAssignmentEditRepository laborAssignmentEditRepo;

    public Boolean laborAssignmentCnt(Long id,Long labor_id,Long gang_id){
        if(laborAssignmentEditRepo.laborAssignmentCnt(id,labor_id,gang_id) > 0) return true;
        else return false;
    }

    public Boolean gangExistenceCnt(Long gang_id){
        if(laborAssignmentEditRepo.gangExistenceCnt(gang_id) > 0) return true;
        else return false;
    }

    public Boolean workCategoryExistenceCnt(Long work_category_id){
        if(laborAssignmentEditRepo.workCategoryExistenceCnt(work_category_id) > 0) return true;
        else return false;
    }

    public Boolean laborExistenceCnt(Long labor_id){
        if(laborAssignmentEditRepo.laborExistenceCnt(labor_id) > 0) return true;
        else return false;
    }

    public Boolean LaborAssignmentUpdate(Long id,Long gang_id, Long labor_id){
        if(laborAssignmentEditRepo.LaborAssignmentUpdate(id,gang_id,labor_id) == 1) return true;
        else return false;
    }
}
