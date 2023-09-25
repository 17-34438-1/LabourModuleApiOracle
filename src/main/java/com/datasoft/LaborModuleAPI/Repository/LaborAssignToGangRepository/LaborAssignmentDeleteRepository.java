package com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface LaborAssignmentDeleteRepository extends CrudRepository<LaborAssignToGang, Long> {
    @Query(value = "SELECT gang_id FROM ctmsmis.lm_assign_labor_to_gang WHERE id=:id", nativeQuery = true)
    Long getGangId(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_labor_to_gang " +
            "WHERE gang_id=:gang_id AND labor_id=:labor_id", nativeQuery = true)
    Integer cntLaborAssignmentByGangAndLabor(@Param("gang_id") Long gang_id,
                                             @Param("labor_id") Long labor_id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_assign_labor_to_gang", nativeQuery = true)
    @Transactional
    Integer deleteAllLaborAssignment();

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_assign_labor_to_gang WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteLaborAssignmentById(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_assign_labor_to_gang " +
            "WHERE gang_id=:gang_id AND labor_id=:labor_id", nativeQuery = true)
    @Transactional
    Integer deleteLaborAssignmentByGandAndLabor(@Param("gang_id") Long gang_id,
                                                @Param("labor_id") Long labor_id);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_gang_assignment_wise_labor SET end_time=NOW() \n" +
            "WHERE gang_id=:gang_id AND labor_id=:labor_id AND end_time IS NULL", nativeQuery = true)
    @Transactional
    Integer endTimeForLabor(@Param("gang_id") Long gang_id,@Param("labor_id") Long labor_id);
}
