package com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface WorkCategoryEditRepository extends CrudRepository<DesignationCategory, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_category WHERE (name=:name) AND (id!=:id)", nativeQuery = true)
    Integer isUnique(@Param("id") Long id,@Param("name") String name);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_work_category SET name=:name, updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer WorkCategoryUpdate(@Param("id") Long id, @Param("name") String name);
}
