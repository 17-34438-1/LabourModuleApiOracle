package com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface WorkCategoryDeleteRepository extends CrudRepository<DesignationCategory, Long> {

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_work_category", nativeQuery = true)
    @Transactional
    Integer deleteAllWorkCategory();

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_assign_labor_to_gang WHERE work_category_id=:id", nativeQuery = true)
    Integer chkWorkLocation(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_gang WHERE work_category_id=:id", nativeQuery = true)
    Integer chkWorkCategoryInGang(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_work_category WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteWorkCategoryById(@Param("id") Long id);
}
