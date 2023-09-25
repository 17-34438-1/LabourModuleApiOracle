package com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories;

import com.datasoft.LaborModuleAPI.Model.GangAssign;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface GangAssignInsertRepository extends CrudRepository<GangAssign, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_gang WHERE id=:gang_id", nativeQuery = true)
    Integer gangExistenceCnt(@Param("gang_id") Long gang_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor WHERE id=:labor_id", nativeQuery = true)
    Integer laborExistenceCnt(@Param("labor_id") Long labor_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_location WHERE id=:work_location_id", nativeQuery = true)
    Integer workLocationExistenceCnt(@Param("work_location_id") Long work_location_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_category WHERE id=:work_category_id", nativeQuery = true)
    Integer workCategoryExistenceCnt(@Param("work_category_id") Long work_category_id);



    @Query(value = "SELECT COUNT(*) FROM sparcsn4.vsl_vessel_visit_details WHERE ib_vyg=:rotation", nativeQuery = true)
    Integer isRotationExists(@Param("rotation") String rotation);




    @Query(value = "SELECT COUNT(*) FROM sparcsn4.inv_unit WHERE id=:container", nativeQuery = true)
    Integer isContainerExists(@Param("container") String container);


    //Query for fetching Vessel Name & vvd_gkey using Rotaion Number Starts....
    @Query(value = "SELECT sparcsn4.vsl_vessel_visit_details.ib_vyg AS rotation_no,sparcsn4.argo_carrier_visit.gkey AS vvd_gkey," +
            "sparcsn4.vsl_vessels.name AS vsl_name\n" +
            "FROM sparcsn4.argo_carrier_visit\n" +
            "INNER JOIN sparcsn4.vsl_vessel_visit_details ON sparcsn4.argo_carrier_visit.cvcvd_gkey=sparcsn4.vsl_vessel_visit_details.vvd_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessels ON sparcsn4.vsl_vessels.gkey=sparcsn4.vsl_vessel_visit_details.vessel_gkey\n" +
            "WHERE vsl_vessel_visit_details.ib_vyg=:rotation LIMIT 1", nativeQuery = true)
    public List[] getVslNameAndVvdGkeyByRotation(@Param("rotation") String rotation);
    //Query for fetching Vessel Name & vvd_gkey using Rotaion Number Ends.....

    //Query for fetching unit_gkey, last import rotation, vsl_name, vvd_gkey using Container Number Starts....
    @Query(value = "SELECT ib_vyg AS rotation_no,inv_unit.id AS cont_no,inv_unit.gkey AS unit_gkey,\n" +
            "argo_carrier_visit.gkey AS vvd_gkey,vsl_vessels.name AS vsl_name\n" +
            "FROM sparcsn4.inv_unit\n" +
            "INNER JOIN sparcsn4.inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN sparcsn4.argo_carrier_visit ON sparcsn4.argo_carrier_visit.gkey= sparcsn4.inv_unit_fcy_visit.actual_ib_cv\n" +
            "INNER JOIN sparcsn4.vsl_vessel_visit_details ON sparcsn4.argo_carrier_visit.cvcvd_gkey=sparcsn4.vsl_vessel_visit_details.vvd_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessels ON sparcsn4.vsl_vessels.gkey=sparcsn4.vsl_vessel_visit_details.vessel_gkey\n" +
            "WHERE inv_unit.id=:container ORDER BY vsl_vessel_visit_details.vvd_gkey DESC LIMIT 1", nativeQuery = true)
    public List[] getUnitGkeyAndVesselByContainer(@Param("container") String container);
    //Query for fetching unit_gkey, last import rotation, vsl_name, vvd_gkey using Container Number Ends.....

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_assign_gang_to_workplace(gang_id,work_location_id,shift,rotation," +
                                        "bl,crane,shed,yard,vsl_name,vvd_gkey,unit_gkey,start_time,end_time,created_at) \n" +
            "VALUES(:gang_id,:work_location_id,:shift,:rotation,:bl,:crane,:shed,:yard,:vsl_name,:vvd_gkey," +
            ":unit_gkey,:start_time,:end_time,NOW())", nativeQuery = true)
    @Transactional
    Integer gangAssignInsertForVesselOperation(@Param("gang_id") Long gang_id,
                            @Param("work_location_id") Long work_location_id,
                            @Param("shift") Integer shift,
                            @Param("rotation") String rotation,
                            @Param("bl") String bl,
                            @Param("crane") String crane,
                            @Param("shed") String shed,
                            @Param("yard") String yard,
                            @Param("vsl_name") String vsl_name,
                            @Param("vvd_gkey") String vvd_gkey,
                            @Param("unit_gkey") String unit_gkey,
                            @Param("start_time") String start_time,
                            @Param("end_time") Date end_time);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_assign_gang_to_workplace(gang_id,work_location_id,shift,rotation," +
            "bl,shed,vsl_name,vvd_gkey,unit_gkey,start_time,end_time,created_at) \n" +
            "VALUES(:gang_id,:work_location_id,:shift,:rotation,:bl,:shed,:vsl_name,:vvd_gkey," +
            ":unit_gkey,:start_time,:end_time,NOW())", nativeQuery = true)
    @Transactional
    Integer gangAssignInsertForShed(@Param("gang_id") Long gang_id,
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


    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_assign_gang_to_workplace(gang_id,work_location_id,shift,shed,start_time,end_time,created_at) \n" +
            "VALUES(:gang_id,:work_location_id,:shift,:shed,:start_time,:end_time,NOW())", nativeQuery = true)
    @Transactional
    Integer gangAssignInsertForUnstuffing(@Param("gang_id") Long gang_id,
                             @Param("work_location_id") Long work_location_id,
                             @Param("shift") Integer shift,
                             @Param("shed") String shed,
                             @Param("start_time") String start_time,
                             @Param("end_time") Date end_time);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_assign_gang_to_workplace(gang_id,work_location_id,shift,yard,start_time,end_time,created_at) \n" +
            "VALUES(:gang_id,:work_location_id,:shift,:yard,:start_time,:end_time,NOW())", nativeQuery = true)
    @Transactional
    Integer gangAssignInsertForYard(@Param("gang_id") Long gang_id,
                                          @Param("work_location_id") Long work_location_id,
                                          @Param("shift") Integer shift,
                                          @Param("yard") String yard,
                                          @Param("start_time") String start_time,
                                          @Param("end_time") Date end_time);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_gang_to_workplace \n" +
            "WHERE gang_id=:gang_id AND work_location_id=:work_location_id AND shift=:shift AND \n" +
            "shed=:shed AND start_time=:start_time ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Integer isGangAssignmentForUnstuffingExists(@Param("gang_id") Long gang_id,
                                                @Param("work_location_id") Long work_location_id,
                                                @Param("shift") Integer shift,
                                                @Param("shed") String shed,
                                                @Param("start_time") String start_time);

    @Query(value = "SELECT id FROM ctmsmis.lm_assign_gang_to_workplace\n" +
            "WHERE gang_id=:gang_id AND work_location_id=:work_location_id AND shift=:shift AND \n" +
            "shed=:shed AND start_time=:start_time ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Integer lastGangAssignmentForUnstuffingId(@Param("gang_id") Long gang_id,
                                                @Param("work_location_id") Long work_location_id,
                                                @Param("shift") Integer shift,
                                                @Param("shed") String shed,
                                                @Param("start_time") String start_time);


    @Query(value = "SELECT id FROM ctmsmis.lm_assign_gang_to_workplace ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Integer lastGangAssignmentId();

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_gang_assignment_wise_container(gang_assignment_to_workplace_id,container,vsl_name,vvd_gkey,unit_gkey)\n" +
            "VALUES(:gang_assignment_to_workplace_id,:container,:vsl_name,:vvd_gkey,:unit_gkey)", nativeQuery = true)
    @Transactional
    Integer gangAssignmentWiseContainerInsert(@Param("gang_assignment_to_workplace_id") Integer gang_assignment_to_workplace_id,
                                        @Param("container") String container,
                                        @Param("vsl_name") String vsl_name,
                                        @Param("vvd_gkey") String vvd_gkey,
                                        @Param("unit_gkey") String unit_gkey);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_gang_assignment_wise_labor(gang_assignment_id,gang_id,labor_id,start_time) \n" +
            "VALUES(:gang_assignment_id,:gang_id,:labor_id,:start_time)", nativeQuery = true)
    @Transactional
    Integer gangAssignWiseLaborInsert(@Param("gang_assignment_id") Integer gang_assignment_id,
                                      @Param("gang_id") Long gang_id,
                                      @Param("labor_id") Long labor_id,
                                      @Param("start_time") String start_time);



}
