package com.datasoft.LaborModuleAPI.Repository.GangRepositories;

import com.datasoft.LaborModuleAPI.Model.Gang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface GangDeleteRepository extends CrudRepository<Gang, Long> {
    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_gang", nativeQuery = true)
    @Transactional
    Integer deleteAllGang();

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_labor_to_gang WHERE gang_id=:id", nativeQuery = true)
    Integer chkLaborAssignToGang(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_gang_to_workplace WHERE gang_id=:id", nativeQuery = true)
    Integer chkGangAssignToWorkplace(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_gang WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteGangById(@Param("id") Long id);
}
