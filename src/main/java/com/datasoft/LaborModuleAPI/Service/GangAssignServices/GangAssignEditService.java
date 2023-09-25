package com.datasoft.LaborModuleAPI.Service.GangAssignServices;

import com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories.GangAssignEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GangAssignEditService {
    @Autowired
    private GangAssignEditRepository gangAssignEditRepo;

    public Boolean isGangAssignmentExists(Long id){



        if(gangAssignEditRepo.isGangAssignmentExists(id) == 1) return true;
        else return false;
    }

    public Integer deleteLaborByGangAssignment(Long id){
        return gangAssignEditRepo.deleteLaborByGangAssignment(id);
//        if(gangAssignEditRepo.deleteLaborByGangAssignment(id) > 0) return true;
//        else return false;
    }

    public Boolean deleteContainerByGangAssignment(Long id){
        if(gangAssignEditRepo.deleteContainerByGangAssignment(id) > 0) return true;
        else return false;
    }

    public Integer gangAssignWiseLaborInsert(Long id, Long gang_id, Long labor_id,String start_time, Date end_time){
        return gangAssignEditRepo.gangAssignWiseLaborInsert(id,gang_id,labor_id,start_time,end_time);
    }

    public Integer gangAssignmentWiseContainerInsert(Long gang_assignment_to_workplace_id, String container,String vsl_name,String vvd_gkey,String unit_gkey){
        return gangAssignEditRepo.gangAssignmentWiseContainerInsert(gang_assignment_to_workplace_id,container,vsl_name,vvd_gkey,unit_gkey);
    }

    public Integer gangAssignmentUpdateforVesselOperation(Long id,Long gang_id, Long work_location_id, Integer shift, String rotation, String crane, String vsl_name, String vvd_gkey, String start_time, Date end_time){
        return gangAssignEditRepo.gangAssignmentUpdateforVesselOperation(id,gang_id,work_location_id,shift,rotation,crane,vsl_name,vvd_gkey,start_time,end_time);
    }

    public Integer gangAssignmentEditforUnstuffing(Long id,Long gang_id, Long work_location_id, Integer shift, String shed, String start_time, Date end_time){
        return gangAssignEditRepo.gangAssignmentEditforUnstuffing(id,gang_id,work_location_id,shift,shed,start_time,end_time);
    }

    public Integer gangAssignmentEditforYard(Long id,Long gang_id, Long work_location_id, Integer shift, String yard, String start_time, Date end_time){
        return gangAssignEditRepo.gangAssignmentEditforYard(id,gang_id,work_location_id,shift,yard,start_time,end_time);
    }

    public Integer gangAssignmentEditforShed(Long id, Long gang_id, Long work_location_id,Integer shift,String rotation,String bl, String shed, String vsl_name,String vvd_gkey,String unit_gkey, String start_time, Date end_time){
        return gangAssignEditRepo.gangAssignmentEditforShed(id,gang_id,work_location_id,shift,rotation,bl,shed,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
    }
}
