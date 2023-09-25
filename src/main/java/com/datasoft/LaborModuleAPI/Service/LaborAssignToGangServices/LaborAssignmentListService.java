package com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import com.datasoft.LaborModuleAPI.Model.LaborByGang;
import com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository.LaborAssignmentListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaborAssignmentListService {
    @Autowired
    private LaborAssignmentListRepository laborAssignmentListRepo;

    public List<LaborAssignToGang> laborAssignmentList(){ return laborAssignmentListRepo.laborAssignmentList(); }

    public LaborAssignToGang laborAssignmentListById(Long id){ return laborAssignmentListRepo.laborAssignmentListById(id);}

    public List<LaborAssignToGang> laborAssignmentListByGangId(Long gang_id){ return laborAssignmentListRepo.laborAssignmentListByGangId(gang_id); }




}
