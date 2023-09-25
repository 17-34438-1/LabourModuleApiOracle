package com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface GangAssignEditRepository extends CrudRepository<LaborAssignToGang, Long> {

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_gang_to_workplace WHERE id=:id", nativeQuery = true)
    Integer isGangAssignmentExists(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_gang_assignment_wise_labor WHERE gang_assignment_id=:id", nativeQuery = true)
    @Transactional
    Integer deleteLaborByGangAssignment(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_gang_assignment_wise_container WHERE gang_assignment_to_workplace_id=:id", nativeQuery = true)
    @Transactional
    Integer deleteContainerByGangAssignment(@Param("id") Long id);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_gang_assignment_wise_labor(gang_assignment_id,gang_id,labor_id,start_time,end_time) \n" +
            "VALUES(:id,:gang_id,:labor_id,:start_time,:end_time)", nativeQuery = true)
    @Transactional
    Integer gangAssignWiseLaborInsert(@Param("id") Long id,
                                      @Param("gang_id") Long gang_id,
                                      @Param("labor_id") Long labor_id,
                                      @Param("start_time") String start_time,
                                      @Param("end_time") Date end_time);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_gang_assignment_wise_container(gang_assignment_to_workplace_id,container,vsl_name,vvd_gkey,unit_gkey)\n" +
            "VALUES(:gang_assignment_to_workplace_id,:container,:vsl_name,:vvd_gkey,:unit_gkey)", nativeQuery = true)
    @Transactional
    Integer gangAssignmentWiseContainerInsert(@Param("gang_assignment_to_workplace_id") Long gang_assignment_to_workplace_id,
                                              @Param("container") String container,
                                              @Param("vsl_name") String vsl_name,
                                              @Param("vvd_gkey") String vvd_gkey,
                                              @Param("unit_gkey") String unit_gkey);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_assign_gang_to_workplace SET gang_id=:gang_id,work_location_id=:work_location_id,shift=:shift,rotation=:rotation,\n" +
            "crane=:crane,vsl_name=:vsl_name,vvd_gkey=:vvd_gkey,start_time=:start_time,end_time=:end_time,updated_at=NOW()\n" +
            "WHERE ctmsmis.lm_assign_gang_to_workplace.id=:id", nativeQuery = true)
    @Transactional
    Integer gangAssignmentUpdateforVesselOperation(@Param("id") Long id,
                                  @Param("gang_id") Long gang_id,
                                  @Param("work_location_id") Long work_location_id,
                                  @Param("shift") Integer shift,
                                  @Param("rotation") String rotation,
                                  @Param("crane") String crane,
                                  @Param("vsl_name") String vsl_name,
                                  @Param("vvd_gkey") String vvd_gkey,
                                  @Param("start_time") String start_time,
                                  @Param("end_time") Date end_time);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_assign_gang_to_workplace SET gang_id=:gang_id,work_location_id=:work_location_id,shift=:shift,shed=:shed,\n" +
            "start_time=:start_time,end_time=:end_time,updated_at=NOW()\n" +
            "WHERE ctmsmis.lm_assign_gang_to_workplace.id=:id", nativeQuery = true)
    @Transactional
    Integer gangAssignmentEditforUnstuffing(@Param("id") Long id,
                                                   @Param("gang_id") Long gang_id,
                                                   @Param("work_location_id") Long work_location_id,
                                                   @Param("shift") Integer shift,
                                                   @Param("shed") String shed,
                                                   @Param("start_time") String start_time,
                                                   @Param("end_time") Date end_time);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_assign_gang_to_workplace SET gang_id=:gang_id,work_location_id=:work_location_id,shift=:shift,yard=:yard,\n" +
            "start_time=:start_time,end_time=:end_time,updated_at=NOW()\n" +
            "WHERE ctmsmis.lm_assign_gang_to_workplace.id=:id", nativeQuery = true)
    @Transactional
    Integer gangAssignmentEditforYard(@Param("id") Long id,
                                            @Param("gang_id") Long gang_id,
                                            @Param("work_location_id") Long work_location_id,
                                            @Param("shift") Integer shift,
                                            @Param("yard") String yard,
                                            @Param("start_time") String start_time,
                                            @Param("end_time") Date end_time);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_assign_gang_to_workplace SET gang_id=:gang_id,work_location_id=:work_location_id,shift=:shift,rotation=:rotation,bl=:bl,\n" +
            "shed=:shed,vsl_name=:vsl_name,vvd_gkey=:vvd_gkey,unit_gkey=:unit_gkey,start_time=:start_time,end_time=:end_time,updated_at=NOW()\n" +
            "WHERE ctmsmis.lm_assign_gang_to_workplace.id=:id", nativeQuery = true)
    @Transactional
    Integer gangAssignmentEditforShed(@Param("id") Long id,
                                      @Param("gang_id") Long gang_id,
                                      @Param("work_location_id") Long work_location_id,
                                      @Param("shift") Integer shift,
                                      @Param("rotation") String rotation,
                                      @Param("bl") String bl,
                                      @Param("shed") String shed,
                                      @Param("vsl_name") String vsl_name,
                                      @Param("vvd_gkey") String vvd_gkey,
                                      @Param("unit_gkey") String unit_gkey,
                                      @Param("start_time") String start_time,
                                      @Param("end_time") Date end_time);
}
