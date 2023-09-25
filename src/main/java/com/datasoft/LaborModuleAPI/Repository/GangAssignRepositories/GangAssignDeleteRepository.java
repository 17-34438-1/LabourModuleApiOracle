package com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface GangAssignDeleteRepository extends CrudRepository<LaborAssignToGang, Long> {
    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_assign_gang_to_workplace", nativeQuery = true)
    @Transactional
    Integer deleteAllGangAssignment();

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_assign_gang_to_workplace WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteGangAssignmentById(@Param("id") Long id);
}
