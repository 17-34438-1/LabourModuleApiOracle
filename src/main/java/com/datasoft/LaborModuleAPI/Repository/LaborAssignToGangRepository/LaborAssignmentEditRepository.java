package com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface LaborAssignmentEditRepository extends CrudRepository<LaborAssignToGang, Long> {

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_labor_to_gang WHERE (labor_id=:labor_id) AND (gang_id=:gang_id) AND (id!=:id)", nativeQuery = true)
    Integer laborAssignmentCnt(@Param("id") Long id,@Param("labor_id") Long labor_id,@Param("gang_id") Long gang_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_gang WHERE id=:gang_id", nativeQuery = true)
    Integer gangExistenceCnt(@Param("gang_id") Long gang_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_category WHERE id=:work_category_id", nativeQuery = true)
    Integer workCategoryExistenceCnt(@Param("work_category_id") Long work_category_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor WHERE id=:labor_id", nativeQuery = true)
    Integer laborExistenceCnt(@Param("labor_id") Long labor_id);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_assign_labor_to_gang SET gang_id=:gang_id,labor_id=:labor_id,updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer LaborAssignmentUpdate(@Param("id") Long id,@Param("gang_id") Long gang_id,@Param("labor_id") Long labor_id);
}
