package com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository;

import com.datasoft.LaborModuleAPI.Model.WorkLocation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface WorkLocationDeleteRepository extends CrudRepository<WorkLocation, Long> {
    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_work_location", nativeQuery = true)
    @Transactional
    Integer deleteAllWorkLocation();

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_gang_to_workplace WHERE work_location_id=:id", nativeQuery = true)
    Integer chkGangAssign(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_work_location WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteWorkLocationById(@Param("id") Long id);
}
