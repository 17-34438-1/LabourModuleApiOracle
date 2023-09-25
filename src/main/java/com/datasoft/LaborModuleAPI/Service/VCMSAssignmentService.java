package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.VCMSAssignment;
import com.datasoft.LaborModuleAPI.Repository.VCMSAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class VCMSAssignmentService {
    @Autowired
    private VCMSAssignmentRepository vcmsAssignmentRepository;

    public List<VCMSAssignment> containerListByDateAndYard(String assignmentDate, String yardNo){
        return vcmsAssignmentRepository.containerListByDateAndYard(assignmentDate,yardNo);
    }

    public List<VCMSAssignment> containerListOfGangAssignmentForUnstuffing(Integer assignment_id){
        return vcmsAssignmentRepository.containerListOfGangAssignmentForUnstuffing(assignment_id);
    }

    public List<VCMSAssignment> containerListOfGangAssignmentForYard(Integer assignment_id){
        return vcmsAssignmentRepository.containerListOfGangAssignmentForYard(assignment_id);
    }
}
