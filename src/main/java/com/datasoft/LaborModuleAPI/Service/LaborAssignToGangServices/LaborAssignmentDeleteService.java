package com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices;

import com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository.LaborAssignmentDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaborAssignmentDeleteService {
    @Autowired
    private LaborAssignmentDeleteRepository labor_assignment_dlt_repo;

    public Long getGangId(Long id){
        Long gang_id = labor_assignment_dlt_repo.getGangId(id);
        return gang_id;
    }

    public Integer cntLaborAssignmentByGangAndLabor(Long gang_id, Long labor_id){
        Integer cntLaborAssignment = labor_assignment_dlt_repo.cntLaborAssignmentByGangAndLabor(gang_id,labor_id);
        return cntLaborAssignment;
    }

    public Boolean deleteAllLaborAssignment(){
        if(labor_assignment_dlt_repo.deleteAllLaborAssignment() == 1) return true;
        else return false;
    }

    public Boolean deleteLaborAssignmentById(Long id){
        if(labor_assignment_dlt_repo.deleteLaborAssignmentById(id) == 1) return true;
        else return false;
    }

    public Boolean deleteLaborAssignmentByGandAndLabor(Long gang_id, Long labor_id){
        if(labor_assignment_dlt_repo.deleteLaborAssignmentByGandAndLabor(gang_id,labor_id) == 1) return true;
        else return false;
    }

    public Integer endTimeForLabor(Long gang_id, Long labor_id){
        return labor_assignment_dlt_repo.endTimeForLabor(gang_id,labor_id);
    }
}
