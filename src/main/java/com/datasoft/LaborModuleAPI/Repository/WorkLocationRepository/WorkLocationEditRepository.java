package com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface WorkLocationEditRepository extends CrudRepository<DesignationCategory, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_location WHERE (name=:name) AND (id!=:id)", nativeQuery = true)
    Integer isUnique(@Param("id") Long id,@Param("name") String name);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_work_location SET name=:name,work_category_id=:work_category_id, updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer WorkLocationUpdate(@Param("id") Long id,@Param("name") String name,@Param("work_category_id") Integer work_category_id);
}
