package com.datasoft.LaborModuleAPI.Repository.LaborRepositories;

import com.datasoft.LaborModuleAPI.Model.Labor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface LaborDetailsDeleteRepository extends CrudRepository<Labor, Long> {

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_labor", nativeQuery = true)
    @Transactional
    Integer deleteAllLabor();

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_labor_to_gang WHERE labor_id=:id", nativeQuery = true)
    Integer chkLaborAssignToGang(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_gang_to_workplace WHERE labor_id=:id", nativeQuery = true)
    Integer chkLaborAssignToWorkplace(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_labor WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteLabor(@Param("id") Long id);
}
