package com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices;

import com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository.LaborAssignToGangInsertRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LaborAssignToGangInsertService {
    @Autowired
    private LaborAssignToGangInsertRepository laborAssignInsertRepository;

    public Boolean laborAssignmentCnt(Long labor_id, Long gang_id){
        if(laborAssignInsertRepository.laborAssignmentCnt(labor_id,gang_id) > 0) return true;
        else return false;
    }

    public Boolean gangExistenceCnt(Long gang_id){
        if(laborAssignInsertRepository.gangExistenceCnt(gang_id) > 0) return true;
        else return false;
    }

    public Boolean laborExistenceCnt(Long labor_id){
        if(laborAssignInsertRepository.laborExistenceCnt(labor_id) > 0) return true;
        else return false;
    }

    public Boolean workCategoryExistenceCnt(Long work_category_id){
        if(laborAssignInsertRepository.workCategoryExistenceCnt(work_category_id) > 0) return true;
        else return false;
    }

    public Integer getGangSize(Long gang_id){
        Integer gangSize = laborAssignInsertRepository.getGangSize(gang_id);
        System.out.println("gang size : " + gangSize);
        return gangSize;
    }

    public Boolean updateGangSize(Long gangId,Integer gangSize){
        if(laborAssignInsertRepository.updateGangSize(gangId,gangSize) == 1) return true;
        else return false;
    }

    public Boolean laborAssignToGangInsert(Long gang_id, Long labor_id){
        if(laborAssignInsertRepository.laborAssignToGangInsert(gang_id,labor_id) == 1) return true;
        else return false;
    }

    public Integer gangAssignWiseLaborInsert(Long gang_assignment_id, Long gang_id, Long labor_id){
        return laborAssignInsertRepository.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id);
    }
}
