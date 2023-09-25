package com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface LaborAssignToGangInsertRepository extends CrudRepository<LaborAssignToGang, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_labor_to_gang WHERE labor_id=:labor_id AND gang_id=:gang_id", nativeQuery = true)
    Integer laborAssignmentCnt(@Param("labor_id") Long labor_id,@Param("gang_id") Long gang_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_gang WHERE id=:gang_id", nativeQuery = true)
    Integer gangExistenceCnt(@Param("gang_id") Long gang_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor WHERE id=:labor_id", nativeQuery = true)
    Integer laborExistenceCnt(@Param("labor_id") Long labor_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_category WHERE id=:work_category_id", nativeQuery = true)
    Integer workCategoryExistenceCnt(@Param("work_category_id") Long work_category_id);

    @Query(value = "SELECT size FROM ctmsmis.lm_gang WHERE id=:gang_id", nativeQuery = true)
    Integer getGangSize(@Param("gang_id") Long gang_id);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_assign_labor_to_gang(gang_id,labor_id,created_at) VALUES(:gang_id,:labor_id,NOW())", nativeQuery = true)
    @Transactional
    Integer laborAssignToGangInsert(@Param("gang_id") Long gang_id,@Param("labor_id") Long labor_id);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_gang SET size=:gangSize WHERE id=:gangId", nativeQuery = true)
    @Transactional
    Integer updateGangSize(@Param("gangId") Long gangId,@Param("gangSize") Integer gangSize);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_gang_assignment_wise_labor(gang_assignment_id,gang_id,labor_id,start_time) \n" +
            "VALUES(:gang_assignment_id,:gang_id,:labor_id,NOW())", nativeQuery = true)
    @Transactional
    Integer gangAssignWiseLaborInsert(@Param("gang_assignment_id") Long gang_assignment_id,
                                      @Param("gang_id") Long gang_id,
                                      @Param("labor_id") Long labor_id);
}
