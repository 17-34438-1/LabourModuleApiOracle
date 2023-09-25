package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.VCMSAssignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VCMSAssignmentRepository extends CrudRepository<VCMSAssignment, Long> {
    @Query(value = "SELECT unit_gkey,cont_no FROM ctmsmis.tmp_vcms_assignment \n" +
            "WHERE assignmentDate=:assignmentDate AND Yard_No=:yardNo", nativeQuery = true)
    public List<VCMSAssignment> containerListByDateAndYard(@Param("assignmentDate") String assignmentDate,
                                                   @Param("yardNo") String yardNo);

    @Query(value = "SELECT container,unit_gkey FROM ctmsmis.lm_gang_assignment_wise_container \n" +
            "WHERE gang_assignment_to_workplace_id=:assignment_id", nativeQuery = true)
    public List<VCMSAssignment> containerListOfGangAssignmentForUnstuffing(@Param("assignment_id") Integer assignment_id);

    @Query(value = "SELECT container,unit_gkey FROM ctmsmis.lm_gang_assignment_wise_container\n" +
            "WHERE gang_assignment_to_workplace_id=:assignment_id", nativeQuery = true)
    public List<VCMSAssignment> containerListOfGangAssignmentForYard(@Param("assignment_id") Integer assignment_id);
}
